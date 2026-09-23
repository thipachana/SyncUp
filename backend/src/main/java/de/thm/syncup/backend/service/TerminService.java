package de.thm.syncup.backend.service;
import de.thm.syncup.backend.model.*;
import de.thm.syncup.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.time.*;
@Service
public class TerminService {
    private final BenachrichtigungRepository notifications;
    private final TerminRepository terms; private final KalenderRepository calendars; private final BenutzerRepository users; private final BuchungRepository bookings;
    public TerminService(TerminRepository terms,KalenderRepository calendars,BenutzerRepository users,BuchungRepository bookings, BenachrichtigungRepository notifications) { this.notifications=notifications; this.terms=terms;this.calendars=calendars;this.users=users;this.bookings=bookings; }
    @Transactional public Termin create(TerminRequest data,Long owner, boolean explicitCalendar) {
        String title=Input.text(data.titel(),"Titel",100);
        if(data.datum()==null || data.startzeit()==null || data.endzeit()==null || !data.endzeit().isAfter(data.startzeit())) throw Input.bad("Gültiges Datum erforderlich; Ende muss nach Beginn liegen.");
        if(data.beschreibung()!=null && data.beschreibung().length()>255) throw Input.bad("Beschreibung ist zu lang.");
        // Serialize first-calendar creation for old accounts; new accounts already have one.
        var user=users.lockById(owner).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Bitte erneut anmelden."));
        Kalender calendar;
        if(explicitCalendar) {
            Long id=data.kalenderId()!=null ? data.kalenderId() : data.kalender()==null ? null : data.kalender().kalenderId();
            calendar=calendars.findById(Input.id(id)).filter(k -> k.getBesitzer().getBenutzerId().equals(owner))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Kalender nicht gefunden."));
        } else {
            calendar=calendars.findFirstByBesitzerBenutzerId(owner).orElseGet(() -> {var k=new Kalender();k.setName("Mein Kalender");k.setBesitzer(user);return calendars.save(k);});
        }
        var term=new Termin(title,data.beschreibung(),data.datum(),data.startzeit(),data.endzeit(),"BESTAETIGT",calendar);
        term.setTeilnehmer(participants(data.benutzerIds(),owner)); return terms.save(term);
    }
    private java.util.List<Benutzer> participants(java.util.List<Long> ids,Long owner) {
        var unique=new java.util.LinkedHashSet<Long>(); unique.add(owner);
        if(ids!=null) for(Long id:ids) unique.add(Input.id(id));
        var result=users.findAllById(unique);
        if(result.size()!=unique.size()) throw Input.bad("Teilnehmer nicht gefunden.");
        return result;
    }
    @Transactional public Termin update(Long id, TerminRequest data, Long owner) {
        var term=terms.lockById(Input.id(id)).filter(t->t.getKalender().getBesitzer().getBenutzerId().equals(owner))
            .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Termin nicht gefunden."));
        String title=Input.text(data.titel(),"Titel",100);
        if(data.datum()==null || data.startzeit()==null || data.endzeit()==null || !data.endzeit().isAfter(data.startzeit())) throw Input.bad("Ende muss nach Beginn liegen.");
        if(data.beschreibung()!=null && data.beschreibung().length()>255) throw Input.bad("Beschreibung ist zu lang.");
        boolean timeChanged=!term.getDatum().equals(data.datum()) || !term.getStartzeit().equals(data.startzeit()) || !term.getEndzeit().equals(data.endzeit());
        if(timeChanged && bookings.existsByTermin_TerminId(id)) throw new ResponseStatusException(HttpStatus.CONFLICT,"Dieser Termin hat eine Ressourcenbuchung. Datum und Uhrzeit können erst nach Klärung der Buchung geändert werden.");
        var next=data.benutzerIds()==null ? term.getTeilnehmer() : participants(data.benutzerIds(),owner);
        var recipients=new java.util.HashSet<Long>();
        term.getTeilnehmer().forEach(u->recipients.add(u.getBenutzerId())); next.forEach(u->recipients.add(u.getBenutzerId()));
        boolean changed=timeChanged || !term.getTitel().equals(title) || !java.util.Objects.equals(term.getBeschreibung(),data.beschreibung()) || !new java.util.HashSet<>(term.getTeilnehmer()).equals(new java.util.HashSet<>(next));
        term.setTitel(title);term.setBeschreibung(data.beschreibung());term.setDatum(data.datum());term.setStartzeit(data.startzeit());term.setEndzeit(data.endzeit());term.setTeilnehmer(next);
        if(changed) for(Long recipient:recipients) if(!recipient.equals(owner)) {
            var n=new Benachrichtigung();n.empfaengerId=recipient;n.terminId=id;
            n.text=title+" wurde geändert. Neuer Stand: "+data.datum()+", "+data.startzeit()+"–"+data.endzeit()+".";
            if(next.stream().noneMatch(u->u.getBenutzerId().equals(recipient))) n.text=title+": Du wurdest aus dem Termin entfernt.";
            notifications.save(n);
        }
        return terms.save(term);
    }
    @Transactional public void delete(Long id,Long owner) {
        var term=terms.lockById(Input.id(id)).filter(t -> t.getKalender().getBesitzer().getBenutzerId().equals(owner))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Termin nicht gefunden."));
        if(bookings.existsByTermin_TerminId(id)) throw new ResponseStatusException(HttpStatus.CONFLICT,"Dieser Termin hat eine Ressourcenbuchung und kann deshalb nicht gelöscht werden.");
        terms.delete(term);
    }
    public record CalendarId(Long kalenderId) {}
    public record TerminRequest(String titel,String beschreibung,LocalDate datum,LocalTime startzeit,LocalTime endzeit,Long kalenderId,CalendarId kalender,java.util.List<Long> benutzerIds) {}
}
