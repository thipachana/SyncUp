package de.thm.syncup.backend.service;
import de.thm.syncup.backend.dto.TerminanfrageRequest;
import de.thm.syncup.backend.model.*;
import de.thm.syncup.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;
import java.util.*;
@Service
public class TerminanfrageService {
    private final TerminanfrageRepository requests;
    private final BenutzerRepository users;
    public TerminanfrageService(TerminanfrageRepository requests, BenutzerRepository users) { this.requests=requests; this.users=users; }
    @Transactional public Terminanfrage erstellen(TerminanfrageRequest data, Benutzer owner) {
        if (data==null) throw Input.bad("Terminanfrage fehlt.");
        if (data.benutzerIds()==null || data.benutzerIds().isEmpty()) throw Input.bad("Mindestens ein Teilnehmer muss ausgewählt werden.");
        String title=Input.text(data.titel(), "Titel", 100);
        var interval=Input.interval(data.zeitraum());
        if (data.dauer()==null || data.dauer()<=0 || data.dauer()>Duration.between(interval.start(), interval.end()).toMinutes()) throw Input.bad("Dauer muss positiv sein und in den Suchzeitraum passen.");
        Set<Long> ids=new LinkedHashSet<>(); ids.add(owner.getBenutzerId());
        if(data.benutzerIds()!=null) { for(Long id:data.benutzerIds()) ids.add(Input.id(id)); }
        var participants=users.findAllById(ids);
        if(participants.size()!=ids.size()) throw Input.bad("Mindestens ein Teilnehmer existiert nicht.");
        var request=new Terminanfrage(title, interval.toString(), data.dauer(), "OFFEN");
        request.setBenutzer(participants); request.setErsteller(owner); return requests.save(request);
    }
}
