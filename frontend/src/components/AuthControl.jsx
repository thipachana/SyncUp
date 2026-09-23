import { useState } from 'react'
import { useSession } from '../session-context'
import { apiFetch, errorMessage } from '../api'

function AuthControl() {
  const { currentUser, changeUser: setCurrentUser, sessionError, sessionLoading, retrySession } = useSession()
  const [authOpen, setAuthOpen] = useState(false)
  const [authMode, setAuthMode] = useState('login')

  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [passwort, setPasswort] = useState('')

  const [message, setMessage] = useState('')
  const [loading, setLoading] = useState(false)

  const resetForm = () => {
    setName('')
    setEmail('')
    setPasswort('')
    setMessage('')
  }

  const openAuth = (mode) => {
    resetForm()
    setAuthMode(mode)
    setAuthOpen(true)
  }

  const closeAuth = () => {
    if (!loading) {
      setAuthOpen(false)
      resetForm()
    }
  }

  const login = async (loginEmail, loginPasswort) => {
    const response = await apiFetch(
      '/api/auth/login',
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include',
        body: JSON.stringify({
          email: loginEmail,
          passwort: loginPasswort,
        }),
      }
    )

    const data = await response.json()

    if (!response.ok) {
      throw new Error(
        data.message || 'Anmeldung fehlgeschlagen.'
      )
    }

    setCurrentUser(data)
  }

  const handleSubmit = async (event) => {
    event.preventDefault()

    setMessage('')
    setLoading(true)

    let registered = false
    try {
      if (authMode === 'register') {
        const registerResponse = await apiFetch(
          '/api/auth/register',
          {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            credentials: 'include',
            body: JSON.stringify({
              name,
              email,
              passwort,
            }),
          }
        )

        const registerData =
          await registerResponse.json()

        if (!registerResponse.ok) {
          throw new Error(
            registerData.message ||
              'Registrierung fehlgeschlagen.'
          )
        }

        registered = true
        await login(email.trim(), passwort)
      } else {
        await login(email.trim(), passwort)
      }

      setAuthOpen(false)
      resetForm()
    } catch (error) {
      if (registered) setAuthMode('login')
      setMessage((registered ? 'Konto erstellt. Bitte anmelden. ' : '') + errorMessage(error))
    } finally {
      setLoading(false)
    }
  }

  const logout = async () => {
    setLoading(true); setMessage('')
    try {
      const response = await apiFetch('/api/auth/logout', { method: 'POST' })
      if (!response.ok && response.status !== 401) throw new Error('Abmeldung fehlgeschlagen. Bitte erneut versuchen.')
      setCurrentUser(null)
    } catch (error) { setMessage(errorMessage(error)) }
    finally { setLoading(false) }
  }

  return (
    <>
      {sessionError && <div role="alert" className="auth-message">{sessionError}<button type="button" onClick={retrySession}>Verbindung erneut prüfen</button></div>}
      {!authOpen && message && <p role="alert" className="auth-message">{message}</p>}
      {currentUser ? (
        <div className="user-area">
          <span className="user-name">
            {currentUser.name}
          </span>

          <button
            className="logout-button"
            type="button"
            onClick={logout}
            disabled={loading}
          >
            Abmelden
          </button>
        </div>
      ) : (
        <button
          className="login-placeholder"
          type="button"
          onClick={() => openAuth('login')}
          disabled={sessionLoading}
        >
          Anmelden
        </button>
      )}

      {authOpen && (
        <div
          className="auth-overlay"
          onMouseDown={(event) => {
            if (event.target === event.currentTarget) {
              closeAuth()
            }
          }}
        >
          <section
            className="auth-modal"
            role="dialog"
            aria-modal="true"
            aria-labelledby="auth-heading"
          >
            <button
              className="auth-close"
              type="button"
              onClick={closeAuth}
              aria-label="Anmeldung schließen"
            >
              ×
            </button>

            <div className="auth-logo">✓</div>

            <h2 id="auth-heading">
              {authMode === 'login'
                ? 'Willkommen zurück'
                : 'Konto erstellen'}
            </h2>

            <p className="auth-subtitle">
              {authMode === 'login'
                ? 'Melde dich bei SyncUp an.'
                : 'Registriere dich bei SyncUp.'}
            </p>

            <div className="auth-tabs" aria-busy={loading}>
              <button
                type="button"
                className={
                  authMode === 'login'
                    ? 'auth-tab active'
                    : 'auth-tab'
                }
                onClick={() => {
                  if (loading) return
                  setAuthMode('login')
                  setPasswort('')
                  setMessage('')
                }}
              >
                Anmelden
              </button>

              <button
                type="button"
                className={
                  authMode === 'register'
                    ? 'auth-tab active'
                    : 'auth-tab'
                }
                onClick={() => {
                  if (loading) return
                  setAuthMode('register')
                  setPasswort('')
                  setMessage('')
                }}
              >
                Registrieren
              </button>
            </div>

            <form
              className="auth-form"
              onSubmit={handleSubmit}
            >
              {authMode === 'register' && (
                <div className="auth-field">
                  <label htmlFor="auth-name">
                    Name
                  </label>

                  <input
                    id="auth-name"
                    maxLength={100}
                    type="text"
                    value={name}
                    onChange={(event) =>
                      setName(event.target.value)
                    }
                    placeholder="Dein Name"
                    disabled={loading}
                  required
                  />
                </div>
              )}

              <div className="auth-field">
                <label htmlFor="auth-email">
                  E-Mail
                </label>

                <input
                  id="auth-email"
                  maxLength={255}
                  type="email"
                  value={email}
                  onChange={(event) =>
                    setEmail(event.target.value)
                  }
                  placeholder="name@beispiel.de"
                  autoComplete="email"
                  disabled={loading}
                  required
                />
              </div>

              <div className="auth-field">
                <label htmlFor="auth-password">
                  Passwort
                </label>

                <input
                  id="auth-password"
                  minLength={authMode === 'register' ? 8 : undefined}
                  maxLength={72}
                  type="password"
                  value={passwort}
                  onChange={(event) =>
                    setPasswort(event.target.value)
                  }
                  placeholder="Passwort"
                  autoComplete={
                    authMode === 'login'
                      ? 'current-password'
                      : 'new-password'
                  }
                  disabled={loading}
                  required
                />
              </div>

              {message && (
                <div className="auth-message">
                  {message}
                </div>
              )}

              <button
                className="auth-submit"
                type="submit"
                disabled={loading}
              >
                {loading
                  ? 'Bitte warten...'
                  : authMode === 'login'
                    ? 'Anmelden'
                    : 'Registrieren'}
              </button>
            </form>
          </section>
        </div>
      )}
    </>
  )
}

export default AuthControl