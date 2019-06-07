package com.iceberg.app.web.rest;

import com.iceberg.app.IcebergApp;

import com.iceberg.app.domain.ParamTaille;
import com.iceberg.app.repository.ParamTailleRepository;
import com.iceberg.app.service.dto.ParamTailleDTO;
import com.iceberg.app.service.mapper.ParamTailleMapper;

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
 * Test class for the ParamTailleResource REST controller.
 *
 * @see ParamTailleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IcebergApp.class)
public class ParamTailleResourceIntTest {

    private static final String DEFAULT_TAILLE = "AAAAAAAAAA";
    private static final String UPDATED_TAILLE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private ParamTailleRepository paramTailleRepository;

    @Autowired
    private ParamTailleMapper paramTailleMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restParamTailleMockMvc;

    private ParamTaille paramTaille;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            ParamTailleResource paramTailleResource = new ParamTailleResource(paramTailleRepository, paramTailleMapper);
        this.restParamTailleMockMvc = MockMvcBuilders.standaloneSetup(paramTailleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParamTaille createEntity(EntityManager em) {
        ParamTaille paramTaille = new ParamTaille()
                .taille(DEFAULT_TAILLE)
                .libelle(DEFAULT_LIBELLE);
        return paramTaille;
    }

    @Before
    public void initTest() {
        paramTaille = createEntity(em);
    }

    @Test
    @Transactional
    public void createParamTaille() throws Exception {
        int databaseSizeBeforeCreate = paramTailleRepository.findAll().size();

        // Create the ParamTaille
        ParamTailleDTO paramTailleDTO = paramTailleMapper.paramTailleToParamTailleDTO(paramTaille);

        restParamTailleMockMvc.perform(post("/api/param-tailles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paramTailleDTO)))
            .andExpect(status().isCreated());

        // Validate the ParamTaille in the database
        List<ParamTaille> paramTailleList = paramTailleRepository.findAll();
        assertThat(paramTailleList).hasSize(databaseSizeBeforeCreate + 1);
        ParamTaille testParamTaille = paramTailleList.get(paramTailleList.size() - 1);
        assertThat(testParamTaille.getTaille()).isEqualTo(DEFAULT_TAILLE);
        assertThat(testParamTaille.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createParamTailleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paramTailleRepository.findAll().size();

        // Create the ParamTaille with an existing ID
        ParamTaille existingParamTaille = new ParamTaille();
        existingParamTaille.setId(1L);
        ParamTailleDTO existingParamTailleDTO = paramTailleMapper.paramTailleToParamTailleDTO(existingParamTaille);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParamTailleMockMvc.perform(post("/api/param-tailles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingParamTailleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ParamTaille> paramTailleList = paramTailleRepository.findAll();
        assertThat(paramTailleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllParamTailles() throws Exception {
        // Initialize the database
        paramTailleRepository.saveAndFlush(paramTaille);

        // Get all the paramTailleList
        restParamTailleMockMvc.perform(get("/api/param-tailles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paramTaille.getId().intValue())))
            .andExpect(jsonPath("$.[*].taille").value(hasItem(DEFAULT_TAILLE.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getParamTaille() throws Exception {
        // Initialize the database
        paramTailleRepository.saveAndFlush(paramTaille);

        // Get the paramTaille
        restParamTailleMockMvc.perform(get("/api/param-tailles/{id}", paramTaille.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paramTaille.getId().intValue()))
            .andExpect(jsonPath("$.taille").value(DEFAULT_TAILLE.toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParamTaille() throws Exception {
        // Get the paramTaille
        restParamTailleMockMvc.perform(get("/api/param-tailles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParamTaille() throws Exception {
        // Initialize the database
        paramTailleRepository.saveAndFlush(paramTaille);
        int databaseSizeBeforeUpdate = paramTailleRepository.findAll().size();

        // Update the paramTaille
        ParamTaille updatedParamTaille = paramTailleRepository.findOne(paramTaille.getId());
        updatedParamTaille
                .taille(UPDATED_TAILLE)
                .libelle(UPDATED_LIBELLE);
        ParamTailleDTO paramTailleDTO = paramTailleMapper.paramTailleToParamTailleDTO(updatedParamTaille);

        restParamTailleMockMvc.perform(put("/api/param-tailles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paramTailleDTO)))
            .andExpect(status().isOk());

        // Validate the ParamTaille in the database
        List<ParamTaille> paramTailleList = paramTailleRepository.findAll();
        assertThat(paramTailleList).hasSize(databaseSizeBeforeUpdate);
        ParamTaille testParamTaille = paramTailleList.get(paramTailleList.size() - 1);
        assertThat(testParamTaille.getTaille()).isEqualTo(UPDATED_TAILLE);
        assertThat(testParamTaille.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingParamTaille() throws Exception {
        int databaseSizeBeforeUpdate = paramTailleRepository.findAll().size();

        // Create the ParamTaille
        ParamTailleDTO paramTailleDTO = paramTailleMapper.paramTailleToParamTailleDTO(paramTaille);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restParamTailleMockMvc.perform(put("/api/param-tailles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paramTailleDTO)))
            .andExpect(status().isCreated());

        // Validate the ParamTaille in the database
        List<ParamTaille> paramTailleList = paramTailleRepository.findAll();
        assertThat(paramTailleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteParamTaille() throws Exception {
        // Initialize the database
        paramTailleRepository.saveAndFlush(paramTaille);
        int databaseSizeBeforeDelete = paramTailleRepository.findAll().size();

        // Get the paramTaille
        restParamTailleMockMvc.perform(delete("/api/param-tailles/{id}", paramTaille.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ParamTaille> paramTailleList = paramTailleRepository.findAll();
        assertThat(paramTailleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParamTaille.class);
    }
}
