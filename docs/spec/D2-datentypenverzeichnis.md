# D2 Datentypenverzeichnis

Stand: 24.09.2026

| Datentyp | Beschreibung |
| --- | --- |
| Benutzer | Enthält Benutzer-ID, Name, E-Mail-Adresse, Passwort-Hash und Rolle. |
| Kalender | Enthält Kalender-ID, Name, Beschreibung und Besitzer. |
| Termin | Enthält Termin-ID, Titel, Beschreibung, Datum, Startzeit, Endzeit, Status, den zugehörigen Kalender sowie gegebenenfalls zugeordnete Teilnehmer. |
| Terminanfrage | Enthält Terminanfrage-ID, Titel, Suchzeitraum, gewünschte Dauer, Status, Ersteller und zugeordnete Teilnehmer. |
| Ressource | Enthält Ressourcen-ID, Name, Typ, Kapazität und Verfügbarkeit. |
| Buchung | Verbindet einen bestehenden Termin mit einer Ressource. Beginn und Ende der Buchung werden aus dem zugehörigen Termin übernommen. Zusätzlich werden die Buchungszuordnung und der Status gespeichert. |
| Benachrichtigung | Enthält Benachrichtigungs-ID, Empfänger, Nachricht beziehungsweise Inhalt und Zeitpunkt. |