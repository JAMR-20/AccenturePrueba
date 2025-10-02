package com.Accenture.Accenture.infrastructure.persistence.repository;

import com.Accenture.Accenture.infrastructure.persistence.entity.FranquiciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio JPA para FranquiciaEntity
 */
@Repository
public interface FranquiciaJpaRepository extends JpaRepository<FranquiciaEntity, Long> {
    
    Optional<FranquiciaEntity> findByNombre(String nombre);
}
