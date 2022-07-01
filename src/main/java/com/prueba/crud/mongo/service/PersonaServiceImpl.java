package com.prueba.crud.mongo.service;

import com.prueba.crud.mongo.exception.GenericException;
import com.prueba.crud.mongo.model.dto.PersonaDto;
import com.prueba.crud.mongo.model.dto.PersonaDtoResponse;
import com.prueba.crud.mongo.model.entity.Persona;
import com.prueba.crud.mongo.model.mapper.PersonaMapper;
import com.prueba.crud.mongo.repository.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonaServiceImpl implements PersonaService{

    private final PersonaRepository personaRepository;

    private final PersonaMapper personaMapper;

    @Override
    public List<PersonaDtoResponse> findAll() {
        try {
            List<Persona> personas= personaRepository.findAll();
            return personas.stream()
                    .map(persona ->
                        personaMapper.toPersonaDtoResponse(persona)
                    ).collect(Collectors.toList());

        }catch (HttpServerErrorException e){
            throw new GenericException(HttpStatus.INTERNAL_SERVER_ERROR,"Ha sucedido un error interno");
        }
    }

    @Override
    public PersonaDtoResponse save(PersonaDto personaDto) {
        this.consultarEmailAndUsername(personaDto.getUsername(),personaDto.getEmail());
        try {
            Persona persona = personaMapper.toPersona(personaDto);
            return personaMapper.toPersonaDtoResponse(personaRepository.save(persona));
        }catch (HttpServerErrorException e){
            throw new GenericException(HttpStatus.INTERNAL_SERVER_ERROR,"Ha sucedido un error interno");
        }
    }

    @Override
    public PersonaDtoResponse findByUsername(String username) {
        try {
            Persona persona  =personaRepository.findByUsername(username).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND,"No se encuentra al usuario"));
            return personaMapper.toPersonaDtoResponse(persona);
        }catch (HttpServerErrorException e){
            throw new GenericException(HttpStatus.INTERNAL_SERVER_ERROR,"Ha sucedido un error interno");
        }
    }

    @Override
    public void deleteByUsername(String username) {
        try {
            Persona persona  = personaRepository.findByUsername(username).orElseThrow(()-> new GenericException(HttpStatus.BAD_REQUEST,"El nombre de usuario no se encuentra"));
            personaRepository.deleteById(persona.getId());
        }catch (HttpClientErrorException e){
            throw new GenericException(HttpStatus.INTERNAL_SERVER_ERROR,"Ha sucedido un error interno");
        }

    }

    @Override
    public PersonaDtoResponse update(String username,PersonaDto personaDto) {
        try {
            Persona persona = personaRepository.findByUsername(username).orElseThrow(()->new GenericException(HttpStatus.NOT_FOUND,"La persona no se encuentra"));
            if (!persona.getUsername().equals(personaDto.getUsername())){
                this.consultarEmailAndUsername(personaDto.getUsername(),null);
            }
            if(!persona.getEmail().equals(personaDto.getEmail())){
                this.consultarEmailAndUsername(null, personaDto.getEmail());
            }
            persona.setNombres(personaDto.getNombres());
            persona.setEdad(personaDto.getEdad());
            persona.setEmail(personaDto.getEmail());
            persona.setUsername(personaDto.getUsername());
            persona.setFoto(personaDto.getFoto());
            persona.setGenero(personaDto.getGenero());
            return personaMapper.toPersonaDtoResponse(personaRepository.save(persona));
        }catch (HttpServerErrorException e){
            throw new GenericException(HttpStatus.INTERNAL_SERVER_ERROR,"Ha sucedido un error interno");
        }
    }

    public void consultarEmailAndUsername(String username,String email){
        Persona persona;
        if(username!=null){
            persona = personaRepository.findByUsername(username).orElse(null);
            if(persona!=null){
                throw new GenericException(HttpStatus.BAD_REQUEST,"El nombre de usuario ya se encuentra en uso");
            }
        }
        if(email!=null){
            persona = personaRepository.findByEmail(email).orElse(null);
            if(persona!=null){
                throw new GenericException(HttpStatus.BAD_REQUEST,"El email ya se encuentra en uso");
            }
        }
    }
}
