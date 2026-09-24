# SyncUp

## Projektbeschreibung

SyncUp ist eine Webanwendung zur gemeinsamen Planung von Terminen und Räumen in Teams.

Die Anwendung unterstützt Benutzer dabei, gemeinsame freie Zeitfenster zu finden, gemeinsame Termine zu erstellen und passende Räume dafür zu reservieren.

Zusätzlich können Benutzer private Termine in ihrem persönlichen Kalender verwalten.

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
- gemeinsame Termine bei den beteiligten Teilnehmern anzeigen
- Benachrichtigungen bei neu festgelegten gemeinsamen Terminen
- Benachrichtigungen löschen
- vorhandene Räume anzeigen
- vorhandenen Termin für eine Raumreservierung auswählen
- zeitbezogene Verfügbarkeit der Räume anzeigen
- Räume für Termine reservieren
- Überschneidungen bei Raumreservierungen verhindern
- parallele Termine in unterschiedlichen freien Räumen zulassen
- nur einen Raum pro Termin zulassen
- gebuchten Raum im Kalender anzeigen
- Raumbuchung beim Löschen eines Termins automatisch mit entfernen
- Speicherung der Daten in PostgreSQL

Private Termine sind persönliche Kalendereinträge und besitzen keine Teilnehmerauswahl.

Ressourcen werden zentral im System bereitgestellt. Benutzer können vorhandene Räume für bestehende Termine reservieren.

Der genaue Funktionsumfang wird zusätzlich in der Spezifikation unter F3 „Anwendungsfunktionen“ beschrieben.

## Nicht Bestandteil des aktuellen Funktionsumfangs

Einige ursprünglich geplante Funktionen wurden nicht in den finalen Funktionsumfang aufgenommen.

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

Das Backend verarbeitet die Geschäftslogik, prüft unter anderem Termin- und Ressourcenregeln und greift auf die PostgreSQL-Datenbank zu.

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

Die Datenbank wird dabei nicht direkt freigegeben. Der Zugriff erfolgt ausschließlich über Frontend und Backend.

## Tests

Informationen zu den durchgeführten Tests befinden sich in:

`docs/TESTING.md`

Dort werden sowohl automatische Tests als auch manuelle Testfälle beschrieben.

Unter anderem werden folgende Szenarien geprüft:

- Erstellung und Verwaltung von Terminen
- gemeinsame freie Zeitfenster
- Erstellung gemeinsamer Termine
- Benachrichtigungen
- Raumreservierungen
- Verhinderung von Doppelbelegungen
- parallele Nutzung unterschiedlicher Räume
- Begrenzung auf einen Raum pro Termin
- Löschen einer Raumreservierung zusammen mit dem zugehörigen Termin

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