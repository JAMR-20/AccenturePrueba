package com.Accenture.Accenture.application.usecase.impl;

import com.Accenture.Accenture.application.dto.ProductoConSucursalDto;
import com.Accenture.Accenture.application.dto.ProductoDto;
import com.Accenture.Accenture.application.dto.ProductoStockDto;
import com.Accenture.Accenture.application.dto.ActualizarNombreProductoDto;
import com.Accenture.Accenture.application.mapper.ProductoMapper;
import com.Accenture.Accenture.application.usecase.ProductoUseCase;
import com.Accenture.Accenture.domain.entity.Producto;
import com.Accenture.Accenture.domain.exception.SucursalNotFoundException;
import com.Accenture.Accenture.domain.exception.ProductoDuplicadoException;
import com.Accenture.Accenture.domain.exception.ProductoNotFoundException;
import com.Accenture.Accenture.domain.exception.FranquiciaNotFoundException;
import com.Accenture.Accenture.domain.repository.ProductoRepository;
import com.Accenture.Accenture.domain.repository.SucursalRepository;
import com.Accenture.Accenture.domain.repository.FranquiciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

/**
 * Implementación del caso de uso de Producto
 */
@Service
@RequiredArgsConstructor
public class ProductoUseCaseImpl implements ProductoUseCase {
    
    private final ProductoRepository productoRepository;
    private final SucursalRepository sucursalRepository;
    private final FranquiciaRepository franquiciaRepository;
    private final ProductoMapper productoMapper;
    
    @Override
    public Mono<ProductoDto> crearProducto(ProductoDto productoDto) {
        // Verificar que la sucursal existe
        return sucursalRepository.findById(productoDto.getSucursalId())
                .switchIfEmpty(Mono.error(new SucursalNotFoundException(productoDto.getSucursalId())))
                .then(Mono.defer(() -> {
                    // Verificar que no existe un producto con el mismo nombre en esta sucursal
                    return productoRepository.findByNombreAndSucursalId(productoDto.getNombre(), productoDto.getSucursalId())
                            .flatMap(productoExistente -> 
                                Mono.<ProductoDto>error(new ProductoDuplicadoException(productoDto.getNombre(), productoDto.getSucursalId()))
                            )
                            .switchIfEmpty(Mono.defer(() -> {
                                Producto producto = productoMapper.toEntity(productoDto);
                                return productoRepository.save(producto)
                                        .map(productoMapper::toDto);
                            }));
                }));
    }
    
    @Override
    public Mono<ProductoDto> modificarStock(Long id, Integer nuevoStock) {
        return productoRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProductoNotFoundException(id)))
                .flatMap(producto -> {
                    producto.setStock(BigDecimal.valueOf(nuevoStock));
                    return productoRepository.save(producto)
                            .map(productoMapper::toDto);
                });
    }
    
    public Mono<ProductoStockDto> modificarStockConHistorial(Long id, Integer nuevoStock) {
        return productoRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProductoNotFoundException(id)))
                .flatMap(producto -> {
                    BigDecimal stockAnterior = producto.getStock();
                    producto.setStock(BigDecimal.valueOf(nuevoStock));
                    return productoRepository.save(producto)
                            .map(productoGuardado -> ProductoStockDto.builder()
                                    .id(productoGuardado.getId())
                                    .nombre(productoGuardado.getNombre())
                                    .stockAnterior(stockAnterior)
                                    .stockNuevo(productoGuardado.getStock())
                                    .sucursalId(productoGuardado.getSucursalId())
                                    .build());
                });
    }
    
    @Override
    public Mono<Void> eliminarProducto(Long id) {
        return productoRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProductoNotFoundException(id)))
                .then(productoRepository.deleteById(id));
    }
    
    @Override
    public Flux<ProductoConSucursalDto> obtenerProductosConMayorStockPorFranquicia(Long franquiciaId) {
        // Verificar que la franquicia existe
        return franquiciaRepository.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new FranquiciaNotFoundException(franquiciaId)))
                .thenMany(productoRepository.findProductosConMayorStockPorSucursal(franquiciaId))
                .flatMap(producto -> 
                    sucursalRepository.findById(producto.getSucursalId())
                            .map(sucursal -> productoMapper.toDtoConSucursal(producto, sucursal.getNombre()))
                );
    }
    
    @Override
    public Mono<ProductoDto> actualizarNombreProducto(Long id, ActualizarNombreProductoDto actualizarNombreDto) {
        return productoRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProductoNotFoundException(id)))
                .flatMap(producto -> {
                    // Verificar que no existe otro producto con el mismo nombre en la misma sucursal
                    return productoRepository.findByNombreAndSucursalId(actualizarNombreDto.getNombre(), producto.getSucursalId())
                            .flatMap(productoExistente -> 
                                Mono.<ProductoDto>error(new ProductoDuplicadoException(actualizarNombreDto.getNombre(), producto.getSucursalId()))
                            )
                            .switchIfEmpty(Mono.defer(() -> {
                                producto.setNombre(actualizarNombreDto.getNombre());
                                return productoRepository.save(producto)
                                        .map(productoMapper::toDto);
                            }));
                });
    }
}
