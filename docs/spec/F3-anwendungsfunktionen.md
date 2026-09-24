# F3 Anwendungsfunktionen

Stand: 24.09.2026

| Bereich | Umgesetzt | Noch offen / nicht umgesetzt |
| --- | --- | --- |
| Benutzer | Registrierung, Anmeldung, Abmeldung, BCrypt, Sitzungsverwaltung und CSRF-Schutz | Profil bearbeiten, Passwort ändern oder zurücksetzen, Adminrollen |
| Kalender | Eigener Monatskalender, private Termine erstellen, anzeigen, bearbeiten und löschen, gemeinsame Termine anzeigen, gebuchten Raum am Termin anzeigen | Suche, Verbindung zu externen Kalendern |
| Terminfindung | Terminanfragen erstellen und anzeigen, Teilnehmer auswählen, gewünschte Dauer angeben, freie Zeitfenster berechnen, freien Zeitslot als Termin übernehmen, Anfrage als erledigt markieren | Teilnehmer nachträglich ändern |
| Ressourcen | Räume anzeigen, Termin auswählen, zeitbezogene Raumverfügbarkeit anzeigen, Raum buchen, Überschneidungen verhindern, parallele Buchungen unterschiedlicher Räume zulassen, nur einen Raum pro Termin zulassen, Buchung beim Löschen des Termins mit entfernen | Alternative Räume automatisch vorschlagen |
| Aufgaben | Nicht umgesetzt | Aufgaben erstellen, zuweisen, bearbeiten und abschließen |
| Benachrichtigungen | Benachrichtigung bei neu festgelegten gemeinsamen Terminen, Benachrichtigungen anzeigen und löschen | Erinnerungen und weitere Änderungsbenachrichtigungen |

Der Ersteller ist immer Teilnehmer seiner eigenen Terminanfrage.

Private Termine gehören nur zum eigenen Kalender und haben keine Teilnehmerauswahl.

Gemeinsame Termine entstehen über eine Terminanfrage und werden bei den ausgewählten Teilnehmern im Kalender angezeigt.

Ein freies Zeitfenster ist zuerst nur ein Vorschlag. Erst wenn der Organisator einen Zeitslot auswählt, wird daraus ein gemeinsamer Termin mit der vorher angegebenen Dauer.

Räume werden zentral im System verwaltet und können nicht von normalen Benutzern neu angelegt werden.

Für die Raumreservierung wählt der Benutzer einen vorhandenen Termin aus. Datum, Startzeit und Endzeit der Buchung werden automatisch aus diesem Termin übernommen.

Die zeitbezogene Verfügbarkeit der Räume wird anhand bereits gespeicherter Buchungen ermittelt. Ein belegter Raum wird für einen überschneidenden Zeitraum als „Nicht verfügbar“ angezeigt, während andere freie Räume im gleichen Zeitraum weiterhin reserviert werden können.

Vor dem Speichern einer Raumreservierung prüft das Backend die Verfügbarkeit erneut. Dadurch werden überschneidende Buchungen desselben Raumes verhindert.

Ein Termin kann höchstens einen Raum haben.

Wenn ein Termin gelöscht wird, wird die dazugehörige Raumbuchung ebenfalls gelöscht.

Die Zeiten werden ohne zusätzliche Zeitzonenlogik verarbeitet. Der Suchzeitraum kann auch mehrere Tage umfassen.

Weitere Tests und noch offene Punkte stehen in [TESTING](../TESTING.md).