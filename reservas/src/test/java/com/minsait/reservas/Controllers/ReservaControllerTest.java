package com.minsait.reservas.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsait.reservas.Models.Factura;
import com.minsait.reservas.Models.Habitacion;
import com.minsait.reservas.Models.Reserva;
import com.minsait.reservas.Service.FacturaServiceFeign;
import com.minsait.reservas.Service.HabitacionesServicesFeign;
import com.minsait.reservas.Service.ReservaService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
    @MockBean
    private HabitacionesServicesFeign habitacionesServicesFeign;
    @MockBean
    private FacturaServiceFeign facturaServiceFeign;
    @InjectMocks
    private ReservaController reservaController;


    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void testFindAll() throws Exception {
        when(service.mostrarTodos()).thenReturn(List.of(Datos.crearReserva().get(), Datos.crearReserva2().get()));
        mvc.perform(get("/api/v1/reservas/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idHabitacion").value(1))
                .andExpect(jsonPath("$[1].idHabitacion").value(2));
    }
    @Test
    void testFindById() throws Exception {
        when(service.buscarPorId(anyLong())).thenReturn(Datos.crearReserva());
        mvc.perform(get("/api/v1/reservas/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idReserva").value(1L))
                .andExpect(jsonPath("$.idHabitacion").value(1));
    }
    @Test
    void testFindByIdIfDoesntExist() throws Exception {
        when(service.buscarPorId(anyLong())).thenThrow(NoSuchElementException.class);
        mvc.perform(get("/api/v1/reservas/3").contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound());
    }

    @Test
    void testSave() throws Exception {
        Reserva reserva = new Reserva(null, 1L, new Date(), new Date());
        when(habitacionesServicesFeign.buscarPorId(anyLong())).thenReturn(Datos.crearHabitacion());
        when(service.crearReserva(any(Reserva.class))).then(invocationOnMock -> {
            Reserva reservaTemporal = invocationOnMock.getArgument(0);
            reservaTemporal.setIdReserva(3L);
            return reservaTemporal;
        });
    }

    @Test
    void testSaveIfHabitacionDoesntAvalible() throws Exception {
        when(habitacionesServicesFeign.buscarPorId(anyLong())).thenThrow(Error.class);
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/reservas/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSaveIfHabitacionDoesntAvalibleException() throws Exception {
        when(habitacionesServicesFeign.buscarPorId(anyLong())).thenThrow(NoSuchElementException.class);
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/reservas/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDelete() throws Exception {
        long id = 1;
        when(service.eliminar(anyLong())).thenReturn(true);
        mvc.perform(delete("/api/v1/reservas/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteIfIdDoesntExist() throws Exception {
        long id = 8;
        when(service.eliminar(anyLong())).thenReturn(false);
        mvc.perform(delete("/api/v1/reservas/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdate() throws Exception {
        Reserva reserva = new Reserva(null, 9L, new Date(), new Date());
        when(service.buscarPorId(anyLong())).thenReturn(Datos.crearReserva());
        when(service.crearReserva(any())).then(invocationOnMock -> {
            Reserva reservaActualizada = reserva;
            reservaActualizada.setIdReserva(1L);
            return reservaActualizada;
        });
        mvc.perform(put("/api/v1/reservas/1").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(reserva)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idReserva").value(1))
                .andExpect(jsonPath("$.idHabitacion", Matchers.is(9)));
        verify(service, atMostOnce()).buscarPorId(1L);
        InOrder order = inOrder(service);
        order.verify(service).buscarPorId(1L);
        order.verify(service).crearReserva(any());
    }

    @Test
    void testUpdateIfIdDoesntExist() throws Exception {
        Reserva reserva = new Reserva(null, 9L, new Date(), new Date());
        when(service.buscarPorId(1L)).thenReturn(Datos.crearReserva());
        when(service.crearReserva(any())).thenThrow(NoSuchElementException.class);
        mvc.perform(put("/api/v1/reservas/6").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(reserva)))
                .andExpect(status().isNotFound());

    }

    @Test
    void testFindAllHabitaciones() throws Exception {
        when(habitacionesServicesFeign.buscarTodas()).thenReturn(List.of(Datos.crearHabitacion().get(), Datos.crearHabitacion2().get()));
        mvc.perform(get("/api/v1/reservas/habitaciones/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idHabitacion").value(1))
                .andExpect(jsonPath("$[1].idHabitacion").value(2));
    }

    @Test
    void testHabitacionesDisponibles() throws Exception {
        when(habitacionesServicesFeign.buscarDisponibles()).thenReturn(List.of(Datos.crearHabitacion().get()));
        mvc.perform(get("/api/v1/reservas/habitaciones/disponibles").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idHabitacion").value(1));
    }

    @Test
    void testFindHabitacionById() throws Exception {
        when(habitacionesServicesFeign.buscarPorId(1L)).thenReturn(Datos.crearHabitacion());
        mvc.perform(get("/api/v1/reservas/habitaciones/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idHabitacion").value(1L))
                .andExpect(jsonPath("$.maxPersonas").value(5))
                .andExpect(jsonPath("$.precioNoche").value(1500))
                .andExpect(jsonPath("$.estatus").value(1));
    }

    @Test
    void testFindHabitacionByIdIfDoesntExist() throws Exception {
        when(habitacionesServicesFeign.buscarPorId(anyLong())).thenThrow(NoSuchElementException.class);
        mvc.perform(get("/api/v1/reservas/habitaciones/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindHabitacionesByCapacityMax() throws Exception {
        when(habitacionesServicesFeign.buscarPorMaxPersonas(anyInt()))
                .thenReturn(List.of(Datos.crearHabitacion().get(), Datos.crearHabitacion2().get()));
        mvc.perform(get("/api/v1/reservas/habitaciones/max/5").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idHabitacion").value(1))
                .andExpect(jsonPath("$[1].idHabitacion").value(2));
    }

    @Test
    void testReservarHabitacion() throws Exception {
        when(habitacionesServicesFeign.buscarPorId(anyLong())).thenReturn(Datos.crearHabitacion());
        mvc.perform(put("/api/v1/reservas/habitaciones/reservar/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idHabitacion").value(1));
    }

    @Test
    void testReservarHabitacionIfDoesntExist() throws Exception {
        when(habitacionesServicesFeign.buscarPorId(anyLong())).thenThrow(NoSuchElementException.class);
        mvc.perform(put("/api/v1/reservas/habitaciones/reservar/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFactura() throws Exception {
        Reserva reserva = Datos.crearReserva().get();
        when(service.buscarPorId(reserva.getIdReserva())).thenReturn(Optional.of(reserva));
        Factura factura = new Factura(1L, 1L, new Date(),new BigDecimal(250));
        when(facturaServiceFeign.crearFactura(any(Reserva.class))).thenReturn(factura);
        when(habitacionesServicesFeign.buscarPorId(reserva.getIdHabitacion())).thenReturn(any());

        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(post("/api/v1/reservas/factura/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reserva)))
                .andExpect(status().isOk());
    }

    @Test
    void testFacturaSiNoExisteReser() throws Exception {
        Reserva reserva = Datos.crearReserva().get();

        Factura factura = new Factura(1L, 1L, new Date(),new BigDecimal(250));
        when(facturaServiceFeign.crearFactura(any(Reserva.class))).thenReturn(factura);


        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(post("/api/v1/reservas/factura/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reserva)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testReserva() throws Exception {
        Reserva reserva = Datos.crearReserva().get();
        when(service.buscarPorId(reserva.getIdReserva())).thenReturn(Optional.of(reserva));
        Factura factura = new Factura(1L, 1L, new Date(),new BigDecimal(250));
        when(service.crearReserva(reserva)).then(invocation ->
        {
            Reserva reservaTemporal=invocation.getArgument(0);
            reservaTemporal.setIdReserva(3L);
            return reservaTemporal;
        });
        when(habitacionesServicesFeign.buscarPorId(reserva.getIdHabitacion())).thenReturn(any());

        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(post("/api/v1/reservas/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reserva)))
                .andExpect(status().isOk());



    }
}