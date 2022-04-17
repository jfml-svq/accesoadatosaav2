package com.josefco.accesoadatosaav2.service;

import com.josefco.accesoadatosaav2.domain.Camion;
import com.josefco.accesoadatosaav2.exception.CamionNoEncontradoException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CamionService {

    Flux<Camion> findAllCamiones();
    Mono<Camion> findCamion(String id) throws CamionNoEncontradoException;
    Mono<Camion> deleteCamion(String id) throws CamionNoEncontradoException;
    Mono<Camion> modifyCamion(String id, Camion Camion) throws CamionNoEncontradoException;

    Mono<Camion> saveCamion(Camion camion);

    Flux<Camion> findCamionesByMarca(String marca) throws CamionNoEncontradoException;

}
