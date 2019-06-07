package com.iceberg.app.repository;

import com.iceberg.app.domain.DetailEmplacement;
import com.iceberg.app.domain.Objet;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.stream.Stream;

/**
 * Spring Data JPA repository for the Objet entity.
 */
@SuppressWarnings("unused")
public interface ObjetRepository extends JpaRepository<Objet,Long> {

    @Query("select objet from Objet objet where objet.user.login = ?#{principal.username}")
    List<Objet> findByUserIsCurrentUser();

    @Query("SELECT distinct objet FROM Objet objet where objet.user.id=?1 and (?2 is null or  exists ( from objet.caracteristiques as car where car.caracteristique='TYPE' and car.valeur=?2)) and (?3 is null or  exists ( from objet.caracteristiques as car where car.caracteristique='COULEUR' and car.valeur=?3)) and ( '%' in (?4) or exists ( from objet.caracteristiques as car where car.caracteristique='TAILLE' and car.valeur IN (?4))) and ( ?5 is null or exists ( from objet.emplacements as emp where emp.paramEmpl.ref = ?5)) order by dateDepot")
    Stream<Objet> findByCaracteristiques(Long user,String type, String couleur, List<String> taille, String emplRef);

    @Query("SELECT count(objet.id) FROM Objet objet where objet.user.id=?1 and (?2 is null or  exists ( from objet.caracteristiques as car where car.caracteristique='TYPE' and car.valeur=?2)) and (?3 is null or  exists ( from objet.caracteristiques as car where car.caracteristique='COULEUR' and car.valeur=?3)) and ( '%' in (?4) or exists ( from objet.caracteristiques as car where car.caracteristique='TAILLE' and car.valeur IN (?4))) and ( ?5 is null or exists ( from objet.emplacements as emp where emp.paramEmpl.ref = ?5))")
    Number countByCaracteristiques(Long user,String type, String couleur, List<String> taille, String emplRef);
    
    @Query("select detEmp from DetailEmplacement detEmp where detEmp.emplacement.id = ?1")
    List<DetailEmplacement> findDetailEmplacementBy(Long idEmp);
    
    @Modifying
    @Query("delete from ObjetCaracteristiques objCar where objCar.objet.id=?1")
    void deleteOnObjectCaracteristiques(Long idObjet);
}
