package com.prueba.crud.mongo.exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MensajeErroresValidation {

    private List<String> errores;

    private String excepcion;

    private String url;

    private String operacion;

    private int status;

}
