# P1 Ziele und Rahmenbedingungen

Stand: 24.09.2026

## Projektziel

SyncUp ist eine Webanwendung, die Teams bei der gemeinsamen Planung von Terminen und Räumen unterstützen soll.

Das Ziel ist, die Terminabstimmung einfacher und übersichtlicher zu machen. Benutzer sollen gemeinsame freie Zeitfenster finden, daraus einen gemeinsamen Termin erstellen und bei Bedarf einen Raum dafür reservieren können.

Zusätzlich können Benutzer private Termine in ihrem eigenen Kalender verwalten.

## Problemstellung

Die Abstimmung von Terminen läuft oft über verschiedene Wege wie Messenger, E-Mail oder Kalender. Dadurch kann schnell der Überblick verloren gehen.

Außerdem kann es passieren, dass Termine oder Räume doppelt eingeplant werden.

SyncUp soll diese Abläufe an einer Stelle zusammenführen und dadurch die gemeinsame Planung vereinfachen.

## Projektumfang

Zum aktuellen Funktionsumfang von SyncUp gehören:

- Registrierung und Anmeldung
- persönlicher Kalender
- private Termine erstellen, bearbeiten und löschen
- Terminanfragen erstellen
- Teilnehmer auswählen
- gewünschte Termindauer festlegen
- gemeinsame freie Zeitfenster berechnen
- aus einem freien Zeitfenster einen gemeinsamen Termin erstellen
- gemeinsame Termine bei den Teilnehmern anzeigen
- Benachrichtigungen bei neu festgelegten gemeinsamen Terminen
- Räume für Termine reservieren
- Überschneidungen bei Raumbuchungen verhindern
- nur einen Raum pro Termin zulassen

Nicht Bestandteil des aktuellen Funktionsumfangs sind:

- Aufgabenverwaltung
- Profil- und Passwortverwaltung
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

Benutzer können sich registrieren und anmelden. Jeder Benutzer besitzt einen persönlichen Kalender.

Private Termine können direkt im eigenen Kalender erstellt, bearbeitet und gelöscht werden.

Für gemeinsame Termine kann eine Terminanfrage erstellt werden. Dabei werden Teilnehmer, Suchzeitraum und gewünschte Dauer festgelegt. SyncUp berechnet anschließend gemeinsame freie Zeitfenster.

Nach Auswahl eines freien Zeitfensters wird ein gemeinsamer Termin erstellt. Dieser wird bei den beteiligten Benutzern im Kalender angezeigt.

Für einen Termin kann außerdem ein Raum reserviert werden. Überschneidende Buchungen desselben Raums werden verhindert.

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
- Das Backend wurde mit Spring Boot umgesetzt.
- Die Daten werden in PostgreSQL gespeichert.
- Frontend und Backend kommunizieren über REST-Schnittstellen.
- Die Anmeldung erfolgt über eine serverseitige Session.
- Persönliche Daten sind an den angemeldeten Benutzer gebunden.
- Die Anwendung wird für das Projekt lokal beziehungsweise innerhalb der Team-Testumgebung betrieben.
- Die Installation und der Start der Anwendung werden in S3 „Inbetriebnahme“ und in der Installationsanleitung beschrieben.

## Erfolgsziele

Mit SyncUp sollen vor allem folgende Ziele erreicht werden:

- weniger Aufwand bei der gemeinsamen Terminfindung
- gemeinsame freie Zeitfenster übersichtlich anzeigen
- private und gemeinsame Termine zentral verwalten
- Räume für Termine reservieren
- doppelte Raumbelegungen verhindern
- eine einfache und verständliche Bedienung

Die umgesetzten Funktionen werden anhand der Testdokumentation überprüft.

## Abgrenzung

SyncUp konzentriert sich auf die Terminplanung innerhalb der Anwendung.

Eine direkte Verbindung zu externen Kalenderdiensten ist nicht Bestandteil der aktuellen Version.

Auch Funktionen wie Aufgabenverwaltung, Zahlungsabwicklung oder Buchhaltung gehören nicht zum aktuellen Funktionsumfang.