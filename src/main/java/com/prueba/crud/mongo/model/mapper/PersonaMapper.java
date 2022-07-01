package com.prueba.crud.mongo.model.mapper;

import com.prueba.crud.mongo.model.dto.PersonaDto;
import com.prueba.crud.mongo.model.dto.PersonaDtoResponse;
import com.prueba.crud.mongo.model.entity.Persona;
import org.springframework.stereotype.Component;

@Component
public class PersonaMapper {

    public Persona toPersona(PersonaDto personaDto){
        return Persona.builder()
                .username(personaDto.getUsername())
                .foto(personaDto.getFoto())
                .nombres(personaDto.getNombres())
                .email(personaDto.getEmail())
                .edad(personaDto.getEdad())
                .genero(personaDto.getGenero())
                .build();
    }

    public PersonaDtoResponse toPersonaDtoResponse(Persona persona){
        return PersonaDtoResponse.builder()
                .id(persona.getId())
                .username(persona.getUsername())
                .nombres(persona.getNombres())
                .email(persona.getEmail())
                .edad(persona.getEdad())
                .genero(persona.getGenero())
                .foto(persona.getFoto())
                .build();
    }

}
