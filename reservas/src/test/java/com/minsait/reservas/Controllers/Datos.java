package com.minsait.reservas.Controllers;

import com.minsait.reservas.Models.Reserva;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

public class Datos {
    public static Optional<Reserva> crearReserva(){
        return Optional. of (new Reserva(1L, 10L,1,new Date(),new Date()));
    }
    public static Optional<Reserva> crearReserva2(){
        return Optional. of (new Reserva(2L, 11L,2,new Date(),new Date()));
    }
}