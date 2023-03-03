package com.minsait.habitaciones.services;

import com.minsait.habitaciones.models.Habitacion;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface HabitacionService {

    List<Habitacion> buscarDisponibles();

    List<Habitacion> buscarTodas();
    Optional<Habitacion> buscarPorId(Long id);

    List<Habitacion>  buscarPorMaxPersonas(Integer max);

    void reservarHabitacion(Long id);

    Habitacion guardar(Habitacion habitacion);

   boolean eliminar(Long id);








}
