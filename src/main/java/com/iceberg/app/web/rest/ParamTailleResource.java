package com.iceberg.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.iceberg.app.domain.ParamTaille;

import com.iceberg.app.repository.ParamTailleRepository;
import com.iceberg.app.web.rest.util.HeaderUtil;
import com.iceberg.app.service.dto.ParamTailleDTO;
import com.iceberg.app.service.mapper.ParamTailleMapper;
import io.github.jhipster.web.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * REST controller for managing ParamTaille.
 */
@RestController
@RequestMapping("/api")
public class ParamTailleResource {

    private final Logger log = LoggerFactory.getLogger(ParamTailleResource.class);

    private static final String ENTITY_NAME = "paramTaille";
        
    private final ParamTailleRepository paramTailleRepository;

    private final ParamTailleMapper paramTailleMapper;

    public ParamTailleResource(ParamTailleRepository paramTailleRepository, ParamTailleMapper paramTailleMapper) {
        this.paramTailleRepository = paramTailleRepository;
        this.paramTailleMapper = paramTailleMapper;
    }

    /**
     * POST  /param-tailles : Create a new paramTaille.
     *
     * @param paramTailleDTO the paramTailleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paramTailleDTO, or with status 400 (Bad Request) if the paramTaille has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/param-tailles")
    @Timed
    public ResponseEntity<ParamTailleDTO> createParamTaille(@RequestBody ParamTailleDTO paramTailleDTO) throws URISyntaxException {
        log.debug("REST request to save ParamTaille : {}", paramTailleDTO);
        if (paramTailleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new paramTaille cannot already have an ID")).body(null);
        }
        ParamTaille paramTaille = paramTailleMapper.paramTailleDTOToParamTaille(paramTailleDTO);
        paramTaille = paramTailleRepository.save(paramTaille);
        ParamTailleDTO result = paramTailleMapper.paramTailleToParamTailleDTO(paramTaille);
        return ResponseEntity.created(new URI("/api/param-tailles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /param-tailles : Updates an existing paramTaille.
     *
     * @param paramTailleDTO the paramTailleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paramTailleDTO,
     * or with status 400 (Bad Request) if the paramTailleDTO is not valid,
     * or with status 500 (Internal Server Error) if the paramTailleDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/param-tailles")
    @Timed
    public ResponseEntity<ParamTailleDTO> updateParamTaille(@RequestBody ParamTailleDTO paramTailleDTO) throws URISyntaxException {
        log.debug("REST request to update ParamTaille : {}", paramTailleDTO);
        if (paramTailleDTO.getId() == null) {
            return createParamTaille(paramTailleDTO);
        }
        ParamTaille paramTaille = paramTailleMapper.paramTailleDTOToParamTaille(paramTailleDTO);
        paramTaille = paramTailleRepository.save(paramTaille);
        ParamTailleDTO result = paramTailleMapper.paramTailleToParamTailleDTO(paramTaille);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paramTailleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /param-tailles : get all the paramTailles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of paramTailles in body
     */
    @GetMapping("/param-tailles")
    @Timed
    public List<ParamTailleDTO> getAllParamTailles() {
        log.debug("REST request to get all ParamTailles");
        List<ParamTaille> paramTailles = paramTailleRepository.findAll(new Sort(Sort.Direction.ASC, "taille"));
        Map<String, ParamTaille> sort = new TreeMap<>();
        Iterator<ParamTaille> it = paramTailles.iterator();
        while(it.hasNext()){
        	ParamTaille pTaille = it.next();
        	String nTaille = pTaille.getTaille();
        	try{
        		int i = Integer.parseInt(nTaille);
        		sort.put(String.format("%08d", i), pTaille);
        	}catch(NumberFormatException e){
        		sort.put(String.format("%08d", 0), pTaille);
        	}
        }
        List<ParamTaille> paramTaillesAfterSort = new ArrayList<>(sort.values());
        List<ParamTailleDTO> test = paramTailleMapper.paramTaillesToParamTailleDTOs(paramTaillesAfterSort);
        return test;
    }

    /**
     * GET  /param-tailles/:id : get the "id" paramTaille.
     *
     * @param id the id of the paramTailleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paramTailleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/param-tailles/{id}")
    @Timed
    public ResponseEntity<ParamTailleDTO> getParamTaille(@PathVariable Long id) {
        log.debug("REST request to get ParamTaille : {}", id);
        ParamTaille paramTaille = paramTailleRepository.findOne(id);
        ParamTailleDTO paramTailleDTO = paramTailleMapper.paramTailleToParamTailleDTO(paramTaille);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(paramTailleDTO));
    }

    /**
     * DELETE  /param-tailles/:id : delete the "id" paramTaille.
     *
     * @param id the id of the paramTailleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/param-tailles/{id}")
    @Timed
    public ResponseEntity<Void> deleteParamTaille(@PathVariable Long id) {
        log.debug("REST request to delete ParamTaille : {}", id);
        paramTailleRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
