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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UsuarioController {

    private final Logger logger = LoggerFactory.getLogger(PaqueteController.class);

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping("/usuarios")
    public ResponseEntity<Flux<Usuario>> getUsuarios(
            @RequestParam(name = "nombre", required = false) String nombre,
            @RequestParam(name = "apellido", required = false) String apellido,
            @RequestParam(name = "direccion", required = false) String direccion,
            @RequestParam(name = "all", defaultValue = "true") boolean all) throws UsuarioNoEncontradoException {
        logger.info("begin getUsuarios");
        Flux<Usuario> usuarios;

        if (all) {
            usuarios = usuarioService.findAllUsuarios();
        } else {
            usuarios = usuarioService.findAllByFilters(nombre, apellido, direccion);
        }
        logger.info("end getUsuarios");
        return ResponseEntity.ok().body(usuarios);
    }


    @GetMapping("/usuario/{id}")
    public ResponseEntity<Mono<Usuario>> getUsuario(@PathVariable String id) throws UsuarioNoEncontradoException {
        logger.info("begin getUsuario");
        Mono<Usuario> usuario = usuarioService.findUsuario(id);
        logger.info("end getUsuario");
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<Mono<Void>> deleteUsuario(@PathVariable String id) throws UsuarioNoEncontradoException {
        logger.info("begin removeUsuario");
        //Mono<Usuario> Usuario = usuarioService.deleteUsuario(id);
        logger.info("end removeUsuario");
        //return Usuario;
        return ResponseEntity.ok().body(usuarioService.deleteUsuario(id));
    }

    @PostMapping("/usuario")
    public ResponseEntity<Mono<Usuario>> addUsuario(@Valid @RequestBody Usuario usuario) {
        logger.info("begin addUsuario");
        Mono<Usuario> newUsuario = usuarioService.addUsuario(usuario);
        logger.info("end addUsuario");
        return ResponseEntity.ok().body(newUsuario);
    }

    @PutMapping("/usuario/{id}")
    public ResponseEntity<Mono<Usuario>> modifyUsuario(@RequestBody Usuario usuario, @PathVariable String id) throws UsuarioNoEncontradoException {
        logger.info("begin modifyUsuario");
        Mono<Usuario> newUsuario = usuarioService.modifyUsuario(id, usuario);
        logger.info("end modifyUsuario");
        return ResponseEntity.ok(newUsuario);
    }

    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<RespuestaError> handleUserNotFoundException(UsuarioNoEncontradoException unee) {
        RespuestaError errorResponse = RespuestaError.generalError(404, unee.getMessage());
        logger.info("404: Usuario no encontrado");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<RespuestaError> handleException(Exception exception) {
        RespuestaError errorResponse = RespuestaError.generalError(999, "Internal server error");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespuestaError> handleException(MethodArgumentNotValidException manve) {
        Map<String, String> errors = new HashMap<>();
        manve.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return ResponseEntity.badRequest().body(RespuestaError.validationError(errors));
    }


}
