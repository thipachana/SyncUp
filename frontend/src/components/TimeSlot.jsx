function TimeSlot({ date, startTime, endTime, available }) {
  return (
    <div className="time-slot">
      <h3>{date}</h3>

      <p>{startTime} – {endTime}</p>

      <p>Status: {available ? "Verfügbar" : "Nicht verfügbar"}</p>
    </div>
  );
}

export default TimeSlot;