package de.thm.syncup.backend.repository;

import de.thm.syncup.backend.model.Benutzer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BenutzerRepository extends JpaRepository<Benutzer, Long> {

    Optional<Benutzer> findByEmailIgnoreCase(String email);

    @org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
    @org.springframework.data.jpa.repository.Query("select b from Benutzer b where b.benutzerId = :id")
    Optional<Benutzer> lockById(@org.springframework.data.repository.query.Param("id") Long id);

    boolean existsByEmailIgnoreCase(String email);
}