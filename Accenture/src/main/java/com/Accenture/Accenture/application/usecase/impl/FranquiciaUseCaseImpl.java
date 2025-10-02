package com.Accenture.Accenture.application.usecase.impl;

import com.Accenture.Accenture.application.dto.FranquiciaDto;
import com.Accenture.Accenture.application.dto.ActualizarNombreFranquiciaDto;
import com.Accenture.Accenture.application.mapper.FranquiciaMapper;
import com.Accenture.Accenture.application.usecase.FranquiciaUseCase;
import com.Accenture.Accenture.domain.entity.Franquicia;
import com.Accenture.Accenture.domain.exception.FranquiciaDuplicadaException;
import com.Accenture.Accenture.domain.exception.FranquiciaNotFoundException;
import com.Accenture.Accenture.domain.repository.FranquiciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementación del caso de uso de Franquicia
 */
@Service
@RequiredArgsConstructor
public class FranquiciaUseCaseImpl implements FranquiciaUseCase {
    
    private final FranquiciaRepository franquiciaRepository;
    private final FranquiciaMapper franquiciaMapper;
    
    @Override
    public Mono<FranquiciaDto> crearFranquicia(FranquiciaDto franquiciaDto) {
        return franquiciaRepository.findByNombre(franquiciaDto.getNombre())
                .hasElement()
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new FranquiciaDuplicadaException(franquiciaDto.getNombre()));
                    }
                    Franquicia franquicia = franquiciaMapper.toEntity(franquiciaDto);
                    return franquiciaRepository.save(franquicia)
                            .map(franquiciaMapper::toDto);
                });
    }
    
    @Override
    public Mono<FranquiciaDto> actualizarNombreFranquicia(Long id, ActualizarNombreFranquiciaDto actualizarNombreDto) {
        return franquiciaRepository.findById(id)
                .switchIfEmpty(Mono.error(new FranquiciaNotFoundException(id)))
                .flatMap(franquicia -> {
                    // Verificar si ya existe otra franquicia con el mismo nombre
                    return franquiciaRepository.findByNombre(actualizarNombreDto.getNombre())
                            .hasElement()
                            .flatMap(exists -> {
                                if (exists) {
                                    return Mono.error(new FranquiciaDuplicadaException(actualizarNombreDto.getNombre()));
                                }
                                franquicia.setNombre(actualizarNombreDto.getNombre());
                                return franquiciaRepository.save(franquicia)
                                        .map(franquiciaMapper::toDto);
                            });
                });
    }
    
}
