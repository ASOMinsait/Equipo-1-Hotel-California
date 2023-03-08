package com.minsait.reservas.Service;

import com.minsait.reservas.Models.Factura;
import com.minsait.reservas.Models.Reserva;
import com.minsait.reservas.Repositories.ReservaRepository;
import com.minsait.reservas.client.FacturasClienteRest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacturaServiceFeignTest {
    @Mock
    FacturasClienteRest facturasClienteRest;

    @InjectMocks
    ReservaServiceImpl reservaService;
    @InjectMocks
    FacturaServiceFeign facturaServiceFeign;

    @Test
    void testCrearFactura() throws ParseException {
        // Crear un objeto simulado de reserva
        String fechaIni = "2023-03-07T16:55:00.938Z";
        String fechaF = "2023-03-09T16:55:00.938Z";

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date fechaInicio = formato.parse(fechaIni);
        Date fechaFin = formato.parse(fechaF);
        Reserva reserva = new Reserva(1L, 1L, fechaInicio, fechaFin);

        when(reservaService.crearReserva(any(Reserva.class))).then(invocation ->
        {
            Reserva reservaTemporal = invocation.getArgument(0);
            reservaTemporal.setIdReserva(3L);
            return reservaTemporal;
        });
        Reserva reserva1 = reservaService.crearReserva(reserva);
        assertTrue(reserva1.getIdReserva() == 3L);


        // Crear un objeto simulado de factura
        Factura factura = new Factura(1L, 1L, new Date(), new BigDecimal(250));

        when(facturasClienteRest.crearFactura(reserva)).thenReturn(ResponseEntity.ok(factura));

        // Llamar al método crearFactura() y verificar si devuelve la factura correcta
        Factura facturaCreada = facturaServiceFeign.crearFactura(reserva);
        assertEquals(factura, facturaCreada);

    }
}