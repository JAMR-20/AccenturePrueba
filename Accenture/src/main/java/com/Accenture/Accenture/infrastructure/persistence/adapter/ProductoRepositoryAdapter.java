package com.Accenture.Accenture.infrastructure.persistence.adapter;

import com.Accenture.Accenture.domain.entity.Producto;
import com.Accenture.Accenture.domain.repository.ProductoRepository;
import com.Accenture.Accenture.infrastructure.persistence.entity.ProductoEntity;
import com.Accenture.Accenture.infrastructure.persistence.mapper.ProductoEntityMapper;
import com.Accenture.Accenture.infrastructure.persistence.repository.ProductoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador que implementa ProductoRepository usando JPA
 */
@Component
@RequiredArgsConstructor
public class ProductoRepositoryAdapter implements ProductoRepository {
    
    private final ProductoJpaRepository productoJpaRepository;
    private final ProductoEntityMapper productoEntityMapper;
    
    @Override
    public Mono<Producto> save(Producto producto) {
        ProductoEntity entity = productoEntityMapper.toEntity(producto);
        ProductoEntity savedEntity = productoJpaRepository.save(entity);
        return Mono.just(productoEntityMapper.toDomain(savedEntity));
    }
    
    @Override
    public Mono<Producto> findById(Long id) {
        return productoJpaRepository.findById(id)
                .map(productoEntityMapper::toDomain)
                .map(Mono::just)
                .orElse(Mono.empty());
    }

    
    @Override
    public Mono<Producto> findByNombreAndSucursalId(String nombre, Long sucursalId) {
        ProductoEntity entity = productoJpaRepository.findByNombreAndSucursalId(nombre, sucursalId);
        if (entity != null) {
            return Mono.just(productoEntityMapper.toDomain(entity));
        } else {
            return Mono.empty();
        }
    }
    
    @Override
    public Mono<Void> deleteById(Long id) {
        productoJpaRepository.deleteById(id);
        return Mono.empty();
    }
    
    @Override
    public Flux<Producto> findProductosConMayorStockPorSucursal(Long franquiciaId) {
        return Flux.fromIterable(productoJpaRepository.findProductosConMayorStockPorSucursal(franquiciaId))
                .map(productoEntityMapper::toDomain);
    }
}
