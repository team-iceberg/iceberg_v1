package com.iceberg.app.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Emplacement entity.
 */
public class EmplacementDTO implements Serializable {

    private Long id;

    private Integer qteTotal;

    private Long objetId;

    private Long paramEmplId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getQteTotal() {
        return qteTotal;
    }

    public void setQteTotal(Integer qteTotal) {
        this.qteTotal = qteTotal;
    }

    public Long getObjetId() {
        return objetId;
    }

    public void setObjetId(Long objetId) {
        this.objetId = objetId;
    }

    public Long getParamEmplId() {
        return paramEmplId;
    }

    public void setParamEmplId(Long paramEmplId) {
        this.paramEmplId = paramEmplId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmplacementDTO emplacementDTO = (EmplacementDTO) o;

        if ( ! Objects.equals(id, emplacementDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EmplacementDTO{" +
            "id=" + id +
            ", qteTotal='" + qteTotal + "'" +
            '}';
    }
}
