# E1 Leseanleitung

Stand: 24.09.2026

## Zweck

Diese Dokumentation beschreibt die Anforderungen, den Aufbau und den finalen Umsetzungsstand von SyncUp.

Sie soll einen Überblick darüber geben, welche Funktionen SyncUp umfasst, wie die Anwendung fachlich aufgebaut ist und welche Bereiche tatsächlich umgesetzt wurden.

## Aufbau der Spezifikation

Die Spezifikation orientiert sich an den Siedersleben-Bausteinen.

| Bereich | Inhalt |
| --- | --- |
| P1 | Ziele und Rahmenbedingungen |
| P2 | Überblick über die Architektur |
| F1 | Geschäftsprozesse |
| F2 | Anwendungsfälle |
| F3 | Funktionen und aktueller Umsetzungsstand |
| D1 | Datenmodell |
| D2 | Datentypen |
| B1 | Benutzeroberfläche und Bedienabläufe |
| B2 | Batch-Verarbeitung |
| B3 | Druckausgaben |
| S1 | Nachbarsysteme |
| S2 | Datenmigration |
| S3 | Inbetriebnahme |
| N1 | Nichtfunktionale Anforderungen |
| N2 | Übergreifende Konzepte |
| E1 | Leseanleitung |
| E2 | Glossar |

Bereiche, die für SyncUp nicht benötigt werden, werden als „nicht anwendbar“ gekennzeichnet und entsprechend begründet.

## Lesereihenfolge

Für einen ersten Überblick können zuerst P1 und P2 gelesen werden.

F1 bis F3 beschreiben anschließend die fachlichen Abläufe, Anwendungsfälle und den aktuellen Funktionsumfang von SyncUp.

D1 und D2 beschreiben die verwendeten Datenobjekte und deren Beziehungen.

B1 beschreibt die Benutzeroberfläche und wichtige Bedienabläufe.

S1 bis S3 behandeln Nachbarsysteme, Datenmigration und Inbetriebnahme.

N1 und N2 enthalten zusätzliche Anforderungen und querschnittliche Konzepte, die für mehrere Bereiche der Anwendung relevant sind.

Wichtige Begriffe werden in E2 erklärt.

## Finaler Umsetzungsstand

In der Spezifikation werden die fachlichen Anforderungen und der tatsächliche Umsetzungsstand beschrieben.

Für einzelne Funktionen wird angegeben, ob sie umgesetzt, nicht umgesetzt oder nicht Bestandteil des finalen Funktionsumfangs sind.

Eine Übersicht über den aktuellen Funktionsstand befindet sich in F3.

Die Spezifikation wurde mit dem finalen Stand der Implementierung abgeglichen, damit dokumentierte Funktionen und tatsächliches Systemverhalten möglichst übereinstimmen.

## Zusammenhang mit Architektur und Code

Neben der Spezifikation gibt es eine separate Architekturdokumentation nach arc42.

Die Spezifikation beschreibt hauptsächlich, was SyncUp fachlich leisten soll.

Die Architekturdokumentation beschreibt, wie das System technisch aufgebaut ist und wie die einzelnen Komponenten zusammenarbeiten.

Spezifikation, Architektur und Quellcode sollen miteinander konsistent sein.

Wichtige Architekturentscheidungen werden zusätzlich in ADRs dokumentiert.

## Installation und Tests

Die Anleitung zur Installation und zum Start von SyncUp befindet sich in `INSTALL.md` und in S3 „Inbetriebnahme“.

Die dokumentierten Testfälle und Testergebnisse befinden sich in `docs/TESTING.md`.

Zusätzlich beschreibt `docs/DEMO.md` einen möglichen Ablauf zur Demonstration der wichtigsten Funktionen.

## Pflege der Dokumentation

Wenn sich wichtige Funktionen oder technische Bestandteile ändern, sollen auch die dazugehörigen Dokumente angepasst werden.

Der Stand der jeweiligen Dokumentation ist durch das angegebene Datum erkennbar.

## Eingesetzte KI-Werkzeuge

Im Projekt wurden KI-Werkzeuge unterstützend eingesetzt.

Dazu gehören unter anderem ChatGPT und, sofern tatsächlich verwendet, Codex.

Einsatzbereiche waren insbesondere:

- Unterstützung bei Code-Entwürfen und Refactoring
- Fehlersuche und Analyse von Implementierungsproblemen
- Überarbeitung und Strukturierung der Dokumentation
- Unterstützung bei Architekturentscheidungen
- Unterstützung bei der Formulierung von Testfällen und technischen Beschreibungen

Die erzeugten Vorschläge wurden durch das Projektteam geprüft, an die vorhandene Implementierung angepasst und abhängig vom jeweiligen Bereich durch Builds, automatisierte Tests und manuelle Funktionsprüfungen überprüft.

Die endgültige Auswahl und Übernahme von Änderungen erfolgte durch das Projektteam.

Die Teammitglieder sollen die übernommenen Inhalte nachvollziehen und im Rahmen der Präsentation sowie des Code Walkthroughs erklären können.

Eine ausführlichere Beschreibung der eingesetzten KI-Werkzeuge befindet sich in der separaten KI-Dokumentation.