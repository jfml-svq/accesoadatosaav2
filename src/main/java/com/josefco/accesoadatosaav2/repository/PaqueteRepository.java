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


//    //SQL
//    @Query(value = "SELECT COUNT(*) FROM \"paquetes\"", nativeQuery = true)
//    int countPaquetes();
//
//    @Query(value = "SELECT * FROM \"paquetes\" WHERE \"ancho\" = :ancho AND  \"alto\" = :alto AND \"largo\" = :largo", nativeQuery = true)
//    Flux<Paquete> getPaquetesFilter(int ancho, int alto, int largo);
//
//    //JPQL
//    @Query("SELECT p FROM paquetes p WHERE peso < :peso")
//    Flux<Paquete> getPaqueteExtraPriceByPeso(int peso);
//
//    @Query(value = "SELECT * FROM \"paquetes\" WHERE \"idPaquete\" = :idPaquete", nativeQuery = true)
//    RutaDTO rutaPaquete(int idPaquete);
}
