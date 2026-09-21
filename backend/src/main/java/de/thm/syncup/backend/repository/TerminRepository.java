package de.thm.syncup.backend.repository;

import de.thm.syncup.backend.model.Termin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TerminRepository extends JpaRepository<Termin, Long> {
    List<Termin> findByKalenderBesitzerBenutzerId(Long benutzerId);
}