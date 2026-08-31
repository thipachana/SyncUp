# 5. Bausteinsicht

## Überblick

Die Architektur von SyncUp besteht aus drei Hauptkomponenten: Frontend, Backend und Datenbank. Jede Komponente übernimmt klar definierte Aufgaben.

## Frontend

Das Frontend wird mit React entwickelt. Es stellt die Benutzeroberfläche bereit und ermöglicht den Benutzern die Interaktion mit dem System.

Zu den Aufgaben des Frontends gehören:

- Anmeldung und Registrierung
- Kalenderansicht
- Terminverwaltung
- Aufgabenverwaltung
- Ressourcenübersicht

## Backend

Das Backend wird mit Spring Boot umgesetzt. Es verarbeitet die Geschäftslogik und stellt REST-Schnittstellen für das Frontend bereit.

Das Backend besteht aus folgenden fachlichen Bereichen:

- Benutzerverwaltung
- Kalenderverwaltung
- Terminverwaltung
- Berechnung gemeinsamer freier Zeitfenster
- Ressourcenverwaltung

## Datenbank

Die Daten werden dauerhaft in einer PostgreSQL-Datenbank gespeichert.

Gespeichert werden unter anderem:

- Benutzer
- Kalender
- Termine
- Aufgaben
- Ressourcen
- Buchungen

## Zusammenspiel

Das Frontend kommuniziert über REST mit dem Backend. Das Backend verarbeitet die Anfragen, führt die Geschäftslogik aus und greift anschließend auf die PostgreSQL-Datenbank zu. Die Ergebnisse werden über das Backend an das Frontend zurückgegeben und dort dargestellt.