# 5. Bausteinsicht

## Überblick

Die Architektur von SyncUp besteht aus drei Hauptkomponenten:

- Frontend
- Backend
- Datenbank

Jede Komponente übernimmt bestimmte Aufgaben und ist klar von den anderen Bereichen getrennt.

## Frontend

Das Frontend wird mit React entwickelt.

Es stellt die Benutzeroberfläche bereit und ermöglicht die Bedienung der Funktionen von SyncUp.

Zu den aktuell umgesetzten Bereichen gehören:

- Registrierung und Anmeldung
- persönlicher Monatskalender
- private Termine erstellen, anzeigen, bearbeiten und löschen
- Terminanfragen erstellen und anzeigen
- Teilnehmer auswählen
- gemeinsame freie Zeitfenster anzeigen
- freien Zeitslot als gemeinsamen Termin übernehmen
- vorhandene Räume anzeigen
- Räume reservieren
- gebuchte Räume im Kalender anzeigen
- Benachrichtigungen anzeigen
- verständliche Fehlermeldungen bei Buchungskonflikten

Für Entwicklung und Build des Frontends wird Vite verwendet.

## Backend

Das Backend wird mit Spring Boot und Java 21 umgesetzt.

Es verarbeitet die Geschäftslogik und stellt REST-Schnittstellen für das Frontend bereit.

Zu den aktuell vorhandenen fachlichen Bereichen gehören:

- Registrierung und Anmeldung
- Sitzungsverwaltung
- Kalender- und Terminverwaltung
- Verarbeitung von Terminanfragen
- Berechnung gemeinsamer freier Zeitfenster
- Erstellung gemeinsamer Termine
- Verwaltung vorhandener Ressourcen
- Ressourcenbuchungen
- Prüfung auf überschneidende Ressourcenbuchungen
- Prüfung, dass einem Termin nur ein Raum zugeordnet wird
- Erstellung und Speicherung von Benachrichtigungen

Das Backend ist hauptsächlich in Controller, Services, Repositories und Entity-Klassen aufgeteilt.

## Datenbank

Die Daten werden dauerhaft in einer PostgreSQL-Datenbank gespeichert.

Zu den aktuell verwendeten Daten gehören unter anderem:

- Benutzer
- Kalender
- Termine
- Terminanfragen
- Ressourcen
- Buchungen
- Benachrichtigungen

Für den Zugriff auf die Datenbank verwendet das Backend Spring Data JPA und Hibernate.

## Zusammenspiel

Das Frontend kommuniziert über REST-Schnittstellen mit dem Backend.

Das Backend verarbeitet die Anfragen, prüft die Geschäftsregeln und greift für die Speicherung auf die PostgreSQL-Datenbank zu.

Ein direkter Zugriff des Frontends auf die Datenbank findet nicht statt.

Die Ergebnisse werden vom Backend an das Frontend zurückgegeben und dort für den Benutzer dargestellt.