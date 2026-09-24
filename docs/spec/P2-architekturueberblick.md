# P2 Architekturüberblick

Stand: 24.09.2026

## Ziel

Dieser Abschnitt gibt einen Überblick über den technischen Aufbau von SyncUp und zeigt, wie die wichtigsten Teile der Anwendung zusammenarbeiten.

Eine ausführlichere Beschreibung befindet sich in der separaten arc42-Architekturdokumentation.

## Systemstruktur

SyncUp besteht aus drei Hauptbereichen:

- **Frontend:** Benutzeroberfläche der Anwendung
- **Backend:** Verarbeitung der Anfragen und Geschäftslogik
- **Datenbank:** Speicherung der Daten

Das Frontend kommuniziert über REST-Schnittstellen mit dem Backend. Die Daten werden hauptsächlich im JSON-Format übertragen.

Das Backend greift über Spring Data JPA und Hibernate auf die PostgreSQL-Datenbank zu.

Das Frontend hat keinen direkten Zugriff auf die Datenbank.

## Frontend

Das Frontend wurde mit React und JavaScript/JSX entwickelt. Für Entwicklung und Build wird Vite verwendet.

Über das Frontend können unter anderem folgende Funktionen genutzt werden:

- Registrierung und Anmeldung
- persönlicher Monatskalender
- private Termine erstellen, anzeigen, bearbeiten und löschen
- Terminanfragen erstellen und anzeigen
- Teilnehmer auswählen
- gewünschte Termindauer angeben
- gemeinsame freie Zeitfenster berechnen und anzeigen
- freien Zeitslot als gemeinsamen Termin übernehmen
- vorhandene Räume anzeigen
- Räume für Termine reservieren
- gebuchten Raum im Kalender anzeigen
- Benachrichtigungen anzeigen

Bei einer Raumreservierung wird der passende Termin ausgewählt. Beginn und Ende der Buchung werden aus dem Termin übernommen und können nicht unabhängig verändert werden.

## Backend

Das Backend wurde mit Spring Boot umgesetzt. Das Projekt verwendet Java 21.

Der Aufbau besteht hauptsächlich aus:

- **Controllern:** nehmen Anfragen vom Frontend entgegen und stellen REST-Schnittstellen bereit
- **Services:** enthalten Teile der Geschäftslogik und Validierung
- **Repositories:** lesen und speichern Daten in der Datenbank
- **Entity-Klassen:** stellen die wichtigsten Datenobjekte der Anwendung dar

Das Backend ermöglicht unter anderem:

- Benutzer registrieren und anmelden
- persönliche Kalenderdaten laden
- Termine anlegen, bearbeiten, anzeigen und löschen
- Terminanfragen anlegen und anzeigen
- freie Zeitfenster berechnen
- gemeinsame Termine aus freien Zeitfenstern erzeugen
- Terminanfragen als erledigt markieren
- Ressourcen abrufen
- Ressourcenbuchungen speichern
- Überschneidungen bei Ressourcenbuchungen prüfen
- mehrere Räume für denselben Termin verhindern
- Benachrichtigungen für Teilnehmer gemeinsamer Termine erzeugen

Persönliche Daten werden durch Anmeldung und Eigentumsprüfungen geschützt.

Ein erweitertes Rollen- oder Administrationssystem ist nicht Bestandteil des aktuellen Funktionsumfangs.

## Datenbank

Für die Speicherung der Daten verwendet SyncUp PostgreSQL.

Gespeichert werden unter anderem:

- Benutzer
- Kalender
- Termine
- Terminanfragen
- Ressourcen
- Buchungen
- Benachrichtigungen

Benutzer können Terminanfragen und gemeinsamen Terminen zugeordnet werden.

Hibernate übernimmt während der Entwicklung die Abbildung der Entity-Klassen auf die Datenbanktabellen.

Das Datenmodell wird zusätzlich in D1 und D2 beschrieben.

## Zusammenspiel der Komponenten

### Gemeinsamen Termin finden

1. Der Benutzer erstellt im Frontend eine Terminanfrage.
2. Titel, Suchzeitraum, gewünschte Dauer und Teilnehmer werden an das Backend gesendet.
3. Die Terminanfrage wird in PostgreSQL gespeichert.
4. Das Backend berücksichtigt vorhandene Termine der beteiligten Benutzer.
5. Gemeinsame freie Zeitfenster werden berechnet.
6. Die Ergebnisse werden an das Frontend zurückgegeben.
7. Der Organisator wählt ein freies Zeitfenster aus.
8. Das Backend erzeugt daraus einen gemeinsamen Termin mit der angegebenen Dauer.
9. Der Termin wird bei den beteiligten Benutzern angezeigt.
10. Die Terminanfrage wird als erledigt markiert.
11. Für die Teilnehmer werden Benachrichtigungen erzeugt.

### Raum buchen

1. Das Frontend lädt die vorhandenen Räume.
2. Der Benutzer wählt einen Termin und einen Raum aus.
3. Beginn und Ende der Buchung werden aus dem Termin übernommen.
4. Die Buchungsanfrage wird an das Backend gesendet.
5. Das Backend prüft, ob der Raum im gleichen Zeitraum bereits gebucht ist.
6. Zusätzlich wird geprüft, ob für den Termin schon ein anderer Raum gebucht wurde.
7. Wenn keine dieser Regeln verletzt wird, wird die Buchung gespeichert.
8. Bei einem Konflikt wird die Buchung abgelehnt.
9. Das Frontend zeigt dem Benutzer eine passende Meldung an.
10. Der gebuchte Raum wird anschließend beim Termin im Kalender angezeigt.

## Lokale Ausführung

Frontend, Backend und Datenbank werden für das Projekt lokal ausgeführt.

Standardmäßig werden folgende Adressen beziehungsweise Ports verwendet:

- Frontend: `http://localhost:5173`
- Backend: `http://localhost:8080`
- PostgreSQL: `localhost:5432`

Die Adresse des Backends kann im Frontend über `VITE_API_URL` eingestellt werden.

## Gemeinsame Testumgebung mit Tailscale

Für gemeinsame Tests im Team wird Tailscale verwendet.

Frontend, Backend und PostgreSQL laufen dabei auf einem Rechner im Team. Die anderen Teammitglieder können über das gemeinsame Tailscale-Netzwerk auf die Anwendung zugreifen.

Tailscale dient dabei nur dazu, die lokal laufende Anwendung innerhalb des Teams erreichbar zu machen. Die Anwendung wird dadurch nicht öffentlich im Internet bereitgestellt.

Für die gemeinsame Testumgebung wird der lokale Vite-Server über Tailscale bereitgestellt. API-Anfragen werden vom Frontend an das lokal laufende Backend weitergeleitet.

Die Datenbank bleibt auf dem Host-Rechner und wird nicht direkt von den anderen Teammitgliedern angesprochen.

## Nicht Bestandteil des aktuellen Funktionsumfangs

Nicht umgesetzt beziehungsweise nicht Teil der aktuellen Version sind unter anderem:

- Aufgabenverwaltung
- Profil- und Passwortverwaltung
- erweitertes Rollen- und Administrationssystem
- automatische Vorschläge für alternative Räume
- Anbindung externer Kalenderdienste