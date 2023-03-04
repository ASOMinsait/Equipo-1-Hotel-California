package com.minsait.reservas.exceptions;


public class HabitacionesException extends RuntimeException {

    public HabitacionesException(String message) {
        super(message);
    }

    public HabitacionesException(String message, Throwable cause) {
        super(message, cause);
    }

}
