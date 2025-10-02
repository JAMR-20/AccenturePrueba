package com.Accenture.Accenture.infrastructure.web.exception;

import com.Accenture.Accenture.application.constants.ApiConstants;
import com.Accenture.Accenture.domain.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.support.WebExchangeBindException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manejador global de excepciones para la aplicación
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ConstraintViolationException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleConstraintViolation(ConstraintViolationException ex) {
        log.error("Error de validación: {}", ex.getMessage());
        
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", ApiConstants.HTTP_BAD_REQUEST);
        response.put("error", ApiConstants.ERROR_VALIDACION);
        response.put("message", ex.getMessage());
        
        return Mono.just(ResponseEntity.badRequest().body(response));
    }
    
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleWebExchangeBindException(WebExchangeBindException ex) {
        log.error("Error de validación de request body: {}", ex.getMessage());
        
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", ApiConstants.HTTP_BAD_REQUEST);
        response.put("error", ApiConstants.ERROR_VALIDACION);
        response.put("message", errorMessage);
        response.put("details", ex.getBindingResult().getFieldErrors().stream()
                .map(error -> Map.of(
                    "field", error.getField(),
                    "message", error.getDefaultMessage(),
                    "rejectedValue", error.getRejectedValue() != null ? error.getRejectedValue().toString() : "null"
                ))
                .collect(Collectors.toList()));
        
        return Mono.just(ResponseEntity.badRequest().body(response));
    }
    
    @ExceptionHandler(FranquiciaNotFoundException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleFranquiciaNotFound(FranquiciaNotFoundException ex) {
        log.warn("Franquicia no encontrada: {}", ex.getMessage());
        
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value());
         response.put("error", ApiConstants.ERROR_FRANQUICIA_NO_ENCONTRADA);
        response.put("message", ex.getMessage());
        
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(response));
    }
    
    @ExceptionHandler(FranquiciaDuplicadaException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleFranquiciaDuplicada(FranquiciaDuplicadaException ex) {
        log.warn("Franquicia duplicada: {}", ex.getMessage());
        
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.CONFLICT.value());
        response.put("error", "Franquicia duplicada");
        response.put("message", ex.getMessage());
        
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(response));
    }
    
    @ExceptionHandler(SucursalDuplicadaException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleSucursalDuplicada(SucursalDuplicadaException ex) {
        log.warn("Sucursal duplicada: {}", ex.getMessage());
        
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.CONFLICT.value());
        response.put("error", ApiConstants.ERROR_SUCURSAL_DUPLICADA);
        response.put("message", ex.getMessage());
        
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(response));
    }
    
    @ExceptionHandler(SucursalNotFoundException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleSucursalNotFound(SucursalNotFoundException ex) {
        log.warn("Sucursal no encontrada: {}", ex.getMessage());
        
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", ApiConstants.ERROR_SUCURSAL_NO_ENCONTRADA);
        response.put("message", ex.getMessage());
        
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(response));
    }
    
    @ExceptionHandler(ProductoDuplicadoException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleProductoDuplicado(ProductoDuplicadoException ex) {
        log.warn("Producto duplicado: {}", ex.getMessage());
        
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.CONFLICT.value());
        response.put("error", ApiConstants.ERROR_PRODUCTO_DUPLICADO);
        response.put("message", ex.getMessage());
        
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(response));
    }
    
    @ExceptionHandler(ProductoNotFoundException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleProductoNotFound(ProductoNotFoundException ex) {
        log.warn("Producto no encontrado: {}", ex.getMessage());
        
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", ApiConstants.ERROR_PRODUCTO_NO_ENCONTRADO);
        response.put("message", ex.getMessage());
        
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(response));
    }
    
    @ExceptionHandler(RuntimeException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleRuntimeException(RuntimeException ex) {
        log.error("Error interno del servidor: {}", ex.getMessage(), ex);
        
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Error interno del servidor");
        response.put("message", "Ha ocurrido un error inesperado");
        
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response));
    }
    
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleGenericException(Exception ex) {
        log.error("Error no manejado: {}", ex.getMessage(), ex);
        
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Error interno del servidor");
        response.put("message", "Ha ocurrido un error inesperado");
        
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response));
    }
}
