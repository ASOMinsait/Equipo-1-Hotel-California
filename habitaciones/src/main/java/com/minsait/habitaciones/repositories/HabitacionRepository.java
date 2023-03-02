package com.minsait.habitaciones.repositories;

import com.minsait.habitaciones.models.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitacionRepository extends JpaRepository<Habitacion,Long> {
}
