package com.josefco.accesoadatosaav2.exception;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RespuestaError {

    private int code;
    private String message;
    private Map<String, String> errors;

    private RespuestaError(int errorCode, String errorMessage) {
        code = errorCode;
        message = errorMessage;
        errors = new HashMap<>();
    }

    public RespuestaError(String message){
        this.message = message;
    }

    private RespuestaError(int code, String message, Map<String, String> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public static RespuestaError generalError(int code, String message) {
        return new RespuestaError(code, message);
    }

    public static RespuestaError validationError(Map<String, String> errors) {
        return new RespuestaError(11, "Validacion de errores", errors);
    }

    public static RespuestaError noEncontrado(int code, String error){
        return new RespuestaError(code,"No se ha encontrado en la base de datos");
    }

}