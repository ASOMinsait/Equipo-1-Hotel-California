package com.minsait.habitaciones.respositories;

import com.minsait.habitaciones.models.Habitacion;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

public class Datos {

    public static Optional<Habitacion> crearHabitacion1() {


        return Optional.of(new Habitacion(1L, 3, new BigDecimal(1550), 1));
    }

    public static Optional<Habitacion> crearHabitacion2() {

        return Optional.of(new Habitacion(2L, 3, new BigDecimal(1650), 1));
    }

    public static Optional<Habitacion> crearHabitacion3() {

        return Optional.of(new Habitacion(3L, 4, new BigDecimal(1850), 0));
    }
}
