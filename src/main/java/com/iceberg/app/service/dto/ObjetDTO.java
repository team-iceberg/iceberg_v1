package com.iceberg.app.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

import com.iceberg.app.domain.DetailEmplacement;
import com.iceberg.app.domain.Emplacement;
import com.iceberg.app.domain.ObjetCaracteristiques;
import com.iceberg.app.domain.Reservation;

/**
 * A DTO for the Objet entity.
 */
public class ObjetDTO implements Serializable {

    private Long id;

    private String libelle;

    private ZonedDateTime dateDepot;
    
    private ObjetCaracteristiques type;
    private ObjetCaracteristiques couleur;
    private Set<ObjetCaracteristiques> tailles;
    private Emplacement emplacement;
    private Set<DetailEmplacement> detailEmplacement;
    private Set<Reservation> lesReservations;
    
    @Lob
    private byte[] image1;
    private String image1ContentType;

    @Lob
    private byte[] image2;
    private String image2ContentType;

    @Lob
    private byte[] image3;
    private String image3ContentType;

    @Lob
    private byte[] image4;
    private String image4ContentType;
    
    private Long position;
    private Long nbEltsTotal;
    
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    public ZonedDateTime getDateDepot() {
        return dateDepot;
    }

    public void setDateDepot(ZonedDateTime dateDepot) {
        this.dateDepot = dateDepot;
    }
    public byte[] getImage1() {
        return image1;
    }

    public void setImage1(byte[] image1) {
        this.image1 = image1;
    }

    public String getImage1ContentType() {
        return image1ContentType;
    }

    public void setImage1ContentType(String image1ContentType) {
        this.image1ContentType = image1ContentType;
    }
    public byte[] getImage2() {
        return image2;
    }

    public void setImage2(byte[] image2) {
        this.image2 = image2;
    }

    public String getImage2ContentType() {
        return image2ContentType;
    }

    public void setImage2ContentType(String image2ContentType) {
        this.image2ContentType = image2ContentType;
    }
    public byte[] getImage3() {
        return image3;
    }

    public void setImage3(byte[] image3) {
        this.image3 = image3;
    }

    public String getImage3ContentType() {
        return image3ContentType;
    }

    public void setImage3ContentType(String image3ContentType) {
        this.image3ContentType = image3ContentType;
    }
    public byte[] getImage4() {
        return image4;
    }

    public void setImage4(byte[] image4) {
        this.image4 = image4;
    }

    public String getImage4ContentType() {
        return image4ContentType;
    }

    public void setImage4ContentType(String image4ContentType) {
        this.image4ContentType = image4ContentType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ObjetDTO objetDTO = (ObjetDTO) o;

        if ( ! Objects.equals(id, objetDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ObjetDTO{" +
            "id=" + id +
            ", libelle='" + libelle + "'" +
            ", dateDepot='" + dateDepot + "'" +
            ", image1='" + image1 + "'" +
            ", image2='" + image2 + "'" +
            ", image3='" + image3 + "'" +
            ", image4='" + image4 + "'" +
            '}';
    }

	public ObjetCaracteristiques getType() {
		return type;
	}

	public void setType(ObjetCaracteristiques type) {
		this.type = type;
	}

	public ObjetCaracteristiques getCouleur() {
		return couleur;
	}

	public void setCouleur(ObjetCaracteristiques couleur) {
		this.couleur = couleur;
	}

	public Set<ObjetCaracteristiques> getTailles() {
		return tailles;
	}

	public void setTailles(Set<ObjetCaracteristiques> tailles) {
		this.tailles = tailles;
	}

	public Emplacement getEmplacement() {
		return emplacement;
	}

	public void setEmplacement(Emplacement emplacement) {
		this.emplacement = emplacement;
	}

	public Set<DetailEmplacement> getDetailEmplacement() {
		return detailEmplacement;
	}

	public void setDetailEmplacement(Set<DetailEmplacement> detailEmplacement) {
		this.detailEmplacement = detailEmplacement;
	}

	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

	public Long getNbEltsTotal() {
		return nbEltsTotal;
	}

	public void setNbEltsTotal(Long nbEltsTotal) {
		this.nbEltsTotal = nbEltsTotal;
	}

	public Set<Reservation> getLesReservations() {
		return lesReservations;
	}

	public void setLesReservations(Set<Reservation> lesReservations) {
		this.lesReservations = lesReservations;
	}

}
