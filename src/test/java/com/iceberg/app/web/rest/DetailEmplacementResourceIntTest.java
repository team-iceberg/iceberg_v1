package com.iceberg.app.web.rest;

import com.iceberg.app.IcebergApp;

import com.iceberg.app.domain.DetailEmplacement;
import com.iceberg.app.repository.DetailEmplacementRepository;
import com.iceberg.app.service.dto.DetailEmplacementDTO;
import com.iceberg.app.service.mapper.DetailEmplacementMapper;

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

import com.iceberg.app.domain.enumeration.Caracteristique;
/**
 * Test class for the DetailEmplacementResource REST controller.
 *
 * @see DetailEmplacementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IcebergApp.class)
public class DetailEmplacementResourceIntTest {

    private static final String DEFAULT_VALEUR_CARACTERISTIQUE = "AAAAAAAAAA";
    private static final String UPDATED_VALEUR_CARACTERISTIQUE = "BBBBBBBBBB";

    private static final Integer DEFAULT_QTE_EN_COURS = 1;
    private static final Integer UPDATED_QTE_EN_COURS = 2;

    private static final Caracteristique DEFAULT_CARACTERISTIQUE = Caracteristique.TYPE;
    private static final Caracteristique UPDATED_CARACTERISTIQUE = Caracteristique.COULEUR;

    @Autowired
    private DetailEmplacementRepository detailEmplacementRepository;

    @Autowired
    private DetailEmplacementMapper detailEmplacementMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restDetailEmplacementMockMvc;

    private DetailEmplacement detailEmplacement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            DetailEmplacementResource detailEmplacementResource = new DetailEmplacementResource(detailEmplacementRepository, detailEmplacementMapper);
        this.restDetailEmplacementMockMvc = MockMvcBuilders.standaloneSetup(detailEmplacementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailEmplacement createEntity(EntityManager em) {
        DetailEmplacement detailEmplacement = new DetailEmplacement()
                .valeurCaracteristique(DEFAULT_VALEUR_CARACTERISTIQUE)
                .qteEnCours(DEFAULT_QTE_EN_COURS)
                .caracteristique(DEFAULT_CARACTERISTIQUE);
        return detailEmplacement;
    }

    @Before
    public void initTest() {
        detailEmplacement = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetailEmplacement() throws Exception {
        int databaseSizeBeforeCreate = detailEmplacementRepository.findAll().size();

        // Create the DetailEmplacement
        DetailEmplacementDTO detailEmplacementDTO = detailEmplacementMapper.detailEmplacementToDetailEmplacementDTO(detailEmplacement);

        restDetailEmplacementMockMvc.perform(post("/api/detail-emplacements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailEmplacementDTO)))
            .andExpect(status().isCreated());

        // Validate the DetailEmplacement in the database
        List<DetailEmplacement> detailEmplacementList = detailEmplacementRepository.findAll();
        assertThat(detailEmplacementList).hasSize(databaseSizeBeforeCreate + 1);
        DetailEmplacement testDetailEmplacement = detailEmplacementList.get(detailEmplacementList.size() - 1);
        assertThat(testDetailEmplacement.getValeurCaracteristique()).isEqualTo(DEFAULT_VALEUR_CARACTERISTIQUE);
        assertThat(testDetailEmplacement.getQteEnCours()).isEqualTo(DEFAULT_QTE_EN_COURS);
        assertThat(testDetailEmplacement.getCaracteristique()).isEqualTo(DEFAULT_CARACTERISTIQUE);
    }

    @Test
    @Transactional
    public void createDetailEmplacementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detailEmplacementRepository.findAll().size();

        // Create the DetailEmplacement with an existing ID
        DetailEmplacement existingDetailEmplacement = new DetailEmplacement();
        existingDetailEmplacement.setId(1L);
        DetailEmplacementDTO existingDetailEmplacementDTO = detailEmplacementMapper.detailEmplacementToDetailEmplacementDTO(existingDetailEmplacement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetailEmplacementMockMvc.perform(post("/api/detail-emplacements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingDetailEmplacementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DetailEmplacement> detailEmplacementList = detailEmplacementRepository.findAll();
        assertThat(detailEmplacementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDetailEmplacements() throws Exception {
        // Initialize the database
        detailEmplacementRepository.saveAndFlush(detailEmplacement);

        // Get all the detailEmplacementList
        restDetailEmplacementMockMvc.perform(get("/api/detail-emplacements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailEmplacement.getId().intValue())))
            .andExpect(jsonPath("$.[*].valeurCaracteristique").value(hasItem(DEFAULT_VALEUR_CARACTERISTIQUE.toString())))
            .andExpect(jsonPath("$.[*].qteEnCours").value(hasItem(DEFAULT_QTE_EN_COURS)))
            .andExpect(jsonPath("$.[*].caracteristique").value(hasItem(DEFAULT_CARACTERISTIQUE.toString())));
    }

    @Test
    @Transactional
    public void getDetailEmplacement() throws Exception {
        // Initialize the database
        detailEmplacementRepository.saveAndFlush(detailEmplacement);

        // Get the detailEmplacement
        restDetailEmplacementMockMvc.perform(get("/api/detail-emplacements/{id}", detailEmplacement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(detailEmplacement.getId().intValue()))
            .andExpect(jsonPath("$.valeurCaracteristique").value(DEFAULT_VALEUR_CARACTERISTIQUE.toString()))
            .andExpect(jsonPath("$.qteEnCours").value(DEFAULT_QTE_EN_COURS))
            .andExpect(jsonPath("$.caracteristique").value(DEFAULT_CARACTERISTIQUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDetailEmplacement() throws Exception {
        // Get the detailEmplacement
        restDetailEmplacementMockMvc.perform(get("/api/detail-emplacements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetailEmplacement() throws Exception {
        // Initialize the database
        detailEmplacementRepository.saveAndFlush(detailEmplacement);
        int databaseSizeBeforeUpdate = detailEmplacementRepository.findAll().size();

        // Update the detailEmplacement
        DetailEmplacement updatedDetailEmplacement = detailEmplacementRepository.findOne(detailEmplacement.getId());
        updatedDetailEmplacement
                .valeurCaracteristique(UPDATED_VALEUR_CARACTERISTIQUE)
                .qteEnCours(UPDATED_QTE_EN_COURS)
                .caracteristique(UPDATED_CARACTERISTIQUE);
        DetailEmplacementDTO detailEmplacementDTO = detailEmplacementMapper.detailEmplacementToDetailEmplacementDTO(updatedDetailEmplacement);

        restDetailEmplacementMockMvc.perform(put("/api/detail-emplacements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailEmplacementDTO)))
            .andExpect(status().isOk());

        // Validate the DetailEmplacement in the database
        List<DetailEmplacement> detailEmplacementList = detailEmplacementRepository.findAll();
        assertThat(detailEmplacementList).hasSize(databaseSizeBeforeUpdate);
        DetailEmplacement testDetailEmplacement = detailEmplacementList.get(detailEmplacementList.size() - 1);
        assertThat(testDetailEmplacement.getValeurCaracteristique()).isEqualTo(UPDATED_VALEUR_CARACTERISTIQUE);
        assertThat(testDetailEmplacement.getQteEnCours()).isEqualTo(UPDATED_QTE_EN_COURS);
        assertThat(testDetailEmplacement.getCaracteristique()).isEqualTo(UPDATED_CARACTERISTIQUE);
    }

    @Test
    @Transactional
    public void updateNonExistingDetailEmplacement() throws Exception {
        int databaseSizeBeforeUpdate = detailEmplacementRepository.findAll().size();

        // Create the DetailEmplacement
        DetailEmplacementDTO detailEmplacementDTO = detailEmplacementMapper.detailEmplacementToDetailEmplacementDTO(detailEmplacement);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDetailEmplacementMockMvc.perform(put("/api/detail-emplacements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailEmplacementDTO)))
            .andExpect(status().isCreated());

        // Validate the DetailEmplacement in the database
        List<DetailEmplacement> detailEmplacementList = detailEmplacementRepository.findAll();
        assertThat(detailEmplacementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDetailEmplacement() throws Exception {
        // Initialize the database
        detailEmplacementRepository.saveAndFlush(detailEmplacement);
        int databaseSizeBeforeDelete = detailEmplacementRepository.findAll().size();

        // Get the detailEmplacement
        restDetailEmplacementMockMvc.perform(delete("/api/detail-emplacements/{id}", detailEmplacement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DetailEmplacement> detailEmplacementList = detailEmplacementRepository.findAll();
        assertThat(detailEmplacementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailEmplacement.class);
    }
}
