package com.minsait.facturas.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.minsait.facturas.models.Factura;
import com.minsait.facturas.models.Habitacion;
import com.minsait.facturas.models.Reservacion;
import com.minsait.facturas.services.FacturaService;
import com.minsait.facturas.services.HabitacionesServicesFeign;
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
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;

import org.hamcrest.Matchers;


import static org.mockito.Mockito.when;

@WebMvcTest(FacturaController.class)
public class testingControllers {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private FacturaService service;

    @MockBean
    HabitacionesServicesFeign habitacionesServicesFeign;

    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void testFindAll() throws Exception {
        //given
        when(service.buscarTodos()).thenReturn(List.of(Datos.crearFactura1().get(), Datos.crearFactura2().get()));

        mvc.perform(get("/api/v1/facturas/").contentType(MediaType.APPLICATION_JSON))//then
                .andExpect(jsonPath("$[0].idFacturas").value(1L))
                .andExpect(jsonPath("$[1].idReservacion").value(1L));

    }

    @Test
    void testFindById() throws Exception {
        when(service.buscarPorId(1L)).thenReturn(Optional.of(Datos.crearFactura1().get()));

        mvc.perform(get("/api/v1/facturas/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idFacturas", Matchers.is(1)))
                .andExpect(jsonPath("$.idReservacion").value(1L));
    }

    @Test
    void testDeleteSiNoExiste() throws Exception {
        Long id = 11L;
        when(service.eliminar(id)).thenReturn(false);
        mvc.perform(delete("/api/v1/facturas/{id}", id)).andExpect(status().isNotFound());
    }

    @Test
    void testDeleteSiExiste() throws Exception {
        Long id = 1L;
        when(service.buscarPorId(id)).thenReturn(Optional.of(Datos.crearFactura1().get()));

        when(service.eliminar(id)).thenReturn(true);
        mvc.perform(delete("/api/v1/facturas/{id}", id)).
                andExpect(status().isNoContent());
    }

    @Test
    void testGuardar() throws Exception {

        Factura factura = new Factura(null, 3L, Date.from(Instant.now()), new BigDecimal(650));
        Long id = 1L;
        Reservacion reservacion1 = new Reservacion(1L, 1L, new Date(), new Date());
        when(habitacionesServicesFeign.buscarPorId(id)).thenReturn(Datos.crearHabitacion1());
        when(service.guardar(factura)).then(invocation ->
        {
            Factura facturaTemporal = invocation.getArgument(0);
            facturaTemporal.setIdFacturas(3L);
            return facturaTemporal;
        });

        mvc.perform(post("/api/v1/facturas/").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(reservacion1)))
                .andExpectAll(

                        status().isCreated()
                );
    }


    @Test
    void testActualizar() throws Exception {
        Factura factura = new Factura(null, 3L, Date.from(Instant.now()), new BigDecimal(650));
        when(service.buscarPorId(3L)).thenReturn(Optional.of(Datos.crearFactura1().get()));
        when(service.guardar(any())).then(invocation -> {
            Factura facturaActualizada = factura;
            facturaActualizada.setIdFacturas(3L);
            return facturaActualizada;
        });

        mvc.perform(put("/api/v1/facturas/3").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(factura)))
                .andExpectAll(
                        jsonPath("$.idFacturas", Matchers.is(3)),
                        jsonPath("$.idReservacion", Matchers.is(3)),
                        jsonPath("$.totalReserva", Matchers.is(650)),
                        status().isCreated()
                );
    }

    @Test
    void testModelos() {
        given(habitacionesServicesFeign.buscarPorId(1L)).willReturn(Optional.of(Datos.crearHabitacion1().get()));
        Optional<Habitacion> habitacion = habitacionesServicesFeign.buscarPorId(1L);
        habitacion.get().setIdHabitacion(1l);
        habitacion.get().setEstatus(1);
        habitacion.get().setMaxPersonas(1);
        habitacion.get().setPrecioNoche(new BigDecimal(123));
        assertTrue(habitacion.get().getIdHabitacion() != null);
        assertTrue(habitacion.get().getPrecioNoche() != null);
        assertTrue(habitacion.get().getEstatus() != null);
        assertTrue(habitacion.get().getMaxPersonas() != null);
    }

    @Test
    void testBuscarSiNoExiste() throws Exception{

        mvc.perform(get("/api/v1/facturas/11").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
