import './App.css'
import { useState } from 'react'
import TimeSlot from './components/TimeSlot'

function App() {
  const [title, setTitle] = useState('')
  const [start, setStart] = useState('')
  const [end, setEnd] = useState('')

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

        <br /><br />

        <label>Von</label>
        <input
          type="datetime-local"
          value={start}
          onChange={(e) => setStart(e.target.value)}
        />

        <br /><br />

        <label>Bis</label>
        <input
          type="datetime-local"
          value={end}
          onChange={(e) => setEnd(e.target.value)}
        
        />
<button type="button">
  Terminanfrage erstellen
</button>
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

