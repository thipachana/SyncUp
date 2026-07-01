# P2 Architekturüberblick

## Ziel des Architekturüberblicks

Dieser Abschnitt beschreibt den geplanten technischen Aufbau von SyncUp auf einer groben Ebene. Der Architekturüberblick dient dazu, die wichtigsten Systembestandteile und deren Zusammenspiel verständlich darzustellen. Eine detaillierte Architekturbeschreibung erfolgt später im Meilenstein M2.

## Geplante Systemstruktur

SyncUp soll als Webanwendung umgesetzt werden. Das System besteht aus einem Frontend, einem Backend und einer Datenbank.

Das Frontend stellt die Benutzeroberfläche bereit. Über diese können Nutzer Termine, Aufgaben und Ressourcen verwalten.

Das Backend verarbeitet die Anfragen des Frontends, enthält die Geschäftslogik und stellt Schnittstellen für die Kommunikation bereit.

Die Datenbank speichert die benötigten Informationen dauerhaft, zum Beispiel Benutzer, Termine, Aufgaben, Kalender und Ressourcen.

## Frontend

Das Frontend soll mit React umgesetzt werden. Es ist für die Darstellung der Oberfläche und die Interaktion mit den Nutzern zuständig.

Geplante Bereiche im Frontend sind:

- Login und Registrierung
- Dashboard
- Kalenderansicht
- Terminplanung
- Aufgabenübersicht
- Ressourcenübersicht

## Backend

Das Backend soll mit Spring Boot umgesetzt werden. Es verarbeitet die fachliche Logik der Anwendung.

Zu den geplanten Aufgaben des Backends gehören:

- Verwaltung von Benutzern
- Verwaltung von Terminen
- Abgleich von Kalendern
- Berechnung gemeinsamer freier Zeitslots
- Verwaltung von Ressourcen wie Räumen
- Bereitstellung von REST-Schnittstellen für das Frontend

## Datenbank

Für die Speicherung der Daten ist PostgreSQL vorgesehen.

Gespeichert werden voraussichtlich:

- Benutzer
- Kalender
- Termine
- Aufgaben
- Ressourcen
- Buchungen

## Kommunikation der Komponenten

Das Frontend kommuniziert mit dem Backend über REST-Schnittstellen. Das Backend greift auf die Datenbank zu, um Daten zu lesen, zu speichern oder zu ändern.

Vereinfacht dargestellt:

```text
Nutzer → Frontend → Backend → Datenbank
## Grober Systemaufbau

Die Architektur von SyncUp besteht aus drei Hauptkomponenten:

```text
                Benutzer
                    │
                    ▼
        +----------------------+
        |  React Frontend      |
        +----------------------+
                    │
              REST-Schnittstelle
                    │
                    ▼
        +----------------------+
        | Spring Boot Backend  |
        +----------------------+
                    │
                    ▼
        +----------------------+
        | PostgreSQL Datenbank |
        +----------------------+
```

### Aufgaben der Komponenten

**Frontend**
- Benutzeroberfläche
- Kalenderansicht
- Terminverwaltung
- Ressourcenübersicht

**Backend**
- Geschäftslogik
- Berechnung gemeinsamer freier Termine
- Benutzerverwaltung
- REST-API

**Datenbank**
- Speicherung von Benutzern
- Terminen
- Aufgaben
- Ressourcen
## Technische Annahmen

Für die erste Version von SyncUp wird eine Webanwendung mit React, Spring Boot und PostgreSQL verwendet. Die Kommunikation zwischen Frontend und Backend erfolgt über eine REST-Schnittstelle.

Die Synchronisierung bezieht sich zunächst auf die innerhalb von SyncUp gespeicherten Kalender- und Termindaten. Eine Anbindung an externe Kalenderdienste kann in einer späteren Version ergänzt werden.

## Zusammenfassung

Die geplante Architektur unterstützt alle wesentlichen Funktionen von SyncUp. Durch die Aufteilung in Frontend, Backend und Datenbank entsteht eine übersichtliche und erweiterbare Systemstruktur.