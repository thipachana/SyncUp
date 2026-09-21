package de.thm.syncup.backend.controller;

import de.thm.syncup.backend.model.Terminanfrage;
import de.thm.syncup.backend.repository.TerminanfrageRepository;
import de.thm.syncup.backend.repository.TerminRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.Duration;
import java.util.Comparator;

@RestController
@RequestMapping("/api/terminanfragen")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5175"})public class TerminanfrageController {

private final TerminanfrageRepository terminanfrageRepository;
private final TerminRepository terminRepository;

public TerminanfrageController(
        TerminanfrageRepository terminanfrageRepository,
        TerminRepository terminRepository) {
    this.terminanfrageRepository = terminanfrageRepository;
    this.terminRepository = terminRepository;
}
    @GetMapping
    public List<Terminanfrage> getAlleTerminanfragen() {
        return terminanfrageRepository.findAll();
    }

    @PostMapping
    public Terminanfrage erstelleTerminanfrage(@RequestBody Terminanfrage terminanfrage) {
        return terminanfrageRepository.save(terminanfrage);
    }
    @GetMapping("/{id}")
public Terminanfrage getTerminanfrage(@PathVariable Long id) {
    return terminanfrageRepository.findById(id).orElse(null);
}
@DeleteMapping("/{id}") public void loescheTerminanfrage(@PathVariable Long id) { terminanfrageRepository.deleteById(id); }
@GetMapping("/{id}/freie-zeitfenster")
public List<FreiesZeitfenster> getFreieZeitfenster(@PathVariable Long id) {

    Terminanfrage anfrage = terminanfrageRepository.findById(id).orElse(null);

    if (anfrage == null) {
        return new ArrayList<>();
    }

    String[] zeitraum = anfrage.getZeitraum().split(" bis ");
    LocalDateTime von = LocalDateTime.parse(zeitraum[0]);
    LocalDateTime bis = LocalDateTime.parse(zeitraum[1]);

    List<Zeitblock> belegteZeiten = new ArrayList<>();

    anfrage.getBenutzer().forEach(benutzer ->
        terminRepository
            .findByKalenderBesitzerBenutzerId(benutzer.getBenutzerId())
            .forEach(termin -> {
                LocalDateTime start =
                    LocalDateTime.of(termin.getDatum(), termin.getStartzeit());

                LocalDateTime ende =
                    LocalDateTime.of(termin.getDatum(), termin.getEndzeit());

                if (ende.isAfter(von) && start.isBefore(bis)) {
                    belegteZeiten.add(new Zeitblock(
                        start.isBefore(von) ? von : start,
                        ende.isAfter(bis) ? bis : ende
                    ));
                }
            })
    );

    belegteZeiten.sort(Comparator.comparing(Zeitblock::start));

    List<Zeitblock> zusammengefasst = new ArrayList<>();

    for (Zeitblock block : belegteZeiten) {
        if (zusammengefasst.isEmpty()) {
            zusammengefasst.add(block);
        } else {
            Zeitblock letzter =
                zusammengefasst.get(zusammengefasst.size() - 1);

            if (!block.start().isAfter(letzter.ende())) {
                LocalDateTime neuesEnde =
                    block.ende().isAfter(letzter.ende())
                        ? block.ende()
                        : letzter.ende();

                zusammengefasst.set(
                    zusammengefasst.size() - 1,
                    new Zeitblock(letzter.start(), neuesEnde)
                );
            } else {
                zusammengefasst.add(block);
            }
        }
    }

    List<FreiesZeitfenster> freieZeiten = new ArrayList<>();
    LocalDateTime aktuell = von;

    for (Zeitblock block : zusammengefasst) {
        if (Duration.between(aktuell, block.start()).toMinutes()
                >= anfrage.getDauer()) {
            freieZeiten.add(
                new FreiesZeitfenster(aktuell, block.start())
            );
        }

        if (block.ende().isAfter(aktuell)) {
            aktuell = block.ende();
        }
    }

    if (Duration.between(aktuell, bis).toMinutes()
            >= anfrage.getDauer()) {
        freieZeiten.add(new FreiesZeitfenster(aktuell, bis));
    }

    return freieZeiten;
}

private record Zeitblock(LocalDateTime start, LocalDateTime ende) {}

public record FreiesZeitfenster(LocalDateTime start, LocalDateTime ende) {}
}