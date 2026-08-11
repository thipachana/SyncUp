# 4. Lösungsstrategie

## Architekturansatz

SyncUp wird als klassische Drei-Schichten-Architektur (Three-Tier Architecture) entwickelt. Die Anwendung besteht aus einer Präsentationsschicht, einer Anwendungsschicht und einer Datenhaltungsschicht.

## Präsentationsschicht

Die Präsentationsschicht wird mit React umgesetzt. Sie stellt die Benutzeroberfläche bereit und ermöglicht die Interaktion mit den Benutzern.

## Anwendungsschicht

Die Anwendungsschicht wird mit Spring Boot entwickelt. Sie verarbeitet die Geschäftslogik, verwaltet Termine, Ressourcen und Benutzer und stellt REST-Schnittstellen zur Verfügung.

## Datenhaltungsschicht

Alle Daten werden dauerhaft in einer PostgreSQL-Datenbank gespeichert.

## Kommunikation

Das Frontend kommuniziert ausschließlich über REST mit dem Backend. Direkte Zugriffe des Frontends auf die Datenbank sind nicht vorgesehen.