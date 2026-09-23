package de.thm.syncup.backend.controller;
import de.thm.syncup.backend.model.*;
import de.thm.syncup.backend.repository.*;
import de.thm.syncup.backend.security.SessionSecurity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.nio.charset.StandardCharsets;
import java.util.*;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final BenutzerRepository users;
    private final KalenderRepository calendars;
    private final SessionSecurity security;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final String dummyHash = encoder.encode(UUID.randomUUID().toString());
    public AuthController(BenutzerRepository users, KalenderRepository calendars, SessionSecurity security) {
        this.users = users; this.calendars = calendars; this.security = security;
    }
    @GetMapping("/csrf") public Map<String, String> csrf(HttpServletRequest request) {
        return Map.of("token", SessionSecurity.csrf(request.getSession()), "headerName", "X-CSRF-TOKEN");
    }
    @PostMapping("/register") @ResponseStatus(HttpStatus.CREATED) @Transactional
    public UserResponse register(@Valid @RequestBody Registration data) {
        if (data.passwort().getBytes(StandardCharsets.UTF_8).length > 72) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwort darf höchstens 72 UTF-8-Bytes enthalten.");
        String email = data.email().strip().toLowerCase(Locale.ROOT);
        if (users.existsByEmailIgnoreCase(email)) throw new ResponseStatusException(HttpStatus.CONFLICT, "Diese E-Mail ist bereits registriert.");
        Benutzer user = new Benutzer(); user.setName(data.name().strip()); user.setEmail(email);
        user.setPasswort(encoder.encode(data.passwort())); user.setRolle("USER"); users.saveAndFlush(user);
        Kalender calendar = new Kalender(); calendar.setName("Mein Kalender"); calendar.setBesitzer(user); calendars.save(calendar);
        return UserResponse.from(user);
    }
    @PostMapping("/login") public UserResponse login(@RequestBody Login data, HttpServletRequest request) {
        if (data.email() == null || data.passwort() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-Mail und Passwort sind erforderlich.");
        var user = users.findByEmailIgnoreCase(data.email().strip().toLowerCase(Locale.ROOT));
        String hash = user.map(Benutzer::getPasswort).orElse(dummyHash);
        if (data.passwort().getBytes(StandardCharsets.UTF_8).length > 72 || !encoder.matches(data.passwort(), hash) || user.isEmpty())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "E-Mail oder Passwort ist falsch.");
        request.getSession(); request.changeSessionId();
        request.getSession().setAttribute("benutzerId", user.get().getBenutzerId());
        request.getSession().removeAttribute("csrf");
        return UserResponse.from(user.get());
    }
    @GetMapping("/me") public UserResponse me(HttpServletRequest request) { return UserResponse.from(security.current(request)); }
    @PostMapping("/logout") public Map<String, String> logout(HttpServletRequest request) {
        request.getSession().invalidate(); return Map.of("message", "Erfolgreich abgemeldet.");
    }
    public record Registration(
        @NotBlank(message="Name erforderlich.") @Size(max=100, message="Name zu lang.") String name,
        @NotBlank(message="E-Mail erforderlich.") @Email(message="Ungültige E-Mail.") @Size(max=255) String email,
        @NotBlank(message="Passwort erforderlich.") @Size(min=8, max=72, message="Passwort muss 8 bis 72 Zeichen enthalten.") String passwort) {}
    public record Login(String email, String passwort) {}
    public record UserResponse(Long benutzerId, String name, String email, String rolle) {
        static UserResponse from(Benutzer u) { return new UserResponse(u.getBenutzerId(), u.getName(), u.getEmail(), u.getRolle()); }
    }
}
