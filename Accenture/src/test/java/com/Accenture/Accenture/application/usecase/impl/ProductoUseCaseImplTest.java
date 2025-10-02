package com.Accenture.Accenture.application.usecase.impl;

import com.Accenture.Accenture.application.dto.ProductoConSucursalDto;
import com.Accenture.Accenture.application.dto.ProductoDto;
import com.Accenture.Accenture.application.dto.ActualizarNombreProductoDto;
import com.Accenture.Accenture.application.mapper.ProductoMapper;
import com.Accenture.Accenture.domain.entity.Franquicia;
import com.Accenture.Accenture.domain.entity.Producto;
import com.Accenture.Accenture.domain.entity.Sucursal;
import com.Accenture.Accenture.domain.exception.FranquiciaNotFoundException;
import com.Accenture.Accenture.domain.exception.ProductoDuplicadoException;
import com.Accenture.Accenture.domain.exception.ProductoNotFoundException;
import com.Accenture.Accenture.domain.exception.SucursalNotFoundException;
import com.Accenture.Accenture.domain.repository.FranquiciaRepository;
import com.Accenture.Accenture.domain.repository.ProductoRepository;
import com.Accenture.Accenture.domain.repository.SucursalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias para ProductoUseCaseImpl
 */
@ExtendWith(MockitoExtension.class)
class ProductoUseCaseImplTest {
    
    @Mock
    private ProductoRepository productoRepository;
    
    @Mock
    private SucursalRepository sucursalRepository;
    
    @Mock
    private FranquiciaRepository franquiciaRepository;
    
    @Mock
    private ProductoMapper productoMapper;
    
    @InjectMocks
    private ProductoUseCaseImpl productoUseCase;
    
    private ProductoDto productoDto;
    private Producto producto;
    private Sucursal sucursal;
    private Franquicia franquicia;
    
    @BeforeEach
    void setUp() {
        franquicia = Franquicia.builder()
                .id(1L)
                .nombre("McDonald's")
                .build();
        
        sucursal = Sucursal.builder()
                .id(1L)
                .nombre("McDonald's Centro")
                .franquiciaId(1L)
                .build();
        
        productoDto = ProductoDto.builder()
                .nombre("Big Mac")
                .stock(BigDecimal.valueOf(50.00))
                .sucursalId(1L)
                .build();
        
        producto = Producto.builder()
                .id(1L)
                .nombre("Big Mac")
                .stock(BigDecimal.valueOf(50.00))
                .sucursalId(1L)
                .build();
    }
    
    @Test
    void crearProducto_DeberiaRetornarProductoCreado() {
        // Given
        when(sucursalRepository.findById(anyLong())).thenReturn(Mono.just(sucursal));
        when(productoRepository.findByNombreAndSucursalId(anyString(), anyLong())).thenReturn(Mono.empty());
        when(productoMapper.toEntity(productoDto)).thenReturn(producto);
        when(productoRepository.save(any(Producto.class))).thenReturn(Mono.just(producto));
        when(productoMapper.toDto(producto)).thenReturn(productoDto);
        
        // When & Then
        StepVerifier.create(productoUseCase.crearProducto(productoDto))
                .expectNext(productoDto)
                .verifyComplete();
    }
    
    @Test
    void crearProducto_ConSucursalNoEncontrada_DeberiaLanzarExcepcion() {
        // Given
        when(sucursalRepository.findById(anyLong())).thenReturn(Mono.empty());
        
        // When & Then
        StepVerifier.create(productoUseCase.crearProducto(productoDto))
                .expectError(SucursalNotFoundException.class)
                .verify();
    }
    
    @Test
    void crearProducto_ConNombreDuplicado_DeberiaLanzarExcepcion() {
        // Given
        when(sucursalRepository.findById(anyLong())).thenReturn(Mono.just(sucursal));
        when(productoRepository.findByNombreAndSucursalId(anyString(), anyLong())).thenReturn(Mono.just(producto));
        
        // When & Then
        StepVerifier.create(productoUseCase.crearProducto(productoDto))
                .expectError(ProductoDuplicadoException.class)
                .verify();
    }
    
