package com.Accenture.Accenture.domain.exception;

/**
 * Excepción lanzada cuando no se encuentra una sucursal
 */
public class SucursalNotFoundException extends RuntimeException {

    public SucursalNotFoundException(Long id) {
        super("Sucursal con ID " + id + " no encontrada");
    }
}
