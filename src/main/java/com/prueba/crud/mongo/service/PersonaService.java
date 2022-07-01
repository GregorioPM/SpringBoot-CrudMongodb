package com.prueba.crud.mongo.service;

import com.prueba.crud.mongo.model.dto.PersonaDto;
import com.prueba.crud.mongo.model.dto.PersonaDtoResponse;

import java.util.List;

public interface PersonaService {

    List<PersonaDtoResponse> findAll();
    PersonaDtoResponse save(PersonaDto personaDto);
    PersonaDtoResponse findByUsername(String username);
    void deleteByUsername(String username);
    PersonaDtoResponse update(String username,PersonaDto personaDto);
}
