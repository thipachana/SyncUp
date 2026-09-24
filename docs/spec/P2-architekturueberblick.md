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

- Registrierung, Anmeldung und Abmeldung
- persönlicher Monatskalender
- private Termine erstellen, anzeigen, bearbeiten und löschen
- Terminanfragen erstellen und anzeigen
- Teilnehmer auswählen
- gewünschte Termindauer angeben
- gemeinsame freie Zeitfenster berechnen und anzeigen
- freien Zeitslot als gemeinsamen Termin übernehmen
- vorhandene Räume anzeigen
- zeitbezogene Raumverfügbarkeit anzeigen
- Räume für bestehende Termine reservieren
- gebuchten Raum im Kalender anzeigen
- Benachrichtigungen anzeigen und löschen
- Erfolgs- und Fehlermeldungen darstellen

Bei einer Raumreservierung wird ein bestehender Termin ausgewählt.

Beginn und Ende der Buchung werden automatisch aus dem Termin übernommen und können nicht unabhängig verändert werden.

## Backend

Das Backend wurde mit Spring Boot umgesetzt. Das Projekt verwendet Java 21.

Der Aufbau besteht hauptsächlich aus:

- **Controllern:** nehmen Anfragen vom Frontend entgegen und stellen REST-Schnittstellen bereit
- **Services:** enthalten Geschäftslogik und Validierung
- **Repositories:** lesen und speichern Daten in der Datenbank
- **Entity-Klassen:** stellen die wichtigsten Datenobjekte der Anwendung dar

Das Backend ermöglicht unter anderem:

- Benutzer registrieren, anmelden und abmelden
- persönliche Kalenderdaten laden
- Termine anlegen, bearbeiten, anzeigen und löschen
- Terminanfragen anlegen und anzeigen
- freie Zeitfenster berechnen
- gemeinsame Termine aus freien Zeitfenstern erzeugen
- Terminanfragen als erledigt markieren
- Ressourcen abrufen
- zeitbezogene Raumverfügbarkeit berechnen
- Ressourcenbuchungen speichern
- Überschneidungen bei Ressourcenbuchungen prüfen
- mehrere Räume für denselben Termin verhindern
- parallele Nutzung unterschiedlicher freier Räume ermöglichen
- Benachrichtigungen für Teilnehmer gemeinsamer Termine erzeugen
- Benachrichtigungen löschen
- zugehörige Raumbuchungen beim Löschen eines Termins entfernen

Persönliche Daten werden durch Anmeldung und serverseitige Eigentums- beziehungsweise Zugriffsprüfungen geschützt.

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

Buchungen verknüpfen einen bestehenden Termin mit einer Ressource.

Hibernate übernimmt die Abbildung der Entity-Klassen auf die Datenbanktabellen.

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
9. Der Termin wird den beteiligten Benutzern zugeordnet und bei ihnen im Kalender angezeigt.
10. Für die Teilnehmer werden Benachrichtigungen erzeugt.
11. Die Terminanfrage wird als erledigt markiert.

### Raum buchen

1. Das Frontend lädt die vorhandenen Räume und relevanten Termine.
2. Der Benutzer wählt einen bestehenden Termin aus.
3. Beginn und Ende der Buchung werden automatisch aus dem Termin übernommen.
4. Das Frontend fragt beim Backend die zeitbezogene Verfügbarkeit der Räume für diesen Termin ab.
5. Das Backend prüft bereits gespeicherte Buchungen.
6. Freie Räume werden als „Verfügbar“ und belegte Räume als „Nicht verfügbar“ an das Frontend zurückgegeben.
7. Der Benutzer wählt einen verfügbaren Raum aus.
8. Das Frontend sendet die Buchungsanfrage an das Backend.
9. Das Backend prüft vor dem Speichern erneut:
   - ob die Ressource grundsätzlich verfügbar ist,
   - ob derselbe Raum im Zeitraum bereits überschneidend gebucht ist,
   - ob für den Termin bereits eine andere Raumreservierung existiert.
10. Wenn keine Regel verletzt wird, wird die Buchung gespeichert.
11. Bei einem Konflikt wird die Buchung abgelehnt.
12. Das Frontend zeigt dem Benutzer eine passende Meldung an.
13. Der gebuchte Raum wird anschließend beim Termin im Kalender angezeigt.

## Lokale Ausführung

Frontend, Backend und Datenbank können für das Projekt lokal auf einem Rechner ausgeführt werden.

Standardmäßig werden folgende Adressen beziehungsweise Ports verwendet:

- Frontend: `http://localhost:5173`
- Backend: `http://localhost:8080`
- PostgreSQL: `localhost:5432`

Das Frontend verwendet relative `/api`-Aufrufe beziehungsweise die konfigurierte API-Anbindung.

## Gemeinsame Testumgebung mit Tailscale

Für gemeinsame Tests im Team wird Tailscale verwendet.

Frontend, Backend und PostgreSQL laufen dabei auf einem Rechner im Team.

Die anderen Teammitglieder können über das gemeinsame Tailscale-Netzwerk auf dieselbe laufende Anwendung zugreifen.

Tailscale dient dabei nur dazu, die lokal laufende Anwendung innerhalb des Teams erreichbar zu machen.

Die Anwendung wird dadurch nicht öffentlich im Internet bereitgestellt.

Die Datenbank bleibt auf dem Host-Rechner und wird nicht direkt von den anderen Teammitgliedern angesprochen.

Alle Datenzugriffe erfolgen über Frontend und Backend.

## Nicht Bestandteil des aktuellen Funktionsumfangs

Nicht umgesetzt beziehungsweise nicht Teil der aktuellen Version sind unter anderem:

- Aufgabenverwaltung
- Profil- und Passwortverwaltung
- erweitertes Rollen- und Administrationssystem
- automatische Vorschläge für alternative Räume
- Anbindung externer Kalenderdienste