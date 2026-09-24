package de.thm.syncup.backend;

import de.thm.syncup.backend.model.*;
import de.thm.syncup.backend.repository.*;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.*;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.JsonNode;
import java.util.*;
import java.util.concurrent.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApiIntegrationTests {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper json;
    @Autowired BenutzerRepository users;
    @Autowired KalenderRepository calendars;
    @Autowired TerminRepository terms;
    @Autowired BuchungRepository bookings;
    @Autowired RessourceRepository resources;
    @Autowired TerminanfrageRepository requests;
    static final String PASSWORD="NurFuerIsolierteTests-2026!";
    static final String HASH=new BCryptPasswordEncoder(4).encode(PASSWORD);
    Benutzer alice,bob;
    MockHttpSession a,b;
    record Result(int status, JsonNode body, MockHttpSession session) {}
    Result call(String method,String path,Object body,MockHttpSession session,boolean csrf) throws Exception {
        var request=switch(method) {case "PUT" -> put(path);case "POST" -> post(path);case "DELETE" -> delete(path);default -> get(path);};
        if(session!=null) request.session(session);
        if(body!=null) request.contentType(MediaType.APPLICATION_JSON).content(json.writeValueAsString(body));
        if(csrf) {
            var token=call("GET","/api/auth/csrf",null,session,false);
            request.session(token.session()).header("X-CSRF-TOKEN",token.body().get("token").asText());
        }
        var result=mvc.perform(request).andReturn();
        String content=result.getResponse().getContentAsString();
        HttpSession actual=result.getRequest().getSession(false);
        return new Result(result.getResponse().getStatus(),content.isBlank()?null:json.readTree(content),(MockHttpSession)actual);
    }
    Benutzer user(String name) {
        var user=new Benutzer();user.setName(name);user.setEmail(UUID.randomUUID()+"@example.test");user.setPasswort(HASH);user.setRolle("USER");return users.save(user);
    }
    MockHttpSession login(Benutzer user) throws Exception {
        var result=call("POST","/api/auth/login",Map.of("email",user.getEmail(),"passwort",PASSWORD),null,true);
        assertThat(result.status()).isEqualTo(200);return result.session();
    }
    @BeforeEach void setup() throws Exception {alice=user("Alice");bob=user("Bob");a=login(alice);b=login(bob);}
    Result term(MockHttpSession session,String start,String end) throws Exception {
        return call("POST","/api/me/termine",Map.of("titel","Testtermin","datum","2026-09-24","startzeit",start,"endzeit",end),session,true);
    }
    Result resource(boolean available) throws Exception {
        return call("POST","/api/ressourcen",Map.of("name","Testraum","typ","Raum","kapazitaet",4,"verfuegbarkeit",available),a,true);
    }
    Result booking(MockHttpSession session,long term,long resource,String range) throws Exception {
        return call("POST","/api/buchungen",Map.of("terminId",term,"ressourcenId",resource,"zeitraum",range),session,true);
    }
    Map<String,Object> query(String range,int duration,List<Long> participants) {
        return Map.of("titel","Besprechung","zeitraum",range,"dauer",duration,"benutzerIds",participants);
    }
    @Test void allDomainEndpointsRequireSession() throws Exception {
        for(String path:List.of("/api/auth/me","/api/termine","/api/me/termine","/api/benutzer","/api/terminanfragen","/api/buchungen","/api/ressourcen"))
            assertThat(call("GET",path,null,null,false).status()).as(path).isEqualTo(401);
        for(String path:List.of("/api/terminanfragen","/api/buchungen","/api/ressourcen"))
            assertThat(call("POST",path,Map.of(),null,false).status()).isEqualTo(401);
    }
    @Test void csrfAndSessionRotationAndLogout() throws Exception {
        var initial=call("GET","/api/auth/csrf",null,null,false);String oldId=initial.session().getId();
        var loggedIn=call("POST","/api/auth/login",Map.of("email",alice.getEmail(),"passwort",PASSWORD),initial.session(),true);
        assertThat(loggedIn.session().getId()).isNotEqualTo(oldId);
        assertThat(call("POST","/api/me/termine",Map.of(),a,false).status()).isEqualTo(403);
        assertThat(call("POST","/api/auth/logout",null,a,true).status()).isEqualTo(200);
        assertThat(a.isInvalid()).isTrue();
        assertThat(call("GET","/api/auth/me",null,null,false).status()).isEqualTo(401);
    }
    @Test void registrationValidationHashAndCalendar() throws Exception {
        String email=UUID.randomUUID()+"@example.test";
        var result=call("POST","/api/auth/register",Map.of("name","Neu","email",email.toUpperCase(),"passwort",PASSWORD),null,true);
        assertThat(result.status()).isEqualTo(201);assertThat(result.body().has("passwort")).isFalse();
        var user=users.findByEmailIgnoreCase(email).orElseThrow();
        assertThat(new BCryptPasswordEncoder().matches(PASSWORD,user.getPasswort())).isTrue();
        assertThat(calendars.findFirstByBesitzerBenutzerId(user.getBenutzerId())).isPresent();
        assertThat(call("POST","/api/auth/register",Map.of("name","Neu","email",email,"passwort",PASSWORD),null,true).status()).isEqualTo(409);
        for(String password:List.of("kurz","😀".repeat(20))) assertThat(call("POST","/api/auth/register",Map.of("name","Neu","email",UUID.randomUUID()+"@example.test","passwort",password),null,true).status()).isEqualTo(400);
        assertThat(call("POST","/api/auth/register",Map.of("name","Neu","email","ungültig","passwort",PASSWORD),null,true).status()).isEqualTo(400);
        assertThat(call("POST","/api/auth/login",Map.of("email",email,"passwort","falsch"),null,true).status()).isEqualTo(401);
    }
    @Test void ownTermsAndNoEntityIdOverwrite() throws Exception {
        long id=term(a,"09:00","10:00").body().get("terminId").asLong();
        assertThat(call("GET","/api/me/termine",null,b,false).body().size()).isZero();
        assertThat(call("DELETE","/api/me/termine/"+id,null,b,true).status()).isEqualTo(404);
        var own=term(b,"11:00","12:00");long calendar=own.body().get("kalender").get("kalenderId").asLong();
        var forged=call("POST","/api/termine",Map.of("terminId",id,"titel","Neuer Termin","datum","2026-09-24","startzeit","13:00","endzeit","14:00","kalenderId",calendar),b,true);
        assertThat(forged.status()).isEqualTo(201);assertThat(forged.body().get("terminId").asLong()).isNotEqualTo(id);
        assertThat(terms.findById(id).orElseThrow().getTitel()).isEqualTo("Testtermin");
        assertThat(term(a,"11:00","10:00").status()).isEqualTo(400);
        assertThat(call("DELETE","/api/me/termine/"+id,null,a,true).status()).isEqualTo(204);
    }
    @Test void requestsValidateParticipantsDurationAndOwnership() throws Exception {
        String range="2026-09-24T09:00 bis 2026-09-24T13:00";
        for(int duration:List.of(0,-1,300)) assertThat(call("POST","/api/terminanfragen",query(range,duration,List.of()),a,true).status()).isEqualTo(400);
        assertThat(call("POST","/api/terminanfragen",query("abc",60,List.of()),a,true).status()).isEqualTo(400);
        assertThat(call("POST","/api/terminanfragen",query(range,60,List.of(Long.MAX_VALUE)),a,true).status()).isEqualTo(400);
        var created=call("POST","/api/terminanfragen",query(range,60,List.of(bob.getBenutzerId())),a,true);
        assertThat(created.status()).isEqualTo(201);assertThat(created.body().get("benutzer").size()).isEqualTo(2);
        String path="/api/terminanfragen/"+created.body().get("terminanfrageId").asLong();
        assertThat(call("GET",path,null,b,false).status()).isEqualTo(404);
        assertThat(call("DELETE",path,null,b,true).status()).isEqualTo(404);
        assertThat(call("GET","/api/terminanfragen/9223372036854775807/freie-zeitfenster",null,a,false).status()).isEqualTo(404);
    }
    @Test void slotsIncludeBothCalendarsAndMergeOverlaps() throws Exception {
        term(a,"09:00","10:00");term(b,"09:30","11:00");
        var created=call("POST","/api/terminanfragen",query("2026-09-24T09:00 bis 2026-09-24T13:00",60,List.of(bob.getBenutzerId())),a,true);
        String path="/api/terminanfragen/"+created.body().get("terminanfrageId").asLong()+"/freie-zeitfenster";
        var slots=call("GET",path,null,a,false);assertThat(slots.status()).isEqualTo(200);assertThat(slots.body().size()).isEqualTo(1);
        assertThat(slots.body().get(0).get("start").asText()).startsWith("2026-09-24T11:00");
        assertThat(slots.body().get(0).get("ende").asText()).startsWith("2026-09-24T13:00");
    }
    @Test
void bookingsCheckAvailabilityOwnershipAndConflicts() throws Exception {
    long term = term(a, "09:00", "10:00")
            .body()
            .get("terminId")
            .asLong();

    long room = resource(true)
            .body()
            .get("ressourcenId")
            .asLong();

    long disabled = resource(false)
            .body()
            .get("ressourcenId")
            .asLong();

    String range = "2026-09-24T09:00 bis 2026-09-24T10:00";

    // Fremder Benutzer darf den Termin nicht buchen
    assertThat(
            booking(b, term, room, range).status()
    ).isEqualTo(404);

    // Nicht verfügbare Ressource darf nicht gebucht werden
    assertThat(
            booking(a, term, disabled, range).status()
    ).isEqualTo(409);

    // Zeitraum aus dem Request ist für die Buchung nicht maßgeblich.
    // Die Zeit wird aus dem Termin übernommen.
    assertThat(
            booking(a, term, room, "abc").status()
    ).isEqualTo(201);

    // Derselbe Termin darf keinen zweiten Raum bekommen
    assertThat(
            booking(a, term, room, range).status()
    ).isEqualTo(409);

    // Buchung ist für fremde Benutzer nicht sichtbar
    assertThat(
            call("GET", "/api/buchungen", null, b, false)
                    .body()
                    .size()
    ).isZero();

    // Beim Löschen des Termins wird die Buchung ebenfalls entfernt
    assertThat(
            call("DELETE", "/api/me/termine/" + term, null, a, true)
                    .status()
    ).isEqualTo(204);

    // Unvollständige Buchungsanfrage wird abgelehnt
    assertThat(
            call(
                    "POST",
                    "/api/buchungen",
                    Map.of("zeitraum", range),
                    a,
                    true
            ).status()
    ).isEqualTo(400);
}
    @Test void concurrentBookingsAllowExactlyOne() throws Exception {
        long t1=term(a,"09:00","10:00").body().get("terminId").asLong();
        long t2=term(b,"09:00","10:00").body().get("terminId").asLong();
        long room=resource(true).body().get("ressourcenId").asLong();
        var barrier=new CyclicBarrier(2);
        try(var pool=Executors.newFixedThreadPool(2)) {
            var first=pool.submit(() -> {barrier.await(5,TimeUnit.SECONDS);return booking(a,t1,room,"2026-09-24T09:00 bis 2026-09-24T10:00").status();});
            var second=pool.submit(() -> {barrier.await(5,TimeUnit.SECONDS);return booking(b,t2,room,"2026-09-24T09:00 bis 2026-09-24T10:00").status();});
            assertThat(List.of(first.get(15,TimeUnit.SECONDS),second.get(15,TimeUnit.SECONDS))).containsExactlyInAnyOrder(201,409);
        }
        assertThat(bookings.findByRessource_RessourcenId(room)).hasSize(1);
    }
    @Test void oldMalformedBookingIsControlledConflict() throws Exception {
        long id=term(a,"09:00","10:00").body().get("terminId").asLong();long room=resource(true).body().get("ressourcenId").asLong();
        var bad=new Buchung();bad.setTermin(terms.findById(id).orElseThrow());bad.setRessource(resources.findById(room).orElseThrow());bad.setZeitraum("abc");bad.setStatus("BESTAETIGT");bookings.save(bad);
        assertThat(booking(a,id,room,"2026-09-24T09:00 bis 2026-09-24T10:00").status()).isEqualTo(409);
    }
    @Test void editNotifiesParticipantsAndProtectsOwnership() throws Exception {
        var data=new HashMap<String,Object>(Map.of("titel","Projektmeeting","datum","2026-09-24","startzeit","14:00","endzeit","15:00","benutzerIds",List.of(bob.getBenutzerId())));
        var created=call("POST","/api/me/termine",data,a,true);
        assertThat(created.status()).isEqualTo(201);
        long id=created.body().get("terminId").asLong();
        assertThat(call("GET","/api/me/termine",null,b,false).body().toString()).contains("Projektmeeting");
        data.put("datum","2026-09-25");data.put("startzeit","16:00");data.put("endzeit","17:00");
        assertThat(call("PUT","/api/me/termine/"+id,data,b,true).status()).isEqualTo(404);
        assertThat(call("PUT","/api/me/termine/"+id,data,a,false).status()).isEqualTo(403);
        assertThat(call("PUT","/api/me/termine/"+id,data,a,true).status()).isEqualTo(200);
        var list=call("GET","/api/me/benachrichtigungen",null,b,false);
        assertThat(list.body().size()).isEqualTo(1);
        assertThat(list.body().get(0).get("text").asText()).contains("Projektmeeting","2026-09-25","16:00");
        long notification=list.body().get(0).get("id").asLong();
        assertThat(call("POST","/api/me/benachrichtigungen/"+notification+"/gelesen",null,a,true).status()).isEqualTo(404);
        assertThat(call("POST","/api/me/benachrichtigungen/"+notification+"/gelesen",null,b,true).body().get("gelesen").asBoolean()).isTrue();
        assertThat(call("PUT","/api/me/termine/"+id,data,a,true).status()).isEqualTo(200);
        assertThat(call("GET","/api/me/benachrichtigungen",null,b,false).body().size()).isEqualTo(1);
        data.put("endzeit","15:00");
        assertThat(call("PUT","/api/me/termine/"+id,data,a,true).status()).isEqualTo(400);
        assertThat(call("GET","/api/me/benachrichtigungen",null,b,false).body().size()).isEqualTo(1);
    }
    @Test void bookedAppointmentCannotBeShifted() throws Exception {
        var created=term(a,"10:00","11:00");long id=created.body().get("terminId").asLong();
        long room=resource(true).body().get("ressourcenId").asLong();
        assertThat(booking(a,id,room,"2026-09-24T10:00 bis 2026-09-24T11:00").status()).isEqualTo(201);
        assertThat(call("PUT","/api/me/termine/"+id,Map.of("titel","Geändert","datum","2026-09-25","startzeit","10:00","endzeit","11:00"),a,true).status()).isEqualTo(409);
        assertThat(terms.findById(id).orElseThrow().getDatum().toString()).isEqualTo("2026-09-24");
    }
}
