package com.iceberg.app.web.rest;

import com.iceberg.app.IcebergApp;

import com.iceberg.app.domain.ObjetCaracteristiques;
import com.iceberg.app.repository.ObjetCaracteristiquesRepository;
import com.iceberg.app.service.dto.ObjetCaracteristiquesDTO;
import com.iceberg.app.service.mapper.ObjetCaracteristiquesMapper;

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
 * Test class for the ObjetCaracteristiquesResource REST controller.
 *
 * @see ObjetCaracteristiquesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IcebergApp.class)
public class ObjetCaracteristiquesResourceIntTest {

    private static final String DEFAULT_VALEUR = "AAAAAAAAAA";
    private static final String UPDATED_VALEUR = "BBBBBBBBBB";

    private static final Caracteristique DEFAULT_CARACTERISTIQUE = Caracteristique.TYPE;
    private static final Caracteristique UPDATED_CARACTERISTIQUE = Caracteristique.COULEUR;

    @Autowired
    private ObjetCaracteristiquesRepository objetCaracteristiquesRepository;

    @Autowired
    private ObjetCaracteristiquesMapper objetCaracteristiquesMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restObjetCaracteristiquesMockMvc;

    private ObjetCaracteristiques objetCaracteristiques;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            ObjetCaracteristiquesResource objetCaracteristiquesResource = new ObjetCaracteristiquesResource(objetCaracteristiquesRepository, objetCaracteristiquesMapper);
        this.restObjetCaracteristiquesMockMvc = MockMvcBuilders.standaloneSetup(objetCaracteristiquesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ObjetCaracteristiques createEntity(EntityManager em) {
        ObjetCaracteristiques objetCaracteristiques = new ObjetCaracteristiques()
                .valeur(DEFAULT_VALEUR)
                .caracteristique(DEFAULT_CARACTERISTIQUE);
        return objetCaracteristiques;
    }

    @Before
    public void initTest() {
        objetCaracteristiques = createEntity(em);
    }

    @Test
    @Transactional
    public void createObjetCaracteristiques() throws Exception {
        int databaseSizeBeforeCreate = objetCaracteristiquesRepository.findAll().size();

        // Create the ObjetCaracteristiques
        ObjetCaracteristiquesDTO objetCaracteristiquesDTO = objetCaracteristiquesMapper.objetCaracteristiquesToObjetCaracteristiquesDTO(objetCaracteristiques);

        restObjetCaracteristiquesMockMvc.perform(post("/api/objet-caracteristiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(objetCaracteristiquesDTO)))
            .andExpect(status().isCreated());

        // Validate the ObjetCaracteristiques in the database
        List<ObjetCaracteristiques> objetCaracteristiquesList = objetCaracteristiquesRepository.findAll();
        assertThat(objetCaracteristiquesList).hasSize(databaseSizeBeforeCreate + 1);
        ObjetCaracteristiques testObjetCaracteristiques = objetCaracteristiquesList.get(objetCaracteristiquesList.size() - 1);
        assertThat(testObjetCaracteristiques.getValeur()).isEqualTo(DEFAULT_VALEUR);
        assertThat(testObjetCaracteristiques.getCaracteristique()).isEqualTo(DEFAULT_CARACTERISTIQUE);
    }

    @Test
    @Transactional
    public void createObjetCaracteristiquesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = objetCaracteristiquesRepository.findAll().size();

        // Create the ObjetCaracteristiques with an existing ID
        ObjetCaracteristiques existingObjetCaracteristiques = new ObjetCaracteristiques();
        existingObjetCaracteristiques.setId(1L);
        ObjetCaracteristiquesDTO existingObjetCaracteristiquesDTO = objetCaracteristiquesMapper.objetCaracteristiquesToObjetCaracteristiquesDTO(existingObjetCaracteristiques);

        // An entity with an existing ID cannot be created, so this API call must fail
        restObjetCaracteristiquesMockMvc.perform(post("/api/objet-caracteristiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingObjetCaracteristiquesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ObjetCaracteristiques> objetCaracteristiquesList = objetCaracteristiquesRepository.findAll();
        assertThat(objetCaracteristiquesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllObjetCaracteristiques() throws Exception {
        // Initialize the database
        objetCaracteristiquesRepository.saveAndFlush(objetCaracteristiques);

        // Get all the objetCaracteristiquesList
        restObjetCaracteristiquesMockMvc.perform(get("/api/objet-caracteristiques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(objetCaracteristiques.getId().intValue())))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR.toString())))
            .andExpect(jsonPath("$.[*].caracteristique").value(hasItem(DEFAULT_CARACTERISTIQUE.toString())));
    }

    @Test
    @Transactional
    public void getObjetCaracteristiques() throws Exception {
        // Initialize the database
        objetCaracteristiquesRepository.saveAndFlush(objetCaracteristiques);

        // Get the objetCaracteristiques
        restObjetCaracteristiquesMockMvc.perform(get("/api/objet-caracteristiques/{id}", objetCaracteristiques.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(objetCaracteristiques.getId().intValue()))
            .andExpect(jsonPath("$.valeur").value(DEFAULT_VALEUR.toString()))
            .andExpect(jsonPath("$.caracteristique").value(DEFAULT_CARACTERISTIQUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingObjetCaracteristiques() throws Exception {
        // Get the objetCaracteristiques
        restObjetCaracteristiquesMockMvc.perform(get("/api/objet-caracteristiques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateObjetCaracteristiques() throws Exception {
        // Initialize the database
        objetCaracteristiquesRepository.saveAndFlush(objetCaracteristiques);
        int databaseSizeBeforeUpdate = objetCaracteristiquesRepository.findAll().size();

        // Update the objetCaracteristiques
        ObjetCaracteristiques updatedObjetCaracteristiques = objetCaracteristiquesRepository.findOne(objetCaracteristiques.getId());
        updatedObjetCaracteristiques
                .valeur(UPDATED_VALEUR)
                .caracteristique(UPDATED_CARACTERISTIQUE);
        ObjetCaracteristiquesDTO objetCaracteristiquesDTO = objetCaracteristiquesMapper.objetCaracteristiquesToObjetCaracteristiquesDTO(updatedObjetCaracteristiques);

        restObjetCaracteristiquesMockMvc.perform(put("/api/objet-caracteristiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(objetCaracteristiquesDTO)))
            .andExpect(status().isOk());

        // Validate the ObjetCaracteristiques in the database
        List<ObjetCaracteristiques> objetCaracteristiquesList = objetCaracteristiquesRepository.findAll();
        assertThat(objetCaracteristiquesList).hasSize(databaseSizeBeforeUpdate);
        ObjetCaracteristiques testObjetCaracteristiques = objetCaracteristiquesList.get(objetCaracteristiquesList.size() - 1);
        assertThat(testObjetCaracteristiques.getValeur()).isEqualTo(UPDATED_VALEUR);
        assertThat(testObjetCaracteristiques.getCaracteristique()).isEqualTo(UPDATED_CARACTERISTIQUE);
    }

    @Test
    @Transactional
    public void updateNonExistingObjetCaracteristiques() throws Exception {
        int databaseSizeBeforeUpdate = objetCaracteristiquesRepository.findAll().size();

        // Create the ObjetCaracteristiques
        ObjetCaracteristiquesDTO objetCaracteristiquesDTO = objetCaracteristiquesMapper.objetCaracteristiquesToObjetCaracteristiquesDTO(objetCaracteristiques);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restObjetCaracteristiquesMockMvc.perform(put("/api/objet-caracteristiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(objetCaracteristiquesDTO)))
            .andExpect(status().isCreated());

        // Validate the ObjetCaracteristiques in the database
        List<ObjetCaracteristiques> objetCaracteristiquesList = objetCaracteristiquesRepository.findAll();
        assertThat(objetCaracteristiquesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteObjetCaracteristiques() throws Exception {
        // Initialize the database
        objetCaracteristiquesRepository.saveAndFlush(objetCaracteristiques);
        int databaseSizeBeforeDelete = objetCaracteristiquesRepository.findAll().size();

        // Get the objetCaracteristiques
        restObjetCaracteristiquesMockMvc.perform(delete("/api/objet-caracteristiques/{id}", objetCaracteristiques.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ObjetCaracteristiques> objetCaracteristiquesList = objetCaracteristiquesRepository.findAll();
        assertThat(objetCaracteristiquesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObjetCaracteristiques.class);
    }
}
