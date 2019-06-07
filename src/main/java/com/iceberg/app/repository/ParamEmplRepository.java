package com.iceberg.app.repository;

import com.iceberg.app.domain.ParamEmpl;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ParamEmpl entity.
 */
@SuppressWarnings("unused")
public interface ParamEmplRepository extends JpaRepository<ParamEmpl,Long> {

}
