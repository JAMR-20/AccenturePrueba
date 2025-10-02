package com.Accenture.Accenture.application.usecase;

import com.Accenture.Accenture.application.dto.ProductoConSucursalDto;
import com.Accenture.Accenture.application.dto.ProductoDto;
import com.Accenture.Accenture.application.dto.ActualizarNombreProductoDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para operaciones de Producto
 */
public interface ProductoUseCase {
    Mono<ProductoDto> crearProducto(ProductoDto productoDto);
    Mono<Void> eliminarProducto(Long id);
    Mono<ProductoDto> modificarStock(Long id, Integer nuevoStock);
    Mono<ProductoDto> actualizarNombreProducto(Long id, ActualizarNombreProductoDto actualizarNombreDto);
    Flux<ProductoConSucursalDto> obtenerProductosConMayorStockPorFranquicia(Long franquiciaId);
}
