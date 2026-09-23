package de.thm.syncup.backend.model;
import jakarta.persistence.*;
import java.time.Instant;
@Entity
public class Benachrichtigung {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
    @com.fasterxml.jackson.annotation.JsonIgnore @Column(nullable=false) public Long empfaengerId;
    @Column(nullable=false,length=500) public String text;
    @Column(nullable=false) public Instant erstelltAm=Instant.now();
    public boolean gelesen=false;
    public Long terminId;
}
