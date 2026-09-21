# SyncUp – Demo-Ablauf

## 1. Anwendung starten
- PostgreSQL starten
- Spring-Boot-Backend starten
- React-Frontend starten

## 2. Terminplanung zeigen
- Vorhandene Termine anzeigen
- Terminanfragen anzeigen
- Automatische Berechnung freier Zeitfenster demonstrieren
- Zeigen, dass bereits belegte Zeiten nicht als frei angezeigt werden

## 3. Ressourcenverwaltung zeigen
- Raum A101 anzeigen
- Typ und Kapazität erklären
- Ressource für einen Termin buchen
- Erfolgreiche Buchung zeigen

## 4. Doppelbuchung testen
- Dieselbe Ressource für einen überschneidenden Zeitraum erneut buchen
- SyncUp verhindert die Buchung
- Frontend zeigt die Meldung:
  "Ressource ist in diesem Zeitraum bereits gebucht."

## 5. Datenbank / Backend erklären
- Termine, Benutzer, Ressourcen und Buchungen werden in PostgreSQL gespeichert
- React kommuniziert über REST mit dem Spring-Boot-Backend

## 6. Sicherheit zeigen
- Benutzerpasswort wird nicht über die API ausgegeben

## Aktueller Stand
SyncUp unterstützt aktuell die Terminplanung, automatische Zeitslot-Erkennung und Ressourcenreservierung. Weitere Funktionen wie Benachrichtigungen und vollständige Benutzerverwaltung sind noch vorgesehen.
