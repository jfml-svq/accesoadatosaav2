package com.josefco.accesoadatosaav2.repository;

import com.josefco.accesoadatosaav2.domain.Usuario;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UsuarioRepository extends ReactiveMongoRepository<Usuario, String> {

    Flux<Usuario> findAll();

    Flux<Usuario> findUsuariosByDireccion(String direccion);


    //Usuario findById(int id);


//    //SQL
//    @Query(value = "SELECT COUNT(*) FROM \"usuarios\"", nativeQuery = true)
//    int countUsuarios();

    //RutaDTO rutaPaquete(int id_paquete);
}
