package com.iceberg.app.service.mapper;

import com.iceberg.app.domain.*;
import com.iceberg.app.service.dto.ObjetDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Objet and its DTO ObjetDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface ObjetMapper {

    @Mapping(source = "user.id", target = "userId")
    ObjetDTO objetToObjetDTO(Objet objet);

    List<ObjetDTO> objetsToObjetDTOs(List<Objet> objets);

    @Mapping(target = "emplacements", ignore = true)
    @Mapping(target = "caracteristiques", ignore = true)
    @Mapping(source = "userId", target = "user")
    Objet objetDTOToObjet(ObjetDTO objetDTO);
    
    List<Objet> objetDTOsToObjets(List<ObjetDTO> objetDTOs);
}
