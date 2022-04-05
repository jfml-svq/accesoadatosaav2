package com.josefco.accesoadatosaav2.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Document(value = "conductores")
public class Conductor {

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

    /*@OneToOne
    @JoinColumn(name = "id_camion")
    @JsonBackReference(value = "conductor-camion")
    private Camion camion;*/


    @OneToMany(mappedBy = "conductor")
    private List<Paquete> paquetes;


    @ManyToMany //(mappedBy = "conductores")
    private List<Camion> camiones;

    public Conductor (){
        camiones = new ArrayList<>();
    }


//    @JsonBackReference
//    @ManyToMany(mappedBy="conductores")
//    private List<Camion> camiones=new ArrayList<>();
//
//    public Conductor(List<Camion> camiones) {
//        this.camiones = camiones;
//    }

    /*public List<Camion> getCamiones() {
        return camiones;
    }

    public void setCamiones(List<Camion> camiones) {
        this.camiones = camiones;
    }*/
}
