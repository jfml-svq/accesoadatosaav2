package com.josefco.accesoadatosaav2.service;

import com.josefco.accesoadatosaav2.domain.Paquete;
import com.josefco.accesoadatosaav2.domain.dto.PaquetDTO;
import com.josefco.accesoadatosaav2.exception.PaqueteNoEncontradoException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaqueteService {


    Flux<Paquete> findAllPaquetes();
    Mono<Paquete> findPaquete(String id) throws PaqueteNoEncontradoException;

    Mono<Paquete> addPaquete(PaquetDTO paquetDTO) throws Exception;
    Mono<Void> deletePaquete(String id) throws PaqueteNoEncontradoException;
    Mono<Paquete> modifyPaquete(String id, Paquete Paquete) throws PaqueteNoEncontradoException;

    Flux<Paquete> findPaqueteByColor(String color) throws PaqueteNoEncontradoException;

//    int countPaquete();
//
//
//    Flux<Paquete> getPaqueteExtraPriceByPeso(int peso);
//
//    Flux<Paquete> getPaquetesFilter(int ancho, int alto, int largo) throws PaqueteNoEncontradoException;
//
//    RutaDTO rutaPaquete(Paquete paquete) throws PaqueteNoEncontradoException;
}
