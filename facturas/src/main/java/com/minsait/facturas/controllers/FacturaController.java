package com.minsait.facturas.controllers;

import com.minsait.facturas.models.Factura;
import com.minsait.facturas.repositories.FacturaRepository;
import com.minsait.facturas.services.FacturaService;
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
    public ResponseEntity<Factura> guardar(@RequestBody Factura factura) {
        try {
            return new ResponseEntity<>(facturaService.guardar(factura), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (facturaService.eliminar(id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
