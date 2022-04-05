package com.josefco.accesoadatosaav2.exception;

public class PaqueteNoEncontradoException extends Exception{

    private static final String DEFAULT_ERROR_MESSAGE = "Paquete no encontrado";

    public PaqueteNoEncontradoException(String message)
    {
        super(message);
    }

    public PaqueteNoEncontradoException() { super(DEFAULT_ERROR_MESSAGE);}
}
