package com.Accenture.Accenture.infrastructure.web.controller;

import com.Accenture.Accenture.application.dto.ProductoDto;
import com.Accenture.Accenture.application.dto.ProductoConSucursalDto;
import com.Accenture.Accenture.application.dto.EliminacionResponseDto;
import com.Accenture.Accenture.application.dto.StockResponseDto;
import com.Accenture.Accenture.application.dto.ProductoCreacionResponseDto;
import com.Accenture.Accenture.application.dto.ActualizarNombreProductoDto;
import com.Accenture.Accenture.application.dto.ActualizarStockDto;
import com.Accenture.Accenture.application.dto.ActualizacionResponseDto;
import com.Accenture.Accenture.application.usecase.ProductoUseCase;
import com.Accenture.Accenture.application.usecase.impl.ProductoUseCaseImpl;
import com.Accenture.Accenture.application.constants.ApiConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.time.LocalDateTime;

/**
 * Controlador REST reactivo para Producto
 */
@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
public class ProductoController {
    
    private final ProductoUseCase productoUseCase;
    
    @PostMapping
    public Mono<ResponseEntity<ProductoCreacionResponseDto>> crearProducto(@Valid @RequestBody ProductoDto productoDto) {
        return productoUseCase.crearProducto(productoDto)
                .map(producto -> ProductoCreacionResponseDto.builder()
                        .mensaje(ApiConstants.PRODUCTO_CREADO_EXITOSAMENTE)
                        .id(producto.getId())
                        .nombre(producto.getNombre())
                        .stock(producto.getStock())
                        .sucursalId(producto.getSucursalId())
                        .timestamp(LocalDateTime.now().toString())
                        .build())
                .map(ResponseEntity::ok);
    }
    
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<EliminacionResponseDto>> eliminarProducto(@PathVariable Long id) {
        return productoUseCase.eliminarProducto(id)
                .then(Mono.just(EliminacionResponseDto.builder()
                        .mensaje(ApiConstants.PRODUCTO_ELIMINADO_EXITOSAMENTE)
                        .id(id)
                        .timestamp(LocalDateTime.now().toString())
                        .build()))
                .map(ResponseEntity::ok);
    }
    
    @PutMapping("/{id}/stock")
    public Mono<ResponseEntity<StockResponseDto>> modificarStock(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarStockDto actualizarStockDto) {
        return ((ProductoUseCaseImpl) productoUseCase).modificarStockConHistorial(id, actualizarStockDto.getStock())
                .map(productoStockDto -> StockResponseDto.builder()
                        .mensaje(ApiConstants.STOCK_ACTUALIZADO_EXITOSAMENTE)
                        .productoId(productoStockDto.getId())
                        .nombreProducto(productoStockDto.getNombre())
                        .stockAnterior(productoStockDto.getStockAnterior())
                        .stockNuevo(productoStockDto.getStockNuevo())
                        .timestamp(LocalDateTime.now().toString())
                        .build())
                .map(ResponseEntity::ok);
    }
    
    @GetMapping("/franquicia/{franquiciaId}/mayor-stock")
    public Flux<ProductoConSucursalDto> obtenerProductosConMayorStockPorFranquicia(@PathVariable Long franquiciaId) {
        return productoUseCase.obtenerProductosConMayorStockPorFranquicia(franquiciaId);
    }
    
    @PutMapping("/{id}/nombre")
    public Mono<ResponseEntity<ActualizacionResponseDto>> actualizarNombreProducto(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarNombreProductoDto actualizarNombreDto) {
        return productoUseCase.actualizarNombreProducto(id, actualizarNombreDto)
                .map(producto -> ActualizacionResponseDto.builder()
                        .mensaje(ApiConstants.PRODUCTO_ACTUALIZADO_EXITOSAMENTE)
                        .id(producto.getId())
                        .nombreNuevo(producto.getNombre())
                        .timestamp(LocalDateTime.now().toString())
                        .build())
                .map(ResponseEntity::ok);
    }
}