# P2 Architekturüberblick

Stand: 22.09.2026

## Ziel

Dieser Abschnitt gibt einen Überblick über den technischen Aufbau von SyncUp und zeigt, wie die wichtigsten Teile des Systems zusammenarbeiten.

Eine ausführlichere Beschreibung der Architektur befindet sich in der separaten arc42-Dokumentation.

## Systemstruktur

SyncUp besteht aus drei Hauptbereichen:

- **Frontend:** Benutzeroberfläche der Anwendung
- **Backend:** Verarbeitung der Anfragen und Geschäftslogik
- **Datenbank:** Speicherung der Daten

Das Frontend kommuniziert über REST-Schnittstellen mit dem Backend. Die Daten werden dabei im JSON-Format übertragen.

Das Backend greift über Spring Data JPA und Hibernate auf die PostgreSQL-Datenbank zu.

Das Frontend hat keinen direkten Zugriff auf die Datenbank.

## Frontend

Das Frontend wurde mit React und JavaScript/JSX entwickelt. Für die Entwicklungsumgebung und den Build wird Vite verwendet.

Aktuell können über das Frontend unter anderem folgende Funktionen genutzt werden:

- Terminanfragen erstellen, anzeigen und löschen
- freie Zeitfenster berechnen und anzeigen
- vorhandene Ressourcen anzeigen
- Ressourcen für einen vorhandenen Termin buchen
- Rückmeldungen bei erfolgreichen oder nicht möglichen Buchungen anzeigen

Für eine Ressourcenbuchung wird aktuell die ID eines vorhandenen Termins eingegeben. Eine direkte Auswahl aus einer Terminliste gibt es noch nicht.

Noch nicht vollständig umgesetzt sind unter anderem:

- Anmeldung und Registrierung
- Dashboard
- vollständige Kalender- und Terminverwaltung
- Teilnehmerauswahl
- Aufgabenverwaltung
- Einladungen und Benachrichtigungen

## Backend

Das Backend wurde mit Spring Boot umgesetzt. Das Projekt verwendet Java 21 als Zielversion.

Der Aufbau besteht hauptsächlich aus:

- **Controllern:** Verarbeiten Anfragen des Frontends und stellen die REST-Schnittstellen bereit.
- **Repositories:** Werden verwendet, um Daten aus der Datenbank zu lesen und zu speichern.
- **Entity-Klassen:** Stellen die Datenobjekte der Anwendung dar.

Eine eigene Service-Schicht ist aktuell noch nicht vorhanden. Ein Teil der Geschäftslogik befindet sich deshalb direkt in den Controllern.

Das Backend ermöglicht aktuell unter anderem:

- Termine anlegen und abrufen
- Terminanfragen anlegen, abrufen und löschen
- freie Zeitfenster berechnen
- Ressourcen anlegen und abrufen
- Ressourcenbuchungen anlegen und abrufen
- Überschneidungen bei Ressourcenbuchungen prüfen

Benutzer und Kalender sind als Datenmodelle vorhanden. Registrierung und Anmeldung sind umgesetzt. Eine weitergehende Zugriffskontrolle ist noch nicht umgesetzt.

## Datenbank

Für die Speicherung der Daten verwendet SyncUp PostgreSQL.

Aktuell werden unter anderem folgende Daten gespeichert:

- Benutzer
- Kalender
- Termine
- Terminanfragen
- Ressourcen
- Buchungen

Benutzer können außerdem Terminanfragen zugeordnet werden.

Für die geplante Aufgabenverwaltung gibt es aktuell noch kein vollständiges Datenmodell.

Hibernate erstellt beziehungsweise aktualisiert die benötigten Tabellen während der Entwicklung anhand der Entity-Klassen.

Das Datenmodell wird zusätzlich in D1 und D2 beschrieben.

## Zusammenspiel der Komponenten

### Freie Zeitfenster berechnen

1. Eine Terminanfrage wird über das Frontend erstellt.
2. Das Frontend sendet die Daten an das Backend.
3. Die Terminanfrage wird in PostgreSQL gespeichert.
4. Die Berechnung der freien Zeitfenster wird angefordert.
5. Das Backend berücksichtigt bereits vorhandene Termine.
6. Mögliche freie Zeitfenster werden berechnet.
7. Das Ergebnis wird an das Frontend zurückgegeben und dort angezeigt.

Die vollständige Teilnehmerauswahl ist aktuell noch nicht über das Frontend umgesetzt.

### Ressource buchen

1. Das Frontend lädt die vorhandenen Ressourcen.
2. Der Benutzer wählt eine Ressource aus und gibt eine Termin-ID sowie einen Zeitraum an.
3. Die Buchungsanfrage wird an das Backend geschickt.
4. Das Backend prüft, ob sich der Zeitraum mit einer vorhandenen Buchung derselben Ressource überschneidet.
5. Wenn keine Überschneidung besteht, wird die Buchung gespeichert.
6. Bei einer Überschneidung lehnt das Backend die Buchung mit dem HTTP-Status 409 ab.
7. Das Frontend zeigt eine entsprechende Meldung an.

## Lokale Ausführung

Frontend, Backend und Datenbank werden während der Entwicklung lokal ausgeführt.

Standardmäßig werden folgende Adressen beziehungsweise Ports verwendet:

- Frontend: `http://localhost:5173`
- Backend: `http://localhost:8080`
- PostgreSQL: `localhost:5432`

Die Adresse des Backends kann im Frontend über `VITE_API_URL` eingestellt werden.

Weitere Informationen zur Installation und zum Start der Anwendung befinden sich in S3 „Inbetriebnahme“ und in `INSTALL.md`.

## Geplante Erweiterungen

Einige ursprünglich geplante Funktionen sind aktuell noch nicht vollständig umgesetzt.

Dazu gehören insbesondere:

- Anmeldung und Registrierung
- Zugriffskontrolle
- vollständige Kalender- und Terminverwaltung
- Teilnehmerverwaltung
- Aufgabenverwaltung
- Einladungen und Benachrichtigungen
- Anbindung externer Kalenderdienste

Diese Funktionen können in einer späteren Version von SyncUp ergänzt werden.