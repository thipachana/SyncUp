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
@ManyToMany(fetch = FetchType.EAGER)
@JoinTable(
    name = "terminanfrage_benutzer",
    joinColumns = @JoinColumn(name = "terminanfrage_id"),
    inverseJoinColumns = @JoinColumn(name = "benutzer_id")
)
private java.util.List<Benutzer> benutzer = new java.util.ArrayList<>();
    @com.fasterxml.jackson.annotation.JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ersteller_id")
    private Benutzer ersteller;
    public Benutzer getErsteller() { return ersteller; }
    public void setErsteller(Benutzer value) { ersteller = value; }

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
   
public java.util.List<Benutzer> getBenutzer() {
    return benutzer;
}

public void setBenutzer(java.util.List<Benutzer> benutzer) {
    this.benutzer = benutzer;
}
}