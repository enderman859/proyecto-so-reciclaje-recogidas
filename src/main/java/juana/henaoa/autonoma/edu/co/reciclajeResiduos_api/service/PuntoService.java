package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto.PuntoResponse;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.PuntoReciclajeEntity;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.repository.PuntoMaterialRepository;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.repository.PuntoReciclajeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PuntoService {

    private final PuntoReciclajeRepository puntoRepository;
    private final PuntoMaterialRepository puntoMaterialRepository;

    public List<PuntoResponse> listar() {
        return puntoRepository.findAll().stream()
                .map(this::aResponse)
                .toList();
    }

    public Optional<PuntoResponse> buscar(Long id) {
        return puntoRepository.findById(id)
                .map(this::aResponse);
    }

    // RN-01: cada punto se muestra con los materiales que acepta
    private PuntoResponse aResponse(PuntoReciclajeEntity punto) {
        List<String> materiales = puntoMaterialRepository.findByPuntoId(punto.getId()).stream()
                .map(pm -> pm.getMaterial().getNombre())
                .toList();
        return PuntoResponse.desde(punto, materiales);
    }
}
