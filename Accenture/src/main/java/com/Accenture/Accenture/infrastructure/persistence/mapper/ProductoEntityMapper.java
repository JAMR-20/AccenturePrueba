package com.Accenture.Accenture.infrastructure.persistence.mapper;

import com.Accenture.Accenture.domain.entity.Producto;
import com.Accenture.Accenture.infrastructure.persistence.entity.ProductoEntity;
import com.Accenture.Accenture.infrastructure.persistence.entity.SucursalEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper entre Producto (dominio) y ProductoEntity (persistencia)
 */
@Component
public class ProductoEntityMapper {
    
    public Producto toDomain(ProductoEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Producto.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .stock(entity.getStock())
                .sucursalId(entity.getSucursal() != null ? entity.getSucursal().getId() : null)
                .build();
    }
    
    public ProductoEntity toEntity(Producto producto) {
        if (producto == null) {
            return null;
        }
        
        // Crear una SucursalEntity con solo el ID para la relación
        SucursalEntity sucursal = null;
        if (producto.getSucursalId() != null) {
            sucursal = SucursalEntity.builder()
                    .id(producto.getSucursalId())
                    .build();
        }
        
        return ProductoEntity.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .stock(producto.getStock())
                .sucursal(sucursal)
                .build();
    }
}
