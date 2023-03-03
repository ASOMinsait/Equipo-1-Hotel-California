package com.minsait.reservas.client;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "habitaciones")
public class HabitacionesClienteRest {
}
