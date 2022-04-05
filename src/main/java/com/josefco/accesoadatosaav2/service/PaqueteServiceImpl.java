package com.josefco.accesoadatosaav2.service;


import com.josefco.accesoadatosaav2.domain.Conductor;
import com.josefco.accesoadatosaav2.domain.Paquete;
import com.josefco.accesoadatosaav2.domain.Usuario;
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
    public Mono<Paquete> findPaquete(String id) throws PaqueteNoEncontradoException {
        return paqueteRepository.findById(id).onErrorReturn(new Paquete());
    }

    @Override
    public Mono<Paquete> addPaquete(PaquetDTO paquetDTO) throws Exception {

        Mono<Usuario> usuario = usuarioRepository.findById(paquetDTO.getUsuario()).onErrorReturn(new Usuario());
        Mono<Conductor> conductor = conductorRepository.findById(paquetDTO.getConductor()).onErrorReturn(new Conductor());

        ModelMapper mapper = new ModelMapper();
        Paquete paquete = mapper.map(paquetDTO, Paquete.class);
        paquete.setUsuario(usuario.block());
        paquete.setConductor(conductor.block());
        return paqueteRepository.save(paquete);
    }


    @Override
    public Mono<Paquete> deletePaquete(String id) throws PaqueteNoEncontradoException {
        Mono<Paquete> paquete = paqueteRepository.findById(id).onErrorReturn(new Paquete());
        paqueteRepository.delete(paquete.block());
        return paquete;
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

//    @Override
//    public int countPaquete() {
//        return paqueteRepository.countPaquetes();
//    }
//
//    @Override
//    public Flux<Paquete> getPaqueteExtraPriceByPeso(int peso) {
//        return paqueteRepository.getPaqueteExtraPriceByPeso(peso);
//    }
//
//    @Override
//    public Flux<Paquete> getPaquetesFilter(int ancho, int alto, int largo) throws PaqueteNoEncontradoException {
//        return paqueteRepository.getPaquetesFilter(ancho, alto, largo);
//    }
//
//    @Override
//    public RutaDTO rutaPaquete(Paquete paquete) throws PaqueteNoEncontradoException {
//        return paqueteRepository.rutaPaquete(paquete.getId());
//    }

//    @Override
//    public RutaDTO rutaPaquete(int idPaquete) throws PaqueteNoEncontradoException {
//
//        return paqueteRepository.rutaPaquete(idPaquete);
//    }


}