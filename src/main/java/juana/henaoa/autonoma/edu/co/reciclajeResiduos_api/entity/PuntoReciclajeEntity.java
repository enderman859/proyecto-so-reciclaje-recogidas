package juana.henaoa.autonoma.edu.co.reciclajeResiduos_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "puntos_reciclaje")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PuntoReciclajeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(nullable = false, length = 200)
    private String direccion;

    @Column(nullable = false)
    private Double latitud;

    @Column(nullable = false)
    private Double longitud;

    @Column(name = "ubicacion_aproximada", nullable = false, length = 120)
    private String ubicacionAproximada;

    @Column(nullable = false)
    private Boolean activo = true;
}
