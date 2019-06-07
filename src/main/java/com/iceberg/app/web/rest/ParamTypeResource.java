package com.iceberg.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.iceberg.app.domain.ParamType;

import com.iceberg.app.repository.ParamTypeRepository;
import com.iceberg.app.web.rest.util.HeaderUtil;
import com.iceberg.app.service.dto.ParamTypeDTO;
import com.iceberg.app.service.mapper.ParamTypeMapper;
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
 * REST controller for managing ParamType.
 */
@RestController
@RequestMapping("/api")
public class ParamTypeResource {

    private final Logger log = LoggerFactory.getLogger(ParamTypeResource.class);

    private static final String ENTITY_NAME = "paramType";
        
    private final ParamTypeRepository paramTypeRepository;

    private final ParamTypeMapper paramTypeMapper;

    public ParamTypeResource(ParamTypeRepository paramTypeRepository, ParamTypeMapper paramTypeMapper) {
        this.paramTypeRepository = paramTypeRepository;
        this.paramTypeMapper = paramTypeMapper;
    }

    /**
     * POST  /param-types : Create a new paramType.
     *
     * @param paramTypeDTO the paramTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paramTypeDTO, or with status 400 (Bad Request) if the paramType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/param-types")
    @Timed
    public ResponseEntity<ParamTypeDTO> createParamType(@RequestBody ParamTypeDTO paramTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ParamType : {}", paramTypeDTO);
        if (paramTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new paramType cannot already have an ID")).body(null);
        }
        ParamType paramType = paramTypeMapper.paramTypeDTOToParamType(paramTypeDTO);
        paramType = paramTypeRepository.save(paramType);
        ParamTypeDTO result = paramTypeMapper.paramTypeToParamTypeDTO(paramType);
        return ResponseEntity.created(new URI("/api/param-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /param-types : Updates an existing paramType.
     *
     * @param paramTypeDTO the paramTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paramTypeDTO,
     * or with status 400 (Bad Request) if the paramTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the paramTypeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/param-types")
    @Timed
    public ResponseEntity<ParamTypeDTO> updateParamType(@RequestBody ParamTypeDTO paramTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ParamType : {}", paramTypeDTO);
        if (paramTypeDTO.getId() == null) {
            return createParamType(paramTypeDTO);
        }
        ParamType paramType = paramTypeMapper.paramTypeDTOToParamType(paramTypeDTO);
        paramType = paramTypeRepository.save(paramType);
        ParamTypeDTO result = paramTypeMapper.paramTypeToParamTypeDTO(paramType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paramTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /param-types : get all the paramTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of paramTypes in body
     */
    @GetMapping("/param-types")
    @Timed
    public List<ParamTypeDTO> getAllParamTypes() {
        log.debug("REST request to get all ParamTypes");
        List<ParamType> paramTypes = paramTypeRepository.findAll(new Sort(Sort.Direction.ASC, "type"));
        return paramTypeMapper.paramTypesToParamTypeDTOs(paramTypes);
    }

    /**
     * GET  /param-types/:id : get the "id" paramType.
     *
     * @param id the id of the paramTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paramTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/param-types/{id}")
    @Timed
    public ResponseEntity<ParamTypeDTO> getParamType(@PathVariable Long id) {
        log.debug("REST request to get ParamType : {}", id);
        ParamType paramType = paramTypeRepository.findOne(id);
        ParamTypeDTO paramTypeDTO = paramTypeMapper.paramTypeToParamTypeDTO(paramType);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(paramTypeDTO));
    }

    /**
     * DELETE  /param-types/:id : delete the "id" paramType.
     *
     * @param id the id of the paramTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/param-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteParamType(@PathVariable Long id) {
        log.debug("REST request to delete ParamType : {}", id);
        paramTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
