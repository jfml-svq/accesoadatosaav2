package com.josefco.accesoadatosaav2.service;

import com.josefco.accesoadatosaav2.domain.Usuario;
import com.josefco.accesoadatosaav2.exception.UsuarioNoEncontradoException;
import com.josefco.accesoadatosaav2.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public Flux<Usuario> findAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Mono<Usuario> findUsuario(String id) throws UsuarioNoEncontradoException {
        return usuarioRepository.findById(id).onErrorReturn(new Usuario());
    }

//    @Override
//    public Mono<Usuario> addUsuario(Usuario Usuario) {
//        return usuarioRepository.save(Usuario);
//    }

    @Override
    public Mono<Usuario> addUsuario(Usuario usuario) {
        Usuario newusuario = new Usuario();
        newusuario.setNombre(usuario.getNombre());
        newusuario.setApellido(usuario.getApellido());
        newusuario.setTelefono(usuario.getTelefono());
        newusuario.setDireccion(usuario.getDireccion());
        newusuario.setFechaReg(usuario.getFechaReg());
        newusuario.setLat(usuario.getLat());
        newusuario.setLon(usuario.getLon());


        return usuarioRepository.save(newusuario);
    }

//    @Override
//    public Mono<Usuario> deleteUsuario(String id) throws UsuarioNoEncontradoException {
//        Mono<Usuario> monoUsuario = usuarioRepository.findById(id).onErrorReturn(new Usuario());
//        usuarioRepository.delete(monoUsuario);
//        return monoUsuario;
//    }

    public Mono<Void> deleteUsuario(final String id){
        return usuarioRepository.deleteById(id);
    }

    @Override
    public Mono<Usuario> modifyUsuario(String id, Usuario newUsuario) throws UsuarioNoEncontradoException {
        Mono<Usuario> monoUsuario = usuarioRepository.findById(id).onErrorReturn(new Usuario());
        Usuario usuario = monoUsuario.block();
        usuario.setNombre(newUsuario.getNombre());
        usuario.setApellido(newUsuario.getApellido());
        usuario.setTelefono(newUsuario.getTelefono());
        usuario.setDireccion(newUsuario.getDireccion());
        usuario.setLat(newUsuario.getLat());
        usuario.setLon(newUsuario.getLon());

        return usuarioRepository.save(usuario);
    }

//    @Override
//    public Flux<Usuario> findUsuariosByDireccion(String direccion) throws UsuarioNoEncontradoException {
//            return usuarioRepository.findUsuariosByDireccion(direccion);
//    }
//
//    @Override
//    public int countUsuario() {
//        return usuarioRepository.countUsuarios();
//    }

}
