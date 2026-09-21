package de.thm.syncup.backend.controller;

import de.thm.syncup.backend.model.Termin;
import de.thm.syncup.backend.repository.TerminRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


@RestController
@RequestMapping("/api/termine")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5175"})public class TerminController {

    private final TerminRepository terminRepository;

    public TerminController(TerminRepository terminRepository) {
        this.terminRepository = terminRepository;
    }

    @GetMapping
    public List<Termin> getAlleTermine() {
        return terminRepository.findAll();
    }
    @PostMapping
public Termin erstelleTermin(@RequestBody Termin termin) {
    return terminRepository.save(termin);
}
}