package com.josefco.accesoadatosaav2.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "paquetes")
public class Paquete {

    @Id
    private String id;
    @Field
    private int ancho;
    @Field
    private int largo;
    @Field
    private int alto;
    @Field
    private int peso;
    @Field
    private String color;
    @Field(name = "fecha")
    @DateTimeFormat(pattern="dd-MM-yyyy")
    private LocalDate fecha;


    @ManyToOne
    @JoinColumn(name = "conductor_id")
    @JsonBackReference(value = "conductor-paquete")
    private Conductor conductor;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference(value = "usuario-paquete")
    private Usuario usuario;



}
