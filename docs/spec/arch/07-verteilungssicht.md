# 7. Verteilungssicht

## Überblick

Die Verteilungssicht beschreibt, auf welchen technischen Umgebungen die einzelnen Bestandteile von SyncUp ausgeführt werden und wie sie miteinander verbunden sind.

## Lokale Entwicklungsumgebung

Während der Entwicklung können Frontend, Backend und Datenbank lokal auf einem Rechner ausgeführt werden.

- Das React-Frontend wird mit Vite gestartet und über einen Webbrowser verwendet.
- Das Spring-Boot-Backend läuft als eigenständige Serveranwendung.
- PostgreSQL übernimmt die persistente Datenhaltung.
- Frontend und Backend kommunizieren über HTTP und REST.
- Das Backend greift über JPA und Hibernate auf PostgreSQL zu.
- Das Frontend greift nicht direkt auf die Datenbank zu.

## Verteilung

```text
Benutzer
   |
   v
Webbrowser
   |
   v
React Frontend
   |
   | REST / HTTP
   v
Spring Boot Backend
   |
   | JPA / Hibernate
   v
PostgreSQL