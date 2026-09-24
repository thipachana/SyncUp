# ADR-005: Serverzentrierte Konsistenzprüfung bei Raumreservierungen

## Status

Akzeptiert

## Kontext

SyncUp ermöglicht die Reservierung von Räumen für vorhandene Termine.

Dabei gelten folgende fachliche Regeln:

- Ein Raum darf nicht gleichzeitig für mehrere sich überschneidende Termine reserviert werden.
- Unterschiedliche Räume dürfen im gleichen Zeitraum für unterschiedliche Termine verwendet werden.
- Ein Termin darf höchstens einen Raum besitzen.
- Die Buchungszeit entspricht dem Zeitraum des zugehörigen Termins.

Da mehrere Benutzer gleichzeitig mit SyncUp arbeiten können, kann sich die Verfügbarkeit eines Raumes zwischen der Anzeige im Frontend und dem tatsächlichen Speichern verändern.

## Entscheidung

Die Konsistenzregeln für Raumreservierungen werden zentral im Backend durchgesetzt.

Das Frontend fragt nach Auswahl eines Termins zunächst die zeitbezogene Verfügbarkeit der Räume beim Backend ab.

Diese Information wird zur Anzeige von „Verfügbar“ beziehungsweise „Nicht verfügbar“ verwendet.

Vor dem Speichern einer Buchung prüft das Backend erneut:

- die grundsätzliche Verfügbarkeit der Ressource,
- vorhandene überschneidende Buchungen,
- eine bereits vorhandene Raumreservierung für denselben Termin.

Die Buchungszeit wird aus dem ausgewählten Termin übernommen.

## Alternativen

### Prüfung nur im Frontend

Verworfen, weil REST-Aufrufe auch unabhängig vom Frontend ausgeführt werden können und sich der Zustand zwischen Anzeige und Speichern ändern kann.

### Prüfung nur einmal beim Laden

Verworfen, weil eine angezeigte Verfügbarkeit bei paralleler Nutzung bereits veraltet sein kann.

### Keine zentrale Prüfung

Verworfen, da dadurch inkonsistente und doppelte Raumreservierungen entstehen könnten.

## Konsequenzen

- Das Backend bleibt die maßgebliche Instanz für die Buchungsregeln.
- Das Frontend kann frühzeitig die zeitbezogene Verfügbarkeit darstellen.
- Doppelbelegungen desselben Raumes werden verhindert.
- Unterschiedliche freie Räume bleiben im gleichen Zeitraum parallel nutzbar.
- Ein Termin kann höchstens eine Raumreservierung besitzen.
- Das Frontend muss Konfliktmeldungen des Backends verständlich darstellen.