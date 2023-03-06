package com.minsait.facturas.services;

import com.minsait.facturas.client.HabitacionesClienteRest;
import com.minsait.facturas.models.Habitacion;

import org.springframework.beans.factory.annotation.Autowired;
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
    public Optional<Habitacion> buscarPorId(Long id) throws NoSuchElementException {
        try {

            ResponseEntity<Habitacion> response = habitacionesClienteRest.buscarPorId(id);
            if (response.getStatusCode().is2xxSuccessful()) {
                return Optional.of(response.getBody());
            }
            throw new NoSuchElementException();
        } catch (Exception e) {
            throw new NoSuchElementException();
        }
    }

}
