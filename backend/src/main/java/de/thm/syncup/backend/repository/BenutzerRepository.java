package de.thm.syncup.backend.repository;

import de.thm.syncup.backend.model.Benutzer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BenutzerRepository extends JpaRepository<Benutzer, Long> {

    Optional<Benutzer> findByEmail(String email);

    boolean existsByEmail(String email);
}