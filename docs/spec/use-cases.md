# Use Cases

Diese Übersicht enthält die wichtigsten Anwendungsfälle von SyncUp.

Die ausführliche Beschreibung der einzelnen Anwendungsfälle mit Vorbedingungen, Ablauf, Fehlerfällen, Abnahmekriterien und aktuellem Umsetzungsstand befindet sich in `F2-anwendungsfaelle.md`.

## Übersicht

- **UC1 – Benutzer registrieren**  
  Ein neuer Benutzer erstellt ein Konto und kann sich anschließend anmelden.

- **UC2 – Termin erstellen**  
  Ein angemeldeter Benutzer erstellt einen privaten Termin in seinem persönlichen Kalender.

- **UC3 – Gemeinsame freie Zeitfenster finden**  
  Für eine Terminanfrage werden die Kalender der ausgewählten Teilnehmer berücksichtigt und gemeinsame freie Zeitfenster berechnet.

- **UC4 – Ressource buchen**  
  Für einen vorhandenen Termin wird ein verfügbarer Raum reserviert. Überschneidende Buchungen desselben Raumes werden verhindert.

- **UC5 – Termin bearbeiten**  
  Ein bestehender privater Termin kann geändert werden.

- **UC6 – Terminanfragen verwalten**  
  Terminanfragen können erstellt, angezeigt und verwaltet werden.

- **UC7 – Gemeinsamen Termin aus Zeitslot erstellen**  
  Ein freies Zeitfenster wird ausgewählt und daraus ein gemeinsamer Termin mit der vorher festgelegten Dauer erstellt.

- **UC8 – Benachrichtigungen verwalten**  
  Teilnehmer erhalten Benachrichtigungen zu neu erstellten gemeinsamen Terminen und können Benachrichtigungen löschen.

- **UC9 – Termin löschen**  
  Ein privater Termin kann gelöscht werden. Ist damit eine Raumreservierung verbunden, wird diese ebenfalls entfernt.

## Hinweise

Private Termine gehören ausschließlich zum persönlichen Kalender des jeweiligen Benutzers und besitzen keine Teilnehmerauswahl.

Gemeinsame Termine entstehen über eine Terminanfrage und werden bei den beteiligten Teilnehmern im Kalender angezeigt.

Räume werden zentral verwaltet. Benutzer reservieren vorhandene Räume für bestehende Termine.

Die detaillierte fachliche Beschreibung befindet sich in [`F2-anwendungsfaelle.md`](F2-anwendungsfaelle.md).