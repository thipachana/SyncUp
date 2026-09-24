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

Durch die Trennung besitzen die einzelnen Bereiche klar abgegrenzte Aufgaben.

Das Frontend übernimmt Darstellung und Benutzereingaben, das Backend die Geschäftslogik und PostgreSQL die dauerhafte Speicherung der Daten.

Dadurch können die einzelnen Bereiche weitgehend unabhängig voneinander entwickelt und getestet werden.

### Konsequenzen

Die einzelnen Schichten müssen über klar definierte Schnittstellen miteinander kommunizieren.

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

Spring Boot bietet Unterstützung für REST-Schnittstellen, Datenbankzugriffe und die Strukturierung einer Webanwendung.

Außerdem kann Spring Data JPA für den Zugriff auf PostgreSQL verwendet werden.

### Konsequenzen

Für die Ausführung des Backends wird Java 21 benötigt.

Das Backend verwendet eine Aufteilung in Controller, Services, Repositories und Entity-Klassen.

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

Die Daten von SyncUp besitzen mehrere Beziehungen untereinander.

Eine relationale Datenbank eignet sich deshalb für die benötigte Datenstruktur.

Spring Data JPA reduziert außerdem den notwendigen Code für grundlegende Datenbankoperationen.

### Konsequenzen

Für die lokale Ausführung muss PostgreSQL eingerichtet und erreichbar sein.

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

REST lässt sich mit Spring Boot direkt umsetzen und vom React-Frontend über HTTP-Anfragen verwenden.

Das Frontend bleibt dadurch von der Datenbank und deren technischer Struktur getrennt.

### Konsequenzen

Für die benötigten Funktionen müssen passende REST-Endpunkte im Backend vorhanden sein.

Das Frontend muss Fehlerantworten des Backends verarbeiten und verständlich darstellen.

---

## ADR-005: Serverzentrierte Konsistenzprüfung bei Raumreservierungen

### Kontext

Bei Raumreservierungen müssen mehrere fachliche Regeln eingehalten werden.

Ein Raum darf nicht für zwei sich zeitlich überschneidende Termine reserviert werden.

Gleichzeitig dürfen unterschiedliche Räume im gleichen Zeitraum weiterhin von unterschiedlichen Terminen genutzt werden.

Zusätzlich darf einem Termin höchstens ein Raum zugeordnet sein.

Da mehrere Benutzer gleichzeitig mit der Anwendung arbeiten können, darf sich die Konsistenz nicht ausschließlich auf den im Frontend angezeigten Zustand verlassen.

### Alternativen

- Prüfung ausschließlich im Frontend
- Prüfung ausschließlich beim Laden der Raumliste
- keine zentrale Konfliktprüfung
- Vorab-Anzeige im Frontend und erneute verbindliche Prüfung im Backend

### Entscheidung

Die Regeln für Raumreservierungen werden zentral im Backend durchgesetzt.

Nach Auswahl eines Termins fragt das Frontend die zeitbezogene Verfügbarkeit der vorhandenen Räume beim Backend ab.

Das Backend ermittelt für jeden Raum, ob im Zeitraum des ausgewählten Termins bereits eine überschneidende Buchung existiert.

Das Frontend verwendet diese Information zur Anzeige von:

- „Verfügbar“
- „Nicht verfügbar“

Vor dem tatsächlichen Speichern einer neuen Buchung führt das Backend die Prüfung erneut durch.

Dabei wird geprüft:

- ob die Ressource grundsätzlich verfügbar ist,
- ob der Raum im Zeitraum des Termins bereits durch eine überschneidende Buchung belegt ist,
- ob für den ausgewählten Termin bereits eine Raumreservierung existiert.

Beginn und Ende der Buchung werden aus dem ausgewählten Termin übernommen.

Direkt aufeinanderfolgende Zeiträume gelten nicht als Überschneidung.

### Begründung

Die Regeln für Raumreservierungen sind Teil der Geschäftslogik und müssen unabhängig vom Frontend eingehalten werden.

Eine ausschließliche Prüfung im Frontend könnte durch direkte REST-Aufrufe umgangen werden.

Außerdem kann sich der Buchungszustand zwischen Anzeige und tatsächlichem Buchungsversuch ändern, wenn mehrere Benutzer gleichzeitig arbeiten.

Die erneute serverseitige Prüfung vor dem Speichern stellt deshalb die maßgebliche Konsistenzprüfung dar.

### Konsequenzen

Das Frontend kann dem Benutzer bereits vor dem Buchungsversuch anzeigen, welche Räume im gewählten Zeitraum belegt sind.

Unterschiedliche freie Räume können im gleichen Zeitraum parallel genutzt werden.

Eine Doppelbelegung desselben Raumes wird vom Backend verhindert.

Für einen Termin kann höchstens eine Raumreservierung gespeichert werden.

Das Frontend muss Konfliktantworten des Backends verarbeiten und dem Benutzer eine verständliche Meldung anzeigen.

Wird ein Termin gelöscht, wird eine zugehörige Raumreservierung ebenfalls entfernt.

---

Die Architekturentscheidungen sind zusätzlich in einzelnen ADR-Dateien dokumentiert:

- `adrs/001-use-spring-boot.md`
- `adrs/002-use-react.md`
- `adrs/003-use-postgresql.md`
- `adrs/004-use-rest-api.md`
- `adrs/005-room-booking-consistency.md`

Die Drei-Schichten-Architektur wird als übergeordnetes Architekturprinzip verwendet.