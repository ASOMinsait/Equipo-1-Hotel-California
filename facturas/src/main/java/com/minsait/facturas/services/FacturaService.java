package com.minsait.facturas.services;

import com.minsait.facturas.models.Factura;

import java.util.List;
import java.util.Optional;

public interface FacturaService {



    List<Factura> buscarTodos();

    Optional <Factura> buscarPorId(Long id);

    Optional <Factura> buscarPorIdReservacion(Long id);



    Factura guardar(Factura factura);

    boolean eliminar(Long id);

    Factura procesarPago(Long id, String formaPago);


}
