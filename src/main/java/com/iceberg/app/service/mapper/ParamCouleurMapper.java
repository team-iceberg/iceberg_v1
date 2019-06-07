package com.iceberg.app.service.mapper;

import com.iceberg.app.domain.*;
import com.iceberg.app.service.dto.ParamCouleurDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ParamCouleur and its DTO ParamCouleurDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ParamCouleurMapper {

    ParamCouleurDTO paramCouleurToParamCouleurDTO(ParamCouleur paramCouleur);

    List<ParamCouleurDTO> paramCouleursToParamCouleurDTOs(List<ParamCouleur> paramCouleurs);

    ParamCouleur paramCouleurDTOToParamCouleur(ParamCouleurDTO paramCouleurDTO);

    List<ParamCouleur> paramCouleurDTOsToParamCouleurs(List<ParamCouleurDTO> paramCouleurDTOs);
}
