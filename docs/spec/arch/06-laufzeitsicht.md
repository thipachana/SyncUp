# 6. Laufzeitsicht

## Ablauf: Gemeinsame freie Zeitfenster berechnen

1. Eine Terminanfrage mit einem gewünschten Zeitraum und einer Termindauer wird verarbeitet.
2. Das Frontend sendet die Anfrage an das Backend.
3. Das Backend lädt die relevanten Termin- und Kalenderdaten.
4. Bereits belegte Zeiträume werden berücksichtigt.
5. Das Backend berechnet die verfügbaren freien Zeitfenster.
6. Die berechneten Zeitfenster werden über die REST-Schnittstelle zurückgegeben.
7. Das Frontend zeigt die verfügbaren Zeitfenster an.

### Beteiligte Komponenten

- React Frontend
- Spring Boot Backend
- PostgreSQL Datenbank

## Ablauf: Ressource reservieren

1. Das Frontend lädt die verfügbaren Ressourcen über die REST-Schnittstelle.
2. Der Benutzer wählt eine Ressource sowie einen Termin und Zeitraum aus.
3. Das Frontend sendet die Buchungsanfrage an das Backend.
4. Das Backend prüft, ob für die Ressource bereits eine zeitlich überschneidende Buchung existiert.
5. Wenn keine Überschneidung besteht, wird die Buchung in PostgreSQL gespeichert.
6. Bei einer Überschneidung lehnt das Backend die Anfrage mit HTTP-Status 409 ab.
7. Das Frontend zeigt dem Benutzer eine entsprechende Meldung an.

### Beteiligte Komponenten

- React Frontend
- Spring Boot Backend
- PostgreSQL Datenbank