package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto;

import java.time.LocalDate;
import java.util.List;

import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.CampanaEntity;

public record CampanaResponse(
        Long id,
        String nombre,
        String descripcion,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        List<String> materiales
) {

    public static CampanaResponse desde(CampanaEntity c, List<String> materiales) {
        return new CampanaResponse(c.getId(), c.getNombre(), c.getDescripcion(),
                c.getFechaInicio(), c.getFechaFin(), materiales);
    }
}
