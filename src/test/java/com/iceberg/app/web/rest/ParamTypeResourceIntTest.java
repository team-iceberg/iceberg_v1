package com.iceberg.app.web.rest;

import com.iceberg.app.IcebergApp;

import com.iceberg.app.domain.ParamType;
import com.iceberg.app.repository.ParamTypeRepository;
import com.iceberg.app.service.dto.ParamTypeDTO;
import com.iceberg.app.service.mapper.ParamTypeMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ParamTypeResource REST controller.
 *
 * @see ParamTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IcebergApp.class)
public class ParamTypeResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private ParamTypeRepository paramTypeRepository;

    @Autowired
    private ParamTypeMapper paramTypeMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restParamTypeMockMvc;

    private ParamType paramType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            ParamTypeResource paramTypeResource = new ParamTypeResource(paramTypeRepository, paramTypeMapper);
        this.restParamTypeMockMvc = MockMvcBuilders.standaloneSetup(paramTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParamType createEntity(EntityManager em) {
        ParamType paramType = new ParamType()
                .type(DEFAULT_TYPE)
                .libelle(DEFAULT_LIBELLE);
        return paramType;
    }

    @Before
    public void initTest() {
        paramType = createEntity(em);
    }

    @Test
    @Transactional
    public void createParamType() throws Exception {
        int databaseSizeBeforeCreate = paramTypeRepository.findAll().size();

        // Create the ParamType
        ParamTypeDTO paramTypeDTO = paramTypeMapper.paramTypeToParamTypeDTO(paramType);

        restParamTypeMockMvc.perform(post("/api/param-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paramTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ParamType in the database
        List<ParamType> paramTypeList = paramTypeRepository.findAll();
        assertThat(paramTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ParamType testParamType = paramTypeList.get(paramTypeList.size() - 1);
        assertThat(testParamType.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testParamType.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createParamTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paramTypeRepository.findAll().size();

        // Create the ParamType with an existing ID
        ParamType existingParamType = new ParamType();
        existingParamType.setId(1L);
        ParamTypeDTO existingParamTypeDTO = paramTypeMapper.paramTypeToParamTypeDTO(existingParamType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParamTypeMockMvc.perform(post("/api/param-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingParamTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ParamType> paramTypeList = paramTypeRepository.findAll();
        assertThat(paramTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllParamTypes() throws Exception {
        // Initialize the database
        paramTypeRepository.saveAndFlush(paramType);

        // Get all the paramTypeList
        restParamTypeMockMvc.perform(get("/api/param-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paramType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getParamType() throws Exception {
        // Initialize the database
        paramTypeRepository.saveAndFlush(paramType);

        // Get the paramType
        restParamTypeMockMvc.perform(get("/api/param-types/{id}", paramType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paramType.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParamType() throws Exception {
        // Get the paramType
        restParamTypeMockMvc.perform(get("/api/param-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParamType() throws Exception {
        // Initialize the database
        paramTypeRepository.saveAndFlush(paramType);
        int databaseSizeBeforeUpdate = paramTypeRepository.findAll().size();

        // Update the paramType
        ParamType updatedParamType = paramTypeRepository.findOne(paramType.getId());
        updatedParamType
                .type(UPDATED_TYPE)
                .libelle(UPDATED_LIBELLE);
        ParamTypeDTO paramTypeDTO = paramTypeMapper.paramTypeToParamTypeDTO(updatedParamType);

        restParamTypeMockMvc.perform(put("/api/param-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paramTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ParamType in the database
        List<ParamType> paramTypeList = paramTypeRepository.findAll();
        assertThat(paramTypeList).hasSize(databaseSizeBeforeUpdate);
        ParamType testParamType = paramTypeList.get(paramTypeList.size() - 1);
        assertThat(testParamType.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testParamType.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingParamType() throws Exception {
        int databaseSizeBeforeUpdate = paramTypeRepository.findAll().size();

        // Create the ParamType
        ParamTypeDTO paramTypeDTO = paramTypeMapper.paramTypeToParamTypeDTO(paramType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restParamTypeMockMvc.perform(put("/api/param-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paramTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ParamType in the database
        List<ParamType> paramTypeList = paramTypeRepository.findAll();
        assertThat(paramTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteParamType() throws Exception {
        // Initialize the database
        paramTypeRepository.saveAndFlush(paramType);
        int databaseSizeBeforeDelete = paramTypeRepository.findAll().size();

        // Get the paramType
        restParamTypeMockMvc.perform(delete("/api/param-types/{id}", paramType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ParamType> paramTypeList = paramTypeRepository.findAll();
        assertThat(paramTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParamType.class);
    }
}
