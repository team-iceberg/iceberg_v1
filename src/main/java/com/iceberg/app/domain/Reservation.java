package com.iceberg.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Reservation.
 */
@Entity
@Table(name = "reservation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_reservation")
    private ZonedDateTime dateReservation;

    @Column(name = "qui")
    private String qui;

    @Column(name = "qte_resa")
    private Integer qteResa;

    @Column(name = "qte_ret")
    private Integer qteRet;

    @ManyToOne
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

    public Reservation dateReservation(ZonedDateTime dateReservation) {
        this.dateReservation = dateReservation;
        return this;
    }

    public void setDateReservation(ZonedDateTime dateReservation) {
        this.dateReservation = dateReservation;
    }

    public String getQui() {
        return qui;
    }

    public Reservation qui(String qui) {
        this.qui = qui;
        return this;
    }

    public void setQui(String qui) {
        this.qui = qui;
    }

    public Integer getQteResa() {
        return qteResa;
    }

    public Reservation qteResa(Integer qteResa) {
        this.qteResa = qteResa;
        return this;
    }

    public void setQteResa(Integer qteResa) {
        this.qteResa = qteResa;
    }

    public Integer getQteRet() {
        return qteRet;
    }

    public Reservation qteRet(Integer qteRet) {
        this.qteRet = qteRet;
        return this;
    }

    public void setQteRet(Integer qteRet) {
        this.qteRet = qteRet;
    }

    public DetailEmplacement getDetailEmplacement() {
        return detailEmplacement;
    }

    public Reservation detailEmplacement(DetailEmplacement detailEmplacement) {
        this.detailEmplacement = detailEmplacement;
        return this;
    }

    public void setDetailEmplacement(DetailEmplacement detailEmplacement) {
        this.detailEmplacement = detailEmplacement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation reservation = (Reservation) o;
        if (reservation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, reservation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Reservation{" +
            "id=" + id +
            ", dateReservation='" + dateReservation + "'" +
            ", qui='" + qui + "'" +
            ", qteResa='" + qteResa + "'" +
            ", qteRet='" + qteRet + "'" +
            '}';
    }
}
