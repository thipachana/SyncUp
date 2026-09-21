# 7. Verteilungssicht

## Überblick

Die Verteilungssicht beschreibt, auf welchen technischen Umgebungen die einzelnen Bestandteile von SyncUp ausgeführt werden.

## Entwicklungsumgebung

Während der Entwicklung werden Frontend, Backend und Datenbank lokal ausgeführt.

- Das React-Frontend wird über einen Webbrowser verwendet.
- Das Spring-Boot-Backend läuft als eigenständige Serveranwendung.
- PostgreSQL stellt die persistente Datenhaltung bereit.
- Frontend und Backend kommunizieren über HTTP und REST.
- Das Backend greift auf die PostgreSQL-Datenbank zu.

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
```

## Lokale Kommunikation

Das Frontend wird während der Entwicklung über Vite gestartet. Die Adresse des Backends wird im Frontend über `VITE_API_URL` konfiguriert.

Das Spring-Boot-Backend läuft standardmäßig auf Port `8080` und stellt die REST-Schnittstellen bereit.

PostgreSQL läuft lokal auf Port `5432` und wird ausschließlich vom Backend angesprochen.