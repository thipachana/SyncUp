package de.thm.syncup.backend.controller;

import de.thm.syncup.backend.model.Buchung;
import de.thm.syncup.backend.model.Ressource;
import de.thm.syncup.backend.model.Termin;
import de.thm.syncup.backend.repository.BuchungRepository;
import de.thm.syncup.backend.repository.RessourceRepository;
import de.thm.syncup.backend.repository.TerminRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buchungen")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5175"})
public class BuchungController {

    private final BuchungRepository buchungRepository;
    private final TerminRepository terminRepository;
    private final RessourceRepository ressourceRepository;

    public BuchungController(
            BuchungRepository buchungRepository,
            TerminRepository terminRepository,
            RessourceRepository ressourceRepository) {
        this.buchungRepository = buchungRepository;
        this.terminRepository = terminRepository;
        this.ressourceRepository = ressourceRepository;
    }

    @GetMapping
    public List<Buchung> getAlleBuchungen() {
        return buchungRepository.findAll();
    }

    @PostMapping
    public Buchung erstelleBuchung(@RequestBody BuchungRequest request) {
        Termin termin = terminRepository.findById(request.terminId()).orElseThrow();
        Ressource ressource = ressourceRepository.findById(request.ressourcenId()).orElseThrow();

        Buchung buchung = new Buchung();
        buchung.setTermin(termin);
        buchung.setRessource(ressource);
        buchung.setZeitraum(request.zeitraum());
        buchung.setStatus(request.status());

        return buchungRepository.save(buchung);
    }

    public record BuchungRequest(
            Long terminId,
            Long ressourcenId,
            String zeitraum,
            String status) {
    }
}
