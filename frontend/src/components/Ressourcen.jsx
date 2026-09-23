import { useEffect, useState } from 'react'
import { useSession } from '../session-context'
import { api, errorMessage } from '../api'
function Ressourcen({ ressourcen, ladefehler, onRefresh }) {
  const { currentUser } = useSession()
  const [termine, setTermine] = useState([])
  const [terminId, setTerminId] = useState('')
  const [start, setStart] = useState('')
  const [end, setEnd] = useState('')
  const [message, setMessage] = useState('')
  const [messageIsSuccess, setSuccess] = useState(false)
  const [busy, setBusy] = useState(false)
  const [termsLoading, setTermsLoading] = useState(Boolean(currentUser))
  const [termsError, setTermsError] = useState('')
  const [refresh, setRefresh] = useState(0)
  const [resourceName, setResourceName] = useState('')
  const [capacity, setCapacity] = useState('1')
  useEffect(() => {
    if (!currentUser) return
    let active = true
    api('/api/me/termine').then((data) => { if (active) setTermine(data) })
      .catch((error) => { if (active && error.name !== 'AbortError') setTermsError(errorMessage(error)) })
      .finally(() => { if (active) setTermsLoading(false) })
    return () => { active = false }
  }, [currentUser, refresh])
  function selectTermin(id) {
    setTerminId(id); setMessage('')
    const term = termine.find((t) => t.terminId === Number(id))
    setStart(term ? `${term.datum}T${term.startzeit.slice(0,5)}` : '')
    setEnd(term ? `${term.datum}T${term.endzeit.slice(0,5)}` : '')
  }
  const bucheRessource = async (ressourcenId) => {
    if (busy) return
    setMessage(''); setSuccess(false)
    if (!currentUser || !terminId || !start || !end || end <= start) { setMessage('Bitte anmelden, einen eigenen Termin und einen gültigen Zeitraum wählen.'); return }
    setBusy(true)
    try {
      await api('/api/buchungen', { method: 'POST', body: { terminId: Number(terminId), ressourcenId, zeitraum: `${start} bis ${end}` } })
      setSuccess(true); setMessage('Buchung erfolgreich.')
    } catch (error) { if (error.name !== 'AbortError') setMessage(errorMessage(error)) }
    finally { setBusy(false) }
  }
  async function createResource(event) {
    event.preventDefault()
    if (busy) return
    setBusy(true); setMessage(''); setSuccess(false)
    try {
      await api('/api/ressourcen', { method: 'POST', body: { name: resourceName.trim(), typ: 'Raum', kapazitaet: Number(capacity), verfuegbarkeit: true } })
      setResourceName(''); setSuccess(true); setMessage('Raum wurde angelegt.'); onRefresh()
    } catch (error) { if (error.name !== 'AbortError') setMessage(errorMessage(error)) }
    finally { setBusy(false) }
  }
  return (
    <section className="dashboard-card resources-card">
      <div className="resource-label">
        03
        <span>RESSOURCEN</span>
      </div>

      <h2 className="resource-title">
        Raum reservieren
      </h2>

      <p className="resource-description">
        Wähle einen vorhandenen Termin und reserviere eine passende
        Ressource.
      </p>

      <div className="form-group">
        <label htmlFor="terminId">
          Termin
        </label>

        <select id="terminId" value={terminId} onChange={(event) => selectTermin(event.target.value)} disabled={busy || !currentUser || termsLoading || Boolean(termsError)}>
          <option value="">Eigenen Termin auswählen</option>
          {termine.map((term) => <option key={term.terminId} value={term.terminId}>{term.titel} · {term.datum} {term.startzeit.slice(0,5)}</option>)}
        </select>
        {termsLoading && <p role="status">Termine werden geladen …</p>}
        {termsError && <p role="alert">{termsError}<button type="button" onClick={() => { setTermsLoading(true); setTermsError(''); setRefresh((v) => v + 1) }}>Termine erneut laden</button></p>}
        {!termsLoading && !termsError && currentUser && !termine.length && <p>Lege zuerst einen Termin in „Mein Kalender“ an.</p>}
      </div>
      <div className="form-group"><label htmlFor="booking-start">Von</label><input id="booking-start" type="datetime-local" value={start} onChange={(event) => setStart(event.target.value)} /></div>
      <div className="form-group"><label htmlFor="booking-end">Bis</label><input id="booking-end" type="datetime-local" value={end} onChange={(event) => setEnd(event.target.value)} /></div>
      {!currentUser && <p>Bitte anmelden, um Ressourcen zu buchen.</p>}
      {ladefehler && <p role="alert">Ressourcen konnten nicht geladen werden.<button type="button" onClick={onRefresh}>Erneut laden</button></p>}

      {!ladefehler && (ressourcen.length === 0 ? (
        <div className="resource-empty">
          <div>□</div>
          <p>Keine Ressourcen vorhanden.</p>
        </div>
      ) : (
        <div className="resource-list">
          {ressourcen.map((ressource) => (
            <article
              className="resource-item"
              key={ressource.ressourcenId}
            >
              <div className="resource-row">
                <div className="resource-symbol">
                  ▣
                </div>

                <div className="resource-main">
                  <strong>
                    {ressource.name}
                  </strong>

                  <span>
                    {ressource.typ} · {ressource.kapazitaet} Personen
                  </span>
                </div>

                <span
                  className={
                    ressource.verfuegbarkeit
                      ? 'availability available'
                      : 'availability unavailable'
                  }
                >
                  {ressource.verfuegbarkeit
                    ? 'Verfügbar'
                    : 'Nicht verfügbar'}
                </span>
              </div>

              <button
                className="primary-button resource-button"
                disabled={busy || !currentUser || !terminId || !ressource.verfuegbarkeit || termsLoading || Boolean(termsError)}
                type="button"
                onClick={() =>
                  bucheRessource(
                    ressource.ressourcenId
                  )
                }
              >
                Ressource buchen
              </button>
            </article>
          ))}
        </div>
      ))}

      {message && (
        <div
          className={`form-message ${
            messageIsSuccess
              ? 'success'
              : 'error'
          }`}
        >
          {message}
        </div>
      )}
      {currentUser && <details className="resource-create"><summary>Neue Ressource anlegen</summary>
        <form onSubmit={createResource}>
          <div className="form-group"><label htmlFor="resource-name">Name</label><input id="resource-name" value={resourceName} onChange={(e) => setResourceName(e.target.value)} maxLength={100} required /></div>
          <div className="form-group"><label htmlFor="resource-capacity">Kapazität</label><input id="resource-capacity" type="number" min="1" step="1" value={capacity} onChange={(e) => setCapacity(e.target.value)} required /></div>
          <button className="primary-button" disabled={busy}>Raum anlegen</button>
        </form>
      </details>}
    </section>
  )
}

export default Ressourcen