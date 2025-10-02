package com.Accenture.Accenture.application.usecase.impl;

import com.Accenture.Accenture.application.dto.SucursalDto;
import com.Accenture.Accenture.application.dto.ActualizarNombreSucursalDto;
import com.Accenture.Accenture.application.mapper.SucursalMapper;
import com.Accenture.Accenture.domain.entity.Franquicia;
import com.Accenture.Accenture.domain.entity.Sucursal;
import com.Accenture.Accenture.domain.exception.FranquiciaNotFoundException;
import com.Accenture.Accenture.domain.exception.SucursalDuplicadaException;
import com.Accenture.Accenture.domain.exception.SucursalNotFoundException;
import com.Accenture.Accenture.domain.repository.FranquiciaRepository;
import com.Accenture.Accenture.domain.repository.SucursalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias para SucursalUseCaseImpl
 */
@ExtendWith(MockitoExtension.class)
class SucursalUseCaseImplTest {
    
    @Mock
    private SucursalRepository sucursalRepository;
    
    @Mock
    private SucursalMapper sucursalMapper;
    
    @Mock
    private FranquiciaRepository franquiciaRepository;
    
    @InjectMocks
    private SucursalUseCaseImpl sucursalUseCase;
    
    private SucursalDto sucursalDto;
    private Sucursal sucursal;
    private Franquicia franquicia;
    
    @BeforeEach
    void setUp() {
        franquicia = Franquicia.builder()
                .id(1L)
                .nombre("McDonald's")
                .build();
        
        sucursalDto = SucursalDto.builder()
                .nombre("McDonald's Centro")
                .franquiciaId(1L)
                .build();
        
        sucursal = Sucursal.builder()
                .id(1L)
                .nombre("McDonald's Centro")
                .franquiciaId(1L)
                .build();
    }
    
    @Test
    void crearSucursal_DeberiaRetornarSucursalCreada() {
        // Given
        when(franquiciaRepository.findById(anyLong())).thenReturn(Mono.just(franquicia));
        when(sucursalRepository.findByNombreAndFranquiciaId(anyString(), anyLong())).thenReturn(Mono.empty());
        when(sucursalMapper.toEntity(sucursalDto)).thenReturn(sucursal);
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(Mono.just(sucursal));
        when(sucursalMapper.toDto(sucursal)).thenReturn(sucursalDto);
        
        // When & Then
        StepVerifier.create(sucursalUseCase.crearSucursal(sucursalDto))
                .expectNext(sucursalDto)
                .verifyComplete();
    }
    
    @Test
    void crearSucursal_ConFranquiciaNoEncontrada_DeberiaLanzarExcepcion() {
        // Given
        when(franquiciaRepository.findById(anyLong())).thenReturn(Mono.empty());
        
        // When & Then
        StepVerifier.create(sucursalUseCase.crearSucursal(sucursalDto))
                .expectError(FranquiciaNotFoundException.class)
                .verify();
    }
    
    @Test
    void crearSucursal_ConNombreDuplicado_DeberiaLanzarExcepcion() {
        // Given
        when(franquiciaRepository.findById(anyLong())).thenReturn(Mono.just(franquicia));
        when(sucursalRepository.findByNombreAndFranquiciaId(anyString(), anyLong())).thenReturn(Mono.just(sucursal));
        
        // When & Then
        StepVerifier.create(sucursalUseCase.crearSucursal(sucursalDto))
                .expectError(SucursalDuplicadaException.class)
                .verify();
    }
    
    @Test
    void actualizarNombreSucursal_DeberiaRetornarSucursalActualizada() {
        // Given
        Long id = 1L;
        ActualizarNombreSucursalDto actualizarDto = ActualizarNombreSucursalDto.builder()
                .nombre("McDonald's Centro Actualizado")
                .build();
        
        Sucursal sucursalActualizada = Sucursal.builder()
                .id(id)
                .nombre("McDonald's Centro Actualizado")
                .franquiciaId(1L)
                .build();
        
        SucursalDto sucursalDtoActualizada = SucursalDto.builder()
                .id(id)
                .nombre("McDonald's Centro Actualizado")
                .franquiciaId(1L)
                .build();
        
        when(sucursalRepository.findById(id)).thenReturn(Mono.just(sucursal));
        when(sucursalRepository.findByNombreAndFranquiciaId(anyString(), anyLong())).thenReturn(Mono.empty());
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(Mono.just(sucursalActualizada));
        when(sucursalMapper.toDto(sucursalActualizada)).thenReturn(sucursalDtoActualizada);
        
        // When & Then
        StepVerifier.create(sucursalUseCase.actualizarNombreSucursal(id, actualizarDto))
                .expectNext(sucursalDtoActualizada)
                .verifyComplete();
    }
    
    @Test
    void actualizarNombreSucursal_ConSucursalNoEncontrada_DeberiaLanzarExcepcion() {
        // Given
        Long id = 999L;
        ActualizarNombreSucursalDto actualizarDto = ActualizarNombreSucursalDto.builder()
                .nombre("McDonald's Centro Actualizado")
                .build();
        
        when(sucursalRepository.findById(id)).thenReturn(Mono.empty());
        
        // When & Then
        StepVerifier.create(sucursalUseCase.actualizarNombreSucursal(id, actualizarDto))
                .expectError(SucursalNotFoundException.class)
                .verify();
    }
    
    @Test
    void actualizarNombreSucursal_ConNombreDuplicado_DeberiaLanzarExcepcion() {
        // Given
        Long id = 1L;
        ActualizarNombreSucursalDto actualizarDto = ActualizarNombreSucursalDto.builder()
                .nombre("McDonald's Centro Duplicado")
                .build();
        
        Sucursal sucursalDuplicada = Sucursal.builder()
                .id(2L)
                .nombre("McDonald's Centro Duplicado")
                .franquiciaId(1L)
                .build();
        
        when(sucursalRepository.findById(id)).thenReturn(Mono.just(sucursal));
        when(sucursalRepository.findByNombreAndFranquiciaId(anyString(), anyLong())).thenReturn(Mono.just(sucursalDuplicada));
        
        // When & Then
        StepVerifier.create(sucursalUseCase.actualizarNombreSucursal(id, actualizarDto))
                .expectError(SucursalDuplicadaException.class)
                .verify();
    }
}
