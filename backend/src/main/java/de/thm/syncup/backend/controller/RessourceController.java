package de.thm.syncup.backend.controller;
import de.thm.syncup.backend.model.Ressource;
import de.thm.syncup.backend.repository.RessourceRepository;
import de.thm.syncup.backend.service.Input;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;
@RestController @RequestMapping("/api/ressourcen")
public class RessourceController {
    private final RessourceRepository repository;
    public RessourceController(RessourceRepository repository) { this.repository=repository; }
    @GetMapping public List<Ressource> list() { return repository.findAll(); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Ressource create(@RequestBody ResourceRequest data) {
        String name=Input.text(data.name(),"Name",100), type=Input.text(data.typ(),"Typ",100);
        if(data.kapazitaet()==null || data.kapazitaet()<=0 || data.verfuegbarkeit()==null) throw Input.bad("Positive Kapazität und Verfügbarkeit erforderlich.");
        var resource=new Ressource();resource.setName(name);resource.setTyp(type);resource.setKapazitaet(data.kapazitaet());resource.setVerfuegbarkeit(data.verfuegbarkeit());return repository.save(resource);
    }
    public record ResourceRequest(String name,String typ,Integer kapazitaet,Boolean verfuegbarkeit) {}
}
