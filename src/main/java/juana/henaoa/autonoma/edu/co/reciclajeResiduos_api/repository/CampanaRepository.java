package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.CampanaEntity;

public interface CampanaRepository extends JpaRepository<CampanaEntity, Long> {

    List<CampanaEntity> findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(LocalDate inicio, LocalDate fin);
}
