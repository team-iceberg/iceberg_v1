package com.iceberg.app.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ParamEmpl entity.
 */
public class ParamEmplDTO implements Serializable {

    private Long id;

    private String ref;

    private String libelle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
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

        ParamEmplDTO paramEmplDTO = (ParamEmplDTO) o;

        if ( ! Objects.equals(id, paramEmplDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ParamEmplDTO{" +
            "id=" + id +
            ", ref='" + ref + "'" +
            ", libelle='" + libelle + "'" +
            '}';
    }
}
