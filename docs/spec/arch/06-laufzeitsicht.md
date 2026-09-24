# 6. Laufzeitsicht

## Ablauf: Gemeinsame freie Zeitfenster berechnen

1. Der Benutzer erstellt eine Terminanfrage mit Suchzeitraum, gewünschter Dauer und Teilnehmern.

2. Das Frontend sendet die Terminanfrage an das Backend.

3. Das Backend lädt die relevanten Termine der beteiligten Benutzer.

4. Bereits belegte Zeiträume werden berücksichtigt.

5. Das Backend berechnet daraus die gemeinsamen freien Zeitfenster.

6. Die berechneten Zeitfenster werden über die REST-Schnittstelle an das Frontend zurückgegeben.

7. Das Frontend zeigt die verfügbaren Zeitfenster an.

8. Der Benutzer kann anschließend einen freien Zeitslot auswählen.

9. Aus dem ausgewählten Zeitslot wird ein gemeinsamer Termin mit der gewünschten Dauer erstellt.

10. Die Terminanfrage wird anschließend als erledigt markiert.

### Beteiligte Komponenten

- React Frontend
- Spring Boot Backend
- PostgreSQL Datenbank

## Ablauf: Raum reservieren

1. Das Frontend lädt die vorhandenen Räume über die REST-Schnittstelle.

2. Der Benutzer wählt einen bestehenden Termin und einen Raum aus.

3. Beginn und Ende der Buchung werden automatisch aus dem ausgewählten Termin übernommen.

4. Das Frontend sendet die Buchungsanfrage an das Backend.

5. Das Backend prüft, ob der ausgewählte Raum während dieses Zeitraums bereits gebucht ist.

6. Zusätzlich wird geprüft, ob für den ausgewählten Termin bereits ein anderer Raum reserviert wurde.

7. Wenn keine dieser Regeln verletzt wird, wird die Buchung in PostgreSQL gespeichert.

8. Bei einem Buchungskonflikt lehnt das Backend die Anfrage ab und gibt eine passende Fehlermeldung zurück.

9. Das Frontend zeigt dem Benutzer das Ergebnis der Buchung an.

10. Der gebuchte Raum wird anschließend beim Termin im Kalender angezeigt.

### Beteiligte Komponenten

- React Frontend
- Spring Boot Backend
- PostgreSQL Datenbank

## Ablauf: Gemeinsamen Termin anzeigen

1. Ein gemeinsamer Termin wird aus einer Terminanfrage erstellt.

2. Das Backend speichert den Termin und die zugeordneten Teilnehmer.

3. Für die Teilnehmer wird eine Benachrichtigung erzeugt.

4. Meldet sich ein Teilnehmer an, lädt das Frontend seine relevanten Termine vom Backend.

5. Der gemeinsame Termin wird im persönlichen Kalender des Teilnehmers angezeigt.

6. Falls ein Raum gebucht wurde, wird dieser ebenfalls beim Termin angezeigt.

### Beteiligte Komponenten

- React Frontend
- Spring Boot Backend
- PostgreSQL Datenbank