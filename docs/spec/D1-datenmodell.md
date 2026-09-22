# D1 Datenmodell

Stand: 22.09.2026

## Ziel

Das Datenmodell beschreibt die wichtigsten Daten, die SyncUp aktuell verwendet.

Die Daten werden in einer PostgreSQL-Datenbank gespeichert.

## Benutzer

Ein Benutzer stellt eine Person in SyncUp dar.

Wichtige Daten sind:

- Benutzer-ID
- Name
- E-Mail-Adresse
- Passwort
- Rolle

Das Passwort wird bei API-Abfragen nicht zurückgegeben.

Registrierung und Anmeldung sind umgesetzt. Passwörter werden mit BCrypt gehasht und nicht in API-Antworten ausgegeben. Eine weitergehende Zugriffskontrolle ist aktuell noch nicht umgesetzt.

## Kalender

Ein Kalender wird verwendet, um Termine zu verwalten.

Wichtige Daten sind:

- Kalender-ID
- Name
- Besitzer
- Beschreibung

Ein Benutzer kann einen oder mehrere Kalender besitzen.

## Termin

Ein Termin beschreibt einen festgelegten Zeitraum.

Wichtige Daten sind:

- Termin-ID
- Titel
- Beschreibung
- Datum
- Startzeit
- Endzeit
- Status
- Kalender

Termine werden unter anderem für die Berechnung freier Zeitfenster und für Ressourcenbuchungen verwendet.

## Terminanfrage

Eine Terminanfrage wird verwendet, um mögliche freie Zeitfenster zu finden.

Wichtige Daten sind:

- Terminanfrage-ID
- Titel
- Zeitraum
- Dauer
- Status
- zugeordnete Benutzer

Terminanfragen können über das Frontend erstellt, angezeigt und gelöscht werden.

## Ressource

Eine Ressource ist zum Beispiel ein Raum, der für einen Termin verwendet werden kann.

Wichtige Daten sind:

- Ressourcen-ID
- Name
- Typ
- Kapazität
- Verfügbarkeit

## Buchung

Eine Buchung verbindet einen Termin mit einer Ressource.

Wichtige Daten sind:

- Buchungs-ID
- Termin
- Ressource
- Zeitraum
- Status

Bei einer neuen Buchung prüft das Backend, ob für dieselbe Ressource bereits eine Buchung im gewünschten Zeitraum vorhanden ist.

## Beziehungen

Die wichtigsten Beziehungen zwischen den Daten sind:

- Benutzer können Kalender besitzen.
- Kalender enthalten Termine.
- Benutzer können Terminanfragen zugeordnet werden.
- Eine Buchung gehört zu einem Termin.
- Eine Buchung gehört zu einer Ressource.
- Eine Ressource kann mehrere Buchungen haben.

## Aktueller Stand

Die wichtigsten Datenobjekte für Termine, Terminanfragen, Ressourcen und Buchungen sind bereits im Backend vorhanden.

Einige ursprünglich geplante Funktionen, wie die vollständige Benutzerverwaltung und Teilnehmerverwaltung, sind noch nicht vollständig umgesetzt.

Die genauen technischen Datentypen und Felder werden zusätzlich in D2 beschrieben.