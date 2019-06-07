package com.iceberg.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.iceberg.app.domain.ObjetCaracteristiques;

import com.iceberg.app.repository.ObjetCaracteristiquesRepository;
import com.iceberg.app.web.rest.util.HeaderUtil;
import com.iceberg.app.web.rest.util.PaginationUtil;
import com.iceberg.app.service.dto.ObjetCaracteristiquesDTO;
import com.iceberg.app.service.mapper.ObjetCaracteristiquesMapper;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing ObjetCaracteristiques.
 */
@RestController
@RequestMapping("/api")
public class ObjetCaracteristiquesResource {

    private final Logger log = LoggerFactory.getLogger(ObjetCaracteristiquesResource.class);

    private static final String ENTITY_NAME = "objetCaracteristiques";
        
    private final ObjetCaracteristiquesRepository objetCaracteristiquesRepository;

    private final ObjetCaracteristiquesMapper objetCaracteristiquesMapper;

    public ObjetCaracteristiquesResource(ObjetCaracteristiquesRepository objetCaracteristiquesRepository, ObjetCaracteristiquesMapper objetCaracteristiquesMapper) {
        this.objetCaracteristiquesRepository = objetCaracteristiquesRepository;
        this.objetCaracteristiquesMapper = objetCaracteristiquesMapper;
    }

    /**
     * POST  /objet-caracteristiques : Create a new objetCaracteristiques.
     *
     * @param objetCaracteristiquesDTO the objetCaracteristiquesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new objetCaracteristiquesDTO, or with status 400 (Bad Request) if the objetCaracteristiques has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/objet-caracteristiques")
    @Timed
    public ResponseEntity<ObjetCaracteristiquesDTO> createObjetCaracteristiques(@RequestBody ObjetCaracteristiquesDTO objetCaracteristiquesDTO) throws URISyntaxException {
        log.debug("REST request to save ObjetCaracteristiques : {}", objetCaracteristiquesDTO);
        if (objetCaracteristiquesDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new objetCaracteristiques cannot already have an ID")).body(null);
        }
        ObjetCaracteristiques objetCaracteristiques = objetCaracteristiquesMapper.objetCaracteristiquesDTOToObjetCaracteristiques(objetCaracteristiquesDTO);
        objetCaracteristiques = objetCaracteristiquesRepository.save(objetCaracteristiques);
        ObjetCaracteristiquesDTO result = objetCaracteristiquesMapper.objetCaracteristiquesToObjetCaracteristiquesDTO(objetCaracteristiques);
        return ResponseEntity.created(new URI("/api/objet-caracteristiques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /objet-caracteristiques : Updates an existing objetCaracteristiques.
     *
     * @param objetCaracteristiquesDTO the objetCaracteristiquesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated objetCaracteristiquesDTO,
     * or with status 400 (Bad Request) if the objetCaracteristiquesDTO is not valid,
     * or with status 500 (Internal Server Error) if the objetCaracteristiquesDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/objet-caracteristiques")
    @Timed
    public ResponseEntity<ObjetCaracteristiquesDTO> updateObjetCaracteristiques(@RequestBody ObjetCaracteristiquesDTO objetCaracteristiquesDTO) throws URISyntaxException {
        log.debug("REST request to update ObjetCaracteristiques : {}", objetCaracteristiquesDTO);
        if (objetCaracteristiquesDTO.getId() == null) {
            return createObjetCaracteristiques(objetCaracteristiquesDTO);
        }
        ObjetCaracteristiques objetCaracteristiques = objetCaracteristiquesMapper.objetCaracteristiquesDTOToObjetCaracteristiques(objetCaracteristiquesDTO);
        objetCaracteristiques = objetCaracteristiquesRepository.save(objetCaracteristiques);
        ObjetCaracteristiquesDTO result = objetCaracteristiquesMapper.objetCaracteristiquesToObjetCaracteristiquesDTO(objetCaracteristiques);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, objetCaracteristiquesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /objet-caracteristiques : get all the objetCaracteristiques.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of objetCaracteristiques in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/objet-caracteristiques")
    @Timed
    public ResponseEntity<List<ObjetCaracteristiquesDTO>> getAllObjetCaracteristiques(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ObjetCaracteristiques");
        Page<ObjetCaracteristiques> page = objetCaracteristiquesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/objet-caracteristiques");
        return new ResponseEntity<>(objetCaracteristiquesMapper.objetCaracteristiquesToObjetCaracteristiquesDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /objet-caracteristiques/:id : get the "id" objetCaracteristiques.
     *
     * @param id the id of the objetCaracteristiquesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the objetCaracteristiquesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/objet-caracteristiques/{id}")
    @Timed
    public ResponseEntity<ObjetCaracteristiquesDTO> getObjetCaracteristiques(@PathVariable Long id) {
        log.debug("REST request to get ObjetCaracteristiques : {}", id);
        ObjetCaracteristiques objetCaracteristiques = objetCaracteristiquesRepository.findOne(id);
        ObjetCaracteristiquesDTO objetCaracteristiquesDTO = objetCaracteristiquesMapper.objetCaracteristiquesToObjetCaracteristiquesDTO(objetCaracteristiques);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(objetCaracteristiquesDTO));
    }

    /**
     * DELETE  /objet-caracteristiques/:id : delete the "id" objetCaracteristiques.
     *
     * @param id the id of the objetCaracteristiquesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/objet-caracteristiques/{id}")
    @Timed
    public ResponseEntity<Void> deleteObjetCaracteristiques(@PathVariable Long id) {
        log.debug("REST request to delete ObjetCaracteristiques : {}", id);
        objetCaracteristiquesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
