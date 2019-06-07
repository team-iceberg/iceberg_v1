package com.iceberg.app.service.mapper;

import com.iceberg.app.domain.*;
import com.iceberg.app.service.dto.DetailEmplacementDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity DetailEmplacement and its DTO DetailEmplacementDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DetailEmplacementMapper {

    @Mapping(source = "emplacement.id", target = "emplacementId")
    DetailEmplacementDTO detailEmplacementToDetailEmplacementDTO(DetailEmplacement detailEmplacement);

    List<DetailEmplacementDTO> detailEmplacementsToDetailEmplacementDTOs(List<DetailEmplacement> detailEmplacements);

    @Mapping(source = "emplacementId", target = "emplacement")
    @Mapping(target = "reservations", ignore = true)
    DetailEmplacement detailEmplacementDTOToDetailEmplacement(DetailEmplacementDTO detailEmplacementDTO);

    List<DetailEmplacement> detailEmplacementDTOsToDetailEmplacements(List<DetailEmplacementDTO> detailEmplacementDTOs);

    default Emplacement emplacementFromId(Long id) {
        if (id == null) {
            return null;
        }
        Emplacement emplacement = new Emplacement();
        emplacement.setId(id);
        return emplacement;
    }
}
