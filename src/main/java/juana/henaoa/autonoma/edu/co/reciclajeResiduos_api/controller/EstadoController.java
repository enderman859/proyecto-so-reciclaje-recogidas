package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto.EstadoResponse;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.service.EstadoService;

@RestController
@RequestMapping("/api/estado")
public class EstadoController {

    private final EstadoService estadoService;

    public EstadoController(EstadoService estadoService) {
        this.estadoService = estadoService;
    }

    @GetMapping
    public EstadoResponse consultarEstado() {
        return estadoService.consultarEstado();
    }
}
