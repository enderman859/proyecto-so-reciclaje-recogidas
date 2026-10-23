package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto;

import java.time.LocalDate;
import java.util.List;

public record CampanaRequest(
        String nombre,
        String descripcion,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        List<Long> materialIds
) {
}
