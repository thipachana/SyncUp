# F2 Anwendungsfälle

Stand: 23.09.2026

## Ziel

Dieser Abschnitt beschreibt die wichtigsten Anwendungsfälle von SyncUp.

Da sich einige Funktionen noch in der Entwicklung befinden, wird bei jedem Anwendungsfall angegeben, was geplant und was aktuell bereits umgesetzt ist.

## UC1 – Benutzer registrieren

**Akteur:** Benutzer

### Geplanter Ablauf

1. Der Benutzer öffnet die Registrierung.
2. Er gibt seinen Namen, seine E-Mail-Adresse und ein Passwort ein.
3. Die eingegebenen Daten werden geprüft.
4. Das Benutzerkonto wird erstellt.
5. Der Benutzer kann SyncUp verwenden.

### Aktueller Stand

Die Registrierung und Anmeldung sind umgesetzt.

Ein Datenmodell für Benutzer ist bereits vorhanden. Das Passwort wird bei API-Abfragen nicht ausgegeben.

Die Passwörter werden mit BCrypt gehasht. Persönliche Termine, Anfragen und Buchungen sind durch Anmeldung und Eigentumsprüfungen geschützt. Ein organisationsbezogenes Rollenmodell ist nicht umgesetzt.

---

## UC2 – Termin erstellen

**Akteur:** Organisator

### Geplanter Ablauf

1. Der Organisator erstellt einen neuen Termin.
2. Er gibt Titel, Datum und Uhrzeit ein.
3. Ein Kalender wird dem Termin zugeordnet.
4. Bei Bedarf werden Teilnehmer hinzugefügt.
5. Der Termin wird gespeichert.

### Aktueller Stand

Das Backend kann Termine bereits anlegen und abrufen. Die Termine werden in PostgreSQL gespeichert.

Eigene Termine können im Monatskalender angelegt, angezeigt und gelöscht werden. Teilnehmer werden in Terminanfragen ausgewählt. Terminbearbeitung und Einladungen sind noch offen.

---

## UC3 – Freie Zeitfenster finden

**Akteur:** Organisator

### Geplanter Ablauf

1. Der Organisator erstellt eine Terminanfrage.
2. Er gibt einen Zeitraum und die gewünschte Termindauer an.
3. Teilnehmer werden ausgewählt.
4. SyncUp berücksichtigt bereits vorhandene Termine.
5. Das System berechnet mögliche gemeinsame freie Zeitfenster.
6. Die Ergebnisse werden im Frontend angezeigt.

### Aktueller Stand

Freie Zeitfenster werden für die ausgewählten Teilnehmer und die gewünschte Dauer berechnet.

Das Backend kann vorhandene Termine berücksichtigen und freie Zeitfenster berechnen. Das Frontend kann die Berechnung aufrufen und die Ergebnisse anzeigen.

---

## UC4 – Ressource buchen

**Akteur:** Organisator

### Ablauf

1. Vorhandene Ressourcen werden angezeigt.
2. Der Benutzer wählt eine Ressource aus.
3. Ein eigener Termin wird ausgewählt und der vorbelegte Buchungszeitraum geprüft.
4. Die Buchungsanfrage wird an das Backend gesendet.
5. Das Backend prüft, ob für die Ressource bereits eine überschneidende Buchung vorhanden ist.
6. Wenn die Ressource im Zeitraum frei ist, wird die Buchung gespeichert.
7. Bei einer Überschneidung wird die Buchung abgelehnt.
8. Das Frontend zeigt eine entsprechende Meldung an.

### Aktueller Stand

Der grundlegende Buchungsablauf ist umgesetzt.

Überschneidende Buchungen derselben Ressource werden vom Backend erkannt. In diesem Fall antwortet das Backend mit dem HTTP-Status 409.

Noch nicht vollständig umgesetzt sind:

- Freigabe einer bereits gebuchten Ressource
- Vorschläge für alternative Ressourcen

---

## UC5 – Termin bearbeiten

**Akteur:** Organisator

### Geplanter Ablauf

1. Der Organisator öffnet einen vorhandenen Termin.
2. Er ändert die gewünschten Angaben.
3. Die Änderungen werden geprüft.
4. Der Termin wird gespeichert.
5. Betroffene Teilnehmer werden über die Änderung informiert.

### Aktueller Stand

Die vollständige Bearbeitung bestehender Termine über das Frontend ist noch nicht umgesetzt.

Auch automatische Benachrichtigungen der Teilnehmer sind noch nicht vorhanden.

---

## UC6 – Terminanfragen verwalten

**Akteur:** Benutzer

### Ablauf

1. Vorhandene Terminanfragen werden im Frontend angezeigt.
2. Der Benutzer kann eine neue Terminanfrage erstellen.
3. Dazu gibt er einen Titel sowie Beginn und Ende des Zeitraums ein.
4. Die Anfrage wird an das Backend gesendet.
5. Das Backend speichert die Terminanfrage.
6. Die neue Anfrage wird anschließend angezeigt.
7. Eine vorhandene Terminanfrage kann wieder gelöscht werden.

### Aktueller Stand

Das Erstellen, Anzeigen und Löschen von Terminanfragen ist bereits umgesetzt.

Teilnehmerwahl, separate Dauer und Abgleich der Teilnehmerkalender sind umgesetzt. Einladungen und automatische Terminbestätigung bleiben offen.

---

## Zusammenfassung des Umsetzungsstands

**Umgesetzt beziehungsweise teilweise umgesetzt:**

- Termine im Backend anlegen und abrufen
- Terminanfragen erstellen, anzeigen und löschen
- freie Zeitfenster berechnen und anzeigen
- Ressourcen anzeigen
- Ressourcen buchen
- Überschneidungen bei Ressourcenbuchungen verhindern
- Speicherung der Daten in PostgreSQL

**Noch nicht vollständig umgesetzt:**

- Einladungen und nachträgliche Änderung der Teilnehmer
- Bearbeiten bestehender Termine im Frontend
- Einladungen und Benachrichtigungen
- Aufgabenverwaltung
- Freigabe von Ressourcen
