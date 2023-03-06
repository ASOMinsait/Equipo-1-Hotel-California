package com.minsait.facturas.helpers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class CalcularDias {


    public static long calcularDias(Date fechaInicio, Date fechaFin) {
        LocalDate inicio = convertirADateLocal(fechaInicio);
        LocalDate fin = convertirADateLocal(fechaFin);
        return ChronoUnit.DAYS.between(inicio, fin);
    }

    private static LocalDate convertirADateLocal(Date fecha) {
        return fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }


}
