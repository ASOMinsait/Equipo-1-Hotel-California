package com.minsait.facturas.models;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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
    @Column(name = "metodo_pago")
    private String metodoPago;
    @Column(name = "estado_pago")
    private String estadoPago;



}
