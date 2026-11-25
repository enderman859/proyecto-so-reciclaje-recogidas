package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto.CambioEstadoRequest;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto.SolicitudRecogidaRequest;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.dto.SolicitudRecogidaResponse;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.CampanaEntity;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.EstadoSolicitud;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.MaterialEntity;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.PuntoReciclajeEntity;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.SeguimientoSolicitudEntity;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity.SolicitudRecogidaEntity;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.repository.CampanaMaterialRepository;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.repository.CampanaRepository;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.repository.MaterialRepository;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.repository.PuntoMaterialRepository;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.repository.PuntoReciclajeRepository;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.repository.SeguimientoSolicitudRepository;
import juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.repository.SolicitudRecogidaRepository;

@ExtendWith(MockitoExtension.class)
class SolicitudRecogidaServiceTest {

    @Mock
    private SolicitudRecogidaRepository solicitudRepository;
    @Mock
    private PuntoReciclajeRepository puntoRepository;
    @Mock
    private CampanaRepository campanaRepository;
    @Mock
    private MaterialRepository materialRepository;
    @Mock
    private PuntoMaterialRepository puntoMaterialRepository;
    @Mock
    private CampanaMaterialRepository campanaMaterialRepository;
    @Mock
    private SeguimientoSolicitudRepository seguimientoRepository;

    @InjectMocks
    private SolicitudRecogidaService service;

    // ---------- datos de apoyo

    private PuntoReciclajeEntity punto() {
        PuntoReciclajeEntity punto = mock(PuntoReciclajeEntity.class);
        lenient().when(punto.getId()).thenReturn(1L);
        lenient().when(punto.getNombre()).thenReturn("Punto Verde Centro");
        return punto;
    }

    // La campaña va del 1 de octubre al 30 de noviembre de 2026
    private CampanaEntity campana() {
        CampanaEntity campana = mock(CampanaEntity.class);
        lenient().when(campana.getId()).thenReturn(1L);
        lenient().when(campana.getNombre()).thenReturn("Recicla en octubre");
        lenient().when(campana.getFechaInicio()).thenReturn(LocalDate.of(2026, 10, 1));
        lenient().when(campana.getFechaFin()).thenReturn(LocalDate.of(2026, 11, 30));
        return campana;
    }

    private MaterialEntity material() {
        MaterialEntity material = mock(MaterialEntity.class);
        lenient().when(material.getId()).thenReturn(1L);
        lenient().when(material.getNombre()).thenReturn("PLASTICO");
        return material;
    }

    private SolicitudRecogidaEntity solicitud() {
        return new SolicitudRecogidaEntity("Ana Gomez", "3001234567", punto(), campana(), material(),
                new BigDecimal("12.5"), LocalDate.of(2026, 11, 20));
    }

    private SolicitudRecogidaRequest request(BigDecimal cantidad, LocalDate fecha) {
        return new SolicitudRecogidaRequest("Ana Gomez", "3001234567", 1L, 1L, 1L, cantidad, fecha);
    }

    private void programarPuntoCampanaYMaterial() {
        PuntoReciclajeEntity punto = punto();
        CampanaEntity campana = campana();
        MaterialEntity material = material();
        when(puntoRepository.findById(1L)).thenReturn(Optional.of(punto));
        when(campanaRepository.findById(1L)).thenReturn(Optional.of(campana));
        when(materialRepository.findById(1L)).thenReturn(Optional.of(material));
    }

    // ---------- buscar

    @Test
    void buscar_existente_devuelveLaSolicitud() {
        // Arrange
        SolicitudRecogidaEntity solicitud = solicitud();
        when(solicitudRepository.findById(5L)).thenReturn(Optional.of(solicitud));

        // Act
        Optional<SolicitudRecogidaResponse> respuesta = service.buscar(5L);

        // Assert
        assertThat(respuesta).isPresent();
        assertThat(respuesta.get().ciudadanoNombre()).isEqualTo("Ana Gomez");
        assertThat(respuesta.get().punto()).isEqualTo("Punto Verde Centro");
        assertThat(respuesta.get().estado()).isEqualTo(EstadoSolicitud.SOLICITADA);
    }

