# E1 Leseanleitung

Stand: 22.09.2026

## Zweck

Diese Dokumentation beschreibt die Anforderungen, den Aufbau und den aktuellen Stand von SyncUp.

Sie soll einen Überblick darüber geben, was mit SyncUp geplant ist, welche Funktionen bereits umgesetzt wurden und wie das System aufgebaut ist.

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

Bereiche, die für SyncUp nicht benötigt werden, werden als „nicht anwendbar“ gekennzeichnet.

## Lesereihenfolge

Für einen ersten Überblick können zuerst P1 und P2 gelesen werden.

F1 bis F3 beschreiben anschließend die geplanten Abläufe und Funktionen von SyncUp sowie den aktuellen Umsetzungsstand.

D1 und D2 beschreiben die verwendeten Daten und deren Beziehungen.

B1 beschreibt die Benutzeroberfläche. S1 bis S3 behandeln externe Systeme und die Inbetriebnahme.

N1 und N2 enthalten zusätzliche Anforderungen und Konzepte, die für mehrere Bereiche der Anwendung wichtig sind.

Wichtige Begriffe werden in E2 erklärt.

## Geplanter und aktueller Stand

In der Spezifikation werden sowohl geplante als auch bereits umgesetzte Funktionen beschrieben.

Dabei wird zwischen folgenden Zuständen unterschieden:

- **Geplant / offen:** Die Funktion ist vorgesehen, aber noch nicht umgesetzt.
- **Teilweise umgesetzt:** Ein Teil der Funktion funktioniert bereits.
- **Umgesetzt:** Die Funktion ist im aktuellen Stand vorhanden.

Eine Übersicht über den aktuellen Stand befindet sich in F3.

Dabei wird auch berücksichtigt, dass eine Funktion bereits im Backend vorhanden sein kann, obwohl sie noch nicht vollständig über das Frontend verwendet werden kann.

## Zusammenhang mit Architektur und Code

Neben der Spezifikation gibt es eine separate Architekturdokumentation nach arc42.

Die Spezifikation beschreibt hauptsächlich, was SyncUp können soll. Die Architekturdokumentation beschreibt, wie das System technisch aufgebaut ist.

Spezifikation, Architektur und Quellcode sollen dabei zusammenpassen.

Wichtige Architekturentscheidungen werden zusätzlich in ADRs dokumentiert.

## Installation und Tests

Die Anleitung zur Installation und zum Start von SyncUp befindet sich in `INSTALL.md` und in S3 „Inbetriebnahme“.

Für die Überprüfung der Funktionen gibt es außerdem eine Testdokumentation.

Dadurch kann geprüft werden, ob die bereits umgesetzten Funktionen wie vorgesehen funktionieren.

## Pflege der Dokumentation

Wenn sich wichtige Funktionen oder technische Bestandteile ändern, sollen auch die dazugehörigen Dokumente angepasst werden.

Der aktuelle Stand der Dokumentation ist jeweils durch das angegebene Datum erkennbar.

## Eingesetzte KI-Werkzeuge

Im Projekt wurden KI-Werkzeuge zur Unterstützung eingesetzt.

Die Verwendung der KI sowie die jeweiligen Einsatzbereiche werden in der dafür vorgesehenen Dokumentation festgehalten.

Die übernommenen Inhalte werden vom Team geprüft. Jedes Teammitglied soll die Inhalte seines Bereichs verstehen und erklären können.