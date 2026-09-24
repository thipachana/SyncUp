# 1. Einführung und Ziele

## Ziel des Dokuments

Dieses Dokument beschreibt die Softwarearchitektur von SyncUp.

Es zeigt, wie die Anwendung technisch aufgebaut ist, welche Komponenten verwendet werden und wie diese miteinander zusammenarbeiten.

Die Architekturdokumentation soll dabei helfen, den Aufbau der Anwendung nachvollziehbar zu machen und spätere Änderungen leichter einordnen zu können.

## Ziel der Architektur

Die Architektur von SyncUp soll Frontend, Backend und Datenhaltung klar voneinander trennen.

Dadurch sollen:

- die einzelnen Bereiche übersichtlich bleiben,
- Änderungen einfacher durchgeführt werden können,
- Fehler leichter gefunden werden,
- einzelne Funktionen unabhängig weiterentwickelt werden können.

Die Geschäftslogik liegt hauptsächlich im Backend. Das Frontend übernimmt die Darstellung und Benutzereingaben. Die Daten werden in PostgreSQL gespeichert.

## Überblick

SyncUp ist eine Webanwendung und besteht aus drei Hauptteilen:

- **Frontend:** React mit JavaScript / JSX
- **Backend:** Spring Boot mit Java 21
- **Datenbank:** PostgreSQL

Das Frontend kommuniziert über REST-Schnittstellen mit dem Backend.

Das Backend verarbeitet die Anfragen, führt die Geschäftslogik aus und greift über Spring Data JPA und Hibernate auf die PostgreSQL-Datenbank zu.

Das Frontend greift nicht direkt auf die Datenbank zu.