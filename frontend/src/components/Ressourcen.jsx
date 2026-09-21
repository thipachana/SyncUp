function Ressourcen({ ressourcen }) {
  return (
    <section>
      <h2>Ressourcen</h2>

      {ressourcen.length === 0 ? (
        <p>Keine Ressourcen vorhanden.</p>
      ) : (
        ressourcen.map((ressource) => (
          <div key={ressource.ressourcenId}>
            <h3>{ressource.name}</h3>
            <p>Typ: {ressource.typ}</p>
            <p>Kapazität: {ressource.kapazitaet}</p>
            <p>
              Status: {ressource.verfuegbarkeit ? 'Verfügbar' : 'Nicht verfügbar'}
            </p>
          </div>
        ))
      )}
    </section>
  )
}

export default Ressourcen