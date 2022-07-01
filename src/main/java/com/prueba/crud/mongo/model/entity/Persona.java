package com.prueba.crud.mongo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "personas")
public class Persona {

    @Id
    private String id;
    private String username;
    private String nombres;
    private String email;
    private Integer edad;
    private Genero genero;
    private String foto;
}
