# SyncUp

## Projektbeschreibung

SyncUp ist eine Webanwendung zur gemeinsamen Planung von Terminen und Räumen in Teams.

Die Anwendung soll dabei helfen, gemeinsame freie Zeitfenster zu finden, Termine zu erstellen und passende Räume dafür zu reservieren.

Zusätzlich können Benutzer private Termine in ihrem eigenen Kalender verwalten.

## Funktionen

Aktuell sind unter anderem folgende Funktionen umgesetzt:

- Registrierung, Anmeldung und Abmeldung
- persönlicher Monatskalender
- private Termine erstellen, anzeigen, bearbeiten und löschen
- Terminanfragen erstellen und anzeigen
- Teilnehmer für Terminanfragen auswählen
- gewünschte Termindauer festlegen
- gemeinsame freie Zeitfenster anhand vorhandener Termine berechnen
- freien Zeitslot als gemeinsamen Termin übernehmen
- gemeinsame Termine bei den Teilnehmern anzeigen
- Benachrichtigungen bei neu festgelegten gemeinsamen Terminen
- vorhandene Räume anzeigen
- Räume für Termine reservieren
- Überschneidungen bei Raumreservierungen verhindern
- nur einen Raum pro Termin zulassen
- gebuchten Raum im Kalender anzeigen
- Raumbuchung beim Löschen eines Termins mit entfernen
- Speicherung der Daten in PostgreSQL

Der genaue Funktionsumfang wird zusätzlich in der Spezifikation unter F3 „Anwendungsfunktionen“ beschrieben.

## Nicht Bestandteil des aktuellen Funktionsumfangs

Einige ursprünglich geplante Funktionen wurden nicht mehr in den finalen Funktionsumfang aufgenommen.

Dazu gehören unter anderem:

- Aufgabenverwaltung
- Profil- und Passwortverwaltung
- erweitertes Rollen- und Administrationssystem
- automatische Vorschläge für alternative Räume
- Verbindung zu externen Kalenderdiensten wie Google Calendar oder Microsoft Outlook

## Technologien

SyncUp verwendet:

- Java 21
- Spring Boot
- React
- JavaScript / JSX
- Vite
- PostgreSQL
- Spring Data JPA
- Hibernate
- Maven
- Git und GitHub
- Tailscale für die gemeinsame Testumgebung

## Aufbau

SyncUp besteht aus drei Hauptteilen:

- **Frontend:** React
- **Backend:** Spring Boot
- **Datenbank:** PostgreSQL

Das Frontend kommuniziert über REST-Schnittstellen mit dem Backend.

Das Backend verarbeitet die Geschäftslogik und greift auf die PostgreSQL-Datenbank zu.

## Lokale Installation

Die Anleitung zur Installation und zum lokalen Start der Anwendung befindet sich in:

`INSTALL.md`

Standardmäßig werden folgende Ports verwendet:

- Frontend: `http://localhost:5173`
- Backend: `http://localhost:8080`
- PostgreSQL: `localhost:5432`

## Gemeinsame Testumgebung

Für gemeinsame Tests im Team wird Tailscale verwendet.

Frontend, Backend und PostgreSQL laufen dabei auf einem Rechner im Team.

Die anderen Teammitglieder können über das gemeinsame Tailscale-Netzwerk auf dieselbe laufende Anwendung zugreifen.

Die Datenbank wird dabei nicht direkt freigegeben. Der Zugriff erfolgt über Frontend und Backend.

## Tests

Informationen zu den durchgeführten Tests befinden sich in:

`docs/TESTING.md`

Dort werden sowohl automatische Tests als auch manuelle Testfälle beschrieben.

## Dokumentation

Weitere Informationen befinden sich unter anderem in:

- `docs/spec` – Spezifikation
- `docs/spec/arch` – Architekturdokumentation
- `docs/TESTING.md` – Testdokumentation
- `docs/DEMO.md` – Demoablauf
- `INSTALL.md` – Installation und Start

## Team

- Thipachana Clarian Kenady — Projektleiterin
- Sarah Kouskous — Software Architect
- David Cabas Canella — Spec/Requirements Lead
- Ilias Jelloli — Implementation Lead