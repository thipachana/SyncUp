package de.thm.syncup.backend.repository;

import de.thm.syncup.backend.model.Buchung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuchungRepository extends JpaRepository<Buchung, Long> {

    boolean existsByTermin_TerminId(Long id);

    List<Buchung> findByTerminKalenderBesitzerBenutzerId(Long id);

    List<Buchung> findByRessource_RessourcenId(Long ressourcenId);

    @Query("""
        select distinct b
        from Buchung b
        left join b.termin.teilnehmer t
        where b.termin.kalender.besitzer.benutzerId = :benutzerId
           or t.benutzerId = :benutzerId
    """)
    List<Buchung> findVisibleForUser(
            @Param("benutzerId") Long benutzerId
    );
}
