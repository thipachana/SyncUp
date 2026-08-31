# ADR 002 – Verwendung von React als Frontend

## Status

Akzeptiert

## Kontext

Für SyncUp wird eine Benutzeroberfläche benötigt, über die Nutzer Termine, Kalender und Ressourcen verwalten können. Die Oberfläche soll übersichtlich aufgebaut sein und Änderungen dynamisch darstellen können.

## Alternativen

Als mögliche Alternativen wurden eine einfache serverseitige Oberfläche mit Thymeleaf sowie andere JavaScript-Frameworks betrachtet.

## Entscheidung

Für die Entwicklung des Frontends wird React verwendet.

## Begründung

React eignet sich gut für interaktive Webanwendungen und ermöglicht eine klare Aufteilung der Benutzeroberfläche in einzelne Komponenten. Außerdem lässt sich React gut mit einem Spring-Boot-Backend über REST-Schnittstellen verbinden.

## Konsequenzen

Frontend und Backend werden getrennt entwickelt. Dadurch entsteht eine klare Trennung zwischen Benutzeroberfläche und Geschäftslogik. Gleichzeitig muss das Team neben Java auch mit React beziehungsweise JavaScript oder TypeScript arbeiten.