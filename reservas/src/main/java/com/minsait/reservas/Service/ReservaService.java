package com.minsait.reservas.Service;

import com.minsait.reservas.Models.Reserva;

import java.util.List;
import java.util.Optional;

public interface ReservaService {
    List<Reserva> mostrarTodos();
    Optional<Reserva> buscarPorId(Long id);
    Reserva crearReserva(Reserva reserva);
    boolean eliminar(Long id);
}
