import { useEffect, useState } from 'react'
import { useSession } from '../session-context'
import { api, errorMessage } from '../api'

function MeinKalender() {
  const { currentUser } = useSession()

  const [editing, setEditing] = useState(null)
  const [description, setDescription] = useState('')
  const [busy, setBusy] = useState(false)
  const [loadError, setLoadError] = useState('')
  const [loading, setLoading] = useState(Boolean(currentUser))
  const [refresh, setRefresh] = useState(0)
  const [currentDate, setCurrentDate] = useState(new Date())
  const [selectedDate, setSelectedDate] = useState(null)
  const [titel, setTitel] = useState('')
  const [von, setVon] = useState('')
  const [bis, setBis] = useState('')
  const [termine, setTermine] = useState([])
  const [buchungen, setBuchungen] = useState([])
  const [hinweis, setHinweis] = useState('')

  const year = currentDate.getFullYear()
  const month = currentDate.getMonth()

  const monthName = currentDate.toLocaleDateString('de-DE', {
    month: 'long',
    year: 'numeric',
  })

  const firstDay = new Date(year, month, 1).getDay()
  const offset = firstDay === 0 ? 6 : firstDay - 1
  const daysInMonth = new Date(year, month + 1, 0).getDate()

  useEffect(() => {
    if (!currentUser) {
      setTermine([])
      setBuchungen([])
      setLoading(false)
      return
    }

    let active = true

    setLoading(true)
    setLoadError('')

    Promise.all([
  api('/api/me/termine'),
  api('/api/buchungen'),
])
.then(([data, bookings]) => {
  if (active) {
    setTermine(data)
    setBuchungen(bookings)
  }
})
      .catch((error) => {
        if (active && error.name !== 'AbortError') {
          setLoadError(errorMessage(error))
        }
      })
      .finally(() => {
        if (active) {
          setLoading(false)
        }
      })

    return () => {
      active = false
    }
  }, [currentUser, refresh])

  const previousMonth = () => {
    setCurrentDate(new Date(year, month - 1, 1))
  }

  const nextMonth = () => {
    setCurrentDate(new Date(year, month + 1, 1))
  }

  const saveTermin = async () => {
    if (busy) return

    setHinweis('')

    if (!currentUser) {
      setHinweis('Bitte anmelden, um Termine zu speichern.')
      return
    }

    if (
      !selectedDate ||
      !titel.trim() ||
      !von ||
      !bis ||
      bis <= von
    ) {
      setHinweis(
        'Bitte Titel und gültige Zeiten angeben. Das Ende muss nach dem Beginn liegen.'
      )
      return
    }

    setBusy(true)

    try {
      const saved = await api(
        editing
          ? `/api/me/termine/${editing}`
          : '/api/me/termine',
        {
          method: editing ? 'PUT' : 'POST',
          body: {
            titel: titel.trim(),
            beschreibung: description,
            benutzerIds: [],
            datum: selectedDate,
            startzeit: von,
            endzeit: bis,
          },
        }
      )

      setTermine((previous) => [
        ...previous.filter(
          (t) => t.terminId !== saved.terminId
        ),
        saved,
      ])

      setRefresh((v) => v + 1)

      setTitel('')
      setVon('')
      setBis('')
      setDescription('')
      setEditing(null)

      setHinweis(
        editing
          ? 'Termin geändert.'
          : 'Termin gespeichert.'
      )
    } catch (error) {
      if (error.name !== 'AbortError') {
        setHinweis(errorMessage(error))
      }
    } finally {
      setBusy(false)
    }
  }

  const deleteTermin = async (id) => {
    if (busy) return

    setBusy(true)
    setHinweis('')

    try {
      await api(`/api/me/termine/${id}`, {
        method: 'DELETE',
      })

      setTermine((items) =>
        items.filter(
          (item) => item.terminId !== id
        )
      )

      setRefresh((v) => v + 1)
      setHinweis('Termin gelöscht.')
    } catch (error) {
      if (error.name !== 'AbortError') {
        setHinweis(errorMessage(error))
      }
    } finally {
      setBusy(false)
    }
  }

  const getDateKey = (day) =>
    `${year}-${String(month + 1).padStart(
      2,
      '0'
    )}-${String(day).padStart(2, '0')}`

  const getRaumFuerTermin = (terminId) => {
    const buchung = buchungen.find(
      (buchung) =>
        buchung.termin?.terminId === terminId
    )

    return buchung?.ressource?.name || null
  }

  return (
    <section className="calendar-page">

      {!currentUser && (
        <p>
          Bitte anmelden, um deine Termine
          zu sehen und zu speichern.
        </p>
      )}

      {loading && (
        <p role="status">
          Termine werden geladen …
        </p>
      )}

      {loadError && (
        <p role="alert">
          {loadError}

          <button
            type="button"
            onClick={() => {
              setLoading(true)
              setLoadError('')
              setRefresh(
                (v) => v + 1
              )
            }}
          >
            Erneut laden
          </button>
        </p>
      )}

      <div className="calendar-header">

        <button
          type="button"
          onClick={previousMonth}
          aria-label="Vorheriger Monat"
        >
          ‹
        </button>

        <h2>{monthName}</h2>

        <button
          type="button"
          onClick={nextMonth}
          aria-label="Nächster Monat"
        >
          ›
        </button>

      </div>

      <div className="month-calendar-grid">

        {[
          'Mo',
          'Di',
          'Mi',
          'Do',
          'Fr',
          'Sa',
          'So',
        ].map((day) => (
          <strong key={day}>
            {day}
          </strong>
        ))}

        {Array.from({
          length: offset,
        }).map((_, index) => (
          <div
            key={`empty-${index}`}
          />
        ))}

        {Array.from({
          length: daysInMonth,
        }).map((_, index) => {
          const day = index + 1
          const dateKey =
            getDateKey(day)

          return (
            <button
              key={day}
              type="button"
              className={`calendar-day ${
                selectedDate === dateKey
                  ? 'selected'
                  : ''
              }`}
              onClick={() => {
                setSelectedDate(dateKey)
                setHinweis('')
              }}
            >

              <span>{day}</span>

              {termine
                .filter(
                  (termin) =>
                    termin.datum ===
                    dateKey
                )
                .map((termin) => {
                  const raum =
                    getRaumFuerTermin(
                      termin.terminId
                    )

                  return (
                    <small
                      key={
                        termin.terminId
                      }
                    >
                      {termin.startzeit?.slice(
                        0,
                        5
                      )}{' '}
                      {termin.titel}

                      {raum && (
                        <>
                          {' '}
                          · {raum}
                        </>
                      )}
                    </small>
                  )
                })}

            </button>
          )
        })}

      </div>

      {selectedDate && (
        <div className="calendar-form">

          <h3>
            {editing
              ? 'Termin bearbeiten'
              : `Termin am ${selectedDate}`}
          </h3>

          <label htmlFor="calendar-date">
            Datum
          </label>

          <input
            id="calendar-date"
            type="date"
            value={selectedDate}
            onChange={(e) => {
              setSelectedDate(
                e.target.value
              )

              if (e.target.value) {
                setCurrentDate(
                  new Date(
                    `${e.target.value}T12:00:00`
                  )
                )
              }
            }}
          />

          <label htmlFor="calendar-title">
            Titel
          </label>

          <input
            id="calendar-title"
            maxLength={100}
            placeholder="Titel"
            value={titel}
            onChange={(e) =>
              setTitel(
                e.target.value
              )
            }
          />

          <label htmlFor="calendar-start">
            Von
          </label>

          <input
            id="calendar-start"
            type="time"
            value={von}
            onChange={(e) =>
              setVon(
                e.target.value
              )
            }
          />

          <label htmlFor="calendar-end">
            Bis
          </label>

          <input
            id="calendar-end"
            type="time"
            value={bis}
            onChange={(e) =>
              setBis(
                e.target.value
              )
            }
          />

          <label htmlFor="calendar-description">
            Beschreibung
          </label>

          <input
            id="calendar-description"
            maxLength={255}
            value={description}
            onChange={(e) =>
              setDescription(
                e.target.value
              )
            }
          />

        

          {editing && (
            <button
              type="button"
              disabled={busy}
              onClick={() => {
                setEditing(null)
                setTitel('')
                setVon('')
                setBis('')
                setParticipants([])
                setDescription('')
              }}
            >
              Bearbeiten abbrechen
            </button>
          )}

          {hinweis && (
            <div className="calendar-message">
              {hinweis}
            </div>
          )}

          <button
            type="button"
            disabled={
              busy ||
              !currentUser
            }
            onClick={saveTermin}
          >
            {busy
              ? 'Bitte warten …'
              : editing
                ? 'Änderungen speichern'
                : 'Termin speichern'}
          </button>

          {termine
            .filter(
              (term) =>
                term.datum ===
                selectedDate
            )
            .map((term) => {
              const raum =
                getRaumFuerTermin(
                  term.terminId
                )

              return (
                <article
                  key={term.terminId}
                  className="request-item"
                >

                  <strong>
                    {term.titel}
                  </strong>

                  <p>
                    {term.startzeit.slice(
                      0,
                      5
                    )}
                    {' – '}
                    {term.endzeit.slice(
                      0,
                      5
                    )}
                  </p>

                  {raum && (
                    <p>
                      📍 {raum}
                    </p>
                  )}

                  {term.beschreibung && (
                    <p>
                      {term.beschreibung}
                    </p>
                  )}

                  {term.kalender
                    ?.besitzer
                    ?.benutzerId ===
                    currentUser
                      ?.benutzerId && (
                    <button
                      type="button"
                      disabled={busy}
                      onClick={() => {
                        setEditing(
                          term.terminId
                        )

                        setTitel(
                          term.titel
                        )

                        setVon(
                          term.startzeit
                        )

                        setBis(
                          term.endzeit
                        )

                        setDescription(
                          term.beschreibung ||
                            ''
                        )

                        setParticipants(
                          (
                            term.teilnehmer ||
                            []
                          ).map(
                            (u) =>
                              u.benutzerId
                          )
                        )

                        setHinweis('')
                      }}
                    >
                      Bearbeiten
                    </button>
                  )}

                  <button
                    hidden={
                      term.kalender
                        ?.besitzer
                        ?.benutzerId !==
                      currentUser
                        ?.benutzerId
                    }
                    type="button"
                    className="delete-button"
                    disabled={busy}
                    onClick={() =>
                      deleteTermin(
                        term.terminId
                      )
                    }
                  >
                    Termin löschen
                  </button>

                </article>
              )
            })}

        </div>
      )}

    </section>
  )
}

export default MeinKalender