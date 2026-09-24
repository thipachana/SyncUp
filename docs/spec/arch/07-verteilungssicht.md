# 7. Verteilungssicht

## Überblick

Die Verteilungssicht beschreibt, auf welchen technischen Umgebungen die einzelnen Bestandteile von SyncUp ausgeführt werden und wie sie miteinander verbunden sind.

SyncUp kann vollständig lokal auf einem Rechner betrieben werden.

Für gemeinsame Tests im Team wird zusätzlich eine interne Testumgebung über Tailscale verwendet.

## Lokale Entwicklungsumgebung

Während der Entwicklung können Frontend, Backend und Datenbank lokal auf einem Rechner ausgeführt werden.

- Das React-Frontend wird mit Vite gestartet und über einen Webbrowser verwendet.
- Das Spring-Boot-Backend läuft als eigenständige Serveranwendung.
- PostgreSQL übernimmt die persistente Datenhaltung.
- Frontend und Backend kommunizieren über HTTP und REST.
- Das Backend greift über Spring Data JPA und Hibernate auf PostgreSQL zu.
- Das Frontend greift nicht direkt auf die Datenbank zu.

Die Standardports sind:

- Frontend: `5173`
- Backend: `8080`
- PostgreSQL: `5432`

## Lokale Verteilung

```text
Benutzer
   |
   v
Webbrowser
   |
   v
React-Frontend
   |
   | REST / HTTP
   v
Spring-Boot-Backend
   |
   | Spring Data JPA / Hibernate
   v
PostgreSQL