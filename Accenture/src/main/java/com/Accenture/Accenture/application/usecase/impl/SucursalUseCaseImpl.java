package com.Accenture.Accenture.application.usecase.impl;

import com.Accenture.Accenture.application.dto.SucursalDto;
import com.Accenture.Accenture.application.dto.ActualizarNombreSucursalDto;
import com.Accenture.Accenture.application.mapper.SucursalMapper;
import com.Accenture.Accenture.application.usecase.SucursalUseCase;
import com.Accenture.Accenture.domain.entity.Sucursal;
import com.Accenture.Accenture.domain.exception.FranquiciaNotFoundException;
import com.Accenture.Accenture.domain.exception.SucursalDuplicadaException;
import com.Accenture.Accenture.domain.exception.SucursalNotFoundException;
import com.Accenture.Accenture.domain.repository.FranquiciaRepository;
import com.Accenture.Accenture.domain.repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementación del caso de uso de Sucursal
 */
@Service
@RequiredArgsConstructor
public class SucursalUseCaseImpl implements SucursalUseCase {
    
    private final SucursalRepository sucursalRepository;
    private final SucursalMapper sucursalMapper;
    private final FranquiciaRepository franquiciaRepository;
    
    @Override
    public Mono<SucursalDto> crearSucursal(SucursalDto sucursalDto) {
        // Verificar que la franquicia existe
        return franquiciaRepository.findById(sucursalDto.getFranquiciaId())
                .switchIfEmpty(Mono.error(new FranquiciaNotFoundException(sucursalDto.getFranquiciaId())))
                .then(Mono.defer(() -> {
                    // Verificar que no existe una sucursal con el mismo nombre para esta franquicia
                    return sucursalRepository.findByNombreAndFranquiciaId(sucursalDto.getNombre(), sucursalDto.getFranquiciaId())
                            .flatMap(sucursalExistente -> 
                                Mono.<SucursalDto>error(new SucursalDuplicadaException(sucursalDto.getNombre(), sucursalDto.getFranquiciaId()))
                            )
                            .switchIfEmpty(Mono.defer(() -> {
                                Sucursal sucursal = sucursalMapper.toEntity(sucursalDto);
                                return sucursalRepository.save(sucursal)
                                        .map(sucursalMapper::toDto);
                            }));
                }));
    }
    
    @Override
    public Mono<SucursalDto> actualizarNombreSucursal(Long id, ActualizarNombreSucursalDto actualizarNombreDto) {
        return sucursalRepository.findById(id)
                .switchIfEmpty(Mono.error(new SucursalNotFoundException(id)))
                .flatMap(sucursal -> {
                    // Verificar que no existe otra sucursal con el mismo nombre en la misma franquicia
                    return sucursalRepository.findByNombreAndFranquiciaId(actualizarNombreDto.getNombre(), sucursal.getFranquiciaId())
                            .flatMap(sucursalExistente -> 
                                Mono.<SucursalDto>error(new SucursalDuplicadaException(actualizarNombreDto.getNombre(), sucursal.getFranquiciaId()))
                            )
                            .switchIfEmpty(Mono.defer(() -> {
                                sucursal.setNombre(actualizarNombreDto.getNombre());
                                return sucursalRepository.save(sucursal)
                                        .map(sucursalMapper::toDto);
                            }));
                });
    }
    
}
