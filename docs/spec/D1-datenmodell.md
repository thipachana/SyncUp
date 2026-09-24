# D1 Datenmodell

Stand: 23.09.2026

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

Registrierung und Anmeldung sind umgesetzt. Passwörter werden mit BCrypt gehasht und nicht in API-Antworten ausgegeben. Anmeldung und Eigentumsprüfungen schützen persönliche Daten. Ein organisationsbezogenes Rollenmodell ist noch offen.

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

Eine Terminanfrage wird verwendet, um gemeinsame freie Zeitfenster für mehrere Benutzer zu finden.

Wichtige Daten sind:

- Terminanfrage-ID
- Titel
- Zeitraum
- Dauer
- Status
- Ersteller
- zugeordnete Benutzer

Terminanfragen können über das Frontend erstellt und angezeigt werden. Nach erfolgreicher Auswahl eines freien Zeitfensters wird die Terminanfrage als erledigt markiert.

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

Der Zeitraum einer Buchung wird aus dem zugehörigen Termin übernommen.
Bei einer neuen Buchung prüft das Backend, ob für dieselbe Ressource bereits eine zeitlich überlappende Buchung vorhanden ist.
Zusätzlich darf einem Termin nur eine Ressource zugeordnet werden.

## Beziehungen

Die wichtigsten Beziehungen zwischen den Daten sind:

- Benutzer können Kalender besitzen.
- Kalender enthalten Termine.
- Termine können mehreren Teilnehmern zugeordnet sein.
- Benutzer können Terminanfragen zugeordnet werden.
- Eine Terminanfrage besitzt einen Ersteller.
- Eine Buchung gehört zu einem Termin.
- Eine Buchung gehört zu einer Ressource.
- Eine Ressource kann mehrere Buchungen haben.
- Ein Termin kann höchstens eine Raumbuchung besitzen.

## Aktueller Stand

Die zentralen Datenobjekte Benutzer, Kalender, Termin, Terminanfrage, Ressource und Buchung sind im Backend umgesetzt.
Registrierung und Anmeldung sind vorhanden. Persönliche Kalender und Termine sind an den jeweiligen Benutzer gebunden.
Gemeinsame Termine können Teilnehmer besitzen und werden in den Kalendern der beteiligten Benutzer angezeigt.
Terminanfragen besitzen einen Ersteller sowie zugeordnete Teilnehmer. Nach erfolgreicher Terminwahl kann ihr Status auf „ERLEDIGT“ gesetzt werden.
Ressourcenbuchungen sind mit einem Termin und einer Ressource verknüpft. Überschneidende Buchungen derselben Ressource sowie mehrere Räume für denselben Termin werden verhindert.
Die genauen technischen Datentypen und Felder werden zusätzlich in D2 beschrieben.

## Eigentum und Bestandsdaten (23.09.2026)

Terminanfragen besitzen zusätzlich `ersteller_id` als Fremdschlüssel auf Benutzer. Bei neuen Anfragen ist der Ersteller gesetzt. Alte Anfragen ohne Ersteller werden keinem Benutzer automatisch zugeordnet und bleiben bei persönlichen Abfragen ausgeblendet; sie werden nicht gelöscht. Eine Zuordnung erfordert eine geprüfte Migration, andernfalls wird die Anfrage neu angelegt.

Das Benutzerfeld Passwort enthält ausschließlich den BCrypt-Hash neu registrierter Konten und wird nicht in API-Antworten ausgegeben. Personenbezogene Rückgaben werden auf den jeweiligen Zweck begrenzt. Persönliche Kalender und Termine bleiben ihren Besitzern zugeordnet.
