package de.thm.syncup.backend.controller;

import de.thm.syncup.backend.model.Terminanfrage;
import de.thm.syncup.backend.service.*;
import de.thm.syncup.backend.security.SessionSecurity;
import de.thm.syncup.backend.dto.TerminanfrageRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;
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
public class TerminanfrageController {

private final TerminanfrageRepository terminanfrageRepository;
private final TerminRepository terminRepository;
private final TerminanfrageService service;
private final SessionSecurity security;
public TerminanfrageController(TerminanfrageRepository requests, TerminRepository terms, TerminanfrageService service, SessionSecurity security) {
    this.terminanfrageRepository=requests; this.terminRepository=terms; this.service=service; this.security=security;
}
@GetMapping public List<Terminanfrage> getAlleTerminanfragen(HttpServletRequest http) {
    return terminanfrageRepository.findByErstellerBenutzerId(security.current(http).getBenutzerId());
}
@PostMapping @ResponseStatus(HttpStatus.CREATED)
public Terminanfrage erstelleTerminanfrage(@RequestBody TerminanfrageRequest data, HttpServletRequest http) {
    return service.erstellen(data, security.current(http));
}
@GetMapping("/{id}") public Terminanfrage getTerminanfrage(@PathVariable Long id, HttpServletRequest http) {
    return terminanfrageRepository.findByTerminanfrageIdAndErstellerBenutzerId(id, security.current(http).getBenutzerId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Terminanfrage nicht gefunden."));
}
@DeleteMapping("/{id}") @Transactional @ResponseStatus(HttpStatus.NO_CONTENT)
public void loescheTerminanfrage(@PathVariable Long id, HttpServletRequest http) { terminanfrageRepository.delete(getTerminanfrage(id,http)); }
@GetMapping("/{id}/freie-zeitfenster")
public List<FreiesZeitfenster> getFreieZeitfenster(@PathVariable Long id, HttpServletRequest http) {
    Terminanfrage anfrage = getTerminanfrage(id,http);
    Input.Interval range;
    try { range=Input.interval(anfrage.getZeitraum()); }
    catch(ResponseStatusException e) { throw new ResponseStatusException(HttpStatus.CONFLICT,"Gespeicherter Suchzeitraum ist ungültig. Bitte Anfrage neu erstellen."); }
    if(anfrage.getDauer()==null || anfrage.getDauer()<=0 || anfrage.getDauer()>Duration.between(range.start(),range.end()).toMinutes() || anfrage.getBenutzer().isEmpty())
        throw new ResponseStatusException(HttpStatus.CONFLICT,"Gespeicherte Dauer oder Teilnehmer sind ungültig.");
    LocalDateTime von=range.start(); LocalDateTime bis=range.end();

    List<Zeitblock> belegteZeiten = new ArrayList<>();

    anfrage.getBenutzer().forEach(benutzer ->
        terminRepository
            .findByKalenderBesitzerBenutzerId(benutzer.getBenutzerId())
            .forEach(termin -> {
                if(termin.getDatum()==null || termin.getStartzeit()==null || termin.getEndzeit()==null || !termin.getEndzeit().isAfter(termin.getStartzeit()))
                    throw new ResponseStatusException(HttpStatus.CONFLICT,"Ein Teilnehmer hat einen ungültigen Terminzeitraum.");
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
@PostMapping("/{id}/erledigt")
@Transactional
public Terminanfrage erledigt(
        @PathVariable Long id,
        HttpServletRequest http
) {
    Long owner = security.current(http).getBenutzerId();

    var request = terminanfrageRepository.findById(id)
            .orElseThrow(() ->
                    new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Terminanfrage nicht gefunden."
                    )
            );

    if (
        request.getErsteller() == null ||
        !request.getErsteller()
                .getBenutzerId()
                .equals(owner)
    ) {
        throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "Nur der Ersteller darf die Terminanfrage abschließen."
        );
    }

    request.setStatus("ERLEDIGT");

    return terminanfrageRepository.save(request);
}
}
