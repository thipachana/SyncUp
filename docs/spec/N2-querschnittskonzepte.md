## Konsistenz bei Ressourcenbuchungen

Die Verfügbarkeit einer Ressource wird nicht ausschließlich im Frontend entschieden.

Für einen ausgewählten Termin fragt das Frontend beim Backend die zeitbezogene Verfügbarkeit der vorhandenen Ressourcen ab. Grundlage sind der Beginn und das Ende des Termins sowie bereits gespeicherte Buchungen.

Eine Überschneidung liegt vor, wenn sich zwei Buchungsintervalle zeitlich schneiden. Direkt aufeinanderfolgende Intervalle gelten nicht als Überschneidung.

Die Anzeige im Frontend dient der Benutzerführung. Vor dem Speichern einer neuen Buchung führt das Backend die Konfliktprüfung erneut durch. Dadurch kann eine Doppelbelegung auch dann verhindert werden, wenn mehrere Benutzer nahezu gleichzeitig denselben Raum auswählen.

Zusätzlich gilt die fachliche Regel, dass einem Termin höchstens eine Ressourcenbuchung zugeordnet werden darf.

Die eigentliche Konsistenzregel wird damit serverseitig durchgesetzt; das Frontend stellt den jeweils bekannten Zustand lediglich dar.