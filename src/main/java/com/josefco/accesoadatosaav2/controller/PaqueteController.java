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
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class PaqueteController {

    private final Logger logger = LoggerFactory.getLogger(PaqueteController.class);


    @Autowired
    private PaqueteService paqueteService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/paquetes")
    public Flux<Paquete> findAllPaquetees() {
        logger.info("begin findAllPaquetees");
        Flux<Paquete> paquetes;
        paquetes = paqueteService.findAllPaquetes();
        logger.info("end findAllPaquetees");
        return paquetes;
    }

    //Filtro de un parametro
    @RequestMapping("/paquetes/color/{color}")
    public Flux<Paquete> findPaqueteByColor(@PathVariable String color) throws PaqueteNoEncontradoException {
        logger.info("begin findPaqueteByColor");
        Flux<Paquete> paquetes = paqueteService.findPaqueteByColor(color);
        logger.info("end findPaqueteByColor");
        return paquetes;
    }

    @GetMapping("/paquete/{id}")
    public Mono<Paquete> findPaquete(@PathVariable String id) throws PaqueteNoEncontradoException {
        logger.info("begin findPaquete by id "+id);
        Mono<Paquete> Paquete = paqueteService.findPaquete(id);
        logger.info("end findPaquete by id " + id);
        return Paquete;
    }


    @DeleteMapping("/paquete/{id}")
    public Mono<Paquete> removePaquete(@PathVariable String id) throws PaqueteNoEncontradoException {
        logger.info("begin removePaquete by id "+ id);
        Mono<Paquete> Paquete = paqueteService.deletePaquete(id);
        logger.info("end removePaquete by id "+ id);
        return Paquete;
    }



    @PostMapping("/paquete")
    public Mono<Paquete> addPaquete(@RequestBody PaquetDTO paquetDTO) throws Exception {
        logger.info("begin addPaquete");
        Mono<Paquete> paquete = paqueteService.addPaquete(paquetDTO);
        logger.info("end addPaquete");
        return paquete;
    }


//    @PutMapping("/paquete/{id}/ruta")
//    public RutaDTO rutaPaquete(@PathVariable int id) throws PaqueteNoEncontradoException, UsuarioNoEncontradoException {
//        logger.info("begin modifyPaquete by id" + id);
//        Paquete paquete = paqueteService.findPaquete(id);
//        RutaDTO rutaDTO = new RutaDTO(paquete);
//        logger.info("end modifyPaquete by id " +id);
//        return rutaDTO;
//    }


    @PutMapping("/paquete/{id}")
    public Mono<Paquete> modifyPaquete(@RequestBody Paquete Paquete, @PathVariable String id) throws PaqueteNoEncontradoException {
        logger.info("begin modifyPaquete by id" + id);
        Mono<Paquete> newPaquete = paqueteService.modifyPaquete(id, Paquete);
        logger.info("end modifyPaquete by id " +id);
        return newPaquete;
    }

//    @GetMapping("/paquetes/contador")
//    public int countPaquete(){
//        logger.info("start countPaquete");
//        int contador = paqueteService.countPaquete();
//        logger.info("end countPaquete");
//        return contador;
//    }
//
//
//    @GetMapping("/paquete_extra_price_weight")
//    public Flux<Paquete> getPaqueteExtraPriceByPeso(int peso){
//        logger.info("start getPaqueteExtraPriceByPeso");
//        Flux<Paquete> paquetes = paqueteService.getPaqueteExtraPriceByPeso(peso);
//        logger.info("end getPaqueteExtraPriceByPeso");
//        return paquetes;
//    }
//
//    @GetMapping("/paquete/filter")
//    public Flux<Paquete> getPaquetesFilter(int ancho, int alto, int largo) throws PaqueteNoEncontradoException{
//        logger.info("start getPaquetesFilter");
//        Flux<Paquete> paquetes = paqueteService.getPaquetesFilter(ancho, alto, largo);
//        logger.info("end getPaquetesFilter");
//        return paquetes;
//    }



    //Codigo para controlar las excepciones de los paquetes
    @ExceptionHandler(PaqueteNoEncontradoException.class)
    public ResponseEntity<RespuestaError> handlePaqueteNoEncontradoException(PaqueteNoEncontradoException pnee) {
        RespuestaError errorResponse = new RespuestaError("1", pnee.getMessage());
        logger.info(pnee.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // TODO Más tipos de excepciones que puedan generar errores

    @ExceptionHandler
    public ResponseEntity<RespuestaError> handleException(Exception exception) {
        RespuestaError errorResponse = new RespuestaError("999", "Internal server error");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
