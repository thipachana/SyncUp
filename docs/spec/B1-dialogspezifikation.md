## Dialog „Raum reservieren“

### Zweck

Der Dialog unterstützt die Reservierung eines Raumes für einen bereits vorhandenen Termin.

### Voraussetzungen

- Der Benutzer ist angemeldet.
- Dem Benutzer stehen vorhandene Termine zur Auswahl.
- Ressourcen wurden zentral im System angelegt.

### Eingaben und Auswahl

Der Benutzer wählt über ein Auswahlfeld einen vorhandenen Termin aus.

Nach der Auswahl übernimmt SyncUp automatisch:

- Datum,
- Startzeit,
- Endzeit

des Termins. Der Buchungszeitraum wird daher nicht separat vom Benutzer eingegeben.

### Anzeige der Ressourcen

Für jede Ressource werden mindestens folgende Informationen angezeigt:

- Name des Raumes,
- Typ,
- Kapazität,
- zeitbezogene Verfügbarkeit.

Die zeitbezogene Verfügbarkeit wird für den Zeitraum des ausgewählten Termins vom Backend ermittelt.

Mögliche Anzeigen sind:

- **Verfügbar:** Für den Zeitraum des ausgewählten Termins liegt keine überschneidende Buchung vor.
- **Nicht verfügbar:** Der Raum ist grundsätzlich nicht buchbar oder bereits während dieses Zeitraums belegt.

Ein als „Nicht verfügbar“ markierter Raum kann nicht über die Oberfläche reserviert werden.

### Reservierung

Bei Auswahl von „Raum reservieren“ sendet die Oberfläche den ausgewählten Termin und die Ressource an das Backend.

Das Backend prüft die Verfügbarkeit unmittelbar vor dem Speichern erneut. Dadurch bleibt die serverseitige Prüfung maßgeblich, auch wenn mehrere Benutzer gleichzeitig versuchen, einen Raum zu reservieren.

Für einen Termin kann höchstens ein Raum reserviert werden.

Nach erfolgreicher Reservierung:

- wird eine Erfolgsbestätigung angezeigt,
- wird die Buchung dem Termin zugeordnet,
- wird der aktuelle Buchungszustand in der Oberfläche aktualisiert.

### Fehlermeldungen

Der Dialog zeigt insbesondere verständliche Meldungen, wenn:

- kein gültiger Termin ausgewählt wurde,
- ein Raum zwischenzeitlich belegt wurde,
- für den Termin bereits ein Raum reserviert ist,
- der Termin oder die Ressource nicht gefunden werden kann,
- ein technischer Fehler auftritt.

### Bezug zu Anwendungsfällen

Der Dialog unterstützt insbesondere **UC4 – Ressource buchen**.