    @Test
    void modificarStock_DeberiaRetornarProductoActualizado() {
        // Given
        Long id = 1L;
        Integer nuevoStock = 100;
        Producto productoActualizado = Producto.builder()
                .id(id)
                .nombre("Big Mac")
                .stock(BigDecimal.valueOf(100))
                .sucursalId(1L)
                .build();
        
        ProductoDto productoDtoActualizado = ProductoDto.builder()
                .id(id)
                .nombre("Big Mac")
                .stock(BigDecimal.valueOf(100))
                .sucursalId(1L)
                .build();
        
        when(productoRepository.findById(id)).thenReturn(Mono.just(producto));
        when(productoRepository.save(any(Producto.class))).thenReturn(Mono.just(productoActualizado));
        when(productoMapper.toDto(productoActualizado)).thenReturn(productoDtoActualizado);
        
        // When & Then
        StepVerifier.create(productoUseCase.modificarStock(id, nuevoStock))
                .expectNext(productoDtoActualizado)
                .verifyComplete();
    }
    
    @Test
    void modificarStock_ConProductoNoEncontrado_DeberiaLanzarExcepcion() {
        // Given
        Long id = 999L;
        Integer nuevoStock = 100;
        
        when(productoRepository.findById(id)).thenReturn(Mono.empty());
        
        // When & Then
        StepVerifier.create(productoUseCase.modificarStock(id, nuevoStock))
                .expectError(ProductoNotFoundException.class)
                .verify();
    }
    
    @Test
    void modificarStockConHistorial_DeberiaRetornarProductoStockDto() {
        // Given
        Long id = 1L;
        Integer nuevoStock = 100;
        BigDecimal stockAnterior = BigDecimal.valueOf(50.00);
        BigDecimal stockNuevo = BigDecimal.valueOf(100.00);
        
        Producto productoActualizado = Producto.builder()
                .id(id)
                .nombre("Big Mac")
                .stock(stockNuevo)
                .sucursalId(1L)
                .build();
        
        when(productoRepository.findById(id)).thenReturn(Mono.just(producto));
        when(productoRepository.save(any(Producto.class))).thenReturn(Mono.just(productoActualizado));
        
        // When & Then
        StepVerifier.create(productoUseCase.modificarStockConHistorial(id, nuevoStock))
                .expectNextMatches(productoStockDto -> 
                    productoStockDto.getId().equals(id) &&
                    productoStockDto.getNombre().equals("Big Mac") &&
                    productoStockDto.getStockAnterior().equals(stockAnterior) &&
                    productoStockDto.getStockNuevo().equals(stockNuevo) &&
                    productoStockDto.getSucursalId().equals(1L)
                )
                .verifyComplete();
    }
    
    @Test
    void eliminarProducto_DeberiaCompletarExitosamente() {
        // Given
        Long id = 1L;
        when(productoRepository.findById(id)).thenReturn(Mono.just(producto));
        when(productoRepository.deleteById(id)).thenReturn(Mono.empty());
        
        // When & Then
        StepVerifier.create(productoUseCase.eliminarProducto(id))
                .verifyComplete();
    }
    
    @Test
    void eliminarProducto_ConProductoNoEncontrado_DeberiaLanzarExcepcion() {
        // Given
        Long id = 999L;
        when(productoRepository.findById(id)).thenReturn(Mono.empty());
        when(productoRepository.deleteById(id)).thenReturn(Mono.empty());
        
        // When & Then
        StepVerifier.create(productoUseCase.eliminarProducto(id))
                .expectError(ProductoNotFoundException.class)
                .verify();
    }
    
