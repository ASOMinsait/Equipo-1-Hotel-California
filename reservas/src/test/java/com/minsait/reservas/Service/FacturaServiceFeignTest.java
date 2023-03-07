package com.minsait.reservas.Service;

import com.minsait.reservas.Models.Factura;
import com.minsait.reservas.Models.Reserva;
import com.minsait.reservas.client.FacturasClienteRest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class FacturaServiceFeignTest {
    @Mock
    FacturasClienteRest facturasClienteRest;
@InjectMocks
FacturaServiceFeign facturaServiceFeign;
    @Test
    void testCrearFactura() {
        when(facturaServiceFeign.crearFactura(any(Reserva.class))).thenReturn(Datos.crearFactura().get());
        Factura factura = facturaServiceFeign.crearFactura(Datos.crearReserva().get());
        assertTrue(factura.getIdFactura()==1);
    }
    @Test
    void testCrearFacturaException(){
        when(facturaServiceFeign.crearFactura(any(Reserva.class))).thenThrow(NoSuchElementException.class);
        facturaServiceFeign.crearFactura(Datos.crearReserva().get());
    }
}