function TimeSlot({ date, endDate, startTime, endTime, available }) {
  return (
    <article className="time-slot">
      <div className="time-slot-check">
        ✓
      </div>

      <div className="time-slot-main">
        <strong>
          {startTime} – {endTime}
        </strong>

        <span>{date}{endDate && endDate !== date ? ` bis ${endDate}` : ''}</span>
      </div>

      <span
        className={
          available
            ? 'availability available'
            : 'availability unavailable'
        }
      >
        {available ? 'Verfügbar' : 'Nicht verfügbar'}
      </span>
    </article>
  )
}

export default TimeSlot