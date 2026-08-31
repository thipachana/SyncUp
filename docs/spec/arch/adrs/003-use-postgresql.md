# ADR 003 – Verwendung von PostgreSQL als Datenbank

## Status

Akzeptiert

## Kontext

SyncUp benötigt eine Datenbank, um Benutzer, Kalender, Termine, Ressourcen und Buchungen dauerhaft zu speichern. Zwischen diesen Daten bestehen mehrere Beziehungen, die zuverlässig abgebildet werden müssen.

## Alternativen

Als Alternativen wurden SQLite und MongoDB betrachtet.

SQLite wäre einfacher einzurichten, eignet sich aber eher für kleinere Anwendungen und lokale Nutzung.

MongoDB bietet eine flexible dokumentenbasierte Speicherung, ist für die stark strukturierten und miteinander verknüpften Daten von SyncUp jedoch weniger passend.

## Entscheidung

Für die Datenhaltung wird PostgreSQL verwendet.

## Begründung

PostgreSQL ist eine relationale Datenbank und eignet sich gut für strukturierte Daten mit klaren Beziehungen. Die geplanten Objekte von SyncUp, wie Benutzer, Kalender, Termine und Ressourcen, lassen sich damit übersichtlich miteinander verknüpfen.

Außerdem lässt sich PostgreSQL gut mit Spring Boot verbinden und bietet eine stabile Grundlage für eine spätere Erweiterung der Anwendung.

## Konsequenzen

Die Daten werden in relationalen Tabellen gespeichert. Beziehungen zwischen den Daten müssen im Datenmodell sauber definiert werden.

Für die Entwicklung muss eine PostgreSQL-Datenbank eingerichtet und mit dem Spring-Boot-Backend verbunden werden.