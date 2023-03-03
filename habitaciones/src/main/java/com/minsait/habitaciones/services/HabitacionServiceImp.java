package com.minsait.habitaciones.services;

import com.minsait.habitaciones.models.Habitacion;
import com.minsait.habitaciones.repositories.HabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
public class HabitacionServiceImp implements HabitacionService {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Habitacion> buscarDisponibles() {
        return habitacionRepository.buscarDisponibles();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Habitacion> buscarTodas() {
        return habitacionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Habitacion> buscarPorId(Long id) {
        return habitacionRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Habitacion> buscarPorMaxPersonas(Integer max) {
        return habitacionRepository.buscarPorMaxPersonas(max);
    }

    @Override
    public void reservarHabitacion(Long id) {
        Habitacion habitacion = habitacionRepository.findById(id).get();
        habitacion.setEstatus(0);
        habitacionRepository.save(habitacion);
    }

    @Override
    public Habitacion guardar(Habitacion habitacion) {
        return habitacionRepository.save(habitacion);
    }

    @Override
    public boolean eliminar(Long id) {

        boolean estadoEliminado = false;
        Optional<Habitacion> habitacion = habitacionRepository.findById(id);
        if (habitacion.isPresent()) {
            habitacionRepository.deleteById(id);
            estadoEliminado = true;
        }
        return estadoEliminado;
    }


}
