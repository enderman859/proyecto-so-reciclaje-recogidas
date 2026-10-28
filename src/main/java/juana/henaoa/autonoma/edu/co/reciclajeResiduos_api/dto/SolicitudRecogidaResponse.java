package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.EstadoSolicitud;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.SolicitudRecogidaEntity;

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

    public static SolicitudRecogidaResponse desde(SolicitudRecogidaEntity s) {
        return new SolicitudRecogidaResponse(
                s.getId(),
                s.getCiudadanoNombre(),
                s.getPunto().getNombre(),
                s.getCampana().getNombre(),
                s.getMaterial().getNombre(),
                s.getCantidadKg(),
                s.getFechaRecogida(),
                s.getEstado(),
                s.getFechaCreacion());
    }
}
