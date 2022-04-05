package com.josefco.accesoadatosaav2.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RutaDTO {

    private int idUsuario;
    private int idPaquete;
    private double latUsuario;
    private double lonUsuario;

}
