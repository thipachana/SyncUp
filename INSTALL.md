# SyncUp – Installation und Inbetriebnahme

Stand: 23.09.2026

## 1. Überblick

SyncUp besteht aus:

- einem React-Frontend
- einem Spring-Boot-Backend
- einer PostgreSQL-Datenbank

Diese Anleitung beschreibt, wie SyncUp lokal auf einem Mac gestartet werden kann.

Die einzelnen Komponenten werden aktuell getrennt gestartet. Docker wird momentan noch nicht verwendet.

## 2. Voraussetzungen

Für die Ausführung werden benötigt:

- Git
- Java 21 oder eine kompatible neuere JDK-Version
- Node.js und npm
- PostgreSQL 17
- ein aktueller Webbrowser
- Homebrew für die Installation von PostgreSQL auf macOS

Die konfigurierte Java-Zielversion des Projekts ist Java 21.

Für das Backend wird der Maven Wrapper verwendet. Maven muss deshalb nicht zusätzlich installiert werden.

Die installierten Versionen können mit folgenden Befehlen geprüft werden:

```bash
git --version
java -version
node --version
npm --version
```

## 3. Projekt herunterladen

Falls das Projekt noch nicht auf dem Computer vorhanden ist:

```bash
git clone https://github.com/thipachana/SyncUp.git
cd SyncUp
```

Falls das Projekt bereits vorhanden ist, kann direkt der vorhandene Projektordner geöffnet werden.

## 4. PostgreSQL einrichten

Falls PostgreSQL 17 noch nicht installiert ist:

```bash
brew install postgresql@17
```

PostgreSQL starten:

```bash
brew services start postgresql@17
```

Falls die PostgreSQL-Befehle im Terminal nicht gefunden werden:

```bash
export PATH="$(brew --prefix postgresql@17)/bin:$PATH"
```

Anschließend kann geprüft werden, ob PostgreSQL läuft:

```bash
pg_isready -h localhost -p 5432
```

### Datenbank erstellen

Das Backend verwendet aktuell:

- Datenbank: `syncup`
- Benutzer: `uni`
- Port: `5432`

Benutzer und Datenbank können bei der ersten Einrichtung mit folgenden Befehlen erstellt werden:

```bash
createuser --host=localhost uni
createdb --host=localhost --owner=uni syncup
```

Falls Benutzer und Datenbank bereits vorhanden sind, müssen diese Befehle nicht erneut ausgeführt werden.

Die Verbindung kann anschließend getestet werden:

```bash
psql --host=localhost --username=uni --dbname=syncup
```

Das Backend greift auf folgende Datenbank zu:

```text
jdbc:postgresql://localhost:5432/syncup
```

Die Tabellen werden während der Entwicklung durch Hibernate erstellt beziehungsweise aktualisiert.

Persönliche Passwörter oder andere Zugangsdaten sollen nicht im Repository gespeichert werden.

## 5. Frontend vorbereiten

Im Projektordner:

```bash
cd frontend
```

Falls noch keine `.env`-Datei vorhanden ist:

```bash
cp .env.example .env
```

In der Datei muss die Adresse des Backends stehen:

```text
VITE_API_URL=
```

Danach die benötigten Pakete installieren:

```bash
npm install
```

## 6. Backend starten

Ein Terminal im Projektordner öffnen:

```bash
cd backend
./mvnw spring-boot:run
```

Das Backend läuft anschließend normalerweise unter:

```text
http://localhost:8080
```

Zum Test kann zum Beispiel folgende Adresse im Browser geöffnet werden:

```text
http://localhost:8080/api/terminanfragen
```

Wenn noch keine Terminanfragen vorhanden sind, kann dort eine leere Liste `[]` angezeigt werden.

Das Terminal mit dem Backend muss während der Nutzung geöffnet bleiben.

## 7. Frontend starten

Ein zweites Terminal im Projektordner öffnen:

```bash
cd frontend
npm run dev -- --port 5173 --strictPort
```

Danach im Browser öffnen:

```text
http://localhost:5173
```

Das Terminal mit dem Frontend muss ebenfalls geöffnet bleiben.

## 8. Anwendung testen

