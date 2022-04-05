package com.josefco.accesoadatosaav2.repository;

import com.josefco.accesoadatosaav2.domain.Conductor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ConductorRepository extends ReactiveMongoRepository<Conductor, String> {

    Flux<Conductor> findAll();
    //Conductor findById(int id);
    Flux<Conductor> findConductorByDireccion(String direccion);
}
