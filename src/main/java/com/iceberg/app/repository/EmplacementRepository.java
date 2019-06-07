package com.iceberg.app.repository;

import com.iceberg.app.domain.Emplacement;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Emplacement entity.
 */
@SuppressWarnings("unused")
public interface EmplacementRepository extends JpaRepository<Emplacement,Long> {

}