Nach dem Start sollte geprüft werden, ob:

1. die SyncUp-Oberfläche geöffnet werden kann,
2. das Frontend Daten vom Backend laden kann,
3. Terminanfragen erstellt und angezeigt werden können,
4. freie Zeitfenster berechnet werden können,
5. Ressourcen angezeigt und gebucht werden können.

Für einige Funktionen müssen bereits passende Daten in der Datenbank vorhanden sein.

Dazu gehören zum Beispiel Benutzer, Kalender, Termine, Terminanfragen und Ressourcen.

Für eine Ressourcenbuchung muss bereits ein Termin vorhanden sein.

Weitere Tests sind in `docs/TESTING.md` beschrieben.

## 9. Backend testen

Die automatisierten Tests verwenden standardmäßig eine isolierte H2-Datenbank; ein laufendes PostgreSQL ist dafür nicht erforderlich.

Im Projektordner:

```bash
cd backend
./mvnw test
```

Wenn die Tests erfolgreich durchlaufen, wird am Ende `BUILD SUCCESS` angezeigt.

Die Tests prüfen den Anwendungsstart und zentrale API-Funktionen einschließlich Anmeldung, Zugriffsrechten, Validierung und Buchungskonflikten. Details stehen in docs/TESTING.md.

## 10. Frontend prüfen

Im Frontend-Ordner kann geprüft werden, ob das Frontend erfolgreich gebaut werden kann:

```bash
npm run build
```

Ein erfolgreicher Build zeigt, dass das Frontend ohne Build-Fehler erstellt werden kann.

Die eigentlichen Funktionen werden zusätzlich über die Testdokumentation geprüft.

## 11. Häufige Probleme

### Backend kann PostgreSQL nicht erreichen

Prüfen, ob PostgreSQL gestartet wurde:

```bash
pg_isready -h localhost -p 5432
```

### Frontend erreicht das Backend nicht

Prüfen, ob:

- das Backend läuft,
- in `.env` die richtige Backend-Adresse steht,
- das Frontend nach einer Änderung neu gestartet wurde.

### Es werden keine Ressourcen angezeigt

Prüfen, ob bereits Ressourcen in der Datenbank vorhanden sind.

### Eine Ressourcenbuchung funktioniert nicht

Prüfen, ob:

- eine gültige Termin-ID verwendet wird,
- die Ressource vorhanden ist,
- der Zeitraum richtig eingegeben wurde,
- die Ressource im angegebenen Zeitraum bereits gebucht ist.

## 12. Anwendung beenden

Frontend und Backend können im jeweiligen Terminal mit:

```text
Strg + C
```

beendet werden.

PostgreSQL kann bei Bedarf ebenfalls beendet werden:

```bash
brew services stop postgresql@17
```

Vor dem nächsten Start des Backends muss PostgreSQL wieder gestartet werden.

## 13. Weitere Dokumentation

Weitere Informationen befinden sich in:

- S3 „Inbetriebnahme“
- `docs/TESTING.md`
- `docs/DEMO.md`
- F3 „Anwendungsfunktionen“

## Aktuelle lokale Verbindung und Tests

`VITE_API_URL` bleibt für den lokalen Standardstart leer. Alle Komponenten verwenden dieselbe API-Anbindung; Vite leitet `/api` an `http://localhost:8080` weiter. Beide Server müssen laufen. Bei belegtem Frontend-Port wird nicht stillschweigend ein anderer Port verwendet.

Die Anmeldung benötigt die Sitzungscookies und für schreibende Aufrufe einen CSRF-Nachweis von `/api/auth/csrf`. Die Oberfläche erledigt dies automatisch. Nach Serverneustart gegebenenfalls neu anmelden.

Backend-Tests laufen standardmäßig gegen eine separate H2-Datenbank im Arbeitsspeicher: `cd backend` und `./mvnw test`. Der normale Anwendungsstart nutzt weiterhin PostgreSQL. Produktions- oder persönliche Daten dürfen nicht für schreibende Integrationstests eingesetzt werden. HTTPS und sichere Bereitstellung außerhalb des lokalen Rechners sind ein eigener Arbeitsschritt.
