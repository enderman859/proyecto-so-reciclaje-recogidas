package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto.PuntoResponse;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.service.PuntoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/puntos")
@RequiredArgsConstructor
public class PuntoController {

    private final PuntoService service;

    @GetMapping
    public List<PuntoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PuntoResponse> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
