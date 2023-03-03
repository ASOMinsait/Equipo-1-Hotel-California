package com.minsait.habitaciones.repositories;

import com.minsait.habitaciones.models.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface HabitacionRepository extends JpaRepository<Habitacion,Long> {

    @Query("SELECT h FROM Habitacion h WHERE h.estatus=1")
    List<Habitacion> buscarDisponibles();

    @Query("SELECT h FROM Habitacion h WHERE h.maxPersonas <= :max and h.estatus=1")
    List<Habitacion> buscarPorMaxPersonas(@Param("max") Integer max);



}

