package com.minsait.habitaciones.models;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "habitaciones")
public class Habitacion implements Serializable {
    static private final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_habitacion")
    private Long idHabitacion;

    @Column(name = "maximo_personas")
    private Integer maxPersonas;



    @Column(name = "precio_noche")
    private BigDecimal precioNoche;

    private Integer estatus;
}
