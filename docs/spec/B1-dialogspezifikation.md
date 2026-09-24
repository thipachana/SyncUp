# B1 Dialogspezifikation

Stand: 23.09.2026

## Oberfläche und Anmeldung

Das bestehende grüne Dashboard mit Hero-Bereich, drei Planungskarten und Monatskalender bleibt erhalten. Home, Mein Kalender und Anmeldung sind auch bei schmalen Fenstern erreichbar.

Anmeldung und Registrierung öffnen ein schließbares Dialogfenster mit zwei Reitern. Registrierung benötigt Name, E-Mail und Passwort (mindestens acht Zeichen, höchstens 72 UTF-8-Bytes). Ein eigener Kalender wird erstellt; anschließend meldet die Oberfläche an. Ohne Anmeldung bleibt die Startseite sichtbar, persönliche Daten werden nicht geladen und Schreibaktionen sind gesperrt.

## Mein Kalender

Die Monatsübersicht bietet Vor-/Zurücknavigation. Ein Klick auf einen Tag öffnet die Eingabe von Titel, Beginn, Ende und optional einer Beschreibung.

Private Termine werden ausschließlich im persönlichen Kalender des angemeldeten Benutzers gespeichert. Für private Termine gibt es keine Teilnehmerauswahl.

Eigene Termine erscheinen am passenden Tag und können bearbeitet oder gelöscht werden. Ist einem Termin ein Raum zugeordnet, wird die zugehörige Raumbuchung beim Löschen des Termins ebenfalls entfernt.

Gemeinsame Termine, die aus einer Terminanfrage entstehen, werden ebenfalls im Kalender angezeigt. Ist für einen Termin ein Raum gebucht, wird dieser beim Termin dargestellt.

## Terminanfragen und Zeitfenster

Die linke Dashboardkarte enthält Titel, Suchzeitraum, gewünschte Termindauer und Teilnehmerauswahl. Der Ersteller nimmt immer teil.

Eigene offene Terminanfragen werden angezeigt. „Freie Zeiten“ berechnet die gemeinsamen freien Intervalle der ausgewählten Teilnehmer und zeigt diese in der mittleren Karte an. Ergebnisse sind der jeweiligen Anfrage zugeordnet; alte Antworten dürfen neuere Ergebnisse nicht überschreiben.

Wählt der Organisator ein freies Zeitfenster aus, erzeugt SyncUp daraus einen gemeinsamen Termin mit exakt der in der Terminanfrage angegebenen Dauer.

Nach erfolgreicher Terminwahl wird die Terminanfrage als erledigt markiert. Anschließend wird der Bereich zur Raumreservierung geöffnet bzw. fokussiert.

## Ressourcen

Die rechte Karte dient zur Reservierung eines Raums für einen bestehenden Termin.

Nach der Auswahl eines freien Zeitfensters wird der neu erzeugte Termin für die Raumreservierung vorausgewählt. Beginn und Ende der Buchung werden automatisch aus dem Termin übernommen und können nicht unabhängig verändert werden.

Die im System hinterlegten Räume werden zur Auswahl angezeigt. Nur verfügbare Räume können gebucht werden.

Ein Raum kann nicht für zwei zeitlich überlappende Termine reserviert werden. Außerdem kann einem Termin nur ein Raum zugeordnet werden.

Bei einer erfolgreichen Buchung wird der Raum beim zugehörigen Termin im Kalender angezeigt.

Ressourcen werden nicht durch normale Benutzer über die Oberfläche angelegt, sondern zentral im System verwaltet.

## Rückmeldungen und Sitzungswechsel

Lade-, Validierungs-, Speicher- und Verbindungsfehler werden sichtbar angezeigt. Während eines Speichervorgangs sind erneute Speicherklicks gesperrt. Nach Abmeldung oder Benutzerwechsel werden die bisherigen persönlichen Daten und Formulare verworfen. Eine fehlgeschlagene Abmeldung wird nicht als Erfolg angezeigt.

## Geplante Dialoge

Profil- und Passwortverwaltung sowie weitergehende Aufgabenfunktionen sind derzeit nicht Bestandteil des umgesetzten Funktionsumfangs.

Benachrichtigungen für gemeinsame Termine, Terminbearbeitung und das Löschen von Terminen sind umgesetzt.
