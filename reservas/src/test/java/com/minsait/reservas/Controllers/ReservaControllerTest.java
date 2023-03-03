package com.minsait.reservas.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsait.reservas.Models.Reserva;
import com.minsait.reservas.Service.ReservaService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.inOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservaController.class)
class ReservaControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ReservaService service;
    ObjectMapper mapper;
    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }
    @Test
    void testFindAll() throws Exception {
        when(service.mostrarTodos()).thenReturn(List.of(Datos.crearReserva().get(), Datos.crearReserva2().get()));
        mvc.perform(get( "/api/v1/reservas/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(  "$[0].idHabitacion").value(  10))
                .andExpect(jsonPath(  "$[1].idHabitacion").value( 11));
    }
    @Test
    void testFindById() throws Exception{
        when(service.buscarPorId( anyLong())).thenReturn(Datos.crearReserva());
        mvc.perform(get("/api/v1/reservas/1").contentType (MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType (MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idReserva").value(1L))
                .andExpect(jsonPath(  "$.idHabitacion").value(  10));
    }
    @Test
    void testFindByIdIfDoesntExist() throws Exception {
        when(service.buscarPorId(anyLong())).thenThrow(NoSuchElementException.class);
        mvc.perform(get("/api/v1/reservas/3").contentType(MediaType.APPLICATION_JSON)) .
                andExpect(status().isNotFound());
    }
    @Test
    void testSave() throws Exception {
        Reserva reserva=new Reserva (null, 12L, 3 ,new Date(), new Date());
        when(service.crearReserva(any (Reserva.class))).then(invocationOnMock -> {
            Reserva reservaTemporal=invocationOnMock.getArgument ( 0);
            reservaTemporal.setIdReserva(3L);
            return reservaTemporal;
        });

        mvc.perform(MockMvcRequestBuilders.post( "/api/v1/reservas/")
                        .contentType (MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(reserva)))
                .andExpectAll(
                        jsonPath("$.idReserva", Matchers.is( 3)),
                        jsonPath("$.idHabitacion" , Matchers.is(12)),
                        jsonPath("$.diasDeReserva", Matchers.is(3)),
                        status().isCreated()
                );
    }
    @Test
    void testDelete() throws Exception{
        long id=1;
        when(service.eliminar(anyLong())).thenReturn(true);
        mvc.perform(delete("/api/v1/reservas/{id}",id))
                .andExpect(status().isNoContent());
    }
    @Test
    void testDeleteIfIdDoesntExist() throws Exception{
        long id=8;
        when(service.eliminar(anyLong())).thenReturn(false);
        mvc.perform(delete("/api/v1/reservas/{id}",id))
                .andExpect(status().isNotFound());
    }
    @Test
    void testUpdate() throws Exception{
        Reserva reserva=new Reserva (null, 9L, 10 ,new Date(), new Date());
        when (service.buscarPorId(anyLong())).thenReturn(Datos.crearReserva());
        when(service.crearReserva(any())).then(invocationOnMock -> {
            Reserva reservaActualizada=reserva;
            reservaActualizada.setIdReserva(1L);
            return reservaActualizada;
        });
        mvc.perform(put( "/api/v1/reservas/1").contentType (MediaType.APPLICATION_JSON)
                        .content (mapper.writeValueAsString(reserva)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(  "$.idReserva").value( 1))
                .andExpect(jsonPath( "$.idHabitacion", Matchers.is( 9)))
                .andExpect(jsonPath(  "$.diasDeReserva", Matchers.is( 10)));
        verify(service, atMostOnce()).buscarPorId(1L);
        InOrder order =inOrder (service);
        order.verify(service).buscarPorId(1L);
        order.verify(service).crearReserva(any());
    }
    @Test
    void testUpdateIfIdDoesntExist() throws Exception{
        Reserva reserva=new Reserva (null, 9L, 1 ,new Date(), new Date());
        when (service.buscarPorId(1L)).thenReturn(Datos.crearReserva());
        when(service.crearReserva(any())).thenThrow(NoSuchElementException.class);
        mvc.perform(put( "/api/v1/reservas/6").contentType (MediaType.APPLICATION_JSON)
                        .content (mapper.writeValueAsString(reserva)))
                .andExpect(status().isNotFound());

    }

}