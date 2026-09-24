# Architekturübersicht

Stand: 24.09.2026

## Ziel

SyncUp ist eine Webanwendung zur gemeinsamen Planung von Terminen und Räumen.

Die Anwendung unterstützt Benutzer dabei, private Termine zu verwalten, gemeinsame freie Zeitfenster zu finden, gemeinsame Termine zu erstellen und für bestehende Termine vorhandene Räume zu reservieren.

## Systemstruktur

SyncUp besteht aus drei Hauptbereichen:

- Frontend
- Backend
- Datenbank

Das Frontend stellt die Benutzeroberfläche bereit und verarbeitet Benutzereingaben.

Das Backend verarbeitet die Geschäftslogik, prüft fachliche Regeln und stellt REST-Schnittstellen zur Verfügung.

Die Datenbank speichert die Daten der Anwendung dauerhaft.

## Technologien

Für SyncUp werden folgende Technologien verwendet:

- **Frontend:** React mit JavaScript / JSX
- **Frontend-Tooling:** Vite
- **Backend:** Spring Boot mit Java 21
- **Datenbank:** PostgreSQL
- **Persistenz:** Spring Data JPA und Hibernate
- **Build:** Maven und npm
- **Versionsverwaltung:** Git und GitHub
- **Gemeinsame Testumgebung:** Tailscale

## Komponenten

Zu den wichtigsten fachlichen Bereichen von SyncUp gehören:

- Registrierung, Anmeldung und Abmeldung
- persönliche Kalenderverwaltung
- private Terminverwaltung
- Terminanfragen
- Teilnehmerzuordnung
- Festlegung einer gewünschten Termindauer
- Berechnung gemeinsamer freier Zeitfenster
- Erstellung gemeinsamer Termine
- Benachrichtigungen anzeigen und löschen
- Verwaltung vorhandener Ressourcen
- zeitbezogene Prüfung der Raumverfügbarkeit
- Raumreservierungen
- Verhinderung überschneidender Buchungen desselben Raumes
- parallele Nutzung unterschiedlicher freier Räume
- Begrenzung auf höchstens einen Raum pro Termin
- Anzeige gebuchter Räume im Kalender
- Entfernen einer zugehörigen Raumbuchung beim Löschen eines Termins

## Kommunikation

Das Frontend kommuniziert über REST-Schnittstellen mit dem Backend.

Die Daten werden hauptsächlich im JSON-Format übertragen.

Das Backend greift über Spring Data JPA und Hibernate auf PostgreSQL zu.

Ein direkter Zugriff des Frontends auf die Datenbank findet nicht statt.

Fachliche Regeln werden zentral im Backend geprüft. Dies gilt insbesondere für Termin- und Raumreservierungen.

## Verteilung

SyncUp kann vollständig lokal auf einem Rechner ausgeführt werden.

Für gemeinsame Mehrbenutzertests wird zusätzlich Tailscale verwendet.

Dabei laufen Frontend, Backend und PostgreSQL auf einem Host-Rechner, während weitere Teammitglieder über das gemeinsame Tailscale-Netzwerk auf die Webanwendung zugreifen.

Die Datenbank wird dabei nicht direkt freigegeben.

## Nicht Bestandteil des aktuellen Funktionsumfangs

Nicht umgesetzt beziehungsweise nicht Teil der finalen Version sind unter anderem:

- Aufgabenverwaltung
- externe Kalenderanbindung
- erweitertes Rollen- und Administrationssystem
- Profil- und Passwortverwaltung
- automatische Vorschläge für alternative Räume