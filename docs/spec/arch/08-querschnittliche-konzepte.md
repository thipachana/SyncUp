# 8. Querschnittliche Konzepte

## Fehlerbehandlung

Fehler werden zentral im Backend behandelt und über HTTP-Statuscodes an das Frontend übermittelt.

Bei einer überschneidenden Ressourcenbuchung lehnt das Backend die Anfrage beispielsweise mit dem HTTP-Statuscode `409 Conflict` ab. Das Frontend verarbeitet diese Antwort und zeigt dem Benutzer eine verständliche Fehlermeldung an.

## Kommunikation

Die Kommunikation zwischen Frontend und Backend erfolgt über REST-Schnittstellen. Die Daten werden im JSON-Format übertragen.

Das Frontend greift nicht direkt auf die PostgreSQL-Datenbank zu.

## Benutzerverwaltung

Benutzerdaten können im Backend verarbeitet und in der Datenbank gespeichert werden.

Registrierung und Anmeldung mit serverseitiger Session sind umgesetzt. SessionSecurity prüft zentral Anmeldung und CSRF-Nachweise. Controller und Services prüfen Eigentum für persönliche Daten. Profilverwaltung und Organisationsrollen bleiben offen.

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

Passwörter werden mit BCrypt gehasht. Die Sitzungs-ID und der CSRF-Nachweis wechseln nach Anmeldung; Abmeldung invalidiert die Sitzung. Cookies sind HttpOnly und SameSite=Lax. Persönliche Daten werden serverseitig nach Besitzer gefiltert.

## Konfiguration

Lokal bleibt `VITE_API_URL` leer; Vite leitet `/api` an Port 8080 weiter. Eine abweichende API-Adresse kann ausdrücklich konfiguriert werden.

Dadurch kann das Frontend in unterschiedlichen lokalen Entwicklungsumgebungen mit verschiedenen Backend-Adressen verwendet werden.

Ressourcenbuchungen sperren den Termin und anschließend die Ressource innerhalb derselben Transaktion. Die Konfliktprüfung und Speicherung erfolgen unter dieser Sperre. Terminlöschen prüft unter derselben Terminsperre vorhandene Buchungen und lehnt dann mit 409 ab.
