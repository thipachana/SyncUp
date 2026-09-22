import { useState } from 'react'

function Ressourcen({ ressourcen, ladefehler }) {
  const [terminId, setTerminId] = useState('1')

  const [zeitraum, setZeitraum] = useState(
    '2026-09-22T10:00 bis 2026-09-22T11:00'
  )

  const [message, setMessage] = useState('')

  const bucheRessource = async (ressourcenId) => {
    try {
      const response = await fetch(
        `${import.meta.env.VITE_API_URL}/api/buchungen`,
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            terminId: Number(terminId),
            ressourcenId,
            zeitraum,
            status: 'BESTAETIGT',
          }),
        }
      )

      if (response.status === 409) {
        setMessage(
          'Ressource ist in diesem Zeitraum bereits gebucht.'
        )
        return
      }

      if (!response.ok) {
        throw new Error('Buchung fehlgeschlagen.')
      }

      const data = await response.json()

      setMessage(
        `Buchung erfolgreich. ID: ${data.buchungId}`
      )
    } catch (error) {
      setMessage(
        'Ressource konnte nicht gebucht werden.'
      )
    }
  }

  const messageIsSuccess =
    message.startsWith('Buchung erfolgreich')

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
          Termin-ID
        </label>

        <input
          id="terminId"
          type="number"
          value={terminId}
          onChange={(e) =>
            setTerminId(e.target.value)
          }
        />
      </div>

      <div className="form-group">
        <label htmlFor="zeitraum">
          Zeitraum
        </label>

        <input
          id="zeitraum"
          type="text"
          value={zeitraum}
          onChange={(e) =>
            setZeitraum(e.target.value)
          }
        />
      </div>

      {ressourcen.length === 0 ? (
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
      )}

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
    </section>
  )
}

export default Ressourcen