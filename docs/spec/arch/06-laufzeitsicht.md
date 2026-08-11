# 6. Laufzeitsicht

## Ablauf: Termin erstellen

Der Benutzer öffnet die Terminverwaltung im Frontend.

Das Frontend sendet die Eingaben über eine REST-Anfrage an das Backend.

Das Backend überprüft die Eingaben und verarbeitet die Geschäftslogik.

Anschließend werden die Daten in der PostgreSQL-Datenbank gespeichert.

Nach erfolgreicher Speicherung sendet das Backend eine Antwort an das Frontend.

Das Frontend informiert den Benutzer über die erfolgreiche Erstellung des Termins.

## Beteiligte Komponenten

- React Frontend
- Spring Boot Backend
- PostgreSQL Datenbank

## Ablauf: Gemeinsamen freien Zeitslot finden

Dieser Ablauf beschreibt die zentrale Funktion von SyncUp.

1. Der Organisator erstellt eine neue Terminanfrage.
2. Das Frontend sendet die Anfrage an das Backend.
3. Das Backend lädt die Kalender- und Termindaten aller ausgewählten Teilnehmer.
4. Das Backend vergleicht die belegten und freien Zeiträume.
5. Das Backend berechnet mögliche gemeinsame freie Zeitfenster.
6. Die berechneten Vorschläge werden an das Frontend zurückgegeben.
7. Das Frontend zeigt die möglichen Termine an.
8. Der Organisator wählt einen Vorschlag aus und erstellt den Termin.

## Beteiligte Komponenten

- React Frontend
- Spring Boot Backend
- PostgreSQL Datenbank