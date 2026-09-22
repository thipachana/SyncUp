# SyncUp – Testcheckliste

Stand: 22.09.2026

Diese Checkliste wird verwendet, um die wichtigsten Funktionen von SyncUp vor der Abgabe zu prüfen.

## 1. Backend

- [ ] Spring Boot startet ohne Fehler
- [ ] Verbindung zu PostgreSQL funktioniert
- [ ] Termine können über die API abgerufen werden
- [ ] Terminanfragen können über die API abgerufen werden
- [ ] Ressourcen können über die API abgerufen werden
- [ ] Buchungen können über die API abgerufen werden

## 2. Terminanfragen und freie Zeitfenster

- [ ] Terminanfrage kann erstellt werden
- [ ] Terminanfrage wird nach dem Speichern angezeigt
- [ ] Terminanfrage bleibt nach dem Neuladen vorhanden
- [ ] Terminanfrage kann gelöscht werden
- [ ] Freie Zeitfenster können berechnet werden
- [ ] Bereits belegte Zeiten werden bei der Berechnung berücksichtigt

## 3. Ressourcen und Buchungen

- [ ] Vorhandene Ressourcen werden angezeigt
- [ ] Eine Ressource kann für einen vorhandenen Termin gebucht werden
- [ ] Die Buchung wird gespeichert
- [ ] Eine überschneidende Buchung derselben Ressource wird verhindert
- [ ] Das Backend antwortet bei einer Doppelbuchung mit HTTP 409
- [ ] Das Frontend zeigt bei einer Doppelbuchung eine verständliche Fehlermeldung

## 4. Sicherheit

- [ ] Passwörter werden bei API-Abfragen nicht ausgegeben

Registrierung, Anmeldung, Session-Erhalt und Abmeldung wurden erfolgreich getestet. Eine weitergehende Zugriffskontrolle ist aktuell noch nicht umgesetzt.

## 5. Frontend

- [ ] Frontend startet ohne Fehler
- [ ] Frontend kann das Backend erreichen
- [ ] Terminanfragen werden angezeigt
- [ ] Freie Zeitfenster werden angezeigt
- [ ] Ressourcen werden angezeigt
- [ ] Fehlermeldungen werden verständlich angezeigt

## 6. Technische Prüfung

- [ ] Backend-Test mit `./mvnw test` erfolgreich
- [ ] Frontend-Build mit `npm run build` erfolgreich

## 7. Vor der Abgabe

- [ ] README ist aktuell
- [ ] F3 zeigt den aktuellen Umsetzungsstand
- [ ] INSTALL.md ist aktuell
- [ ] Spezifikation und Architektur passen zum Code
- [ ] Keine Passwörter oder geheimen Zugangsdaten im Repository
- [ ] Alle benötigten Änderungen wurden auf GitHub gepusht
- [ ] Git-Status ist bei den Teammitgliedern sauber