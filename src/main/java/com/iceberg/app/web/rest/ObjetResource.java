package com.iceberg.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.iceberg.app.domain.DetailEmplacement;
import com.iceberg.app.domain.Emplacement;
import com.iceberg.app.domain.Objet;
import com.iceberg.app.domain.ObjetCaracteristiques;
import com.iceberg.app.domain.Reservation;
import com.iceberg.app.repository.ObjetCaracteristiquesRepository;
import com.iceberg.app.repository.ObjetRepository;
import com.iceberg.app.web.rest.util.HeaderUtil;
import com.iceberg.app.web.rest.util.PaginationUtil;
import com.iceberg.app.service.dto.ObjetDTO;
import com.iceberg.app.service.mapper.ObjetMapper;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * REST controller for managing Objet.
 */
@RestController
@RequestMapping("/api")
public class ObjetResource {

    private final Logger log = LoggerFactory.getLogger(ObjetResource.class);

    private static final String ENTITY_NAME = "objet";
        
    private final ObjetRepository objetRepository;
    
    private final ObjetMapper objetMapper;

    public ObjetResource(ObjetRepository objetRepository, ObjetMapper objetMapper) {
        this.objetRepository = objetRepository;
        this.objetMapper = objetMapper;
    }

    /**
     * POST  /objets : Create a new objet.
     *
     * @param objetDTO the objetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new objetDTO, or with status 400 (Bad Request) if the objet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/objets")
    @Timed
    public ResponseEntity<ObjetDTO> createObjet(@RequestBody ObjetDTO objetDTO) throws URISyntaxException {
        log.debug("REST request to save Objet : {}", objetDTO);
        if (objetDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new objet cannot already have an ID")).body(null);
        }
        Objet objet = objetMapper.objetDTOToObjet(objetDTO);
        objet = objetRepository.save(objet);
        ObjetDTO result = objetMapper.objetToObjetDTO(objet);
        return ResponseEntity.created(new URI("/api/objets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /objets : Updates an existing objet.
     *
     * @param objetDTO the objetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated objetDTO,
     * or with status 400 (Bad Request) if the objetDTO is not valid,
     * or with status 500 (Internal Server Error) if the objetDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/objets")
    @Timed
    public ResponseEntity<ObjetDTO> updateObjet(@RequestBody ObjetDTO objetDTO) throws URISyntaxException {
        log.debug("REST request to update Objet : {}", objetDTO);
        if (objetDTO.getId() == null) {
            return createObjet(objetDTO);
        }
        Objet objet = objetMapper.objetDTOToObjet(objetDTO);
        objet = objetRepository.save(objet);
        ObjetDTO result = objetMapper.objetToObjetDTO(objet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, objetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /objets : get all the objets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of objets in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/objets")
    @Timed
    public ResponseEntity<List<ObjetDTO>> getAllObjets(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Objets");
        Page<Objet> page = objetRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/objets");
        return new ResponseEntity<>(objetMapper.objetsToObjetDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /objets/:id : get the "id" objet.
     *
     * @param id the id of the objetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the objetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/objets/{id}")
    @Timed
    public ResponseEntity<ObjetDTO> getObjet(@PathVariable Long id) {
        log.debug("REST request to get Objet : {}", id);
        System.out.println("---------------------------  getObjet -----------------------------------");
        //search objet
        Objet objet = objetRepository.findOne(id);
        
        ObjetDTO objetDTO = objetMapper.objetToObjetDTO(objet);
        
        //search objet caracteristiques
        Iterator<ObjetCaracteristiques> itObjetCaracteristiques  = objet.getCaracteristiques().iterator();
        Set<ObjetCaracteristiques> setTailles = new HashSet<>();
        while(itObjetCaracteristiques.hasNext()){
        	ObjetCaracteristiques item = itObjetCaracteristiques.next();
        	
        	item.setObjet(null);
        	switch(item.getCaracteristique()){
        		case COULEUR : objetDTO.setCouleur(item); break;
        		case TYPE : objetDTO.setType(item); break;
        		case TAILLE : setTailles.add(item); break;
        	}
        }
        objetDTO.setTailles(setTailles);
        
        //1 emplacement
        Emplacement emplacement = null;
        Set<Emplacement> lEmp = objet.getEmplacements();
        Iterator<Emplacement> itLEmp = lEmp.iterator();
        while(itLEmp.hasNext()){
        	emplacement = itLEmp.next();
        	emplacement.setObjet(null);
        }
    	
        Set<Reservation> setResa = new HashSet(10);
        Iterator<DetailEmplacement> itD = emplacement.getDetailEmplacements().iterator();
        while(itD.hasNext()){
        		DetailEmplacement itemDEmpl = itD.next();
        		System.out.println("DetailEmpla -> "+itemDEmpl);
        		Iterator<Reservation> itResa = itemDEmpl.getReservations().iterator();
        		System.out.println("Les résas -> "+itemDEmpl.getReservations());
        		while(itResa.hasNext()){
        			Reservation itemResa = itResa.next();
        			if(itemResa.getQteResa().intValue() - itemResa.getQteRet().intValue() > 0){
        				setResa.add(itemResa);
        			}
        		}
        }
        
        objetDTO.setEmplacement(emplacement);
        objetDTO.setDetailEmplacement(emplacement.getDetailEmplacements());
        objetDTO.setLesReservations(setResa);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(objetDTO));
    }

    /**
     * DELETE  /objets/:id : delete the "id" objet.
     *
     * @param id the id of the objetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/objets/{id}")
    @Timed
    public ResponseEntity<Void> deleteObjet(@PathVariable Long id) {
        log.debug("REST request to delete Objet : {}", id);
        
        //delete on objetCaracteristiques
        
        //delete on deplacement
        
        //delete on detail deplacement
        
        //delete on reservation
        
        //delete on objet
        objetRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * GET  /objetsByCaracteristiques/:caracteristiques : get objet by caracteristics.
     *
     * @param id the id of the objetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the objetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/objetsByCaracteristiques/{userId}/{type}/{couleur}/{taille}/{empl}")
    @Transactional
    public ResponseEntity<List<ObjetDTO>> getObjetByCaracteristiques(@PathVariable Long userId,@PathVariable String type,@PathVariable String couleur,@PathVariable String taille, @PathVariable String empl) {
        log.debug("REST request to get Objet : {}", 1);
        int LIMIT = 75;
        //param
        String pType = type;
        if(pType.equals("@"))
        	pType=null;
        String pCouleur = couleur;
        if(pCouleur.equals("@"))
        	pCouleur=null;
        String pEmpl = empl;
        if(pEmpl.equals("@"))
        	pEmpl = null;
        //instruction IN -> transform to List
        List<String> lTaille = new ArrayList<String>(10);
        String pTaille = taille;
        if(pTaille.equals("@")){
        	lTaille.add("%");
        }else {
        	StringTokenizer strTokenizer = new StringTokenizer(pTaille, ",");
        	while(strTokenizer.hasMoreTokens()){
        		lTaille.add(strTokenizer.nextToken());
        	}
        }
        
        Stream<Objet> stream =objetRepository.findByCaracteristiques(userId, pType,pCouleur, lTaille , pEmpl);

        List<Objet> lObjets = new ArrayList<Objet>(100);
        stream.limit(LIMIT).forEach(e -> lObjets.add(e));
        stream.close();
        
        List<ObjetDTO> lObjetDTO = objetMapper.objetsToObjetDTOs(lObjets);
        if(lObjetDTO.size() == LIMIT){
        	//recherche exact du nombre d'éléments
            Number count=  objetRepository.countByCaracteristiques(userId, pType,pCouleur, lTaille , pEmpl);
            lObjetDTO.get(0).setNbEltsTotal(count.longValue());
        }
        
        return Optional.ofNullable(lObjetDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    

}
