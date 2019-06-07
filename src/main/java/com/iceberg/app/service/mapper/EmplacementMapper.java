package com.iceberg.app.service.mapper;

import com.iceberg.app.domain.*;
import com.iceberg.app.service.dto.EmplacementDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Emplacement and its DTO EmplacementDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmplacementMapper {

    @Mapping(source = "objet.id", target = "objetId")
    @Mapping(source = "paramEmpl.id", target = "paramEmplId")
    EmplacementDTO emplacementToEmplacementDTO(Emplacement emplacement);

    List<EmplacementDTO> emplacementsToEmplacementDTOs(List<Emplacement> emplacements);

    @Mapping(source = "objetId", target = "objet")
    @Mapping(target = "detailEmplacements", ignore = true)
    @Mapping(source = "paramEmplId", target = "paramEmpl")
    Emplacement emplacementDTOToEmplacement(EmplacementDTO emplacementDTO);

    List<Emplacement> emplacementDTOsToEmplacements(List<EmplacementDTO> emplacementDTOs);

    default Objet objetFromId(Long id) {
        if (id == null) {
            return null;
        }
        Objet objet = new Objet();
        objet.setId(id);
        return objet;
    }

    default ParamEmpl paramEmplFromId(Long id) {
        if (id == null) {
            return null;
        }
        ParamEmpl paramEmpl = new ParamEmpl();
        paramEmpl.setId(id);
        return paramEmpl;
    }
}
