# N1 Nichtfunktionale Anforderungen

Stand: 24.09.2026

## Benutzerfreundlichkeit

SyncUp soll einfach aufgebaut und ohne lange Einarbeitung nutzbar sein.

Die wichtigsten Funktionen sollen übersichtlich dargestellt werden, damit Benutzer schnell verstehen, wie Termine erstellt, freie Zeitfenster gefunden und Räume gebucht werden können.

Fehlermeldungen und Rückmeldungen sollen verständlich angezeigt werden.

## Performance

Die Anwendung soll bei normaler Nutzung schnell auf Anfragen reagieren.

Das Laden und Speichern von Daten sowie die Berechnung freier Zeitfenster sollen ohne unnötig lange Wartezeiten möglich sein.

## Sicherheit

Benutzerdaten und Passwörter sollen geschützt verarbeitet werden.

Passwörter werden nicht im Klartext gespeichert, sondern mit BCrypt gehasht.

Passwörter werden nicht in API-Antworten an das Frontend übertragen.

Persönliche Kalender, Termine und Terminanfragen sind an den angemeldeten Benutzer gebunden. Eigentumsprüfungen verhindern, dass Benutzer persönliche Daten anderer Benutzer verändern.

Für die Anmeldung wird eine Sitzung verwendet. Zusätzlich ist CSRF-Schutz vorhanden.

Ein erweitertes Rollen- oder Administrationssystem ist nicht Bestandteil des aktuellen Funktionsumfangs.

## Wartbarkeit

SyncUp ist in Frontend, Backend und Datenbank aufgeteilt.

Das Frontend wurde mit React umgesetzt, das Backend mit Spring Boot und die Daten werden in PostgreSQL gespeichert.

Durch diese Aufteilung können die einzelnen Bereiche getrennt bearbeitet und erweitert werden.

## Verfügbarkeit

Die Anwendung ist für den Projektbetrieb als lokal beziehungsweise innerhalb der Teamumgebung gestartete Webanwendung vorgesehen.

Damit SyncUp verwendet werden kann, müssen Frontend, Backend und PostgreSQL laufen.

Für die gemeinsame Testumgebung wurde die Anwendung innerhalb des Teams über Tailscale erreichbar gemacht.

Eine dauerhaft öffentlich betriebene Produktivumgebung ist nicht Bestandteil des Projekts.