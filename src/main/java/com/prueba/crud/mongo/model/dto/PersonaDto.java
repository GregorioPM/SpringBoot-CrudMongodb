package com.prueba.crud.mongo.model.dto;

import com.prueba.crud.mongo.model.entity.Genero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDto {

    @NotEmpty(message = "El nombre de usuario no debe estar vacio")
    @Size(min = 4,max = 20,message = "El nombre de usuario debe tener como minimo 4 caracteres y 20 como maximo")
    private String username;
    private String nombres;

    @Email(message ="El email debe tener un formato estandar xxx@mail.com")
    @NotEmpty(message = "El email no debe estar vacio")
    private String email;

    @Min(value = 18,message = "El usuario debe tener mas de 18 años")
    private Integer edad;
    private Genero genero;
    private String foto;

}
