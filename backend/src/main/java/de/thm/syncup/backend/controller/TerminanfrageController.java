package de.thm.syncup.backend.controller;

import de.thm.syncup.backend.dto.TerminanfrageRequest;
import de.thm.syncup.backend.model.Terminanfrage;
import de.thm.syncup.backend.repository.TerminanfrageRepository;
import de.thm.syncup.backend.repository.TerminRepository;
import de.thm.syncup.backend.service.TerminanfrageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/terminanfragen")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5175"})
public class TerminanfrageController {

    private final TerminanfrageRepository terminanfrageRepository;
    private final TerminRepository terminRepository;
    private final TerminanfrageService terminanfrageService;

    public TerminanfrageController(
            TerminanfrageRepository terminanfrageRepository,
            TerminRepository terminRepository,
            TerminanfrageService terminanfrageService) {
        this.terminanfrageRepository = terminanfrageRepository;
        this.terminRepository = terminRepository;
        this.terminanfrageService = terminanfrageService;
    }

    @GetMapping
    public List<Terminanfrage> getAlleTerminanfragen() {
        return terminanfrageRepository.findAll();
    }

    @PostMapping
    public Terminanfrage erstelleTerminanfrage(
            @RequestBody TerminanfrageRequest request) {

        pruefeTerminanfrage(request);
        return terminanfrageService.erstellen(request);
    }

    @GetMapping("/{id}")
    public Terminanfrage getTerminanfrage(@PathVariable Long id) {
        return terminanfrageRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void loescheTerminanfrage(@PathVariable Long id) {
        terminanfrageRepository.deleteById(id);
    }

    @GetMapping("/{id}/freie-zeitfenster")
    public List<FreiesZeitfenster> getFreieZeitfenster(@PathVariable Long id) {

        Terminanfrage anfrage =
                terminanfrageRepository.findById(id).orElse(null);

        if (anfrage == null) {
            return new ArrayList<>();
        }

        String[] zeitraum = anfrage.getZeitraum().split(" bis ");
        LocalDateTime von = LocalDateTime.parse(zeitraum[0]);
        LocalDateTime bis = LocalDateTime.parse(zeitraum[1]);

        List<Zeitblock> belegteZeiten = new ArrayList<>();

        anfrage.getBenutzer().forEach(benutzer ->
                terminRepository
                        .findByKalenderBesitzerBenutzerId(
                                benutzer.getBenutzerId()
                        )
                        .forEach(termin -> {
                            LocalDateTime start =
                                    LocalDateTime.of(
                                            termin.getDatum(),
                                            termin.getStartzeit()
                                    );

                            LocalDateTime ende =
                                    LocalDateTime.of(
                                            termin.getDatum(),
                                            termin.getEndzeit()
                                    );

                            if (ende.isAfter(von)
                                    && start.isBefore(bis)) {
                                belegteZeiten.add(
                                        new Zeitblock(
                                                start.isBefore(von)
                                                        ? von
                                                        : start,
                                                ende.isAfter(bis)
                                                        ? bis
                                                        : ende
                                        )
                                );
                            }
                        })
        );

        belegteZeiten.sort(
                Comparator.comparing(Zeitblock::start)
        );

        List<Zeitblock> zusammengefasst = new ArrayList<>();

        for (Zeitblock block : belegteZeiten) {
            if (zusammengefasst.isEmpty()) {
                zusammengefasst.add(block);
            } else {
                Zeitblock letzter =
                        zusammengefasst.get(
                                zusammengefasst.size() - 1
                        );

                if (!block.start().isAfter(letzter.ende())) {
                    LocalDateTime neuesEnde =
                            block.ende().isAfter(letzter.ende())
                                    ? block.ende()
                                    : letzter.ende();

                    zusammengefasst.set(
                            zusammengefasst.size() - 1,
                            new Zeitblock(
                                    letzter.start(),
                                    neuesEnde
                            )
                    );
                } else {
                    zusammengefasst.add(block);
                }
            }
        }

        List<FreiesZeitfenster> freieZeiten =
                new ArrayList<>();

        LocalDateTime aktuell = von;

        for (Zeitblock block : zusammengefasst) {
            if (Duration.between(
                    aktuell,
                    block.start()
            ).toMinutes() >= anfrage.getDauer()) {
                freieZeiten.add(
                        new FreiesZeitfenster(
                                aktuell,
                                block.start()
                        )
                );
            }

            if (block.ende().isAfter(aktuell)) {
                aktuell = block.ende();
            }
        }

        if (Duration.between(
                aktuell,
                bis
        ).toMinutes() >= anfrage.getDauer()) {
            freieZeiten.add(
                    new FreiesZeitfenster(
                            aktuell,
                            bis
                    )
            );
        }

        return freieZeiten;
    }

    private void pruefeTerminanfrage(
            TerminanfrageRequest request) {

        if (request == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Terminanfrage fehlt."
            );
        }

        if (request.titel() == null
                || request.titel().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ein Titel muss angegeben werden."
            );
        }

        if (request.dauer() == null
                || request.dauer() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Die Dauer muss größer als 0 sein."
            );
        }

        if (request.benutzerIds() == null
                || request.benutzerIds().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Mindestens ein Teilnehmer muss ausgewählt werden."
            );
        }

        if (request.zeitraum() == null
                || request.zeitraum().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ein Suchzeitraum muss angegeben werden."
            );
        }

        String[] teile =
                request.zeitraum().split(" bis ", -1);

        if (teile.length != 2
                || teile[0].isBlank()
                || teile[1].isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ungültiger Suchzeitraum."
            );
        }

        try {
            LocalDateTime start =
                    LocalDateTime.parse(teile[0]);

            LocalDateTime ende =
                    LocalDateTime.parse(teile[1]);

            if (!ende.isAfter(start)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Das Ende muss nach dem Anfang liegen."
                );
            }

            long suchdauer =
                    Duration.between(start, ende).toMinutes();

            if (request.dauer() > suchdauer) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Die gewünschte Dauer darf nicht länger als der Suchzeitraum sein."
                );
            }

        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ungültiges Datumsformat im Suchzeitraum."
            );
        }
    }

    private record Zeitblock(
            LocalDateTime start,
            LocalDateTime ende) {
    }

    public record FreiesZeitfenster(
            LocalDateTime start,
            LocalDateTime ende) {
    }
}
