package com.Accenture.Accenture.infrastructure.web.controller;

import com.Accenture.Accenture.application.constants.ApiConstants;
import com.Accenture.Accenture.application.dto.FranquiciaDto;
import com.Accenture.Accenture.application.dto.FranquiciaCreacionResponseDto;
import com.Accenture.Accenture.application.dto.ActualizarNombreFranquiciaDto;
import com.Accenture.Accenture.application.dto.ActualizacionResponseDto;
import com.Accenture.Accenture.application.usecase.FranquiciaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.time.LocalDateTime;

/**
 * Controlador REST reactivo para Franquicia
 */
@RestController
@RequestMapping("/api/v1/franquicias")
@RequiredArgsConstructor
public class FranquiciaController {
    
    private final FranquiciaUseCase franquiciaUseCase;
    
    @PostMapping
    public Mono<ResponseEntity<FranquiciaCreacionResponseDto>> crearFranquicia(@Valid @RequestBody FranquiciaDto franquiciaDto) {
        return franquiciaUseCase.crearFranquicia(franquiciaDto)
                .map(franquicia -> FranquiciaCreacionResponseDto.builder()
                        .mensaje(ApiConstants.FRANQUICIA_CREADA_EXITOSAMENTE)
                        .id(franquicia.getId())
                        .nombre(franquicia.getNombre())
                        .timestamp(LocalDateTime.now().toString())
                        .build())
                .map(ResponseEntity::ok);
    }
    
    @PutMapping("/{id}/nombre")
    public Mono<ResponseEntity<ActualizacionResponseDto>> actualizarNombreFranquicia(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarNombreFranquiciaDto actualizarNombreDto) {
        return franquiciaUseCase.actualizarNombreFranquicia(id, actualizarNombreDto)
                .map(franquicia -> ActualizacionResponseDto.builder()
                        .mensaje(ApiConstants.FRANQUICIA_ACTUALIZADA_EXITOSAMENTE)
                        .id(franquicia.getId())
                        .nombreNuevo(franquicia.getNombre())
                        .timestamp(LocalDateTime.now().toString())
                        .build())
                .map(ResponseEntity::ok);
    }
    
}
