package com.Accenture.Accenture.domain.repository;

import com.Accenture.Accenture.domain.entity.Franquicia;
import reactor.core.publisher.Mono;

/**
 * Interfaz del repositorio de Franquicia en la capa de dominio
 */
public interface FranquiciaRepository {
    Mono<Franquicia> save(Franquicia franquicia);
    Mono<Franquicia> findById(Long id);
    Mono<Franquicia> findByNombre(String nombre);
}
