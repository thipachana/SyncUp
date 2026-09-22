package de.thm.syncup.backend.controller;

import de.thm.syncup.backend.model.Benutzer;
import de.thm.syncup.backend.repository.BenutzerRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(
    origins = {
        "http://localhost:5173",
        "http://localhost:5175"
    },
    allowCredentials = "true"
)
public class AuthController {

    private final BenutzerRepository benutzerRepository;
    private final BCryptPasswordEncoder passwordEncoder =
        new BCryptPasswordEncoder();

    public AuthController(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
        @RequestBody Map<String, String> request
    ) {
        String name = request.get("name");
        String email = request.get("email");
        String passwort = request.get("passwort");

        if (
            name == null || name.isBlank()
            || email == null || email.isBlank()
            || passwort == null || passwort.isBlank()
        ) {
            return ResponseEntity
                .badRequest()
                .body(
                    Map.of(
                        "message",
                        "Name, E-Mail und Passwort müssen angegeben werden."
                    )
                );
        }

        email = email.trim().toLowerCase();

        if (benutzerRepository.existsByEmail(email)) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                    Map.of(
                        "message",
                        "Diese E-Mail-Adresse ist bereits registriert."
                    )
                );
        }

        Benutzer benutzer = new Benutzer();
        benutzer.setName(name.trim());
        benutzer.setEmail(email);
        benutzer.setPasswort(passwordEncoder.encode(passwort));
        benutzer.setRolle("USER");

        Benutzer gespeichert = benutzerRepository.save(benutzer);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(gespeichert);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
        @RequestBody Map<String, String> request,
        HttpSession session
    ) {
        String email = request.get("email");
        String passwort = request.get("passwort");

        if (
            email == null || email.isBlank()
            || passwort == null || passwort.isBlank()
        ) {
            return ResponseEntity
                .badRequest()
                .body(
                    Map.of(
                        "message",
                        "E-Mail und Passwort müssen angegeben werden."
                    )
                );
        }

        Optional<Benutzer> benutzerOptional =
            benutzerRepository.findByEmail(
                email.trim().toLowerCase()
            );

        if (benutzerOptional.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                    Map.of(
                        "message",
                        "E-Mail oder Passwort ist falsch."
                    )
                );
        }

        Benutzer benutzer = benutzerOptional.get();

        if (
            !passwordEncoder.matches(
                passwort,
                benutzer.getPasswort()
            )
        ) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                    Map.of(
                        "message",
                        "E-Mail oder Passwort ist falsch."
                    )
                );
        }

        session.setAttribute(
            "benutzerId",
            benutzer.getBenutzerId()
        );

        return ResponseEntity.ok(benutzer);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();

        return ResponseEntity.ok(
            Map.of(
                "message",
                "Erfolgreich abgemeldet."
            )
        );
    }

    @GetMapping("/me")
    public ResponseEntity<?> currentUser(HttpSession session) {
        Object benutzerId =
            session.getAttribute("benutzerId");

        if (benutzerId == null) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                    Map.of(
                        "message",
                        "Nicht angemeldet."
                    )
                );
        }

        return benutzerRepository
            .findById((Long) benutzerId)
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElseGet(
                () ->
                    ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(
                            Map.of(
                                "message",
                                "Benutzer nicht gefunden."
                            )
                        )
            );
    }
}