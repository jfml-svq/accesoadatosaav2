package com.josefco.accesoadatosaav2.repository;


import com.josefco.accesoadatosaav2.domain.Paquete;
import com.josefco.accesoadatosaav2.exception.PaqueteNoEncontradoException;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PaqueteRepository extends ReactiveMongoRepository<Paquete, String> {

    Flux<Paquete> findAll();
    Flux<Paquete> findPaqueteByColor(String color) throws PaqueteNoEncontradoException;

}
