# ADR 001 – Verwendung von Spring Boot als Backend

## Status

Akzeptiert

## Kontext

Für SyncUp wird ein Backend benötigt, das die Geschäftslogik verarbeitet, REST-Schnittstellen bereitstellt und mit der Datenbank kommuniziert.

## Entscheidung

Für die Entwicklung des Backends wird Spring Boot verwendet.

## Begründung

Spring Boot ermöglicht eine strukturierte Entwicklung von Webanwendungen und bietet eine einfache Erstellung von REST-Schnittstellen. Das Framework ist gut dokumentiert, weit verbreitet und eignet sich für die Umsetzung der geplanten Architektur von SyncUp.

## Konsequenzen

Das Backend wird vollständig mit Spring Boot umgesetzt. Alle Anfragen des Frontends werden über REST-Schnittstellen verarbeitet und anschließend mit der PostgreSQL-Datenbank verbunden.