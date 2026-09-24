# F1 Geschäftsprozesse

Stand: 24.09.2026

## Ziel

Dieser Abschnitt beschreibt die wichtigsten fachlichen Abläufe von SyncUp.

SyncUp unterstützt Benutzer dabei, private Termine zu verwalten, gemeinsame Termine mit mehreren Teilnehmern zu planen und für gemeinsame Termine Räume zu reservieren.

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
10. Der gemeinsame Termin wird in den Kalendern der beteiligten Benutzer angezeigt.
11. Die Teilnehmer erhalten eine Benachrichtigung über den festgelegten Termin.
12. Die Terminanfrage wird anschließend als erledigt markiert.

### Aktueller Stand

Dieser Geschäftsprozess ist umgesetzt.

Registrierung und Anmeldung, Teilnehmerauswahl, Berechnung gemeinsamer freier Zeitfenster, Auswahl eines Zeitfensters, Erstellung des gemeinsamen Termins sowie Benachrichtigungen der Teilnehmer sind vorhanden.

Der erzeugte Termin besitzt die zuvor in der Terminanfrage festgelegte Dauer und nicht automatisch die gesamte Länge des angezeigten freien Zeitfensters.

## Geschäftsprozess 2: Raum reservieren

### Ablauf

1. Der Organisator legt einen gemeinsamen Termin fest.
2. Anschließend wird der Bereich zur Raumreservierung geöffnet beziehungsweise fokussiert.
3. Der zuvor erstellte Termin wird für die Buchung vorausgewählt.
4. Der Benutzer wählt einen vorhandenen Raum aus.
5. Beginn und Ende der Buchung werden automatisch aus dem zugehörigen Termin übernommen.
6. SyncUp prüft, ob der Raum während dieses Zeitraums bereits gebucht ist.
7. SyncUp prüft außerdem, ob für den Termin bereits ein anderer Raum gebucht wurde.
8. Ist der Raum verfügbar, wird die Buchung gespeichert.
9. Ist eine Überschneidung vorhanden oder besitzt der Termin bereits eine Raumbuchung, wird die Buchung abgelehnt.
10. Der gebuchte Raum wird anschließend beim Termin im Kalender angezeigt.

### Aktueller Stand

Dieser Geschäftsprozess ist umgesetzt.

Die im System vorhandenen Räume können ausgewählt und für Termine reserviert werden.

Die Buchungszeit wird aus dem Termin übernommen und kann nicht unabhängig vom Termin festgelegt werden.

Zeitlich überlappende Buchungen derselben Ressource werden verhindert. Außerdem kann einem Termin nur ein Raum zugeordnet werden.

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

Für private Termine gibt es keine Teilnehmerauswahl. Dadurch unterscheiden sie sich von gemeinsamen Terminen, die über eine Terminanfrage entstehen.

Eigene Termine können erstellt, angezeigt, bearbeitet und gelöscht werden.

## Beteiligte Rollen

### Organisator

Der Organisator erstellt eine Terminanfrage, wählt Teilnehmer aus, lässt gemeinsame freie Zeitfenster berechnen und legt einen gemeinsamen Termin fest.

Er kann anschließend einen Raum für den Termin reservieren.

### Teilnehmer

Teilnehmer sind Benutzer, die einer Terminanfrage beziehungsweise einem gemeinsamen Termin zugeordnet sind.

Gemeinsame Termine werden in ihren Kalendern angezeigt. Teilnehmer erhalten außerdem eine Benachrichtigung, wenn ein gemeinsamer Termin festgelegt wurde.

### Benutzer

Ein angemeldeter Benutzer besitzt einen persönlichen Kalender und kann darin private Termine verwalten.

## Nicht Bestandteil des finalen Funktionsumfangs

Folgende Funktionen gehören nicht zum aktuell umgesetzten Kernumfang:

- Anbindung externer Kalenderdienste
- Profil- und Passwortverwaltung
- Aufgabenverwaltung
- automatische Vorschläge alternativer Räume bei einer belegten Ressource

## Abgrenzung

SyncUp arbeitet mit den innerhalb der Anwendung gespeicherten Kalender-, Termin-, Benutzer- und Ressourcendaten.

Eine Verbindung zu externen Kalenderdiensten wie Google Calendar oder Microsoft Outlook gehört nicht zur aktuellen Version.

Zahlungsabwicklung, Rechnungsstellung und Warenwirtschaft sind ebenfalls nicht Bestandteil von SyncUp.