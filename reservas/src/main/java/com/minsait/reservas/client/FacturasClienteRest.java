package com.minsait.reservas.client;

import com.minsait.reservas.Models.Factura;
import com.minsait.reservas.Models.Habitacion;
import com.minsait.reservas.Models.Reserva;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "facturas", url="${facturas.url}")
public interface FacturasClienteRest {
    @PostMapping("/")
    public ResponseEntity<Factura> crearFactura(@PathVariable Reserva reserva);
}
