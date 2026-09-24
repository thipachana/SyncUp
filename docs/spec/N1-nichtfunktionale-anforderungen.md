# N1 Nichtfunktionale Anforderungen

Stand: 24.09.2026

## Benutzerfreundlichkeit

SyncUp soll einfach aufgebaut und ohne lange Einarbeitung nutzbar sein.

Die wichtigsten Funktionen sollen übersichtlich dargestellt werden, damit Benutzer schnell verstehen, wie Termine erstellt, gemeinsame freie Zeitfenster gefunden und Räume gebucht werden können.

Fehlermeldungen, Erfolgsmeldungen und Statusinformationen sollen verständlich angezeigt werden.

## Performance

Die Anwendung soll bei normaler Nutzung ohne unnötig lange Wartezeiten reagieren.

Das Laden und Speichern von Daten sowie die Berechnung gemeinsamer freier Zeitfenster sollen für die im Projekt vorgesehenen Testszenarien ausreichend schnell erfolgen.

Es werden keine festen maximalen Antwortzeiten oder Lastgrenzen zugesichert.

## Sicherheit

Benutzerdaten und Passwörter sollen geschützt verarbeitet werden.

Passwörter werden nicht im Klartext gespeichert, sondern mit BCrypt gehasht.

Passwörter werden nicht in normalen API-Antworten an das Frontend übertragen.

Persönliche Kalender, Termine und Terminanfragen sind an den jeweiligen Benutzer beziehungsweise Ersteller gebunden.

Serverseitige Zugriffs- und Eigentumsprüfungen sollen verhindern, dass Benutzer unberechtigt persönliche Daten anderer Benutzer verändern.

Für die Anmeldung wird eine serverseitige Sitzung verwendet.

Schreibende Anfragen werden zusätzlich durch CSRF-Schutz abgesichert.

Ein erweitertes Rollen- oder Administrationssystem ist nicht Bestandteil des aktuellen Funktionsumfangs.

## Wartbarkeit

SyncUp ist in Frontend, Backend und Datenbank aufgeteilt.

Das Frontend wurde mit React umgesetzt, das Backend mit Spring Boot und die Daten werden in PostgreSQL gespeichert.

Durch diese Trennung können die einzelnen Bereiche getrennt bearbeitet, getestet und erweitert werden.

Die Geschäftslogik wird möglichst zentral im Backend gehalten.

## Konsistenz

Fachliche Regeln sollen unabhängig von der Benutzeroberfläche eingehalten werden.

Dies betrifft insbesondere Raumreservierungen.

Das Backend prüft vor dem Speichern, ob eine Buchung mit bereits vorhandenen Buchungen kollidiert und ob einem Termin bereits ein Raum zugeordnet wurde.

Dadurch soll verhindert werden, dass durch direkte oder parallele Anfragen ungültige Buchungszustände entstehen.

## Verfügbarkeit

Die Anwendung ist für den Projektbetrieb als lokal beziehungsweise innerhalb der Teamumgebung gestartete Webanwendung vorgesehen.

Damit SyncUp verwendet werden kann, müssen Frontend, Backend und PostgreSQL laufen.

Für die gemeinsame Testumgebung wurde die Anwendung innerhalb des Teams über Tailscale erreichbar gemacht.

Eine dauerhaft öffentlich betriebene Produktivumgebung sowie zugesicherte Verfügbarkeitswerte sind nicht Bestandteil des Projekts.