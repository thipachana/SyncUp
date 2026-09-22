package de.thm.syncup.backend.controller;

import de.thm.syncup.backend.model.Benutzer;
import de.thm.syncup.backend.model.Kalender;
import de.thm.syncup.backend.model.Termin;
import de.thm.syncup.backend.repository.BenutzerRepository;
import de.thm.syncup.backend.repository.KalenderRepository;
import de.thm.syncup.backend.repository.TerminRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/me/termine")
@CrossOrigin(
    origins = {
        "http://localhost:5173",
        "http://localhost:5175"
    },
    allowCredentials = "true"
)
public class MeinKalenderController {

    private final TerminRepository terminRepository;
    private final KalenderRepository kalenderRepository;
    private final BenutzerRepository benutzerRepository;

    public MeinKalenderController(
        TerminRepository terminRepository,
        KalenderRepository kalenderRepository,
        BenutzerRepository benutzerRepository
    ) {
        this.terminRepository = terminRepository;
        this.kalenderRepository = kalenderRepository;
        this.benutzerRepository = benutzerRepository;
    }

    @GetMapping
    public ResponseEntity<?> meineTermine(HttpSession session) {
        Long benutzerId = getBenutzerId(session);

        if (benutzerId == null) {
            return nichtAngemeldet();
        }

        List<Termin> termine =
            terminRepository.findByKalenderBesitzerBenutzerId(benutzerId);

        return ResponseEntity.ok(termine);
    }

    @PostMapping
    public ResponseEntity<?> terminErstellen(
        @RequestBody TerminRequest request,
        HttpSession session
    ) {
        Long benutzerId = getBenutzerId(session);

        if (benutzerId == null) {
            return nichtAngemeldet();
        }

        if (
            request.titel() == null
            || request.titel().isBlank()
            || request.datum() == null
            || request.startzeit() == null
            || request.endzeit() == null
        ) {
            return ResponseEntity.badRequest().body(
                Map.of(
                    "message",
                    "Titel, Datum, Startzeit und Endzeit müssen angegeben werden."
                )
            );
        }

        if (!request.endzeit().isAfter(request.startzeit())) {
            return ResponseEntity.badRequest().body(
                Map.of(
                    "message",
                    "Die Endzeit muss nach der Startzeit liegen."
                )
            );
        }

        Optional<Benutzer> benutzerOptional =
            benutzerRepository.findById(benutzerId);

        if (benutzerOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                Map.of("message", "Benutzer nicht gefunden.")
            );
        }

        Kalender kalender =
            kalenderRepository
                .findFirstByBesitzerBenutzerId(benutzerId)
                .orElseGet(() -> {
                    Kalender neuerKalender = new Kalender();
                    neuerKalender.setName("Mein Kalender");
                    neuerKalender.setBeschreibung(
                        "Persönlicher SyncUp-Kalender"
                    );
                    neuerKalender.setBesitzer(benutzerOptional.get());

                    return kalenderRepository.save(neuerKalender);
                });

        Termin termin = new Termin();
        termin.setTitel(request.titel().trim());
        termin.setBeschreibung(request.beschreibung());
        termin.setDatum(request.datum());
        termin.setStartzeit(request.startzeit());
        termin.setEndzeit(request.endzeit());
        termin.setStatus("BESTAETIGT");
        termin.setKalender(kalender);

        Termin gespeichert = terminRepository.save(termin);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(gespeichert);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> terminLoeschen(
        @PathVariable Long id,
        HttpSession session
    ) {
        Long benutzerId = getBenutzerId(session);

        if (benutzerId == null) {
            return nichtAngemeldet();
        }

        Optional<Termin> terminOptional =
            terminRepository.findById(id);

        if (terminOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Termin termin = terminOptional.get();

        if (
            termin.getKalender() == null
            || termin.getKalender().getBesitzer() == null
            || !benutzerId.equals(
                termin.getKalender()
                    .getBesitzer()
                    .getBenutzerId()
            )
        ) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                Map.of(
                    "message",
                    "Dieser Termin gehört nicht zu deinem Kalender."
                )
            );
        }

        terminRepository.delete(termin);

        return ResponseEntity.noContent().build();
    }

    private Long getBenutzerId(HttpSession session) {
        Object benutzerId =
            session.getAttribute("benutzerId");

        if (benutzerId instanceof Long id) {
            return id;
        }

        return null;
    }

    private ResponseEntity<?> nichtAngemeldet() {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(
                Map.of(
                    "message",
                    "Nicht angemeldet."
                )
            );
    }

    public record TerminRequest(
        String titel,
        String beschreibung,
        LocalDate datum,
        LocalTime startzeit,
        LocalTime endzeit
    ) {
    }
}
