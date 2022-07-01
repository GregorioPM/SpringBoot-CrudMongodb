package com.prueba.crud.mongo.repository;

import com.prueba.crud.mongo.model.entity.Persona;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PersonaRepository extends MongoRepository<Persona, String> {

    Optional<Persona> findByEmail(String email);
    Optional<Persona> findByUsername(String username);
}
