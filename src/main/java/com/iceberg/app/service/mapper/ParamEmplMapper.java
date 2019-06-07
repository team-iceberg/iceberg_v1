package com.iceberg.app.service.mapper;

import com.iceberg.app.domain.*;
import com.iceberg.app.service.dto.ParamEmplDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ParamEmpl and its DTO ParamEmplDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ParamEmplMapper {

    ParamEmplDTO paramEmplToParamEmplDTO(ParamEmpl paramEmpl);

    List<ParamEmplDTO> paramEmplsToParamEmplDTOs(List<ParamEmpl> paramEmpls);

    ParamEmpl paramEmplDTOToParamEmpl(ParamEmplDTO paramEmplDTO);

    List<ParamEmpl> paramEmplDTOsToParamEmpls(List<ParamEmplDTO> paramEmplDTOs);
}
