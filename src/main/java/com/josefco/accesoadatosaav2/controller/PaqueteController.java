package com.josefco.accesoadatosaav2.controller;

import com.josefco.accesoadatosaav2.domain.Paquete;
import com.josefco.accesoadatosaav2.domain.dto.PaquetDTO;
import com.josefco.accesoadatosaav2.exception.PaqueteNoEncontradoException;
import com.josefco.accesoadatosaav2.exception.RespuestaError;
import com.josefco.accesoadatosaav2.service.PaqueteService;
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

import java.util.HashMap;
import java.util.Map;

@RestController
public class PaqueteController {

    private final Logger logger = LoggerFactory.getLogger(PaqueteController.class);


    @Autowired
    private PaqueteService paqueteService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/paquetes")
    public ResponseEntity<Flux<Paquete>> findAllPaquetees() {
        logger.info("begin findAllPaquetees");
        Flux<Paquete> paquetes;
        paquetes = paqueteService.findAllPaquetes();
        logger.info("end findAllPaquetees");
        return ResponseEntity.ok().body(paquetes);
    }

    //Filtro de un parametro
    @RequestMapping("/paquetes/color/{color}")
    public ResponseEntity<Flux<Paquete>> findPaqueteByColor(@PathVariable String color) throws PaqueteNoEncontradoException {
        logger.info("begin findPaqueteByColor");
        Flux<Paquete> paquetes = paqueteService.findPaqueteByColor(color);
        logger.info("end findPaqueteByColor");
        return ResponseEntity.ok().body(paquetes);
    }

    @GetMapping("/paquete/{id}")
    public ResponseEntity<Mono<Paquete>> findPaquete(@PathVariable String id) throws PaqueteNoEncontradoException {
        logger.info("begin findPaquete by id " + id);
        Mono<Paquete> Paquete = paqueteService.findPaquete(id);
        logger.info("end findPaquete by id " + id);
        return ResponseEntity.ok().body(Paquete);
    }


    @DeleteMapping("/paquete/{id}")
    public ResponseEntity<Mono<Void>> removePaquete(@PathVariable String id) throws PaqueteNoEncontradoException {
        logger.info("begin removePaquete");
        logger.info("end removePaquete");
        return ResponseEntity.ok().body(paqueteService.deletePaquete(id));
    }


    @PostMapping("/paquete")
    public ResponseEntity<Mono<Paquete>> addPaquete(@RequestBody PaquetDTO paquetDTO) throws Exception {
        logger.info("begin addPaquete");
        Mono<Paquete> paquete = paqueteService.addPaquete(paquetDTO);
        logger.info("end addPaquete");
        return ResponseEntity.ok().body(paquete);
    }




    @PutMapping("/paquete/{id}")
    public ResponseEntity<Mono<Paquete>> modifyPaquete(@RequestBody Paquete Paquete, @PathVariable String id) throws PaqueteNoEncontradoException {
        logger.info("begin modifyPaquete by id" + id);
        Mono<Paquete> newPaquete = paqueteService.modifyPaquete(id, Paquete);
        logger.info("end modifyPaquete by id " + id);
        return ResponseEntity.ok().body(newPaquete);
    }


    //    Codigo para controlar las excepciones de los paquetes
    @ExceptionHandler(PaqueteNoEncontradoException.class)
    public ResponseEntity<RespuestaError> handlePaqueteNotFoundException(PaqueteNoEncontradoException pnee) {
        RespuestaError errorResponse = RespuestaError.generalError(404, pnee.getMessage());
        logger.info("404: Paquete no encontrado");
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
