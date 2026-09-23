package de.thm.syncup.backend.repository;

import de.thm.syncup.backend.model.Termin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TerminRepository extends JpaRepository<Termin, Long> {
    java.util.Optional<Termin> findByTerminIdAndKalenderBesitzerBenutzerId(Long id, Long owner);
    @org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
    @org.springframework.data.jpa.repository.Query("select t from Termin t where t.terminId = :id")
    java.util.Optional<Termin> lockById(@org.springframework.data.repository.query.Param("id") Long id);
    @org.springframework.data.jpa.repository.Query("select distinct t from Termin t left join t.teilnehmer p where t.kalender.besitzer.benutzerId = :benutzerId or p.benutzerId = :benutzerId")
    List<Termin> findByKalenderBesitzerBenutzerId(@org.springframework.data.repository.query.Param("benutzerId") Long benutzerId);
}