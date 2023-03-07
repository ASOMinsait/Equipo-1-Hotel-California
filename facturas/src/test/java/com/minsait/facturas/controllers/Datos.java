package com.minsait.facturas.controllers;

import com.minsait.facturas.models.Factura;
import com.minsait.facturas.models.Habitacion;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

public class Datos {

    public static Optional<Habitacion> crearHabitacion1() {


        return Optional.of(new Habitacion(1L, 3, new BigDecimal(1550), 1));
    }

    public static Optional<Factura> crearFactura1()  {



        return Optional.of(new Factura(1L,1L,Date.from(Instant.now()),new BigDecimal(200)));
    }

    public static Optional<Factura> crearFactura2()  {

        return Optional.of(new Factura(1L,1L, Date.from(Instant.now()) ,new BigDecimal(2010)));
    }
}
