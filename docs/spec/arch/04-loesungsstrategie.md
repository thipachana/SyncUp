# 4. Lösungsstrategie

## Architekturansatz

SyncUp wird als klassische Drei-Schichten-Architektur umgesetzt.

Die Anwendung besteht aus:

- Präsentationsschicht
- Anwendungsschicht
- Datenhaltungsschicht

Diese Trennung sorgt dafür, dass Benutzeroberfläche, Geschäftslogik und Datenspeicherung klar voneinander getrennt bleiben.

Fachliche Regeln werden zentral im Backend umgesetzt und nicht ausschließlich im Frontend geprüft.

## Präsentationsschicht

Die Präsentationsschicht wird mit React umgesetzt.

Sie stellt die Benutzeroberfläche bereit und verarbeitet die Eingaben der Benutzer.

Dazu gehören unter anderem:

- Registrierung, Anmeldung und Abmeldung
- persönlicher Kalender
- private Termine
- Terminanfragen
- Auswahl von Teilnehmern
- Eingabe der gewünschten Termindauer
- Anzeige gemeinsamer freier Zeitfenster
- Auswahl eines freien Zeitslots
- Anzeige gemeinsamer Termine
- Raumreservierungen
- Anzeige der zeitbezogenen Raumverfügbarkeit
- Anzeige gebuchter Räume im Kalender
- Benachrichtigungen anzeigen und löschen
- Darstellung von Erfolgs- und Fehlermeldungen

Für Entwicklung und Build des Frontends wird Vite verwendet.

Das Frontend kommuniziert mit dem Backend über REST-Schnittstellen und greift nicht direkt auf die Datenbank zu.

## Anwendungsschicht

Die Anwendungsschicht wird mit Spring Boot und Java 21 umgesetzt.

Sie verarbeitet die Geschäftslogik und stellt REST-Schnittstellen für das Frontend bereit.

Das Backend übernimmt unter anderem:

- Benutzerverwaltung und Anmeldung
- Sitzungsverwaltung
- Zugriffsschutz auf persönliche Daten
- Verwaltung von Kalendern und Terminen
- Verarbeitung von Terminanfragen
- Berechnung gemeinsamer freier Zeitfenster
- Erstellung gemeinsamer Termine
- Erstellung und Verwaltung von Benachrichtigungen
- Verwaltung vorhandener Ressourcen
- Berechnung der zeitbezogenen Raumverfügbarkeit
- Verarbeitung von Ressourcenbuchungen
- Prüfung auf Überschneidungen bei Raumreservierungen
- Prüfung, dass einem Termin höchstens ein Raum zugeordnet wird
- Entfernen einer zugehörigen Raumreservierung beim Löschen eines Termins

Die Geschäftslogik ist hauptsächlich auf Controller, Services und Repositories verteilt.

## Datenhaltungsschicht

Die Daten werden dauerhaft in einer PostgreSQL-Datenbank gespeichert.

Dazu gehören unter anderem:

- Benutzer
- Kalender
- Termine
- Terminanfragen
- Ressourcen
- Buchungen
- Benachrichtigungen

Für den Zugriff auf die Datenbank werden Spring Data JPA und Hibernate verwendet.

Die Beziehungen zwischen den fachlichen Objekten werden über die Entity-Klassen des Backends abgebildet.

## Kommunikation

Das Frontend kommuniziert über REST-Schnittstellen mit dem Backend.

Die Daten werden hauptsächlich im JSON-Format übertragen.

Das Frontend greift nicht direkt auf die PostgreSQL-Datenbank zu.

Alle Zugriffe auf Daten und Geschäftslogik laufen über das Backend.

Dadurch bleibt die Datenhaltung vom Frontend getrennt und fachliche Regeln können zentral serverseitig durchgesetzt werden.

## Umgang mit fachlichen Konflikten

Fachliche Konflikte werden im Backend geprüft.

Ein Beispiel ist die Raumreservierung:

- Das Frontend fragt zunächst die zeitbezogene Verfügbarkeit der Räume ab.
- Das Backend ermittelt diese anhand der bereits gespeicherten Buchungen.
- Vor dem eigentlichen Speichern einer Buchung prüft das Backend die Regeln erneut.
- Überschneidende Buchungen desselben Raumes werden verhindert.
- Unterschiedliche freie Räume können im gleichen Zeitraum parallel verwendet werden.
- Für einen Termin kann höchstens ein Raum reserviert werden.

Das Frontend stellt den vom Backend ermittelten Zustand dar und zeigt bei Konflikten verständliche Meldungen an.