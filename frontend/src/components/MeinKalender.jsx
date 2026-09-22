import { useState } from 'react'

function MeinKalender() {
  const [currentDate, setCurrentDate] = useState(new Date())
  const [selectedDate, setSelectedDate] = useState(null)
  const [titel, setTitel] = useState('')
  const [von, setVon] = useState('')
  const [bis, setBis] = useState('')
  const [termine, setTermine] = useState([])
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

  const previousMonth = () => {
    setCurrentDate(new Date(year, month - 1, 1))
  }

  const nextMonth = () => {
    setCurrentDate(new Date(year, month + 1, 1))
  }

  const saveTermin = async () => {
    setHinweis('')

    try {
      const response = await fetch(
        `${import.meta.env.VITE_API_URL || 'http://localhost:8080'}/api/auth/me`,
        { credentials: 'include' }
      )

      if (!response.ok) {
        setHinweis(
          'Bitte melde dich an oder registriere dich, um Termine zu speichern.'
        )
        return
      }
    } catch {
      setHinweis(
        'Bitte melde dich an oder registriere dich, um Termine zu speichern.'
      )
      return
    }

    if (!selectedDate || !titel || !von || !bis) {
      setHinweis('Bitte Titel, Von und Bis ausfüllen.')
      return
    }

    setTermine([
      ...termine,
      {
        date: selectedDate,
        titel,
        von,
        bis,
      },
    ])

    setTitel('')
    setVon('')
    setBis('')
  }

  const getDateKey = (day) =>
    `${year}-${String(month + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`

  return (
    <section className="calendar-page">
      <div className="calendar-header">
        <button type="button" onClick={previousMonth}>‹</button>
        <h2>{monthName}</h2>
        <button type="button" onClick={nextMonth}>›</button>
      </div>

      <div className="month-calendar-grid">
        {['Mo', 'Di', 'Mi', 'Do', 'Fr', 'Sa', 'So'].map((day) => (
          <strong key={day}>{day}</strong>
        ))}

        {Array.from({ length: offset }).map((_, index) => (
          <div key={`empty-${index}`} />
        ))}

        {Array.from({ length: daysInMonth }).map((_, index) => {
          const day = index + 1
          const dateKey = getDateKey(day)

          return (
            <button
              key={day}
              type="button"
              className={`calendar-day ${
                selectedDate === dateKey ? 'selected' : ''
              }`}
              onClick={() => {
                setSelectedDate(dateKey)
                setHinweis('')
              }}
            >
              <span>{day}</span>

              {termine
                .filter((termin) => termin.date === dateKey)
                .map((termin, i) => (
                  <small key={i}>
                    {termin.von} {termin.titel}
                  </small>
                ))}
            </button>
          )
        })}
      </div>

      {selectedDate && (
        <div className="calendar-form">
          <h3>Termin am {selectedDate}</h3>

          <input
            placeholder="Titel"
            value={titel}
            onChange={(e) => setTitel(e.target.value)}
          />

          <input
            type="time"
            value={von}
            onChange={(e) => setVon(e.target.value)}
          />

          <input
            type="time"
            value={bis}
            onChange={(e) => setBis(e.target.value)}
          />

          {hinweis && (
            <div className="calendar-message">
              {hinweis}
            </div>
          )}

          <button type="button" onClick={saveTermin}>
            Termin speichern
          </button>
        </div>
      )}
    </section>
  )
}

export default MeinKalender
