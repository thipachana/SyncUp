package de.thm.syncup.backend.controller;

import de.thm.syncup.backend.model.Kalender;
import de.thm.syncup.backend.model.Termin;
import de.thm.syncup.backend.repository.KalenderRepository;
import de.thm.syncup.backend.repository.TerminRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/termine")
@CrossOrigin(
    origins = {
        "http://localhost:5173",
        "http://localhost:5175"
    },
    allowCredentials = "true"
)
public class TerminController {

    private final TerminRepository terminRepository;
    private final KalenderRepository kalenderRepository;

    public TerminController(
        TerminRepository terminRepository,
        KalenderRepository kalenderRepository
    ) {
        this.terminRepository = terminRepository;
        this.kalenderRepository = kalenderRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAlleTermine(HttpSession session) {

        Object benutzerId = session.getAttribute("benutzerId");

        if (benutzerId == null) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Nicht angemeldet."));
        }

        Long id = ((Number) benutzerId).longValue();

        return ResponseEntity.ok(
            terminRepository.findByKalenderBesitzerBenutzerId(id)
        );
    }

    @PostMapping
    public ResponseEntity<?> erstelleTermin(
        @RequestBody Termin termin,
        HttpSession session
    ) {

        Object benutzerId = session.getAttribute("benutzerId");

        if (benutzerId == null) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Nicht angemeldet."));
        }

        Long id = ((Number) benutzerId).longValue();

        if (
            termin == null ||
            termin.getTitel() == null ||
            termin.getTitel().isBlank() ||
            termin.getDatum() == null ||
            termin.getStartzeit() == null ||
            termin.getEndzeit() == null ||
            !termin.getEndzeit().isAfter(termin.getStartzeit())
        ) {
            return ResponseEntity
                .badRequest()
                .body(Map.of("message", "Ungültige Termindaten."));
        }

        if (
            termin.getKalender() == null ||
            termin.getKalender().getKalenderId() == null
        ) {
            return ResponseEntity
                .badRequest()
                .body(Map.of("message", "Kalender fehlt."));
        }

        Kalender kalender = kalenderRepository
            .findById(termin.getKalender().getKalenderId())
            .orElse(null);

        if (kalender == null) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Kalender nicht gefunden."));
        }

        if (
            kalender.getBesitzer() == null ||
            !id.equals(kalender.getBesitzer().getBenutzerId())
        ) {
            return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(Map.of("message", "Kein Zugriff auf diesen Kalender."));
        }

        termin.setKalender(kalender);

        if (termin.getStatus() == null || termin.getStatus().isBlank()) {
            termin.setStatus("BESTAETIGT");
        }

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(terminRepository.save(termin));
    }
}
