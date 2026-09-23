package de.thm.syncup.backend.repository;

import de.thm.syncup.backend.model.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RessourceRepository extends JpaRepository<Ressource, Long> {
    @org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
    @org.springframework.data.jpa.repository.Query("select r from Ressource r where r.ressourcenId = :id")
    java.util.Optional<Ressource> lockById(@org.springframework.data.repository.query.Param("id") Long id);
}