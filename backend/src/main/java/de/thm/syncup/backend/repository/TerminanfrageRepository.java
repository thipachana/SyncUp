package de.thm.syncup.backend.repository;

import de.thm.syncup.backend.model.Terminanfrage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminanfrageRepository extends JpaRepository<Terminanfrage, Long> {
}