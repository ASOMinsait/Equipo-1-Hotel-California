package com.minsait.facturas.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Habitacion {

    private Long idHabitacion;

    private Integer maxPersonas;

    private BigDecimal precioNoche;

    private Integer estatus;



}
