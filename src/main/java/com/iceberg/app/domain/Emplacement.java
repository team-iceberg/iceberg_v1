package com.iceberg.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Emplacement.
 */
@Entity
@Table(name = "emplacement")
public class Emplacement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "qte_total")
    private Integer qteTotal;

    @ManyToOne
    private Objet objet;

    @OneToMany(mappedBy = "emplacement", fetch = FetchType.EAGER, cascade=CascadeType.REMOVE)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    private Set<DetailEmplacement> detailEmplacements = new HashSet<>();

    @ManyToOne
    private ParamEmpl paramEmpl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQteTotal() {
        return qteTotal;
    }

    public Emplacement qteTotal(Integer qteTotal) {
        this.qteTotal = qteTotal;
        return this;
    }

    public void setQteTotal(Integer qteTotal) {
        this.qteTotal = qteTotal;
    }

    public Objet getObjet() {
        return objet;
    }

    public Emplacement objet(Objet objet) {
        this.objet = objet;
        return this;
    }

    public void setObjet(Objet objet) {
        this.objet = objet;
    }

    public Set<DetailEmplacement> getDetailEmplacements() {
        return detailEmplacements;
    }

    public Emplacement detailEmplacements(Set<DetailEmplacement> detailEmplacements) {
        this.detailEmplacements = detailEmplacements;
        return this;
    }

    public Emplacement addDetailEmplacement(DetailEmplacement detailEmplacement) {
        this.detailEmplacements.add(detailEmplacement);
        detailEmplacement.setEmplacement(this);
        return this;
    }

    public Emplacement removeDetailEmplacement(DetailEmplacement detailEmplacement) {
        this.detailEmplacements.remove(detailEmplacement);
        detailEmplacement.setEmplacement(null);
        return this;
    }

    public void setDetailEmplacements(Set<DetailEmplacement> detailEmplacements) {
        this.detailEmplacements = detailEmplacements;
    }

    public ParamEmpl getParamEmpl() {
        return paramEmpl;
    }

    public Emplacement paramEmpl(ParamEmpl paramEmpl) {
        this.paramEmpl = paramEmpl;
        return this;
    }

    public void setParamEmpl(ParamEmpl paramEmpl) {
        this.paramEmpl = paramEmpl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Emplacement emplacement = (Emplacement) o;
        if (emplacement.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, emplacement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Emplacement{" +
            "id=" + id +
            ", qteTotal='" + qteTotal + "'" +
            '}';
    }
}
