# SyncUp – Demo

Stand: 24.09.2026

Diese Demo zeigt die wichtigsten Funktionen von SyncUp anhand eines einfachen Testablaufs.

## Vorbereitung

1. PostgreSQL starten.
2. Backend starten.
3. Frontend starten.
4. `http://localhost:5173` im Browser öffnen.
5. Für die Demo nur Testdaten verwenden.

## Demo-Ablauf

1. Zwei Testkonten registrieren, zum Beispiel Benutzer A und Benutzer B.

2. Mit Benutzer B einen privaten Termin anlegen, zum Beispiel von 09:00 bis 10:00 Uhr.

3. Abmelden und mit Benutzer A anmelden.

4. Mit Benutzer A einen privaten Termin am gleichen Tag von 10:00 bis 11:00 Uhr anlegen.

5. Eine neue Terminanfrage erstellen:
   - Titel eingeben
   - Suchzeitraum von 09:00 bis 13:00 Uhr festlegen
   - Dauer auf 60 Minuten setzen
   - Benutzer B als Teilnehmer auswählen

6. Die gemeinsamen freien Zeitfenster berechnen lassen.

7. Als freies Zeitfenster sollte in diesem Beispiel 11:00 bis 13:00 Uhr angezeigt werden.

8. Einen freien Zeitslot auswählen.

9. SyncUp erstellt daraus einen gemeinsamen Termin mit der gewünschten Dauer von 60 Minuten.

10. Der Termin wird bei den beteiligten Benutzern im Kalender angezeigt.

11. Anschließend den Bereich zur Raumreservierung öffnen beziehungsweise verwenden.

12. Den zuvor erstellten Termin auswählen und einen vorhandenen Raum buchen.

13. Die Buchungszeit wird automatisch aus dem Termin übernommen.

14. Prüfen, ob der gebuchte Raum beim Termin im Kalender angezeigt wird.

15. Mit dem zweiten Benutzer anmelden und prüfen, ob:
   - der gemeinsame Termin im Kalender angezeigt wird,
   - der gebuchte Raum sichtbar ist,
   - eine Benachrichtigung zum gemeinsamen Termin vorhanden ist.

16. Zusätzlich prüfen, ob eine doppelte oder überschneidende Buchung desselben Raums abgelehnt wird.

17. Einen Termin mit Raumreservierung löschen und prüfen, ob die zugehörige Raumbuchung ebenfalls entfernt wird.

## Erwartetes Ergebnis

Am Ende der Demo sollte gezeigt worden sein, dass:

- Benutzer sich registrieren und anmelden können,
- private Termine erstellt werden können,
- gemeinsame freie Zeitfenster berechnet werden,
- aus einem freien Zeitfenster ein gemeinsamer Termin entsteht,
- die gewünschte Termindauer eingehalten wird,
- Räume gebucht werden können,
- doppelte Raumbelegungen verhindert werden,
- gemeinsame Termine bei den Teilnehmern angezeigt werden,
- gebuchte Räume im Kalender sichtbar sind,
- Teilnehmer Benachrichtigungen erhalten.

Weitere Tests und bekannte Grenzen befinden sich in `docs/TESTING.md`.