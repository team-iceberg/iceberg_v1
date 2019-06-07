package com.iceberg.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.iceberg.app.domain.ParamEmpl;
import com.iceberg.app.domain.ParamTaille;
import com.iceberg.app.repository.ParamEmplRepository;
import com.iceberg.app.web.rest.util.HeaderUtil;
import com.iceberg.app.service.dto.ParamEmplDTO;
import com.iceberg.app.service.mapper.ParamEmplMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * REST controller for managing ParamEmpl.
 */
@RestController
@RequestMapping("/api")
public class ParamEmplResource {

    private final Logger log = LoggerFactory.getLogger(ParamEmplResource.class);

    private static final String ENTITY_NAME = "paramEmpl";
        
    private final ParamEmplRepository paramEmplRepository;

    private final ParamEmplMapper paramEmplMapper;

    public ParamEmplResource(ParamEmplRepository paramEmplRepository, ParamEmplMapper paramEmplMapper) {
        this.paramEmplRepository = paramEmplRepository;
        this.paramEmplMapper = paramEmplMapper;
    }

    /**
     * POST  /param-empls : Create a new paramEmpl.
     *
     * @param paramEmplDTO the paramEmplDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paramEmplDTO, or with status 400 (Bad Request) if the paramEmpl has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/param-empls")
    @Timed
    public ResponseEntity<ParamEmplDTO> createParamEmpl(@RequestBody ParamEmplDTO paramEmplDTO) throws URISyntaxException {
        log.debug("REST request to save ParamEmpl : {}", paramEmplDTO);
        if (paramEmplDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new paramEmpl cannot already have an ID")).body(null);
        }
        ParamEmpl paramEmpl = paramEmplMapper.paramEmplDTOToParamEmpl(paramEmplDTO);
        paramEmpl = paramEmplRepository.save(paramEmpl);
        ParamEmplDTO result = paramEmplMapper.paramEmplToParamEmplDTO(paramEmpl);
        return ResponseEntity.created(new URI("/api/param-empls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /param-empls : Updates an existing paramEmpl.
     *
     * @param paramEmplDTO the paramEmplDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paramEmplDTO,
     * or with status 400 (Bad Request) if the paramEmplDTO is not valid,
     * or with status 500 (Internal Server Error) if the paramEmplDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/param-empls")
    @Timed
    public ResponseEntity<ParamEmplDTO> updateParamEmpl(@RequestBody ParamEmplDTO paramEmplDTO) throws URISyntaxException {
        log.debug("REST request to update ParamEmpl : {}", paramEmplDTO);
        if (paramEmplDTO.getId() == null) {
            return createParamEmpl(paramEmplDTO);
        }
        ParamEmpl paramEmpl = paramEmplMapper.paramEmplDTOToParamEmpl(paramEmplDTO);
        paramEmpl = paramEmplRepository.save(paramEmpl);
        ParamEmplDTO result = paramEmplMapper.paramEmplToParamEmplDTO(paramEmpl);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paramEmplDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /param-empls : get all the paramEmpls.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of paramEmpls in body
     */
    @GetMapping("/param-empls")
    @Timed
    public List<ParamEmplDTO> getAllParamEmpls() {
        log.debug("REST request to get all ParamEmpls");
        List<ParamEmpl> paramEmpls = paramEmplRepository.findAll(new Sort(Sort.Direction.ASC, "ref"));
        Map<Integer, ParamEmpl> sort = new TreeMap<>();
        Iterator<ParamEmpl> it = paramEmpls.iterator();
        while(it.hasNext()){
        	ParamEmpl pEmpl = it.next();
        	try{
        		int numberEmpl = Integer.parseInt(pEmpl.getRef());
        		sort.put(new Integer(numberEmpl) , pEmpl);
        	}catch(NumberFormatException e){
        		sort.put(0 , pEmpl);
        	}	
        }
        List<ParamEmpl> paramEmplAfterSort = new ArrayList<>(sort.values());
        return paramEmplMapper.paramEmplsToParamEmplDTOs(paramEmplAfterSort);
    }

    /**
     * GET  /param-empls/:id : get the "id" paramEmpl.
     *
     * @param id the id of the paramEmplDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paramEmplDTO, or with status 404 (Not Found)
     */
    @GetMapping("/param-empls/{id}")
    @Timed
    public ResponseEntity<ParamEmplDTO> getParamEmpl(@PathVariable Long id) {
        log.debug("REST request to get ParamEmpl : {}", id);
        ParamEmpl paramEmpl = paramEmplRepository.findOne(id);
        ParamEmplDTO paramEmplDTO = paramEmplMapper.paramEmplToParamEmplDTO(paramEmpl);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(paramEmplDTO));
    }

    /**
     * DELETE  /param-empls/:id : delete the "id" paramEmpl.
     *
     * @param id the id of the paramEmplDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/param-empls/{id}")
    @Timed
    public ResponseEntity<Void> deleteParamEmpl(@PathVariable Long id) {
        log.debug("REST request to delete ParamEmpl : {}", id);
        paramEmplRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
