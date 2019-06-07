package com.iceberg.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.iceberg.app.domain.ParamCouleur;

import com.iceberg.app.repository.ParamCouleurRepository;
import com.iceberg.app.web.rest.util.HeaderUtil;
import com.iceberg.app.service.dto.ParamCouleurDTO;
import com.iceberg.app.service.mapper.ParamCouleurMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing ParamCouleur.
 */
@RestController
@RequestMapping("/api")
public class ParamCouleurResource {

    private final Logger log = LoggerFactory.getLogger(ParamCouleurResource.class);

    private static final String ENTITY_NAME = "paramCouleur";
        
    private final ParamCouleurRepository paramCouleurRepository;

    private final ParamCouleurMapper paramCouleurMapper;

    public ParamCouleurResource(ParamCouleurRepository paramCouleurRepository, ParamCouleurMapper paramCouleurMapper) {
        this.paramCouleurRepository = paramCouleurRepository;
        this.paramCouleurMapper = paramCouleurMapper;
    }

    /**
     * POST  /param-couleurs : Create a new paramCouleur.
     *
     * @param paramCouleurDTO the paramCouleurDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paramCouleurDTO, or with status 400 (Bad Request) if the paramCouleur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/param-couleurs")
    @Timed
    public ResponseEntity<ParamCouleurDTO> createParamCouleur(@RequestBody ParamCouleurDTO paramCouleurDTO) throws URISyntaxException {
        log.debug("REST request to save ParamCouleur : {}", paramCouleurDTO);
        if (paramCouleurDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new paramCouleur cannot already have an ID")).body(null);
        }
        ParamCouleur paramCouleur = paramCouleurMapper.paramCouleurDTOToParamCouleur(paramCouleurDTO);
        paramCouleur = paramCouleurRepository.save(paramCouleur);
        ParamCouleurDTO result = paramCouleurMapper.paramCouleurToParamCouleurDTO(paramCouleur);
        return ResponseEntity.created(new URI("/api/param-couleurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /param-couleurs : Updates an existing paramCouleur.
     *
     * @param paramCouleurDTO the paramCouleurDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paramCouleurDTO,
     * or with status 400 (Bad Request) if the paramCouleurDTO is not valid,
     * or with status 500 (Internal Server Error) if the paramCouleurDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/param-couleurs")
    @Timed
    public ResponseEntity<ParamCouleurDTO> updateParamCouleur(@RequestBody ParamCouleurDTO paramCouleurDTO) throws URISyntaxException {
        log.debug("REST request to update ParamCouleur : {}", paramCouleurDTO);
        if (paramCouleurDTO.getId() == null) {
            return createParamCouleur(paramCouleurDTO);
        }
        ParamCouleur paramCouleur = paramCouleurMapper.paramCouleurDTOToParamCouleur(paramCouleurDTO);
        paramCouleur = paramCouleurRepository.save(paramCouleur);
        ParamCouleurDTO result = paramCouleurMapper.paramCouleurToParamCouleurDTO(paramCouleur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paramCouleurDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /param-couleurs : get all the paramCouleurs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of paramCouleurs in body
     */
    @GetMapping("/param-couleurs")
    @Timed
    public List<ParamCouleurDTO> getAllParamCouleurs() {
        log.debug("REST request to get all ParamCouleurs");
        List<ParamCouleur> paramCouleurs = paramCouleurRepository.findAll(new Sort(Sort.Direction.ASC, "couleur"));
        return paramCouleurMapper.paramCouleursToParamCouleurDTOs(paramCouleurs);
    }

    /**
     * GET  /param-couleurs/:id : get the "id" paramCouleur.
     *
     * @param id the id of the paramCouleurDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paramCouleurDTO, or with status 404 (Not Found)
     */
    @GetMapping("/param-couleurs/{id}")
    @Timed
    public ResponseEntity<ParamCouleurDTO> getParamCouleur(@PathVariable Long id) {
        log.debug("REST request to get ParamCouleur : {}", id);
        ParamCouleur paramCouleur = paramCouleurRepository.findOne(id);
        ParamCouleurDTO paramCouleurDTO = paramCouleurMapper.paramCouleurToParamCouleurDTO(paramCouleur);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(paramCouleurDTO));
    }

    /**
     * DELETE  /param-couleurs/:id : delete the "id" paramCouleur.
     *
     * @param id the id of the paramCouleurDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/param-couleurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteParamCouleur(@PathVariable Long id) {
        log.debug("REST request to delete ParamCouleur : {}", id);
        paramCouleurRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
