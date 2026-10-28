package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto.SolicitudRecogidaRequest;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto.SolicitudRecogidaResponse;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.service.SolicitudRecogidaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/solicitudes-recogida")
@RequiredArgsConstructor
public class SolicitudRecogidaController {

    private final SolicitudRecogidaService service;

    @GetMapping
    public List<SolicitudRecogidaResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudRecogidaResponse> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SolicitudRecogidaResponse crear(@RequestBody SolicitudRecogidaRequest request) {
        return service.crear(request);
    }
}
