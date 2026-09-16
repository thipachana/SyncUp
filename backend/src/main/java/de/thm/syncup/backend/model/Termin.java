package de.thm.syncup.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "termin")
public class Termin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "termin_id")
    private Long terminId;

    @Column(nullable = false, length = 100)
    private String titel;

    @Column(length = 255)
    private String beschreibung;

    @Column(nullable = false)
    private LocalDate datum;

    @Column(name = "startzeit", nullable = false)
    private LocalTime startzeit;

    @Column(name = "endzeit", nullable = false)
    private LocalTime endzeit;

    @Column(nullable = false, length = 50)
    private String status;

    @ManyToOne
    @JoinColumn(name = "kalender_id", nullable = false)
    private Kalender kalender;

    public Termin() {
    }

    public Termin(String titel, String beschreibung, LocalDate datum,
                  LocalTime startzeit, LocalTime endzeit,
                  String status, Kalender kalender) {
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.datum = datum;
        this.startzeit = startzeit;
        this.endzeit = endzeit;
        this.status = status;
        this.kalender = kalender;
    }

    public Long getTerminId() {
        return terminId;
    }

    public void setTerminId(Long terminId) {
        this.terminId = terminId;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public LocalTime getStartzeit() {
        return startzeit;
    }

    public void setStartzeit(LocalTime startzeit) {
        this.startzeit = startzeit;
    }

    public LocalTime getEndzeit() {
        return endzeit;
    }

    public void setEndzeit(LocalTime endzeit) {
        this.endzeit = endzeit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Kalender getKalender() {
        return kalender;
    }

    public void setKalender(Kalender kalender) {
        this.kalender = kalender;
    }
}