import { test, beforeEach, afterEach } from 'node:test'
import assert from 'node:assert/strict'
import { api, apiFetch, resetSession, errorMessage } from '../src/api.js'
const originalFetch = globalThis.fetch
const json = (body, status = 200) => new Response(JSON.stringify(body), { status, headers: { 'content-type': 'application/json' } })
beforeEach(() => { resetSession(); globalThis.window = new EventTarget() })
afterEach(() => { globalThis.fetch = originalFetch })
test('concurrent writes share CSRF fetch and include credentials', async () => {
  let tokens = 0
  globalThis.fetch = async (path, options) => {
    assert.equal(options.credentials, 'include')
    if (path.endsWith('/csrf')) { tokens++; return json({ token: 'token' }) }
    assert.equal(options.headers['X-CSRF-TOKEN'], 'token'); return json({ ok: true })
  }
  await Promise.all([api('/api/ressourcen', { method: 'POST', body: {} }), api('/api/terminanfragen', { method: 'POST', body: {} })])
  assert.equal(tokens, 1)
})
test('login rotates cached CSRF token', async () => {
  let tokens = 0
  globalThis.fetch = async (path, options) => {
    if (path.endsWith('/csrf')) return json({ token: `token-${++tokens}` })
    assert.equal(options.headers['X-CSRF-TOKEN'], path.endsWith('/login') ? 'token-1' : 'token-2')
    return json({ ok: true })
  }
  await api('/api/auth/login', { method: 'POST', body: {} })
  await api('/api/me/termine', { method: 'POST', body: {} })
  assert.equal(tokens, 2)
})
test('old-session response is discarded after logout or account switch', async () => {
  let resolve
  globalThis.fetch = () => new Promise((done) => { resolve = done })
  const pending = api('/api/me/termine')
  resetSession(); resolve(json([{ titel: 'private old data' }]))
  await assert.rejects(pending, { name: 'AbortError' })
})
test('old-session JSON body is also discarded during parsing', async () => {
  let resolve
  globalThis.fetch = async () => ({ status: 200, ok: true, headers: new Headers({ 'content-type': 'application/json' }), json: () => new Promise((done) => { resolve = done }) })
  const response = await apiFetch('/api/me/termine'); const pending = response.json()
  resetSession(); resolve([])
  await assert.rejects(pending, { name: 'AbortError' })
})
test('HTML from wrong API endpoint is not treated as empty data', async () => {
  globalThis.fetch = async () => new Response('<html>fallback</html>', { headers: { 'content-type': 'text/html' } })
  await assert.rejects(api('/api/me/termine'), /Backend antwortet nicht/)
})
test('failed logout remains an error', async () => {
  globalThis.fetch = async (path) => path.endsWith('/csrf') ? json({ token: 'token' }) : json({ message: 'Abmeldung fehlgeschlagen.' }, 500)
  await assert.rejects(api('/api/auth/logout', { method: 'POST' }), /Abmeldung fehlgeschlagen/)
})
test('unauthorized domain request emits session-expired event', async () => {
  let expired = 0; window.addEventListener('syncup:expired', () => { expired++ })
  globalThis.fetch = async () => json({ message: 'Bitte anmelden.' }, 401)
  await assert.rejects(api('/api/me/termine'), /Bitte anmelden/)
  assert.equal(expired, 1)
})
test('connection errors have readable message', () => { assert.match(errorMessage(new TypeError('Failed to fetch')), /Verbindung zum Backend/) })
