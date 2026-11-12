package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto;

import java.util.List;

import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.PuntoReciclajeEntity;

public record PuntoResponse(
        Long id,
        String nombre,
        String ubicacionAproximada,
        Boolean activo,
        List<String> materiales
) {

    public static PuntoResponse desde(PuntoReciclajeEntity p, List<String> materiales) {
        return new PuntoResponse(p.getId(), p.getNombre(), p.getUbicacionAproximada(), p.getActivo(), materiales);
    }
}
