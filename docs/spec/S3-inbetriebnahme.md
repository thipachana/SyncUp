# S3 Inbetriebnahme

Stand: 24.09.2026

## Ziel

Dieser Abschnitt beschreibt, was benötigt wird, um SyncUp lokal zu starten und wie die Anwendung für gemeinsame Tests im Team erreichbar gemacht werden kann.

Eine ausführlichere Anleitung mit den benötigten Befehlen befindet sich in der Datei `INSTALL.md`.

## Voraussetzungen

Für die lokale Ausführung werden benötigt:

- Git
- Java 21
- Node.js und npm
- PostgreSQL
- ein aktueller Webbrowser

Für das Backend wird der Maven Wrapper verwendet, der bereits im Projekt enthalten ist. Maven muss deshalb nicht extra installiert werden.

Für gemeinsame Tests im Team wird zusätzlich Tailscale verwendet.

## Aufbau

SyncUp besteht aus drei Teilen:

- **Frontend:** React
- **Backend:** Spring Boot
- **Datenbank:** PostgreSQL

Das Frontend kommuniziert über REST mit dem Backend.

Das Backend greift auf die PostgreSQL-Datenbank zu.

## Datenbank

Vor dem Start des Backends muss PostgreSQL laufen.

Für die lokale Teamumgebung wird folgende Datenbank verwendet:

- Datenbankname: `syncup`
- Benutzer: `uni`
- Adresse: `localhost`
- Port: `5432`

Falls lokal andere Zugangsdaten verwendet werden, muss die Backend-Konfiguration entsprechend angepasst werden.

Die benötigten Tabellen werden mithilfe von Hibernate erstellt beziehungsweise aktualisiert.

## Frontend

Das Frontend benötigt die Verbindung zum Backend.

Für den lokalen Standardstart bleibt `VITE_API_URL` leer. Vite leitet Aufrufe an `/api` an das lokal laufende Backend weiter.

## Lokaler Start

Die Anwendung wird in folgender Reihenfolge gestartet:

1. PostgreSQL starten.
2. Backend starten.
3. Frontend starten.
4. Anwendung im Browser öffnen.

Das Backend läuft standardmäßig unter:

`http://localhost:8080`

Das Frontend läuft standardmäßig unter:

`http://localhost:5173`

Die genauen Befehle zum Start befinden sich in `INSTALL.md`.

## Gemeinsame Testumgebung mit Tailscale

Für gemeinsame Tests wird ein Rechner aus dem Team als Host verwendet.

Auf diesem Rechner laufen:

- PostgreSQL
- das Spring-Boot-Backend
- das React-Frontend

Über Tailscale wird das Frontend innerhalb des gemeinsamen Tailscale-Netzwerks erreichbar gemacht.

Dadurch können die anderen Teammitglieder die gleiche laufende Anwendung und den gleichen Datenbestand verwenden.

Die PostgreSQL-Datenbank wird dabei nicht direkt über Tailscale freigegeben. Die anderen Benutzer greifen nur über die Webanwendung und das Backend auf die Daten zu.

Der Host-Rechner muss eingeschaltet sein und Frontend, Backend sowie Tailscale müssen laufen, damit die gemeinsame Anwendung erreichbar ist.

Tailscale wird nur für die interne Testumgebung des Teams verwendet. Eine öffentliche Bereitstellung der Anwendung ist nicht Bestandteil des Projekts.

## Prüfung

Nach dem Start sollte geprüft werden, ob:

- das Backend ohne Fehler startet,
- die Verbindung zur Datenbank funktioniert,
- das Frontend erreichbar ist,
- Frontend und Backend miteinander kommunizieren,
- eine Anmeldung möglich ist,
- Daten über die Anwendung geladen und gespeichert werden können.

Bei der gemeinsamen Testumgebung sollte zusätzlich geprüft werden, ob die Anwendung von einem zweiten Teammitglied über Tailscale erreichbar ist.

Für die vollständige Prüfung der Funktionen wird die Datei `docs/TESTING.md` verwendet.

## Testdaten

Für einige Funktionen werden bereits vorhandene beziehungsweise während der Nutzung angelegte Daten benötigt.

Dazu gehören zum Beispiel:

- Benutzer
- Kalender
- Termine
- Terminanfragen
- Ressourcen
- Buchungen
- Benachrichtigungen

Für eine Ressourcenbuchung muss zum Beispiel bereits ein Termin vorhanden sein.

## Sicherheit bei der lokalen Nutzung

Die Anmeldung verwendet Sitzungscookies.

Für schreibende Aufrufe wird zusätzlich ein CSRF-Nachweis verwendet.

Die Oberfläche übernimmt diese Verarbeitung automatisch.

Nach einem Neustart des Backends kann es notwendig sein, sich erneut anzumelden.

## Tests

Backend-Tests können mit dem Maven Wrapper ausgeführt werden.

Dazu wird im Backend-Verzeichnis folgender Befehl verwendet:

`./mvnw test`

Die automatisierten Backend-Tests verwenden eine separate Testdatenbank und greifen nicht auf persönliche Daten aus der normalen Team-Datenbank zu.

Zusätzlich werden die wichtigsten Abläufe manuell über die Weboberfläche geprüft.

## Weitere Dokumentation

Weitere Informationen befinden sich in:

- `INSTALL.md` – Installation und Start
- `docs/TESTING.md` – Test der Funktionen
- `docs/DEMO.md` – Ablauf der Demonstration
- F3 „Anwendungsfunktionen“ – aktueller Funktionsumfang