package com.iceberg.app.web.rest;

import com.iceberg.app.IcebergApp;

import com.iceberg.app.domain.Emplacement;
import com.iceberg.app.repository.EmplacementRepository;
import com.iceberg.app.service.dto.EmplacementDTO;
import com.iceberg.app.service.mapper.EmplacementMapper;

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
 * Test class for the EmplacementResource REST controller.
 *
 * @see EmplacementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IcebergApp.class)
public class EmplacementResourceIntTest {

    private static final Integer DEFAULT_QTE_TOTAL = 1;
    private static final Integer UPDATED_QTE_TOTAL = 2;

    @Autowired
    private EmplacementRepository emplacementRepository;

    @Autowired
    private EmplacementMapper emplacementMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restEmplacementMockMvc;

    private Emplacement emplacement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            EmplacementResource emplacementResource = new EmplacementResource(emplacementRepository, emplacementMapper);
        this.restEmplacementMockMvc = MockMvcBuilders.standaloneSetup(emplacementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Emplacement createEntity(EntityManager em) {
        Emplacement emplacement = new Emplacement()
                .qteTotal(DEFAULT_QTE_TOTAL);
        return emplacement;
    }

    @Before
    public void initTest() {
        emplacement = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmplacement() throws Exception {
        int databaseSizeBeforeCreate = emplacementRepository.findAll().size();

        // Create the Emplacement
        EmplacementDTO emplacementDTO = emplacementMapper.emplacementToEmplacementDTO(emplacement);

        restEmplacementMockMvc.perform(post("/api/emplacements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emplacementDTO)))
            .andExpect(status().isCreated());

        // Validate the Emplacement in the database
        List<Emplacement> emplacementList = emplacementRepository.findAll();
        assertThat(emplacementList).hasSize(databaseSizeBeforeCreate + 1);
        Emplacement testEmplacement = emplacementList.get(emplacementList.size() - 1);
        assertThat(testEmplacement.getQteTotal()).isEqualTo(DEFAULT_QTE_TOTAL);
    }

    @Test
    @Transactional
    public void createEmplacementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emplacementRepository.findAll().size();

        // Create the Emplacement with an existing ID
        Emplacement existingEmplacement = new Emplacement();
        existingEmplacement.setId(1L);
        EmplacementDTO existingEmplacementDTO = emplacementMapper.emplacementToEmplacementDTO(existingEmplacement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmplacementMockMvc.perform(post("/api/emplacements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingEmplacementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Emplacement> emplacementList = emplacementRepository.findAll();
        assertThat(emplacementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmplacements() throws Exception {
        // Initialize the database
        emplacementRepository.saveAndFlush(emplacement);

        // Get all the emplacementList
        restEmplacementMockMvc.perform(get("/api/emplacements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emplacement.getId().intValue())))
            .andExpect(jsonPath("$.[*].qteTotal").value(hasItem(DEFAULT_QTE_TOTAL)));
    }

    @Test
    @Transactional
    public void getEmplacement() throws Exception {
        // Initialize the database
        emplacementRepository.saveAndFlush(emplacement);

        // Get the emplacement
        restEmplacementMockMvc.perform(get("/api/emplacements/{id}", emplacement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emplacement.getId().intValue()))
            .andExpect(jsonPath("$.qteTotal").value(DEFAULT_QTE_TOTAL));
    }

    @Test
    @Transactional
    public void getNonExistingEmplacement() throws Exception {
        // Get the emplacement
        restEmplacementMockMvc.perform(get("/api/emplacements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmplacement() throws Exception {
        // Initialize the database
        emplacementRepository.saveAndFlush(emplacement);
        int databaseSizeBeforeUpdate = emplacementRepository.findAll().size();

        // Update the emplacement
        Emplacement updatedEmplacement = emplacementRepository.findOne(emplacement.getId());
        updatedEmplacement
                .qteTotal(UPDATED_QTE_TOTAL);
        EmplacementDTO emplacementDTO = emplacementMapper.emplacementToEmplacementDTO(updatedEmplacement);

        restEmplacementMockMvc.perform(put("/api/emplacements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emplacementDTO)))
            .andExpect(status().isOk());

        // Validate the Emplacement in the database
        List<Emplacement> emplacementList = emplacementRepository.findAll();
        assertThat(emplacementList).hasSize(databaseSizeBeforeUpdate);
        Emplacement testEmplacement = emplacementList.get(emplacementList.size() - 1);
        assertThat(testEmplacement.getQteTotal()).isEqualTo(UPDATED_QTE_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingEmplacement() throws Exception {
        int databaseSizeBeforeUpdate = emplacementRepository.findAll().size();

        // Create the Emplacement
        EmplacementDTO emplacementDTO = emplacementMapper.emplacementToEmplacementDTO(emplacement);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmplacementMockMvc.perform(put("/api/emplacements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emplacementDTO)))
            .andExpect(status().isCreated());

        // Validate the Emplacement in the database
        List<Emplacement> emplacementList = emplacementRepository.findAll();
        assertThat(emplacementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmplacement() throws Exception {
        // Initialize the database
        emplacementRepository.saveAndFlush(emplacement);
        int databaseSizeBeforeDelete = emplacementRepository.findAll().size();

        // Get the emplacement
        restEmplacementMockMvc.perform(delete("/api/emplacements/{id}", emplacement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Emplacement> emplacementList = emplacementRepository.findAll();
        assertThat(emplacementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Emplacement.class);
    }
}
