package com.minsait.reservas.client;


import com.minsait.reservas.Models.Habitacion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "habitaciones", url="${habitaciones.url}")
public interface HabitacionesClienteRest {

    @GetMapping("/")
    ResponseEntity<List<Habitacion>> listarTodas();

    @GetMapping("/disponibles")
    public ResponseEntity<List<Habitacion>> listarDisponibles();

    @GetMapping("/{id}")
    public ResponseEntity<Habitacion> buscarPorId(@PathVariable Long id);

    @GetMapping("/max/{max}")
    public ResponseEntity<List<Habitacion>> buscarPorMaximoPersonas(@PathVariable Integer max);

    @PutMapping("/reservar/{id}")
    public ResponseEntity<Habitacion> reservarHabitacion(@PathVariable Long id);



}
