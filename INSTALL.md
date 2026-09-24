# SyncUp – Installation und Inbetriebnahme

Stand: 24.09.2026

## 1. Überblick

SyncUp besteht aus:

- einem React-Frontend
- einem Spring-Boot-Backend
- einer PostgreSQL-Datenbank

Diese Anleitung beschreibt, wie SyncUp lokal auf einem Mac gestartet werden kann.

Für gemeinsame Tests im Team kann zusätzlich Tailscale verwendet werden.

## 2. Voraussetzungen

Für die lokale Ausführung werden benötigt:

- Git
- Java 21
- Node.js und npm
- PostgreSQL 17
- ein aktueller Webbrowser
- Homebrew für die Installation von PostgreSQL auf macOS

Für das Backend wird der Maven Wrapper verwendet. Maven muss deshalb nicht zusätzlich installiert werden.

Die installierten Versionen können mit folgenden Befehlen geprüft werden:

```bash
git --version
java -version
node --version
npm --version