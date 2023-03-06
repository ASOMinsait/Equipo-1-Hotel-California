package com.minsait.facturas.controllers;

import com.minsait.facturas.models.Factura;
import com.minsait.facturas.models.Habitacion;
import com.minsait.facturas.models.Reservacion;
import com.minsait.facturas.services.FacturaService;
import com.minsait.facturas.services.HabitacionesServicesFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/facturas")
public class FacturaController {

    @Autowired
    FacturaService facturaService;

    @Autowired
    HabitacionesServicesFeign habitacionesServicesFeign;

    @GetMapping("/")
    public ResponseEntity<List<Factura>> listar() {
        return ResponseEntity.ok(facturaService.buscarTodos());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Factura> buscarPorId(@PathVariable Long id) {
        try {
            Factura factura = facturaService.buscarPorId(id).get();
            return ResponseEntity.ok(factura);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/")
    public ResponseEntity<Factura> guardar(@RequestBody Reservacion reservacion) {

            Habitacion habitacion = habitacionesServicesFeign.buscarPorId(reservacion.getIdHabitacion()).get();
            Factura factura = new Factura(null, reservacion.getIdReserva(), null, null);
            factura.calcularTotal(reservacion, habitacion);
            return new ResponseEntity<>(facturaService.guardar(factura), HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Factura> actualizar(@PathVariable Long id, @RequestBody Factura factura) {
        try {
            Factura facturaActualizada = facturaService.buscarPorId(id).orElseThrow();

            facturaActualizada.setTotalReserva(factura.getTotalReserva());
            return new ResponseEntity<>(facturaService.guardar(facturaActualizada), HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (facturaService.eliminar(id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
