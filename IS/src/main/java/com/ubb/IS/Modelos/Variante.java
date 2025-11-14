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
@Table(name="variante")
public class Variante {
    @Id
    @Column(name = "ID_variante")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_variante;

    @Column(name = "detalle")
    private String detalle;

    @Column(name = "valor_adicional", nullable = false)
    private float valor_adicional;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_variante", nullable = false)
    private TipoVariante tipo_variante;

    @OneToMany(mappedBy = "variante")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<DetalleCotizacion> detalles;
}
