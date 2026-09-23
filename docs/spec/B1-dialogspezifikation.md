# B1 Dialogspezifikation

Stand: 23.09.2026

## Oberfläche und Anmeldung

Das bestehende grüne Dashboard mit Hero-Bereich, drei Planungskarten und Monatskalender bleibt erhalten. Home, Mein Kalender und Anmeldung sind auch bei schmalen Fenstern erreichbar.

Anmeldung und Registrierung öffnen ein schließbares Dialogfenster mit zwei Reitern. Registrierung benötigt Name, E-Mail und Passwort (mindestens acht Zeichen, höchstens 72 UTF-8-Bytes). Ein eigener Kalender wird erstellt; anschließend meldet die Oberfläche an. Ohne Anmeldung bleibt die Startseite sichtbar, persönliche Daten werden nicht geladen und Schreibaktionen sind gesperrt.

## Mein Kalender

Die Monatsübersicht bietet Vor-/Zurücknavigation. Ein Klick auf einen Tag öffnet die Eingabe von Titel, Beginn und Ende. Eigene Termine erscheinen am passenden Tag. Löschen ist für eigene Termine ohne Ressourcenbuchung möglich; andernfalls wird ein Konflikt angezeigt. Terminbearbeitung ist noch nicht umgesetzt.

## Terminanfragen und Zeitfenster

Die linke Dashboardkarte enthält Titel, Suchzeitraum, unabhängige Dauer und Teilnehmerauswahl. Der Ersteller nimmt immer teil. Eigene Anfragen können angezeigt und gelöscht werden. „Freie Zeiten“ zeigt die gemeinsamen freien Intervalle in der mittleren Karte, einschließlich des Enddatums bei mehrtägigen Intervallen. Ergebnisse sind ihrer Anfrage zugeordnet; alte Antworten dürfen neuere Ergebnisse nicht überschreiben.

## Ressourcen

Die rechte Karte bietet eine Auswahl eigener Termine. Deren Zeiten werden vorbelegt und können angepasst werden. Nur verfügbare Ressourcen können gebucht werden. Überschneidungen ergeben eine sichtbare Konfliktmeldung. Unter „Neue Ressource anlegen“ kann ein Raum mit Name und Kapazität angelegt werden. Das Freigeben bestehender Buchungen ist noch offen.

## Rückmeldungen und Sitzungswechsel

Lade-, Validierungs-, Speicher- und Verbindungsfehler werden sichtbar angezeigt. Während eines Speichervorgangs sind erneute Speicherklicks gesperrt. Nach Abmeldung oder Benutzerwechsel werden die bisherigen persönlichen Daten und Formulare verworfen. Eine fehlgeschlagene Abmeldung wird nicht als Erfolg angezeigt.

## Geplante Dialoge

Profil- und Passwortverwaltung, Aufgaben, Einladungen, Benachrichtigungen, Terminbearbeitung und Buchungsstornierung sind noch nicht umgesetzt.
