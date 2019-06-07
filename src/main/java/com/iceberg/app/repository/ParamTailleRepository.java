package com.iceberg.app.repository;

import com.iceberg.app.domain.ParamTaille;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ParamTaille entity.
 */
@SuppressWarnings("unused")
public interface ParamTailleRepository extends JpaRepository<ParamTaille,Long> {

}
