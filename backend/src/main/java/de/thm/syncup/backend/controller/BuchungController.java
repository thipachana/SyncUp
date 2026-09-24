package de.thm.syncup.backend.controller;

import de.thm.syncup.backend.model.Buchung;
import de.thm.syncup.backend.repository.BuchungRepository;
import de.thm.syncup.backend.repository.RessourceRepository;
import de.thm.syncup.backend.repository.TerminRepository;
import de.thm.syncup.backend.security.SessionSecurity;
import de.thm.syncup.backend.service.Input;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/api/buchungen")
public class BuchungController {

    private final BuchungRepository bookings;
    private final TerminRepository terms;
    private final RessourceRepository resources;
    private final SessionSecurity security;

    public BuchungController(
            BuchungRepository bookings,
            TerminRepository terms,
            RessourceRepository resources,
            SessionSecurity security
    ) {
        this.bookings = bookings;
        this.terms = terms;
        this.resources = resources;
        this.security = security;
    }

    @GetMapping
    public List<Buchung> list(HttpServletRequest http) {
        return bookings.findVisibleForUser(
                security.current(http).getBenutzerId()
        );
    }
@GetMapping("/verfuegbarkeit")
public Map<Long, Boolean> verfuegbarkeit(
        @RequestParam Long terminId,
        HttpServletRequest http
) {
    Long owner = security.current(http).getBenutzerId();

    var term = terms.findById(Input.id(terminId))
            .filter(t ->
                    t.getKalender()
                            .getBesitzer()
                            .getBenutzerId()
                            .equals(owner)
            )
            .orElseThrow(() ->
                    new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Termin nicht gefunden."
                    )
            );

    var interval = new Input.Interval(
            LocalDateTime.of(
                    term.getDatum(),
                    term.getStartzeit()
            ),
            LocalDateTime.of(
                    term.getDatum(),
                    term.getEndzeit()
            )
    );

    Map<Long, Boolean> result = new LinkedHashMap<>();

    for (var resource : resources.findAll()) {
        boolean available =
                Boolean.TRUE.equals(resource.getVerfuegbarkeit());

        if (available) {
            for (var booking :
                    bookings.findByRessource_RessourcenId(
                            resource.getRessourcenId()
                    )) {

                var occupied = Input.interval(
                        booking.getZeitraum()
                );

                if (interval.overlaps(occupied)) {
                    available = false;
                    break;
                }
            }
        }

        result.put(
                resource.getRessourcenId(),
                available
        );
    }

    return result;
}
    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public Buchung create(
            @RequestBody BuchungRequest data,
            HttpServletRequest http
    ) {
        Input.id(data.terminId());
        Input.id(data.ressourcenId());

        Long owner = security.current(http).getBenutzerId();

        var term = terms.lockById(data.terminId())
                .filter(t ->
                        t.getKalender()
                                .getBesitzer()
                                .getBenutzerId()
                                .equals(owner)
                )
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Termin nicht gefunden."
                        )
                );

        if (bookings.existsByTermin_TerminId(data.terminId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Für diesen Termin wurde bereits ein Raum reserviert."
            );
        }

        var interval = new Input.Interval(
                LocalDateTime.of(
                        term.getDatum(),
                        term.getStartzeit()
                ),
                LocalDateTime.of(
                        term.getDatum(),
                        term.getEndzeit()
                )
        );

        var resource = resources.lockById(data.ressourcenId())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Ressource nicht gefunden."
                        )
                );

        if (!Boolean.TRUE.equals(resource.getVerfuegbarkeit())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Diese Ressource ist nicht verfügbar."
            );
        }

        for (var booking :
                bookings.findByRessource_RessourcenId(
                        data.ressourcenId()
                )) {

            Input.Interval occupied;

            try {
                occupied = Input.interval(
                        booking.getZeitraum()
                );
            } catch (ResponseStatusException e) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Eine vorhandene Buchung enthält einen ungültigen Zeitraum."
                );
            }

            if (interval.overlaps(occupied)) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Ressource ist in diesem Zeitraum bereits gebucht."
                );
            }
        }

        var booking = new Buchung();

        booking.setTermin(term);
        booking.setRessource(resource);
        booking.setZeitraum(interval.toString());
        booking.setStatus("BESTAETIGT");

        return bookings.save(booking);
    }

    public record BuchungRequest(
            Long terminId,
            Long ressourcenId,
            String zeitraum
    ) {
    }
}
