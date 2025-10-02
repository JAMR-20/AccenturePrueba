package com.Accenture.Accenture.infrastructure.web.controller;

import com.Accenture.Accenture.application.constants.ApiConstants;
import com.Accenture.Accenture.application.dto.SucursalDto;
import com.Accenture.Accenture.application.dto.SucursalCreacionResponseDto;
import com.Accenture.Accenture.application.dto.ActualizarNombreSucursalDto;
import com.Accenture.Accenture.application.dto.ActualizacionResponseDto;
import com.Accenture.Accenture.application.usecase.SucursalUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.time.LocalDateTime;

/**
 * Controlador REST reactivo para Sucursal
 */
@RestController
@RequestMapping("/api/v1/sucursales")
@RequiredArgsConstructor
public class SucursalController {
    
    private final SucursalUseCase sucursalUseCase;
    
    @PostMapping
    public Mono<ResponseEntity<SucursalCreacionResponseDto>> crearSucursal(@Valid @RequestBody SucursalDto sucursalDto) {
        return sucursalUseCase.crearSucursal(sucursalDto)
                .map(sucursal -> SucursalCreacionResponseDto.builder()
                        .mensaje(ApiConstants.SUCURSAL_CREADA_EXITOSAMENTE)
                        .id(sucursal.getId())
                        .nombre(sucursal.getNombre())
                        .franquiciaId(sucursal.getFranquiciaId())
                        .timestamp(LocalDateTime.now().toString())
                        .build())
                .map(ResponseEntity::ok);
    }
    
    @PutMapping("/{id}/nombre")
    public Mono<ResponseEntity<ActualizacionResponseDto>> actualizarNombreSucursal(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarNombreSucursalDto actualizarNombreDto) {
        return sucursalUseCase.actualizarNombreSucursal(id, actualizarNombreDto)
                .map(sucursal -> ActualizacionResponseDto.builder()
                        .mensaje(ApiConstants.SUCURSAL_ACTUALIZADA_EXITOSAMENTE)
                        .id(sucursal.getId())
                        .nombreNuevo(sucursal.getNombre())
                        .timestamp(LocalDateTime.now().toString())
                        .build())
                .map(ResponseEntity::ok);
    }
}