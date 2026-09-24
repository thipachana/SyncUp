# D2 Datentypenverzeichnis

Stand: 24.09.2026

| Datentyp | Beschreibung |
| --- | --- |
| Benutzer | Enthält Benutzer-ID, Name, E-Mail-Adresse, Passwort-Hash und Rolle. |
| Kalender | Enthält Kalender-ID, Name, Beschreibung und Besitzer. |
| Termin | Enthält Termin-ID, Titel, Beschreibung, Datum, Startzeit, Endzeit, Status, den zugehörigen Kalender sowie zugeordnete Teilnehmer. |
| Terminanfrage | Enthält Terminanfrage-ID, Titel, Suchzeitraum, gewünschte Dauer, Status, Ersteller und zugeordnete Benutzer. |
| Ressource | Enthält Ressourcen-ID, Name, Typ, Kapazität und Verfügbarkeit. |
| Buchung | Verbindet einen Termin mit einer Ressource. Die Buchungszeit orientiert sich am Zeitraum des zugehörigen Termins; zusätzlich werden Status und Buchungszuordnung gespeichert. |