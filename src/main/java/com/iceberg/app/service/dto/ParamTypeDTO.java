package com.iceberg.app.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ParamType entity.
 */
public class ParamTypeDTO implements Serializable {

    private Long id;

    private String type;

    private String libelle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

        ParamTypeDTO paramTypeDTO = (ParamTypeDTO) o;

        if ( ! Objects.equals(id, paramTypeDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ParamTypeDTO{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", libelle='" + libelle + "'" +
            '}';
    }
}
