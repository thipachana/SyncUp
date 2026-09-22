# B1 Dialogspezifikation

Stand: 22.09.2026

## Ziel

Dieser Abschnitt beschreibt die wichtigsten Benutzeroberflächen von SyncUp und den aktuellen Stand des Frontends.

## Login und Registrierung

Eine Anmeldung und Registrierung ist in SyncUp umgesetzt.

Benutzer können sich mit ihrer E-Mail-Adresse und einem Passwort anmelden oder ein neues Konto erstellen.

Diese Funktionen sind aktuell noch nicht umgesetzt.

## Dashboard

Ein Dashboard mit einer Übersicht über Termine, Aufgaben und Benachrichtigungen ist geplant.

Diese Ansicht ist aktuell noch nicht umgesetzt.

## Termine und Terminanfragen

Terminanfragen können im Frontend angezeigt, erstellt und gelöscht werden.

Termine werden aktuell hauptsächlich über das Backend verwaltet.

Für Terminanfragen können freie Zeitfenster berechnet und im Frontend angezeigt werden.

## Terminplanung

Für eine Terminanfrage wird ein Zeitraum festgelegt.

Das Backend berücksichtigt bereits vorhandene Termine und berechnet daraus mögliche freie Zeitfenster.

Die Ergebnisse werden im Frontend angezeigt.

Noch nicht vollständig umgesetzt sind:

- Teilnehmerauswahl
- vollständiger Abgleich mehrerer Teilnehmer über das Frontend
- Einladungen
- Bestätigung eines freien Zeitfensters als neuer Termin

## Ressourcen

Vorhandene Ressourcen wie Räume können im Frontend angezeigt werden.

Für eine Ressource kann eine Buchung mit einer vorhandenen Termin-ID und einem Zeitraum erstellt werden.

Das Backend prüft, ob für dieselbe Ressource bereits eine Buchung im gewünschten Zeitraum vorhanden ist.

Bei einer Überschneidung wird die Buchung abgelehnt und das Frontend zeigt eine Fehlermeldung an.

## Aufgaben

Eine Aufgabenverwaltung ist geplant.

Das Anlegen, Bearbeiten und Abschließen von Aufgaben ist aktuell noch nicht umgesetzt.

## Aktuell mögliche Bedienabläufe

Aktuell können unter anderem folgende Abläufe über die Oberfläche durchgeführt werden:

1. Terminanfragen anzeigen, erstellen und löschen.
2. Freie Zeitfenster für eine Terminanfrage berechnen und anzeigen.
3. Vorhandene Ressourcen anzeigen.
4. Eine Ressource für einen vorhandenen Termin buchen.
5. Eine Fehlermeldung anzeigen, wenn eine Ressource im gewünschten Zeitraum bereits gebucht ist.

## Noch nicht vollständig umgesetzt

- Anmeldung und Registrierung
- Profil- und Passwortverwaltung
- vollständige Terminverwaltung im Frontend
- Teilnehmerverwaltung
- Einladungen
- Aufgabenverwaltung
- Benachrichtigungen und Erinnerungen
- Freigabe bereits gebuchter Ressourcen