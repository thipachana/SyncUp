# UC5 – Termin bearbeiten und Benachrichtigungen

Stand: 24.09.2026

## Termin bearbeiten

Eigene private Termine können über den persönlichen Kalender bearbeitet werden.

Dabei können die vorhandenen Angaben eines Termins geändert und anschließend gespeichert werden.

Gemeinsame Termine werden bei den beteiligten Teilnehmern im Kalender angezeigt.

Private Termine besitzen keine Teilnehmerauswahl.

Eine weitergehende Bearbeitung gemeinsamer Termine wird nur dann als Bestandteil des finalen Funktionsumfangs betrachtet, wenn sie im finalen Stand tatsächlich getestet und bestätigt wurde.

## Benachrichtigungen

Bei neu festgelegten gemeinsamen Terminen erhalten die beteiligten Teilnehmer eine Benachrichtigung innerhalb von SyncUp.

Die Benachrichtigungen werden dem jeweiligen Benutzer zugeordnet und können über die Benutzeroberfläche:

- angezeigt
- gelöscht

werden.

Es werden keine E-Mails oder Betriebssystem-Push-Benachrichtigungen versendet.

Weitergehende Benachrichtigungen, zum Beispiel bei jeder nachträglichen Änderung eines bestehenden Termins, sind nicht Bestandteil des final nachgewiesenen Funktionsumfangs.

## Ressourcenbuchungen und Terminänderungen

Ressourcenbuchungen sind an einen bestehenden Termin gebunden.

Beginn und Ende der Buchung werden aus dem Zeitraum des zugehörigen Termins übernommen.

Beim Löschen eines Termins wird eine vorhandene Raumbuchung ebenfalls entfernt.

Das Verhalten bei einer nachträglichen Änderung der Zeit eines bereits gebuchten Termins wird nicht als eigener abgeschlossener Ablauf beschrieben, solange dieses Verhalten nicht im finalen Teststand ausdrücklich bestätigt wurde.

## Schnittstellen

Für Termin- und Benachrichtigungsfunktionen stellt das Backend REST-Schnittstellen bereit.

Schreibende Zugriffe benötigen eine gültige Anmeldung und den vorgesehenen CSRF-Schutz.

Die genaue technische Umsetzung ist in der Backend-Implementierung und der Architekturdokumentation beschrieben.

## Zuständigkeiten

Die Bearbeitung dieses Bereichs erfolgte entsprechend der Aufgabenverteilung im Team:

- **David Cabas Canella:** Requirements und fachliche Beschreibung
- **Sarah Kouskous:** Frontend und Softwarearchitektur
- **Thipachana Clarian Kenady:** Projektleitung, Spezifikation und finaler Dokumentationsabgleich
- **Ilias Jelloli:** Implementierung und technische Integration

## Aktueller Stand

Die Bearbeitung eigener privater Termine ist umgesetzt.

Benachrichtigungen für neu festgelegte gemeinsame Termine sind umgesetzt.

Benachrichtigungen können angezeigt und gelöscht werden.

Weitere Benachrichtigungsarten und zusätzliche Bearbeitungsregeln gehören nur dann zum finalen Funktionsumfang, wenn sie vor der Abgabe tatsächlich getestet und bestätigt wurden.