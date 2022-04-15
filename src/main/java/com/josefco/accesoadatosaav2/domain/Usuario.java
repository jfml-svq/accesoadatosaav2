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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private String nombre;
    @Field
    @NotNull
    private String apellido;
    @Field
    @NotNull
    private String telefono;
    @Field
    @NotNull
    private String direccion;
    @Field(name = "fecha_reg")
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate fechaReg;
    @Field
    private double lat;
    @Field
    private double lon;
    @Field
    @NotNull
    @Email
    private String email;


    /*@OneToMany(mappedBy = "usuario")
    private List<Paquete> paquete;*/

    @OneToMany(fetch = FetchType.LAZY)
    private List<Paquete> paquetes;
}
