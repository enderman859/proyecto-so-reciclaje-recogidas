package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SolicitudRecogidaRequest(
        String ciudadanoNombre,
        String ciudadanoContacto,
        Long puntoId,
        Long campanaId,
        Long materialId,
        BigDecimal cantidadKg,
        LocalDate fechaRecogida
) {
}
