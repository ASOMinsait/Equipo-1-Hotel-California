package com.minsait.reservas.Service;

import com.minsait.reservas.Models.Factura;
import com.minsait.reservas.Models.Habitacion;
import com.minsait.reservas.Models.Reserva;
import com.minsait.reservas.client.FacturasClienteRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class FacturaServiceFeign implements FacturaService {

    @Autowired
    private FacturasClienteRest facturasClienteRest;


    @Override
    public Factura crearFactura(Reserva reserva) {

        ResponseEntity<Factura> response = facturasClienteRest.crearFactura(reserva);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        throw new NoSuchElementException();

    }
}
