import './App.css'
import { useState } from 'react'
import TimeSlot from './components/TimeSlot'

function App() {
  const [title, setTitle] = useState('')
  const [start, setStart] = useState('')
  const [end, setEnd] = useState('')
  const [message, setMessage] = useState('')

  const createAppointmentRequest = async () => {
    if (!title || !start || !end) {
      setMessage('Bitte Titel, Von und Bis ausfüllen.')
      return
    }

    const startDate = new Date(start)
    const endDate = new Date(end)

    if (endDate <= startDate) {
      setMessage('Das Enddatum muss nach dem Startdatum liegen.')
      return
    }

    const duration = Math.round(
      (endDate.getTime() - startDate.getTime()) / 60000
    )

    const request = {
      titel: title,
      zeitraum: `${start} bis ${end}`,
      dauer: duration,
      status: 'OFFEN',
    }

    try {
      const response = await fetch(
        'http://192.168.178.32:8080/api/terminanfragen',
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(request),
        }
      )

      if (!response.ok) {
        throw new Error('Terminanfrage konnte nicht erstellt werden.')
      }

      const savedRequest = await response.json()

      setMessage(
        `Terminanfrage erstellt. ID: ${savedRequest.terminanfrageId}`
      )

      setTitle('')
      setStart('')
      setEnd('')
    } catch (error) {
      setMessage(
        'Fehler beim Erstellen. Läuft das Backend auf Port 8080?'
      )
    }
  }

  return (
    <main>
      <h1>SyncUp</h1>
      <p>Gemeinsam den passenden Termin finden.</p>

      <section>
        <h2>Terminanfrage erstellen</h2>

        <label>Titel</label>
        <input
          type="text"
          placeholder="z. B. Teammeeting"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />

        <br />
        <br />

        <label>Von</label>
        <input
          type="datetime-local"
          value={start}
          onChange={(e) => setStart(e.target.value)}
        />

        <br />
        <br />

        <label>Bis</label>
        <input
          type="datetime-local"
          value={end}
          onChange={(e) => setEnd(e.target.value)}
        />

        <br />
        <br />

 <button type="button" onClick={createAppointmentRequest}>
  Terminanfrage erstellen
</button>

        {message && <p>{message}</p>}

        <h2>Freie Zeitfenster</h2>

        <TimeSlot
          start="10:00"
          end="11:00"
          available={true}
        />
      </section>
    </main>
  )
}

export default App
