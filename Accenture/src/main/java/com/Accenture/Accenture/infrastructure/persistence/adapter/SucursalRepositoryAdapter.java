package com.Accenture.Accenture.infrastructure.persistence.adapter;

import com.Accenture.Accenture.domain.entity.Sucursal;
import com.Accenture.Accenture.domain.repository.SucursalRepository;
import com.Accenture.Accenture.infrastructure.persistence.entity.SucursalEntity;
import com.Accenture.Accenture.infrastructure.persistence.mapper.SucursalEntityMapper;
import com.Accenture.Accenture.infrastructure.persistence.repository.SucursalJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador que implementa SucursalRepository usando JPA
 */
@Component
@RequiredArgsConstructor
public class SucursalRepositoryAdapter implements SucursalRepository {
    
    private final SucursalJpaRepository sucursalJpaRepository;
    private final SucursalEntityMapper sucursalEntityMapper;
    
    @Override
    public Mono<Sucursal> save(Sucursal sucursal) {
        SucursalEntity entity = sucursalEntityMapper.toEntity(sucursal);
        SucursalEntity savedEntity = sucursalJpaRepository.save(entity);
        return Mono.just(sucursalEntityMapper.toDomain(savedEntity));
    }
    
    @Override
    public Mono<Sucursal> findById(Long id) {
        return sucursalJpaRepository.findById(id)
                .map(sucursalEntityMapper::toDomain)
                .map(Mono::just)
                .orElse(Mono.empty());
    }
    
    @Override
    public Flux<Sucursal> findByFranquiciaId(Long franquiciaId) {
        return Flux.fromIterable(sucursalJpaRepository.findByFranquiciaId(franquiciaId))
                .map(sucursalEntityMapper::toDomain);
    }
    
    @Override
    public Mono<Sucursal> findByNombreAndFranquiciaId(String nombre, Long franquiciaId) {
        SucursalEntity entity = sucursalJpaRepository.findByNombreAndFranquiciaId(nombre, franquiciaId);
        if (entity != null) {
            return Mono.just(sucursalEntityMapper.toDomain(entity));
        } else {
            return Mono.empty();
        }
    }
    
    @Override
    public Mono<Void> deleteById(Long id) {
        sucursalJpaRepository.deleteById(id);
        return Mono.empty();
    }
}
