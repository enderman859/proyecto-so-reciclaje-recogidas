package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

class SolicitudRecogidaEntityTest {

    private SolicitudRecogidaEntity nuevaSolicitud() {
        return new SolicitudRecogidaEntity("Ana Gomez", "3001234567",
                mock(PuntoReciclajeEntity.class), mock(CampanaEntity.class), mock(MaterialEntity.class),
                new BigDecimal("12.5"), LocalDate.of(2026, 11, 20));
    }

    @Test
    void constructor_solicitudNueva_quedaSolicitadaYConFechaActual() {
        // Act
        SolicitudRecogidaEntity solicitud = nuevaSolicitud();

        // Assert
        assertThat(solicitud.getEstado()).isEqualTo(EstadoSolicitud.SOLICITADA);
        assertThat(solicitud.getFechaCreacion())
                .isCloseTo(LocalDateTime.now(), within(5, ChronoUnit.SECONDS));
    }

    @Test
    void cambiarEstado_deSolicitadaAProgramada_cambiaElEstado() {
        // Arrange
        SolicitudRecogidaEntity solicitud = nuevaSolicitud();

        // Act
        boolean cambio = solicitud.cambiarEstado(EstadoSolicitud.PROGRAMADA);

        // Assert
        assertThat(cambio).isTrue();
        assertThat(solicitud.getEstado()).isEqualTo(EstadoSolicitud.PROGRAMADA);
    }

    @Test
    void cambiarEstado_deSolicitadaARecolectada_noCambiaElEstado() {
        // Arrange
        SolicitudRecogidaEntity solicitud = nuevaSolicitud();

        // Act
        boolean cambio = solicitud.cambiarEstado(EstadoSolicitud.RECOLECTADA);

        // Assert
        assertThat(cambio).isFalse();
        assertThat(solicitud.getEstado()).isEqualTo(EstadoSolicitud.SOLICITADA);
    }
}
