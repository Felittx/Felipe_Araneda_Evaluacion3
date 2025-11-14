package com.ubb.IS.Modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cotizacion")
public class Cotizacion {
    @Id
    @Column(name = "ID_cotizacion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cotizacion;

    @Column(name = "fecha_cotizacion")
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(name="estado")
    private EstadoCotizacion estado;

    @Column(name = "total_cotizacion")
    private double total;

    @OneToMany(mappedBy = "cotizacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DetalleCotizacion> detalles = new HashSet<>();

    @OneToOne(mappedBy = "cotizacion", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Venta venta;
}
