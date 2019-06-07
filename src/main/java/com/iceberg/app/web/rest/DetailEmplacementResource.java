package com.iceberg.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.iceberg.app.domain.DetailEmplacement;

import com.iceberg.app.repository.DetailEmplacementRepository;
import com.iceberg.app.service.dto.ReservationDTO;
import com.iceberg.app.web.rest.util.HeaderUtil;
import com.iceberg.app.web.rest.util.PaginationUtil;
import com.iceberg.app.service.dto.DetailEmplacementDTO;
import com.iceberg.app.service.mapper.DetailEmplacementMapper;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing DetailEmplacement.
 */
@RestController
@RequestMapping("/api")
public class DetailEmplacementResource {

    private final Logger log = LoggerFactory.getLogger(DetailEmplacementResource.class);

    private static final String ENTITY_NAME = "detailEmplacement";

    private final DetailEmplacementRepository detailEmplacementRepository;

    private final DetailEmplacementMapper detailEmplacementMapper;

    public DetailEmplacementResource(DetailEmplacementRepository detailEmplacementRepository, DetailEmplacementMapper detailEmplacementMapper) {
        this.detailEmplacementRepository = detailEmplacementRepository;
        this.detailEmplacementMapper = detailEmplacementMapper;
    }

    /**
     * POST  /detail-emplacements : Create a new detailEmplacement.
     *
     * @param detailEmplacementDTO the detailEmplacementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new detailEmplacementDTO, or with status 400 (Bad Request) if the detailEmplacement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/detail-emplacements")
    @Timed
    public ResponseEntity<DetailEmplacementDTO> createDetailEmplacement(@RequestBody DetailEmplacementDTO detailEmplacementDTO) throws URISyntaxException {
        log.debug("REST request to save DetailEmplacement : {}", detailEmplacementDTO);
        if (detailEmplacementDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new detailEmplacement cannot already have an ID")).body(null);
        }
        DetailEmplacement detailEmplacement = detailEmplacementMapper.detailEmplacementDTOToDetailEmplacement(detailEmplacementDTO);
        detailEmplacement = detailEmplacementRepository.save(detailEmplacement);
        DetailEmplacementDTO result = detailEmplacementMapper.detailEmplacementToDetailEmplacementDTO(detailEmplacement);
        return ResponseEntity.created(new URI("/api/detail-emplacements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /detail-emplacements : Updates an existing detailEmplacement.
     *
     * @param detailEmplacementDTO the detailEmplacementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated detailEmplacementDTO,
     * or with status 400 (Bad Request) if the detailEmplacementDTO is not valid,
     * or with status 500 (Internal Server Error) if the detailEmplacementDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/detail-emplacements")
    @Timed
    public ResponseEntity<DetailEmplacementDTO> updateDetailEmplacement(@RequestBody DetailEmplacementDTO detailEmplacementDTO) throws URISyntaxException {
        log.debug("REST request to update DetailEmplacement : {}", detailEmplacementDTO);
        if (detailEmplacementDTO.getId() == null) {
            return createDetailEmplacement(detailEmplacementDTO);
        }
        DetailEmplacement detailEmplacement = detailEmplacementMapper.detailEmplacementDTOToDetailEmplacement(detailEmplacementDTO);
        detailEmplacement = detailEmplacementRepository.save(detailEmplacement);
        DetailEmplacementDTO result = detailEmplacementMapper.detailEmplacementToDetailEmplacementDTO(detailEmplacement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, detailEmplacementDTO.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /detail-emplacements : Updates an existing detailEmplacement.
     *
     * @param detailEmplacementDTOS
     * @return the ResponseEntity with status 200 (OK) and with body the updated detailEmplacementDTO,
     * or with status 400 (Bad Request) if the detailEmplacementDTO is not valid,
     * or with status 500 (Internal Server Error) if the detailEmplacementDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-qty-detail-emplacements")
    @Timed
    public ResponseEntity<List<DetailEmplacementDTO>> updateQtyDetailObject(@RequestBody List<DetailEmplacementDTO> detailEmplacementDTOS) throws URISyntaxException {
        log.debug("REST request to update DetailEmplacement : {}", detailEmplacementDTOS);
        List<DetailEmplacementDTO> resultat = new ArrayList<>();

        for (DetailEmplacementDTO detailEmplacementDTO : detailEmplacementDTOS) {
            DetailEmplacement detailEmplacement = detailEmplacementRepository.findOne(detailEmplacementDTO.getId());
            detailEmplacement.setQteEnCours(detailEmplacementDTO.getQteEnCours());
            resultat.add(detailEmplacementMapper.detailEmplacementToDetailEmplacementDTO(detailEmplacementRepository.save(detailEmplacement)));
        }

        return Optional.ofNullable(resultat)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /detail-emplacements : get all the detailEmplacements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of detailEmplacements in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/detail-emplacements")
    @Timed
    public ResponseEntity<List<DetailEmplacementDTO>> getAllDetailEmplacements(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of DetailEmplacements");
        Page<DetailEmplacement> page = detailEmplacementRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/detail-emplacements");
        return new ResponseEntity<>(detailEmplacementMapper.detailEmplacementsToDetailEmplacementDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /detail-emplacements/:id : get the "id" detailEmplacement.
     *
     * @param id the id of the detailEmplacementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the detailEmplacementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/detail-emplacements/{id}")
    @Timed
    public ResponseEntity<DetailEmplacementDTO> getDetailEmplacement(@PathVariable Long id) {
        log.debug("REST request to get DetailEmplacement : {}", id);
        DetailEmplacement detailEmplacement = detailEmplacementRepository.findOne(id);
        DetailEmplacementDTO detailEmplacementDTO = detailEmplacementMapper.detailEmplacementToDetailEmplacementDTO(detailEmplacement);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(detailEmplacementDTO));
    }

    /**
     * DELETE  /detail-emplacements/:id : delete the "id" detailEmplacement.
     *
     * @param id the id of the detailEmplacementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/detail-emplacements/{id}")
    @Timed 
    public ResponseEntity<Void> deleteDetailEmplacement(@PathVariable Long id) {
        log.debug("REST request to delete DetailEmplacement : {}", id);
        detailEmplacementRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
