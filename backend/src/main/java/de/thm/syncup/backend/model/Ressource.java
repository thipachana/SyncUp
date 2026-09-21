package de.thm.syncup.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ressource")
public class Ressource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ressourcen_id")
    private Long ressourcenId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String typ;

    @Column(nullable = false)
    private Integer kapazitaet;

    @Column(nullable = false)
    private Boolean verfuegbarkeit;

    public Ressource() {
    }

    public Long getRessourcenId() {
        return ressourcenId;
    }

    public void setRessourcenId(Long ressourcenId) {
        this.ressourcenId = ressourcenId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public Integer getKapazitaet() {
        return kapazitaet;
    }

    public void setKapazitaet(Integer kapazitaet) {
        this.kapazitaet = kapazitaet;
    }

    public Boolean getVerfuegbarkeit() {
        return verfuegbarkeit;
    }

    public void setVerfuegbarkeit(Boolean verfuegbarkeit) {
        this.verfuegbarkeit = verfuegbarkeit;
    }
}