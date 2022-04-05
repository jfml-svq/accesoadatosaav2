package com.josefco.accesoadatosaav2.controller;

import com.josefco.accesoadatosaav2.domain.Camion;
import com.josefco.accesoadatosaav2.domain.Conductor;
import com.josefco.accesoadatosaav2.domain.dto.AsignacionDTO;
import com.josefco.accesoadatosaav2.exception.CamionNoEncontradoException;
import com.josefco.accesoadatosaav2.exception.ConductorNoEncontradoException;
import com.josefco.accesoadatosaav2.exception.Respuesta;
import com.josefco.accesoadatosaav2.exception.RespuestaError;
import com.josefco.accesoadatosaav2.service.CamionService;
import com.josefco.accesoadatosaav2.service.ConductorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ConductorController {

    private final Logger logger = LoggerFactory.getLogger(ConductorController.class);

    @Autowired
    private ConductorService conductorService;

    @Autowired
    private CamionService camionService;

    @GetMapping("/conductores")
    public Flux<Conductor> findAllConductores() {
        logger.info("begin findAllConductores");
        Flux<Conductor> conductores;
        conductores = conductorService.findAllConductores();
        logger.info("end findAllConductores");
        return conductores;
    }

    @GetMapping("/conductor/{id}")
    public Mono<Conductor> findConductor(@PathVariable String id) throws ConductorNoEncontradoException {
        logger.info("begin findConductor by id: "+id);
        Mono<Conductor> conductor = conductorService.findConductor(id);
        logger.info("end findConductor by id: "+id);
        return conductor;
    }

    @DeleteMapping("/conductor/{id}")
    public Mono<Conductor> removeConductor(@PathVariable String id) throws ConductorNoEncontradoException {
        logger.info("begin removeConductor by id: "+id);
        Mono<Conductor> Conductor = conductorService.deleteConductor(id);
        logger.info("end removeConductor by id: "+id);
        return Conductor;
    }

    @PostMapping("/conductores")
    public Mono<Conductor> saveCondutor(@RequestBody Conductor conductor) {
        logger.info("begin saveCondutor");
        Mono<Conductor> newConductor = conductorService.saveConductor(conductor);
        logger.info("end saveCondutor");
        return newConductor;
    }

    @PutMapping("/conductor/{id}")
    public Mono<Conductor> modifyConductor(@RequestBody Conductor Conductor, @PathVariable String id) throws ConductorNoEncontradoException {
        logger.info("begin modifyConductor id: "+id);
        Mono<Conductor> newConductor = conductorService.modifyConductor(id, Conductor);
        logger.info("begin modifyConductor id: "+ id);
        return newConductor;
    }


    @RequestMapping(value = "/conductores/direccion/{direccion}")
    public ResponseEntity<?> findConductorByDireccion(@PathVariable String direccion) throws ConductorNoEncontradoException{
        logger.info("begin findConductorByDireccion");
        if (direccion.equals("a")){
            return ResponseEntity.badRequest().body("Error");
        }else{
            Flux<Conductor> conductores = conductorService.findConductorByDireccion(direccion);
            logger.info("end findConductorByDireccion");
            return ResponseEntity.ok().body(conductores);
        }
    }

    @PostMapping("/asignacion")
    public ResponseEntity<Respuesta> asignacion(@RequestBody AsignacionDTO asignacionDTO)
            throws CamionNoEncontradoException, ConductorNoEncontradoException {
        Mono<Camion> camion = camionService.findCamion(asignacionDTO.getIdCamion());
        Mono<Conductor> conductor = conductorService.findConductor(asignacionDTO.getIdConductor());
        conductorService.addAsignacion(conductor.block(), camion.block());

        Respuesta respuesta = new Respuesta("105", "Asignacion definida: Conductor id: "
                                            + conductor.block().getId() + " + con camion " + camion.block().getMatricula());
        return ResponseEntity.ok(respuesta);
    }

    @ExceptionHandler(ConductorNoEncontradoException.class)
    public ResponseEntity<RespuestaError> handleConductorNoEncontradoException(ConductorNoEncontradoException cnee) {
        RespuestaError errorResponse = new RespuestaError("1", cnee.getMessage());
        logger.error(cnee.getMessage(), cnee);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // TODO Más tipos de excepciones que puedan generar errores

    @ExceptionHandler
    public ResponseEntity<RespuestaError> handleException(Exception exception) {
        RespuestaError errorResponse = new RespuestaError("999", "Internal server error");
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
