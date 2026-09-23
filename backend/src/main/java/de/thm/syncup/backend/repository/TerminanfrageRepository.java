package de.thm.syncup.backend.repository;

import de.thm.syncup.backend.model.Terminanfrage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminanfrageRepository extends JpaRepository<Terminanfrage, Long> {
    java.util.List<Terminanfrage> findByErstellerBenutzerId(Long id);
    java.util.Optional<Terminanfrage> findByTerminanfrageIdAndErstellerBenutzerId(Long id, Long owner);
}