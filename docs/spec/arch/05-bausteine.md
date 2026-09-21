# 5. Bausteinsicht

## Überblick

Die Architektur von SyncUp besteht aus drei Hauptkomponenten: Frontend, Backend und Datenbank. Jede Komponente übernimmt klar definierte Aufgaben.

## Frontend

Das Frontend wird mit React entwickelt. Es stellt die Benutzeroberfläche bereit und ermöglicht die Interaktion mit den Funktionen des Systems.

Zu den aktuell umgesetzten Bereichen des Frontends gehören:

- Anzeige von Terminen und Terminanfragen
- Anzeige gemeinsamer freier Zeitfenster
- Ressourcenübersicht
- Reservierung von Ressourcen
- Anzeige von Fehlermeldungen bei überschneidenden Ressourcenbuchungen

Weitere Funktionen wie eine vollständige Benutzerverwaltung, Einladungen und Benachrichtigungen sind als Erweiterungen vorgesehen.

## Backend

Das Backend wird mit Spring Boot umgesetzt. Es verarbeitet die Geschäftslogik und stellt REST-Schnittstellen für das Frontend bereit.

Zu den aktuell vorhandenen fachlichen Bereichen gehören:

- Benutzerverwaltung auf Datenebene
- Kalender- und Terminverwaltung
- Verarbeitung von Terminanfragen
- Berechnung gemeinsamer freier Zeitfenster
- Ressourcenverwaltung
- Ressourcenbuchungen
- Prüfung auf überschneidende Ressourcenbuchungen

## Datenbank

Die Daten werden dauerhaft in einer PostgreSQL-Datenbank gespeichert.

Zu den aktuell verwendeten Daten gehören unter anderem:

- Benutzer
- Kalender
- Termine
- Terminanfragen
- Ressourcen
- Buchungen

## Zusammenspiel

Das Frontend kommuniziert über REST mit dem Backend. Das Backend verarbeitet die Anfragen und führt die Geschäftslogik aus. Für persistente Daten greift das Backend über Spring Data JPA auf PostgreSQL zu.

Ein direkter Zugriff des Frontends auf die Datenbank findet nicht statt. Die Ergebnisse werden vom Backend über die REST-Schnittstellen an das Frontend zurückgegeben und dort dargestellt.