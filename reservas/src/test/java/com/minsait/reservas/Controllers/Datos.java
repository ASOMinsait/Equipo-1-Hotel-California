package com.minsait.reservas.Controllers;

import com.minsait.reservas.Models.Factura;
import com.minsait.reservas.Models.Habitacion;
import com.minsait.reservas.Models.Reserva;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

public class Datos {
    public static Optional<Reserva> crearReserva(){
        return Optional. of (new Reserva(1L, 1L,new Date(),new Date()));
    }
    public static Optional<Reserva> crearReserva2(){
        return Optional. of (new Reserva(2L, 2L,new Date(),new Date()));
    }
    public static Optional<Habitacion> crearHabitacion(){
        return Optional. of (new Habitacion(1L, 5, new BigDecimal(1500), 1));
    }
    public static Optional<Habitacion> crearHabitacion2(){
        return Optional. of (new Habitacion(2L, 5, new BigDecimal(1500), 0));
    }
    public static Optional<Factura> crearFactura(){
        return Optional. of (new Factura(1L,1L,new Date(),new BigDecimal(5000)));
    }
}
