> Historischer Befund vom 23.09.2026 vor den lokalen Korrekturen. Den aktuellen Bearbeitungsstand und Nachweis beschreibt [TESTING.md](TESTING.md). Die unten aufgeführten Fehler sind nicht pauschal als weiterhin offen zu lesen.

# SyncUp – Fehlerprüfung vor den Korrekturen

Stand: 23.09.2026, ca. 16:04 Uhr. Grundlage: `main` und `origin/main` auf `3560799` sowie die vorhandenen lokalen Änderungen. GitHub wurde erneut abgeglichen; es fehlen keine neueren Commits. Keine Commits oder Pushes durch Codex.

Die grüne Oberfläche ist der aktuelle Projektstand. Frühere Ergebnisse zu einer zwischenzeitlichen anderen lokalen Implementierung gelten nicht für diesen Stand. Insbesondere sind die damals genannten 19 Backend-Tests hier nicht vorhanden.

## Tatsächlich geprüft

- Frontend-Build: erfolgreich.
- Frontend-Lint: 2.432 Warnungen, davon 2.428 ungewöhnliche Leerzeichen in AuthControl und vier ungenutzte Variablen/Parameter. Warnungen sind nicht automatisch 2.432 Funktionsfehler.
- Backend: frischer Build mit `./mvnw clean test` erfolgreich; **ein** Test (`contextLoads`), kein fachlicher Funktionstest.
- Browser: grüne Homepage erreichbar unter `http://localhost:5173/`.
- API ohne Anmeldung: `/api/auth/me` und `/api/termine` liefern 401; `/api/terminanfragen`, `/api/buchungen` und `/api/ressourcen` liefern 200.
- Unbekannte Anfrage-ID: Detail liefert 200 mit leerem Inhalt; freie Zeitfenster liefern 200 mit leerer Liste.

Schreibende Fehlerszenarien wurden nicht gegen eure vorhandenen Nutzerdaten ausgeführt. Die unten als Codebefund bezeichneten Fälle ergeben sich aus dem Quellcode; sie müssen nach einer Korrektur mit isolierten Testdaten geprüft werden. Eine vollständige Fehlerfreiheit wird durch diese Prüfung nicht zugesichert.

## Zuerst beheben

| Nr. | Problem und Auswirkung | Nötige Änderung | Vorschlag Zuständigkeit |
| --- | --- | --- | --- |
| 1 | **Zugriffsschutz lückenhaft.** Anfragen, Buchungen und Ressourcen sind ohne Anmeldung abrufbar. Die betreffenden Controller prüfen auch bei Änderungen keine Sitzung. Buchungen liefern verschachtelt Termin- und Benutzerdaten, sobald Datensätze existieren. | Zentral Anmeldung erzwingen, persönliche Daten nach Eigentümer filtern, fremde IDs beim Lesen/Ändern/Löschen abweisen. Die gemeinsame Sicht auf Ressourcen ausdrücklich festlegen. | David; Architekturprüfung Sarah |
| 2 | **Bestehende Termine können über POST überschrieben werden.** `/api/termine` akzeptiert eine vollständige Entity einschließlich `terminId`. Geprüft wird nur der mitgesendete Kalender. Eine fremde Termin-ID kann dadurch mit einem eigenen Kalender an `save` übergeben werden. Dasselbe Grundproblem besteht bei Ressourcen und Anfragen. Codebefund. | Eingabeobjekte ohne Datenbank-ID verwenden; neue Entities serverseitig erzeugen. Änderungen bestehender Objekte separat mit Eigentumsprüfung behandeln. | David |
| 3 | **Kalender und Anmeldung verschwinden bei schmalen Fenstern.** `.nav` wird unter 1.100 px ausgeblendet; `.login-placeholder` unter 720 px. Es gibt keinen Ersatz. Im aktuellen schmalen Browserfenster sichtbar nachvollzogen. | Navigation umbrechen oder mobiles Menü ergänzen. Anmeldung auf jeder Breite erreichbar halten; grünes Layout bewahren. | Sarah |
| 4 | **Anmeldung wird nicht an den Kalender weitergegeben.** AuthControl verwaltet seinen Benutzer allein. MeinKalender lädt nur beim Einhängen. Wer im bereits geöffneten Kalender die Person wechselt oder sich abmeldet, kann weiterhin die zuvor geladenen Termine sehen. Codebefund. | Gemeinsamen Sitzungszustand verwenden. Bei An-/Abmeldung Listen zurücksetzen und passend neu laden; laufende alte Antworten verwerfen. | Sarah |
| 5 | **Doppelbuchungen bei gleichzeitigen Anfragen möglich.** Konfliktprüfung und Speichern sind nicht als gesperrte Transaktion verbunden. Zwei Aufrufe können beide denselben freien Zeitraum sehen. Codebefund. | Ressource während Prüfung und Speicherung transaktional sperren oder gleichwertige Datenbankregel einsetzen. Paralleltest mit genau einer erfolgreichen Buchung ergänzen. | David |

