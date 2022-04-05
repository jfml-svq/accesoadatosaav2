package com.josefco.accesoadatosaav2.domain.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PaquetDTO {

    @Id
    private String idPaquete;
    private int ancho;
    private int largo;
    private int alto;
    private int peso;
    private String color;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fecha;
    private String usuario;
    private String conductor;

}

