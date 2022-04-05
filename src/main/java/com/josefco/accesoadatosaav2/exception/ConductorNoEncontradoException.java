package com.josefco.accesoadatosaav2.exception;

public class ConductorNoEncontradoException extends Exception {

    private static final String DEFAULT_ERROR_MESSAGE = "Conductor no encontrado";

    public ConductorNoEncontradoException(String message)
    {
        super(message);
    }

    public ConductorNoEncontradoException() { super(DEFAULT_ERROR_MESSAGE);}
}
