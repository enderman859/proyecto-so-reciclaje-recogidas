package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto;

import java.time.LocalDate;
import java.util.List;

public record CampanaResponse(
        Long id,
        String nombre,
        String descripcion,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        List<String> materiales
) {
}
