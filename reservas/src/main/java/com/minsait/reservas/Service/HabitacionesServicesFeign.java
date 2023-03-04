package com.minsait.reservas.Service;

import com.minsait.reservas.Models.Habitacion;
import com.minsait.reservas.client.HabitacionesClienteRest;
import com.minsait.reservas.exceptions.HabitacionesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabitacionesServicesFeign implements HabitacionesServices {

    @Autowired
    private HabitacionesClienteRest habitacionesClienteRest;


    @Override
    public List<Habitacion> buscarDisponibles() {

        ResponseEntity<List<Habitacion>> response = habitacionesClienteRest.listarDisponibles();
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new HabitacionesException("Error al obtener las habitaciones: " + response.getStatusCode());
        }
    }

    @Override
    public List<Habitacion> buscarTodas() {
        ResponseEntity<List<Habitacion>> response = habitacionesClienteRest.listarTodas();
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new HabitacionesException("Error al obtener las habitaciones: " + response.getStatusCode());
        }

    }

    @Override
    public Optional<Habitacion> buscarPorId(Long id) {
        ResponseEntity<Habitacion> response = habitacionesClienteRest.buscarPorId(id);
        if (response.getStatusCode().is2xxSuccessful()) {
            return Optional.of(response.getBody());
        } else {
            throw new HabitacionesException("Error al obtener las habitaciones: " + response.getStatusCode());
        }
    }

    @Override
    public List<Habitacion> buscarPorMaxPersonas(Integer max) {
        return habitacionesClienteRest.buscarPorMaximoPersonas(max);
    }

    @Override
    public void reservarHabitacion(Long id) {
        habitacionesClienteRest.reservarHabitacion(id);
    }

    @Override
    public Habitacion guardar(Habitacion habitacion) {
        return habitacionesClienteRest.guardar(habitacion).getBody();
    }

    @Override
    public boolean eliminar(Long id) {
        ResponseEntity<?> response = habitacionesClienteRest.eliminar(id);
        return response.getStatusCode().is2xxSuccessful();

    }
}
