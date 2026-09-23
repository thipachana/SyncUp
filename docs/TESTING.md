# SyncUp – Prüfstand 23.09.2026

Basis: GitHub main adbb031 plus die noch nicht committeten lokalen Korrekturen.

## Automatisierte Prüfung

- Backend: jetzt 12 Tests erfolgreich gegen H2 einschließlich UC5. Der vorherige Stand mit 10 Tests wurde auch gegen eine separate PostgreSQL-17-Testdatenbank geprüft.
- Frontend: 8 API-Client-Tests erfolgreich; Produktionsbuild erfolgreich; Lint-Prüfung ohne Warnungen.
- Backend deckt Registrierung, Passwortprüfung, Sessionwechsel, CSRF, anonyme Zugriffe, fremde Objekte, Eingabevalidierung, gemeinsame freie Zeitfenster und konkurrierende Ressourcenbuchungen ab.
- Client-Tests prüfen insbesondere Sessionwechsel, verspätete Antworten, CSRF, Verbindungsfehler und fehlgeschlagene Abmeldung.

Vom Projektverzeichnis aus:

```bash
cd backend
./mvnw test
cd ../frontend
npm ci
npm test
npm run lint
npm run build
```

Tests verwenden standardmäßig H2 und verändern keine laufenden Anwendungsdaten. PostgreSQL-Integrationstests ausschließlich mit einer getrennten Testdatenbank ausführen: Die Tests erstellen und löschen Tabellen!

## Im Browser geprüft

Auf einer getrennten Testinstanz: Anmeldung, persönlicher Kalendereintrag, Teilnehmerauswahl, gemeinsame freie Zeiten, Raum anlegen, eigenen Termin auswählen, erfolgreiche Buchung, verständliche Meldung bei Doppelbuchung sowie Abmeldung ohne zurückbleibende private Anfragen. Navigation auch bei schmalem Fenster sichtbar. Das vorhandene grüne Layout bleibt erhalten.
Registrierung ist per API geprüft, nicht als vollständiger Browserablauf. Eine vollständige Prüfung aller Browser und Geräte steht aus.

## Noch offen / bewusste Grenzen

- Bestehende Terminanfragen ohne Ersteller bleiben gespeichert, sind aber verborgen. Eine fachlich bestätigte Zuordnung alter Daten ist erforderlich, wenn diese weiter genutzt werden sollen.
- Rollenabhängige Administration, Einladungen, Benachrichtigungen, Aufgaben, Passwortzurücksetzung und Ressourcenfreigabe sind noch nicht vollständig umgesetzt.
- UC5 ergänzt: Terminbearbeitung, Teilnehmer und gespeicherte Benachrichtigungen; Details in UC5-TERMIN-BEARBEITEN.md. Die neue Oberfläche ist gebaut und statisch geprüft, noch nicht vollständig im Browser abgenommen.
- Termine mit Buchungen können nicht gelöscht oder zeitlich verschoben werden; dafür wird eine verständliche Konfliktmeldung zurückgegeben. Eine Stornierungsfunktion fehlt.
- Betrieb außerhalb des lokalen Rechners benötigt eine eigene HTTPS-, Cookie- und Proxy-Konfiguration.
- Fachliche Abnahme durch das Team steht aus. Erfolgreiche Tests sind keine Garantie, dass das gesamte Projekt fehlerfrei ist.
