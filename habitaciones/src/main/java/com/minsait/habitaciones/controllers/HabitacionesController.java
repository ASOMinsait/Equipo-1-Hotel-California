package com.minsait.habitaciones.controllers;


import com.minsait.habitaciones.models.Habitacion;
import com.minsait.habitaciones.services.HabitacionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/habitaciones")
public class HabitacionesController {


    @Autowired
    HabitacionService habitacionService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Habitacion>> buscarTodas() {
        return ResponseEntity.ok(habitacionService.buscarTodas());
    }

    @GetMapping("/disponibles")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Habitacion>> buscarDisponibles() {
        return ResponseEntity.ok(habitacionService.buscarDisponibles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habitacion> buscarPorId(@PathVariable Long id) {
        try {
            Habitacion habitacion = habitacionService.buscarPorId(id).get();
            return ResponseEntity.ok(habitacion);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/max/{max}")
    public ResponseEntity<List<Habitacion>> buscarPorMaximoPersona(@PathVariable Integer max) {
        return ResponseEntity.ok(habitacionService.buscarPorMaxPersonas(max));
    }

    @PutMapping("/reservar/{id}")
    public ResponseEntity<Habitacion> reservarHabitacion(@PathVariable Long id) {
        try {
            Habitacion habitacion = habitacionService.buscarPorId(id).get();
         if (habitacion.getEstatus()==1){
             habitacionService.reservarHabitacion(habitacion.getIdHabitacion());
             return ResponseEntity.ok(habitacionService.buscarPorId(id).get());
         }else{
             return ResponseEntity.notFound().build();
         }
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Habitacion> guardar(@RequestBody Habitacion habitacion) {
        try {
            return new ResponseEntity<>(habitacionService.guardar(habitacion), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (habitacionService.eliminar(id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar( @RequestBody Habitacion habitacion, @PathVariable Long id) {

        Optional<Habitacion> o = habitacionService.buscarPorId(id);
        if (o.isPresent()) {
            Habitacion habitacionBd = o.get();
            habitacionBd.setEstatus(habitacion.getEstatus());
            habitacionBd.setMaxPersonas(habitacion.getMaxPersonas());
            habitacionBd.setPrecioNoche(habitacion.getPrecioNoche());

            return ResponseEntity.status(HttpStatus.CREATED).body(habitacionService.guardar(habitacionBd));
        }
        return ResponseEntity.notFound().build();
    }
}
