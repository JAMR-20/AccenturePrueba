package com.Accenture.Accenture.domain.exception;

/**
 * Excepción lanzada cuando no se encuentra un producto
 */
public class ProductoNotFoundException extends RuntimeException {
    
    public ProductoNotFoundException(String message) {
        super(message);
    }
    
    public ProductoNotFoundException(Long id) {
        super("Producto con ID " + id + " no encontrado");
    }
}
