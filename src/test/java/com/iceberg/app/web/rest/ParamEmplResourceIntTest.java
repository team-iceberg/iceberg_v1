package com.iceberg.app.web.rest;

import com.iceberg.app.IcebergApp;

import com.iceberg.app.domain.ParamEmpl;
import com.iceberg.app.repository.ParamEmplRepository;
import com.iceberg.app.service.dto.ParamEmplDTO;
import com.iceberg.app.service.mapper.ParamEmplMapper;

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
 * Test class for the ParamEmplResource REST controller.
 *
 * @see ParamEmplResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IcebergApp.class)
public class ParamEmplResourceIntTest {

    private static final String DEFAULT_REF = "AAAAAAAAAA";
    private static final String UPDATED_REF = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private ParamEmplRepository paramEmplRepository;

    @Autowired
    private ParamEmplMapper paramEmplMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restParamEmplMockMvc;

    private ParamEmpl paramEmpl;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            ParamEmplResource paramEmplResource = new ParamEmplResource(paramEmplRepository, paramEmplMapper);
        this.restParamEmplMockMvc = MockMvcBuilders.standaloneSetup(paramEmplResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParamEmpl createEntity(EntityManager em) {
        ParamEmpl paramEmpl = new ParamEmpl()
                .ref(DEFAULT_REF)
                .libelle(DEFAULT_LIBELLE);
        return paramEmpl;
    }

    @Before
    public void initTest() {
        paramEmpl = createEntity(em);
    }

    @Test
    @Transactional
    public void createParamEmpl() throws Exception {
        int databaseSizeBeforeCreate = paramEmplRepository.findAll().size();

        // Create the ParamEmpl
        ParamEmplDTO paramEmplDTO = paramEmplMapper.paramEmplToParamEmplDTO(paramEmpl);

        restParamEmplMockMvc.perform(post("/api/param-empls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paramEmplDTO)))
            .andExpect(status().isCreated());

        // Validate the ParamEmpl in the database
        List<ParamEmpl> paramEmplList = paramEmplRepository.findAll();
        assertThat(paramEmplList).hasSize(databaseSizeBeforeCreate + 1);
        ParamEmpl testParamEmpl = paramEmplList.get(paramEmplList.size() - 1);
        assertThat(testParamEmpl.getRef()).isEqualTo(DEFAULT_REF);
        assertThat(testParamEmpl.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createParamEmplWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paramEmplRepository.findAll().size();

        // Create the ParamEmpl with an existing ID
        ParamEmpl existingParamEmpl = new ParamEmpl();
        existingParamEmpl.setId(1L);
        ParamEmplDTO existingParamEmplDTO = paramEmplMapper.paramEmplToParamEmplDTO(existingParamEmpl);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParamEmplMockMvc.perform(post("/api/param-empls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingParamEmplDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ParamEmpl> paramEmplList = paramEmplRepository.findAll();
        assertThat(paramEmplList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllParamEmpls() throws Exception {
        // Initialize the database
        paramEmplRepository.saveAndFlush(paramEmpl);

        // Get all the paramEmplList
        restParamEmplMockMvc.perform(get("/api/param-empls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paramEmpl.getId().intValue())))
            .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getParamEmpl() throws Exception {
        // Initialize the database
        paramEmplRepository.saveAndFlush(paramEmpl);

        // Get the paramEmpl
        restParamEmplMockMvc.perform(get("/api/param-empls/{id}", paramEmpl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paramEmpl.getId().intValue()))
            .andExpect(jsonPath("$.ref").value(DEFAULT_REF.toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParamEmpl() throws Exception {
        // Get the paramEmpl
        restParamEmplMockMvc.perform(get("/api/param-empls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParamEmpl() throws Exception {
        // Initialize the database
        paramEmplRepository.saveAndFlush(paramEmpl);
        int databaseSizeBeforeUpdate = paramEmplRepository.findAll().size();

        // Update the paramEmpl
        ParamEmpl updatedParamEmpl = paramEmplRepository.findOne(paramEmpl.getId());
        updatedParamEmpl
                .ref(UPDATED_REF)
                .libelle(UPDATED_LIBELLE);
        ParamEmplDTO paramEmplDTO = paramEmplMapper.paramEmplToParamEmplDTO(updatedParamEmpl);

        restParamEmplMockMvc.perform(put("/api/param-empls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paramEmplDTO)))
            .andExpect(status().isOk());

        // Validate the ParamEmpl in the database
        List<ParamEmpl> paramEmplList = paramEmplRepository.findAll();
        assertThat(paramEmplList).hasSize(databaseSizeBeforeUpdate);
        ParamEmpl testParamEmpl = paramEmplList.get(paramEmplList.size() - 1);
        assertThat(testParamEmpl.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testParamEmpl.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingParamEmpl() throws Exception {
        int databaseSizeBeforeUpdate = paramEmplRepository.findAll().size();

        // Create the ParamEmpl
        ParamEmplDTO paramEmplDTO = paramEmplMapper.paramEmplToParamEmplDTO(paramEmpl);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restParamEmplMockMvc.perform(put("/api/param-empls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paramEmplDTO)))
            .andExpect(status().isCreated());

        // Validate the ParamEmpl in the database
        List<ParamEmpl> paramEmplList = paramEmplRepository.findAll();
        assertThat(paramEmplList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteParamEmpl() throws Exception {
        // Initialize the database
        paramEmplRepository.saveAndFlush(paramEmpl);
        int databaseSizeBeforeDelete = paramEmplRepository.findAll().size();

        // Get the paramEmpl
        restParamEmplMockMvc.perform(delete("/api/param-empls/{id}", paramEmpl.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ParamEmpl> paramEmplList = paramEmplRepository.findAll();
        assertThat(paramEmplList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParamEmpl.class);
    }
}
