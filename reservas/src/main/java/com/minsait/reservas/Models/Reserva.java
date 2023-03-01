package com.minsait.reservas.Models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservas")
public class Reserva implements Serializable {
    static private final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long id;
    @Column(name = "id_habitacion")
    private Long idHabitacion;
    @Column(name = "fecha_checkin")
    private Date fechaCheckIn;
    @Column(name = "fecha_checkout")
    private Date fechaCheckOut;
    @Column(name = "estado_reservacion")
    private String estadoReservacion;
}
