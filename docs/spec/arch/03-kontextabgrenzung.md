# 3. Kontextabgrenzung

## Systemkontext

SyncUp ist eine Webanwendung zur gemeinsamen Planung von Terminen und Räumen.

Die Benutzer greifen über einen Webbrowser auf die Anwendung zu.

Die Verwaltung von Benutzern, Kalendern, Terminen, Terminanfragen, Ressourcen und Buchungen erfolgt innerhalb von SyncUp.

Externe Kalenderdienste oder andere Fremdsysteme werden im aktuellen Funktionsumfang nicht angebunden.

## Externe Akteure

Der zentrale externe Akteur ist der:

- **Benutzer**

Ein Benutzer kann sich registrieren und anmelden, private Termine verwalten, Terminanfragen erstellen, an gemeinsamen Terminen teilnehmen und Räume reservieren.

Ein separates Administratorsystem ist im aktuellen Funktionsumfang nicht umgesetzt.

## Nachbarsysteme

Aktuell besitzt SyncUp keine direkte Verbindung zu externen Nachbarsystemen.

Eine spätere Anbindung an Kalenderdienste wie Google Calendar oder Microsoft Outlook wäre grundsätzlich möglich, gehört aber nicht zum aktuellen Funktionsumfang.

Tailscale wird nur für die gemeinsame Testumgebung im Team verwendet und ist kein fachliches Nachbarsystem von SyncUp.

## Abgrenzung des Systems

Innerhalb von SyncUp befinden sich:

- Registrierung und Anmeldung
- persönlicher Kalender
- Verwaltung privater Termine
- Terminanfragen
- Auswahl von Teilnehmern
- Berechnung gemeinsamer freier Zeitfenster
- Erstellung gemeinsamer Termine
- Benachrichtigungen innerhalb der Anwendung
- Verwaltung vorhandener Räume
- Raumreservierungen
- Prüfung auf Überschneidungen bei Raumreservierungen

Nicht Bestandteil des aktuellen Funktionsumfangs sind:

- externe Kalenderdienste
- Aufgabenverwaltung
- Profil- und Passwortverwaltung
- erweitertes Rollen- und Administrationssystem
- automatische Vorschläge für alternative Räume