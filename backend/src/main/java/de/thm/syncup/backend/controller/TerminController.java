package de.thm.syncup.backend.controller;
import de.thm.syncup.backend.model.Termin;
import de.thm.syncup.backend.repository.TerminRepository;
import de.thm.syncup.backend.service.TerminService;
import de.thm.syncup.backend.security.SessionSecurity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;
@RestController @RequestMapping("/api/termine")
public class TerminController {
    private final TerminRepository terms; private final TerminService service; private final SessionSecurity security;
    public TerminController(TerminRepository terms,TerminService service,SessionSecurity security) {this.terms=terms;this.service=service;this.security=security;}
    @GetMapping public List<Termin> list(HttpServletRequest http) {return terms.findByKalenderBesitzerBenutzerId(security.current(http).getBenutzerId());}
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Termin create(@RequestBody TerminService.TerminRequest data,HttpServletRequest http) {return service.create(data,security.current(http).getBenutzerId(),true);}
    @PutMapping("/{id}") public Termin update(@PathVariable Long id,@RequestBody TerminService.TerminRequest data,HttpServletRequest http) {return service.update(id,data,security.current(http).getBenutzerId());}
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id,HttpServletRequest http) {service.delete(id,security.current(http).getBenutzerId());}
}
