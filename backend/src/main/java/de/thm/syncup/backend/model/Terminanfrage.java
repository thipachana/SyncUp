package de.thm.syncup.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "terminanfrage")
public class Terminanfrage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "terminanfrage_id")
    private Long terminanfrageId;

    @Column(nullable = false, length = 100)
    private String titel;

    @Column(nullable = false, length = 255)
    private String zeitraum;

    @Column(nullable = false)
    private Integer dauer;

    @Column(nullable = false, length = 50)
    private String status;

    public Terminanfrage() {
    }

    public Terminanfrage(String titel, String zeitraum, Integer dauer, String status) {
        this.titel = titel;
        this.zeitraum = zeitraum;
        this.dauer = dauer;
        this.status = status;
    }

    public Long getTerminanfrageId() {
        return terminanfrageId;
    }

    public void setTerminanfrageId(Long terminanfrageId) {
        this.terminanfrageId = terminanfrageId;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getZeitraum() {
        return zeitraum;
    }

    public void setZeitraum(String zeitraum) {
        this.zeitraum = zeitraum;
    }

    public Integer getDauer() {
        return dauer;
    }

    public void setDauer(Integer dauer) {
        this.dauer = dauer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}