package com.minsait.habitaciones.respositories;

import com.minsait.habitaciones.models.Habitacion;
import com.minsait.habitaciones.repositories.HabitacionRepository;
import com.minsait.habitaciones.services.HabitacionService;
import com.minsait.habitaciones.services.HabitacionServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)

public class HabitacionesRepositoriesTest {

    @Mock
    HabitacionRepository habitacionRepository;

    @InjectMocks
    HabitacionServiceImp habitacionService;

    @Test
    void testFindAll() {
        List<Habitacion> habitaciones=new ArrayList<>();
        when(habitacionService.buscarTodas()).thenReturn(List.of(Datos.crearHabitacion1().get(),Datos.crearHabitacion2().get()));
        habitaciones=habitacionService.buscarTodas();
        assertTrue(!habitaciones.isEmpty());
        verify(habitacionRepository,atLeastOnce()).findAll();

    }
}
