package com.iceberg.app.service.mapper;

import com.iceberg.app.domain.*;
import com.iceberg.app.service.dto.ParamTypeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ParamType and its DTO ParamTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ParamTypeMapper {

    ParamTypeDTO paramTypeToParamTypeDTO(ParamType paramType);

    List<ParamTypeDTO> paramTypesToParamTypeDTOs(List<ParamType> paramTypes);

    ParamType paramTypeDTOToParamType(ParamTypeDTO paramTypeDTO);

    List<ParamType> paramTypeDTOsToParamTypes(List<ParamTypeDTO> paramTypeDTOs);
}
