# Übergabe der lokalen Korrekturen

Basis: origin/main adbb031, 23.09.2026. Es wurden keine Commits erstellt und nichts gepusht.
Die Pakete enthalten auch bereits vorher lokal vorhandene Änderungen. Jede zuständige Person prüft ihren gesamten Diff und committet unter ihrem eigenen Namen.

Die aktuellen Pakete enthalten zusätzlich UC5: Terminbearbeitung und Benachrichtigungen (siehe UC5-TERMIN-BEARBEITEN.md). Sie ersetzen die früheren Pakete vollständig.

## Reihenfolge

1. David: Backend, Datenbankschema, Tests und Testskript. Nach erfolgreicher Prüfung pushen.
2. Sarah: Frontend und Architekturtexte. Erst nach Davids Push beginnen; beide Änderungen gehören für die neue CSRF-Anmeldung zusammen.
3. Thipachana: übrige Spezifikationen, README, Installation, Test- und Demo-Dokumentation. Nach Sarahs Push beginnen.

Ilias sollte die Integration anschließend gegenlesen. Aufgaben, Einladungen und weitere geplante Funktionen sind nicht automatisch durch dieses Paket fertig.

## Vorbereitung

Das Übergabe-ZIP an alle drei weitergeben und nach ~/Downloads/syncup-uebergabe entpacken. Die Befehle unten nutzen jeweils eine neue Arbeitskopie, damit vorhandene uncommittete Änderungen erhalten bleiben. Falls der Zielordner schon existiert, nicht löschen: einen anderen neuen Namen wählen. Bei Konflikten oder fehlgeschlagenen Tests stoppen und gemeinsam prüfen.

### David

```bash
cd ~
git clone https://github.com/thipachana/SyncUp.git SyncUp-David-Pruefung
cd SyncUp-David-Pruefung
git apply --check ~/Downloads/syncup-uebergabe/01-David.patch
git apply ~/Downloads/syncup-uebergabe/01-David.patch
cd backend
./mvnw test
cd ..
git diff --check
git diff
git status --short
git add backend database scripts
git commit -m "fix(backend): secure sessions and validate scheduling and bookings"
git push origin main
```

### Sarah – nach Davids erfolgreichem Push

```bash
cd ~
git clone https://github.com/thipachana/SyncUp.git SyncUp-Sarah-Pruefung
cd SyncUp-Sarah-Pruefung
git apply --check ~/Downloads/syncup-uebergabe/02-Sarah.patch
git apply ~/Downloads/syncup-uebergabe/02-Sarah.patch
cd frontend
npm ci
npm test
npm run lint
npm run build
cd ..
git diff --check
git diff
git status --short
git add frontend docs/spec/arch
git commit -m "fix(frontend): connect authenticated calendar and booking flows"
git push origin main
```

### Thipachana – nach Sarahs erfolgreichem Push

```bash
cd ~
git clone https://github.com/thipachana/SyncUp.git SyncUp-Thipachana-Pruefung
cd SyncUp-Thipachana-Pruefung
git apply --check ~/Downloads/syncup-uebergabe/03-Thipachana.patch
git apply ~/Downloads/syncup-uebergabe/03-Thipachana.patch
git diff --check
git diff
git status --short
git add README.md INSTALL.md docs
git commit -m "docs: align specifications and verification with current implementation"
git push origin main
```

Die Prüfungen und den Diff jeweils zuerst ansehen; Commit und Push erst danach ausführen. Wenn main geschützt ist, stattdessen einen eigenen Branch mit git switch -c codex/<passender-name> anlegen und diesen pushen, anschließend Pull Request erstellen. Keine Force-Pushes verwenden.

Die alte Arbeitskopie /Users/thipachana/SyncUp bleibt absichtlich uncommittet. Dort später kein blindes git pull oder reset --hard ausführen. Nach erfolgreicher Übergabe die neue geprüfte Arbeitskopie verwenden.
