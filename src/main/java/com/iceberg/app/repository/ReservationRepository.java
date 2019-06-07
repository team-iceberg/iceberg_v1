package com.iceberg.app.repository;

import com.iceberg.app.domain.Objet;
import com.iceberg.app.domain.Reservation;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.stream.Stream;

/**
 * Spring Data JPA repository for the Reservation entity.
 */
@SuppressWarnings("unused")
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    @Query("SELECT reservation FROM Reservation reservation where reservation.qteResa-reservation.qteRet <> 0")
    Stream<Reservation> findReservationsEnCours();

}
