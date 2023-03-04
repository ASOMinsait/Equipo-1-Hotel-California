package com.minsait.reservas.Service;

import com.minsait.reservas.Models.Habitacion;
import com.minsait.reservas.client.HabitacionesClienteRest;
import com.minsait.reservas.exceptions.HabitacionNotFoundException;
import com.minsait.reservas.exceptions.HabitacionesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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
    public Optional<Habitacion> buscarPorId(Long id) throws NoSuchElementException {
        try {

            ResponseEntity<Habitacion> response = habitacionesClienteRest.buscarPorId(id);
        if (response.getStatusCode().is2xxSuccessful()) {
            return Optional.of(response.getBody());
        } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new HabitacionNotFoundException("No se encontró la habitación con ID: " + id);
        } else {
            throw new HabitacionesException("Error al obtener la habitación con ID: " + id + ", código de estado: " + response.getStatusCode());
        }
        } catch (NoSuchElementException e) {
            throw new HabitacionNotFoundException("No se encontró la habitación con ID: " + id);
        }
    }

    @Override
    public List<Habitacion> buscarPorMaxPersonas(Integer max) {
        ResponseEntity<List<Habitacion>> response = habitacionesClienteRest.buscarPorMaximoPersonas(max);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new HabitacionesException("Error al obtener las habitaciones: " + response.getStatusCode());
        }
    }

    @Override
    public void reservarHabitacion(Long id) {
        habitacionesClienteRest.reservarHabitacion(id);
    }


}
