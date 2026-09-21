package de.thm.syncup.backend.controller;

import de.thm.syncup.backend.model.Ressource;
import de.thm.syncup.backend.repository.RessourceRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ressourcen")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5175"})
public class RessourceController {

    private final RessourceRepository ressourceRepository;

    public RessourceController(RessourceRepository ressourceRepository) {
        this.ressourceRepository = ressourceRepository;
    }

    @GetMapping
    public List<Ressource> getAlleRessourcen() {
        return ressourceRepository.findAll();
    }

    @PostMapping
    public Ressource erstelleRessource(@RequestBody Ressource ressource) {
        return ressourceRepository.save(ressource);
    }
}
