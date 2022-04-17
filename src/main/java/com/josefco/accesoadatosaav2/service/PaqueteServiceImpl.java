package com.josefco.accesoadatosaav2.service;


import com.josefco.accesoadatosaav2.domain.Paquete;
import com.josefco.accesoadatosaav2.domain.dto.PaquetDTO;
import com.josefco.accesoadatosaav2.exception.PaqueteNoEncontradoException;
import com.josefco.accesoadatosaav2.repository.ConductorRepository;
import com.josefco.accesoadatosaav2.repository.PaqueteRepository;
import com.josefco.accesoadatosaav2.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PaqueteServiceImpl implements PaqueteService {


    @Autowired
    private PaqueteRepository paqueteRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ConductorRepository conductorRepository;

    @Override
    public Flux<Paquete> findAllPaquetes() {
        return paqueteRepository.findAll();
    }

    @Override
    public Mono<Paquete> findPaquete(String id){
        Mono<Paquete> fallback = Mono.error(PaqueteNoEncontradoException::new);
        return paqueteRepository.findById(id).switchIfEmpty(fallback);
    }

    @Override
    public Mono<Paquete> addPaquete(PaquetDTO paquetDTO) throws Exception {
//        DATOS PARA GUARDAR CON PAQUETEDTO TODO
//        Mono<Usuario> usuario = usuarioRepository.findById(paquetDTO.getUsuario()).onErrorReturn(new Usuario());
//        Mono<Conductor> conductor = conductorRepository.findById(paquetDTO.getConductor()).onErrorReturn(new Conductor());
//
//        ModelMapper mapper = new ModelMapper();
//        Paquete paquete = mapper.map(paquetDTO, Paquete.class);
//        paquete.setUsuario(usuario.block());
//        paquete.setConductor(conductor.block());
//        return paqueteRepository.save(paquete);
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        String text = date.format(formatters);
        LocalDate parsedDate = LocalDate.parse(text, formatters);

        Paquete newpaquete = new Paquete();
        newpaquete.setAncho(paquetDTO.getAncho());
        newpaquete.setLargo(paquetDTO.getLargo());
        newpaquete.setAlto(paquetDTO.getAlto());
        newpaquete.setPeso(paquetDTO.getPeso());
        newpaquete.setColor(paquetDTO.getColor());
        newpaquete.setFecha(parsedDate);
        return paqueteRepository.save(newpaquete);
    }

    @Override
    public Mono<Void> deletePaquete(String id){
        Mono<Paquete> fallback = Mono.error(new PaqueteNoEncontradoException());
        paqueteRepository.findById(id).switchIfEmpty(fallback);
        return paqueteRepository.deleteById(id);
    }

    @Override
    public Mono<Paquete> modifyPaquete(String id, Paquete newPaquete) throws PaqueteNoEncontradoException {
        Mono<Paquete> monoPaquete = paqueteRepository.findById(id).onErrorReturn(new Paquete());
        Paquete paquete = monoPaquete.block();

        paquete.setAlto(newPaquete.getAlto());
        paquete.setAncho(newPaquete.getAncho());
        paquete.setLargo(newPaquete.getLargo());
        paquete.setColor(newPaquete.getColor());
        paquete.setPeso(newPaquete.getPeso());
        paquete.setConductor(newPaquete.getConductor());
        paquete.setUsuario(newPaquete.getUsuario());

        return paqueteRepository.save(paquete);
    }

    @Override
    public Flux<Paquete> findPaqueteByColor(String color) throws PaqueteNoEncontradoException{
        return paqueteRepository.findPaqueteByColor(color) ;
    }



}