import './App.css'
import { useEffect, useState } from 'react'
import TimeSlot from './components/TimeSlot'
import Ressourcen from './components/Ressourcen'

function App() {
  
  const [title, setTitle] = useState('')
  const [start, setStart] = useState('')
  const [end, setEnd] = useState('')
  const [message, setMessage] = useState('')
  const [appointmentRequests, setAppointmentRequests] = useState([])
  const [freeTimeSlots, setFreeTimeSlots] = useState([])
  const [ressourcen, setRessourcen] = useState([])
  useEffect(() => {
  const loadAppointmentRequests = async () => {
    try {
      const response = await fetch(
        `${import.meta.env.VITE_API_URL}/api/terminanfragen`
      )

      if (!response.ok) {
        throw new Error('Terminanfragen konnten nicht geladen werden.')
      }

      const data = await response.json()
      setAppointmentRequests(data)
    } catch (error) {
      console.error('Fehler beim Laden der Terminanfragen:', error)
    }
  }

  loadAppointmentRequests()
}, [])

  useEffect(() => {
    const loadRessourcen = async () => {
      try {
        const response = await fetch(
          `${import.meta.env.VITE_API_URL}/api/ressourcen`
        )

        if (!response.ok) {
          throw new Error('Ressourcen konnten nicht geladen werden.')
        }

        const data = await response.json()
        setRessourcen(data)
      } catch (error) {
        console.error('Fehler beim Laden der Ressourcen:', error)
      }
    }

    loadRessourcen()
  }, [])

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
        `${import.meta.env.VITE_API_URL}/api/terminanfragen`,
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
setAppointmentRequests((prev) => [...prev, savedRequest])


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
const deleteAppointmentRequest = async (id) => {
  try {
    const response = await fetch(
      `${import.meta.env.VITE_API_URL}/api/terminanfragen/${id}`,
      {
        method: 'DELETE',
      }
    )

    if (!response.ok) {
      throw new Error('Terminanfrage konnte nicht gelöscht werden.')
    }

    setAppointmentRequests((prev) =>
      prev.filter((request) => request.terminanfrageId !== id)
    )
  } catch (error) {
    console.error('Fehler beim Löschen:', error)
  }
}
const loadFreeTimeSlots = async (id) => {
  try {
    const response = await fetch(
      `${import.meta.env.VITE_API_URL}/api/terminanfragen/${id}/freie-zeitfenster`
    )

    if (!response.ok) {
      throw new Error('Freie Zeitfenster konnten nicht geladen werden.')
    }

    const data = await response.json()
    setFreeTimeSlots(data)
  } catch (error) {
    console.error('Fehler beim Laden der freien Zeitfenster:', error)
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
<h2>Offene Terminanfragen</h2>

{appointmentRequests.length === 0 ? (
  <p>Keine Terminanfragen vorhanden.</p>
) : (
  appointmentRequests.map((request) => (
    <div key={request.terminanfrageId}>
      <h3>{request.titel}</h3>
      <p>Zeitraum: {request.zeitraum}</p>
      <p>Dauer: {request.dauer} Minuten</p>
      <p>Status: {request.status}</p>
      <button
  type="button"
  onClick={() => loadFreeTimeSlots(request.terminanfrageId)}
>
  Freie Zeitfenster anzeigen
</button>

<button
  type="button"
  onClick={() => deleteAppointmentRequest(request.terminanfrageId)}
>
  Löschen
</button>
    </div>

  ))
)}

   <h2>Freie Zeitfenster</h2>

{freeTimeSlots.length === 0 ? (
  <p>Keine freien Zeitfenster angezeigt.</p>
) : (
  freeTimeSlots.map((slot, index) => (
    <TimeSlot
  key={index}
  date={slot.start.slice(0, 10)}
  startTime={slot.start.slice(11, 16)}
  endTime={slot.ende.slice(11, 16)}
  available={true}
/>
  ))
)}
        <Ressourcen ressourcen={ressourcen} />
      </section>
    </main>
  )
}

export default App
