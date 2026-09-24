# 8. Querschnittliche Konzepte

## Fehlerbehandlung

Fehler werden hauptsächlich im Backend erkannt und über passende HTTP-Statuscodes an das Frontend zurückgegeben.

Das Frontend verarbeitet diese Antworten und zeigt dem Benutzer eine verständliche Meldung an.

Ein Beispiel ist eine überschneidende Raumreservierung. Ist ein Raum im gewünschten Zeitraum bereits belegt, wird die neue Buchung abgelehnt.

Auch wenn für einen Termin bereits ein Raum gebucht wurde, kann nicht noch ein weiterer Raum für denselben Termin reserviert werden.

## Kommunikation

Die Kommunikation zwischen Frontend und Backend erfolgt über REST-Schnittstellen.

Die Daten werden hauptsächlich im JSON-Format übertragen.

Das Frontend greift nicht direkt auf die PostgreSQL-Datenbank zu. Alle Datenzugriffe laufen über das Backend.

## Benutzerverwaltung

Benutzer können sich in SyncUp registrieren, anmelden und wieder abmelden.

Nach der Anmeldung wird eine serverseitige Sitzung verwendet.

Persönliche Daten wie Kalender und Termine werden dem jeweiligen Benutzer zugeordnet. Das Backend prüft bei geschützten Funktionen, ob ein Benutzer angemeldet ist und auf die jeweiligen Daten zugreifen darf.

Eine erweiterte Profilverwaltung sowie ein Rollen- oder Administrationssystem sind nicht Bestandteil des aktuellen Funktionsumfangs.

## Datenhaltung

Die Daten der Anwendung werden dauerhaft in einer PostgreSQL-Datenbank gespeichert.

Dazu gehören unter anderem:

- Benutzer
- Kalender
- Termine
- Terminanfragen
- Ressourcen
- Buchungen
- Benachrichtigungen

Für den Datenbankzugriff verwendet das Backend Spring Data JPA und Hibernate.

## Sicherheit

Passwörter werden nicht im Klartext gespeichert, sondern mit BCrypt gehasht.

Das Passwortfeld wird außerdem nicht über normale REST-Antworten an das Frontend übertragen.

Für angemeldete Benutzer wird eine serverseitige Sitzung verwendet.

Schreibende Anfragen werden zusätzlich über einen CSRF-Nachweis geschützt.

Persönliche Daten werden serverseitig nach dem jeweiligen Benutzer beziehungsweise Besitzer gefiltert.

Dadurch soll verhindert werden, dass ein Benutzer über fremde IDs auf persönliche Daten eines anderen Benutzers zugreifen kann.

## Konfiguration

Für den lokalen Standardstart bleibt `VITE_API_URL` leer.

Das Frontend verwendet relative `/api`-Aufrufe. Vite leitet diese während der lokalen Entwicklung an das Backend auf Port `8080` weiter.

Dadurch wird für Frontend und Backend eine einheitliche API-Anbindung verwendet.

Für die gemeinsame Testumgebung wird Tailscale verwendet. Die anderen Teammitglieder greifen dabei auf die Anwendung des Host-Rechners zu.

## Ressourcenbuchungen

Eine Raumreservierung ist immer mit einem bestehenden Termin verbunden.

Beginn und Ende der Buchung werden aus dem ausgewählten Termin übernommen und nicht unabhängig davon eingegeben.

Vor dem Speichern prüft das Backend:

- ob der Raum im Zeitraum des Termins bereits belegt ist,
- ob für den Termin bereits ein anderer Raum gebucht wurde.

Bei einem Konflikt wird die Buchung nicht gespeichert.

Wird ein Termin gelöscht, wird eine zugehörige Raumbuchung ebenfalls entfernt.