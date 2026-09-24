## UC4 – Ressource buchen

**Akteur:** Angemeldeter Benutzer

**Ziel:** Für einen vorhandenen eigenen bzw. zugänglichen Termin wird ein verfügbarer Raum reserviert.

### Vorbedingungen

- Der Benutzer ist angemeldet.
- Der Termin ist gespeichert und für den Benutzer zugänglich.
- Mindestens eine Ressource ist im System vorhanden.
- Der Termin besitzt einen gültigen Beginn und ein gültiges Ende.

### Ablauf

1. Der Benutzer öffnet den Bereich „Raum reservieren“.
2. Er wählt einen seiner vorhandenen Termine aus.
3. SyncUp übernimmt Datum, Beginn und Ende automatisch aus dem ausgewählten Termin.
4. Das Backend prüft für diesen Zeitraum die Verfügbarkeit aller vorhandenen Räume.
5. Bereits durch andere Termine belegte Räume werden in der Oberfläche als „Nicht verfügbar“ angezeigt und können nicht ausgewählt werden.
6. Freie Räume werden als „Verfügbar“ angezeigt.
7. Der Benutzer wählt einen verfügbaren Raum aus.
8. Das Backend prüft vor dem Speichern erneut, ob der Raum im Zeitraum des Termins noch frei ist.
9. Zusätzlich wird geprüft, ob für den ausgewählten Termin bereits ein Raum reserviert wurde.
10. Bei erfolgreicher Prüfung wird die Buchung gespeichert und dem Termin sowie der Ressource zugeordnet.
11. Die Oberfläche bestätigt die erfolgreiche Reservierung und aktualisiert den Buchungszustand.

### Fehler- und Alternativfälle

- Der Termin existiert nicht oder ist für den Benutzer nicht zugänglich.
- Die Ressource existiert nicht.
- Die Ressource ist grundsätzlich nicht verfügbar.
- Der gewünschte Raum ist im Zeitraum des Termins bereits durch eine andere Buchung belegt.
- Für den ausgewählten Termin wurde bereits ein Raum reserviert.
- Zwischen Anzeige und Buchungsversuch wurde der Raum durch einen anderen Benutzer reserviert.

In diesen Fällen wird keine zusätzliche Buchung gespeichert. Der Benutzer erhält eine verständliche Fehlermeldung.

### Nachbedingungen

Bei erfolgreicher Buchung ist genau eine Ressource dem ausgewählten Termin zugeordnet. Andere Termine dürfen denselben Raum nur außerhalb eines sich überschneidenden Zeitraums reservieren.

### Abnahmekriterien

- Die Oberfläche zeigt für den ausgewählten Termin die tatsächliche zeitbezogene Verfügbarkeit der Räume an.
- Ein bereits belegter Raum wird als „Nicht verfügbar“ dargestellt.
- Ein freier Raum kann reserviert werden.
- Zwei verschiedene Räume können für zwei verschiedene Termine im selben Zeitraum reserviert werden.
- Derselbe Raum kann nicht für zwei zeitlich überlappende Termine reserviert werden.
- Direkt aufeinanderfolgende Buchungen, beispielsweise 14:00–15:00 Uhr und 15:00–16:00 Uhr, gelten nicht als Überschneidung.
- Für einen Termin kann höchstens ein Raum reserviert werden.
- Die endgültige Konfliktprüfung erfolgt im Backend, auch wenn die Oberfläche einen Raum zuvor als verfügbar angezeigt hat.
- Nach erfolgreicher Reservierung wird der neue Buchungszustand in der Oberfläche berücksichtigt.

### Aktueller Umsetzungsstand

Implementiert. Der Benutzer wählt einen vorhandenen Termin aus; Datum und Zeitraum werden aus diesem Termin übernommen.

Die zeitbezogene Raumverfügbarkeit wird im Backend anhand bereits gespeicherter Buchungen berechnet und von der Oberfläche als „Verfügbar“ beziehungsweise „Nicht verfügbar“ dargestellt.

Vor dem Speichern einer Buchung prüft das Backend erneut auf Überschneidungen. Dadurch wird verhindert, dass derselbe Raum für zwei zeitlich überlappende Termine reserviert wird.

Für jeden Termin ist höchstens eine Raumreservierung zulässig. Andere freie Räume können während desselben Zeitraums weiterhin von anderen Terminen gebucht werden.

Die beschriebenen Fälle wurden mit mehreren Benutzern und parallelen Terminen manuell getestet.