# S1 Nachbarsysteme

Stand: 24.09.2026

## Beschreibung

SyncUp ist in der aktuellen Version nicht direkt mit fachlichen externen Systemen verbunden.

Benutzer, Kalender, Termine, Terminanfragen, Ressourcen, Buchungen und Benachrichtigungen werden innerhalb von SyncUp verwaltet und in der eigenen PostgreSQL-Datenbank gespeichert.

Eine direkte fachliche Schnittstelle zu externen Kalender-, Raumverwaltungs- oder Benachrichtigungssystemen existiert aktuell nicht.

## Technische Hilfsmittel

Für die gemeinsame Testumgebung im Team wird Tailscale verwendet.

Tailscale dient ausschließlich dazu, die lokal laufende Anwendung innerhalb des Teams erreichbar zu machen.

Es ist kein fachliches Nachbarsystem von SyncUp und verarbeitet keine fachliche Geschäftslogik der Anwendung.

## Mögliche Erweiterungen

In einer späteren Version könnte SyncUp mit externen Kalenderdiensten wie Google Calendar oder Microsoft Outlook verbunden werden.

Auch weitere externe Dienste, beispielsweise für Benachrichtigungen oder Raumverwaltung, wären grundsätzlich denkbar.

Diese Anbindungen sind aktuell nicht Bestandteil des Funktionsumfangs.