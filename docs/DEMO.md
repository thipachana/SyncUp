# SyncUp – Demo-Ablauf

Stand: 22.09.2026

## 1. Anwendung starten

Für die Demo werden nacheinander gestartet:

- PostgreSQL
- Spring-Boot-Backend
- React-Frontend

Anschließend wird SyncUp im Browser geöffnet.

## 2. Terminanfragen zeigen

- Vorhandene Terminanfragen anzeigen
- Neue Terminanfrage erstellen
- Terminanfrage nach dem Speichern anzeigen
- Terminanfrage bei Bedarf wieder löschen

## 3. Freie Zeitfenster zeigen

- Für eine Terminanfrage freie Zeitfenster berechnen
- Ergebnis im Frontend anzeigen
- Zeigen, dass bereits belegte Zeiten bei der Berechnung berücksichtigt werden

Die vollständige Teilnehmerauswahl ist aktuell noch nicht über das Frontend umgesetzt.

## 4. Ressourcen zeigen

- Vorhandene Ressourcen anzeigen
- Name, Typ und Kapazität einer Ressource erklären
- Ressource für einen vorhandenen Termin buchen
- Erfolgreiche Buchung zeigen

## 5. Doppelbuchung testen

Anschließend wird dieselbe Ressource für einen überschneidenden Zeitraum erneut gebucht.

Erwartetes Ergebnis:

- Die zweite Buchung wird nicht gespeichert
- Das Backend antwortet mit HTTP 409
- Das Frontend zeigt die Meldung:

`Ressource ist in diesem Zeitraum bereits gebucht.`

## 6. Backend und Datenbank erklären

Während der Demo kann kurz erklärt werden:

- Daten werden in PostgreSQL gespeichert
- Das Frontend kommuniziert über REST mit dem Spring-Boot-Backend
- Das Backend verarbeitet die Anfragen und greift auf die Datenbank zu

Zu den gespeicherten Daten gehören unter anderem:

- Benutzer
- Kalender
- Termine
- Terminanfragen
- Ressourcen
- Buchungen

## 7. Sicherheit zeigen

Über eine API-Abfrage kann gezeigt werden, dass das Passwort eines Benutzers nicht ausgegeben wird.

Eine vollständige Anmeldung und Zugriffskontrolle ist aktuell noch nicht umgesetzt.

## Aktueller Stand

Aktuell sind unter anderem folgende Funktionen vorhanden:

- Terminanfragen erstellen, anzeigen und löschen
- freie Zeitfenster berechnen
- Ressourcen anzeigen
- Ressourcen buchen
- überschneidende Ressourcenbuchungen verhindern
- Speicherung der Daten in PostgreSQL

Noch nicht vollständig umgesetzt sind unter anderem:

- Anmeldung und Registrierung
- Teilnehmerverwaltung
- vollständige Terminverwaltung im Frontend
- Einladungen und Benachrichtigungen
- Aufgabenverwaltung
- Freigabe von Ressourcen