package com.iceberg.app.repository;

import com.iceberg.app.domain.ParamType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ParamType entity.
 */
@SuppressWarnings("unused")
public interface ParamTypeRepository extends JpaRepository<ParamType,Long> {

}
