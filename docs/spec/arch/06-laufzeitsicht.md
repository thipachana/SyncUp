# 6. Laufzeitsicht

## Ablauf: Termin erstellen

1. Der Benutzer öffnet die Terminverwaltung im Frontend.
2. Das Frontend sendet die eingegebenen Daten über eine REST-Anfrage an das Backend.
3. Das Backend überprüft die Eingaben und verarbeitet die Geschäftslogik.
4. Anschließend werden die Termindaten in der PostgreSQL-Datenbank gespeichert.
5. Nach erfolgreicher Speicherung sendet das Backend eine Bestätigung an das Frontend.
6. Das Frontend informiert den Benutzer über die erfolgreiche Erstellung des Termins.

### Beteiligte Komponenten

- React Frontend
- Spring Boot Backend
- PostgreSQL Datenbank

## Ablauf: Gemeinsamen freien Zeitslot finden

Dieser Ablauf beschreibt die zentrale Funktion von SyncUp.

1. Der Organisator erstellt eine neue Terminanfrage.
2. Das Frontend sendet die Anfrage an das Backend.
3. Das Backend lädt die Kalender- und Termindaten aller ausgewählten Teilnehmer.
4. Das Backend vergleicht belegte und freie Zeiträume.
5. Das Backend berechnet gemeinsame freie Zeitfenster.
6. Die berechneten Vorschläge werden an das Frontend zurückgegeben.
7. Das Frontend zeigt die möglichen Termine an.
8. Der Organisator wählt einen Vorschlag aus.
9. Erst danach wird der gemeinsame Termin erstellt und in der Datenbank gespeichert.

### Beteiligte Komponenten

- React Frontend
- Spring Boot Backend
- PostgreSQL Datenbank