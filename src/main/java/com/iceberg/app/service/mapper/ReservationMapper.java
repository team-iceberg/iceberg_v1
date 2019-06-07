package com.iceberg.app.service.mapper;

import com.iceberg.app.domain.DetailEmplacement;
import com.iceberg.app.domain.Reservation;
import com.iceberg.app.service.dto.ReservationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity Reservation and its DTO ReservationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReservationMapper {

    @Mapping(source = "detailEmplacement.id", target = "detailEmplacementId")
    ReservationDTO reservationToReservationDTO(Reservation reservation);

    List<ReservationDTO> reservationsToReservationDTOs(List<Reservation> reservations);

    @Mapping(source = "detailEmplacementId", target = "detailEmplacement")
    Reservation reservationDTOToReservation(ReservationDTO reservationDTO);

    List<Reservation> reservationDTOsToReservations(List<ReservationDTO> reservationDTOs);

    default DetailEmplacement detailEmplacementFromId(Long id) {
        if (id == null) {
            return null;
        }
        DetailEmplacement detailEmplacement = new DetailEmplacement();
        detailEmplacement.setId(id);
        return detailEmplacement;
    }
}
