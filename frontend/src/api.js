const base = import.meta.env?.VITE_API_URL || ''
let csrfToken, pendingToken, epoch = 0
export function resetSession() { epoch += 1; csrfToken = undefined; pendingToken = undefined }
export function errorMessage(error) {
  return error.name === 'TypeError' ? 'Verbindung zum Backend fehlgeschlagen. Bitte erneut versuchen.' : error.message
}
async function token() {
  if (csrfToken) return csrfToken
  if (pendingToken) return pendingToken
  const version = epoch
  const pending = (async () => {
    const response = await fetch(`${base}/api/auth/csrf`, { credentials: 'include' })
    if (!response.ok || !response.headers.get('content-type')?.includes('application/json')) throw new Error('Backend nicht erreichbar. Bitte erneut versuchen.')
    const data = await response.json()
    if (version !== epoch) throw new DOMException('Veraltete Sitzung', 'AbortError')
    if (!data.token) throw new Error('Sitzungsschutz konnte nicht geladen werden.')
    csrfToken = data.token
    return csrfToken
  })()
  pendingToken = pending
  try { return await pending } finally { if (pendingToken === pending) pendingToken = undefined }
}
export async function apiFetch(path, options = {}) {
  if (import.meta.env?.VITE_PAGES_PREVIEW === 'true') throw new Error('Diese Vorschau hat noch kein Backend. Anmeldung und Speichern sind hier noch nicht verfügbar.')
  const version = epoch
  const method = options.method || 'GET'
  const headers = { ...options.headers }
  const writing = !['GET', 'HEAD', 'OPTIONS'].includes(method)
  if (writing) headers['X-CSRF-TOKEN'] = await token()
  if (version !== epoch) throw new DOMException('Veraltete Sitzung', 'AbortError')
  const response = await fetch(`${base}${path}`, { ...options, headers, credentials: 'include' })
  if (version !== epoch) throw new DOMException('Veraltete Sitzung', 'AbortError')
  if (response.status === 403) { csrfToken = undefined; pendingToken = undefined }
  if (response.ok && ['/api/auth/login', '/api/auth/logout'].includes(path)) { csrfToken = undefined; pendingToken = undefined }
  if (response.status === 401 && !path.startsWith('/api/auth/')) window.dispatchEvent(new Event('syncup:expired'))
  if (response.status !== 204 && !response.headers.get('content-type')?.includes('application/json')) throw new Error('Backend antwortet nicht mit Anwendungsdaten. Bitte Verbindung prüfen.')
  const readJson = response.json.bind(response)
  response.json = async () => {
    const data = await readJson()
    if (version !== epoch) throw new DOMException('Veraltete Sitzung', 'AbortError')
    return data
  }
  return response
}
export async function api(path, options = {}) {
  const response = await apiFetch(path, { ...options, ...(options.body ? { body: JSON.stringify(options.body), headers: { 'Content-Type': 'application/json' } } : {}) })
  const data = response.status === 204 ? null : await response.json()
  if (!response.ok) throw new Error(data?.message || 'Anfrage fehlgeschlagen.')
  return data
}
