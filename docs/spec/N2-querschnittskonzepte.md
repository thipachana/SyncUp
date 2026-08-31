# N2. Querschnittskonzepte

## Fehlerbehandlung

Fehlerhafte Eingaben werden durch das Backend geprüft. Dem Benutzer werden verständliche Fehlermeldungen angezeigt.

## Sicherheit

Die Kommunikation zwischen Frontend und Backend erfolgt über REST-Schnittstellen. Für eine produktive Bereitstellung ist eine verschlüsselte HTTPS-Verbindung vorgesehen. Benutzer authentifizieren sich mit ihren Zugangsdaten.

## Datenhaltung

Alle Daten werden in einer PostgreSQL-Datenbank gespeichert. Dazu gehören Benutzer, Kalender, Termine und Ressourcen.

## Kommunikation

Das Frontend kommuniziert über REST mit dem Backend. Die Daten werden im JSON-Format übertragen.

## Protokollierung

Anwendungsfehler und Systemereignisse können serverseitig protokolliert werden, um Fehler einfacher zu analysieren.