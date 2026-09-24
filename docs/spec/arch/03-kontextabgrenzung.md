# 3. Kontextabgrenzung

## Systemkontext

SyncUp ist eine Webanwendung zur gemeinsamen Planung von Terminen und Räumen.

Die Benutzer greifen über einen Webbrowser auf die Anwendung zu.

Die Verwaltung von Benutzern, Kalendern, Terminen, Terminanfragen, Ressourcen, Buchungen und Benachrichtigungen erfolgt innerhalb von SyncUp.

Externe Kalenderdienste oder andere fachliche Fremdsysteme werden im aktuellen Funktionsumfang nicht angebunden.

## Externe Akteure

Der zentrale externe Akteur ist der:

- **Benutzer**

Ein Benutzer kann sich registrieren, anmelden und abmelden.

Angemeldete Benutzer können insbesondere:

- private Termine im persönlichen Kalender verwalten,
- Terminanfragen erstellen,
- Teilnehmer für Terminanfragen auswählen,
- gemeinsame freie Zeitfenster berechnen lassen,
- aus einem freien Zeitslot einen gemeinsamen Termin erstellen,
- gemeinsame Termine im Kalender sehen,
- Benachrichtigungen anzeigen und löschen,
- vorhandene Räume für Termine reservieren.

Ein separates Administratorsystem ist im aktuellen Funktionsumfang nicht umgesetzt.

## Nachbarsysteme

Aktuell besitzt SyncUp keine direkte fachliche Verbindung zu externen Nachbarsystemen.

Eine spätere Anbindung an Kalenderdienste wie Google Calendar oder Microsoft Outlook wäre grundsätzlich möglich, gehört aber nicht zum aktuellen Funktionsumfang.

Tailscale wird ausschließlich für die gemeinsame Testumgebung im Team verwendet und ist kein fachliches Nachbarsystem von SyncUp.

## Abgrenzung des Systems

Innerhalb von SyncUp befinden sich:

- Registrierung, Anmeldung und Abmeldung
- persönlicher Kalender
- Verwaltung privater Termine
- Terminanfragen
- Auswahl von Teilnehmern
- Festlegung einer gewünschten Termindauer
- Berechnung gemeinsamer freier Zeitfenster
- Erstellung gemeinsamer Termine
- Anzeige gemeinsamer Termine bei den beteiligten Benutzern
- Benachrichtigungen innerhalb der Anwendung
- Anzeige und Löschung von Benachrichtigungen
- Anzeige vorhandener Räume
- zeitbezogene Prüfung der Raumverfügbarkeit
- Raumreservierungen
- Verhinderung überschneidender Buchungen desselben Raumes
- Beschränkung auf höchstens einen Raum pro Termin
- Anzeige des gebuchten Raumes im Kalender
- Entfernen einer zugehörigen Raumreservierung beim Löschen eines Termins

Nicht Bestandteil des aktuellen Funktionsumfangs sind:

- externe Kalenderdienste
- Aufgabenverwaltung
- Profil- und Passwortverwaltung
- erweitertes Rollen- und Administrationssystem
- automatische Vorschläge für alternative Räume