package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto.SolicitudRecogidaRequest;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto.SolicitudRecogidaResponse;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.CampanaEntity;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.MaterialEntity;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.PuntoReciclajeEntity;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.SolicitudRecogidaEntity;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.repository.CampanaMaterialRepository;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.repository.CampanaRepository;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.repository.MaterialRepository;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.repository.PuntoMaterialRepository;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.repository.PuntoReciclajeRepository;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.repository.SolicitudRecogidaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SolicitudRecogidaService {

    private final SolicitudRecogidaRepository solicitudRepository;
    private final PuntoReciclajeRepository puntoRepository;
    private final CampanaRepository campanaRepository;
    private final MaterialRepository materialRepository;
    private final PuntoMaterialRepository puntoMaterialRepository;
    private final CampanaMaterialRepository campanaMaterialRepository;

    public List<SolicitudRecogidaResponse> listar() {
        return solicitudRepository.findAll().stream()
                .map(SolicitudRecogidaResponse::desde)
                .toList();
    }

    public Optional<SolicitudRecogidaResponse> buscar(Long id) {
        return solicitudRepository.findById(id)
                .map(SolicitudRecogidaResponse::desde);
    }

    public SolicitudRecogidaResponse crear(SolicitudRecogidaRequest request) {
        // RN-02: la cantidad debe ser positiva
        if (request.cantidadKg().signum() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad debe ser positiva");
        }

        PuntoReciclajeEntity punto = puntoRepository.findById(request.puntoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El punto no existe"));
        CampanaEntity campana = campanaRepository.findById(request.campanaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La campaña no existe"));
        MaterialEntity material = materialRepository.findById(request.materialId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El material no existe"));

        // RN-03: la fecha debe estar dentro de la campaña
        if (request.fechaRecogida().isBefore(campana.getFechaInicio())
                || request.fechaRecogida().isAfter(campana.getFechaFin())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha está fuera de la campaña");
        }

        // RN-04: el material debe ser compatible con el punto y con la campaña
        if (!puntoMaterialRepository.existsByPuntoIdAndMaterialId(punto.getId(), material.getId())
                || !campanaMaterialRepository.existsByCampanaIdAndMaterialId(campana.getId(), material.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El material no es compatible");
        }

        SolicitudRecogidaEntity solicitud = new SolicitudRecogidaEntity(
                request.ciudadanoNombre(), request.ciudadanoContacto(),
                punto, campana, material, request.cantidadKg(), request.fechaRecogida());
        return SolicitudRecogidaResponse.desde(solicitudRepository.save(solicitud));
    }
}
