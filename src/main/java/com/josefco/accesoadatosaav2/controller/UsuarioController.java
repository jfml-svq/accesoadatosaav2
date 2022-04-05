package com.josefco.accesoadatosaav2.controller;

import com.josefco.accesoadatosaav2.domain.Usuario;
import com.josefco.accesoadatosaav2.exception.RespuestaError;
import com.josefco.accesoadatosaav2.exception.UsuarioNoEncontradoException;
import com.josefco.accesoadatosaav2.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UsuarioController {

    private final Logger logger = LoggerFactory.getLogger(PaqueteController.class);

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public Flux<Usuario> findAllUsuarios() {
        logger.info("begin findAllUsuarios");
        Flux<Usuario> usuarios;
        usuarios =  usuarioService.findAllUsuarios();
        logger.info("end findAllUsuarios");
        return usuarios;
    }

//    @RequestMapping(value = "/usuarios/direccion/{direccion}")
//    public Flux<Usuario> findUsuariosByDireccion(@PathVariable String direccion) throws UsuarioNoEncontradoException {
//        logger.info("begin findUsuariosFilter");
//        Flux<Usuario> usuarios = usuarioService.findUsuariosByDireccion(direccion);
//        logger.info("end findUsuariosFilter");
//        return usuarios;
//    }

    @GetMapping("/usuario/{id}")
    public Mono<Usuario> getUsuario(@PathVariable String id) throws UsuarioNoEncontradoException {
        logger.info("begin getUsuario");
        Mono<Usuario> Usuario = usuarioService.findUsuario(id);
        logger.info("end getUsuario");
        return Usuario;
    }

    @DeleteMapping("/usuario/{id}")
    public Mono<Void> deleteUsuario(@PathVariable String id) throws UsuarioNoEncontradoException {
        logger.info("begin removeUsuario");
        //Mono<Usuario> Usuario = usuarioService.deleteUsuario(id);
        logger.info("end removeUsuario");
        //return Usuario;
        return usuarioService.deleteUsuario(id);
    }

    @PostMapping("/usuario")
    public Mono<Usuario> addUsuario(@RequestBody Usuario usuario) {
        logger.info("begin addUsuario");
        Mono<Usuario> newUsuario = usuarioService.addUsuario(usuario);
        logger.info("end addUsuario");
        return newUsuario;
    }

    @PutMapping("/usuario/{id}")
    public Mono<Usuario> modifyUsuario(@RequestBody Usuario usuario, @PathVariable String id) throws UsuarioNoEncontradoException {
        logger.info("begin modifyUsuario");
        Mono<Usuario> newUsuario = usuarioService.modifyUsuario(id, usuario);
        logger.info("end modifyUsuario");
        return newUsuario;
    }

//    @GetMapping("/usuarios/contador")
//    public int countUsuario(){
//        logger.info("start countUsuario");
//        int contador = usuarioService.countUsuario();
//        logger.info("end countUsuario");
//        return contador;
//    }



    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<RespuestaError> handleUsuarioNoEncontradoException(UsuarioNoEncontradoException unee) {
        RespuestaError errorResponse = new RespuestaError("1", unee.getMessage());
        logger.info(unee.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // TODO Más tipos de excepciones que puedan generar errores

    @ExceptionHandler
    public ResponseEntity<RespuestaError> handleException(Exception exception) {
        RespuestaError errorResponse = new RespuestaError("999", "Internal server error");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
