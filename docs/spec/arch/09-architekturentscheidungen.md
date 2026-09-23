# 9. Architekturentscheidungen

In diesem Kapitel werden zentrale Architekturentscheidungen des Projekts SyncUp dokumentiert.

## Architekturprinzip: Drei-Schichten-Architektur

### Kontext
SyncUp benötigt eine Benutzeroberfläche, eine zentrale Verarbeitung der Anwendungslogik und eine dauerhafte Speicherung der Daten.

### Alternativen
- Anwendung ohne klare Trennung der Schichten
- Direkter Zugriff des Frontends auf die Datenbank
- Trennung in Frontend, Backend und Datenbank

### Entscheidung
SyncUp verwendet eine Drei-Schichten-Architektur bestehend aus React-Frontend, Spring-Boot-Backend und PostgreSQL-Datenbank.

### Begründung
Die Trennung ermöglicht eine klare Aufteilung der Verantwortlichkeiten. Frontend, Backend und Datenhaltung können dadurch weitgehend unabhängig entwickelt und getestet werden.

### Konsequenzen
Die einzelnen Schichten müssen über definierte Schnittstellen miteinander kommunizieren. Dadurch entsteht zusätzlicher Konfigurationsaufwand, beispielsweise für die Verbindung zwischen Frontend und Backend.

---

## ADR-004: REST-Schnittstelle zwischen Frontend und Backend

### Kontext
Das React-Frontend benötigt eine Schnittstelle, um Daten vom Backend abzurufen und neue Daten an das Backend zu senden.

### Alternativen
- Direkter Datenbankzugriff durch das Frontend
- GraphQL
- REST

### Entscheidung
Die Kommunikation zwischen Frontend und Backend erfolgt über REST-Schnittstellen und JSON.

### Begründung
REST lässt sich mit Spring Boot einfach bereitstellen und vom React-Frontend über HTTP-Anfragen verwenden. Außerdem bleibt das Frontend dadurch von der Datenbank getrennt.

### Konsequenzen
Für benötigte Funktionen müssen entsprechende API-Endpunkte im Backend bereitgestellt werden. Zusätzlich muss die Kommunikation zwischen Frontend und Backend, beispielsweise über CORS, korrekt konfiguriert werden.

---

## ADR-003: PostgreSQL mit Spring Data JPA

### Kontext
Termine, Benutzer, Kalender, Ressourcen und Buchungen müssen dauerhaft gespeichert werden.

### Alternativen
- Speicherung ausschließlich im Arbeitsspeicher
- Direkter Datenbankzugriff über JDBC
- Relationale PostgreSQL-Datenbank mit Spring Data JPA

### Entscheidung
SyncUp verwendet PostgreSQL als relationale Datenbank. Der Datenzugriff im Backend erfolgt mit Spring Data JPA und Hibernate.

### Begründung
Die Daten von SyncUp besitzen Beziehungen untereinander. Eine relationale Datenbank eignet sich für diese Datenstruktur. Spring Data JPA reduziert außerdem den notwendigen Code für grundlegende Datenbankoperationen.

### Konsequenzen
Für die lokale Ausführung muss PostgreSQL eingerichtet sein. Änderungen an den Datenmodellen können außerdem Auswirkungen auf das Datenbankschema haben.

---

## ADR-002: React mit Vite für das Frontend

### Kontext
SyncUp benötigt eine browserbasierte Benutzeroberfläche zur Darstellung und Bedienung der Funktionen.

### Alternativen
- Serverseitig erzeugte Benutzeroberfläche
- Vue
- React

### Entscheidung
Das Frontend wird mit React umgesetzt. Für Entwicklung und Build wird Vite verwendet.

### Begründung
React ermöglicht die komponentenbasierte Entwicklung der Benutzeroberfläche und kann unabhängig vom Spring-Boot-Backend entwickelt werden. Vite stellt eine einfache Entwicklungs- und Build-Umgebung bereit.

### Konsequenzen
Für das Frontend werden Node.js und npm benötigt. Außerdem muss die Adresse des Backends für die Kommunikation des Frontends korrekt konfiguriert sein.

---

## ADR-005: Prüfung überschneidender Ressourcenbuchungen im Backend

### Kontext
Eine Ressource, beispielsweise ein Raum, darf nicht gleichzeitig für sich überschneidende Zeiträume mehrfach reserviert werden.

### Alternativen
- Prüfung ausschließlich im Frontend
- Keine Prüfung auf Überschneidungen
- Zentrale Prüfung im Backend

### Entscheidung
Innerhalb einer Transaktion sperrt das Backend die Ressource und prüft vor dem Speichern, ob für die ausgewählte Ressource bereits eine zeitlich überschneidende Buchung existiert.

### Begründung
Die Geschäftsregel soll unabhängig vom verwendeten Frontend gelten. Eine Prüfung ausschließlich im Frontend könnte umgangen werden, beispielsweise durch einen direkten Aufruf der REST-Schnittstelle.

### Konsequenzen
Überschneidende Buchungen werden vom Backend abgelehnt. Das Frontend muss die entsprechende Fehlermeldung behandeln und dem Benutzer anzeigen.

Die nummerierten Basisentscheidungen stehen in [ADR 001 – Spring Boot](adrs/001-use-spring-boot.md), [ADR 002 – React](adrs/002-use-react.md), [ADR 003 – PostgreSQL](adrs/003-use-postgresql.md) und [ADR 004 – REST](adrs/004-use-rest-api.md). Die Schichtentrennung ist ein Architekturprinzip ohne konkurrierende ADR-Nummer.
