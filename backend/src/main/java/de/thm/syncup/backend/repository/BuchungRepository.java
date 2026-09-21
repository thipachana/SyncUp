package de.thm.syncup.backend.repository;

import de.thm.syncup.backend.model.Buchung;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuchungRepository extends JpaRepository<Buchung, Long> {
}
