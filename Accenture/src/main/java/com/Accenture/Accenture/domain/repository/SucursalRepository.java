package com.Accenture.Accenture.domain.repository;

import com.Accenture.Accenture.domain.entity.Sucursal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Interfaz del repositorio de Sucursal en la capa de dominio
 */
public interface SucursalRepository {
    Mono<Sucursal> save(Sucursal sucursal);
    Mono<Sucursal> findById(Long id);
    Flux<Sucursal> findByFranquiciaId(Long franquiciaId);
    Mono<Sucursal> findByNombreAndFranquiciaId(String nombre, Long franquiciaId);
    Mono<Void> deleteById(Long id);
}
