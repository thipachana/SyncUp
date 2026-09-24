# S2 Datenmigration

Stand: 24.09.2026

## Beschreibung

Für SyncUp ist keine Datenmigration aus einem bestehenden Fremdsystem erforderlich.

Alle benötigten Daten werden direkt innerhalb von SyncUp angelegt und in der eigenen PostgreSQL-Datenbank gespeichert.

## Begründung

SyncUp ersetzt keine bereits vorhandene Anwendung.

Deshalb müssen keine bestehenden Daten aus einem externen System übernommen, konvertiert oder importiert werden.

## Interne Datenanpassungen

Während der Entwicklung können sich Datenstrukturen innerhalb von SyncUp ändern.

Falls ältere Entwicklungsdaten nicht mehr vollständig zur aktuellen Datenstruktur passen, werden diese bei Bedarf gezielt angepasst oder neu angelegt.

Solche Änderungen sind interne Entwicklungsanpassungen und keine Migration aus einem Fremdsystem.