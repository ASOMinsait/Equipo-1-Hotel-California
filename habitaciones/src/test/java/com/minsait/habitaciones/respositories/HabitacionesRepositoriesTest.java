package com.minsait.habitaciones.respositories;

import com.minsait.habitaciones.HabitacionesApplication;
import com.minsait.habitaciones.config.SwaggerConfig;
import com.minsait.habitaciones.models.Habitacion;
import com.minsait.habitaciones.repositories.HabitacionRepository;
import com.minsait.habitaciones.services.HabitacionService;
import com.minsait.habitaciones.services.HabitacionServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)

public class HabitacionesRepositoriesTest {
    @InjectMocks
    SwaggerConfig swaggerConfig;
    @Mock
    HabitacionRepository habitacionRepository;
    @InjectMocks
    HabitacionServiceImp habitacionService;
    @Mock
    SwaggerConfig config;

    @Test
    void testFindAll() {
        List<Habitacion> habitaciones=new ArrayList<>();
        when(habitacionService.buscarTodas()).thenReturn(List.of(Datos.crearHabitacion1().get(),Datos.crearHabitacion2().get()));
        habitaciones=habitacionService.buscarTodas();
        assertTrue(!habitaciones.isEmpty());
        verify(habitacionRepository,atLeastOnce()).findAll();
    }

    @Test
    void testFindAvaible() {
        List<Habitacion> habitaciones=new ArrayList<>();
        when(habitacionService.buscarDisponibles()).thenReturn(List.of(Datos.crearHabitacion1().get(),Datos.crearHabitacion2().get(),Datos.crearHabitacion3().get()));
        habitaciones=habitacionService.buscarDisponibles();
        assertTrue(!habitaciones.isEmpty());
        verify(habitacionRepository,atLeastOnce()).buscarDisponibles();
    }

    @Test
    void testFindById() {
        when(habitacionService.buscarPorId(1L)).thenReturn(Datos.crearHabitacion1());
        Habitacion habitacion= habitacionService.buscarPorId(1L).get();
        assertThat(habitacion).isNotNull();
    }

    @Test
    void testFindMaxPeople() {
        List<Habitacion> habitaciones=new ArrayList<>();
        when(habitacionService.buscarPorMaxPersonas(3)).thenReturn(List.of(Datos.crearHabitacion1().get(),Datos.crearHabitacion2().get(),Datos.crearHabitacion3().get()));
        habitaciones=habitacionService.buscarPorMaxPersonas(3);
        assertTrue(!habitaciones.isEmpty());
        verify(habitacionRepository,atLeast(1)).buscarPorMaxPersonas(3);
    }

    @Test
    void testReservarHabitacion() {

        when(habitacionService.buscarPorId(1L)).thenReturn(Datos.crearHabitacion1());
        habitacionService.reservarHabitacion(1L);
        Habitacion habitacion= habitacionService.buscarPorId(1L).get();
        assertTrue(habitacion.getEstatus()==0);
    }

    @Test
    void testGuardar() {
        Habitacion habitacion = new Habitacion(null, 3, new BigDecimal(1550), 1);
        when(habitacionService.guardar(habitacion)).then(invocation ->
        {
            Habitacion habitacionTemporal = invocation.getArgument(0);
            habitacionTemporal.setIdHabitacion(4L);
            return habitacionTemporal;
        });
        Habitacion facturaNueva = habitacionService.guardar(habitacion);
        assertTrue(facturaNueva.getIdHabitacion() == 4L);
    }


    @Test
    void testDeleteById() {
        Long borrarId = 1L;
        when(habitacionService.buscarPorId(borrarId)).thenReturn(Optional.of(com.minsait.habitaciones.controllers.Datos.crearHabitacion1().get()));
        boolean estadoEliminado = habitacionService.eliminar(borrarId);
        assertTrue(estadoEliminado);
    }
//    @Test
//    public void main() {
//        HabitacionesApplication.main(new String[]{});
//    }


    @Test
    public void swagger() {
        String info= swaggerConfig.SpringOpenApi().getInfo().getVersion();
        assertTrue(info.equals("0.0.1-SNAPSHOT"));
    }


}
