package com.iceberg.app.web.rest;

import com.iceberg.app.IcebergApp;

import com.iceberg.app.domain.ParamCouleur;
import com.iceberg.app.repository.ParamCouleurRepository;
import com.iceberg.app.service.dto.ParamCouleurDTO;
import com.iceberg.app.service.mapper.ParamCouleurMapper;

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
 * Test class for the ParamCouleurResource REST controller.
 *
 * @see ParamCouleurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IcebergApp.class)
public class ParamCouleurResourceIntTest {

    private static final String DEFAULT_COULEUR = "AAAAAAAAAA";
    private static final String UPDATED_COULEUR = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_HEXA = "AAAAAAAAAA";
    private static final String UPDATED_CODE_HEXA = "BBBBBBBBBB";

    @Autowired
    private ParamCouleurRepository paramCouleurRepository;

    @Autowired
    private ParamCouleurMapper paramCouleurMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restParamCouleurMockMvc;

    private ParamCouleur paramCouleur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            ParamCouleurResource paramCouleurResource = new ParamCouleurResource(paramCouleurRepository, paramCouleurMapper);
        this.restParamCouleurMockMvc = MockMvcBuilders.standaloneSetup(paramCouleurResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParamCouleur createEntity(EntityManager em) {
        ParamCouleur paramCouleur = new ParamCouleur()
                .couleur(DEFAULT_COULEUR)
                .codeHexa(DEFAULT_CODE_HEXA);
        return paramCouleur;
    }

    @Before
    public void initTest() {
        paramCouleur = createEntity(em);
    }

    @Test
    @Transactional
    public void createParamCouleur() throws Exception {
        int databaseSizeBeforeCreate = paramCouleurRepository.findAll().size();

        // Create the ParamCouleur
        ParamCouleurDTO paramCouleurDTO = paramCouleurMapper.paramCouleurToParamCouleurDTO(paramCouleur);

        restParamCouleurMockMvc.perform(post("/api/param-couleurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paramCouleurDTO)))
            .andExpect(status().isCreated());

        // Validate the ParamCouleur in the database
        List<ParamCouleur> paramCouleurList = paramCouleurRepository.findAll();
        assertThat(paramCouleurList).hasSize(databaseSizeBeforeCreate + 1);
        ParamCouleur testParamCouleur = paramCouleurList.get(paramCouleurList.size() - 1);
        assertThat(testParamCouleur.getCouleur()).isEqualTo(DEFAULT_COULEUR);
        assertThat(testParamCouleur.getCodeHexa()).isEqualTo(DEFAULT_CODE_HEXA);
    }

    @Test
    @Transactional
    public void createParamCouleurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paramCouleurRepository.findAll().size();

        // Create the ParamCouleur with an existing ID
        ParamCouleur existingParamCouleur = new ParamCouleur();
        existingParamCouleur.setId(1L);
        ParamCouleurDTO existingParamCouleurDTO = paramCouleurMapper.paramCouleurToParamCouleurDTO(existingParamCouleur);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParamCouleurMockMvc.perform(post("/api/param-couleurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingParamCouleurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ParamCouleur> paramCouleurList = paramCouleurRepository.findAll();
        assertThat(paramCouleurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllParamCouleurs() throws Exception {
        // Initialize the database
        paramCouleurRepository.saveAndFlush(paramCouleur);

        // Get all the paramCouleurList
        restParamCouleurMockMvc.perform(get("/api/param-couleurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paramCouleur.getId().intValue())))
            .andExpect(jsonPath("$.[*].couleur").value(hasItem(DEFAULT_COULEUR.toString())))
            .andExpect(jsonPath("$.[*].codeHexa").value(hasItem(DEFAULT_CODE_HEXA.toString())));
    }

    @Test
    @Transactional
    public void getParamCouleur() throws Exception {
        // Initialize the database
        paramCouleurRepository.saveAndFlush(paramCouleur);

        // Get the paramCouleur
        restParamCouleurMockMvc.perform(get("/api/param-couleurs/{id}", paramCouleur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paramCouleur.getId().intValue()))
            .andExpect(jsonPath("$.couleur").value(DEFAULT_COULEUR.toString()))
            .andExpect(jsonPath("$.codeHexa").value(DEFAULT_CODE_HEXA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParamCouleur() throws Exception {
        // Get the paramCouleur
        restParamCouleurMockMvc.perform(get("/api/param-couleurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParamCouleur() throws Exception {
        // Initialize the database
        paramCouleurRepository.saveAndFlush(paramCouleur);
        int databaseSizeBeforeUpdate = paramCouleurRepository.findAll().size();

        // Update the paramCouleur
        ParamCouleur updatedParamCouleur = paramCouleurRepository.findOne(paramCouleur.getId());
        updatedParamCouleur
                .couleur(UPDATED_COULEUR)
                .codeHexa(UPDATED_CODE_HEXA);
        ParamCouleurDTO paramCouleurDTO = paramCouleurMapper.paramCouleurToParamCouleurDTO(updatedParamCouleur);

        restParamCouleurMockMvc.perform(put("/api/param-couleurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paramCouleurDTO)))
            .andExpect(status().isOk());

        // Validate the ParamCouleur in the database
        List<ParamCouleur> paramCouleurList = paramCouleurRepository.findAll();
        assertThat(paramCouleurList).hasSize(databaseSizeBeforeUpdate);
        ParamCouleur testParamCouleur = paramCouleurList.get(paramCouleurList.size() - 1);
        assertThat(testParamCouleur.getCouleur()).isEqualTo(UPDATED_COULEUR);
        assertThat(testParamCouleur.getCodeHexa()).isEqualTo(UPDATED_CODE_HEXA);
    }

    @Test
    @Transactional
    public void updateNonExistingParamCouleur() throws Exception {
        int databaseSizeBeforeUpdate = paramCouleurRepository.findAll().size();

        // Create the ParamCouleur
        ParamCouleurDTO paramCouleurDTO = paramCouleurMapper.paramCouleurToParamCouleurDTO(paramCouleur);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restParamCouleurMockMvc.perform(put("/api/param-couleurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paramCouleurDTO)))
            .andExpect(status().isCreated());

        // Validate the ParamCouleur in the database
        List<ParamCouleur> paramCouleurList = paramCouleurRepository.findAll();
        assertThat(paramCouleurList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteParamCouleur() throws Exception {
        // Initialize the database
        paramCouleurRepository.saveAndFlush(paramCouleur);
        int databaseSizeBeforeDelete = paramCouleurRepository.findAll().size();

        // Get the paramCouleur
        restParamCouleurMockMvc.perform(delete("/api/param-couleurs/{id}", paramCouleur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ParamCouleur> paramCouleurList = paramCouleurRepository.findAll();
        assertThat(paramCouleurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParamCouleur.class);
    }
}
