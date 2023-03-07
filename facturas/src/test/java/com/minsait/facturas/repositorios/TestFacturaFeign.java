package com.minsait.facturas.repositorios;

import com.minsait.facturas.client.HabitacionesClienteRest;
import com.minsait.facturas.controllers.Datos;
import com.minsait.facturas.models.Habitacion;
import com.minsait.facturas.services.FacturaService;
import com.minsait.facturas.services.HabitacionesServicesFeign;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)


public class TestFacturaFeign {

    @Mock
    HabitacionesClienteRest habitacionesClienteRest;
    @Mock
    FacturaService facturaService;
    @InjectMocks
    HabitacionesServicesFeign habitacionesServicesFeign;


    @Test
    void testBuscarPorId() {
        Habitacion habitacion= Datos.crearHabitacion1().get();
        ResponseEntity<Habitacion> response = ResponseEntity.ok(habitacion);
        when(habitacionesClienteRest.buscarPorId(1L)).thenReturn(response);
        Optional<Habitacion> habitacionEncontrada = habitacionesServicesFeign.buscarPorId(1L);
        assertEquals(habitacion, habitacionEncontrada.get());
    }


    @Test()

    void testBuscarIdSiNoExiste() throws NoSuchElementException{

        try {
            // Llamar al método buscarPorId con el id inválido
            Habitacion habitacion= Datos.crearHabitacion1().get();
            ResponseEntity<Habitacion> response = ResponseEntity.ok(habitacion);
            when(habitacionesClienteRest.buscarPorId(11L)).thenReturn(response);
            Optional<Habitacion> habitacionEncontrada = habitacionesServicesFeign.buscarPorId(1L);
            assertEquals(habitacion, habitacionEncontrada.get());
            fail("Se esperaba una excepción NoSuchElementException");
        } catch (NoSuchElementException e) {

        }

    }
}
