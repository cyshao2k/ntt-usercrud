package com.ntt.evaluation.user_manager.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.ntt.evaluation.user_manager.model.Error; // Su modelo Error de OpenAPI

@ControllerAdvice 
public class GlobalExceptionHandler {

    // 1. Maneja fallos de validación JSR-380 (@Valid, @Pattern, @Email)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleValidationExceptions(MethodArgumentNotValidException ex) {
        
        // Obtiene el primer mensaje de error detallado
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        
        // Crea el objeto Error (su DTO de error de OpenAPI)
        Error apiError = new Error();
        apiError.setMensaje("Error de Validación: " + errorMessage);
        
        // Devuelve el ResponseEntity<Error> con código 400
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
    
    // 2. Maneja excepciones lanzadas explícitamente con ResponseStatusException
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Error> handleResponseStatusException(ResponseStatusException ex) {
        
        Error apiError = new Error();
        apiError.setMensaje(ex.getReason());
        
        // Devuelve el ResponseEntity<Error> con el código de estado de la excepción (ej: 400, 404)
        return new ResponseEntity<>(apiError, ex.getStatusCode());
    }

//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ResponseEntity<Error> handleUsernameNotFoundxception(UsernameNotFoundException ex) {
        
//        Error apiError = new Error();
//        apiError.setMensaje(ex.getMessage());
        
        // Devuelve el ResponseEntity<Error> con el código de estado de la excepción (ej: 400, 404)
//        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
//    }

    // Opcional: Manejador genérico para errores no capturados (ej: 500 Internal Server Error)
    // ...
}