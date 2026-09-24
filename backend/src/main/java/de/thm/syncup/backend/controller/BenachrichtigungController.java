package de.thm.syncup.backend.controller;
import de.thm.syncup.backend.model.Benachrichtigung;
import de.thm.syncup.backend.repository.BenachrichtigungRepository;
import de.thm.syncup.backend.security.SessionSecurity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
@RestController @RequestMapping("/api/me/benachrichtigungen")
public class BenachrichtigungController {
 private final BenachrichtigungRepository notifications; private final SessionSecurity security;
 public BenachrichtigungController(BenachrichtigungRepository notifications,SessionSecurity security) {this.notifications=notifications;this.security=security;}
 @GetMapping public List<Benachrichtigung> list(HttpServletRequest http) {return notifications.findByEmpfaengerIdOrderByErstelltAmDesc(security.current(http).getBenutzerId());}
 @PostMapping("/{id}/gelesen") public Benachrichtigung read(@PathVariable Long id,HttpServletRequest http) {
 var item=notifications.findByIdAndEmpfaengerId(id,security.current(http).getBenutzerId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
 item.gelesen=true;return notifications.save(item);
 }
 @DeleteMapping("/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void delete(@PathVariable Long id, HttpServletRequest http) {
    var item = notifications
        .findByIdAndEmpfaengerId(
            id,
            security.current(http).getBenutzerId()
        )
        .orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

    notifications.delete(item);
}
}
