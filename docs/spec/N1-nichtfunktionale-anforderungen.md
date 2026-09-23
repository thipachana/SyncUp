# N1 Nichtfunktionale Anforderungen

Stand: 23.09.2026

## Benutzerfreundlichkeit

SyncUp soll einfach aufgebaut und ohne lange Einarbeitung nutzbar sein.

Die wichtigsten Funktionen sollen für den Benutzer verständlich dargestellt werden.

## Performance

Die Anwendung soll bei normaler Nutzung möglichst schnell auf Anfragen reagieren.

Das Laden und Speichern von Daten soll ohne unnötig lange Wartezeiten möglich sein.

## Sicherheit

Benutzerdaten und Passwörter sollen geschützt verarbeitet werden.

Passwörter werden bei API-Abfragen nicht an das Frontend zurückgegeben.

Registrierung und Anmeldung sowie eine sichere Passwortspeicherung mit BCrypt sind umgesetzt. Anmeldung und Eigentumsprüfungen schützen persönliche Daten. Ein organisationsbezogenes Rollenmodell ist noch offen.

## Wartbarkeit

SyncUp ist in Frontend, Backend und Datenbank aufgeteilt.

Dadurch können die einzelnen Bereiche getrennt bearbeitet und erweitert werden.

## Verfügbarkeit

Die Anwendung soll während der Nutzung stabil laufen.

Da SyncUp aktuell lokal ausgeführt wird, hängt die Verfügbarkeit auch davon ab, ob Frontend, Backend und Datenbank gestartet sind.