Fundstellen: `backend/.../controller/TerminanfrageController.java`, `BuchungController.java`, `RessourceController.java`, `TerminController.java`; `frontend/src/App.css` (Responsive-Regeln), `frontend/src/components/AuthControl.jsx`, `MeinKalender.jsx`.

## Weitere funktionale Fehler

| Nr. | Problem und Auswirkung | Nötige Änderung | Vorschlag |
| --- | --- | --- | --- |
| 6 | **Terminanfragen werden serverseitig ungeprüft gespeichert.** Ungültige Zeitstrings, Dauer null/0/negativ und fehlende oder unbekannte Teilnehmer können zu falschen Ergebnissen oder Serverfehlern führen. Der vorhandene TerminanfrageService wird vom Controller nicht verwendet. | DTO tatsächlich anschließen; Pflichtfelder, gültiges Intervall, positive passende Dauer und existierende Teilnehmer vor dem Speichern prüfen. Festlegen, ob der Ersteller stets teilnimmt. | David |
| 7 | **Nicht verfügbare Ressourcen bleiben buchbar.** Das Backend prüft `verfuegbarkeit` nicht; der Frontend-Button ist auch bei „Nicht verfügbar“ aktiv. | Buchung im Backend ablehnen und Button samt Erklärung im Frontend deaktivieren. | David + Sarah |
| 8 | **Fehlende IDs und alte ungültige Buchungen können 500 auslösen.** Buchungs-IDs werden ohne Nullprüfung an Repositories übergeben; gespeicherte Zeitstrings werden ungeprüft geparst. Auch überlange Texte scheitern erst an der Datenbank. | IDs und Längen validieren; Bestandsdatenfehler verständlich behandeln; einheitliche Fehlerantworten mit 400/404/409. | David |
| 9 | **Unbekannte Anfrage sieht wie eine gültige leere Suche aus.** Detail und Zeitfenster liefern bei unbekannter ID 200. Live bestätigt. | Beide Endpunkte mit 404 und verständlicher Meldung beantworten. | David |
| 10 | **Terminlöschen mit verknüpfter Buchung scheitert.** MeinKalenderController löscht direkt; Buchung hat einen Fremdschlüssel auf Termin. Es gibt keine Behandlung dieser Beziehung. Codebefund. | Fachregel festlegen: verknüpfte Buchungen bewusst mit stornieren oder Löschen mit verständlichem Konflikt ablehnen. | David; Regel mit Thipachana abstimmen |
| 11 | **Registrierung prüft fast nur leere Werte.** E-Mail-Format, Feldlängen und Passwortlänge werden nicht serverseitig geprüft. Überlange BCrypt-Passwörter bzw. Datenbankfelder können technische Fehler ergeben. Zwei gleichzeitige Registrierungen derselben E-Mail können die Vorprüfung passieren. | Eingaben validieren, BCrypt-Grenze berücksichtigen, Datenbank-Duplikate als 409 behandeln. | David |
| 12 | **Sitzungs-ID bleibt beim Login bestehen.** Es wird nur `session.setAttribute` gesetzt; keine Erneuerung der ID nach erfolgreicher Anmeldung. | Sitzungs-ID beim Login erneuern oder einen etablierten Authentifizierungsmechanismus einsetzen; Sitzungs-/CSRF-Schutz durchgängig gestalten. Keine bloße CORS-Freigabe als Zugriffsschutz behandeln. | David + Sarah |
| 13 | **Abmeldung kann Erfolg vortäuschen.** AuthControl ignoriert HTTP-Fehler und setzt auch bei Verbindungsfehler den Benutzer auf null. Die Serversitzung kann dann weiter gültig sein. | Nur bestätigte Abmeldung als erfolgreich anzeigen; Fehler sichtbar machen und erneutes Abmelden anbieten. | Sarah |
| 14 | **Backend-Adressen werden uneinheitlich gebildet.** Auth/Kalender fallen auf `localhost:8080` zurück; App/Ressourcen verwenden die Umgebungsvariable direkt. Ohne Variable wird daraus `undefined/api/...`. Bei `127.0.0.1` mischen sich außerdem Hostnamen und Sitzungscookies. | Einheitliche API-Basis für alle Komponenten, vorzugsweise relative `/api`-Pfade mit Vite-Proxy. `.env.example` und Installationsanleitung angleichen. Der Proxy wurde lokal zum Start bereits ergänzt, die uneinheitlichen Komponenten bleiben offen. | Sarah |
| 15 | **Ladefehler werden als leere Ergebnisse angezeigt.** Ressourcen erhält `ladefehler`, verwendet es aber nicht. Anfragen und Kalender protokollieren Ladefehler oft nur in der Konsole. | Sichtbare Zustände für Laden, Fehler und tatsächlich leere Listen plus Wiederholen anbieten. | Sarah |
| 16 | **Zeitfenster können der falschen Anfrage zugeordnet werden.** Schnell nacheinander ausgelöste Abrufe sind nicht abgesichert. Eine langsame alte Antwort kann eine neuere überschreiben oder nach dem Löschen wieder erscheinen. | Anfrage-ID bei Ergebnissen speichern; veraltete Abrufe abbrechen bzw. Antworten ignorieren. | Sarah |
| 17 | **Mehrtagige Zeitfenster verlieren das Enddatum.** App gibt an TimeSlot nur das Startdatum und zwei Uhrzeiten weiter. Ein Zeitraum über Mitternacht wird dadurch missverständlich dargestellt. | Start- und Enddatum einschließlich Uhrzeiten anzeigen. | Sarah |
| 18 | **Mehrfachklicks können doppelte Datensätze erzeugen.** Anfrage erstellen, Termin speichern und Ressource buchen haben keinen ausstehenden Zustand, der weitere Klicks verhindert. | Während laufendem Speichern den jeweiligen Button deaktivieren und Ergebnis/Fehler anzeigen. | Sarah |
| 19 | **Ressourcenbuchung verwendet feste Beispieldaten.** Vorbelegt sind Termin-ID 1 und der 22.09.2026. Diese gehören nicht notwendig zum angemeldeten Benutzer und sind inzwischen vergangen. | Eigene vorhandene Termine auswählbar machen und deren Zeitraum übernehmen. Backend trotzdem unabhängig prüfen. | Sarah + David |
| 20 | **Kalendernamen können beim ersten gleichzeitigen Speichern doppelt entstehen.** Zwei Aufrufe finden zunächst keinen persönlichen Kalender und erstellen beide einen. Codebefund. | Kalender bei Registrierung anlegen oder die nachträgliche Anlage transaktional mit geeigneter Sperre absichern. | David |

