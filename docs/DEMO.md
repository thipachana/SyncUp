# SyncUp – Demo

Stand: 24.09.2026

Diese Demo zeigt die wichtigsten Funktionen von SyncUp anhand eines einfachen Testablaufs mit mehreren Benutzern.

## Vorbereitung

1. PostgreSQL starten.
2. Backend starten.
3. Frontend starten.
4. `http://localhost:5173` im Browser öffnen.
5. Für die Demo ausschließlich Testdaten verwenden.

## Demo-Ablauf

1. Zwei Testkonten registrieren, zum Beispiel Benutzer A und Benutzer B.

2. Mit Benutzer B einen privaten Termin anlegen, zum Beispiel:

   - Datum: gleicher Testtag
   - Uhrzeit: 09:00 bis 10:00 Uhr

3. Benutzer B abmelden.

4. Mit Benutzer A anmelden.

5. Mit Benutzer A einen privaten Termin am gleichen Tag von 10:00 bis 11:00 Uhr anlegen.

6. Eine neue Terminanfrage erstellen:

   - Titel eingeben
   - Suchzeitraum von 09:00 bis 13:00 Uhr festlegen
   - gewünschte Dauer auf 60 Minuten setzen
   - Benutzer B als Teilnehmer auswählen

7. Die gemeinsamen freien Zeitfenster berechnen lassen.

8. Aufgrund der vorhandenen Termine sollte als gemeinsamer freier Zeitraum ab 11:00 Uhr ein passendes Zeitfenster angezeigt werden.

9. Einen freien Zeitslot auswählen.

10. SyncUp erstellt daraus einen gemeinsamen Termin mit der festgelegten Dauer von 60 Minuten.

11. Prüfen, ob der gemeinsame Termin im Kalender von Benutzer A angezeigt wird.

12. Den Bereich „Raum reservieren“ öffnen.

13. Den zuvor erstellten gemeinsamen Termin auswählen.

14. Prüfen, ob:

   - Datum und Uhrzeit automatisch aus dem Termin übernommen werden,
   - vorhandene Räume angezeigt werden,
   - freie Räume als „Verfügbar“ angezeigt werden,
   - belegte Räume als „Nicht verfügbar“ angezeigt werden.

15. Einen verfügbaren Raum reservieren.

16. Prüfen, ob:

   - die Reservierung erfolgreich gespeichert wird,
   - für denselben Termin kein zweiter Raum reserviert werden kann,
   - der gebuchte Raum beim Termin im Kalender angezeigt wird.

17. Benutzer A abmelden.

18. Mit Benutzer B anmelden.

19. Prüfen, ob:

   - der gemeinsame Termin im Kalender angezeigt wird,
   - der gebuchte Raum beim Termin sichtbar ist,
   - eine Benachrichtigung zum neu erstellten gemeinsamen Termin vorhanden ist.

## Zusätzlicher Test – parallele Raumreservierung

20. Benutzer A erstellt einen weiteren Termin, zum Beispiel von 14:00 bis 15:00 Uhr, und reserviert dafür einen Raum.

21. Benutzer B erstellt einen anderen Termin ebenfalls von 14:00 bis 15:00 Uhr.

22. Benutzer B öffnet die Raumreservierung und wählt seinen Termin aus.

23. Prüfen, ob:

   - der von Benutzer A bereits reservierte Raum als „Nicht verfügbar“ angezeigt wird,
   - andere freie Räume weiterhin als „Verfügbar“ angezeigt werden,
   - Benutzer B einen anderen freien Raum im gleichen Zeitraum reservieren kann.

24. Zusätzlich prüfen, dass derselbe Raum nicht für zwei sich überschneidende Termine reserviert werden kann.

## Löschen eines Termins mit Raumreservierung

25. Einen Termin auswählen, für den bereits ein Raum reserviert wurde.

26. Den Termin löschen.

27. Prüfen, ob:

   - der Termin aus dem Kalender entfernt wird,
   - die zugehörige Raumreservierung ebenfalls gelöscht wird,
   - der Raum anschließend wieder für andere Termine verfügbar ist.

## Erwartetes Ergebnis

Am Ende der Demo sollte gezeigt worden sein, dass:

- Benutzer sich registrieren, anmelden und abmelden können,
- private Termine erstellt und verwaltet werden können,
- private Termine keine Teilnehmerauswahl besitzen,
- Terminanfragen mit Teilnehmern erstellt werden können,
- gemeinsame freie Zeitfenster anhand vorhandener Termine berechnet werden,
- aus einem freien Zeitfenster ein gemeinsamer Termin entsteht,
- die gewünschte Termindauer eingehalten wird,
- gemeinsame Termine bei den beteiligten Benutzern angezeigt werden,
- Teilnehmer Benachrichtigungen zu gemeinsamen Terminen erhalten,
- vorhandene Räume für Termine reserviert werden können,
- die zeitbezogene Raumverfügbarkeit angezeigt wird,
- belegte Räume nicht erneut im gleichen oder überschneidenden Zeitraum gebucht werden können,
- unterschiedliche freie Räume parallel für unterschiedliche Termine genutzt werden können,
- für einen Termin höchstens ein Raum reserviert werden kann,
- gebuchte Räume im Kalender sichtbar sind,
- eine Raumreservierung beim Löschen des zugehörigen Termins ebenfalls entfernt wird.

Weitere Tests und bekannte Grenzen befinden sich in `docs/TESTING.md`.