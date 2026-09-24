# P1 Ziele und Rahmenbedingungen

Stand: 24.09.2026

## Projektziel

SyncUp ist eine Webanwendung, die Teams bei der gemeinsamen Planung von Terminen und Räumen unterstützen soll.

Das Ziel ist, die Terminabstimmung einfacher und übersichtlicher zu machen. Benutzer sollen gemeinsame freie Zeitfenster finden, daraus einen gemeinsamen Termin erstellen und bei Bedarf einen Raum dafür reservieren können.

Zusätzlich können Benutzer private Termine in ihrem eigenen Kalender verwalten.

## Problemstellung

Die Abstimmung von Terminen läuft häufig über verschiedene Wege wie Messenger, E-Mail oder Kalender. Dadurch kann schnell der Überblick verloren gehen.

Außerdem kann es passieren, dass Termine oder Räume doppelt eingeplant werden.

SyncUp soll diese Abläufe an einer Stelle zusammenführen und dadurch die gemeinsame Planung vereinfachen.

## Projektumfang

Zum aktuellen Funktionsumfang von SyncUp gehören:

- Registrierung, Anmeldung und Abmeldung
- persönlicher Kalender
- private Termine erstellen, anzeigen, bearbeiten und löschen
- Terminanfragen erstellen und anzeigen
- Teilnehmer auswählen
- gewünschte Termindauer festlegen
- gemeinsame freie Zeitfenster berechnen
- aus einem freien Zeitfenster einen gemeinsamen Termin erstellen
- gemeinsame Termine bei den beteiligten Benutzern anzeigen
- Benachrichtigungen bei neu festgelegten gemeinsamen Terminen
- Benachrichtigungen anzeigen und löschen
- vorhandene Räume anzeigen
- zeitbezogene Raumverfügbarkeit anzeigen
- Räume für bestehende Termine reservieren
- Überschneidungen bei Raumbuchungen verhindern
- unterschiedliche freie Räume im gleichen Zeitraum parallel nutzen
- höchstens einen Raum pro Termin zulassen
- gebuchten Raum beim Termin im Kalender anzeigen
- zugehörige Raumbuchung beim Löschen eines Termins entfernen

Nicht Bestandteil des aktuellen Funktionsumfangs sind:

- Aufgabenverwaltung
- Profil- und Passwortverwaltung
- erweitertes Rollen- und Administrationssystem
- automatische Vorschläge für alternative Räume
- Verbindung zu externen Kalenderdiensten wie Google Calendar oder Microsoft Outlook
- Zahlungsfunktionen
- Rechnungsverwaltung
- Warenwirtschaft
- Buchhaltung

Der genaue Stand der einzelnen Funktionen wird in F3 „Anwendungsfunktionen“ beschrieben.

## Aktueller Stand

SyncUp besteht aus einem React-Frontend, einem Spring-Boot-Backend und einer PostgreSQL-Datenbank.

Die wichtigsten Abläufe der Anwendung sind umgesetzt.

Benutzer können sich registrieren, anmelden und abmelden. Jeder Benutzer besitzt einen persönlichen Kalender.

Private Termine können direkt im eigenen Kalender erstellt, angezeigt, bearbeitet und gelöscht werden. Private Termine besitzen keine Teilnehmerauswahl.

Für gemeinsame Termine kann eine Terminanfrage erstellt werden. Dabei werden Teilnehmer, Suchzeitraum und gewünschte Dauer festgelegt. SyncUp berechnet anschließend gemeinsame freie Zeitfenster.

Nach Auswahl eines freien Zeitfensters wird ein gemeinsamer Termin mit der zuvor festgelegten Dauer erstellt. Dieser wird bei den beteiligten Benutzern im Kalender angezeigt.

Die beteiligten Teilnehmer erhalten eine Benachrichtigung über den neu festgelegten gemeinsamen Termin.

Für einen bestehenden Termin kann außerdem ein Raum reserviert werden.

Die zeitbezogene Verfügbarkeit der vorhandenen Räume wird angezeigt. Überschneidende Buchungen desselben Raumes werden verhindert, während unterschiedliche freie Räume im gleichen Zeitraum weiterhin genutzt werden können.

Für einen Termin kann höchstens ein Raum reserviert werden.

Ein gebuchter Raum wird beim Termin im Kalender angezeigt.

Wird ein Termin gelöscht, wird auch eine zugehörige Raumreservierung entfernt.

## Zielgruppe

SyncUp richtet sich hauptsächlich an:

- Studierende
- Projektgruppen
- kleine Teams
- Vereine
- Teams innerhalb von Unternehmen

## Rahmenbedingungen

- SyncUp ist eine browserbasierte Webanwendung.
- Das System besteht aus Frontend, Backend und Datenbank.
- Das Frontend wurde mit React umgesetzt.
- Das Backend wurde mit Spring Boot und Java 21 umgesetzt.
- Die Daten werden in PostgreSQL gespeichert.
- Frontend und Backend kommunizieren über REST-Schnittstellen.
- Die Anmeldung erfolgt über eine serverseitige Sitzung.
- Schreibende Anfragen werden zusätzlich durch CSRF-Schutz abgesichert.
- Persönliche Daten sind an den jeweiligen Benutzer beziehungsweise Besitzer gebunden.
- Fachliche Regeln werden zentral im Backend geprüft.
- Die Anwendung wird für das Projekt lokal beziehungsweise innerhalb der Team-Testumgebung betrieben.
- Für gemeinsame Tests im Team wird Tailscale verwendet.
- Die Installation und der Start der Anwendung werden in S3 „Inbetriebnahme“ und in `INSTALL.md` beschrieben.

## Erfolgsziele

Mit SyncUp sollen vor allem folgende Ziele erreicht werden:

- weniger Aufwand bei der gemeinsamen Terminfindung
- gemeinsame freie Zeitfenster übersichtlich anzeigen
- private und gemeinsame Termine zentral verwalten
- Räume für Termine reservieren
- doppelte Raumbelegungen verhindern
- mehrere unterschiedliche Räume parallel nutzbar machen
- eine einfache und verständliche Bedienung
- fachliche Regeln serverseitig absichern

Die umgesetzten Funktionen werden anhand der Testdokumentation überprüft.

## Abgrenzung

SyncUp konzentriert sich auf die Termin- und Raumplanung innerhalb der Anwendung.

Eine direkte Verbindung zu externen Kalenderdiensten ist nicht Bestandteil der aktuellen Version.

Auch Funktionen wie Aufgabenverwaltung, Zahlungsabwicklung, Rechnungsverwaltung, Warenwirtschaft oder Buchhaltung gehören nicht zum aktuellen Funktionsumfang.