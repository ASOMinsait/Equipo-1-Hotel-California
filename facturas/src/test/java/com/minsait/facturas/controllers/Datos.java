package com.minsait.facturas.controllers;

import com.minsait.facturas.models.Factura;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

public class Datos {

    public static Optional<Factura> crearFactura1()  {



        return Optional.of(new Factura(1L,1L,Date.from(Instant.now()),new BigDecimal(200),"TARJETA","PAGADO"));
    }

    public static Optional<Factura> crearFactura2()  {

        return Optional.of(new Factura(1L,1L, Date.from(Instant.now()) ,new BigDecimal(2010),"EFECTIVO","RESERVADO"));
    }
}
