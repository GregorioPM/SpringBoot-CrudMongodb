package com.prueba.crud.mongo.controller;

import com.prueba.crud.mongo.model.dto.PersonaDto;
import com.prueba.crud.mongo.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/persona")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping({"/",""})
    private ResponseEntity<?> getAll(){
        return ResponseEntity.ok().body(personaService.findAll());
    }

    @GetMapping("/{username}")
    private ResponseEntity<?> getById(@PathVariable("username") String username){
        return ResponseEntity.ok(personaService.findByUsername(username));
    }

    @PostMapping
    private ResponseEntity<?> savePersona(@Valid @RequestBody PersonaDto personaDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(personaService.save(personaDto));
    }

    @PutMapping("/{username}")
    private ResponseEntity<?> updatePersona(@Valid @PathVariable("username") String username,@RequestBody PersonaDto personaDto){
        return ResponseEntity.ok(personaService.update(username,personaDto));
    }

    @DeleteMapping("/{username}")
    private ResponseEntity<?> deleteByUsername(@PathVariable("username") String username){
        personaService.deleteByUsername(username);
        return ResponseEntity.noContent().build();
    }
}
