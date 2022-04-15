package com.josefco.accesoadatosaav2.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Document(value = "camiones")
public class Camion {

    @Id
    private String id;
    @Field
    @NotNull
    private String matricula;
    @Field
    @NotNull
    private String modelo;
    @Field
    @NotNull
    private String marca;
    @Field
    @PositiveOrZero
    @NotNull
    private int gasolina;

    /*@OneToOne(mappedBy = "camion")
    private Conductor conductor;*/

    @JsonBackReference
    @ManyToMany
    private List<Conductor> conductores;

    public Camion(){
        conductores = new ArrayList<>();
    }

//    @JsonBackReference
//    @ManyToMany
//    @JoinTable(name="camion_conductor",joinColumns=@JoinColumn(name="camion_id"),
//            inverseJoinColumns=@JoinColumn(name="conductor_id"))
//    private List<Conductor> conductores=new ArrayList<>();
//
//    public Camion(List<Conductor> conductores) {
//        this.conductores = conductores;
//    }

    /*public List<Conductor> getConductores() {
        return conductores;
    }

    public void setConductores(List<Conductor> conductores) {
        this.conductores = conductores;
    }*/
}
