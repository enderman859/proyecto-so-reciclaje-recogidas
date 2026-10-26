package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto;

import java.util.List;

public record PuntoResponse(
        Long id,
        String nombre,
        String ubicacionAproximada,
        Boolean activo,
        List<String> materiales
) {
}
