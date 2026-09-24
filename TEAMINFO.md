# Teaminfo & Projektidee

Stand: 24.09.2026

## Projekttitel

SyncUp

## Kurzbeschreibung

SyncUp ist eine Webanwendung zur gemeinsamen Terminplanung für Projektgruppen, Unternehmen und kleinere Teams.

Die Anwendung unterstützt Benutzer dabei, gemeinsame freie Zeitfenster zu finden. Dafür werden die vorhandenen Termine der beteiligten Personen berücksichtigt.

Aus einem freien Zeitfenster kann anschließend ein gemeinsamer Termin erstellt werden.

Zusätzlich können für Termine vorhandene Räume reserviert werden. Dabei prüft SyncUp, ob der gewünschte Raum zu dieser Zeit bereits belegt ist.

Jeder Benutzer besitzt außerdem einen persönlichen Kalender, in dem private Termine erstellt, bearbeitet und gelöscht werden können.

## Team

| Name | Studiengang | Rolle | Git-Handle |
|---|---|---|---|
| Thipachana Clarian Kenady | WI B.Sc. | Projektleiterin | thipachana |
| Sarah Kouskous | WI B.Sc. | Software Architect | sae0900 |
| David Cabas Canella | WI B.Sc. | Spec/Requirements Lead | david241104 |
| Ilias Jelloli | WI B.Sc. | Implementation Lead | Ilias04j |

Im öffentlichen Repository werden keine Matrikelnummern, privaten Telefonnummern oder persönlichen E-Mail-Adressen veröffentlicht.

Die vollständigen Angaben der Teammitglieder werden bei Bedarf separat an den Betreuer übermittelt.

## Technologien

Für SyncUp werden folgende Technologien verwendet:

- **Backend:** Java 21 und Spring Boot
- **Frontend:** React mit JavaScript / JSX
- **Frontend-Tooling:** Vite
- **Datenbank:** PostgreSQL
- **Persistenz:** Spring Data JPA und Hibernate
- **Build:** Maven und npm
- **Versionsverwaltung:** Git und GitHub
- **Gemeinsame Testumgebung:** Tailscale

## Technischer Aufbau

SyncUp besteht aus drei Hauptteilen:

- React-Frontend
- Spring-Boot-Backend
- PostgreSQL-Datenbank

Das Frontend kommuniziert über REST-Schnittstellen mit dem Backend.

Das Backend verarbeitet die Geschäftslogik und greift über JPA und Hibernate auf die PostgreSQL-Datenbank zu.

Für gemeinsame Tests im Team wird Tailscale verwendet.

## Repository

- **URL:** https://github.com/thipachana/SyncUp.git
- **Sichtbarkeit:** öffentlich

## Eingesetzte KI-Werkzeuge

Im Projekt wurden KI-Werkzeuge unterstützend eingesetzt.

Verwendet wurden unter anderem:

- ChatGPT für Dokumentation, Strukturierung, technische Fragen und Fehlersuche
- Codex zur Unterstützung bei einzelnen Codeprüfungen und Änderungen

Die erzeugten Vorschläge wurden nicht ungeprüft übernommen.

Änderungen wurden vom Team angepasst und durch Builds, Tests oder manuelle Prüfungen kontrolliert.

Die endgültige Entscheidung über die Verwendung der Vorschläge lag beim Team.

Eine ausführlichere Beschreibung des KI-Einsatzes befindet sich in der entsprechenden Projektdokumentation.