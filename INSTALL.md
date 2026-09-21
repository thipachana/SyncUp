# SyncUp – Installation und Inbetriebnahme

## Voraussetzungen

Für die lokale Ausführung werden benötigt:

- Java 21
- PostgreSQL
- Node.js und npm
- Git

Das Backend verwendet den im Projekt enthaltenen Maven Wrapper.

## 1. Repository klonen

```bash
git clone https://github.com/thipachana/SyncUp.git
cd SyncUp
```

## 2. PostgreSQL vorbereiten

SyncUp verwendet eine lokale PostgreSQL-Datenbank mit dem Namen `syncup`.

Die Datenbankverbindung lautet:

```text
jdbc:postgresql://localhost:5432/syncup
```

Der lokale PostgreSQL-Benutzer muss Zugriff auf diese Datenbank besitzen.

## 3. Backend starten

```bash
cd backend
./mvnw spring-boot:run
```

Das Backend läuft standardmäßig unter:

```text
http://localhost:8080
```

## 4. Frontend konfigurieren

Im Ordner `frontend` wird eine lokale `.env`-Datei verwendet.

Beispiel:

```text
VITE_API_URL=http://localhost:8080
```

## 5. Frontend starten

```bash
cd frontend
npm install
npm run dev
```

Vite zeigt anschließend die lokale Adresse des Frontends im Terminal an.

## 6. Tests

Backend:

```bash
cd backend
./mvnw test
```

Frontend:

```bash
cd frontend
npm run build
```

## 7. Funktionsprüfung

Nach dem Start können unter anderem folgende Funktionen geprüft werden:

- Termine und Terminanfragen anzeigen
- gemeinsame freie Zeitfenster berechnen
- Ressourcen anzeigen
- Ressourcen reservieren
- überschneidende Ressourcenbuchungen verhindern
- Speicherung der Daten in PostgreSQL
- Benutzerpasswörter werden nicht über die API ausgegeben

## Architektur

SyncUp verwendet eine Drei-Schichten-Architektur:

1. React-Frontend
2. Spring-Boot-Backend mit REST-Schnittstellen
3. PostgreSQL-Datenbank

Das Frontend kommuniziert über REST mit dem Backend. Ein direkter Zugriff des Frontends auf die Datenbank erfolgt nicht.