package de.thm.syncup.backend.controller;

import de.thm.syncup.backend.model.Buchung;
import de.thm.syncup.backend.model.Ressource;
import de.thm.syncup.backend.model.Termin;
import de.thm.syncup.backend.repository.BuchungRepository;
import de.thm.syncup.backend.repository.RessourceRepository;
import de.thm.syncup.backend.repository.TerminRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
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

        // Zeitraum zuerst validieren, bevor irgendetwas gespeichert wird.
        pruefeZeitraum(request.zeitraum());

        Termin termin = terminRepository.findById(request.terminId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Termin wurde nicht gefunden."
                ));

        Ressource ressource = ressourceRepository.findById(request.ressourcenId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Ressource wurde nicht gefunden."
                ));

        boolean belegt = buchungRepository
                .findByRessource_RessourcenId(request.ressourcenId())
                .stream()
                .anyMatch(buchung ->
                        ueberschneidet(
                                buchung.getZeitraum(),
                                request.zeitraum()
                        )
                );

        if (belegt) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ressource ist in diesem Zeitraum bereits gebucht."
            );
        }

        Buchung buchung = new Buchung();
        buchung.setTermin(termin);
        buchung.setRessource(ressource);
        buchung.setZeitraum(request.zeitraum());
        buchung.setStatus(request.status());

        return buchungRepository.save(buchung);
    }

    private void pruefeZeitraum(String zeitraum) {

        if (zeitraum == null || zeitraum.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ein Buchungszeitraum muss angegeben werden."
            );
        }

        String[] teile = zeitraum.split(" bis ", -1);

        if (teile.length != 2
                || teile[0].isBlank()
                || teile[1].isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ungültiger Buchungszeitraum."
            );
        }

        try {
            LocalDateTime start = LocalDateTime.parse(teile[0]);
            LocalDateTime ende = LocalDateTime.parse(teile[1]);

            if (!ende.isAfter(start)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Das Ende muss nach dem Anfang liegen."
                );
            }

        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ungültiges Datumsformat im Buchungszeitraum."
            );
        }
    }

    private boolean ueberschneidet(String zeitraum1, String zeitraum2) {

        String[] teile1 = zeitraum1.split(" bis ");
        String[] teile2 = zeitraum2.split(" bis ");

        LocalDateTime start1 = LocalDateTime.parse(teile1[0]);
        LocalDateTime ende1 = LocalDateTime.parse(teile1[1]);

        LocalDateTime start2 = LocalDateTime.parse(teile2[0]);
        LocalDateTime ende2 = LocalDateTime.parse(teile2[1]);

        return start1.isBefore(ende2) && start2.isBefore(ende1);
    }

    public record BuchungRequest(
            Long terminId,
            Long ressourcenId,
            String zeitraum,
            String status) {
    }
}
