package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto.CambioEstadoRequest;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto.SolicitudRecogidaRequest;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto.SolicitudRecogidaResponse;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.EstadoSolicitud;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.service.SolicitudRecogidaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SolicitudRecogidaController {

    private final SolicitudRecogidaService service;

    @GetMapping("/solicitudes-recogida")
    public List<SolicitudRecogidaResponse> listar(@RequestParam(required = false) EstadoSolicitud estado) {
        return service.listar(estado);
    }

    @GetMapping("/solicitudes-recogida/{id}")
    public ResponseEntity<SolicitudRecogidaResponse> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/solicitudes-recogida")
    @ResponseStatus(HttpStatus.CREATED)
    public SolicitudRecogidaResponse crear(@RequestBody SolicitudRecogidaRequest request) {
        return service.crear(request);
    }

    @PatchMapping("/solicitudes/{id}/estado")
    public SolicitudRecogidaResponse cambiarEstado(@PathVariable Long id, @RequestBody CambioEstadoRequest request) {
        return service.cambiarEstado(id, request);
    }

    @DeleteMapping("/solicitudes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void retirar(@PathVariable Long id) {
        service.retirar(id);
    }
}
