# 1. Einführung und Ziele

## Ziel des Dokuments

Dieses Dokument beschreibt die Softwarearchitektur der Webanwendung SyncUp. Die Architektur zeigt, wie die Anwendung technisch aufgebaut ist und wie die einzelnen Komponenten zusammenarbeiten.

## Ziel der Architektur

Die Architektur soll eine klare Trennung zwischen Benutzeroberfläche, Geschäftslogik und Datenhaltung ermöglichen. Dadurch soll das System leicht erweiterbar, wartbar und übersichtlich bleiben.

## Überblick

SyncUp wird als Webanwendung entwickelt. Die Anwendung besteht aus einem React-Frontend, einem Spring-Boot-Backend und einer PostgreSQL-Datenbank. Die Kommunikation erfolgt über REST-Schnittstellen.