package com.Accenture.Accenture.application.usecase;

import com.Accenture.Accenture.application.dto.FranquiciaDto;
import com.Accenture.Accenture.application.dto.ActualizarNombreFranquiciaDto;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para operaciones de Franquicia
 */
public interface FranquiciaUseCase {
    Mono<FranquiciaDto> crearFranquicia(FranquiciaDto franquiciaDto);
    Mono<FranquiciaDto> actualizarNombreFranquicia(Long id, ActualizarNombreFranquiciaDto actualizarNombreDto);
}
