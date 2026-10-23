package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto;

import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.EstadoSolicitud;

public record CambioEstadoRequest(
        EstadoSolicitud estado,
        String observacion
) {
}
