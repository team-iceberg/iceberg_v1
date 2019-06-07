package com.iceberg.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ParamTaille.
 */
@Entity
@Table(name = "param_taille")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ParamTaille implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "taille")
    private String taille;

    @Column(name = "libelle")
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

    public ParamTaille taille(String taille) {
        this.taille = taille;
        return this;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    public String getLibelle() {
        return libelle;
    }

    public ParamTaille libelle(String libelle) {
        this.libelle = libelle;
        return this;
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
        ParamTaille paramTaille = (ParamTaille) o;
        if (paramTaille.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, paramTaille.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ParamTaille{" +
            "id=" + id +
            ", taille='" + taille + "'" +
            ", libelle='" + libelle + "'" +
            '}';
    }
}
