package com.ubb.IS.Modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name="venta")
@Entity
public class Venta {

    @Id
    @Column(name="ID_venta")
    private int id_venta;

    @Column(name = "fecha_venta", nullable = false)
    private LocalDate fecha_venta;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Cotizacion cotizacion;
}
