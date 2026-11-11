package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.CampanaMaterialEntity;

public interface CampanaMaterialRepository extends JpaRepository<CampanaMaterialEntity, Long> {

    boolean existsByCampanaIdAndMaterialId(Long campanaId, Long materialId);

    List<CampanaMaterialEntity> findByCampanaId(Long campanaId);
}
