package de.thm.syncup.backend.service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
public final class Input {
    private Input() {}
    public static ResponseStatusException bad(String message) { return new ResponseStatusException(HttpStatus.BAD_REQUEST, message); }
    public static String text(String value, String name, int max) {
        if (value == null || value.isBlank() || value.strip().length() > max) throw bad(name + " ist erforderlich (höchstens " + max + " Zeichen).");
        return value.strip();
    }
    public static Long id(Long id) { if (id == null || id <= 0) throw bad("Eine gültige ID ist erforderlich."); return id; }
    public record Interval(LocalDateTime start, LocalDateTime end) {
        public boolean overlaps(Interval other) { return start.isBefore(other.end) && other.start.isBefore(end); }
        @Override public String toString() { return start + " bis " + end; }
    }
    public static Interval interval(String value) {
        if (value == null || value.length() > 100) throw bad("Ungültiger Zeitraum.");
        String[] parts = value.strip().split(" bis ", -1);
        if (parts.length != 2) throw bad("Zeitraum muss 'Beginn bis Ende' enthalten.");
        try {
            var start = LocalDateTime.parse(parts[0].strip()); var end = LocalDateTime.parse(parts[1].strip());
            if (!end.isAfter(start)) throw bad("Das Ende muss nach dem Beginn liegen.");
            return new Interval(start, end);
        } catch (DateTimeParseException e) { throw bad("Ungültiges Datum im Zeitraum."); }
    }
}
