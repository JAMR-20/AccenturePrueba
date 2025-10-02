package com.Accenture.Accenture.infrastructure.persistence.repository;

import com.Accenture.Accenture.infrastructure.persistence.entity.SucursalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para SucursalEntity
 */
@Repository
public interface SucursalJpaRepository extends JpaRepository<SucursalEntity, Long> {

    @Query("SELECT s FROM SucursalEntity s WHERE s.nombre = :nombre AND s.franquicia.id = :franquiciaId")
    SucursalEntity findByNombreAndFranquiciaId(@Param("nombre") String nombre, @Param("franquiciaId") Long franquiciaId);
}
