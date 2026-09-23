package de.thm.syncup.backend.repository;

import de.thm.syncup.backend.model.Buchung;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuchungRepository extends JpaRepository<Buchung, Long> {
    boolean existsByTermin_TerminId(Long id);
    List<Buchung> findByTerminKalenderBesitzerBenutzerId(Long id);

List<Buchung> findByRessource_RessourcenId(Long ressourcenId);
}
