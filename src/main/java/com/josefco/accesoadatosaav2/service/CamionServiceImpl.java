package com.josefco.accesoadatosaav2.service;


import com.josefco.accesoadatosaav2.domain.Camion;
import com.josefco.accesoadatosaav2.exception.CamionNoEncontradoException;
import com.josefco.accesoadatosaav2.repository.CamionRepository;
import com.josefco.accesoadatosaav2.repository.ConductorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CamionServiceImpl implements CamionService {

    @Autowired
    private CamionRepository camionRepository;
    @Autowired
    private ConductorRepository conductorRepository;

    @Override
    public Flux<Camion> findAllCamiones() {
        return camionRepository.findAll();
    }


    @Override
    public Mono<Camion> findCamion(String id) throws CamionNoEncontradoException {
            return camionRepository.findById(id).onErrorReturn(new Camion());
    }


    @Override
    public Mono<Camion> deleteCamion(String id) throws CamionNoEncontradoException {
        Mono<Camion> camion = camionRepository.findById(id).onErrorReturn(new Camion());
        camionRepository.delete(camion.block());
        return camion;
    }

    @Override
    public Mono<Camion> modifyCamion(String id, Camion newCamion) throws CamionNoEncontradoException {
        Mono<Camion> monoCamion = camionRepository.findById(id).onErrorReturn(new Camion());
        Camion camion = monoCamion.block();
        camion.setGasolina(newCamion.getGasolina());
        camion.setMarca(newCamion.getMarca());
        camion.setModelo(newCamion.getModelo());
        camion.setMatricula(newCamion.getMatricula());
        camion.setConductores(newCamion.getConductores());

        return camionRepository.save(camion);
    }

    @Override
    public Mono<Camion> saveCamion(Camion camion) {
        Camion newCamion = new Camion();
        newCamion.setGasolina(camion.getGasolina());
        newCamion.setMarca(camion.getMarca());
        newCamion.setMatricula(camion.getMatricula());
        newCamion.setModelo(camion.getModelo());
        //newCamion.setConductores(camion.getConductores());
        return camionRepository.save(newCamion);
    }

    @Override
    public Flux<Camion> findCamionesByMarca(String marca) throws CamionNoEncontradoException {
        return camionRepository.findCamionesByMarca(marca);
    }

//    @Override
//    public int countCamiones() {
//        return camionRepository.countCamiones();
//    }
}
