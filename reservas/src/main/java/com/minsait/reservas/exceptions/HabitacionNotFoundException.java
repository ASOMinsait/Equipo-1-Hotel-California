package com.minsait.reservas.exceptions;
public class HabitacionNotFoundException extends RuntimeException {

    public HabitacionNotFoundException(String message) {
        super(message);
    }

    public HabitacionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
