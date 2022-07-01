package com.prueba.crud.mongo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ManejadorException {

    private static final Map<String, Integer> STATUS = new HashMap<>();


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MensajeErroresValidation> handleInvalidArgument(MethodArgumentNotValidException ex,HttpServletRequest request) {
        ResponseEntity<MensajeErroresValidation> resultado;

        List<FieldError> errorListado= ex.getBindingResult().getFieldErrors();
        List<String> erroresPorField = new ArrayList<>();

        for (int i = 0; i<errorListado.size();i++){
            FieldError errorCampo = errorListado.get(i);
            String mensaje = errorCampo.getField()+ ": " + errorCampo.getDefaultMessage();
            System.out.println(mensaje);
            erroresPorField.add(mensaje);

        }
        MensajeErroresValidation mensajeErroresValidation = MensajeErroresValidation.builder()
                .errores(erroresPorField)
                .excepcion(ex.getClass().getSimpleName())
                .url(request.getRequestURI())
                .operacion(request.getMethod().toString())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        resultado = new ResponseEntity(mensajeErroresValidation, HttpStatus.valueOf(HttpStatus.BAD_REQUEST.value()));
        return resultado;
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<MensajeError> AllExceptions(HttpServletRequest request, Exception exception) {
        ResponseEntity<MensajeError> resultado;

        String excepcion = exception.getClass().getSimpleName();
        String mensaje = exception.getMessage();
        Integer codigo = getStatus(exception);

        if (codigo == null) {
            codigo = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }

        MensajeError error = new MensajeError();
        error.setMensaje(mensaje);
        error.setExcepcion(excepcion);
        error.setUrl(request.getRequestURI());
        error.setStatus(codigo);
        error.setOperacion(request.getMethod());
        resultado = new ResponseEntity<>(error, HttpStatus.valueOf(codigo));
        exception.printStackTrace();
        return resultado;
    }

    private Integer getStatus(Exception e) {
        if (e instanceof GenericException) {
            GenericException ex = (GenericException) e;
            if (ex.getHttpStatus() != null) {
                return ex.getHttpStatus().value();
            }
        }
        return STATUS.get(e.getClass().getSimpleName());
    }



}
