import { useState } from 'react'

function Ressourcen({ ressourcen }) {
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

      if (!response.ok) {
        throw new Error('Buchung fehlgeschlagen.')
      }

      const data = await response.json()
      setMessage(`Buchung erfolgreich. ID: ${data.buchungId}`)
    } catch (error) {
      setMessage('Ressource konnte nicht gebucht werden.')
    }
  }

  return (
    <section>
      <h2>Ressourcen</h2>

      <label>Termin-ID</label>
      <input
        type="number"
        value={terminId}
        onChange={(e) => setTerminId(e.target.value)}
      />

      <label>Zeitraum</label>
      <input
        type="text"
        value={zeitraum}
        onChange={(e) => setZeitraum(e.target.value)}
      />

      {ressourcen.length === 0 ? (
        <p>Keine Ressourcen vorhanden.</p>
      ) : (
        ressourcen.map((ressource) => (
          <div key={ressource.ressourcenId}>
            <h3>{ressource.name}</h3>
            <p>Typ: {ressource.typ}</p>
            <p>Kapazität: {ressource.kapazitaet}</p>
            <p>
              Status:{' '}
              {ressource.verfuegbarkeit
                ? 'Verfügbar'
                : 'Nicht verfügbar'}
            </p>

            <button
              type="button"
              onClick={() => bucheRessource(ressource.ressourcenId)}
            >
              Ressource buchen
            </button>
          </div>
        ))
      )}

      {message && <p>{message}</p>}
    </section>
  )
}

export default Ressourcen
