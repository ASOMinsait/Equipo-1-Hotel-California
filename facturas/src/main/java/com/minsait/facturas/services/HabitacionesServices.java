package com.minsait.facturas.services;


import com.minsait.facturas.models.Habitacion;

import java.util.List;
import java.util.Optional;


public interface HabitacionesServices {

    Optional<Habitacion> buscarPorId(Long id);



}
