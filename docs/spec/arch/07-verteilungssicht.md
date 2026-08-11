# 7. Verteilungssicht

## Überblick

Die Verteilungssicht beschreibt, auf welchen technischen Umgebungen die einzelnen Bestandteile von SyncUp ausgeführt werden.

## Entwicklungsumgebung

Während der Entwicklung werden Frontend, Backend und Datenbank zunächst lokal ausgeführt.

- Das React-Frontend wird über einen Webbrowser verwendet.
- Das Spring-Boot-Backend läuft als eigenständige Serveranwendung.
- PostgreSQL stellt die persistente Datenhaltung bereit.

## Geplante Verteilung

```text
Benutzer
   |
   v
Webbrowser
   |
   v
React Frontend
   |
   v
Spring Boot Backend
   |
   v
PostgreSQL