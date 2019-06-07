package com.iceberg.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Objet.
 */
@Entity
@Table(name = "objet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Objet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "date_depot")
    private ZonedDateTime dateDepot;

    @Lob
    @Column(name = "image_1")
    private byte[] image1;

    @Column(name = "image_1_content_type")
    private String image1ContentType;

    @Lob
    @Column(name = "image_2")
    private byte[] image2;

    @Column(name = "image_2_content_type")
    private String image2ContentType;

    @Lob
    @Column(name = "image_3")
    private byte[] image3;

    @Column(name = "image_3_content_type")
    private String image3ContentType;

    @Lob
    @Column(name = "image_4")
    private byte[] image4;

    @Column(name = "image_4_content_type")
    private String image4ContentType;

    @OneToMany(mappedBy = "objet", fetch = FetchType.EAGER, cascade=CascadeType.REMOVE)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Emplacement> emplacements = new HashSet<>();

    @OneToMany(mappedBy = "objet", fetch = FetchType.EAGER, cascade=CascadeType.REMOVE)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    private Set<ObjetCaracteristiques> caracteristiques = new HashSet<>();

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Objet libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public ZonedDateTime getDateDepot() {
        return dateDepot;
    }

    public Objet dateDepot(ZonedDateTime dateDepot) {
        this.dateDepot = dateDepot;
        return this;
    }

    public void setDateDepot(ZonedDateTime dateDepot) {
        this.dateDepot = dateDepot;
    }

    public byte[] getImage1() {
        return image1;
    }

    public Objet image1(byte[] image1) {
        this.image1 = image1;
        return this;
    }

    public void setImage1(byte[] image1) {
        this.image1 = image1;
    }

    public String getImage1ContentType() {
        return image1ContentType;
    }

    public Objet image1ContentType(String image1ContentType) {
        this.image1ContentType = image1ContentType;
        return this;
    }

    public void setImage1ContentType(String image1ContentType) {
        this.image1ContentType = image1ContentType;
    }

    public byte[] getImage2() {
        return image2;
    }

    public Objet image2(byte[] image2) {
        this.image2 = image2;
        return this;
    }

    public void setImage2(byte[] image2) {
        this.image2 = image2;
    }

    public String getImage2ContentType() {
        return image2ContentType;
    }

    public Objet image2ContentType(String image2ContentType) {
        this.image2ContentType = image2ContentType;
        return this;
    }

    public void setImage2ContentType(String image2ContentType) {
        this.image2ContentType = image2ContentType;
    }

    public byte[] getImage3() {
        return image3;
    }

    public Objet image3(byte[] image3) {
        this.image3 = image3;
        return this;
    }

    public void setImage3(byte[] image3) {
        this.image3 = image3;
    }

    public String getImage3ContentType() {
        return image3ContentType;
    }

    public Objet image3ContentType(String image3ContentType) {
        this.image3ContentType = image3ContentType;
        return this;
    }

    public void setImage3ContentType(String image3ContentType) {
        this.image3ContentType = image3ContentType;
    }

    public byte[] getImage4() {
        return image4;
    }

    public Objet image4(byte[] image4) {
        this.image4 = image4;
        return this;
    }

    public void setImage4(byte[] image4) {
        this.image4 = image4;
    }

    public String getImage4ContentType() {
        return image4ContentType;
    }

    public Objet image4ContentType(String image4ContentType) {
        this.image4ContentType = image4ContentType;
        return this;
    }

    public void setImage4ContentType(String image4ContentType) {
        this.image4ContentType = image4ContentType;
    }

    public Set<Emplacement> getEmplacements() {
        return emplacements;
    }

    public Objet emplacements(Set<Emplacement> emplacements) {
        this.emplacements = emplacements;
        return this;
    }

    public Objet addEmplacement(Emplacement emplacement) {
        this.emplacements.add(emplacement);
        emplacement.setObjet(this);
        return this;
    }

    public Objet removeEmplacement(Emplacement emplacement) {
        this.emplacements.remove(emplacement);
        emplacement.setObjet(null);
        return this;
    }

    public void setEmplacements(Set<Emplacement> emplacements) {
        this.emplacements = emplacements;
    }

    public Set<ObjetCaracteristiques> getCaracteristiques() {
        return caracteristiques;
    }

    public Objet caracteristiques(Set<ObjetCaracteristiques> objetCaracteristiques) {
        this.caracteristiques = objetCaracteristiques;
        return this;
    }

    public Objet addCaracteristique(ObjetCaracteristiques objetCaracteristiques) {
        this.caracteristiques.add(objetCaracteristiques);
        objetCaracteristiques.setObjet(this);
        return this;
    }

    public Objet removeCaracteristique(ObjetCaracteristiques objetCaracteristiques) {
        this.caracteristiques.remove(objetCaracteristiques);
        objetCaracteristiques.setObjet(null);
        return this;
    }

    public void setCaracteristiques(Set<ObjetCaracteristiques> objetCaracteristiques) {
        this.caracteristiques = objetCaracteristiques;
    }

    public User getUser() {
        return user;
    }

    public Objet user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Objet objet = (Objet) o;
        if (objet.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, objet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Objet{" +
            "id=" + id +
            ", libelle='" + libelle + "'" +
            ", dateDepot='" + dateDepot + "'" +
            ", image1='" + image1 + "'" +
            ", image1ContentType='" + image1ContentType + "'" +
            ", image2='" + image2 + "'" +
            ", image2ContentType='" + image2ContentType + "'" +
            ", image3='" + image3 + "'" +
            ", image3ContentType='" + image3ContentType + "'" +
            ", image4='" + image4 + "'" +
            ", image4ContentType='" + image4ContentType + "'" +
            '}';
    }
}
