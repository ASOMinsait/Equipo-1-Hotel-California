package com.minsait.reservas.Service;

import com.minsait.reservas.Models.Reserva;
import com.minsait.reservas.Repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class ReservaServiceImpl implements ReservaService{
    @Autowired
    ReservaRepository reservaRepository;
    @Override
    public List<Reserva> mostrarTodos(){
        return reservaRepository.findAll();
    }
    @Override
    public Optional<Reserva> buscarPorId(Long id){
        return reservaRepository.findById(id);
    }
    @Override
    public Reserva crearReserva(Reserva reserva){
        return reservaRepository.save(reserva);
    }
    @Override
    public boolean eliminar(Long id){
        boolean bool = false;
        Optional<Reserva> reserva = reservaRepository.findById(id);
        if (reserva.isPresent()) {
            reservaRepository.deleteById(id);
            bool = true;
        }
        return bool;
    }
    @Override
    public Optional<Reserva> buscarHabitacionPorId(Long id){return reservaRepository.findByHabitacion(id);}
}
