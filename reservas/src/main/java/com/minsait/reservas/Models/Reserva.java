package com.minsait.reservas.Models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservas")
public class Reserva implements Serializable {
    static private final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReserva;
    @Column(name = "id_habitacion")
    private Long idHabitacion;
    @Column(name = "dias_reserva")
    private int diasDeReserva;
    @Column(name = "fecha_entrada")
    private Date fechaEntrada;
    @Column(name = "fecha_salida")
    private Date fechaSalida;

}
