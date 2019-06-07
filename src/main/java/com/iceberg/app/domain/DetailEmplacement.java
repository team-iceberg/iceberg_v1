package com.iceberg.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.iceberg.app.domain.enumeration.Caracteristique;

/**
 * A DetailEmplacement.
 */
@Entity
@Table(name = "detail_emplacement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DetailEmplacement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "valeur_caracteristique")
    private String valeurCaracteristique;

    @Column(name = "qte_en_cours")
    private Integer qteEnCours;

    @Enumerated(EnumType.STRING)
    @Column(name = "caracteristique")
    private Caracteristique caracteristique;

    @ManyToOne
    private Emplacement emplacement;

    @OneToMany(mappedBy = "detailEmplacement", fetch = FetchType.EAGER)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reservation> reservations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValeurCaracteristique() {
        return valeurCaracteristique;
    }

    public DetailEmplacement valeurCaracteristique(String valeurCaracteristique) {
        this.valeurCaracteristique = valeurCaracteristique;
        return this;
    }

    public void setValeurCaracteristique(String valeurCaracteristique) {
        this.valeurCaracteristique = valeurCaracteristique;
    }

    public Integer getQteEnCours() {
        return qteEnCours;
    }

    public DetailEmplacement qteEnCours(Integer qteEnCours) {
        this.qteEnCours = qteEnCours;
        return this;
    }

    public void setQteEnCours(Integer qteEnCours) {
        this.qteEnCours = qteEnCours;
    }

    public Caracteristique getCaracteristique() {
        return caracteristique;
    }

    public DetailEmplacement caracteristique(Caracteristique caracteristique) {
        this.caracteristique = caracteristique;
        return this;
    }

    public void setCaracteristique(Caracteristique caracteristique) {
        this.caracteristique = caracteristique;
    }

    public Emplacement getEmplacement() {
        return emplacement;
    }

    public DetailEmplacement emplacement(Emplacement emplacement) {
        this.emplacement = emplacement;
        return this;
    }

    public void setEmplacement(Emplacement emplacement) {
        this.emplacement = emplacement;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public DetailEmplacement reservations(Set<Reservation> reservations) {
        this.reservations = reservations;
        return this;
    }

    public DetailEmplacement addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setDetailEmplacement(this);
        return this;
    }

    public DetailEmplacement removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
        reservation.setDetailEmplacement(null);
        return this;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DetailEmplacement detailEmplacement = (DetailEmplacement) o;
        if (detailEmplacement.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, detailEmplacement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DetailEmplacement{" +
            "id=" + id +
            ", valeurCaracteristique='" + valeurCaracteristique + "'" +
            ", qteEnCours='" + qteEnCours + "'" +
            ", caracteristique='" + caracteristique + "'" +
            '}';
    }
}
