package com.minsait.reservas.Service;

import com.minsait.reservas.Models.Habitacion;
import com.minsait.reservas.client.HabitacionesClienteRest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class HabitacionesServicesFeignTest {
    @Mock
    HabitacionesClienteRest habitacionesClienteRest;
    @InjectMocks
    HabitacionesServicesFeign habitacionesServicesFeign;
    @Test
    void testBuscarDisponibles() throws Exception{
        when(habitacionesServicesFeign.buscarDisponibles()).thenReturn(List.of(Datos.crearHabitacion().get()));
        List<Habitacion> habitacions=habitacionesServicesFeign.buscarDisponibles();
        assertTrue(!habitacions.isEmpty());

    }

    @Test
    void testBuscarTodas() {
        when(habitacionesServicesFeign.buscarTodas()).thenReturn(List.of(Datos.crearHabitacion().get(),Datos.crearHabitacion2().get()));
        List<Habitacion> habitacions=habitacionesServicesFeign.buscarTodas();
        assertTrue(!habitacions.isEmpty());
    }

    @Test
    void TestBuscarPorId() {
        when(habitacionesServicesFeign.buscarPorId(anyLong())).thenReturn(Datos.crearHabitacion());
        Habitacion habitacion=habitacionesServicesFeign.buscarPorId(1L).get();
        assertTrue(habitacion.getIdHabitacion()==1L);
    }

    @Test
    void testBuscarPorMaxPersonas() {
        when(habitacionesServicesFeign.buscarPorMaxPersonas(anyInt())).thenReturn(List.of(Datos.crearHabitacion().get(),Datos.crearHabitacion2().get()));
        List<Habitacion> habitacions=habitacionesServicesFeign.buscarPorMaxPersonas(5);
        assertTrue(!habitacions.isEmpty());
    }

    @Test
    void testReservarHabitacion() {
        habitacionesServicesFeign.reservarHabitacion(anyLong());
    }
}