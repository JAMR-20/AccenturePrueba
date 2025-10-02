package com.Accenture.Accenture.domain.repository;

import com.Accenture.Accenture.domain.entity.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Interfaz del repositorio de Producto en la capa de dominio
 */
public interface ProductoRepository {
    Mono<Producto> save(Producto producto);
    Mono<Producto> findById(Long id);
    Mono<Producto> findByNombreAndSucursalId(String nombre, Long sucursalId);
    Mono<Void> deleteById(Long id);
    Flux<Producto> findProductosConMayorStockPorSucursal(Long franquiciaId);
}
