package de.thm.syncup.backend.repository;
import de.thm.syncup.backend.model.Benachrichtigung;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface BenachrichtigungRepository extends JpaRepository<Benachrichtigung,Long> {
 List<Benachrichtigung> findByEmpfaengerIdOrderByErstelltAmDesc(Long owner);
 Optional<Benachrichtigung> findByIdAndEmpfaengerId(Long id,Long owner);
}
