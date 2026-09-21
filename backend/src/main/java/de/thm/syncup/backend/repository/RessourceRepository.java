package de.thm.syncup.backend.repository;

import de.thm.syncup.backend.model.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RessourceRepository extends JpaRepository<Ressource, Long> {
}