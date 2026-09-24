# 8. Querschnittliche Konzepte

## Fehlerbehandlung

Fehler werden hauptsächlich im Backend erkannt und über passende HTTP-Statuscodes an das Frontend zurückgegeben.

Das Frontend verarbeitet diese Antworten und zeigt dem Benutzer eine verständliche Meldung an.

Ein Beispiel ist eine überschneidende Raumreservierung. Ist ein Raum im gewünschten Zeitraum bereits belegt, wird die neue Buchung abgelehnt.

Auch wenn für einen Termin bereits ein Raum gebucht wurde, kann nicht noch ein weiterer Raum für denselben Termin reserviert werden.

Die serverseitige Prüfung ist dabei maßgeblich. Auch wenn das Frontend einen Raum zuvor als verfügbar angezeigt hat, wird unmittelbar vor dem Speichern erneut geprüft, ob weiterhin eine gültige Buchung möglich ist.

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

Nachdem ein Termin ausgewählt wurde, fragt das Frontend die zeitbezogene Verfügbarkeit der vorhandenen Räume beim Backend ab.

Das Backend berücksichtigt dafür bereits gespeicherte Buchungen und prüft, ob sich deren Zeiträume mit dem ausgewählten Termin überschneiden.

Ein Raum wird im Frontend als „Nicht verfügbar“ angezeigt, wenn:

- der Raum grundsätzlich nicht buchbar ist oder
- für den Zeitraum des ausgewählten Termins bereits eine überschneidende Buchung existiert.

Andere Räume bleiben im selben Zeitraum weiterhin verfügbar, solange für diese keine überschneidende Buchung existiert.

Vor dem tatsächlichen Speichern einer neuen Buchung führt das Backend die Konfliktprüfung erneut durch.

Dabei wird geprüft:

- ob der Raum im Zeitraum des Termins bereits belegt ist,
- ob die Ressource grundsätzlich verfügbar ist,
- ob für den Termin bereits ein anderer Raum gebucht wurde.

Dadurch dient die Verfügbarkeitsanzeige im Frontend nur der Benutzerführung, während die endgültige Konsistenzprüfung im Backend erfolgt.

Direkt aufeinanderfolgende Buchungen gelten nicht als Überschneidung. Eine Buchung von 14:00 bis 15:00 Uhr verhindert daher beispielsweise keine weitere Buchung desselben Raumes von 15:00 bis 16:00 Uhr.

Für einen Termin kann höchstens eine Raumreservierung existieren.

Wird ein Termin gelöscht, wird eine zugehörige Raumbuchung ebenfalls entfernt.