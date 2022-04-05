package com.josefco.accesoadatosaav2.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "usuarios")
public class Usuario {

    @Id
    private String id;
    @Field
    private String nombre;
    @Field
    private String apellido;
    @Field
    private String telefono;
    @Field
    private String direccion;
    @Field(name = "fecha_reg")
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate fechaReg;
    @Field
    private double lat;
    @Field
    private double lon;


    /*@OneToMany(mappedBy = "usuario")
    private List<Paquete> paquete;*/

    @OneToMany(fetch = FetchType.LAZY)
    private List<Paquete> paquetes;
}
