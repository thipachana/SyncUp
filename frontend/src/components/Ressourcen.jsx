import { useEffect, useState } from 'react'
import { useSession } from '../session-context'
import { api, errorMessage } from '../api'

function Ressourcen({
  ressourcen,
  ladefehler,
  onRefresh,
  preselectedTerminId,
}) {
  const { currentUser } = useSession()

  const [termine, setTermine] = useState([])
  const [buchungen, setBuchungen] = useState([])
  const [terminId, setTerminId] = useState('')
  const [start, setStart] = useState('')
  const [end, setEnd] = useState('')

  const [message, setMessage] = useState('')
  const [messageIsSuccess, setSuccess] = useState(false)
  const [busy, setBusy] = useState(false)

  const [termsLoading, setTermsLoading] = useState(
    Boolean(currentUser)
  )

  const [termsError, setTermsError] = useState('')
  const [refresh, setRefresh] = useState(0)

  useEffect(() => {
    if (!currentUser) {
      setTermine([])
      setBuchungen([])
      setTerminId('')
      setStart('')
      setEnd('')
      return
    }

    let active = true

    setTermsLoading(true)
    setTermsError('')

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
        if (
          active &&
          error.name !== 'AbortError'
        ) {
          setTermsError(
            errorMessage(error)
          )
        }
      })
      .finally(() => {
        if (active) {
          setTermsLoading(false)
        }
      })

    return () => {
      active = false
    }
  }, [currentUser, refresh])

  useEffect(() => {
    if (
      !preselectedTerminId ||
      termine.length === 0
    ) {
      return
    }

    const term = termine.find(
      (item) =>
        item.terminId ===
        Number(preselectedTerminId)
    )

    if (!term) return

    setTerminId(
      String(term.terminId)
    )

    setStart(
      `${term.datum}T${term.startzeit.slice(
        0,
        5
      )}`
    )

    setEnd(
      `${term.datum}T${term.endzeit.slice(
        0,
        5
      )}`
    )

    setMessage('')
  }, [preselectedTerminId, termine])

  function selectTermin(id) {
    setTerminId(id)
    setMessage('')

    const term = termine.find(
      (item) =>
        item.terminId === Number(id)
    )

    setStart(
      term
        ? `${term.datum}T${term.startzeit.slice(
            0,
            5
          )}`
        : ''
    )

    setEnd(
      term
        ? `${term.datum}T${term.endzeit.slice(
            0,
            5
          )}`
        : ''
    )
  }
