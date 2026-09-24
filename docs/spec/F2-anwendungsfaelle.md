# F2 Anwendungsfälle

Stand: 24.09.2026

## Ziel

Dieser Abschnitt beschreibt die wichtigsten Anwendungsfälle von SyncUp und den aktuellen Umsetzungsstand.

## UC1 – Benutzer registrieren

**Akteur:** Benutzer

### Ablauf

1. Der Benutzer öffnet die Registrierung.
2. Er gibt seinen Namen, seine E-Mail-Adresse und ein Passwort ein.
3. SyncUp prüft die eingegebenen Daten.
4. Das Benutzerkonto wird erstellt.
5. Für den Benutzer wird ein persönlicher Kalender angelegt.
6. Der Benutzer kann sich anmelden und SyncUp verwenden.

### Aktueller Stand

Implementiert.

Registrierung und Anmeldung sind vorhanden. Passwörter werden mit BCrypt gehasht und nicht in API-Antworten ausgegeben.

Persönliche Kalender-, Termin- und Terminanfragedaten sind an den angemeldeten Benutzer gebunden.

---

## UC2 – Privaten Termin erstellen

**Akteur:** Benutzer

### Ablauf

1. Der Benutzer öffnet „Mein Kalender“.
2. Er wählt einen Tag im Monatskalender aus.
3. Er gibt Titel, Beginn und Ende des Termins ein.
4. Optional ergänzt er eine Beschreibung.
5. SyncUp prüft die Eingaben.
6. Der Termin wird gespeichert.
7. Der Termin erscheint im persönlichen Kalender.

### Aktueller Stand

Implementiert.

Private Termine können über „Mein Kalender“ erstellt, angezeigt, bearbeitet und gelöscht werden.

Für private Termine gibt es keine Teilnehmerauswahl.

---

## UC3 – Freie Zeitfenster finden

**Akteur:** Organisator

### Ablauf

1. Der Organisator erstellt eine Terminanfrage.
2. Er gibt einen Titel, einen Suchzeitraum und die gewünschte Termindauer an.
3. Er wählt die gewünschten Teilnehmer aus.
4. Der Organisator selbst nimmt ebenfalls teil.
5. SyncUp berücksichtigt die bereits gespeicherten Termine der beteiligten Benutzer.
6. Das System berechnet gemeinsame freie Zeitfenster.
7. Die Ergebnisse werden im Frontend angezeigt.
8. Der Organisator wählt eines der vorgeschlagenen Zeitfenster aus.
9. SyncUp erstellt daraus einen gemeinsamen Termin mit der in der Terminanfrage angegebenen Dauer.
10. Der Termin wird in den Kalendern der beteiligten Benutzer angezeigt.
11. Die Terminanfrage wird als erledigt markiert.

### Aktueller Stand

Implementiert.

Freie Zeitfenster werden anhand der ausgewählten Teilnehmer, des Suchzeitraums und der gewünschten Dauer berechnet.

Vorhandene Termine der beteiligten Benutzer werden berücksichtigt.

Aus einem ausgewählten freien Zeitfenster wird ein gemeinsamer Termin erzeugt. Der Termin besitzt exakt die angegebene gewünschte Dauer und nicht automatisch die gesamte Länge des freien Zeitfensters.

---

## UC4 – Ressource buchen

**Akteur:** Organisator

### Ablauf

1. Der Organisator legt einen gemeinsamen Termin fest.
2. Anschließend wird der Bereich zur Raumreservierung geöffnet beziehungsweise fokussiert.
3. Der zuvor erstellte Termin wird vorausgewählt.
4. Der Benutzer wählt einen vorhandenen Raum aus.
5. Beginn und Ende der Buchung werden aus dem zugehörigen Termin übernommen.
6. Das Backend prüft, ob für dieselbe Ressource bereits eine zeitlich überlappende Buchung vorhanden ist.
7. Das Backend prüft außerdem, ob für den Termin bereits eine andere Ressource gebucht wurde.
8. Ist der Raum verfügbar, wird die Buchung gespeichert.
9. Bei einer Überschneidung oder einer bereits vorhandenen Raumbuchung wird die Buchung abgelehnt.
10. Der gebuchte Raum wird beim Termin im Kalender angezeigt.

