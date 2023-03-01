package com.minsait.reservas.Repositories;

import com.minsait.reservas.Models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
