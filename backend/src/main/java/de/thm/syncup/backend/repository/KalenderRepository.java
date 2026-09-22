package de.thm.syncup.backend.repository;

import de.thm.syncup.backend.model.Kalender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KalenderRepository extends JpaRepository<Kalender, Long> {

    Optional<Kalender> findFirstByBesitzerBenutzerId(Long benutzerId);
}
