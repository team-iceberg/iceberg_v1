package com.iceberg.app.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ParamCouleur entity.
 */
public class ParamCouleurDTO implements Serializable {

    private Long id;

    private String couleur;

    private String codeHexa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }
    public String getCodeHexa() {
        return codeHexa;
    }

    public void setCodeHexa(String codeHexa) {
        this.codeHexa = codeHexa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParamCouleurDTO paramCouleurDTO = (ParamCouleurDTO) o;

        if ( ! Objects.equals(id, paramCouleurDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ParamCouleurDTO{" +
            "id=" + id +
            ", couleur='" + couleur + "'" +
            ", codeHexa='" + codeHexa + "'" +
            '}';
    }
}
