import { useEffect, useState } from 'react'

const API_URL =
  import.meta.env.VITE_API_URL || 'http://localhost:8080'

function AuthControl() {
  const [currentUser, setCurrentUser] = useState(null)
  const [authOpen, setAuthOpen] = useState(false)
  const [authMode, setAuthMode] = useState('login')

  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [passwort, setPasswort] = useState('')

  const [message, setMessage] = useState('')
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    const loadCurrentUser = async () => {
      try {
        const response = await fetch(
          `${API_URL}/api/auth/me`,
          {
            credentials: 'include',
          }
        )

        if (!response.ok) {
          return
        }

        const data = await response.json()
        setCurrentUser(data)
      } catch (error) {
        console.error(
          'Angemeldeter Benutzer konnte nicht geladen werden:',
          error
        )
      }
    }

    loadCurrentUser()
  }, [])

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
    const response = await fetch(
      `${API_URL}/api/auth/login`,
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

    try {
      if (authMode === 'register') {
        const registerResponse = await fetch(
          `${API_URL}/api/auth/register`,
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

        await login(email, passwort)
      } else {
        await login(email, passwort)
      }

      setAuthOpen(false)
      resetForm()
    } catch (error) {
      setMessage(error.message)
    } finally {
      setLoading(false)
    }
  }

  const logout = async () => {
    try {
      await fetch(
        `${API_URL}/api/auth/logout`,
        {
          method: 'POST',
          credentials: 'include',
        }
      )
    } catch (error) {
      console.error('Fehler beim Abmelden:', error)
    }

    setCurrentUser(null)
  }

  return (
    <>
      {currentUser ? (
        <div className="user-area">
          <span className="user-name">
            {currentUser.name}
          </span>

          <button
            className="logout-button"
            type="button"
            onClick={logout}
          >
            Abmelden
          </button>
        </div>
      ) : (
        <button
          className="login-placeholder"
          type="button"
          onClick={() => openAuth('login')}
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
          >
            <button
              className="auth-close"
              type="button"
              onClick={closeAuth}
            >
              ×
            </button>

            <div className="auth-logo">✓</div>

            <h2>
              {authMode === 'login'
                ? 'Willkommen zurück'
                : 'Konto erstellen'}
            </h2>

            <p className="auth-subtitle">
              {authMode === 'login'
                ? 'Melde dich bei SyncUp an.'
                : 'Registriere dich bei SyncUp.'}
            </p>

            <div className="auth-tabs">
              <button
                type="button"
                className={
                  authMode === 'login'
                    ? 'auth-tab active'
                    : 'auth-tab'
                }
                onClick={() => {
                  setAuthMode('login')
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
                  setAuthMode('register')
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
                    type="text"
                    value={name}
                    onChange={(event) =>
                      setName(event.target.value)
                    }
                    placeholder="Dein Name"
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
                  type="email"
                  value={email}
                  onChange={(event) =>
                    setEmail(event.target.value)
                  }
                  placeholder="name@beispiel.de"
                  autoComplete="email"
                  required
                />
              </div>

              <div className="auth-field">
                <label htmlFor="auth-password">
                  Passwort
                </label>

                <input
                  id="auth-password"
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