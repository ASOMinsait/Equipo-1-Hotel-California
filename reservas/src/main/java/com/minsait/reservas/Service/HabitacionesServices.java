package com.minsait.reservas.Service;


import com.minsait.reservas.Models.Habitacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;


public interface HabitacionesServices {
    List<Habitacion> buscarDisponibles();

    List<Habitacion> buscarTodas();
    Optional<Habitacion> buscarPorId(Long id);

    List<Habitacion>  buscarPorMaxPersonas(Integer max);

    void reservarHabitacion(Long id);


}
