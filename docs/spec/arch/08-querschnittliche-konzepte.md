# 8. Querschnittliche Konzepte

## Fehlerbehandlung

Fehler werden zentral im Backend behandelt und über HTTP-Statuscodes an das Frontend übermittelt.

Bei einer überschneidenden Ressourcenbuchung lehnt das Backend die Anfrage beispielsweise mit dem HTTP-Statuscode `409 Conflict` ab. Das Frontend verarbeitet diese Antwort und zeigt dem Benutzer eine verständliche Fehlermeldung an.

## Kommunikation

Die Kommunikation zwischen Frontend und Backend erfolgt über REST-Schnittstellen. Die Daten werden im JSON-Format übertragen.

Das Frontend greift nicht direkt auf die PostgreSQL-Datenbank zu.

## Benutzerverwaltung

Benutzerdaten können im Backend verarbeitet und in der Datenbank gespeichert werden.

Eine vollständige Benutzerverwaltung mit Anmeldung, Registrierung, Profilverwaltung und Zugriffskontrolle ist im aktuellen Entwicklungsstand noch nicht vollständig umgesetzt.

## Datenhaltung

Die Daten der Anwendung werden dauerhaft in einer PostgreSQL-Datenbank gespeichert.

Dazu gehören unter anderem:

- Benutzer
- Kalender
- Termine
- Terminanfragen
- Ressourcen
- Buchungen

Für den Datenbankzugriff verwendet das Backend Spring Data JPA und Hibernate.

## Sicherheit

Das Passwortfeld eines Benutzers wird nicht in den JSON-Antworten der REST-Schnittstellen ausgegeben.

Dadurch wird verhindert, dass gespeicherte Passwörter bei normalen API-Abfragen an das Frontend übertragen werden.

Eine weitergehende Authentifizierung und sichere Passwortverarbeitung ist als Erweiterung vorgesehen.

## Konfiguration

Die Adresse des Backends wird im Frontend über `VITE_API_URL` konfiguriert.

Dadurch kann das Frontend in unterschiedlichen lokalen Entwicklungsumgebungen mit verschiedenen Backend-Adressen verwendet werden.