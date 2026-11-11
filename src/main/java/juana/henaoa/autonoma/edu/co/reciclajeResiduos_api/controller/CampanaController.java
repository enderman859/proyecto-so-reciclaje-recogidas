package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto.CampanaRequest;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto.CampanaResponse;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.service.CampanaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/campanas")
@RequiredArgsConstructor
public class CampanaController {

    private final CampanaService service;

    @GetMapping
    public List<CampanaResponse> listarVigentes() {
        return service.listarVigentes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampanaResponse> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CampanaResponse crear(@RequestBody CampanaRequest request) {
        return service.crear(request);
    }

    @PutMapping("/{id}")
    public CampanaResponse reemplazar(@PathVariable Long id, @RequestBody CampanaRequest request) {
        return service.reemplazar(id, request);
    }
}
