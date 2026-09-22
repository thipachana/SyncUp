package de.thm.syncup.backend.service;

import de.thm.syncup.backend.dto.TerminanfrageRequest;
import de.thm.syncup.backend.model.Benutzer;
import de.thm.syncup.backend.model.Terminanfrage;
import de.thm.syncup.backend.repository.BenutzerRepository;
import de.thm.syncup.backend.repository.TerminanfrageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TerminanfrageService {

    private final TerminanfrageRepository terminanfrageRepository;
    private final BenutzerRepository benutzerRepository;

    public TerminanfrageService(
        TerminanfrageRepository terminanfrageRepository,
        BenutzerRepository benutzerRepository
    ) {
        this.terminanfrageRepository = terminanfrageRepository;
        this.benutzerRepository = benutzerRepository;
    }

    public Terminanfrage erstellen(TerminanfrageRequest request) {

        List<Long> ids =
            request.benutzerIds() == null
                ? List.of()
                : request.benutzerIds();

        List<Benutzer> benutzer =
            benutzerRepository.findAllById(ids);

        Terminanfrage anfrage = new Terminanfrage();
        anfrage.setTitel(request.titel());
        anfrage.setZeitraum(request.zeitraum());
        anfrage.setDauer(request.dauer());
        anfrage.setStatus(
            request.status() == null
                ? "OFFEN"
                : request.status()
        );
        anfrage.setBenutzer(benutzer);

        return terminanfrageRepository.save(anfrage);
    }
}
