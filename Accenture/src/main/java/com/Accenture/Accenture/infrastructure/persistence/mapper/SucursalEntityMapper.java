package com.Accenture.Accenture.infrastructure.persistence.mapper;

import com.Accenture.Accenture.domain.entity.Sucursal;
import com.Accenture.Accenture.infrastructure.persistence.entity.FranquiciaEntity;
import com.Accenture.Accenture.infrastructure.persistence.entity.SucursalEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper entre Sucursal (dominio) y SucursalEntity (persistencia)
 */
@Component
public class SucursalEntityMapper {
    
    public Sucursal toDomain(SucursalEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Sucursal.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .franquiciaId(entity.getFranquicia() != null ? entity.getFranquicia().getId() : null)
                .productos(null) // Se maneja por separado para evitar recursión
                .build();
    }
    
    public SucursalEntity toEntity(Sucursal sucursal) {
        if (sucursal == null) {
            return null;
        }
        
        // Crear una FranquiciaEntity con solo el ID para la relación
        FranquiciaEntity franquicia = null;
        if (sucursal.getFranquiciaId() != null) {
            franquicia = FranquiciaEntity.builder()
                    .id(sucursal.getFranquiciaId())
                    .build();
        }
        
        return SucursalEntity.builder()
                .id(sucursal.getId())
                .nombre(sucursal.getNombre())
                .franquicia(franquicia)
                .productos(null) // Se maneja por separado para evitar recursión
                .build();
    }
}
