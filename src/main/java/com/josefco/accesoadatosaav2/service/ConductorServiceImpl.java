package com.josefco.accesoadatosaav2.service;

import com.josefco.accesoadatosaav2.domain.Camion;
import com.josefco.accesoadatosaav2.domain.Conductor;
import com.josefco.accesoadatosaav2.exception.ConductorNoEncontradoException;
import com.josefco.accesoadatosaav2.repository.CamionRepository;
import com.josefco.accesoadatosaav2.repository.ConductorRepository;
import com.josefco.accesoadatosaav2.repository.ConductorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ConductorServiceImpl implements ConductorService {

    @Autowired
    private ConductorRepository conductorRepository;
    @Autowired
    private CamionRepository camionRepository;

    @Override
    public Flux<Conductor> findAllConductores() {
        return conductorRepository.findAll();
    }

    @Override
    public Mono<Conductor> findConductor(String id) throws ConductorNoEncontradoException {
        return conductorRepository.findById(id).onErrorReturn(new Conductor());
    }


    @Override
    public Mono<Conductor> deleteConductor(String id) throws ConductorNoEncontradoException {
        Mono<Conductor> conductor = conductorRepository.findById(id).onErrorReturn(new Conductor());
        conductorRepository.delete(conductor.block());
        return conductor;
    }

    @Override
    public Mono<Conductor> modifyConductor(String id, Conductor newConductor) throws ConductorNoEncontradoException {
        Mono<Conductor> monoConductor = conductorRepository.findById(id).onErrorReturn(new Conductor());
        Conductor conductor = monoConductor.block();
        conductor.setNombre(newConductor.getNombre());
        conductor.setApellido(newConductor.getApellido());
        conductor.setTelefono(newConductor.getTelefono());
        conductor.setDireccion(newConductor.getDireccion());
        return conductorRepository.save(conductor);
    }

    @Override
    public Flux<Conductor> findConductorByDireccion(String direccion){
        return conductorRepository.findConductorByDireccion(direccion);
    }

    @Override
    public Mono<Conductor> saveConductor(Conductor conductor) {
        Conductor newConductor = new Conductor();
        newConductor.setNombre(conductor.getNombre());
        newConductor.setApellido(conductor.getApellido());
        newConductor.setTelefono(conductor.getTelefono());
        newConductor.setDireccion(conductor.getDireccion());
        return conductorRepository.save(newConductor);
    }

    @Override
    public void addAsignacion(Conductor conductor, Camion camion) {
        conductor.getCamiones().add(camion);
        camion.getConductores().add(conductor);
        camionRepository.save(camion);
        conductorRepository.save(conductor);
    }

}
