package de.thm.syncup.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "buchung")
public class Buchung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buchung_id")
    private Long buchungId;

    @ManyToOne
    @JoinColumn(name = "termin_id", nullable = false)
    private Termin termin;

    @ManyToOne
    @JoinColumn(name = "ressourcen_id", nullable = false)
    private Ressource ressource;

    @Column(nullable = false, length = 100)
    private String zeitraum;

    @Column(nullable = false, length = 50)
    private String status;

    public Buchung() {
    }

    public Long getBuchungId() {
        return buchungId;
    }

    public void setBuchungId(Long buchungId) {
        this.buchungId = buchungId;
    }

    public Termin getTermin() {
        return termin;
    }

    public void setTermin(Termin termin) {
        this.termin = termin;
    }

    public Ressource getRessource() {
        return ressource;
    }

    public void setRessource(Ressource ressource) {
        this.ressource = ressource;
    }

    public String getZeitraum() {
        return zeitraum;
    }

    public void setZeitraum(String zeitraum) {
        this.zeitraum = zeitraum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}