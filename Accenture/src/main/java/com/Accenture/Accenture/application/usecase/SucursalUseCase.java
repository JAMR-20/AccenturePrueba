package com.Accenture.Accenture.application.usecase;

import com.Accenture.Accenture.application.dto.SucursalDto;
import com.Accenture.Accenture.application.dto.ActualizarNombreSucursalDto;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para operaciones de Sucursal
 */
public interface SucursalUseCase {
    Mono<SucursalDto> crearSucursal(SucursalDto sucursalDto);
    Mono<SucursalDto> actualizarNombreSucursal(Long id, ActualizarNombreSucursalDto actualizarNombreDto);
}
