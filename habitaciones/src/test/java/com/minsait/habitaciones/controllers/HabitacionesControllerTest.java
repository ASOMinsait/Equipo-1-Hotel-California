package com.minsait.habitaciones.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsait.habitaciones.models.Habitacion;
import com.minsait.habitaciones.services.HabitacionService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;

@WebMvcTest(HabitacionesController.class)

public class HabitacionesControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private HabitacionService habitacionService;

    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void testFindAll() throws Exception {
        when(habitacionService.buscarTodas()).thenReturn(List.of(Datos.crearHabitacion1().get(), Datos.crearHabitacion2().get()));

        mvc.perform(get("/api/v1/habitaciones/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idHabitacion").value(1L))
                .andExpect(jsonPath("$[1].maxPersonas").value(3L));
    }

    @Test
    void testFindAllAvaibles() throws Exception {
        when(habitacionService.buscarDisponibles()).thenReturn(List.of(Datos.crearHabitacion1().get(), Datos.crearHabitacion2().get()));

        mvc.perform(get("/api/v1/habitaciones/disponibles").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idHabitacion").value(1L))
                .andExpect(jsonPath("$[0].maxPersonas").value(3))
                .andExpect(jsonPath("$[0].precioNoche").value(1550))
                .andExpect(jsonPath("$[0].estatus").value(1));
    }

    @Test
    void testFindById() throws Exception {
        Long id = 1L;
        when(habitacionService.buscarPorId(id)).thenReturn(Datos.crearHabitacion1());
        mvc.perform(get("/api/v1/habitaciones/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idHabitacion", Matchers.is(1)))
                .andExpect(jsonPath("$.maxPersonas").value(3));
    }


    @Test
    void testFindAllMaxPeople() throws Exception {
        when(habitacionService.buscarPorMaxPersonas(3)).thenReturn(List.of(Datos.crearHabitacion1().get(), Datos.crearHabitacion2().get()));
        mvc.perform(get("/api/v1/habitaciones/max/{max}", 3).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idHabitacion").value(1L))
                .andExpect(jsonPath("$[0].maxPersonas").value(3))
                .andExpect(jsonPath("$[0].precioNoche").value(1550))
                .andExpect(jsonPath("$[0].estatus").value(1));
    }

    @Test
    void testReservar() throws Exception {
        given(habitacionService.buscarPorId(1L)).willReturn(Optional.of(Datos.crearHabitacion1().get()));
        mvc.perform(put("/api/v1/habitaciones/reservar/{id}", 1L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idHabitacion").value(1L))
                .andExpect(jsonPath("$.maxPersonas").value(3))
                .andExpect(jsonPath("$.precioNoche").value(1550))
                .andExpect(status().isOk())
        ;
    }

    @Test
    void testGuardar() throws Exception {

        Habitacion habitacion = new Habitacion(null, 3, new BigDecimal(1550), 1);
        when(habitacionService.guardar(habitacion)).then(invocation ->
        {
            Habitacion HabitacionTemporal = invocation.getArgument(0);
            HabitacionTemporal.setIdHabitacion(4L);
            return HabitacionTemporal;
        });

        mvc.perform(post("/api/v1/habitaciones/").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(habitacion)))
                .andExpectAll(
                        jsonPath("$.idHabitacion", Matchers.is(4)),
                        jsonPath("$.maxPersonas", Matchers.is(3)),
                        jsonPath("$.precioNoche", Matchers.is(1550)),
                        status().isCreated()
                );
    }

    @Test
    void testDeleteSiNoExiste() throws Exception {
        Long id = 11L;
        when(habitacionService.eliminar(id)).thenReturn(false);
        mvc.perform(delete("/api/v1/habitaciones/{id}",id)).andExpect(status().isNotFound());
    }
    @Test
    void testDeleteSiExiste() throws Exception {
        Long id=1L;
        when(habitacionService.buscarPorId(id)).thenReturn(Optional.of(Datos.crearHabitacion1().get()));

        when(habitacionService.eliminar(id)).thenReturn(true);
        mvc.perform(delete("/api/v1/habitaciones/{id}",id)).
                andExpect(status().isNoContent());
    }

}
