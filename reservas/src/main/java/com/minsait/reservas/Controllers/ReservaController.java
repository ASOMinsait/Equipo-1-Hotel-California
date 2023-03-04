package com.minsait.reservas.Controllers;

import com.minsait.reservas.Models.Habitacion;
import com.minsait.reservas.Models.Reserva;
import com.minsait.reservas.Service.HabitacionesServicesFeign;
import com.minsait.reservas.Service.ReservaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RequestMapping("/api/v1/reservas")
@Slf4j
@RestController
public class ReservaController {
    @Autowired
    ReservaService service;

    @Autowired
    HabitacionesServicesFeign habitacionesServicesFeign;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Reserva> findAll() {
        log.info("Listando todo...");
        return service.mostrarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> findById(@PathVariable Long id) {
        try {
            Reserva reserva = service.buscarPorId(id).get();
            return ResponseEntity.ok(reserva);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Reserva save(@RequestBody Reserva reserva) {
        return service.crearReserva(reserva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (service.eliminar(id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizar(@PathVariable Long id, @RequestBody Reserva reserva) {
        try {
            Reserva reservaActualizada = service.buscarPorId(id).get();
            reservaActualizada.setIdHabitacion(reserva.getIdHabitacion());
            reservaActualizada.setFechaEntrada(reserva.getFechaEntrada());
            reservaActualizada.setFechaSalida(reserva.getFechaSalida());
            reservaActualizada.setEstadoReservacion(reserva.getEstadoReservacion());
            return new ResponseEntity<>(service.crearReserva(reservaActualizada), HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/habitaciones/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Habitacion>> buscarTodasHabitaciones() {
        try {
        return ResponseEntity.ok(habitacionesServicesFeign.buscarTodas());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/habitaciones/disponibles")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Habitacion>> buscarHabitacionDisponibles() {
        return ResponseEntity.ok(habitacionesServicesFeign.buscarDisponibles());
    }

    @GetMapping("/habitaciones/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Habitacion> buscarHabitacionPorId(@PathVariable Long id) {
        return ResponseEntity.ok(habitacionesServicesFeign.buscarPorId(id).get());
    }
}