    @Test
    void obtenerProductosConMayorStockPorFranquicia_DeberiaRetornarFluxDeProductos() {
        // Given
        Long franquiciaId = 1L;
        ProductoConSucursalDto productoConSucursal = ProductoConSucursalDto.builder()
                .productoId(1L)
                .nombreProducto("Big Mac")
                .stock(BigDecimal.valueOf(100.00))
                .sucursalId(1L)
                .nombreSucursal("McDonald's Centro")
                .build();
        
        when(franquiciaRepository.findById(franquiciaId)).thenReturn(Mono.just(franquicia));
        when(productoRepository.findProductosConMayorStockPorSucursal(franquiciaId)).thenReturn(Flux.just(producto));
        when(sucursalRepository.findById(anyLong())).thenReturn(Mono.just(sucursal));
        when(productoMapper.toDtoConSucursal(any(Producto.class), anyString())).thenReturn(productoConSucursal);
        
        // When & Then
        StepVerifier.create(productoUseCase.obtenerProductosConMayorStockPorFranquicia(franquiciaId))
                .expectNext(productoConSucursal)
                .verifyComplete();
    }
    
    @Test
    void obtenerProductosConMayorStockPorFranquicia_ConFranquiciaNoEncontrada_DeberiaLanzarExcepcion() {
        // Given
        Long franquiciaId = 999L;
        when(franquiciaRepository.findById(franquiciaId)).thenReturn(Mono.empty());
        
        // When & Then
        StepVerifier.create(productoUseCase.obtenerProductosConMayorStockPorFranquicia(franquiciaId))
                .expectError(FranquiciaNotFoundException.class)
                .verify();
    }
    
    @Test
    void actualizarNombreProducto_DeberiaRetornarProductoActualizado() {
        // Given
        Long id = 1L;
        ActualizarNombreProductoDto actualizarDto = ActualizarNombreProductoDto.builder()
                .nombre("Big Mac Actualizado")
                .build();
        
        Producto productoActualizado = Producto.builder()
                .id(id)
                .nombre("Big Mac Actualizado")
                .stock(BigDecimal.valueOf(50.00))
                .sucursalId(1L)
                .build();
        
        ProductoDto productoDtoActualizado = ProductoDto.builder()
                .id(id)
                .nombre("Big Mac Actualizado")
                .stock(BigDecimal.valueOf(50.00))
                .sucursalId(1L)
                .build();
        
        when(productoRepository.findById(id)).thenReturn(Mono.just(producto));
        when(productoRepository.findByNombreAndSucursalId(anyString(), anyLong())).thenReturn(Mono.empty());
        when(productoRepository.save(any(Producto.class))).thenReturn(Mono.just(productoActualizado));
        when(productoMapper.toDto(productoActualizado)).thenReturn(productoDtoActualizado);
        
        // When & Then
        StepVerifier.create(productoUseCase.actualizarNombreProducto(id, actualizarDto))
                .expectNext(productoDtoActualizado)
                .verifyComplete();
    }
    
    @Test
    void actualizarNombreProducto_ConProductoNoEncontrado_DeberiaLanzarExcepcion() {
        // Given
        Long id = 999L;
        ActualizarNombreProductoDto actualizarDto = ActualizarNombreProductoDto.builder()
                .nombre("Big Mac Actualizado")
                .build();
        
        when(productoRepository.findById(id)).thenReturn(Mono.empty());
        
        // When & Then
        StepVerifier.create(productoUseCase.actualizarNombreProducto(id, actualizarDto))
                .expectError(ProductoNotFoundException.class)
                .verify();
    }
    
    @Test
    void actualizarNombreProducto_ConNombreDuplicado_DeberiaLanzarExcepcion() {
        // Given
        Long id = 1L;
        ActualizarNombreProductoDto actualizarDto = ActualizarNombreProductoDto.builder()
                .nombre("Big Mac Duplicado")
                .build();
        
        Producto productoDuplicado = Producto.builder()
                .id(2L)
                .nombre("Big Mac Duplicado")
                .stock(BigDecimal.valueOf(30.00))
                .sucursalId(1L)
                .build();
        
        when(productoRepository.findById(id)).thenReturn(Mono.just(producto));
        when(productoRepository.findByNombreAndSucursalId(anyString(), anyLong())).thenReturn(Mono.just(productoDuplicado));
        
        // When & Then
        StepVerifier.create(productoUseCase.actualizarNombreProducto(id, actualizarDto))
                .expectError(ProductoDuplicadoException.class)
                .verify();
    }
}
