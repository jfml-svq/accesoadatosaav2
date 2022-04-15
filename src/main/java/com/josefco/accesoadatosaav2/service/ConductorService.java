package com.josefco.accesoadatosaav2.service;

import com.josefco.accesoadatosaav2.domain.Camion;
import com.josefco.accesoadatosaav2.domain.Conductor;
import com.josefco.accesoadatosaav2.exception.ConductorNoEncontradoException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ConductorService {

    Flux<Conductor> findAllConductores();
    Mono<Conductor> findConductor(String id) throws ConductorNoEncontradoException;
    Mono<Void> deleteConductor (String id) throws  ConductorNoEncontradoException;
    Mono<Conductor> modifyConductor (String id, Conductor Conductor) throws ConductorNoEncontradoException;

    Flux<Conductor> findConductorByDireccion(String direccion) throws ConductorNoEncontradoException;

    Mono<Conductor>  saveConductor(Conductor conductor);

    void addAsignacion(Conductor conductor, Camion camion);
}
