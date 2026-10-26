package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.EstadoSolicitud;

public record SolicitudRecogidaResponse(
        Long id,
        String ciudadanoNombre,
        String punto,
        String campana,
        String material,
        BigDecimal cantidadKg,
        LocalDate fechaRecogida,
        EstadoSolicitud estado,
        LocalDateTime fechaCreacion
) {
}
