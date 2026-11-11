package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.EstadoSolicitud;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.SolicitudRecogidaEntity;

public interface SolicitudRecogidaRepository extends JpaRepository<SolicitudRecogidaEntity, Long> {

    List<SolicitudRecogidaEntity> findByEstado(EstadoSolicitud estado);
}
