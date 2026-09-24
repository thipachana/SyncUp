# N2 Querschnittskonzepte

Stand: 24.09.2026

## Fehlerbehandlung

Das Backend prüft eingehende Anfragen und gibt bei fehlerhaften Eingaben oder ungültigen Aktionen passende Fehlermeldungen zurück.

Zum Beispiel wird eine Ressourcenbuchung abgelehnt, wenn derselbe Raum im gleichen Zeitraum bereits gebucht ist oder wenn für einen Termin schon ein Raum reserviert wurde.

Das Frontend zeigt diese Fehler für den Benutzer verständlich an.

## Sicherheit

Frontend und Backend kommunizieren über REST-Schnittstellen.

Passwörter werden nicht im Klartext gespeichert und nicht in API-Antworten an das Frontend übertragen.

Die Anmeldung erfolgt über eine serverseitige Session. Zusätzlich ist CSRF-Schutz vorhanden.

Persönliche Daten wie eigene Kalender, Termine und Terminanfragen sind an den angemeldeten Benutzer gebunden. Eigentumsprüfungen verhindern unberechtigte Änderungen.

Ein erweitertes Rollen- oder Administrationssystem ist nicht Bestandteil des aktuellen Funktionsumfangs.

Für eine produktive Nutzung außerhalb der lokalen beziehungsweise internen Testumgebung sollte die Verbindung über HTTPS abgesichert werden.

## Datenhaltung

Die Daten werden in einer PostgreSQL-Datenbank gespeichert.

Dazu gehören unter anderem:

- Benutzer
- Kalender
- Termine
- Terminanfragen
- Ressourcen
- Buchungen
- Benachrichtigungen

Für den Zugriff auf die Daten verwendet das Backend Spring Data JPA und Hibernate.

## Kommunikation

Das Frontend kommuniziert über REST mit dem Backend.

Die Daten werden dabei hauptsächlich im JSON-Format übertragen.

## Protokollierung

Spring Boot gibt beim Start und während der Ausführung Informationen und Fehlermeldungen im Backend-Terminal aus.

Eine zusätzliche eigene Protokollierungsoberfläche innerhalb von SyncUp ist nicht Bestandteil des aktuellen Funktionsumfangs.