## GitHub-Stand gegenüber lokalen Änderungen

Im unveränderten GitHub-Stand `3560799` verwendet App.jsx `benutzer`, `selectedBenutzer`, `benutzerMessage` und `loadBenutzer`, ohne sie anzulegen. Das kann die Homepage bereits beim Rendern abbrechen. **Im aktuellen lokalen App.jsx ist die fehlende Definition bereits ergänzt.** Diese Korrektur ist noch nicht committet und damit noch nicht für einen frischen Clone verfügbar.

Aktuell sind außerdem `frontend/src/components/MeinKalender.jsx`, `frontend/package-lock.json` und `frontend/vite.config.js` lokal verändert. Diese Änderungen einzeln prüfen; nicht durch ein Zurücksetzen oder ein unbedachtes Übernehmen anderer Dateien verlieren. Ein grüner Build allein erkennt nicht jeden Fehler durch undefinierte Variablen zur Laufzeit.

## Tests und Dokumentation

| Nr. | Befund | Nächster Schritt | Vorschlag |
| --- | --- | --- | --- |
| 21 | Maven führt nur `contextLoads` aus. Fehlerfälle, Rechte, Zeitfenster und Parallelbuchungen werden dadurch nicht geprüft. | Fachliche Tests mit isolierter Testdatenbank ergänzen. | David |
| 22 | Das Smoke-Skript schreibt fest nach `localhost:8080`, legt bei jedem Lauf ein Konto mit bekanntem Testpasswort an und löscht dieses Konto nicht wieder. Es ist kein isolierter Testlauf. | Testadresse konfigurierbar machen, ausschließlich eigene Testdatenbank verwenden, Testdaten nachvollziehbar aufräumen. | David |
| 23 | 2.432 Lint-Warnungen: vor allem Sonderleerzeichen in AuthControl, außerdem unbehandelte/ungenutzte Variablen. | Sonderleerzeichen normalisieren; Fehlerbehandlung tatsächlich verwenden; ungenutzten Code entfernen. | Sarah |
| 24 | B1 behauptet weiterhin, Login/Registrierung seien nicht umgesetzt. P2 enthält veraltete Aussagen zur Teilnehmerwahl und „keine Service-Schicht“. Kapitel 09 benutzt andere ADR-Nummern als die einzelnen ADR-Dateien. TESTING endet mit offenem Codeblock und einem einzelnen `q`. | Dokumente mit diesem tatsächlichen Stand abgleichen, ADR-Verweise vereinheitlichen, Testnachweise mit Datum und geprüftem Commit versehen. | Thipachana, fachliche Rückprüfung David/Sarah |

## Empfohlene Reihenfolge

1. Sarah: lokal bereits vorhandene Startkorrektur prüfen; mobile Navigation und Anmeldung erreichbar machen.
2. David: Zugriffsschutz und sichere Eingabeobjekte; danach Zeitvalidierung und transaktionale Buchungen.
3. Sarah: gemeinsamen Anmeldestatus, API-Basis und sichtbare Fehlerzustände korrigieren.
4. David und Sarah: gemeinsamer Ablauf mit zwei Testkonten, An-/Abmeldung, Kalender, Teilnehmern und Buchungskonflikt in einer getrennten Testumgebung.
5. Thipachana: Ergebnisse und Specs aktualisieren; erst den gemeinsam geprüften Stand durch die jeweiligen Bearbeiter committen und pushen lassen.

Die Zuständigkeiten sind ein Vorschlag zur Arbeitsverteilung, keine Behauptung darüber, wer einen Fehler verursacht hat. Die grüne Gestaltung soll bei den Korrekturen bestehen bleiben.
