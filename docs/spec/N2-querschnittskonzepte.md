# N2 Querschnittskonzepte

Stand: 22.09.2026

## Fehlerbehandlung

Das Backend prüft Anfragen und kann bei Fehlern eine entsprechende Fehlermeldung zurückgeben.

Zum Beispiel wird eine Ressourcenbuchung abgelehnt, wenn die Ressource im gleichen Zeitraum bereits gebucht ist. Das Frontend zeigt dem Benutzer dann eine Fehlermeldung an.

## Sicherheit

Frontend und Backend kommunizieren über REST-Schnittstellen.

Passwörter werden bei API-Abfragen nicht an das Frontend zurückgegeben.

Registrierung und Anmeldung mit serverseitiger Session sind umgesetzt. Eine weitergehende Zugriffskontrolle ist aktuell noch nicht umgesetzt.

Für eine spätere produktive Nutzung sollte die Verbindung zusätzlich über HTTPS abgesichert werden.

## Datenhaltung

Die Daten werden in einer PostgreSQL-Datenbank gespeichert.

Dazu gehören aktuell unter anderem:

- Benutzer
- Kalender
- Termine
- Terminanfragen
- Ressourcen
- Buchungen

Für den Zugriff auf die Daten verwendet das Backend Spring Data JPA und Hibernate.

## Kommunikation

Das Frontend kommuniziert über REST mit dem Backend.

Die Daten werden dabei im JSON-Format übertragen.

## Protokollierung

Spring Boot gibt beim Start und während der Ausführung Informationen und Fehlermeldungen im Backend-Terminal aus.

Eine eigene Protokollierungsfunktion innerhalb von SyncUp wurde bisher nicht umgesetzt.