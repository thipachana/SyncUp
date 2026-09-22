package de.thm.syncup.backend.controller;

import de.thm.syncup.backend.model.Benutzer;
import de.thm.syncup.backend.repository.BenutzerRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/benutzer")
@CrossOrigin(
    origins = {
        "http://localhost:5173",
        "http://localhost:5175"
    },
    allowCredentials = "true"
)
public class BenutzerController {

    private final BenutzerRepository benutzerRepository;

    public BenutzerController(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
    }

    @GetMapping
    public ResponseEntity<?> alleBenutzer(HttpSession session) {

        Object benutzerId = session.getAttribute("benutzerId");

        if (benutzerId == null) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Nicht angemeldet."));
        }

        List<BenutzerKurz> benutzer =
            benutzerRepository
                .findAll()
                .stream()
                .map(b -> new BenutzerKurz(
                    b.getBenutzerId(),
                    b.getName(),
                    b.getEmail()
                ))
                .toList();

        return ResponseEntity.ok(benutzer);
    }

    public record BenutzerKurz(
        Long benutzerId,
        String name,
        String email
    ) {
    }
}
