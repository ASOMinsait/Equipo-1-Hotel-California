package com.minsait.facturas.repositorios;


import com.minsait.facturas.Datos;
import com.minsait.facturas.FacturasApplication;

import com.minsait.facturas.configuration.Config;


import com.minsait.facturas.models.Factura;
import com.minsait.facturas.repositories.FacturaRepository;
import com.minsait.facturas.services.FacturaServiceImp;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class testRepo {


    @Mock
    FacturaRepository facturaRepository;

    @InjectMocks
    FacturaServiceImp facturaService;
    @InjectMocks
    Config swaggerConfig;

    @Test
    void testFindAll() {

        List<Factura> facturas = new ArrayList<>();
        when(facturaService.buscarTodos()).thenReturn(List.of(Datos.crearFactura1().get(), Datos.crearFactura2().get()));

        facturas = facturaService.buscarTodos();

        assertTrue(!facturas.isEmpty());
        verify(facturaRepository, atLeastOnce()).findAll();

    }


    @Test
    void testFindById() {
        when(facturaService.buscarPorId(1L)).thenReturn(Optional.of(Datos.crearFactura1().get()));
        Factura factura = facturaService.buscarPorId(1L).get();
        assertThat(factura).isNotNull();
    }

    @Test
    void testGetFactura() {
        when(facturaService.buscarPorId(1L)).thenReturn(Optional.of(Datos.crearFactura1().get()));
        Optional<Factura> factura = facturaRepository.findById(1L);
        assertTrue(factura.get().getIdFacturas() != null);
        assertTrue(factura.get().getIdReservacion() != null);
        assertTrue(factura.get().getFechaEmision() != null);
        assertTrue(factura.get().getTotalReserva() != null);

    }


    @Test
    void testDeleteById() {
        Long borrarId = 1L;
        when(facturaService.buscarPorId(borrarId)).thenReturn(Optional.of(Datos.crearFactura1().get()));
        boolean estadoEliminado = facturaService.eliminar(borrarId);
        assertTrue(estadoEliminado);
    }

    @Test
    void testGuardar() {
        Factura factura = new Factura(null, 3L, Date.from(Instant.now()), new BigDecimal(650));
        when(facturaService.guardar(factura)).then(invocation ->
        {
            Factura facturaTemporal = invocation.getArgument(0);
            facturaTemporal.setIdFacturas(3L);
            return facturaTemporal;
        });
        Factura facturaNueva = facturaService.guardar(factura);
        assertTrue(facturaNueva.getIdFacturas() == 3L);
    }

    @Test
    void testFindByReservacionId() {
        when(facturaService.buscarPorIdReservacion(anyLong())).thenReturn(Datos.crearFactura1());
        Factura factura = facturaService.buscarPorIdReservacion(1L).get();
        assertThat(factura).isNotNull();
    }


//    @Test
//    public void main() {
//        FacturasApplication.main(new String[]{});
//    }

    @Test
    public void swagger() {

        String info = swaggerConfig.SpringOpenApi().getInfo().getVersion();
        assertTrue(info.equals("0.0.1-SNAPSHOT"));
    }
}
