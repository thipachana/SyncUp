# SyncUp – Demo

Stand: 23.09.2026, lokale Korrekturen auf Basis adbb031.

1. PostgreSQL, Backend und Frontend nach INSTALL.md starten; http://localhost:5173 öffnen.
2. Zwei eigene Testkonten registrieren. Keine echten Passwörter in Dokumente oder Git aufnehmen.
3. Mit Konto B einen Termin am gewählten Tag von 09:00 bis 10:00 erstellen und abmelden.
4. Mit Konto A einen Termin am selben Tag von 10:00 bis 11:00 erstellen.
5. Eine Terminanfrage von 09:00 bis 13:00 mit Dauer 60 Minuten und beiden Konten anlegen. Erwartetes gemeinsames freies Fenster: 11:00 bis 13:00.
6. Unter Ressourcen einen Raum anlegen, den eigenen Termin auswählen und buchen.
7. Dieselbe Buchung wiederholen: Eine verständliche Konfliktmeldung erscheint, keine zweite Buchung wird gespeichert.
8. Abmelden: Private Anfragen und Termine verschwinden. Geschützte API-Endpunkte verlangen Anmeldung.

Nur isolierte Testdaten verwenden. Der dokumentierte Prüfstand und verbleibende Grenzen stehen in TESTING.md. Bestehende Termine bearbeiten, Buchungen stornieren, Einladungen und Aufgaben sind noch keine vollständigen Demo-Funktionen.
