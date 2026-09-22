# S3 Inbetriebnahme

Stand: 22.09.2026

## Ziel

Dieser Abschnitt beschreibt kurz, was benötigt wird, um SyncUp lokal zu starten.

Eine ausführlichere Anleitung mit den benötigten Befehlen befindet sich in der Datei `INSTALL.md`.

## Voraussetzungen

Für die lokale Ausführung werden benötigt:

- Git
- Java 21
- Node.js und npm
- PostgreSQL
- ein aktueller Webbrowser

Für das Backend wird der Maven Wrapper verwendet, der bereits im Projekt enthalten ist. Maven muss deshalb nicht extra installiert werden.

## Aufbau

SyncUp besteht aus drei Teilen:

- **Frontend:** React
- **Backend:** Spring Boot
- **Datenbank:** PostgreSQL

Das Frontend kommuniziert über REST mit dem Backend. Das Backend greift auf die PostgreSQL-Datenbank zu.

## Datenbank

Vor dem Start des Backends muss PostgreSQL laufen.

Aktuell wird folgende lokale Datenbank verwendet:

- Datenbankname: `syncup`
- Benutzer: `uni`
- Adresse: `localhost`
- Port: `5432`

Falls lokal andere Zugangsdaten verwendet werden, muss die Konfiguration des Backends entsprechend angepasst werden.

Die benötigten Tabellen werden während der Entwicklung mithilfe von Hibernate erstellt beziehungsweise aktualisiert.

## Frontend

Das Frontend benötigt die Adresse des Backends.

Diese wird über `VITE_API_URL` eingestellt.

Für eine lokale Ausführung kann beispielsweise folgende Einstellung verwendet werden:

`VITE_API_URL=http://localhost:8080`

## Start der Anwendung

Die Anwendung wird in folgender Reihenfolge gestartet:

1. PostgreSQL starten.
2. Backend starten.
3. Frontend starten.
4. Anwendung im Browser öffnen.

Das Backend läuft standardmäßig unter:

`http://localhost:8080`

Das Frontend wird über Vite gestartet. Die aktuelle Adresse wird dabei im Terminal angezeigt.

Die genauen Befehle zum Start befinden sich in `INSTALL.md`.

## Prüfung

Nach dem Start sollte geprüft werden, ob:

- das Backend ohne Fehler startet,
- die Verbindung zur Datenbank funktioniert,
- das Frontend erreichbar ist,
- Frontend und Backend miteinander kommunizieren,
- Daten über die Anwendung geladen werden können.

Für die vollständige Prüfung der Funktionen wird die Datei `docs/TESTING.md` verwendet.

## Testdaten

Für einige Funktionen werden bereits vorhandene Daten benötigt.

Dazu gehören zum Beispiel:

- Benutzer
- Kalender
- Termine
- Terminanfragen
- Ressourcen
- Buchungen

Für eine Ressourcenbuchung muss beispielsweise bereits ein Termin vorhanden sein.

## Weitere Dokumentation

Weitere Informationen befinden sich in:

- `INSTALL.md` – Installation und Start
- `docs/TESTING.md` – Test der Funktionen
- `docs/DEMO.md` – Ablauf der Demonstration
- F3 „Anwendungsfunktionen“ – aktueller Funktionsumfang