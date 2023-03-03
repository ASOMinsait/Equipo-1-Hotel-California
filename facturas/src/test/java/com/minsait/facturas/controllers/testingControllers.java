package com.minsait.facturas.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.minsait.facturas.models.Factura;
import com.minsait.facturas.services.FacturaService;
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
        mvc.perform(delete("/api/v1/facturas/{id}",id)).andExpect(status().isNotFound());
    }

    @Test
    void testDeleteSiExiste() throws Exception {
        Long id=1L;
        when(service.buscarPorId(id)).thenReturn(Optional.of(Datos.crearFactura1().get()));

        when(service.eliminar(id)).thenReturn(true);
        mvc.perform(delete("/api/v1/facturas/{id}",id)).
                andExpect(status().isNoContent());
    }

    @Test
    void testGuardar() throws Exception {

        Factura factura=new Factura(null,3L, Date.from(Instant.now()),new BigDecimal(650),"TARJETA","PAGADO");
        when(service.guardar(factura)).then(invocation ->
        {
           Factura facturaTemporal=invocation.getArgument(0);
           facturaTemporal.setIdFacturas(3L);
           return facturaTemporal;
        });

        mvc.perform(post("/api/v1/facturas/").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(factura)))
                .andExpectAll(
                        jsonPath("$.idFacturas", Matchers.is(3)),
                        jsonPath("$.idReservacion", Matchers.is(3)),
                        jsonPath("$.totalReserva", Matchers.is(650)),
                        status().isCreated()
                );
    }


    @Test
    void testActualizar() throws Exception {
        Factura factura=new Factura(null,3L, Date.from(Instant.now()),new BigDecimal(650),"TARJETA","PAGADO");
        when(service.buscarPorId(3L)).thenReturn(Optional.of(Datos.crearFactura1().get()));
        when(service.guardar(any())).then(invocation -> {
            Factura facturaActualizada=factura;
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


}
