# F1 Geschäftsprozesse

Stand: 22.09.2026

## Ziel

Dieser Abschnitt beschreibt die wichtigsten Abläufe von SyncUp.

SyncUp soll Teams dabei helfen, gemeinsame Termine zu finden und benötigte Ressourcen wie Räume zu reservieren.

Da noch nicht alle geplanten Funktionen umgesetzt sind, wird bei den einzelnen Geschäftsprozessen zwischen dem geplanten Ablauf und dem aktuellen Stand unterschieden.

## Geschäftsprozess 1: Gemeinsamen Termin planen

### Geplanter Ablauf

1. Ein Organisator erstellt eine Terminanfrage.
2. Er gibt einen Titel, einen Zeitraum und eine gewünschte Termindauer ein.
3. Die Teilnehmer werden ausgewählt.
4. SyncUp berücksichtigt die vorhandenen Termine der Teilnehmer.
5. Das System berechnet mögliche gemeinsame freie Zeitfenster.
6. Der Organisator wählt einen passenden Zeitpunkt aus.
7. Der Termin wird gespeichert.
8. Die Teilnehmer werden über den Termin informiert.

### Aktueller Stand

Terminanfragen können bereits über das Frontend erstellt, angezeigt und gelöscht werden.

Für eine Terminanfrage können freie Zeitfenster berechnet und anschließend im Frontend angezeigt werden. Bereits vorhandene Termine werden bei der Berechnung berücksichtigt.

Noch nicht vollständig umgesetzt sind:

- Anmeldung der Benutzer
- Teilnehmerauswahl im Frontend
- vollständiger Abgleich mehrerer Teilnehmer über die Oberfläche
- separate Eingabe der gewünschten Termindauer
- Auswahl eines freien Zeitfensters als neuer bestätigter Termin
- Einladungen und Benachrichtigungen

## Geschäftsprozess 2: Ressource reservieren

### Geplanter Ablauf

1. Der Benutzer wählt einen vorhandenen Termin aus.
2. Eine passende Ressource, zum Beispiel ein Raum, wird ausgewählt.
3. Der gewünschte Zeitraum wird angegeben.
4. SyncUp prüft, ob die Ressource in diesem Zeitraum bereits gebucht ist.
5. Wenn die Ressource frei ist, wird die Buchung gespeichert.
6. Wenn sie bereits gebucht ist, wird die Buchung abgelehnt.

### Aktueller Stand

Vorhandene Ressourcen können im Frontend angezeigt werden.

Für eine Buchung werden aktuell eine vorhandene Termin-ID und ein Zeitraum angegeben. Das Backend prüft anschließend, ob für dieselbe Ressource bereits eine Buchung im gewünschten Zeitraum vorhanden ist.

Wenn sich die Zeiträume überschneiden, lehnt das Backend die Buchung mit dem HTTP-Status 409 ab. Das Frontend zeigt dem Benutzer dazu eine Fehlermeldung an.

Noch nicht vollständig umgesetzt sind:

- Auswahl des Termins über eine Terminliste
- vollständige Prüfung der allgemeinen Ressourcenverfügbarkeit
- Vorschläge für alternative Ressourcen
- Freigabe bereits gebuchter Ressourcen

## Geschäftsprozess 3: Termin verwalten

### Geplanter Ablauf

1. Ein Benutzer öffnet einen vorhandenen Termin.
2. Die gewünschten Angaben werden geändert.
3. Die Änderungen werden gespeichert.
4. Die betroffenen Teilnehmer werden über Änderungen informiert.

### Aktueller Stand

Termine können im Backend bereits gespeichert und abgerufen werden.

Eine vollständige Bearbeitung bestehender Termine über das Frontend sowie automatische Benachrichtigungen sind aktuell noch nicht umgesetzt.

## Beteiligte Rollen

### Organisator

Der Organisator plant einen Termin beziehungsweise eine Terminanfrage und kann eine Ressource reservieren.

### Teilnehmer

Teilnehmer sollen an gemeinsamen Terminen beteiligt werden können.

Die vollständige Teilnehmerverwaltung ist aktuell noch nicht umgesetzt.

## Geplante Erweiterungen

Noch nicht vollständig umgesetzt sind insbesondere:

- Benutzeranmeldung und Registrierung
- Teilnehmerverwaltung
- Einladungen und Benachrichtigungen
- vollständige Terminverwaltung
- Aufgabenverwaltung
- Freigabe von Ressourcen

## Abgrenzung

SyncUp arbeitet aktuell mit den innerhalb der Anwendung gespeicherten Daten.

Eine Verbindung zu externen Kalenderdiensten wie Google Calendar oder Microsoft Outlook gehört nicht zur aktuellen Version.

Zahlungsabwicklung, Rechnungsstellung und Warenwirtschaft sind ebenfalls nicht Bestandteil von SyncUp.