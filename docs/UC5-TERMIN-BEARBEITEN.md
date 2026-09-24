# UC5 – Termin bearbeiten und Benachrichtigungen

Stand: 24.09.2026

## Termin bearbeiten

Eigene Termine können über den persönlichen Kalender bearbeitet werden.

Dabei können die vorhandenen Angaben eines Termins geändert und anschließend gespeichert werden.

Teilnehmer gemeinsamer Termine können den Termin in ihrem Kalender sehen. Die Bearbeitung erfolgt durch den jeweiligen Besitzer beziehungsweise Organisator des Termins.

Private Termine besitzen keine Teilnehmerauswahl.

## Benachrichtigungen

Bei neu festgelegten gemeinsamen Terminen erhalten die beteiligten Teilnehmer eine Benachrichtigung innerhalb von SyncUp.

Die Benachrichtigungen werden dem jeweiligen Benutzer zugeordnet und können über die Benutzeroberfläche angezeigt werden.

Es werden keine E-Mails oder Betriebssystem-Push-Benachrichtigungen versendet.

Weitergehende Benachrichtigungen, zum Beispiel bei jeder nachträglichen Änderung eines bestehenden Termins, sind nicht Bestandteil des aktuell nachgewiesenen Funktionsumfangs.

## Ressourcenbuchungen und Terminänderungen

Ressourcenbuchungen sind an einen bestehenden Termin gebunden.

Die Buchungszeit wird aus dem Zeitraum des Termins übernommen.

Beim Löschen eines Termins wird eine vorhandene Raumbuchung ebenfalls entfernt.

Das Verhalten bei einer nachträglichen Änderung der Zeit eines bereits gebuchten Termins muss mit dem tatsächlich getesteten Stand übereinstimmen und wird deshalb nicht als eigener abgeschlossener Ablauf beschrieben.

## Schnittstellen

Für die Termin- und Benachrichtigungsfunktionen stellt das Backend REST-Schnittstellen bereit.

Schreibende Zugriffe benötigen eine gültige Anmeldung und den vorgesehenen CSRF-Schutz.

Die genaue technische Umsetzung ist in der Backend-Implementierung und der Architekturdokumentation beschrieben.

## Zuständigkeiten

- David prüft die Backend-Logik und die Speicherung.
- Sarah prüft die Darstellung und Bearbeitung im Frontend.
- Thipachana prüft die Beschreibung in der Spezifikation und den Ablauf mit Testkonten.
- Ilias unterstützt bei der technischen Integration.

## Aktueller Stand

Terminbearbeitung ist umgesetzt.

Benachrichtigungen für neu festgelegte gemeinsame Termine sind umgesetzt.

Weitere Benachrichtigungsarten und zusätzliche Bearbeitungsregeln gehören nur dann zum finalen Funktionsumfang, wenn sie vor der Abgabe tatsächlich getestet und bestätigt wurden.