package com.minsait.habitaciones.services;

import com.minsait.habitaciones.models.Habitacion;
import com.minsait.habitaciones.repositories.HabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class HabitacionServiceImp implements HabitacionService {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Override
    public List<Habitacion> buscarDisponibles() {
        return null;
    }

    @Override
    public List<Habitacion> buscarTodas() {
        return habitacionRepository.findAll();
    }

    @Override
    public Habitacion buscarPorId() {
        return null;
    }

    @Override
    public List<Habitacion> buscarPorMaxPersonas() {
        return null;
    }

    @Override
    public void reservarHabitacion(Long id) {

    }

    @Override
    public Habitacion guardar(Habitacion habitacion) {
        return null;
    }

    @Override
    public void eliminar(Long id) {

    }

    @Override
    public List<Habitacion> buscarPorRangoPrecios(BigDecimal precioMin, BigDecimal precioMax) {
        return null;
    }
}
