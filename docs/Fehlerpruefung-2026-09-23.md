# Historische Fehlerprüfung vor den Korrekturen

Stand: 23.09.2026

> Dieses Dokument beschreibt einen früheren Zwischenstand von SyncUp vor den letzten Korrekturen.
> Die hier genannten Punkte sind deshalb nicht automatisch noch aktuell.
> Der aktuelle Stand der Tests und der noch vorhandenen Einschränkungen wird in `TESTING.md` dokumentiert.

## Zweck

Während der Entwicklung wurde der damalige Stand von SyncUp genauer geprüft.

Dabei wurden verschiedene technische und funktionale Probleme gefunden und anschließend im Team bearbeitet.

Dieses Dokument dient nur noch dazu, den damaligen Entwicklungsstand und die durchgeführte Fehleranalyse nachvollziehbar zu machen.

## Damaliger Prüfstand

Zum Zeitpunkt der Prüfung wurden unter anderem folgende Bereiche betrachtet:

- Frontend-Build
- Backend-Build
- Anmeldung und Sitzungsverwaltung
- Zugriff auf persönliche Daten
- Terminanfragen
- Berechnung freier Zeitfenster
- Kalender
- Ressourcen und Buchungen
- Fehlerbehandlung
- responsive Darstellung
- API-Kommunikation
- Tests und Dokumentation

Die grüne Benutzeroberfläche war zu diesem Zeitpunkt bereits die Grundlage für die weitere Entwicklung.

## Gefundene Themen

Bei der Prüfung wurden unter anderem Probleme in folgenden Bereichen festgestellt.

### Zugriffsschutz

Einige API-Endpunkte waren zu diesem Zeitpunkt noch nicht vollständig durch eine Anmeldung geschützt.

Persönliche Daten mussten stärker nach dem jeweiligen Benutzer gefiltert und Eigentumsprüfungen ergänzt werden.

### Anmeldung und Sitzungsverwaltung

Der Anmeldestatus wurde im Frontend noch nicht überall einheitlich verwendet.

Dadurch konnten nach einem Benutzerwechsel oder einer Abmeldung teilweise noch zuvor geladene Daten angezeigt werden.

Auch die Behandlung von Sitzungen und CSRF-Schutz wurde überprüft.

### Termine und Terminanfragen

Bei Terminanfragen fehlten teilweise Prüfungen für ungültige Eingaben.

Dazu gehörten zum Beispiel:

- ungültige Zeitangaben
- fehlende Teilnehmer
- ungültige Dauer
- nicht vorhandene IDs

Auch die Zuordnung freier Zeitfenster zur richtigen Terminanfrage wurde überprüft.

### Ressourcenbuchungen

Bei Ressourcenbuchungen wurden mehrere Punkte geprüft.

Dazu gehörten:

- Überschneidungen bei Buchungen
- parallele Buchungsanfragen
- nicht verfügbare Ressourcen
- ungültige IDs
- Buchungen für bestehende Termine
- Verhalten beim Löschen eines Termins mit vorhandener Buchung

Außerdem wurde geprüft, wie verhindert werden kann, dass derselbe Raum gleichzeitig mehrfach gebucht wird.

### Benutzeroberfläche

Im Frontend wurden unter anderem folgende Punkte überprüft:

- Navigation bei kleineren Bildschirmgrößen
- Erreichbarkeit von Anmeldung und Kalender
- sichtbare Fehlermeldungen
- Mehrfachklicks beim Speichern
- Darstellung mehrtägiger Zeitfenster
- Laden und Zurücksetzen persönlicher Daten
- Auswahl vorhandener Termine bei einer Ressourcenbuchung

### Datenvalidierung

Für mehrere Eingaben wurden zusätzliche Prüfungen betrachtet.

Dazu gehörten zum Beispiel:

- E-Mail-Adresse
- Passwortlänge
- Textlängen
- Zeiträume
- Termindauer
- vorhandene Benutzer
- vorhandene Termine und Ressourcen

## Tests

Zum damaligen Zeitpunkt waren nur wenige automatisierte Tests vorhanden.

Deshalb wurde empfohlen, zusätzlich fachliche Tests für wichtige Abläufe einzubauen.

Besonders relevant waren:

- Anmeldung und Zugriffsschutz
- Terminanfragen
- Berechnung freier Zeitfenster
- Ressourcenbuchungen
- parallele Buchungsversuche
- ungültige Eingaben
- Rechte und Eigentumsprüfungen

Zusätzlich wurden manuelle Tests über die Benutzeroberfläche durchgeführt.

Der aktuelle Stand der Tests befindet sich in `TESTING.md`.

## Dokumentation

Bei der damaligen Prüfung wurden auch Unterschiede zwischen Dokumentation und Implementierung gefunden.

Deshalb wurden die Spezifikation, Architekturtexte und Testdokumentation anschließend mit dem tatsächlichen Stand der Anwendung abgeglichen.

Dabei wurden insbesondere folgende Dokumente überprüft:

- B1 Dialogspezifikation
- F1 Geschäftsprozesse
- F2 Anwendungsfälle
- F3 Anwendungsfunktionen
- P1 Ziele und Rahmenbedingungen
- P2 Architekturüberblick
- D1 Datenmodell
- N1 Nichtfunktionale Anforderungen
- N2 Querschnittskonzepte
- INSTALL.md
- TESTING.md
- DEMO.md

## Zuständigkeiten während der Korrektur

Die gefundenen Punkte wurden im Team nach den jeweiligen Aufgabenbereichen aufgeteilt.

- David kümmerte sich hauptsächlich um Backend, Datenbank und Validierung.
- Sarah kümmerte sich hauptsächlich um Frontend und Benutzeroberfläche.
- Thipachana kümmerte sich hauptsächlich um Spezifikation und Projektdokumentation.
- Ilias unterstützte bei der technischen Integration und Backend-Implementierung.

Die Aufteilung beschreibt die Zusammenarbeit bei der Bearbeitung und nicht, wer einen Fehler verursacht hat.

## Aktueller Stand

Dieses Dokument beschreibt nicht den finalen Stand von SyncUp.

Für den aktuellen Funktionsumfang gelten die aktuellen Spezifikationsdokumente.

Der aktuelle Teststand und bekannte Einschränkungen befinden sich in:

`docs/TESTING.md`

Die finale Anwendung soll anhand der dort beschriebenen Tests sowie des Demo-Ablaufs überprüft werden.