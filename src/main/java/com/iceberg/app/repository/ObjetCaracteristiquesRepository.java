package com.iceberg.app.repository;

import com.iceberg.app.domain.ObjetCaracteristiques;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ObjetCaracteristiques entity.
 */
@SuppressWarnings("unused")
public interface ObjetCaracteristiquesRepository extends JpaRepository<ObjetCaracteristiques,Long> {

}
