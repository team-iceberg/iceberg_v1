package com.iceberg.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.iceberg.app.domain.Emplacement;

import com.iceberg.app.repository.EmplacementRepository;
import com.iceberg.app.web.rest.util.HeaderUtil;
import com.iceberg.app.web.rest.util.PaginationUtil;
import com.iceberg.app.service.dto.EmplacementDTO;
import com.iceberg.app.service.mapper.EmplacementMapper;
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
 * REST controller for managing Emplacement.
 */
@RestController
@RequestMapping("/api")
public class EmplacementResource {

    private final Logger log = LoggerFactory.getLogger(EmplacementResource.class);

    private static final String ENTITY_NAME = "emplacement";
        
    private final EmplacementRepository emplacementRepository;

    private final EmplacementMapper emplacementMapper;

    public EmplacementResource(EmplacementRepository emplacementRepository, EmplacementMapper emplacementMapper) {
        this.emplacementRepository = emplacementRepository;
        this.emplacementMapper = emplacementMapper;
    }

    /**
     * POST  /emplacements : Create a new emplacement.
     *
     * @param emplacementDTO the emplacementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new emplacementDTO, or with status 400 (Bad Request) if the emplacement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/emplacements")
    @Timed
    public ResponseEntity<EmplacementDTO> createEmplacement(@RequestBody EmplacementDTO emplacementDTO) throws URISyntaxException {
        log.debug("REST request to save Emplacement : {}", emplacementDTO);
        if (emplacementDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new emplacement cannot already have an ID")).body(null);
        }
        Emplacement emplacement = emplacementMapper.emplacementDTOToEmplacement(emplacementDTO);
        emplacement = emplacementRepository.save(emplacement);
        EmplacementDTO result = emplacementMapper.emplacementToEmplacementDTO(emplacement);
        return ResponseEntity.created(new URI("/api/emplacements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /emplacements : Updates an existing emplacement.
     *
     * @param emplacementDTO the emplacementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated emplacementDTO,
     * or with status 400 (Bad Request) if the emplacementDTO is not valid,
     * or with status 500 (Internal Server Error) if the emplacementDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/emplacements")
    @Timed
    public ResponseEntity<EmplacementDTO> updateEmplacement(@RequestBody EmplacementDTO emplacementDTO) throws URISyntaxException {
        log.debug("REST request to update Emplacement : {}", emplacementDTO);
        if (emplacementDTO.getId() == null) {
            return createEmplacement(emplacementDTO);
        }
        Emplacement emplacement = emplacementMapper.emplacementDTOToEmplacement(emplacementDTO);
        emplacement = emplacementRepository.save(emplacement);
        EmplacementDTO result = emplacementMapper.emplacementToEmplacementDTO(emplacement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, emplacementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /emplacements : get all the emplacements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of emplacements in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/emplacements")
    @Timed
    public ResponseEntity<List<EmplacementDTO>> getAllEmplacements(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Emplacements");
        Page<Emplacement> page = emplacementRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/emplacements");
        return new ResponseEntity<>(emplacementMapper.emplacementsToEmplacementDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /emplacements/:id : get the "id" emplacement.
     *
     * @param id the id of the emplacementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the emplacementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/emplacements/{id}")
    @Timed
    public ResponseEntity<EmplacementDTO> getEmplacement(@PathVariable Long id) {
        log.debug("REST request to get Emplacement : {}", id);
        Emplacement emplacement = emplacementRepository.findOne(id);
        EmplacementDTO emplacementDTO = emplacementMapper.emplacementToEmplacementDTO(emplacement);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(emplacementDTO));
    }

    /**
     * DELETE  /emplacements/:id : delete the "id" emplacement.
     *
     * @param id the id of the emplacementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/emplacements/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmplacement(@PathVariable Long id) {
        log.debug("REST request to delete Emplacement : {}", id);
        emplacementRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
