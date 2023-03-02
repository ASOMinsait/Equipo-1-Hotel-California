package com.minsait.habitaciones.services;

import com.minsait.habitaciones.models.Habitacion;

import java.math.BigDecimal;
import java.util.List;

public interface HabitacionService {

    List<Habitacion> buscarDisponibles();

    List<Habitacion> buscarTodas();
    Habitacion buscarPorId();

    List<Habitacion>  buscarPorMaxPersonas();

    void reservarHabitacion(Long id);

    Habitacion guardar(Habitacion habitacion);

   void eliminar(Long id);

   List<Habitacion> buscarPorRangoPrecios(BigDecimal precioMin, BigDecimal precioMax);







}