const terminHatBereitsRaum =
  Boolean(terminId) &&
  buchungen.some(
    (buchung) =>
      buchung.termin?.terminId === Number(terminId)
  )
  const ressourceIstBelegt = (ressourcenId) => {
  if (!start || !end) return false

  return buchungen.some((buchung) => {
    if (
      buchung.ressource?.ressourcenId !== ressourcenId ||
      !buchung.termin
    ) {
      return false
    }

    const belegterStart =
      `${buchung.termin.datum}T${buchung.termin.startzeit.slice(0, 5)}`

    const belegtesEnde =
      `${buchung.termin.datum}T${buchung.termin.endzeit.slice(0, 5)}`

    return start < belegtesEnde && belegterStart < end
  })
}
  const bucheRessource = async (
    ressourcenId
  ) => {
    if (busy) return

    setMessage('')
    setSuccess(false)

    if (
      !currentUser ||
      !terminId ||
      !start ||
      !end ||
      end <= start
    ) {
      setMessage(
        'Bitte anmelden und einen gültigen Termin auswählen.'
      )
      return
    }

    setBusy(true)

    try {
      const neueBuchung = await api('/api/buchungen', {
        method: 'POST',
        body: {
          terminId:
            Number(terminId),

          ressourcenId,

          zeitraum:
            `${start} bis ${end}`,
        },
      })

      setSuccess(true)
      setMessage(
        'Raum erfolgreich reserviert.'
      )
    } catch (error) {
      if (
        error.name !== 'AbortError'
      ) {
        setMessage(
          errorMessage(error)
        )
      }
    } finally {
      setBusy(false)
    }
  }

  return (
    <section className="dashboard-card resources-card">

      <div className="card-title">
  <div className="title-icon">◈</div>
  <h2 className="resource-title">
    Raum reservieren
  </h2>
</div>

      <p className="resource-description">
        Wähle einen vorhandenen Termin
        und reserviere einen passenden
        Raum.
      </p>

      <div className="form-group">

        <label htmlFor="terminId">
          Termin
        </label>

        <select
          id="terminId"
          value={terminId}
          onChange={(event) =>
            selectTermin(
              event.target.value
            )
          }
          disabled={
            busy ||
            !currentUser ||
            termsLoading ||
            Boolean(termsError)
          }
        >

          <option value="">
            Eigenen Termin auswählen
          </option>

          {termine.map((term) => (
            <option
              key={term.terminId}
              value={term.terminId}
            >
              {term.titel}
              {' · '}
              {term.datum}
              {' '}
              {term.startzeit.slice(
                0,
                5
              )}
              –
              {term.endzeit.slice(
                0,
                5
              )}
            </option>
          ))}

        </select>

        {termsLoading && (
          <p role="status">
            Termine werden geladen …
          </p>
        )}

        {termsError && (
          <p role="alert">
            {termsError}

            <button
              type="button"
              onClick={() => {
                setTermsLoading(true)
                setTermsError('')
                setRefresh(
                  (value) =>
                    value + 1
                )
              }}
            >
              Termine erneut laden
            </button>
          </p>
        )}

        {!termsLoading &&
          !termsError &&
          currentUser &&
          !termine.length && (
            <p>
              Lege zuerst einen Termin
              über eine Terminanfrage
              fest.
            </p>
          )}

      </div>

      <div className="form-group">
        <label htmlFor="booking-start">
          Von
        </label>

        <input
          id="booking-start"
          type="datetime-local"
          value={start}
          readOnly
        />
      </div>

      <div className="form-group">
        <label htmlFor="booking-end">
          Bis
        </label>

        <input
          id="booking-end"
          type="datetime-local"
          value={end}
          readOnly
        />
      </div>

      {!currentUser && (
        <p>
          Bitte anmelden, um einen
          Raum zu reservieren.
        </p>
      )}

      {ladefehler && (
        <p role="alert">
          Ressourcen konnten nicht
          geladen werden.

          <button
            type="button"
            onClick={onRefresh}
          >
            Erneut laden
          </button>
        </p>
      )}

      {!ladefehler &&
        (ressourcen.length === 0 ? (

          <div className="resource-empty">
            <div>□</div>

            <p>
              Keine Räume vorhanden.
            </p>
          </div>

        ) : (

          <div className="resource-list">

            {ressourcen.map(
              (ressource) => (

                <article
                  className="resource-item"
                  key={
                    ressource.ressourcenId
                  }
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
      {ressource.typ}
      {' · '}
      {ressource.kapazitaet}
      {' '}
      Personen
    </span>
  </div>

  <span
    className={
      terminHatBereitsRaum ||
      ressourceIstBelegt(ressource.ressourcenId)
        ? 'availability unavailable'
        : ressource.verfuegbarkeit
          ? 'availability available'
          : 'availability unavailable'
    }
  >
    {terminHatBereitsRaum
      ? 'Bereits reserviert'
      : ressourceIstBelegt(ressource.ressourcenId)
        ? 'Nicht verfügbar'
        : ressource.verfuegbarkeit
          ? 'Verfügbar'
          : 'Nicht verfügbar'}
  </span>

</div>

                  
                  <button
                    className="primary-button resource-button"
                    disabled={
                      busy ||
                      !currentUser ||
                      !terminId ||
                      terminHatBereitsRaum ||
                      !ressource.verfuegbarkeit ||
                      termsLoading ||
                      Boolean(
                        termsError
                      )
                    }
                    type="button"
                    onClick={() =>
                      bucheRessource(
                        ressource.ressourcenId
                      )
                    }
                  >
                    {busy
                      ? 'Bitte warten …'
                      : 'Raum reservieren'}
                  </button>

                </article>

              )
            )}

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

    </section>
  )
}

export default Ressourcen