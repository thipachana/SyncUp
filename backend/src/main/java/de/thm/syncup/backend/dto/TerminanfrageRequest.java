package de.thm.syncup.backend.dto;

import java.util.List;

public record TerminanfrageRequest(
    String titel,
    String zeitraum,
    Integer dauer,
    String status,
    List<Long> benutzerIds
) {
}
