package com.Accenture.Accenture.infrastructure.persistence.adapter;

import com.Accenture.Accenture.domain.entity.Franquicia;
import com.Accenture.Accenture.domain.repository.FranquiciaRepository;
import com.Accenture.Accenture.infrastructure.persistence.entity.FranquiciaEntity;
import com.Accenture.Accenture.infrastructure.persistence.mapper.FranquiciaEntityMapper;
import com.Accenture.Accenture.infrastructure.persistence.repository.FranquiciaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador que implementa FranquiciaRepository usando JPA
 */
@Component
@RequiredArgsConstructor
public class FranquiciaRepositoryAdapter implements FranquiciaRepository {
    
    private final FranquiciaJpaRepository franquiciaJpaRepository;
    private final FranquiciaEntityMapper franquiciaEntityMapper;
    
    @Override
    public Mono<Franquicia> save(Franquicia franquicia) {
        FranquiciaEntity entity = franquiciaEntityMapper.toEntity(franquicia);
        FranquiciaEntity savedEntity = franquiciaJpaRepository.save(entity);
        return Mono.just(franquiciaEntityMapper.toDomain(savedEntity));
    }
    
    @Override
    public Mono<Franquicia> findById(Long id) {
        return franquiciaJpaRepository.findById(id)
                .map(franquiciaEntityMapper::toDomain)
                .map(Mono::just)
                .orElse(Mono.empty());
    }
    
    @Override
    public Flux<Franquicia> findAll() {
        return Flux.fromIterable(franquiciaJpaRepository.findAll())
                .map(franquiciaEntityMapper::toDomain);
    }
    
    @Override
    public Mono<Void> deleteById(Long id) {
        franquiciaJpaRepository.deleteById(id);
        return Mono.empty();
    }
    
    @Override
    public Mono<Franquicia> findByNombre(String nombre) {
        return franquiciaJpaRepository.findByNombre(nombre)
                .map(franquiciaEntityMapper::toDomain)
                .map(Mono::just)
                .orElse(Mono.empty());
    }
}
