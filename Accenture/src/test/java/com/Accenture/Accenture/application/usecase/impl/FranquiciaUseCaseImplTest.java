package com.Accenture.Accenture.application.usecase.impl;

import com.Accenture.Accenture.application.dto.FranquiciaDto;
import com.Accenture.Accenture.application.dto.ActualizarNombreFranquiciaDto;
import com.Accenture.Accenture.application.mapper.FranquiciaMapper;
import com.Accenture.Accenture.domain.entity.Franquicia;
import com.Accenture.Accenture.domain.exception.FranquiciaDuplicadaException;
import com.Accenture.Accenture.domain.exception.FranquiciaNotFoundException;
import com.Accenture.Accenture.domain.repository.FranquiciaRepository;
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
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias para FranquiciaUseCaseImpl
 */
@ExtendWith(MockitoExtension.class)
class FranquiciaUseCaseImplTest {
    
    @Mock
    private FranquiciaRepository franquiciaRepository;
    
    @Mock
    private FranquiciaMapper franquiciaMapper;
    
    @InjectMocks
    private FranquiciaUseCaseImpl franquiciaUseCase;
    
    private FranquiciaDto franquiciaDto;
    private Franquicia franquicia;
    
    @BeforeEach
    void setUp() {
        franquiciaDto = FranquiciaDto.builder()
                .nombre("McDonald's")
                .build();
        
        franquicia = Franquicia.builder()
                .id(1L)
                .nombre("McDonald's")
                .build();
    }
    
    @Test
    void crearFranquicia_DeberiaRetornarFranquiciaCreada() {
        // Given
        when(franquiciaRepository.findByNombre(anyString())).thenReturn(Mono.empty());
        when(franquiciaMapper.toEntity(franquiciaDto)).thenReturn(franquicia);
        when(franquiciaRepository.save(any(Franquicia.class))).thenReturn(Mono.just(franquicia));
        when(franquiciaMapper.toDto(franquicia)).thenReturn(franquiciaDto);
        
        // When & Then
        StepVerifier.create(franquiciaUseCase.crearFranquicia(franquiciaDto))
                .expectNext(franquiciaDto)
                .verifyComplete();
    }
    
    @Test
    void crearFranquicia_ConNombreDuplicado_DeberiaLanzarExcepcion() {
        // Given
        when(franquiciaRepository.findByNombre(anyString())).thenReturn(Mono.just(franquicia));
        
        // When & Then
        StepVerifier.create(franquiciaUseCase.crearFranquicia(franquiciaDto))
                .expectError(FranquiciaDuplicadaException.class)
                .verify();
    }
    
    @Test
    void actualizarNombreFranquicia_DeberiaRetornarFranquiciaActualizada() {
        // Given
        Long id = 1L;
        ActualizarNombreFranquiciaDto actualizarDto = ActualizarNombreFranquiciaDto.builder()
                .nombre("McDonald's Actualizado")
                .build();
        
        Franquicia franquiciaActualizada = Franquicia.builder()
                .id(id)
                .nombre("McDonald's Actualizado")
                .build();
        
        FranquiciaDto franquiciaDtoActualizada = FranquiciaDto.builder()
                .id(id)
                .nombre("McDonald's Actualizado")
                .build();
        
        when(franquiciaRepository.findById(id)).thenReturn(Mono.just(franquicia));
        when(franquiciaRepository.findByNombre(anyString())).thenReturn(Mono.empty());
        when(franquiciaRepository.save(any(Franquicia.class))).thenReturn(Mono.just(franquiciaActualizada));
        when(franquiciaMapper.toDto(franquiciaActualizada)).thenReturn(franquiciaDtoActualizada);
        
        // When & Then
        StepVerifier.create(franquiciaUseCase.actualizarNombreFranquicia(id, actualizarDto))
                .expectNext(franquiciaDtoActualizada)
                .verifyComplete();
    }
    
    @Test
    void actualizarNombreFranquicia_ConFranquiciaNoEncontrada_DeberiaLanzarExcepcion() {
        // Given
        Long id = 999L;
        ActualizarNombreFranquiciaDto actualizarDto = ActualizarNombreFranquiciaDto.builder()
                .nombre("McDonald's Actualizado")
                .build();
        
        when(franquiciaRepository.findById(id)).thenReturn(Mono.empty());
        
        // When & Then
        StepVerifier.create(franquiciaUseCase.actualizarNombreFranquicia(id, actualizarDto))
                .expectError(FranquiciaNotFoundException.class)
                .verify();
    }
    
    @Test
    void actualizarNombreFranquicia_ConNombreDuplicado_DeberiaLanzarExcepcion() {
        // Given
        Long id = 1L;
        ActualizarNombreFranquiciaDto actualizarDto = ActualizarNombreFranquiciaDto.builder()
                .nombre("McDonald's Duplicado")
                .build();
        
        Franquicia franquiciaDuplicada = Franquicia.builder()
                .id(2L)
                .nombre("McDonald's Duplicado")
                .build();
        
        when(franquiciaRepository.findById(id)).thenReturn(Mono.just(franquicia));
        when(franquiciaRepository.findByNombre(anyString())).thenReturn(Mono.just(franquiciaDuplicada));
        
        // When & Then
        StepVerifier.create(franquiciaUseCase.actualizarNombreFranquicia(id, actualizarDto))
                .expectError(FranquiciaDuplicadaException.class)
                .verify();
    }
}
