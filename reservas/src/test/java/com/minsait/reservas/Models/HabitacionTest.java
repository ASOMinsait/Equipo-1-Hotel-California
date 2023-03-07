package com.minsait.reservas.Models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class HabitacionTest {
    @Test
    void testHabitacion(){
        Habitacion habitacion=new Habitacion();
        habitacion.setIdHabitacion(2L);
        habitacion.setMaxPersonas(5);
        habitacion.setPrecioNoche(new BigDecimal(1500));
        habitacion.setEstatus(0);
        habitacion.getIdHabitacion();
        habitacion.getMaxPersonas();
        habitacion.getPrecioNoche();
        habitacion.getEstatus();
        Habitacion habitacion1=new Habitacion(1L,5,new BigDecimal(1500),1);
    }
}