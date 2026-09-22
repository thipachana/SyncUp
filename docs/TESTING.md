# SyncUp – Testcheckliste

Stand: 23.09.2026

Diese Checkliste wird verwendet, um die wichtigsten Funktionen von SyncUp vor der Abgabe zu prüfen.

## 1. Backend

- [x] Spring Boot startet ohne Fehler
- [x] Verbindung zu PostgreSQL funktioniert
- [x] Persönliche Termine können über die API abgerufen werden
- [ ] Terminanfragen können über die API abgerufen werden
- [ ] Ressourcen können über die API abgerufen werden
- [ ] Buchungen können über die API abgerufen werden

## 2. Persönlicher Kalender

- [x] Angemeldeter Benutzer kann einen persönlichen Termin erstellen
- [x] Persönliche Termine können geladen werden
- [x] Ein persönlicher Termin kann gelöscht werden
- [x] Persönliche Terminendpunkte verwenden die angemeldete Session
- [x] `/api/termine` ist ohne Anmeldung nicht zugänglich
- [ ] Monatskalender im Frontend vollständig geprüft

## 3. Terminanfragen und freie Zeitfenster

- [ ] Terminanfrage kann erstellt werden
- [ ] Teilnehmer können einer Terminanfrage zugeordnet werden
- [ ] Suchzeitraum und Termindauer können unabhängig angegeben werden
- [ ] Terminanfrage wird nach dem Speichern angezeigt
- [ ] Terminanfrage bleibt nach dem Neuladen vorhanden
- [ ] Terminanfrage kann gelöscht werden
- [ ] Gemeinsame freie Zeitfenster können berechnet werden
- [ ] Termine der ausgewählten Teilnehmer werden bei der Berechnung berücksichtigt

## 4. Ressourcen und Buchungen

- [ ] Vorhandene Ressourcen werden angezeigt
- [ ] Eine Ressource kann für einen vorhandenen Termin gebucht werden
- [ ] Die Buchung wird gespeichert
- [ ] Eine überschneidende Buchung derselben Ressource wird verhindert
- [ ] Das Backend antwortet bei einer Doppelbuchung mit HTTP 409
- [ ] Das Frontend zeigt bei einer Doppelbuchung eine verständliche Fehlermeldung
- [ ] Eine als nicht verfügbar markierte Ressource kann nicht gebucht werden

## 5. Anmeldung und Sicherheit

- [x] Registrierung funktioniert
- [x] Anmeldung funktioniert
- [x] Session kann über `/api/auth/me` geprüft werden
- [x] Benutzerliste ist für angemeldete Benutzer erreichbar
- [x] Abmeldung funktioniert
- [x] Nach der Abmeldung liefert `/api/auth/me` HTTP 401
- [x] Passwörter werden bei API-Abfragen nicht ausgegeben
- [x] `/api/termine` liefert ohne Anmeldung HTTP 401

Die persönlichen Terminendpunkte sind an die angemeldete Session gebunden.
Eine vollständige rollen- und objektbezogene Zugriffskontrolle für alle Geschäftsbereiche ist derzeit noch nicht umgesetzt.

## 6. Automatisierter Backend-Smoke-Test

Für zentrale Backend-Funktionen steht folgendes Skript zur Verfügung:

```bash
./scripts/backend-smoke-test.sh
q
