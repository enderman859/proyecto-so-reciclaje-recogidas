package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.PuntoMaterialEntity;

public interface PuntoMaterialRepository extends JpaRepository<PuntoMaterialEntity, Long> {

    boolean existsByPuntoIdAndMaterialId(Long puntoId, Long materialId);

    List<PuntoMaterialEntity> findByPuntoId(Long puntoId);
}
