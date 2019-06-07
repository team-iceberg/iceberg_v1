package com.iceberg.app.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ParamTaille entity.
 */
public class ParamTailleDTO implements Serializable {

    private Long id;

    private String taille;

    private String libelle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }
    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParamTailleDTO paramTailleDTO = (ParamTailleDTO) o;

        if ( ! Objects.equals(id, paramTailleDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ParamTailleDTO{" +
            "id=" + id +
            ", taille='" + taille + "'" +
            ", libelle='" + libelle + "'" +
            '}';
    }
}
