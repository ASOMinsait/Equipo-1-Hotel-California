package com.minsait.reservas.Models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ReservaTest {
    @Test
    void testReserva(){
        Reserva reserva1=new Reserva(1L,1L,new Date(), new Date());
        Reserva reserva=new Reserva();
        reserva.setIdReserva(1L);
        reserva.setIdHabitacion(1L);
        reserva.setFechaEntrada(new Date());
        reserva.setFechaSalida(new Date());
        reserva.getIdReserva();
        reserva.getIdHabitacion();
        reserva.getFechaEntrada();
        reserva.getFechaSalida();
    }
}