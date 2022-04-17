package com.josefco.accesoadatosaav2.service;

import com.josefco.accesoadatosaav2.domain.Usuario;
import com.josefco.accesoadatosaav2.exception.UsuarioNoEncontradoException;
import com.josefco.accesoadatosaav2.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public Flux<Usuario> findAllUsuarios() {
        return usuarioRepository.findAll();
    }


    @Override
    public Mono<Usuario> findUsuario(String id) {
        Mono<Usuario> fallback = Mono.error(UsuarioNoEncontradoException::new);
        return usuarioRepository.findById(id).switchIfEmpty(fallback);
    }


    @Override
    public Mono<Usuario> addUsuario(Usuario usuario) {

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        String text = date.format(formatters);
        LocalDate parsedDate = LocalDate.parse(text, formatters);

        Usuario newusuario = new Usuario();
        newusuario.setNombre(usuario.getNombre());
        newusuario.setApellido(usuario.getApellido());
        newusuario.setTelefono(usuario.getTelefono());
        newusuario.setDireccion(usuario.getDireccion());
        newusuario.setFechaReg(parsedDate);
        newusuario.setLat(usuario.getLat());
        newusuario.setLon(usuario.getLon());
        newusuario.setEmail(usuario.getEmail());
        return usuarioRepository.save(newusuario);
    }


    public Mono<Void> deleteUsuario(String id){
        Mono<Usuario> fallback = Mono.error(new UsuarioNoEncontradoException());
        usuarioRepository.findById(id).switchIfEmpty(fallback);
        return usuarioRepository.deleteById(id);
    }

    @Override
    public Mono<Usuario> modifyUsuario(String id, Usuario newUsuario){

        Mono<Usuario> monoUsuario = usuarioRepository.findById(id).onErrorReturn(new Usuario());
        Usuario usuario = monoUsuario.block();
        usuario.setNombre(newUsuario.getNombre());
        usuario.setApellido(newUsuario.getApellido());
        usuario.setTelefono(newUsuario.getTelefono());
        usuario.setDireccion(newUsuario.getDireccion());
        usuario.setLat(newUsuario.getLat());
        usuario.setLon(newUsuario.getLon());
        usuario.setEmail(newUsuario.getEmail());
        return usuarioRepository.save(usuario);

    }

    @Override
    public Mono<Boolean> usuarioExiste(String id) {
        return usuarioRepository.existsById(id);
    }

    @Override
    public Flux<Usuario> findAllByFilters(String nombre, String apellido, String direccion) throws UsuarioNoEncontradoException {
        return usuarioRepository.findByNombreOrApellidoOrDireccion(nombre, apellido,direccion);
    }


}
