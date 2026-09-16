package de.thm.syncup.backend.controller;

import de.thm.syncup.backend.model.Terminanfrage;
import de.thm.syncup.backend.repository.TerminanfrageRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/terminanfragen")
@CrossOrigin(origins = "http://localhost:5173")
public class TerminanfrageController {

    private final TerminanfrageRepository terminanfrageRepository;

    public TerminanfrageController(TerminanfrageRepository terminanfrageRepository) {
        this.terminanfrageRepository = terminanfrageRepository;
    }

    @GetMapping
    public List<Terminanfrage> getAlleTerminanfragen() {
        return terminanfrageRepository.findAll();
    }

    @PostMapping
    public Terminanfrage erstelleTerminanfrage(@RequestBody Terminanfrage terminanfrage) {
        return terminanfrageRepository.save(terminanfrage);
    }
}