package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto.CampanaRequest;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto.CampanaResponse;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.CampanaEntity;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.CampanaMaterialEntity;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.MaterialEntity;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.repository.CampanaMaterialRepository;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.repository.CampanaRepository;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CampanaService {

    private final CampanaRepository campanaRepository;
    private final MaterialRepository materialRepository;
    private final CampanaMaterialRepository campanaMaterialRepository;

    // Campañas vigentes: hoy está entre la fecha de inicio y la fecha de fin
    public List<CampanaResponse> listarVigentes() {
        LocalDate hoy = LocalDate.now();
        return campanaRepository.findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(hoy, hoy).stream()
                .map(this::aResponse)
                .toList();
    }

    public Optional<CampanaResponse> buscar(Long id) {
        return campanaRepository.findById(id)
                .map(this::aResponse);
    }

    public CampanaResponse crear(CampanaRequest request) {
        List<MaterialEntity> materiales = validar(request);
        CampanaEntity campana = campanaRepository.save(new CampanaEntity(
                request.nombre(), request.descripcion(), request.fechaInicio(), request.fechaFin()));
        guardarMateriales(campana, materiales);
        return aResponse(campana);
    }

    public CampanaResponse reemplazar(Long id, CampanaRequest request) {
        CampanaEntity campana = campanaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La campaña no existe"));

        // Solo se puede reemplazar antes de que la campaña inicie
        if (!LocalDate.now().isBefore(campana.getFechaInicio())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La campaña ya inició");
        }

        List<MaterialEntity> materiales = validar(request);
        campana.reemplazar(request.nombre(), request.descripcion(), request.fechaInicio(), request.fechaFin());
        campanaRepository.save(campana);
        campanaMaterialRepository.deleteAll(campanaMaterialRepository.findByCampanaId(id));
        guardarMateriales(campana, materiales);
        return aResponse(campana);
    }

    private List<MaterialEntity> validar(CampanaRequest request) {
        if (request.fechaFin().isBefore(request.fechaInicio())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de fin es anterior a la de inicio");
        }
        List<MaterialEntity> materiales = materialRepository.findAllById(request.materialIds());
        if (materiales.size() != request.materialIds().size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Algún material no existe");
        }
        return materiales;
    }

    private void guardarMateriales(CampanaEntity campana, List<MaterialEntity> materiales) {
        materiales.forEach(material -> campanaMaterialRepository.save(new CampanaMaterialEntity(campana, material)));
    }

    private CampanaResponse aResponse(CampanaEntity campana) {
        List<String> materiales = campanaMaterialRepository.findByCampanaId(campana.getId()).stream()
                .map(cm -> cm.getMaterial().getNombre())
                .toList();
        return CampanaResponse.desde(campana, materiales);
    }
}
