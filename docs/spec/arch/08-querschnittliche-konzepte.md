# 8. Querschnittliche Konzepte

## Fehlerbehandlung

Fehler werden vom Backend erkannt und als verständliche Rückmeldungen an das Frontend weitergegeben. Das Frontend informiert den Benutzer über fehlerhafte Eingaben oder fehlgeschlagene Aktionen.

## Kommunikation

Die Kommunikation zwischen Frontend und Backend erfolgt über REST-Schnittstellen. Die Daten werden im JSON-Format übertragen.

## Benutzerverwaltung

Benutzer authentifizieren sich mit ihren Zugangsdaten. Nach erfolgreicher Anmeldung können nur berechtigte Benutzer auf die für sie vorgesehenen Funktionen und Daten zugreifen.

## Datenhaltung

Die Daten der Anwendung werden dauerhaft in einer PostgreSQL-Datenbank gespeichert. Dazu gehören insbesondere Benutzer, Kalender, Termine, Ressourcen und Buchungen.

## Sicherheit

Passwörter werden nicht im Klartext gespeichert. Für die Kommunikation zwischen Client und Server ist bei einer späteren Bereitstellung eine verschlüsselte HTTPS-Verbindung vorgesehen.