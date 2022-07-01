package com.prueba.crud.mongo.model.dto;

import com.prueba.crud.mongo.model.entity.Genero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDtoResponse {

    private String id;
    private String username;
    private String nombres;
    private String email;
    private Integer edad;
    private Genero genero;
    private String foto;
}
