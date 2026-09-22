# P1 Ziele und Rahmenbedingungen

Stand: 22.09.2026

## Projektziel

SyncUp ist eine Webanwendung, die Teams bei der gemeinsamen Planung von Terminen und Ressourcen unterstützen soll.

Das Ziel ist es, die Terminabstimmung einfacher zu machen. Nutzer sollen gemeinsame freie Zeitfenster finden und Ressourcen wie zum Beispiel Besprechungsräume für Termine reservieren können.

## Problemstellung

Die Abstimmung von Terminen findet häufig über verschiedene Wege wie Messenger, E-Mail oder Kalender statt. Dadurch kann die Planung schnell unübersichtlich werden und es können Terminüberschneidungen entstehen.

SyncUp soll die wichtigsten Informationen an einer Stelle zusammenführen und dadurch die gemeinsame Terminplanung vereinfachen.

## Geplanter Projektumfang

Für SyncUp wurden folgende Funktionsbereiche geplant:

- Benutzerverwaltung mit Registrierung und Anmeldung
- Kalender- und Terminverwaltung
- Berechnung gemeinsamer freier Zeitfenster
- Ressourcenverwaltung und Ressourcenbuchung
- Aufgabenverwaltung
- Einladungen und Benachrichtigungen

Nicht alle ursprünglich geplanten Funktionen sind im aktuellen Stand bereits vollständig umgesetzt. Der genaue Umsetzungsstand wird in F3 „Anwendungsfunktionen“ beschrieben.

Nicht zum Projekt gehören:

- Zahlungsfunktionen
- Rechnungsverwaltung
- Warenwirtschaft
- Buchhaltung

Eine Verbindung zu externen Kalenderdiensten wie Google Calendar oder Microsoft Outlook wäre eine mögliche spätere Erweiterung und gehört nicht zur aktuellen Version.

## Aktueller Entwicklungsstand

Der aktuelle Stand von SyncUp besteht aus einem React-Frontend, einem Spring-Boot-Backend und einer PostgreSQL-Datenbank.

Bereits umgesetzt sind unter anderem:

- Terminanfragen erstellen, anzeigen und löschen
- Vorhandene Ressourcen anzeigen
- Ressourcen für einen vorhandenen Termin buchen
- Überschneidende Buchungen derselben Ressource verhindern
- Daten dauerhaft in PostgreSQL speichern
- Termine über das Backend anlegen und abrufen

Die gemeinsame Terminfindung ist teilweise umgesetzt. Das Backend kann anhand vorhandener Termine freie Zeitfenster berechnen. Diese können anschließend im Frontend angezeigt werden.

Für einen vollständigen Ablauf fehlen aktuell noch Funktionen wie die Teilnehmerauswahl und eine vollständige Terminverwaltung über das Frontend.

Noch nicht vollständig umgesetzt sind unter anderem:

- Registrierung und Anmeldung
- Zugriffskontrolle
- vollständige Kalender- und Terminverwaltung im Frontend
- Aufgabenverwaltung
- Einladungen und Benachrichtigungen
- Freigabe bereits gebuchter Ressourcen

Der aktuelle Stand der einzelnen Funktionen wird zusätzlich in F3 „Anwendungsfunktionen“ dokumentiert.

## Zielgruppe

SyncUp richtet sich hauptsächlich an:

- Studierende
- Projektgruppen
- kleine Teams
- Vereine
- Teams innerhalb von Unternehmen

## Rahmenbedingungen

- SyncUp wird als browserbasierte Webanwendung entwickelt.
- Das System besteht aus Frontend, Backend und Datenbank.
- Die Daten werden in PostgreSQL gespeichert.
- Die Anwendung wird aktuell lokal ausgeführt.
- Frontend und Backend kommunizieren über REST-Schnittstellen.
- Registrierung und Anmeldung sind umgesetzt. Eine weitergehende Zugriffskontrolle ist aktuell noch nicht umgesetzt.
- Die Installation und der Start der Anwendung werden in S3 „Inbetriebnahme“ und in der Installationsanleitung beschrieben.

## Erfolgsziele

Mit SyncUp sollen vor allem folgende Ziele erreicht werden:

- Weniger Aufwand bei der gemeinsamen Terminfindung
- Freie Zeitfenster übersichtlich anzeigen
- Termine und Ressourcen zentral verwalten
- Überschneidende Ressourcenbuchungen verhindern
- Eine möglichst einfache und verständliche Bedienung

Ob die bereits umgesetzten Funktionen korrekt funktionieren, wird anhand der Testdokumentation überprüft.

## Abgrenzung des aktuellen Umfangs

Während der Entwicklung wurde der geplante Funktionsumfang an den aktuellen Projektstand angepasst.

Funktionen, die bis zur Abgabe nicht vollständig umgesetzt werden, werden weiterhin dokumentiert, aber klar als noch offen oder nur teilweise umgesetzt gekennzeichnet.

Der genaue Stand der einzelnen Funktionen ist in F3 „Anwendungsfunktionen“ festgehalten.