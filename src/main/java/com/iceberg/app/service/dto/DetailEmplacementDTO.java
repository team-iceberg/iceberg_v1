package com.iceberg.app.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.iceberg.app.domain.Reservation;
import com.iceberg.app.domain.enumeration.Caracteristique;

/**
 * A DTO for the DetailEmplacement entity.
 */
public class DetailEmplacementDTO implements Serializable {

    private Long id;

    private String valeurCaracteristique;

    private Integer qteEnCours;

    private Caracteristique caracteristique;

    private Long emplacementId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getValeurCaracteristique() {
        return valeurCaracteristique;
    }

    public void setValeurCaracteristique(String valeurCaracteristique) {
        this.valeurCaracteristique = valeurCaracteristique;
    }
    public Integer getQteEnCours() {
        return qteEnCours;
    }

    public void setQteEnCours(Integer qteEnCours) {
        this.qteEnCours = qteEnCours;
    }
    public Caracteristique getCaracteristique() {
        return caracteristique;
    }

    public void setCaracteristique(Caracteristique caracteristique) {
        this.caracteristique = caracteristique;
    }

    public Long getEmplacementId() {
        return emplacementId;
    }

    public void setEmplacementId(Long emplacementId) {
        this.emplacementId = emplacementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DetailEmplacementDTO detailEmplacementDTO = (DetailEmplacementDTO) o;

        if ( ! Objects.equals(id, detailEmplacementDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DetailEmplacementDTO{" +
            "id=" + id +
            ", valeurCaracteristique='" + valeurCaracteristique + "'" +
            ", qteEnCours='" + qteEnCours + "'" +
            ", caracteristique='" + caracteristique + "'" +
            '}';
    }
}
