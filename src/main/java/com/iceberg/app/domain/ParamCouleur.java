package com.iceberg.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ParamCouleur.
 */
@Entity
@Table(name = "param_couleur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ParamCouleur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "couleur")
    private String couleur;

    @Column(name = "code_hexa")
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

    public ParamCouleur couleur(String couleur) {
        this.couleur = couleur;
        return this;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getCodeHexa() {
        return codeHexa;
    }

    public ParamCouleur codeHexa(String codeHexa) {
        this.codeHexa = codeHexa;
        return this;
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
        ParamCouleur paramCouleur = (ParamCouleur) o;
        if (paramCouleur.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, paramCouleur.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ParamCouleur{" +
            "id=" + id +
            ", couleur='" + couleur + "'" +
            ", codeHexa='" + codeHexa + "'" +
            '}';
    }
}
