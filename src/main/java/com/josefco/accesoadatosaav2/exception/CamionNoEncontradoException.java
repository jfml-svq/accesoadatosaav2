package com.josefco.accesoadatosaav2.exception;

public class CamionNoEncontradoException extends Exception{

    private static final String DEFAULT_ERROR_MESSAGE = "Camion no encontrado";

    public CamionNoEncontradoException(String message)
    {
        super(message);
    }

    public CamionNoEncontradoException() { super(DEFAULT_ERROR_MESSAGE);}



}
