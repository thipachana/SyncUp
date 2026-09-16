package de.thm.syncup.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "kalender")
public class Kalender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kalender_id")
    private Long kalenderId;

    @Column(nullable = false)
    private String name;

    private String beschreibung;

    @ManyToOne
    @JoinColumn(name = "besitzer_id", nullable = false)
    private Benutzer besitzer;

    public Kalender() {
    }

    public Long getKalenderId() {
        return kalenderId;
    }

    public void setKalenderId(Long kalenderId) {
        this.kalenderId = kalenderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public Benutzer getBesitzer() {
        return besitzer;
    }

    public void setBesitzer(Benutzer besitzer) {
        this.besitzer = besitzer;
    }
}