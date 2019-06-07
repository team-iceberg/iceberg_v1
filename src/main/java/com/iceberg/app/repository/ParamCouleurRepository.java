package com.iceberg.app.repository;

import com.iceberg.app.domain.ParamCouleur;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ParamCouleur entity.
 */
@SuppressWarnings("unused")
public interface ParamCouleurRepository extends JpaRepository<ParamCouleur,Long> {

}
