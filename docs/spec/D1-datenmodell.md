# D1 Datenmodell

## Ziel

Das Datenmodell beschreibt die wichtigsten Datenobjekte, die SyncUp für die Terminplanung, Kalenderabstimmung und Ressourcenbuchung benötigt.

## Zentrale Entitäten

### Benutzer

Ein Benutzer ist eine Person, die SyncUp verwendet.

Attribute:
- Benutzer-ID
- Name
- E-Mail-Adresse
- Passwort
- Rolle

### Kalender

Ein Kalender enthält Termine eines Benutzers oder eines Teams.

Attribute:
- Kalender-ID
- Name
- Besitzer
- Beschreibung

### Termin

Ein Termin beschreibt ein geplantes Ereignis.

Attribute:
- Termin-ID
- Titel
- Beschreibung
- Datum
- Startzeit
- Endzeit
- Status

### Terminabfrage

Eine Terminabfrage wird erstellt, um gemeinsame freie Zeitfenster zu finden.

Attribute:
- Abfrage-ID
- Titel
- Zeitraum
- Dauer
- eingeladene Benutzer
- Status

### Ressource

Eine Ressource ist zum Beispiel ein Besprechungsraum oder ein Arbeitsraum.

Attribute:
- Ressourcen-ID
- Name
- Typ
- Kapazität
- Verfügbarkeit

### Buchung

Eine Buchung verbindet einen Termin mit einer Ressource.

Attribute:
- Buchungs-ID
- Termin
- Ressource
- Zeitraum
- Status

## Beziehungen

- Ein Benutzer kann mehrere Kalender besitzen.
- Ein Kalender kann mehrere Termine enthalten.
- Ein Termin kann mehreren Benutzern zugeordnet sein.
- Eine Terminabfrage kann mehrere Benutzer enthalten.
- Eine Ressource kann für einen Termin gebucht werden.
- Eine Buchung gehört zu genau einem Termin und genau einer Ressource.

## Nicht anwendbare Punkte

Ein detailliertes Datentypenverzeichnis wird für M1 noch nicht vollständig ausgearbeitet. Die genaue technische Umsetzung der Datentypen erfolgt später im Rahmen der Architektur und Implementierung.