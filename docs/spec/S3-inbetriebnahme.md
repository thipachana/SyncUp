# S3 Inbetriebnahme

Stand: 24.09.2026

## Ziel

Dieser Abschnitt beschreibt die Voraussetzungen und den grundlegenden Ablauf zur lokalen Inbetriebnahme von SyncUp sowie die gemeinsame Testumgebung des Teams.

Eine ausführliche Schritt-für-Schritt-Anleitung mit den benötigten Befehlen befindet sich in `INSTALL.md`.

## Voraussetzungen

Für die lokale Ausführung werden benötigt:

- Git
- Java 21
- Node.js und npm
- PostgreSQL 17
- ein aktueller Webbrowser

Für das Backend wird der im Projekt enthaltene Maven Wrapper verwendet. Eine separate Maven-Installation ist daher nicht erforderlich.

Für gemeinsame Tests im Team wird zusätzlich Tailscale eingesetzt.

## Systemaufbau

SyncUp besteht aus drei Hauptkomponenten:

- **Frontend:** React mit Vite
- **Backend:** Spring Boot
- **Datenbank:** PostgreSQL

Das Frontend kommuniziert über REST-Schnittstellen mit dem Backend.

Das Backend enthält die Anwendungs- und Geschäftslogik und greift über Spring Data JPA und Hibernate auf PostgreSQL zu.

## Datenbank

Vor dem Start des Backends muss PostgreSQL laufen.

Für die lokale Anwendung wird folgende Datenbank verwendet:

- Datenbankname: `syncup`
- Adresse: `localhost`
- Port: `5432`

Die verwendete JDBC-Adresse lautet:

`jdbc:postgresql://localhost:5432/syncup`

Der lokale PostgreSQL-Benutzer kann sich je nach Entwicklungsrechner unterscheiden. Falls die lokalen Zugangsdaten nicht der vorhandenen Backend-Konfiguration entsprechen, müssen diese entsprechend angepasst oder über geeignete Umgebungsvariablen gesetzt werden.

Die benötigten Tabellen werden über Hibernate/JPA anhand der vorhandenen Entity-Klassen verwaltet.

## Frontend

Das Frontend benötigt eine Verbindung zum Spring-Boot-Backend.

Beim lokalen Standardbetrieb läuft das Frontend über Vite auf Port `5173` und kommuniziert mit dem Backend auf Port `8080`.

Die konkreten Einstellungen und Startbefehle sind in `INSTALL.md` dokumentiert.

## Lokaler Start

Die Anwendung wird in folgender Reihenfolge gestartet:

1. PostgreSQL starten.
2. Spring-Boot-Backend starten.
3. React-Frontend starten.
4. Anwendung im Browser öffnen.

Das Backend läuft standardmäßig unter:

`http://localhost:8080`

Das Frontend läuft standardmäßig unter:

`http://localhost:5173`

Die genauen Befehle zur Installation und zum Start befinden sich in `INSTALL.md`.

## Gemeinsame Testumgebung mit Tailscale

Für gemeinsame Mehrbenutzertests wird ein Rechner aus dem Team als Host verwendet.

Auf diesem Rechner laufen:

- PostgreSQL
- Spring-Boot-Backend
- React-Frontend
- Tailscale

Das Frontend wird über Tailscale innerhalb des gemeinsamen Tailnets erreichbar gemacht.

Dadurch greifen alle beteiligten Teammitglieder auf dieselbe laufende Anwendung und denselben Datenbestand zu.

Die PostgreSQL-Datenbank wird nicht direkt über Tailscale für andere Rechner freigegeben. Der Zugriff auf die Daten erfolgt ausschließlich über die Webanwendung und das Backend.

Der Host-Rechner muss eingeschaltet sein. Zusätzlich müssen PostgreSQL, Backend, Frontend und Tailscale aktiv sein, damit die gemeinsame Testumgebung erreichbar ist.

Tailscale wird ausschließlich für interne Entwicklungs- und Testzwecke verwendet. Eine öffentliche Bereitstellung von SyncUp ist nicht Bestandteil des Projekts.

## Prüfung der Inbetriebnahme

Nach dem Start sollte geprüft werden, ob:

- PostgreSQL erreichbar ist,
- das Backend ohne Fehler startet,
- die Verbindung zwischen Backend und Datenbank hergestellt wird,
- das Frontend erreichbar ist,
- Frontend und Backend miteinander kommunizieren,
- eine Registrierung und Anmeldung möglich ist,
- Daten über die Anwendung gespeichert und erneut geladen werden können.

Bei der gemeinsamen Testumgebung wird zusätzlich geprüft, ob mindestens ein weiterer Rechner innerhalb des Tailnets auf die Anwendung zugreifen kann.

Die funktionalen Tests der Anwendung sind in `docs/TESTING.md` dokumentiert.

## Benötigte Testdaten

Für verschiedene Funktionen werden Daten benötigt, die entweder bereits vorhanden sind oder über die Anwendung angelegt werden.

Dazu gehören insbesondere:

- Benutzer
- Kalender
- Termine
- Terminanfragen
- Ressourcen
- Buchungen
- Benachrichtigungen

Für eine Raumreservierung muss beispielsweise bereits ein Termin vorhanden sein.

Ressourcen werden zentral bereitgestellt und nicht von normalen Benutzern über die Oberfläche angelegt.

## Sicherheit bei der lokalen Nutzung

Die Authentifizierung verwendet serverseitige Sitzungen beziehungsweise Sitzungscookies.

Schreibende Aufrufe werden zusätzlich durch einen CSRF-Schutz abgesichert.

Die notwendige Verarbeitung erfolgt über die Anwendung, sodass Benutzer hierfür keine manuellen Schritte durchführen müssen.

Nach einem Neustart des Backends kann eine erneute Anmeldung erforderlich sein.

## Tests

Backend-Tests können im Backend-Verzeichnis mit dem Maven Wrapper ausgeführt werden:

```bash
./mvnw test