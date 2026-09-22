import './App.css'
import { useEffect, useState } from 'react'
import TimeSlot from './components/TimeSlot'
import Ressourcen from './components/Ressourcen'
import AuthControl from './components/AuthControl'

function App() {
  const [title, setTitle] = useState('')
  const [start, setStart] = useState('')
  const [end, setEnd] = useState('')
  const [message, setMessage] = useState('')

  const [appointmentRequests, setAppointmentRequests] = useState([])
  const [freeTimeSlots, setFreeTimeSlots] = useState([])
  const [ressourcen, setRessourcen] = useState([])
  const [ressourcenFehler, setRessourcenFehler] = useState(false)

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
        setRessourcenFehler(false)
      } catch (error) {
        console.error('Fehler beim Laden der Ressourcen:', error)
        setRessourcenFehler(true)
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
      setFreeTimeSlots([])
    } catch (error) {
      console.error('Fehler beim Löschen:', error)
    }
  }

  const loadFreeTimeSlots = async (id) => {
    setFreeTimeSlots([])

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
      console.error(
        'Fehler beim Laden der freien Zeitfenster:',
        error
      )
    }
  }

  const scrollTo = (id) => {
    document.getElementById(id)?.scrollIntoView({
      behavior: 'smooth',
      block: 'start',
    })
  }

  const messageIsSuccess = message.startsWith(
    'Terminanfrage erstellt'
  )

  return (
    <main className="app-shell">
      <header className="topbar">
        <div className="brand">
          <div className="brand-logo">✓</div>

          <div>
            <h1>SyncUp</h1>
            <p>Plan. Share. Achieve.</p>
          </div>
        </div>

        <nav className="nav">
          <button
            className="nav-item active"
            type="button"
            onClick={() => window.scrollTo({
              top: 0,
              behavior: 'smooth',
            })}
          >
            ⌂ Home
          </button>

          <button
            className="nav-item"
            type="button"
            onClick={() => scrollTo('terminplanung')}
          >
            ▣ Termine
          </button>

          <button
            className="nav-item"
            type="button"
            onClick={() => scrollTo('ressourcen')}
          >
            ◆ Ressourcen
          </button>
        </nav>

        <AuthControl />
      </header>

      <section className="hero">
        <div className="hero-content">
          <h2>
            Dein Team. Deine Termine.
            <br />
            Alles an einem Ort.
          </h2>

          <p>Einfach. Übersichtlich. Effizient.</p>
        </div>

        <div className="hero-visual">
          <div className="calendar-illustration">
            <div className="calendar-rings">
              <span></span>
              <span></span>
            </div>

            <div className="calendar-top"></div>

            <div className="calendar-grid">
              {Array.from({ length: 15 }).map((_, index) => (
                <span
                  key={index}
                  className={index === 12 ? 'selected-day' : ''}
                ></span>
              ))}
            </div>
          </div>

          <div className="mini-event-card">
            <div className="event-line long"></div>
            <div className="event-line short"></div>

            <div className="mini-clock">
              ◷
            </div>
          </div>

          <div className="hero-note">
            Better planning.
            <br />
            Better teamwork.
          </div>
        </div>
      </section>

      <div className="dashboard-grid">
        <section
          id="terminplanung"
          className="dashboard-card request-card"
        >
          <div className="card-title">
            <div className="title-icon">▣</div>
            <h2>Terminanfrage</h2>
          </div>

          <div className="form-group">
            <label htmlFor="title">Titel</label>

            <input
              id="title"
              type="text"
              placeholder="z. B. Projekt-Meeting"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
            />
          </div>

          <div className="date-grid">
            <div className="form-group">
              <label htmlFor="start">Von</label>

              <input
                id="start"
                type="datetime-local"
                value={start}
                onChange={(e) => setStart(e.target.value)}
              />
            </div>

            <div className="form-group">
              <label htmlFor="end">Bis</label>

              <input
                id="end"
                type="datetime-local"
                value={end}
                onChange={(e) => setEnd(e.target.value)}
              />
            </div>
          </div>

          <button
            className="primary-button"
            type="button"
            onClick={createAppointmentRequest}
          >
            + Anfrage erstellen
          </button>

          {message && (
            <div
              className={`form-message ${
                messageIsSuccess ? 'success' : 'error'
              }`}
            >
              {message}
            </div>
          )}

          <div className="request-section">
            <div className="small-heading">
              <h3>Offene Terminanfragen</h3>
              <span>{appointmentRequests.length}</span>
            </div>

            {appointmentRequests.length === 0 ? (
              <div className="small-empty-state">
                Noch keine Terminanfragen vorhanden.
              </div>
            ) : (
              <div className="request-list">
                {appointmentRequests.map((request) => (
                  <article
                    className="request-item"
                    key={request.terminanfrageId}
                  >
                    <div className="request-top">
                      <h4>{request.titel}</h4>

                      <span className="status-badge">
                        {request.status}
                      </span>
                    </div>

                    <p>{request.zeitraum}</p>

                    <small>
                      {request.dauer} Minuten
                    </small>

                    <div className="request-actions">
                      <button
                        className="secondary-button"
                        type="button"
                        onClick={() =>
                          loadFreeTimeSlots(
                            request.terminanfrageId
                          )
                        }
                      >
                        Freie Zeiten
                      </button>

                      <button
                        className="delete-button"
                        type="button"
                        onClick={() =>
                          deleteAppointmentRequest(
                            request.terminanfrageId
                          )
                        }
                      >
                        Löschen
                      </button>
                    </div>
                  </article>
                ))}
              </div>
            )}
          </div>
        </section>

        <section className="dashboard-card slots-card">
          <div className="card-title">
            <div className="title-icon">◉</div>
            <h2>Freie Zeitfenster</h2>
          </div>

          <p className="card-helper">
            Wähle bei einer Terminanfrage „Freie Zeiten“.
          </p>

          {freeTimeSlots.length === 0 ? (
            <div className="large-empty-state">
              <div className="empty-clock">◷</div>

              <strong>
                Noch keine freien Zeitfenster
              </strong>

              <span>
                Wähle zuerst eine Terminanfrage aus.
              </span>
            </div>
          ) : (
            <div className="time-slot-list">
              {freeTimeSlots.map((slot, index) => (
                <TimeSlot
                  key={index}
                  date={slot.start.slice(0, 10)}
                  startTime={slot.start.slice(11, 16)}
                  endTime={slot.ende.slice(11, 16)}
                  available={true}
                />
              ))}
            </div>
          )}
        </section>

        <div id="ressourcen">
          <Ressourcen
          ressourcen={ressourcen}
          ladefehler={ressourcenFehler}
        />
        </div>
      </div>

      <section className="benefits">
        <article>
          <div className="benefit-icon">♧</div>

          <div>
            <strong>Effiziente Planung</strong>
            <p>Spare Zeit und koordiniere dein Team.</p>
          </div>
        </article>

        <article>
          <div className="benefit-icon">◇</div>

          <div>
            <strong>Optimale Auslastung</strong>
            <p>Nutze deine Ressourcen bestmöglich.</p>
          </div>
        </article>

        <article>
          <div className="benefit-icon">♢</div>

          <div>
            <strong>Weniger Konflikte</strong>
            <p>Behalte Termine und Buchungen im Blick.</p>
          </div>
        </article>

        <div className="footer-note">
          Work together.
          <br />
          Stay in sync.
        </div>
      </section>

      <footer className="footer">
        <strong>SyncUp</strong>
        <span>Softwareprojekt · THM</span>
      </footer>
    </main>
  )
}

export default App