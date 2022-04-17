package com.josefco.accesoadatosaav2.controller;


import com.josefco.accesoadatosaav2.domain.Camion;
import com.josefco.accesoadatosaav2.exception.CamionNoEncontradoException;
import com.josefco.accesoadatosaav2.exception.RespuestaError;
import com.josefco.accesoadatosaav2.service.CamionService;
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
public class CamionController {

    private final Logger logger = LoggerFactory.getLogger(CamionController.class);

    @Autowired
    private CamionService camionService;

    @GetMapping("/camiones")
    public ResponseEntity<Flux<Camion>> findAllCamiones() {
        logger.info("begin findAllCamiones");
        Flux<Camion> camiones;
        camiones = camionService.findAllCamiones();
        logger.info("End findAllCamiones");
        return ResponseEntity.ok().body(camiones);
    }

    @GetMapping("/camion/{id}")
    public ResponseEntity<Mono<Camion>> findCamion(@PathVariable String id) throws CamionNoEncontradoException {
        logger.info("begin findCamion by id " + id);
        Mono<Camion> Camion = camionService.findCamion(id);
        logger.info("end findCamion by id" + id);
        return ResponseEntity.ok().body(Camion);
    }

    @DeleteMapping("/camion/{id}")
    public ResponseEntity<Mono<Camion>> removeCamion(@PathVariable String id) throws CamionNoEncontradoException {
        logger.info("begin removeCamion by id " + id);
        Mono<Camion> Camion = camionService.deleteCamion(id);
        logger.info("end removeCamion by id " + id);
        return ResponseEntity.ok().body(Camion);
    }

    @PutMapping("/camion/{id}")
    public ResponseEntity<Mono<Camion>> modifyCamion(@RequestBody Camion Camion, @PathVariable String id) throws CamionNoEncontradoException {
        logger.info("begin modifyCamion by id " + id);
        Mono<Camion> newCamion = camionService.modifyCamion(id, Camion);
        logger.info("end modifyCamion by id " + id);
        return ResponseEntity.ok().body(newCamion);
    }

    @PostMapping("/camiones")
    public ResponseEntity<Mono<Camion>> saveCamion(@Valid @RequestBody Camion camion) {
        logger.info("begin saveCamion");
        Mono<Camion> newCamion = camionService.saveCamion(camion);
        logger.info("end saveCamion");
        return ResponseEntity.ok().body(newCamion);
    }

    @RequestMapping(value = "/camiones/marca/{marca}")
    public ResponseEntity<Flux<Camion>> findCamionesByMarca(@PathVariable String marca) throws CamionNoEncontradoException {
        logger.info("begin findCamionesByMarca");
        Flux<Camion> camiones = camionService.findCamionesByMarca(marca);
        logger.info("end findCamionesByMarca");
        return ResponseEntity.ok().body(camiones);
    }

    @ExceptionHandler(CamionNoEncontradoException.class)
    public ResponseEntity<RespuestaError> handleCamionNotFoundException(CamionNoEncontradoException cnee) {
        RespuestaError errorResponse = RespuestaError.generalError(404, cnee.getMessage());
        logger.info("404: Camion no encontrado");
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
