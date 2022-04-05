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
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
public class CamionController {

    private final Logger logger = LoggerFactory.getLogger(CamionController.class);

    @Autowired
    private CamionService camionService;

    @GetMapping("/camiones")
    public Flux<Camion> findAllCamiones() {
        logger.info("begin findAllCamiones");
        Flux<Camion> camiones;
        camiones = camionService.findAllCamiones();
        logger.info("End findAllCamiones");
        return camiones;
    }

    @GetMapping("/camion/{id}")
    public Mono<Camion> findCamion(@PathVariable String id) throws CamionNoEncontradoException {
        logger.info("begin findCamion by id " + id);
        Mono<Camion> Camion = camionService.findCamion(id);
        logger.info("end findCamion by id" + id);
        return Camion;
    }

    @DeleteMapping("/camion/{id}")
    public Mono<Camion> removeCamion(@PathVariable String id) throws CamionNoEncontradoException {
        logger.info("begin removeCamion by id " + id);
        Mono<Camion> Camion = camionService.deleteCamion(id);
        logger.info("end removeCamion by id " + id);
        return Camion;
    }

    @PutMapping("/camion/{id}")
    public Mono<Camion> modifyCamion(@RequestBody Camion Camion, @PathVariable String id) throws CamionNoEncontradoException {
        logger.info("begin modifyCamion by id " + id);
        Mono<Camion> newCamion = camionService.modifyCamion(id, Camion);
        logger.info("end modifyCamion by id " + id);
        return newCamion;
    }

    @PostMapping("/camiones")
    public Mono<Camion> saveCamion(@RequestBody Camion camion) {
        logger.info("begin saveCamion");
        Mono<Camion> newCamion = camionService.saveCamion(camion);
        logger.info("end saveCamion");
        return newCamion;
    }

    @RequestMapping(value = "/camiones/marca/{marca}")
    public Flux<Camion> findCamionesByMarca(@PathVariable String marca) throws CamionNoEncontradoException {
        logger.info("begin findCamionesByMarca");
        Flux<Camion> camiones = camionService.findCamionesByMarca(marca);
        logger.info("end findCamionesByMarca");
        return camiones;
    }

//    @GetMapping("camiones/contador")
//    public int countCamiones(){
//        logger.info("start countCamiones");
//        int contador = camionService.countCamiones();
//        logger.info("end countCamiones");
//        return contador;
//    }



    @ExceptionHandler(CamionNoEncontradoException.class)
    public ResponseEntity<RespuestaError> handleCamionNoEncontradoException(CamionNoEncontradoException cnee) {
        RespuestaError errorResponse = new RespuestaError("1", cnee.getMessage());
        logger.info(cnee.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // TODO Más tipos de excepciones que puedan generar errores

    @ExceptionHandler
    public ResponseEntity<RespuestaError> handleException(Exception exception) {
        RespuestaError errorResponse = new RespuestaError("999", "Internal server error");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
