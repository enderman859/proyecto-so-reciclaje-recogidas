package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "solicitudes_recogida")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SolicitudRecogidaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ciudadano_nombre", nullable = false, length = 120)
    private String ciudadanoNombre;

    @Column(name = "ciudadano_contacto", nullable = false, length = 120)
    private String ciudadanoContacto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "punto_id", nullable = false)
    private PuntoReciclajeEntity punto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "campana_id", nullable = false)
    private CampanaEntity campana;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "material_id", nullable = false)
    private MaterialEntity material;

    @Column(name = "cantidad_kg", nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidadKg;

    @Column(name = "fecha_recogida", nullable = false)
    private LocalDate fechaRecogida;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoSolicitud estado;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    // Toda solicitud nueva nace en estado SOLICITADA (RN-05): lo decide la entidad
    public SolicitudRecogidaEntity(String ciudadanoNombre, String ciudadanoContacto,
            PuntoReciclajeEntity punto, CampanaEntity campana, MaterialEntity material,
            BigDecimal cantidadKg, LocalDate fechaRecogida) {
        this.ciudadanoNombre = ciudadanoNombre;
        this.ciudadanoContacto = ciudadanoContacto;
        this.punto = punto;
        this.campana = campana;
        this.material = material;
        this.cantidadKg = cantidadKg;
        this.fechaRecogida = fechaRecogida;
        this.estado = EstadoSolicitud.SOLICITADA;
        this.fechaCreacion = LocalDateTime.now();
    }
}
