package com.minsait.facturas.models;



import lombok.*;
import java.util.Date;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Reservacion {


    private Long idReserva;

    private Long idHabitacion;

    private Date fechaEntrada;

    private Date fechaSalida;




}
