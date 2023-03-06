package com.minsait.reservas.Service;

import com.minsait.reservas.Models.Factura;
import com.minsait.reservas.Models.Reserva;

public interface FacturaService {

    public Factura crearFactura(Reserva reserva);
}
