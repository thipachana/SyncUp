# UC5 – Termin bearbeiten und Benachrichtigungen

Der Ersteller kann im Monatskalender einen bestehenden Termin mit „Bearbeiten“ öffnen, Titel, Beschreibung, Datum, Zeiten und Teilnehmer ändern und speichern. Teilnehmer sehen den Termin ebenfalls in ihrem Kalender, dürfen ihn jedoch nicht bearbeiten oder löschen.

Die Glocke rechts oben zeigt gespeicherte Benachrichtigungen und die Anzahl ungelesener Meldungen. Beim Öffnen sowie alle 15 Sekunden wird aktualisiert; einzelne Meldungen können als gelesen markiert werden. Nur der jeweilige Empfänger hat Zugriff. Es werden keine E-Mails oder Betriebssystem-Push-Nachrichten versendet.

Änderungen benachrichtigen die bisherigen und neuen Teilnehmer außer dem Bearbeiter. Entfernte Teilnehmer bekommen nur einen Entfernungshinweis. Unverändertes Speichern erzeugt keine zusätzliche Nachricht. Alte persönliche Termine erhalten nicht automatisch Teilnehmer aus früheren Terminanfragen; diese Zuordnung muss beim Bearbeiten ausdrücklich erfolgen.

Bestehende Ressourcenbuchungen verhindern Änderungen an Datum und Zeiten mit HTTP 409. Es wird keine Buchung stillschweigend verschoben. Titel, Beschreibung und Teilnehmer bleiben bearbeitbar. Stornieren bzw. gemeinsames Verschieben der Buchung ist ein eigener noch offener Anwendungsfall.

API: PUT /api/me/termine/{id} und PUT /api/termine/{id}; optionale benutzerIds im Termin-Payload; GET /api/me/benachrichtigungen; POST /api/me/benachrichtigungen/{id}/gelesen. Schreibzugriffe benötigen Session und CSRF-Token. Migration im lokalen Entwicklungsbetrieb erfolgt über Hibernate ddl-auto=update; database/schema.sql beschreibt Neuinstallationen.

## Zuständigkeiten

David: Backend, Teilnehmerzuordnung, Benachrichtigungsdaten, Schema und Integrationstests.
Sarah: Bearbeiten-Formular, Teilnehmerauswahl, Glocke, Darstellung und Frontendprüfung.
Thipachana: Spezifikation und Abnahme mit zwei Konten; vorhandene Übergabeanleitung verwenden.

Die aktuellen Übergabepakete ersetzen die früheren Pakete vollständig. Nicht zusätzlich auf bereits angewendete alte Pakete anwenden.
