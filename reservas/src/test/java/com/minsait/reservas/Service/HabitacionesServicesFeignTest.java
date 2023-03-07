package com.minsait.reservas.Service;

import com.minsait.reservas.Models.Habitacion;
import com.minsait.reservas.Repositories.ReservaRepository;
import com.minsait.reservas.client.FacturasClienteRest;
import com.minsait.reservas.client.HabitacionesClienteRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HabitacionesServicesFeignTest {
    private List<Habitacion> habitaciones = new ArrayList<>();
    private Habitacion habitacion1 = new Habitacion();
    private Habitacion habitacion2 = new Habitacion();
    @Mock
    HabitacionesClienteRest habitacionesClienteRest;
    @InjectMocks
    HabitacionesServicesFeign habitacionesServicesFeign;
    @Mock
    FacturasClienteRest facturasClienteRest;
    @Mock
    ReservaRepository reservaRepository;
    @InjectMocks
    ReservaServiceImpl reservaService;
    @InjectMocks
    FacturaServiceFeign facturaServiceFeign;
    @BeforeEach
    void setUp(){


        habitacion1.setIdHabitacion(1L);
        habitacion1.setMaxPersonas(2);
        habitacion1.setEstatus(1);
        habitacion1.setPrecioNoche(new BigDecimal(200));
        habitaciones.add(habitacion1);

        habitacion2.setIdHabitacion(2L);
        habitacion2.setMaxPersonas(2);
        habitacion2.setEstatus(1);
        habitacion2.setPrecioNoche(new BigDecimal(2200));
        habitaciones.add(habitacion2);

    }

    @Test
    void testBuscarDisponibles() throws Exception {

        // Configurar el objeto simulado de HabitacionesClienteRest para devolver la lista simulada de habitaciones
        when(habitacionesClienteRest.listarDisponibles()).thenReturn(ResponseEntity.ok(habitaciones));

        // Llamar al método buscarDisponibles() y verificar si devuelve la lista correcta de habitaciones disponibles
        List<Habitacion> habitacionesDisponibles = habitacionesServicesFeign.buscarDisponibles();
        assertEquals(habitaciones, habitacionesDisponibles);
    }



    @Test
    void testBuscarTodas() {


        // Configurar el objeto simulado de HabitacionesClienteRest para devolver la lista simulada de habitaciones
        when(habitacionesClienteRest.listarTodas()).thenReturn(ResponseEntity.ok(habitaciones));
        // Llamar al método buscarDisponibles() y verificar si devuelve la lista correcta de habitaciones disponibles
        List<Habitacion> habitacionesDisponibles = habitacionesServicesFeign.buscarTodas();
        assertEquals(habitaciones, habitacionesDisponibles);
    }

    @Test
    void TestBuscarPorId() {
        ResponseEntity<Habitacion> response = ResponseEntity.ok(habitacion1);
        when(habitacionesClienteRest.buscarPorId(1L)).thenReturn(response);
        Optional<Habitacion> habitacionEncontrada = habitacionesServicesFeign.buscarPorId(1L);
        assertEquals(habitacion1, habitacionEncontrada.get());
    }

    @Test
    void testBuscarPorMaxPersonas() {
        ResponseEntity<List<Habitacion>> response = ResponseEntity.ok(habitaciones);
        when(habitacionesClienteRest.buscarPorMaximoPersonas(2)).thenReturn(response);
        List<Habitacion> habitacionesEncontradas = habitacionesServicesFeign.buscarPorMaxPersonas(2);
        assertEquals(2, habitacionesEncontradas.size());

    }

    @Test
    void testReservarHabitacion() {

        habitacionesServicesFeign.reservarHabitacion(1L);
        verify(habitacionesClienteRest, times(1)).reservarHabitacion(1L);

    }
}