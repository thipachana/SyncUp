# Architekturübersicht

Stand: 24.09.2026

## Ziel

SyncUp ist eine Webanwendung zur gemeinsamen Planung von Terminen und Räumen.

Die Anwendung unterstützt Benutzer dabei, private Termine zu verwalten, gemeinsame freie Zeitfenster zu finden und für Termine vorhandene Räume zu reservieren.

## Systemstruktur

SyncUp besteht aus drei Hauptbereichen:

- Frontend
- Backend
- Datenbank

Das Frontend stellt die Benutzeroberfläche bereit.

Das Backend verarbeitet die Geschäftslogik und stellt REST-Schnittstellen zur Verfügung.

Die Datenbank speichert die Daten der Anwendung dauerhaft.

## Technologien

Für SyncUp werden folgende Technologien verwendet:

- **Frontend:** React mit JavaScript / JSX
- **Frontend-Tooling:** Vite
- **Backend:** Spring Boot mit Java 21
- **Datenbank:** PostgreSQL
- **Persistenz:** Spring Data JPA und Hibernate
- **Build:** Maven und npm
- **Versionsverwaltung:** Git und GitHub
- **Gemeinsame Testumgebung:** Tailscale

## Komponenten

Zu den wichtigsten fachlichen Bereichen von SyncUp gehören:

- Registrierung und Anmeldung
- persönliche Kalenderverwaltung
- private Terminverwaltung
- Terminanfragen
- Teilnehmerzuordnung
- Berechnung gemeinsamer freier Zeitfenster
- Erstellung gemeinsamer Termine
- Benachrichtigungen
- Ressourcenverwaltung
- Raumreservierungen
- Prüfung von Buchungskonflikten

## Kommunikation

Das Frontend kommuniziert über REST-Schnittstellen mit dem Backend.

Die Daten werden hauptsächlich im JSON-Format übertragen.

Das Backend greift über Spring Data JPA und Hibernate auf PostgreSQL zu.

Ein direkter Zugriff des Frontends auf die Datenbank findet nicht statt.

## Nicht Bestandteil des aktuellen Funktionsumfangs

Nicht umgesetzt beziehungsweise nicht Teil der finalen Version sind unter anderem:

- Aufgabenverwaltung
- externe Kalenderanbindung
- erweitertes Rollen- und Administrationssystem
- Profil- und Passwortverwaltung
- automatische Vorschläge für alternative Räume