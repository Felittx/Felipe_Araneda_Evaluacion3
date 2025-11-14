package com.ubb.IS.Modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="mueble")
public class Mueble {
    @Id
    @Column(name = "ID_mueble")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_mueble;

    @Column(name = "nombre_mueble")
    private String nombre_mueble;

    @Column(name = "stock")
    private int stock;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private Tipo tipo;

    @Column(name = "precio_base", nullable = false)
    private float precio_base;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "tamaño", nullable = false)
    private Tamanio tamanio;

    @Column(name = "material", nullable = false)
    private String material;

    @OneToMany(mappedBy = "mueble")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<DetalleCotizacion> detalles;

}
