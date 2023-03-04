package com.minsait.reservas.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "facturas")
public interface FacturasClienteRest {
}
