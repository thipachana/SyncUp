package de.thm.syncup.backend.security;
import de.thm.syncup.backend.model.Benutzer;
import de.thm.syncup.backend.repository.BenutzerRepository;
import jakarta.servlet.http.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.server.ResponseStatusException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

@Component
public class SessionSecurity implements HandlerInterceptor {
    private final BenutzerRepository users;
    private static final Set<String> PUBLIC = Set.of("/api/auth/csrf", "/api/auth/register", "/api/auth/login");
    public SessionSecurity(BenutzerRepository users) { this.users = users; }
    public Benutzer current(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || !(session.getAttribute("benutzerId") instanceof Number id))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bitte zuerst anmelden.");
        return users.findById(id.longValue()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bitte erneut anmelden."));
    }
    public static String csrf(HttpSession session) {
        synchronized (session) {
            if (session.getAttribute("csrf") == null) session.setAttribute("csrf", UUID.randomUUID().toString());
            return (String) session.getAttribute("csrf");
        }
    }
    @Override public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equals("OPTIONS")) return true;
        if (!PUBLIC.contains(request.getRequestURI())) current(request);
        if (!Set.of("GET", "HEAD", "OPTIONS").contains(request.getMethod())) {
            HttpSession session = request.getSession(false);
            String expected = session == null ? null : (String) session.getAttribute("csrf");
            String supplied = request.getHeader("X-CSRF-TOKEN");
            if (expected == null || supplied == null || !MessageDigest.isEqual(expected.getBytes(StandardCharsets.UTF_8), supplied.getBytes(StandardCharsets.UTF_8)))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sitzungsschutz abgelaufen. Bitte erneut versuchen.");
        }
        return true;
    }
}
