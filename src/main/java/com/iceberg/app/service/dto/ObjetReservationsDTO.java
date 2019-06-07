package com.iceberg.app.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

import com.iceberg.app.domain.Objet;
import com.iceberg.app.domain.Reservation;

public class ObjetReservationsDTO implements Serializable{
	
	private String qui;
	private String strDateReservation;
	
	private Objet objet;
	private List<Reservation > lesReservationsEnCours;
	
	public Objet getObjet() {
		return objet;
	}
	public void setObjet(Objet objet) {
		this.objet = objet;
	}
	
	public List<Reservation > getLesReservationsEnCours() {
		return lesReservationsEnCours;
	}
	public void setLesReservationsEnCours(List<Reservation > lesReservationsEnCours) {
		this.lesReservationsEnCours = lesReservationsEnCours;
	}
	public String getQui() {
		return qui;
	}
	public void setQui(String qui) {
		this.qui = qui;
	}
	public String getDateReservation() {
		return strDateReservation;
	}
	public void setDateReservation(String dateReservation) {
		this.strDateReservation = dateReservation;
	}
}
