package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.MaterialEntity;

public interface MaterialRepository extends JpaRepository<MaterialEntity, Long> {
}
