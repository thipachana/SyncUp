# SyncUp

## Projektbeschreibung

SyncUp ist eine Webanwendung zur gemeinsamen Planung von Terminen und Ressourcen in Teams.

Die Anwendung soll dabei helfen, freie Zeitfenster zu finden und Ressourcen wie Räume für Termine zu reservieren.

## Aktuell umgesetzt

Aktuell sind unter anderem folgende Funktionen vorhanden:

- Terminanfragen erstellen, anzeigen und löschen
- Eigene Termine im Monatskalender erstellen, anzeigen und löschen
- Registrierung, Anmeldung und Abmeldung mit Sitzungsschutz
- Teilnehmerauswahl und separate Meetingdauer
- freie Zeitfenster anhand vorhandener Termine berechnen
- Ressourcen anzeigen
- Ressourcen buchen
- überschneidende Ressourcenbuchungen verhindern
- Daten in PostgreSQL speichern
- Verbindung zwischen React-Frontend, Spring-Boot-Backend und PostgreSQL

Einige Funktionen sind bisher nur teilweise umgesetzt. Der genaue Stand wird in der Spezifikation unter F3 „Anwendungsfunktionen“ beschrieben.

## Noch nicht vollständig umgesetzt

Dazu gehören unter anderem:

- vollständige Benutzerverwaltung
- Termine bearbeiten und durchsuchen
- Erweiterte Teilnehmerverwaltung und Einladungen
- Einladungen und Benachrichtigungen
- Ressourcen freigeben
- Aufgabenverwaltung

## Technologien

SyncUp verwendet aktuell:

- Java 21
- Spring Boot
- React
- Vite
- PostgreSQL
- Maven
- Git und GitHub

Docker war ursprünglich für das Projekt vorgesehen, ist im aktuellen Stand aber noch nicht eingerichtet.

## Installation

Die Anleitung zur lokalen Installation und zum Start der Anwendung befindet sich in:

`INSTALL.md`

## Dokumentation

Weitere Informationen befinden sich unter anderem in:

- `docs/spec` – Spezifikation
- `docs/TESTING.md` – Testdokumentation
- `docs/DEMO.md` – Demoablauf
- `docs/spec/arch` – Architekturdokumentation

## Team

- Thipachana Clarian Kenady — Projektleiterin
- Sarah Kouskous — Software Architect
- David Cabas Canella — Spec/Requirements Lead
- Ilias Jelloli — Implementation Lead