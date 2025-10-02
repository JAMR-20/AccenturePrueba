package com.Accenture.Accenture.infrastructure.persistence.repository;

import com.Accenture.Accenture.infrastructure.persistence.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para ProductoEntity
 */
@Repository
public interface ProductoJpaRepository extends JpaRepository<ProductoEntity, Long> {
    
    List<ProductoEntity> findBySucursalId(Long sucursalId);
    
    @Query("""
        SELECT p FROM ProductoEntity p
        INNER JOIN p.sucursal s
        WHERE s.franquicia.id = :franquiciaId
        AND p.stock = (
            SELECT MAX(p2.stock) 
            FROM ProductoEntity p2 
            INNER JOIN p2.sucursal s2 
            WHERE s2.id = s.id
        )
        ORDER BY s.id, p.stock DESC
        """)
    List<ProductoEntity> findProductosConMayorStockPorSucursal(@Param("franquiciaId") Long franquiciaId);
    
    ProductoEntity findByNombreAndSucursalId(String nombre, Long sucursalId);
}
