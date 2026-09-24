# F1 Geschäftsprozesse

Stand: 24.09.2026

## Ziel

Dieser Abschnitt beschreibt die wichtigsten fachlichen Abläufe von SyncUp.

SyncUp unterstützt Benutzer dabei, private Termine zu verwalten, gemeinsame Termine mit mehreren Teilnehmern zu planen und für bestehende Termine Räume zu reservieren.

## Geschäftsprozess 1: Gemeinsamen Termin planen

### Ablauf

1. Der Organisator meldet sich bei SyncUp an.
2. Er erstellt eine Terminanfrage.
3. Er gibt einen Titel, einen Suchzeitraum und die gewünschte Termindauer ein.
4. Er wählt die gewünschten Teilnehmer aus.
5. Der Organisator selbst nimmt ebenfalls an der Terminanfrage teil.
6. SyncUp berücksichtigt die bereits gespeicherten Termine der beteiligten Benutzer.
7. Das System berechnet gemeinsame freie Zeitfenster.
8. Der Organisator wählt eines der vorgeschlagenen Zeitfenster aus.
9. SyncUp erstellt daraus einen gemeinsamen Termin mit exakt der in der Terminanfrage angegebenen Dauer.
10. Der gemeinsame Termin wird bei den beteiligten Benutzern im Kalender angezeigt.
11. Die Teilnehmer erhalten eine Benachrichtigung über den festgelegten Termin.
12. Die Terminanfrage wird anschließend als erledigt markiert.

### Aktueller Stand

Dieser Geschäftsprozess ist umgesetzt.

Registrierung und Anmeldung, Teilnehmerauswahl, Berechnung gemeinsamer freier Zeitfenster, Auswahl eines Zeitfensters, Erstellung des gemeinsamen Termins sowie Benachrichtigungen der Teilnehmer sind vorhanden.

Der erzeugte Termin besitzt die zuvor in der Terminanfrage festgelegte Dauer und nicht automatisch die gesamte Länge des angezeigten freien Zeitfensters.

## Geschäftsprozess 2: Raum reservieren

### Ablauf

1. Es existiert bereits ein Termin, für den ein Raum reserviert werden soll.
2. Der Benutzer öffnet den Bereich zur Raumreservierung.
3. Er wählt den gewünschten Termin aus.
4. Datum, Beginn und Ende der Buchung werden automatisch aus dem Termin übernommen.
5. SyncUp ermittelt die zeitbezogene Verfügbarkeit der vorhandenen Räume.
6. Bereits belegte Räume werden als „Nicht verfügbar“ angezeigt.
7. Andere freie Räume bleiben im gleichen Zeitraum weiterhin als „Verfügbar“ auswählbar.
8. Der Benutzer wählt einen verfügbaren Raum aus.
9. Vor dem Speichern prüft das Backend erneut:
   - ob die Ressource grundsätzlich verfügbar ist,
   - ob für denselben Raum eine zeitlich überschneidende Buchung existiert,
   - ob für den Termin bereits eine andere Raumreservierung existiert.
10. Wird keine Regel verletzt, wird die Buchung gespeichert.
11. Bei einem Konflikt wird die Buchung abgelehnt und eine passende Fehlermeldung angezeigt.
12. Der gebuchte Raum wird anschließend beim Termin im Kalender angezeigt.

### Aktueller Stand

Dieser Geschäftsprozess ist umgesetzt.

Die im System vorhandenen Räume können für bestehende Termine reserviert werden.

Die Buchungszeit wird automatisch aus dem Termin übernommen und kann nicht unabhängig vom Termin festgelegt werden.

Zeitlich überschneidende Buchungen desselben Raumes werden verhindert.

Unterschiedliche freie Räume können im gleichen Zeitraum für unterschiedliche Termine reserviert werden.

Für einen Termin kann höchstens ein Raum reserviert werden.

Die Verfügbarkeit wird zunächst für die Anzeige im Frontend ermittelt und vor dem Speichern im Backend erneut geprüft.

Wird ein Termin gelöscht, wird auch die zugehörige Raumbuchung entfernt.

## Geschäftsprozess 3: Privaten Termin verwalten

### Ablauf

1. Der Benutzer öffnet „Mein Kalender“.
2. Er wählt einen Tag aus.
3. Er gibt Titel, Beginn und Ende des Termins ein.
4. Optional kann eine Beschreibung ergänzt werden.
5. Der Benutzer speichert den Termin.
6. Der Termin wird in seinem persönlichen Kalender angezeigt.
7. Ein bestehender eigener Termin kann bearbeitet oder gelöscht werden.

### Aktueller Stand

Dieser Geschäftsprozess ist umgesetzt.

Private Termine werden dem persönlichen Kalender des angemeldeten Benutzers zugeordnet.

Für private Termine gibt es keine Teilnehmerauswahl.

Dadurch unterscheiden sie sich von gemeinsamen Terminen, die über eine Terminanfrage entstehen.

Eigene Termine können erstellt, angezeigt, bearbeitet und gelöscht werden.

## Beteiligte Rollen

### Organisator

Der Organisator erstellt eine Terminanfrage, wählt Teilnehmer aus, lässt gemeinsame freie Zeitfenster berechnen und legt einen gemeinsamen Termin fest.

Er kann anschließend einen Raum für einen bestehenden Termin reservieren.

### Teilnehmer

Teilnehmer sind Benutzer, die einer Terminanfrage beziehungsweise einem gemeinsamen Termin zugeordnet sind.

Gemeinsame Termine werden in ihren Kalendern angezeigt.

Teilnehmer erhalten außerdem eine Benachrichtigung, wenn ein gemeinsamer Termin festgelegt wurde.

### Benutzer

Ein angemeldeter Benutzer besitzt einen persönlichen Kalender und kann darin private Termine verwalten.

Er kann außerdem für für ihn relevante Termine vorhandene Räume reservieren.

## Nicht Bestandteil des finalen Funktionsumfangs

Folgende Funktionen gehören nicht zum aktuell umgesetzten Kernumfang:

- Anbindung externer Kalenderdienste
- Profil- und Passwortverwaltung
- Aufgabenverwaltung
- erweitertes Rollen- und Administrationssystem
- automatische Vorschläge alternativer Räume bei einer belegten Ressource

## Abgrenzung

SyncUp arbeitet mit den innerhalb der Anwendung gespeicherten Kalender-, Termin-, Benutzer- und Ressourcendaten.

Eine Verbindung zu externen Kalenderdiensten wie Google Calendar oder Microsoft Outlook gehört nicht zur aktuellen Version.

Zahlungsabwicklung, Rechnungsstellung und Warenwirtschaft sind ebenfalls nicht Bestandteil von SyncUp.