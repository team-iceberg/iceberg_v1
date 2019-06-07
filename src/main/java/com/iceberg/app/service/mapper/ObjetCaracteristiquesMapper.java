package com.iceberg.app.service.mapper;

import com.iceberg.app.domain.*;
import com.iceberg.app.service.dto.ObjetCaracteristiquesDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ObjetCaracteristiques and its DTO ObjetCaracteristiquesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ObjetCaracteristiquesMapper {

    @Mapping(source = "objet.id", target = "objetId")
    ObjetCaracteristiquesDTO objetCaracteristiquesToObjetCaracteristiquesDTO(ObjetCaracteristiques objetCaracteristiques);

    List<ObjetCaracteristiquesDTO> objetCaracteristiquesToObjetCaracteristiquesDTOs(List<ObjetCaracteristiques> objetCaracteristiques);

    @Mapping(source = "objetId", target = "objet")
    ObjetCaracteristiques objetCaracteristiquesDTOToObjetCaracteristiques(ObjetCaracteristiquesDTO objetCaracteristiquesDTO);

    List<ObjetCaracteristiques> objetCaracteristiquesDTOsToObjetCaracteristiques(List<ObjetCaracteristiquesDTO> objetCaracteristiquesDTOs);

    default Objet objetFromId(Long id) {
        if (id == null) {
            return null;
        }
        Objet objet = new Objet();
        objet.setId(id);
        return objet;
    }
}
