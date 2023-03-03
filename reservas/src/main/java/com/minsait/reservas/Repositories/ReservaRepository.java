package com.minsait.reservas.Repositories;

import com.minsait.reservas.Models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    @Query("SELECT f FROM facturas f WHERE f.idHabitacion = :id")
    Optional<Reserva> findByHabitacion(@Param("id") Long id);
}
