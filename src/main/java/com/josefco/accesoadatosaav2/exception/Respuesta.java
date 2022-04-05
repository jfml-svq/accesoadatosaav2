package com.josefco.accesoadatosaav2.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Respuesta {

    private String codigoInterno;
    private String mensaje;
}
