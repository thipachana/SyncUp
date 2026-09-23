import { cloneElement, useCallback, useEffect, useState } from 'react'
import { Session } from './session-context'
import { apiFetch, resetSession, errorMessage } from './api'
export function SessionProvider({ children }) {
  const [currentUser, setCurrentUser] = useState(null)
  const [sessionError, setSessionError] = useState('')
  const [sessionLoading, setSessionLoading] = useState(true)
  const changeUser = useCallback((user) => { resetSession(); setCurrentUser(user); setSessionError('') }, [])
  const retrySession = useCallback(async () => {
    if (import.meta.env.VITE_PAGES_PREVIEW === 'true') {
      setSessionLoading(false); setSessionError('GitLab-Pages-Vorschau: Anmeldung und gespeicherte Daten benötigen ein separat bereitgestelltes Backend.'); return
    }
    setSessionLoading(true); setSessionError('')
    try {
      const response = await apiFetch('/api/auth/me')
      if (response.status === 401) { setCurrentUser(null); return }
      const data = await response.json()
      if (!response.ok) throw new Error(data.message || 'Sitzung konnte nicht geladen werden.')
      setCurrentUser(data)
    } catch (error) { if (error.name !== 'AbortError') setSessionError(errorMessage(error)) }
    finally { setSessionLoading(false) }
  }, [])
  useEffect(() => {
    Promise.resolve().then(retrySession)
    const expired = () => { changeUser(null); setSessionError('Deine Sitzung ist abgelaufen. Bitte erneut anmelden.') }
    window.addEventListener('syncup:expired', expired)
    return () => window.removeEventListener('syncup:expired', expired)
  }, [retrySession, changeUser])
  return <Session.Provider value={{ currentUser, changeUser, sessionError, sessionLoading, retrySession }}>{cloneElement(children, { key: currentUser?.benutzerId || 'guest' })}</Session.Provider>
}
