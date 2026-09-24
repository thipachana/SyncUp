# D1 Datenmodell

Stand: 24.09.2026

## Ziel

Das Datenmodell beschreibt die wichtigsten fachlichen Datenobjekte, die SyncUp aktuell verwendet.

Die Daten werden dauerhaft in einer PostgreSQL-Datenbank gespeichert.

## Benutzer

Ein Benutzer stellt eine Person in SyncUp dar.

Wichtige Daten sind:

- Benutzer-ID
- Name
- E-Mail-Adresse
- Passwort
- Rolle

Das Passwort wird nicht im Klartext gespeichert, sondern mit BCrypt gehasht.

Das Passwortfeld wird nicht über normale API-Antworten an das Frontend übertragen.

Registrierung und Anmeldung sind umgesetzt. Persönliche Daten werden dem jeweiligen Benutzer zugeordnet und serverseitig geschützt.

Ein erweitertes organisationsbezogenes Rollenmodell ist nicht Bestandteil des aktuellen Funktionsumfangs.

## Kalender

Ein Kalender wird verwendet, um Termine eines Benutzers zu verwalten.

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
- Teilnehmer

Private Termine gehören ausschließlich zum persönlichen Kalender eines Benutzers und besitzen keine Teilnehmerauswahl.

Gemeinsame Termine können mehreren Teilnehmern zugeordnet sein.

Termine werden unter anderem für die Berechnung freier Zeitfenster und für Ressourcenbuchungen verwendet.

## Terminanfrage

Eine Terminanfrage wird verwendet, um gemeinsame freie Zeitfenster für mehrere Benutzer zu finden.

Wichtige Daten sind:

- Terminanfrage-ID
- Titel
- Suchzeitraum
- gewünschte Dauer
- Status
- Ersteller
- zugeordnete Teilnehmer

Der Ersteller ist Teilnehmer seiner eigenen Terminanfrage.

Terminanfragen können über das Frontend erstellt und angezeigt werden.

Nach erfolgreicher Auswahl eines freien Zeitfensters wird ein gemeinsamer Termin erstellt und die Terminanfrage als erledigt markiert.

## Ressource

Eine Ressource ist ein buchbares Objekt innerhalb von SyncUp, zum Beispiel ein Raum.

Wichtige Daten sind:

- Ressourcen-ID
- Name
- Typ
- Kapazität
- Verfügbarkeit

Ressourcen werden zentral im System bereitgestellt und nicht von normalen Benutzern über die Oberfläche angelegt.

## Buchung

Eine Buchung verbindet einen bestehenden Termin mit einer Ressource.

Wichtige Daten sind:

- Buchungs-ID
- Termin
- Ressource
- Zeitraum
- Status

Beginn und Ende der Buchung werden aus dem zugehörigen Termin übernommen.

Bei einer neuen Buchung prüft das Backend:

- ob die Ressource grundsätzlich verfügbar ist,
- ob für dieselbe Ressource bereits eine zeitlich überschneidende Buchung existiert,
- ob für den Termin bereits eine andere Raumreservierung vorhanden ist.

Ein Termin kann höchstens eine Raumreservierung besitzen.

Unterschiedliche Ressourcen können im gleichen Zeitraum für unterschiedliche Termine verwendet werden, sofern keine Überschneidung derselben Ressource vorliegt.

Wird ein Termin gelöscht, wird die dazugehörige Raumreservierung ebenfalls entfernt.

## Benachrichtigung

Eine Benachrichtigung informiert einen Benutzer über ein relevantes Ereignis innerhalb von SyncUp.

Wichtige Daten sind:

- Benachrichtigungs-ID
- Empfänger
- Nachricht beziehungsweise Inhalt
- Zeitpunkt

Beim Erstellen eines gemeinsamen Termins werden Benachrichtigungen für die beteiligten Teilnehmer erzeugt.

Benachrichtigungen können über die Anwendung angezeigt und gelöscht werden.

## Beziehungen

Die wichtigsten Beziehungen zwischen den Daten sind:

- Benutzer können Kalender besitzen.
- Kalender enthalten Termine.
- Termine können mehreren Teilnehmern zugeordnet sein.
- Benutzer können Terminanfragen zugeordnet werden.
- Eine Terminanfrage besitzt einen Ersteller.
- Eine Buchung gehört zu genau einem Termin.
- Eine Buchung gehört zu genau einer Ressource.
- Eine Ressource kann mehrere Buchungen besitzen.
- Ein Termin kann höchstens eine Raumbuchung besitzen.
- Benachrichtigungen sind einem Benutzer als Empfänger zugeordnet.

## Aktueller Stand

Die zentralen Datenobjekte Benutzer, Kalender, Termin, Terminanfrage, Ressource, Buchung und Benachrichtigung sind im Backend umgesetzt.

Registrierung und Anmeldung sind vorhanden. Persönliche Kalender und private Termine sind an den jeweiligen Benutzer gebunden.

Gemeinsame Termine können Teilnehmer besitzen und werden bei den beteiligten Benutzern im Kalender angezeigt.

Terminanfragen besitzen einen Ersteller sowie zugeordnete Teilnehmer. Nach erfolgreicher Terminwahl kann ihr Status auf „ERLEDIGT“ gesetzt werden.

Ressourcenbuchungen sind mit einem Termin und einer Ressource verknüpft. Überschneidende Buchungen derselben Ressource sowie mehrere Räume für denselben Termin werden verhindert.

Benachrichtigungen werden bei neu erstellten gemeinsamen Terminen gespeichert und können vom jeweiligen Benutzer gelöscht werden.

Die genauen technischen Datentypen und Felder werden zusätzlich in D2 beschrieben.

## Bestandsdaten

Ältere Datensätze können von später ergänzten Feldern abweichen.

Beispielsweise besitzen ältere Terminanfragen gegebenenfalls keinen gesetzten Ersteller. Solche Datensätze werden nicht automatisch einem Benutzer zugeordnet.

Eine nachträgliche Zuordnung erfolgt nur über eine gezielte und geprüfte Migration beziehungsweise durch Neuanlage der betroffenen Daten.