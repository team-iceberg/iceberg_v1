package com.iceberg.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.iceberg.app.domain.enumeration.Caracteristique;

/**
 * A ObjetCaracteristiques.
 */
@Entity
@Table(name = "objet_caracteristiques")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ObjetCaracteristiques implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "valeur")
    private String valeur;

    @Enumerated(EnumType.STRING)
    @Column(name = "caracteristique")
    private Caracteristique caracteristique;

    @ManyToOne
    private Objet objet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValeur() {
        return valeur;
    }

    public ObjetCaracteristiques valeur(String valeur) {
        this.valeur = valeur;
        return this;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public Caracteristique getCaracteristique() {
        return caracteristique;
    }

    public ObjetCaracteristiques caracteristique(Caracteristique caracteristique) {
        this.caracteristique = caracteristique;
        return this;
    }

    public void setCaracteristique(Caracteristique caracteristique) {
        this.caracteristique = caracteristique;
    }

    public Objet getObjet() {
        return objet;
    }

    public ObjetCaracteristiques objet(Objet objet) {
        this.objet = objet;
        return this;
    }

    public void setObjet(Objet objet) {
        this.objet = objet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ObjetCaracteristiques objetCaracteristiques = (ObjetCaracteristiques) o;
        if (objetCaracteristiques.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, objetCaracteristiques.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ObjetCaracteristiques{" +
            "id=" + id +
            ", valeur='" + valeur + "'" +
            ", caracteristique='" + caracteristique + "'" +
            '}';
    }
}
