# 2. Randbedingungen

## Technische Randbedingungen

Für die Entwicklung von SyncUp werden folgende Technologien verwendet:

- **Frontend:** React mit JavaScript / JSX
- **Backend:** Spring Boot mit Java 21
- **Datenbank:** PostgreSQL
- **Persistenz:** Spring Data JPA und Hibernate
- **Build und Entwicklung:** Maven, npm und Vite
- **Versionsverwaltung:** Git und GitHub

Die Kommunikation zwischen Frontend und Backend erfolgt über REST-Schnittstellen.

Das Frontend greift nicht direkt auf die Datenbank zu. Der Zugriff auf die Daten erfolgt über das Backend.

Für gemeinsame Tests im Team wird zusätzlich Tailscale verwendet.

## Organisatorische Randbedingungen

Das Projekt wird im Rahmen des Moduls Wirtschaftsinformatik-Projekt I an der THM entwickelt.

Die Entwicklung erfolgt gemeinsam im Team.

Für die Zusammenarbeit und Versionsverwaltung werden Git und GitHub verwendet.

Die Aufgaben sind im Team auf verschiedene Rollen verteilt, zum Beispiel Projektleitung, Softwarearchitektur, Requirements und Implementierung.

Änderungen werden vor der Übernahme in den gemeinsamen Projektstand geprüft.

## Qualitätsziele

Bei der Architektur stehen besonders folgende Ziele im Vordergrund:

- **Wartbarkeit:** Der Code soll so aufgebaut sein, dass Änderungen möglichst einfach vorgenommen werden können.
- **Erweiterbarkeit:** Neue Funktionen sollen ergänzt werden können, ohne große Teile des Systems ändern zu müssen.
- **Übersichtlichkeit:** Frontend, Backend und Datenbank sollen klar voneinander getrennt sein.
- **Nachvollziehbarkeit:** Der Aufbau der Anwendung soll für alle Teammitglieder verständlich sein.
- **Sicherheit:** Persönliche Daten und geschützte Funktionen sollen nur angemeldeten Benutzern zugänglich sein.