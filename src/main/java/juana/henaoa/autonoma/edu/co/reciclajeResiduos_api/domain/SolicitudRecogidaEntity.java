package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.domain;

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

@Entity
@Table(name = "solicitudes_recogida")
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
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public String getCiudadanoNombre() {
        return ciudadanoNombre;
    }

    public void setCiudadanoNombre(String ciudadanoNombre) {
        this.ciudadanoNombre = ciudadanoNombre;
    }

    public String getCiudadanoContacto() {
        return ciudadanoContacto;
    }

    public void setCiudadanoContacto(String ciudadanoContacto) {
        this.ciudadanoContacto = ciudadanoContacto;
    }

    public PuntoReciclajeEntity getPunto() {
        return punto;
    }

    public void setPunto(PuntoReciclajeEntity punto) {
        this.punto = punto;
    }

    public CampanaEntity getCampana() {
        return campana;
    }

    public void setCampana(CampanaEntity campana) {
        this.campana = campana;
    }

    public MaterialEntity getMaterial() {
        return material;
    }

    public void setMaterial(MaterialEntity material) {
        this.material = material;
    }

    public BigDecimal getCantidadKg() {
        return cantidadKg;
    }

    public void setCantidadKg(BigDecimal cantidadKg) {
        this.cantidadKg = cantidadKg;
    }

    public LocalDate getFechaRecogida() {
        return fechaRecogida;
    }

    public void setFechaRecogida(LocalDate fechaRecogida) {
        this.fechaRecogida = fechaRecogida;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
}
