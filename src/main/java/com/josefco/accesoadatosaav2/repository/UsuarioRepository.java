package com.josefco.accesoadatosaav2.repository;

import com.josefco.accesoadatosaav2.domain.Usuario;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface UsuarioRepository extends ReactiveMongoRepository<Usuario, String> {

    Flux<Usuario> findAll();

    Flux<Usuario> findByNombreOrApellidoOrDireccion(String nombre, String apellido, String direccion);

    //RutaDTO rutaPaquete(int id_paquete);
}
