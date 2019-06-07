package com.iceberg.app.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.iceberg.app.domain.enumeration.Caracteristique;

/**
 * A DTO for the ObjetCaracteristiques entity.
 */
public class ObjetCaracteristiquesDTO implements Serializable {

    private Long id;

    private String valeur;

    private Caracteristique caracteristique;

    private Long objetId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
    public Caracteristique getCaracteristique() {
        return caracteristique;
    }

    public void setCaracteristique(Caracteristique caracteristique) {
        this.caracteristique = caracteristique;
    }

    public Long getObjetId() {
        return objetId;
    }

    public void setObjetId(Long objetId) {
        this.objetId = objetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ObjetCaracteristiquesDTO objetCaracteristiquesDTO = (ObjetCaracteristiquesDTO) o;

        if ( ! Objects.equals(id, objetCaracteristiquesDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ObjetCaracteristiquesDTO{" +
            "id=" + id +
            ", valeur='" + valeur + "'" +
            ", caracteristique='" + caracteristique + "'" +
            '}';
    }
}
