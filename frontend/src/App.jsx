import './App.css'
import { useEffect, useRef, useState } from 'react'
import { useSession } from './session-context'
import { apiFetch, errorMessage } from './api'
import TimeSlot from './components/TimeSlot'
import Ressourcen from './components/Ressourcen'
import Benachrichtigungen from './components/Benachrichtigungen'
import AuthControl from './components/AuthControl'
import MeinKalender from './components/MeinKalender'

function App() {
  const { currentUser } = useSession()
  const ownerId = currentUser?.benutzerId
  const [busy, setBusy] = useState(false)
  const [loadingRequests, setLoadingRequests] = useState(false)
  const [requestError, setRequestError] = useState('')
  const [requestVersion, setRequestVersion] = useState(0)
  const [slotState, setSlotState] = useState({ loading: false, error: '', title: '' })
  const slotSequence = useRef(0)
  useEffect(() => () => { slotSequence.current += 1 }, [])
  const [title, setTitle] = useState('')
  const [start, setStart] = useState('')
  const [end, setEnd] = useState('')
  const [duration, setDuration] = useState('60')
  const [message, setMessage] = useState('')

  const [appointmentRequests, setAppointmentRequests] = useState([])
  const [freeTimeSlots, setFreeTimeSlots] = useState([])
  const [ressourcen, setRessourcen] = useState([])
  const [ressourcenFehler, setRessourcenFehler] = useState(false)
  const [activePage, setActivePage] = useState('home')
  const [selectedRequestId, setSelectedRequestId] = useState(null)
const [preselectedTerminId, setPreselectedTerminId] = useState(null)

  const [benutzer, setBenutzer] = useState([])
  const [selectedBenutzer, setSelectedBenutzer] = useState(ownerId ? [ownerId] : [])
  const [benutzerMessage, setBenutzerMessage] = useState('')

  const loadBenutzer = async () => {
    setBenutzerMessage('')
    if (!ownerId) { setBenutzerMessage('Bitte zuerst anmelden.'); return }

    try {
      const response = await apiFetch(
        `/api/benutzer`,
        {
          credentials: 'include',
        }
      )

      if (response.status === 401) {
        setBenutzer([])
        setBenutzerMessage('Bitte zuerst anmelden.')
        return
      }

      if (!response.ok) {
        throw new Error('Teilnehmer konnten nicht geladen werden.')
      }

      const data = await response.json()

      setBenutzer(data)

      if (data.length === 0) {
        setBenutzerMessage('Keine Benutzer vorhanden.')
      }
    } catch (error) {
      if (error.name === 'AbortError') return
      setBenutzer([])
      setBenutzerMessage('Teilnehmer konnten nicht geladen werden.')
    }
  }

  useEffect(() => {
    if (!ownerId) return
    let active = true
    const loadAppointmentRequests = async () => {
      setLoadingRequests(true); setRequestError('')
      try {
        const response = await apiFetch(
          `/api/terminanfragen`
        )

        if (!response.ok) {
          throw new Error('Terminanfragen konnten nicht geladen werden.')
        }

        const data = await response.json()
        if (active) setAppointmentRequests(data)
      } catch (error) {
        if (active && error.name !== 'AbortError') setRequestError(errorMessage(error))
      } finally { if (active) setLoadingRequests(false)
      }
    }

    loadAppointmentRequests()
    return () => { active = false }
  }, [ownerId, requestVersion])

  useEffect(() => {
    if (!ownerId) return
    let active = true
    const loadRessourcen = async () => {
      try {
        const response = await apiFetch(
          `/api/ressourcen`
        )

        if (!response.ok) {
          throw new Error('Ressourcen konnten nicht geladen werden.')
        }

        const data = await response.json()
        if (!active) return
        setRessourcen(data)
        setRessourcenFehler(false)
      } catch (error) {
        if (!active || error.name === 'AbortError') return
        setRessourcenFehler(true)
      }
    }

    loadRessourcen()
    return () => { active = false }
  }, [ownerId, requestVersion])

  const createAppointmentRequest = async () => {
    if (busy) return
    if (!ownerId) { setMessage('Bitte zuerst anmelden.'); return }
    if (!title || !start || !end || !duration) {
      setMessage('Bitte Titel, Von, Bis und Dauer ausfüllen.')
      return
    }

    const startDate = new Date(`${start}Z`)
    const endDate = new Date(`${end}Z`)
    const durationNumber = Number(duration)

    if (!Number.isFinite(startDate.getTime()) || !Number.isFinite(endDate.getTime()) || endDate <= startDate) {
      setMessage('Das Enddatum muss nach dem Startdatum liegen.')
      return
    }

    if (!Number.isInteger(durationNumber) || durationNumber <= 0) {
      setMessage('Die gewünschte Dauer muss größer als 0 Minuten sein.')
      return
    }

    const searchWindowMinutes =
      (endDate.getTime() - startDate.getTime()) / 60000

    if (durationNumber > searchWindowMinutes) {
      setMessage('Die gewünschte Dauer darf nicht länger als der Suchzeitraum sein.')
      return
    }

    if (selectedBenutzer.length === 0) {
      setMessage('Bitte mindestens einen Teilnehmer auswählen.')
      return
    }

    const request = {
      titel: title.trim(),
      zeitraum: `${start} bis ${end}`,
      dauer: durationNumber,
      status: 'OFFEN',
      benutzerIds: selectedBenutzer,
    }

    setBusy(true)
    try {
      const response = await apiFetch(
        `/api/terminanfragen`,
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(request),
        }
      )

      if (!response.ok) {
        const error = await response.json()
        throw new Error(error.message || 'Terminanfrage konnte nicht erstellt werden.')
      }

      const savedRequest = await response.json()

setAppointmentRequests((prev) => [...prev, savedRequest])
await loadFreeTimeSlots(
  savedRequest.terminanfrageId,
  savedRequest
)
setMessage(
  `Terminanfrage erstellt. ID: ${savedRequest.terminanfrageId}`
)

// Freie Zeitfenster direkt automatisch laden
await loadFreeTimeSlots(savedRequest.terminanfrageId)

      setTitle('')
      setStart('')
      setEnd('')
      setDuration('60')
      setSelectedBenutzer([ownerId])
    } catch (error) {
      if (error.name !== 'AbortError') setMessage(errorMessage(error))
    } finally { setBusy(false) }
  }

  const deleteAppointmentRequest = async (id) => {
    if (busy) return
    setBusy(true); slotSequence.current += 1; setFreeTimeSlots([]); setSlotState({ loading: false, error: '', title: '' })
    try {
      const response = await apiFetch(
        `/api/terminanfragen/${id}`,
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
      if (error.name !== 'AbortError') setMessage(errorMessage(error))
    } finally { setBusy(false) }
  }
const loadFreeTimeSlots = async (id, requestOverride = null) => {
  const sequence = ++slotSequence.current

  setSelectedRequestId(id)
  setFreeTimeSlots([])

  const request =
    requestOverride ||
    appointmentRequests.find(
      (item) => item.terminanfrageId === id
    )

  const requestTitle = request?.titel || ''

  setSlotState({
    loading: true,
    error: '',
    title: requestTitle,
  })

  setTimeout(() => {
    document
      .querySelector('.slots-card')
      ?.scrollIntoView({
        behavior: 'smooth',
        block: 'start',
      })
  }, 50)

  try {
    const response = await apiFetch(
      `/api/terminanfragen/${id}/freie-zeitfenster`
    )

    const data = await response.json()

    if (!response.ok) {
      throw new Error(
        data.message ||
          'Zeitfenster konnten nicht geladen werden.'
      )
    }

    if (sequence !== slotSequence.current) return

    setFreeTimeSlots(data)

    setSlotState({
      loading: false,
      error: '',
      title: requestTitle,
    })
  } catch (error) {
    if (
      sequence === slotSequence.current &&
      error.name !== 'AbortError'
    ) {
      setSlotState({
        loading: false,
        error: errorMessage(error),
        title: requestTitle,
      })
    }
  }
}
const addMinutes = (time, minutes) => {
  const [hours, mins] = time.split(':').map(Number)

  const total =
    hours * 60 +
    mins +
    Number(minutes)

  const newHours =
    Math.floor(total / 60) % 24

  const newMinutes =
    total % 60

  return `${String(newHours).padStart(2, '0')}:${String(
    newMinutes
  ).padStart(2, '0')}`
}

const confirmTimeSlot = async (slot) => {
  if (busy || !selectedRequestId) return

  const request = appointmentRequests.find(
    (item) =>
      item.terminanfrageId === selectedRequestId
  )

  if (!request) {
    setSlotState((prev) => ({
      ...prev,
      error: 'Die Terminanfrage wurde nicht gefunden.',
    }))
    return
  }

  const startTime =
    slot.start.slice(11, 16)

  const endTime =
    addMinutes(
      startTime,
      request.dauer
    )

  const confirmed = window.confirm(
    `${slot.start.slice(0, 10)} von ` +
      `${startTime} bis ${endTime} ` +
      `als Termin festlegen?`
  )

  if (!confirmed) return

  setBusy(true)

  try {
    const response = await apiFetch(
      '/api/me/termine',
      {
        method: 'POST',
        headers: {
          'Content-Type':
            'application/json',
        },
        body: JSON.stringify({
          titel: request.titel,

          beschreibung:
            'Aus gemeinsamer Terminanfrage erstellt',

          datum:
            slot.start.slice(0, 10),

          startzeit:
            startTime,

          endzeit:
            endTime,

          benutzerIds:
            (request.benutzer || []).map(
              (user) =>
                user.benutzerId
            ),
        }),
      }
    )

    const savedTermin =
      await response.json()

    if (!response.ok) {
      throw new Error(
        savedTermin.message ||
          'Termin konnte nicht gespeichert werden.'
      )
    }

    setPreselectedTerminId(
      savedTermin.terminId
    )

    setFreeTimeSlots([])

    setSlotState({
      loading: false,
      error: '',
      title:
        `${request.titel} wurde festgelegt.`,
    })

    setTimeout(() => {
      document
        .getElementById('ressourcen')
        ?.scrollIntoView({
          behavior: 'smooth',
          block: 'start',
        })
    }, 150)
  } catch (error) {
    if (error.name !== 'AbortError') {
      setSlotState((prev) => ({
        ...prev,
        error: errorMessage(error),
      }))
    }
  } finally {
    setBusy(false)
  }
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
            className={`nav-item ${activePage === 'home' ? 'active' : ''}`}
            type="button"
            onClick={() => setActivePage('home')}
          >
            ⌂ Home
          </button>

          <button
            className={`nav-item ${activePage === 'calendar' ? 'active' : ''}`}
            type="button"
            onClick={() => setActivePage('calendar')}
          >
            ▣ Mein Kalender
          </button>
        </nav>

        <div className="account-actions"><Benachrichtigungen /><AuthControl /></div>
      </header>

      {activePage === 'home' ? (
        <>
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
              id="title" maxLength={100}
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

          <div className="form-group">
            <label htmlFor="duration">Gewünschte Dauer in Minuten</label>
            <input
              id="duration"
              type="number"
              min="1"
              value={duration}
              onChange={(e) => setDuration(e.target.value)}
            />
          </div>

          <div className="form-group">
            <label>Teilnehmer</label>

            <button
              className="participant-load-button"
              type="button"
              onClick={loadBenutzer}
            >
              Teilnehmer laden
            </button>

            {benutzerMessage && (
              <small className="participant-message">{benutzerMessage}</small>
            )}

            {benutzer.map((person) => (
              <label className="participant-option" key={person.benutzerId}>
                <input
                  type="checkbox"
                  disabled={person.benutzerId === ownerId}
                  checked={selectedBenutzer.includes(person.benutzerId)}
                  onChange={() => {
                    setSelectedBenutzer((prev) =>
                      prev.includes(person.benutzerId)
                        ? prev.filter((id) => id !== person.benutzerId)
                        : [...prev, person.benutzerId]
                    )
                  }}
                />
                {person.name} ({person.email})
              </label>
            ))}
          </div>

          <button
            className="primary-button"
            type="button"
            disabled={busy || !ownerId}
            onClick={createAppointmentRequest}
          >
            {busy ? 'Bitte warten …' : '+ Anfrage erstellen'}
          </button>

          {!ownerId && <p>Bitte anmelden, um Terminanfragen zu erstellen.</p>}
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

            {loadingRequests && <p role="status">Anfragen werden geladen …</p>}
            {requestError && <p role="alert">{requestError}<button type="button" onClick={() => setRequestVersion((v) => v + 1)}>Erneut laden</button></p>}
            {!loadingRequests && !requestError && (appointmentRequests.length === 0 ? (
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
                        className="secondary-button" disabled={busy}
                        type="button"
                        onClick={() =>
  loadFreeTimeSlots(
    request.terminanfrageId,
    request
  )
}
                      >
                        Freie Zeiten
                      </button>

                      <button
                        className="delete-button" disabled={busy}
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
            ))}
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

          {slotState.title && <p>Für: {slotState.title}</p>}
          {slotState.loading && <p role="status">Zeitfenster werden berechnet …</p>}
          {slotState.error && <p role="alert">{slotState.error}</p>}
          {!slotState.loading && !slotState.error && (freeTimeSlots.length === 0 ? (
            <div className="large-empty-state">
              <div className="empty-clock">◷</div>

              <strong>
                Noch keine freien Zeitfenster
              </strong>

              <span>
                {slotState.title ? 'Für diese Dauer gibt es kein gemeinsames freies Zeitfenster.' : 'Wähle zuerst eine Terminanfrage aus.'}
              </span>
            </div>
          ) : (
            <div className="time-slot-list">
              {freeTimeSlots.map((slot, index) => (
  <button
    key={index}
    type="button"
    className="time-slot-button"
    disabled={busy}
    onClick={() =>
      confirmTimeSlot(slot)
    }
  >
    <TimeSlot
      endDate={slot.ende.slice(0, 10)}
      date={slot.start.slice(0, 10)}
      startTime={slot.start.slice(11, 16)}
      endTime={slot.ende.slice(11, 16)}
      available={true}
    />
  </button>
))}
            </div>
          ))}
        </section>

        <div id="ressourcen">
          <Ressourcen
  key={`${ownerId || 'guest'}-${preselectedTerminId || 'none'}`}
  ressourcen={ressourcen}
  ladefehler={ressourcenFehler}
  preselectedTerminId={preselectedTerminId}
  onRefresh={() =>
    setRequestVersion((v) => v + 1)
  }
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

        </>
      ) : (
        <MeinKalender key={ownerId || 'guest'} />
      )}

      <footer className="footer">
        <strong>SyncUp</strong>
        <span>Softwareprojekt · THM</span>
      </footer>
    </main>
  )
}

export default App