### Aktueller Stand

Implementiert.

Überschneidende Buchungen derselben Ressource werden verhindert.

Einem Termin kann nur ein Raum zugeordnet werden.

Die Buchungszeit wird aus dem zugehörigen Termin übernommen.

Wird ein Termin gelöscht, wird auch die zugehörige Raumbuchung entfernt.

Nicht umgesetzt sind automatische Vorschläge alternativer Räume.

---

## UC5 – Termin bearbeiten

**Akteur:** Benutzer

### Ablauf

1. Der Benutzer öffnet einen eigenen Termin.
2. Er ändert die gewünschten Angaben.
3. SyncUp prüft die Änderungen.
4. Der Termin wird gespeichert.
5. Die aktualisierten Daten werden im Kalender angezeigt.

### Aktueller Stand

Implementiert.

Eigene Termine können über die Benutzeroberfläche bearbeitet werden.

Eine automatische Benachrichtigung aller Teilnehmer bei jeder nachträglichen Änderung ist nicht Bestandteil des aktuell nachgewiesenen Funktionsumfangs.

---

## UC6 – Terminanfragen verwalten

**Akteur:** Benutzer

### Ablauf

1. Vorhandene offene Terminanfragen werden im Frontend angezeigt.
2. Der Benutzer kann eine neue Terminanfrage erstellen.
3. Er gibt Titel, Suchzeitraum und gewünschte Dauer an.
4. Er wählt die gewünschten Teilnehmer aus.
5. Die Anfrage wird an das Backend gesendet und gespeichert.
6. Die neue Anfrage wird anschließend angezeigt.
7. Für die Anfrage können gemeinsame freie Zeitfenster berechnet werden.
8. Nach Auswahl eines freien Zeitfensters wird ein gemeinsamer Termin angelegt.
9. Die Terminanfrage wird anschließend als erledigt markiert.

### Aktueller Stand

Implementiert.

Terminanfragen können erstellt und angezeigt werden.

Teilnehmerauswahl, gewünschte Dauer, Abgleich der Teilnehmerkalender und Berechnung gemeinsamer freier Zeitfenster sind umgesetzt.

Nach erfolgreicher Terminwahl wird die Anfrage abgeschlossen.

---

## UC7 – Benachrichtigung über gemeinsamen Termin

**Akteur:** Teilnehmer

### Ablauf

1. Ein Organisator legt aus einer Terminanfrage einen gemeinsamen Termin fest.
2. SyncUp erstellt für die beteiligten Teilnehmer eine Benachrichtigung.
3. Die Teilnehmer können die Benachrichtigung in der Anwendung sehen.

### Aktueller Stand

Implementiert.

Teilnehmer gemeinsamer Termine erhalten eine Benachrichtigung über neu festgelegte Termine.

---

## Zusammenfassung des Umsetzungsstands

**Umgesetzt:**

- Registrierung und Anmeldung
- persönlicher Kalender
- private Termine erstellen
- private Termine bearbeiten und löschen
- Terminanfragen erstellen und anzeigen
- Teilnehmer auswählen
- gewünschte Termindauer festlegen
- gemeinsame freie Zeitfenster berechnen
- freien Zeitslot als gemeinsamen Termin übernehmen
- gemeinsame Termine bei Teilnehmern anzeigen
- Benachrichtigungen für gemeinsame Termine
- vorhandene Räume anzeigen
- Räume für Termine buchen
- überlappende Raumbelegungen verhindern
- mehrere Räume für denselben Termin verhindern
- gebuchten Raum im Kalender anzeigen
- Raumbuchung beim Löschen eines Termins entfernen
- Speicherung der Daten in PostgreSQL

**Nicht Bestandteil des aktuellen Funktionsumfangs:**

- automatische Vorschläge alternativer Räume
- Profil- und Passwortverwaltung
- Aufgabenverwaltung
- Anbindung externer Kalenderdienste