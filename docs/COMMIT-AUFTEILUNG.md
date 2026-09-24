# Übergabe und Integration

Stand: 24.09.2026

## Zweck

Dieses Dokument beschreibt, wie die letzten Änderungen im Team zusammengeführt, abgestimmt und vor der finalen Abgabe geprüft werden.

## Zuständigkeiten

Die Aufgaben sind im Team schwerpunktmäßig wie folgt verteilt:

- **David Cabas Canella:** Requirements beziehungsweise Spezifikation sowie Unterstützung bei Backend- und Datenbankthemen
- **Sarah Kouskous:** Softwarearchitektur und Frontend
- **Thipachana Clarian Kenady:** Projektleitung sowie Koordination der finalen Dokumentation und Abgabe
- **Ilias Jelloli:** Implementierung und technische Integration

Die Bereiche werden nicht vollständig getrennt bearbeitet. Bei Änderungen, die mehrere Komponenten betreffen, erfolgt eine Abstimmung im Team.

## Vorgehen

Änderungen werden vor der Übernahme in den finalen Projektstand geprüft.

Dabei werden insbesondere folgende Punkte berücksichtigt:

- relevante Builds und Tests werden ausgeführt,
- das tatsächliche Systemverhalten wird mit Spezifikation und Architektur abgeglichen,
- unbeabsichtigte Änderungen sollen vermieden werden,
- Commit-Nachrichten sollen dem Conventional-Commits-Schema folgen,
- Änderungen werden über den gemeinsamen `main`-Branch zusammengeführt,
- Force-Pushes auf den gemeinsamen Projektstand werden vermieden.

Bei Funktionen, die mehrere Komponenten betreffen, werden Frontend, Backend und Datenbank im Zusammenspiel geprüft.

Dazu gehören beispielsweise:

- Erstellung gemeinsamer Termine,
- Benachrichtigungen,
- Berechnung der Raumverfügbarkeit,
- Raumreservierungen,
- Verhinderung von Buchungskonflikten,
- Löschen von Terminen mit zugehöriger Raumbuchung.

## Abschlussprüfung

Vor der finalen Abgabe wird geprüft, ob:

- alle relevanten Änderungen auf `main` vorhanden sind,
- keine unbeabsichtigten lokalen Änderungen mehr offen sind,
- die Backend-Tests erfolgreich ausgeführt werden,
- die Frontend-Tests erfolgreich ausgeführt werden,
- der Frontend-Build erfolgreich erstellt werden kann,
- das Linting keine Fehler enthält,
- die zentralen Funktionen manuell geprüft wurden,
- die gemeinsame Testumgebung funktioniert,
- Spezifikation, Architektur und Implementierung zusammenpassen,
- README, Installation, Test- und Demo-Dokumentation dem finalen Stand entsprechen,
- die finale Version mit einem annotierten Git-Tag markiert wird.

## Finale Übergabe

Für die Abgabe wird der finale Stand des `main`-Branches verwendet.

Der verwendete Git-Tag verweist auf den finalen Commit der Abgabeversion.

Die Projektleitung dokumentiert für die Abgabe insbesondere:

- Repository
- finalen Git-Tag
- zugehörigen Commit
- beteiligte Teammitglieder