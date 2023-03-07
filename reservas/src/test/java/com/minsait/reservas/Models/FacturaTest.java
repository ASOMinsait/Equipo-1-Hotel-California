package com.minsait.reservas.Models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
class FacturaTest {
    @Test
    void testFactura(){
        Factura factura=new Factura();
        factura.setIdFactura(1L);
        factura.setIdReservacion(1L);
        factura.setFechaEmision(new Date());
        factura.setTotalReservacion(new BigDecimal(3000));

        factura.getIdFactura();
        factura.getIdReservacion();
        factura.getFechaEmision();
        factura.getTotalReservacion();

        Factura factura1=new Factura(2L,2L,new Date(),new BigDecimal(5000));
    }
}