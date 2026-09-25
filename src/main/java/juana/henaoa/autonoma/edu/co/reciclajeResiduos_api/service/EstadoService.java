package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.service;

import org.springframework.stereotype.Service;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto.EstadoResponse;

@Service
public class EstadoService {

    public EstadoResponse consultarEstado() {
        return new EstadoResponse(
                "reciclajeResiduos-api",
                "disponible"
        );
    }
}
