package com.josefco.accesoadatosaav2.controller;

import com.josefco.accesoadatosaav2.domain.Camion;
import com.josefco.accesoadatosaav2.domain.Conductor;
import com.josefco.accesoadatosaav2.domain.dto.AsignacionDTO;
import com.josefco.accesoadatosaav2.exception.*;
import com.josefco.accesoadatosaav2.service.CamionService;
import com.josefco.accesoadatosaav2.service.ConductorService;
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
public class ConductorController {

    private final Logger logger = LoggerFactory.getLogger(ConductorController.class);

    @Autowired
    private ConductorService conductorService;

    @Autowired
    private CamionService camionService;

    @GetMapping("/conductores")
    public ResponseEntity<Flux<Conductor>> findAllConductores() {
        logger.info("begin findAllConductores");
        Flux<Conductor> conductores;
        conductores = conductorService.findAllConductores();
        logger.info("end findAllConductores");
        return ResponseEntity.ok().body(conductores);
    }

    @GetMapping("/conductor/{id}")
    public ResponseEntity<Mono<Conductor>> findConductor(@PathVariable String id) throws ConductorNoEncontradoException {
        logger.info("begin findConductor by id: "+id);
        Mono<Conductor> conductor = conductorService.findConductor(id);
        logger.info("end findConductor by id: "+id);
        return ResponseEntity.ok().body(conductor);
    }


    @DeleteMapping("/conductor/{id}")
    public ResponseEntity<Mono<Void>> removeConductor(@PathVariable String id) throws ConductorNoEncontradoException {
        logger.info("begin removeConductor");
        logger.info("end removeConductor");
        return ResponseEntity.ok().body(conductorService.deleteConductor(id));
    }

    @PostMapping("/conductores")
    public ResponseEntity<Mono<Conductor>> saveCondutor(@RequestBody Conductor conductor) {
        logger.info("begin saveCondutor");
        Mono<Conductor> newConductor = conductorService.saveConductor(conductor);
        logger.info("end saveCondutor");
        return ResponseEntity.ok().body(newConductor);
    }

    @PutMapping("/conductor/{id}")
    public ResponseEntity<Mono<Conductor>> modifyConductor(@RequestBody Conductor Conductor, @PathVariable String id) throws ConductorNoEncontradoException {
        logger.info("begin modifyConductor id: "+id);
        Mono<Conductor> newConductor = conductorService.modifyConductor(id, Conductor);
        logger.info("begin modifyConductor id: "+ id);
        return ResponseEntity.ok().body(newConductor);
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
    public ResponseEntity<RespuestaError> handleConductorNotFoundException(ConductorNoEncontradoException cnee) {
        RespuestaError errorResponse = RespuestaError.generalError(404, cnee.getMessage());
        logger.info("404: Conductor no encontrado");
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
