package com.iceberg.app.service.mapper;

import com.iceberg.app.domain.*;
import com.iceberg.app.service.dto.ParamTailleDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ParamTaille and its DTO ParamTailleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ParamTailleMapper {

    ParamTailleDTO paramTailleToParamTailleDTO(ParamTaille paramTaille);

    List<ParamTailleDTO> paramTaillesToParamTailleDTOs(List<ParamTaille> paramTailles);

    ParamTaille paramTailleDTOToParamTaille(ParamTailleDTO paramTailleDTO);

    List<ParamTaille> paramTailleDTOsToParamTailles(List<ParamTailleDTO> paramTailleDTOs);
}
