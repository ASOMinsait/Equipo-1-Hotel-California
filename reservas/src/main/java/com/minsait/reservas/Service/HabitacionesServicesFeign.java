package com.minsait.reservas.Service;

import com.minsait.reservas.Models.Habitacion;
import com.minsait.reservas.client.HabitacionesClienteRest;

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
            throw new NoSuchElementException();
        }
    }

    @Override
    public List<Habitacion> buscarTodas() {
        ResponseEntity<List<Habitacion>> response = habitacionesClienteRest.listarTodas();
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new NoSuchElementException();
        }

    }

    @Override
    public Optional<Habitacion> buscarPorId(Long id) throws NoSuchElementException {


        ResponseEntity<Habitacion> response = habitacionesClienteRest.buscarPorId(id);
        if (response.getStatusCode().is2xxSuccessful()) {
            return Optional.of(response.getBody());
        }
        throw new NoSuchElementException();

    }

    @Override
    public List<Habitacion> buscarPorMaxPersonas(Integer max) {
        ResponseEntity<List<Habitacion>> response = habitacionesClienteRest.buscarPorMaximoPersonas(max);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void reservarHabitacion(Long id) {
        try {
            habitacionesClienteRest.reservarHabitacion(id);
        }catch (NoSuchElementException e){
            throw new NoSuchElementException();
        }

    }


}
