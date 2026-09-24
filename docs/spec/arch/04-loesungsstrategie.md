# 4. Lösungsstrategie

## Architekturansatz

SyncUp wird als klassische Drei-Schichten-Architektur aufgebaut.

Die Anwendung besteht aus:

- Präsentationsschicht
- Anwendungsschicht
- Datenhaltungsschicht

Diese Trennung soll dafür sorgen, dass Benutzeroberfläche, Geschäftslogik und Datenspeicherung möglichst unabhängig voneinander bleiben.

## Präsentationsschicht

Die Präsentationsschicht wird mit React umgesetzt.

Sie stellt die Benutzeroberfläche bereit und verarbeitet die Eingaben der Benutzer.

Dazu gehören unter anderem:

- Registrierung und Anmeldung
- persönlicher Kalender
- private Termine
- Terminanfragen
- Auswahl von Teilnehmern
- Anzeige freier Zeitfenster
- Raumreservierungen
- Benachrichtigungen

Für Entwicklung und Build des Frontends wird Vite verwendet.

## Anwendungsschicht

Die Anwendungsschicht wird mit Spring Boot und Java 21 umgesetzt.

Sie verarbeitet die Geschäftslogik und stellt REST-Schnittstellen für das Frontend bereit.

Das Backend übernimmt unter anderem:

- Benutzerverwaltung und Anmeldung
- Verwaltung von Kalendern und Terminen
- Verarbeitung von Terminanfragen
- Berechnung gemeinsamer freier Zeitfenster
- Erstellung gemeinsamer Termine
- Verwaltung von Ressourcen und Buchungen
- Prüfung auf Überschneidungen bei Raumreservierungen
- Erstellung von Benachrichtigungen

Die Logik ist im Backend auf Controller, Services und Repositories aufgeteilt.

## Datenhaltungsschicht

Die Daten werden in einer PostgreSQL-Datenbank gespeichert.

Dazu gehören unter anderem:

- Benutzer
- Kalender
- Termine
- Terminanfragen
- Ressourcen
- Buchungen
- Benachrichtigungen

Für den Zugriff auf die Datenbank werden Spring Data JPA und Hibernate verwendet.

## Kommunikation

Das Frontend kommuniziert über REST-Schnittstellen mit dem Backend.

Die Daten werden hauptsächlich im JSON-Format übertragen.

Das Frontend greift nicht direkt auf die PostgreSQL-Datenbank zu.

Alle Zugriffe auf Daten und Geschäftslogik laufen über das Backend.