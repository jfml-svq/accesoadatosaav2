package com.josefco.accesoadatosaav2.repository;

import com.josefco.accesoadatosaav2.domain.Camion;
import com.josefco.accesoadatosaav2.exception.CamionNoEncontradoException;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CamionRepository extends ReactiveMongoRepository<Camion, String> {

    Flux<Camion> findAll();

    Flux<Camion> findCamionesByMarca(String marca) throws CamionNoEncontradoException;
}
