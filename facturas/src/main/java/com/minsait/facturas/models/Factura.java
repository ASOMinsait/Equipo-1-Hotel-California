package com.minsait.facturas.models;


import com.minsait.facturas.helpers.CalcularDias;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Date;

@Data
@Entity(name = "facturas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Table(name = "facturas")
public class Factura {


    static private final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Long idFacturas;

    @Column(name = "id_reservacion")
    private Long idReservacion;

    @Column(name = "fecha_emision")
    private Date fechaEmision;
    @Column(name = "total_reservacion")
    private BigDecimal totalReserva;


    public void calcularTotal(Reservacion reservacion, Habitacion habitacion) {


        long diasEntreFechas = CalcularDias.calcularDias(reservacion.getFechaEntrada(), reservacion.getFechaSalida());

        this.totalReserva = diasEntreFechas!=0 ?habitacion.getPrecioNoche().multiply(new BigDecimal(diasEntreFechas)) : habitacion.getPrecioNoche();



    }


}
