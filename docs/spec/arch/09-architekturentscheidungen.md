# 9. Architekturentscheidungen

In diesem Kapitel werden die wichtigsten Architekturentscheidungen von SyncUp beschrieben.

Für jede Entscheidung werden der Hintergrund, mögliche Alternativen, die gewählte Lösung und die daraus entstehenden Folgen festgehalten.

## Architekturprinzip: Drei-Schichten-Architektur

### Kontext

SyncUp benötigt eine Benutzeroberfläche, eine zentrale Verarbeitung der Geschäftslogik und eine dauerhafte Speicherung der Daten.

### Alternativen

- Anwendung ohne klare Trennung der Bereiche
- direkter Zugriff des Frontends auf die Datenbank
- Trennung in Frontend, Backend und Datenbank

### Entscheidung

SyncUp verwendet eine Drei-Schichten-Architektur.

Die Anwendung besteht aus:

- React-Frontend
- Spring-Boot-Backend
- PostgreSQL-Datenbank

### Begründung

Durch die Trennung haben die einzelnen Bereiche klare Aufgaben.

Das Frontend übernimmt die Darstellung und Benutzereingaben, das Backend die Geschäftslogik und PostgreSQL die Speicherung der Daten.

Dadurch können die einzelnen Bereiche leichter unabhängig voneinander entwickelt und getestet werden.

### Konsequenzen

Die einzelnen Schichten müssen über festgelegte Schnittstellen miteinander kommunizieren.

Dadurch entsteht zusätzlicher Aufwand für die Konfiguration und Kommunikation zwischen Frontend und Backend.

---

## ADR-001: Spring Boot für das Backend

### Kontext

SyncUp benötigt ein Backend, das REST-Schnittstellen bereitstellt, Geschäftslogik verarbeitet und auf die Datenbank zugreift.

### Alternativen

- Node.js
- andere Java-Webframeworks
- Spring Boot

### Entscheidung

Das Backend wird mit Spring Boot und Java 21 umgesetzt.

### Begründung

Spring Boot bietet eine gute Unterstützung für REST-Schnittstellen, Datenbankzugriffe und die Strukturierung einer Webanwendung.

Außerdem kann Spring Data JPA direkt für den Zugriff auf PostgreSQL verwendet werden.

### Konsequenzen

Für die Ausführung des Backends wird Java 21 benötigt.

Das Backend verwendet die typische Aufteilung in Controller, Services, Repositories und Entity-Klassen.

---

## ADR-002: React mit Vite für das Frontend

### Kontext

SyncUp benötigt eine browserbasierte Benutzeroberfläche zur Darstellung und Bedienung der Funktionen.

### Alternativen

- serverseitig erzeugte Benutzeroberfläche
- Vue
- React

### Entscheidung

Das Frontend wird mit React umgesetzt.

Für Entwicklung und Build wird Vite verwendet.

### Begründung

React ermöglicht eine komponentenbasierte Entwicklung der Benutzeroberfläche.

Das Frontend kann dadurch unabhängig vom Spring-Boot-Backend entwickelt werden.

Vite stellt eine einfache Entwicklungs- und Build-Umgebung bereit.

### Konsequenzen

Für das Frontend werden Node.js und npm benötigt.

Für die Kommunikation mit dem Backend muss die API-Anbindung korrekt konfiguriert sein.

---

## ADR-003: PostgreSQL mit Spring Data JPA

### Kontext

Benutzer, Kalender, Termine, Terminanfragen, Ressourcen, Buchungen und Benachrichtigungen müssen dauerhaft gespeichert werden.

### Alternativen

- Speicherung ausschließlich im Arbeitsspeicher
- direkter Datenbankzugriff über JDBC
- relationale PostgreSQL-Datenbank mit Spring Data JPA

### Entscheidung

SyncUp verwendet PostgreSQL als relationale Datenbank.

Der Datenzugriff im Backend erfolgt über Spring Data JPA und Hibernate.

### Begründung

Die Daten von SyncUp besitzen viele Beziehungen untereinander.

Eine relationale Datenbank eignet sich deshalb gut für die benötigte Datenstruktur.

Spring Data JPA reduziert außerdem den notwendigen Code für viele grundlegende Datenbankoperationen.

### Konsequenzen

Für die lokale Ausführung muss PostgreSQL eingerichtet sein.

Änderungen an den Entity-Klassen können Auswirkungen auf das Datenbankschema haben.

---

## ADR-004: REST-Schnittstelle zwischen Frontend und Backend

### Kontext

Das React-Frontend benötigt eine Schnittstelle, um Daten vom Backend abzurufen und neue Daten an das Backend zu senden.

### Alternativen

- direkter Datenbankzugriff durch das Frontend
- GraphQL
- REST

### Entscheidung

Die Kommunikation zwischen Frontend und Backend erfolgt über REST-Schnittstellen.

Die Daten werden hauptsächlich im JSON-Format übertragen.

### Begründung

REST lässt sich mit Spring Boot einfach umsetzen und vom React-Frontend über HTTP-Anfragen verwenden.

Das Frontend bleibt dadurch von der Datenbank getrennt.

### Konsequenzen

Für die benötigten Funktionen müssen passende REST-Endpunkte im Backend vorhanden sein.

Außerdem muss die Kommunikation zwischen Frontend und Backend korrekt konfiguriert werden.

---

## ADR-005: Prüfung von Raumreservierungen im Backend

### Kontext

Ein Raum darf nicht gleichzeitig für mehrere sich überschneidende Termine reserviert werden.

Außerdem soll ein Termin nur einen Raum besitzen.

### Alternativen

- Prüfung nur im Frontend
- keine zentrale Prüfung
- Prüfung der Geschäftsregeln im Backend

### Entscheidung

Die Regeln für Raumreservierungen werden im Backend geprüft.

Vor dem Speichern wird geprüft:

- ob der ausgewählte Raum im Zeitraum des Termins bereits belegt ist,
- ob für den Termin bereits ein anderer Raum reserviert wurde.

Die Buchungszeit wird aus dem ausgewählten Termin übernommen.

### Begründung

Diese Regeln gehören zur Geschäftslogik und sollen unabhängig vom verwendeten Frontend gelten.

Eine Prüfung nur im Frontend könnte umgangen werden, zum Beispiel durch einen direkten Aufruf der REST-Schnittstelle.

### Konsequenzen

Ungültige oder überschneidende Buchungen werden vom Backend abgelehnt.

Das Frontend muss die Antwort verarbeiten und dem Benutzer eine passende Fehlermeldung anzeigen.

---

Die einzelnen Basisentscheidungen sind zusätzlich in folgenden ADR-Dateien dokumentiert:

- `adrs/001-use-spring-boot.md`
- `adrs/002-use-react.md`
- `adrs/003-use-postgresql.md`
- `adrs/004-use-rest-api.md`

Die Drei-Schichten-Architektur wird als übergeordnetes Architekturprinzip verwendet.

Die Prüfung der Raumreservierungen wird in diesem Kapitel als zusätzliche Architekturentscheidung beschrieben.