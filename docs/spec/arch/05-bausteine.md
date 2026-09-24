# 5. Bausteinsicht

## Überblick

Die Architektur von SyncUp besteht aus drei Hauptkomponenten:

- Frontend
- Backend
- Datenbank

Jede Komponente übernimmt klar abgegrenzte Aufgaben.

Das Frontend stellt die Benutzeroberfläche bereit, das Backend verarbeitet die Geschäftslogik und die PostgreSQL-Datenbank übernimmt die dauerhafte Speicherung.

## Frontend

Das Frontend wird mit React entwickelt.

Es stellt die Benutzeroberfläche bereit und ermöglicht die Bedienung der Funktionen von SyncUp.

Zu den aktuell umgesetzten Bereichen gehören:

- Registrierung, Anmeldung und Abmeldung
- persönlicher Monatskalender
- private Termine erstellen, anzeigen, bearbeiten und löschen
- Terminanfragen erstellen und anzeigen
- Teilnehmer für Terminanfragen auswählen
- gewünschte Termindauer festlegen
- gemeinsame freie Zeitfenster anzeigen
- freien Zeitslot als gemeinsamen Termin übernehmen
- gemeinsame Termine im Kalender anzeigen
- vorhandene Räume anzeigen
- vorhandenen Termin für eine Raumreservierung auswählen
- zeitbezogene Raumverfügbarkeit anzeigen
- Räume reservieren
- gebuchte Räume im Kalender anzeigen
- Benachrichtigungen anzeigen und löschen
- verständliche Erfolgs- und Fehlermeldungen darstellen

Für Entwicklung und Build des Frontends wird Vite verwendet.

Das Frontend greift nicht direkt auf die Datenbank zu, sondern kommuniziert ausschließlich über REST-Schnittstellen mit dem Backend.

## Backend

Das Backend wird mit Spring Boot und Java 21 umgesetzt.

Es verarbeitet die Geschäftslogik und stellt REST-Schnittstellen für das Frontend bereit.

Zu den aktuell vorhandenen fachlichen Bereichen gehören:

- Registrierung und Anmeldung
- Sitzungsverwaltung
- CSRF-Schutz für schreibende Aufrufe
- Kalender- und Terminverwaltung
- Verarbeitung von Terminanfragen
- Berechnung gemeinsamer freier Zeitfenster
- Erstellung gemeinsamer Termine
- Erstellung und Verwaltung von Benachrichtigungen
- Verwaltung vorhandener Ressourcen
- Berechnung der zeitbezogenen Raumverfügbarkeit
- Ressourcenbuchungen
- Prüfung auf überschneidende Ressourcenbuchungen
- Prüfung, dass einem Termin höchstens ein Raum zugeordnet wird
- Entfernen einer zugehörigen Raumreservierung beim Löschen eines Termins

Das Backend stellt die maßgebliche Instanz für fachliche Prüfungen dar.

Insbesondere bei Raumreservierungen wird die Verfügbarkeit serverseitig geprüft. Dadurch kann die Geschäftslogik nicht ausschließlich durch das Verhalten der Benutzeroberfläche umgangen werden.

Das Backend ist hauptsächlich in folgende Bereiche aufgeteilt:

- Controller
- Services
- Repositories
- Entity-Klassen
- DTOs beziehungsweise Request-Objekte

### Controller

Controller stellen die REST-Endpunkte bereit und nehmen Anfragen des Frontends entgegen.

Sie übergeben fachliche Verarbeitung soweit vorgesehen an Services beziehungsweise greifen über Repositories auf benötigte Daten zu.

### Services

Services enthalten zentrale Geschäftslogik.

Dazu gehören beispielsweise:

- Erstellen und Ändern von Terminen
- Verarbeitung gemeinsamer Termine
- Benachrichtigungen
- Prüfungen fachlicher Regeln

### Repositories

Repositories bilden die Zugriffsschicht auf die PostgreSQL-Datenbank.

Sie basieren auf Spring Data JPA und stellen Datenbankoperationen für die jeweiligen Entity-Klassen bereit.

### Entity-Klassen

Die Entity-Klassen bilden die dauerhaft gespeicherten fachlichen Datenobjekte ab.

Dazu gehören unter anderem Benutzer, Kalender, Termine, Terminanfragen, Ressourcen, Buchungen und Benachrichtigungen.

## Datenbank

Die Daten werden dauerhaft in einer PostgreSQL-Datenbank gespeichert.

Zu den aktuell verwendeten Daten gehören unter anderem:

- Benutzer
- Kalender
- Termine
- Terminanfragen
- Ressourcen
- Buchungen
- Benachrichtigungen

Für den Zugriff auf die Datenbank verwendet das Backend Spring Data JPA und Hibernate.

Die Beziehungen zwischen den fachlichen Objekten werden über die Entity-Klassen abgebildet.

## Zusammenspiel der Bausteine

Das Frontend kommuniziert über REST-Schnittstellen mit dem Backend.

Das Backend verarbeitet die Anfragen, prüft die Geschäftsregeln und greift für die Speicherung auf die PostgreSQL-Datenbank zu.

Ein direkter Zugriff des Frontends auf die Datenbank findet nicht statt.

Ein typischer Ablauf ist:

1. Der Benutzer führt eine Aktion im React-Frontend aus.
2. Das Frontend sendet eine HTTP-Anfrage an einen REST-Endpunkt des Backends.
3. Das Backend prüft Anmeldung, Eingaben und fachliche Regeln.
4. Falls notwendig, liest oder verändert das Backend Daten über die Repository-Schicht.
5. PostgreSQL speichert beziehungsweise liefert die Daten.
6. Das Backend sendet das Ergebnis oder eine Fehlermeldung an das Frontend zurück.
7. Das Frontend aktualisiert die Darstellung für den Benutzer.

## Beispiel: Raumreservierung

Bei einer Raumreservierung arbeiten die Bausteine wie folgt zusammen:

1. Der Benutzer wählt im Frontend einen vorhandenen Termin aus.
2. Das Frontend fragt beim Backend die zeitbezogene Verfügbarkeit der Räume ab.
3. Das Backend prüft bereits gespeicherte Buchungen in der Datenbank.
4. Das Frontend zeigt freie Räume als „Verfügbar“ und belegte Räume als „Nicht verfügbar“ an.
5. Wählt der Benutzer einen freien Raum aus, sendet das Frontend eine Buchungsanfrage an das Backend.
6. Das Backend prüft die Buchungsregeln erneut.
7. Bei einer gültigen Buchung wird die Reservierung in PostgreSQL gespeichert.
8. Das Ergebnis wird an das Frontend zurückgegeben und dort angezeigt.

Durch diese Aufteilung bleibt die Geschäftslogik im Backend, während das Frontend für Darstellung und Bedienung zuständig ist.