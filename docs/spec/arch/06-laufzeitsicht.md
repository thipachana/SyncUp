# 6. Laufzeitsicht

## Ablauf: Gemeinsame freie Zeitfenster berechnen

1. Der Benutzer erstellt eine Terminanfrage mit Suchzeitraum, gewünschter Dauer und Teilnehmern.
2. Das Frontend sendet die Terminanfrage an das Backend.
3. Das Backend speichert die Anfrage und die zugeordneten Teilnehmer.
4. Für die Berechnung lädt das Backend die relevanten Termine der beteiligten Benutzer.
5. Bereits belegte Zeiträume werden berücksichtigt.
6. Das Backend berechnet daraus gemeinsame freie Zeitfenster, die mindestens der gewünschten Dauer entsprechen.
7. Die berechneten Zeitfenster werden über die REST-Schnittstelle an das Frontend zurückgegeben.
8. Das Frontend zeigt die verfügbaren Zeitfenster an.
9. Der Benutzer wählt einen freien Zeitslot aus.
10. Aus dem ausgewählten Zeitslot wird ein gemeinsamer Termin mit der vorher festgelegten Dauer erstellt.
11. Der gemeinsame Termin wird den beteiligten Benutzern zugeordnet.
12. Für die Teilnehmer werden Benachrichtigungen erzeugt.
13. Die Terminanfrage wird anschließend als erledigt markiert.

### Beteiligte Komponenten

- React-Frontend
- Spring-Boot-Backend
- PostgreSQL-Datenbank

## Ablauf: Raum reservieren

1. Das Frontend lädt die vorhandenen Räume über die REST-Schnittstelle.
2. Das Frontend lädt die für den angemeldeten Benutzer relevanten Termine.
3. Der Benutzer wählt einen bestehenden Termin aus.
4. Datum, Beginn und Ende werden automatisch aus dem ausgewählten Termin übernommen.
5. Das Frontend fragt beim Backend die zeitbezogene Verfügbarkeit der vorhandenen Räume für diesen Termin ab.
6. Das Backend lädt die bereits gespeicherten Buchungen der Ressourcen.
7. Das Backend prüft für jeden Raum, ob sich eine vorhandene Buchung mit dem Zeitraum des ausgewählten Termins überschneidet.
8. Das Backend gibt die ermittelte Verfügbarkeit an das Frontend zurück.
9. Das Frontend zeigt belegte Räume als „Nicht verfügbar“ und freie Räume als „Verfügbar“ an.
10. Der Benutzer wählt einen verfügbaren Raum aus.
11. Das Frontend sendet die Buchungsanfrage an das Backend.
12. Das Backend prüft unmittelbar vor dem Speichern erneut:
    - ob die Ressource grundsätzlich verfügbar ist,
    - ob im Zeitraum des Termins bereits eine überschneidende Buchung für diesen Raum existiert,
    - ob für den Termin bereits ein anderer Raum reserviert wurde.
13. Wird keine Regel verletzt, wird die Buchung in PostgreSQL gespeichert.
14. Bei einem Konflikt wird die Buchung nicht gespeichert und das Backend gibt eine passende Fehlermeldung zurück.
15. Das Frontend aktualisiert den Buchungszustand und zeigt dem Benutzer das Ergebnis an.
16. Der gebuchte Raum wird anschließend beim Termin im Kalender angezeigt.

### Beteiligte Komponenten

- React-Frontend
- Spring-Boot-Backend
- PostgreSQL-Datenbank

## Ablauf: Gemeinsamen Termin anzeigen

1. Ein gemeinsamer Termin wird aus einer Terminanfrage erstellt.
2. Das Backend speichert den Termin und die zugeordneten Teilnehmer.
3. Für die beteiligten Teilnehmer werden Benachrichtigungen erzeugt.
4. Meldet sich ein Teilnehmer an, lädt das Frontend seine relevanten Termine vom Backend.
5. Das Backend liefert sowohl persönliche als auch für den Benutzer relevante gemeinsame Termine zurück.
6. Der gemeinsame Termin wird im persönlichen Kalender des Teilnehmers angezeigt.
7. Falls für den Termin ein Raum gebucht wurde, wird dieser ebenfalls beim Termin angezeigt.

### Beteiligte Komponenten

- React-Frontend
- Spring-Boot-Backend
- PostgreSQL-Datenbank

## Ablauf: Termin mit Raumreservierung löschen

1. Ein Benutzer löscht einen eigenen Termin über die Kalenderoberfläche.
2. Das Frontend sendet die Löschanfrage an das Backend.
3. Das Backend prüft, ob der Benutzer zum Löschen des Termins berechtigt ist.
4. Existiert für den Termin eine Raumreservierung, wird diese zusammen mit dem Termin entfernt.
5. Die Änderungen werden in PostgreSQL gespeichert.
6. Das Frontend aktualisiert anschließend die Kalenderdarstellung.
7. Der zuvor gebuchte Raum steht danach wieder für andere Termine zur Verfügung.

### Beteiligte Komponenten

- React-Frontend
- Spring-Boot-Backend
- PostgreSQL-Datenbank