    @Test
    void buscar_inexistente_devuelveVacio() {
        // Arrange
        when(solicitudRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<SolicitudRecogidaResponse> respuesta = service.buscar(99L);

        // Assert
        assertThat(respuesta).isEmpty();
    }

    // ---------- listar

    @Test
    void listar_conEstado_devuelveSoloLasDeEseEstado() {
        // Arrange
        SolicitudRecogidaEntity primera = solicitud();
        SolicitudRecogidaEntity segunda = solicitud();
        when(solicitudRepository.findByEstado(EstadoSolicitud.SOLICITADA))
                .thenReturn(List.of(primera, segunda));

        // Act
        List<SolicitudRecogidaResponse> respuesta = service.listar(EstadoSolicitud.SOLICITADA);

        // Assert
        assertThat(respuesta).hasSize(2)
                .extracting(SolicitudRecogidaResponse::estado)
                .containsOnly(EstadoSolicitud.SOLICITADA);
        verify(solicitudRepository, never()).findAll();
    }

    // ---------- crear: regla de negocio

    @Test
    void crear_solicitudValida_quedaSolicitadaYConFechaActual() {
        // Arrange
        programarPuntoCampanaYMaterial();
        when(puntoMaterialRepository.existsByPuntoIdAndMaterialId(1L, 1L)).thenReturn(true);
        when(campanaMaterialRepository.existsByCampanaIdAndMaterialId(1L, 1L)).thenReturn(true);
        when(solicitudRepository.save(any(SolicitudRecogidaEntity.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        // Act
        SolicitudRecogidaResponse respuesta =
                service.crear(request(new BigDecimal("12.5"), LocalDate.of(2026, 11, 20)));

        // Assert
        assertThat(respuesta.estado()).isEqualTo(EstadoSolicitud.SOLICITADA);
        assertThat(respuesta.ciudadanoNombre()).isEqualTo("Ana Gomez");
        assertThat(respuesta.fechaCreacion()).isCloseTo(LocalDateTime.now(), within(5, ChronoUnit.SECONDS));
        verify(solicitudRepository).save(any(SolicitudRecogidaEntity.class));
    }

    @Test
    void crear_cantidadEnCero_lanza400YNoGuarda() {
        // Act
        ResponseStatusException error = assertThrows(ResponseStatusException.class,
                () -> service.crear(request(BigDecimal.ZERO, LocalDate.of(2026, 11, 20))));

        // Assert
        assertThat(error.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        verify(solicitudRepository, never()).save(any());
    }

    @Test
    void crear_fechaFueraDeLaCampana_lanza400YNoGuarda() {
        // Arrange
        programarPuntoCampanaYMaterial();

        // Act
        ResponseStatusException error = assertThrows(ResponseStatusException.class,
                () -> service.crear(request(new BigDecimal("12.5"), LocalDate.of(2027, 1, 15))));

        // Assert
        assertThat(error.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        verify(solicitudRepository, never()).save(any());
    }

    @Test
    void crear_materialQueElPuntoNoAcepta_lanza400YNoGuarda() {
        // Arrange
        programarPuntoCampanaYMaterial();
        when(puntoMaterialRepository.existsByPuntoIdAndMaterialId(1L, 1L)).thenReturn(false);

        // Act
        ResponseStatusException error = assertThrows(ResponseStatusException.class,
                () -> service.crear(request(new BigDecimal("12.5"), LocalDate.of(2026, 11, 20))));

        // Assert
        assertThat(error.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        verify(solicitudRepository, never()).save(any());
    }

    // ---------- cambiarEstado

    @Test
    void cambiarEstado_valido_cambiaElEstadoYRegistraElSeguimiento() {
        // Arrange
        SolicitudRecogidaEntity solicitud = solicitud();
        when(solicitudRepository.findById(5L)).thenReturn(Optional.of(solicitud));

        // Act
        SolicitudRecogidaResponse respuesta = service.cambiarEstado(5L,
                new CambioEstadoRequest(EstadoSolicitud.PROGRAMADA, "Recogida asignada"));

        // Assert
        assertThat(respuesta.estado()).isEqualTo(EstadoSolicitud.PROGRAMADA);
        verify(seguimientoRepository).save(any(SeguimientoSolicitudEntity.class));
    }

    @Test
    void cambiarEstado_inexistente_lanza404YNoGuarda() {
        // Arrange
        when(solicitudRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        ResponseStatusException error = assertThrows(ResponseStatusException.class,
                () -> service.cambiarEstado(99L, new CambioEstadoRequest(EstadoSolicitud.PROGRAMADA, "x")));

        // Assert
        assertThat(error.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(solicitudRepository, never()).save(any());
    }

    // ---------- retirar

    @Test
    void retirar_solicitada_borraLaSolicitudEncontrada() {
        // Arrange
        SolicitudRecogidaEntity solicitud = solicitud();
        when(solicitudRepository.findById(5L)).thenReturn(Optional.of(solicitud));

        // Act
        service.retirar(5L);

        // Assert
        verify(solicitudRepository).delete(solicitud);
    }

    @Test
    void retirar_yaProgramada_lanza400YNoBorraNada() {
        // Arrange
        SolicitudRecogidaEntity solicitud = solicitud();
        solicitud.cambiarEstado(EstadoSolicitud.PROGRAMADA);
        when(solicitudRepository.findById(5L)).thenReturn(Optional.of(solicitud));

        // Act
        ResponseStatusException error = assertThrows(ResponseStatusException.class,
                () -> service.retirar(5L));

        // Assert
        assertThat(error.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        verify(solicitudRepository, never()).delete(any());
    }
}
