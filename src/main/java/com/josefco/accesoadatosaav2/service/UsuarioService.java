package com.josefco.accesoadatosaav2.service;


import com.josefco.accesoadatosaav2.domain.Usuario;
import com.josefco.accesoadatosaav2.exception.UsuarioNoEncontradoException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UsuarioService {

    Flux<Usuario> findAllUsuarios();
    Mono<Usuario> findUsuario(String id) throws UsuarioNoEncontradoException;
    Mono<Usuario> addUsuario(Usuario usuario);
    Mono<Void> deleteUsuario(String id) throws UsuarioNoEncontradoException;
    Mono<Usuario> modifyUsuario(String id, Usuario usuario) throws UsuarioNoEncontradoException;
    public Mono<Boolean> usuarioExiste(String id);

    Flux<Usuario> findAllByFilters(String nombre, String apellido, String direccion) throws UsuarioNoEncontradoException;

}
