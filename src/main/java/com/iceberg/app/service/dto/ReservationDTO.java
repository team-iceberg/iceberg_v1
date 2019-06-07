package com.iceberg.app.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.iceberg.app.domain.DetailEmplacement;
import com.iceberg.app.domain.Objet;

import java.util.Objects;

/**
 * A DTO for the Reservation entity.
 */
public class ReservationDTO implements Serializable {

    private Long id;

    private ZonedDateTime dateReservation;

    private String qui;

    private Integer qteResa;

    private Integer qteRet;

    private Long detailEmplacementId;
    
    private DetailEmplacement detailEmplacement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ZonedDateTime getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(ZonedDateTime dateReservation) {
        this.dateReservation = dateReservation;
    }
    public String getQui() {
        return qui;
    }

    public void setQui(String qui) {
        this.qui = qui;
    }
    public Integer getQteResa() {
        return qteResa;
    }

    public void setQteResa(Integer qteResa) {
        this.qteResa = qteResa;
    }
    public Integer getQteRet() {
        return qteRet;
    }

    public void setQteRet(Integer qteRet) {
        this.qteRet = qteRet;
    }

    public Long getDetailEmplacementId() {
        return detailEmplacementId;
    }

    public void setDetailEmplacementId(Long detailEmplacementId) {
        this.detailEmplacementId = detailEmplacementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReservationDTO reservationDTO = (ReservationDTO) o;

        if ( ! Objects.equals(id, reservationDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
            "id=" + id +
            ", dateReservation='" + dateReservation + "'" +
            ", qui='" + qui + "'" +
            ", qteResa='" + qteResa + "'" +
            ", qteRet='" + qteRet + "'" +
            '}';
    }


	public DetailEmplacement getDetailEmplacement() {
		return detailEmplacement;
	}

	public void setDetailEmplacement(DetailEmplacement detailEmplacement) {
		this.detailEmplacement = detailEmplacement;
	}
}
