package com.josefco.accesoadatosaav2.exception;

public class UsuarioNoEncontradoException extends Exception{

    private static final String DEFAULT_ERROR_MESSAGE = "Usuario no encontrado";

    public UsuarioNoEncontradoException(String message)
    {
        super(message);
    }

    public UsuarioNoEncontradoException() { super(DEFAULT_ERROR_MESSAGE);}
}
