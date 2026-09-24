# 1. Einführung und Ziele

## Ziel des Dokuments

Dieses Dokument beschreibt die Softwarearchitektur von SyncUp.

Es zeigt, wie die Anwendung technisch aufgebaut ist, welche Komponenten verwendet werden und wie diese miteinander zusammenarbeiten.

Die Architekturdokumentation soll den Aufbau der Anwendung nachvollziehbar machen und dabei unterstützen, technische Entscheidungen und spätere Änderungen einordnen zu können.

## Ziel der Architektur

Die Architektur von SyncUp trennt Benutzeroberfläche, Geschäftslogik und Datenhaltung voneinander.

Dadurch sollen:

- die einzelnen Bereiche übersichtlich bleiben,
- Verantwortlichkeiten klar getrennt sein,
- Änderungen einfacher durchgeführt werden können,
- Fehler leichter gefunden werden,
- einzelne Komponenten unabhängig weiterentwickelt und getestet werden können.

Die fachliche Geschäftslogik und die maßgeblichen Prüfungen liegen im Backend.

Dazu gehören beispielsweise:

- Zugriffsprüfungen,
- Verarbeitung von Terminanfragen,
- Berechnung gemeinsamer freier Zeitfenster,
- Erstellung gemeinsamer Termine,
- Prüfung von Raumreservierungen.

Das Frontend übernimmt hauptsächlich die Darstellung, Benutzereingaben und die Kommunikation mit dem Backend.

Die dauerhafte Speicherung der Daten erfolgt in PostgreSQL.

## Überblick

SyncUp ist eine webbasierte Anwendung und besteht aus drei Hauptteilen:

- **Frontend:** React mit JavaScript / JSX
- **Backend:** Spring Boot mit Java 21
- **Datenbank:** PostgreSQL

Das Frontend kommuniziert über REST-Schnittstellen mit dem Backend.

Das Backend verarbeitet die Anfragen, führt die Geschäftslogik aus und greift über Spring Data JPA und Hibernate auf die PostgreSQL-Datenbank zu.

Das Frontend greift nicht direkt auf die Datenbank zu.

Dadurch bleibt die Benutzeroberfläche von der Datenhaltung getrennt und fachliche Regeln können zentral im Backend durchgesetzt werden.

## Wesentliche Architekturziele

Für SyncUp sind insbesondere folgende Ziele relevant:

- klare Trennung von Frontend, Backend und Datenbank,
- zentrale Durchsetzung fachlicher Regeln im Backend,
- dauerhafte Speicherung der Anwendungsdaten,
- verständliche und wartbare Struktur des Quellcodes,
- Unterstützung mehrerer Benutzer über eine gemeinsame Webanwendung,
- Vermeidung inkonsistenter Termin- und Ressourcenbuchungen.