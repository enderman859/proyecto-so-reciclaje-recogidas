package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto.EstadoResponse;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.service.EstadoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/estado")
@RequiredArgsConstructor
public class EstadoController {

    private final EstadoService estadoService;

    @GetMapping
    public EstadoResponse consultarEstado() {
        return estadoService.consultarEstado();
    }
}
