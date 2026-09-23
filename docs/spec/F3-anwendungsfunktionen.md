# F3 Anwendungsfunktionen

Stand: 23.09.2026 – lokaler Arbeitsstand vor Commit und Teamabnahme.

| Bereich | Umgesetzt | Noch offen |
| --- | --- | --- |
| Benutzer | Registrierung, Anmeldung, Abmeldung; BCrypt; Sitzungswechsel und CSRF-Schutz | Profil bearbeiten, Passwort ändern/zurücksetzen, Organisations-/Adminrollen |
| Kalender | Eigener Monatskalender; Termine erstellen, anzeigen und löschen | Bestehende Termine bearbeiten, Suche, externe Kalendersynchronisation |
| Terminfindung | Eigene Anfragen erstellen/anzeigen/löschen; Teilnehmer auswählen; Dauer getrennt eingeben; gemeinsame freie Intervalle berechnen | Einladungen, automatische Terminbestätigung für alle Teilnehmer |
| Ressourcen | Räume anzeigen/anlegen; eigenen Termin auswählen und buchen; Nichtverfügbarkeit und Überschneidungen inklusive Parallelbuchungen prüfen | Buchungen stornieren/freigeben, alternative Räume vorschlagen |
| Aufgaben | Noch nicht umgesetzt | Aufgaben anlegen, zuweisen, bearbeiten und abschließen |
| Benachrichtigungen | Noch nicht umgesetzt | Einladungen, Erinnerungen und Änderungsnachrichten |

Der Ersteller ist immer Teilnehmer seiner Anfrage. Private Termine, Anfragen und Buchungen sind auf das eigene Konto beschränkt. Ressourcen und die Teilnehmerübersicht sind innerhalb dieser Teaminstanz für angemeldete Benutzer gemeinsam sichtbar.

Ein Termin mit Ressourcenbuchung wird beim Löschen mit einer verständlichen Konfliktmeldung abgewiesen. Buchungen werden nicht stillschweigend gelöscht. Ein freies Zeitfenster ist ein Vorschlag, keine Reservierung.

Die Zeiten sind lokale Kalenderzeiten ohne Zeitzonen. Ein Suchzeitraum kann mehrere Tage umfassen. Testergebnisse und verbleibende Funktionsgrenzen stehen in [TESTING](../TESTING.md).
