package com.minsait.reservas.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Factura {

    private Long idFactura;

    private Long idReservacion;

    private Date fechaEmision;

    private BigDecimal totalReservacion;

    private String metodoPago;

    private String estadoPago;


}
