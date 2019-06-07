package com.iceberg.app.repository;

import com.iceberg.app.domain.DetailEmplacement;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DetailEmplacement entity.
 */
@SuppressWarnings("unused")
public interface DetailEmplacementRepository extends JpaRepository<DetailEmplacement,Long> {

}
