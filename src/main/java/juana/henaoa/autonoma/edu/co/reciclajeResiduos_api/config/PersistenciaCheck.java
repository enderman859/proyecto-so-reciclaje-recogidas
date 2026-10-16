package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.repository.SolicitudRecogidaRepository;

@Component
public class PersistenciaCheck implements CommandLineRunner {

    private final SolicitudRecogidaRepository repository;

    public PersistenciaCheck(SolicitudRecogidaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        System.out.println("Total: " + repository.count());
    }
}
