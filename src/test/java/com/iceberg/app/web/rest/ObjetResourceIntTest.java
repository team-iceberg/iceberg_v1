package com.iceberg.app.web.rest;

import com.iceberg.app.IcebergApp;

import com.iceberg.app.domain.Objet;
import com.iceberg.app.repository.ObjetRepository;
import com.iceberg.app.service.dto.ObjetDTO;
import com.iceberg.app.service.mapper.ObjetMapper;

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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.iceberg.app.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ObjetResource REST controller.
 *
 * @see ObjetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IcebergApp.class)
public class ObjetResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_DEPOT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_DEPOT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final byte[] DEFAULT_IMAGE_1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_1 = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_1_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_1_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGE_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_2 = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_2_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGE_3 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_3 = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_3_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_3_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGE_4 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_4 = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_4_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_4_CONTENT_TYPE = "image/png";

    @Autowired
    private ObjetRepository objetRepository;

    @Autowired
    private ObjetMapper objetMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restObjetMockMvc;

    private Objet objet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            ObjetResource objetResource = new ObjetResource(objetRepository, objetMapper);
        this.restObjetMockMvc = MockMvcBuilders.standaloneSetup(objetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Objet createEntity(EntityManager em) {
        Objet objet = new Objet()
                .libelle(DEFAULT_LIBELLE)
                .dateDepot(DEFAULT_DATE_DEPOT)
                .image1(DEFAULT_IMAGE_1)
                .image1ContentType(DEFAULT_IMAGE_1_CONTENT_TYPE)
                .image2(DEFAULT_IMAGE_2)
                .image2ContentType(DEFAULT_IMAGE_2_CONTENT_TYPE)
                .image3(DEFAULT_IMAGE_3)
                .image3ContentType(DEFAULT_IMAGE_3_CONTENT_TYPE)
                .image4(DEFAULT_IMAGE_4)
                .image4ContentType(DEFAULT_IMAGE_4_CONTENT_TYPE);
        return objet;
    }

    @Before
    public void initTest() {
        objet = createEntity(em);
    }

    @Test
    @Transactional
    public void createObjet() throws Exception {
        int databaseSizeBeforeCreate = objetRepository.findAll().size();

        // Create the Objet
        ObjetDTO objetDTO = objetMapper.objetToObjetDTO(objet);

        restObjetMockMvc.perform(post("/api/objets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(objetDTO)))
            .andExpect(status().isCreated());

        // Validate the Objet in the database
        List<Objet> objetList = objetRepository.findAll();
        assertThat(objetList).hasSize(databaseSizeBeforeCreate + 1);
        Objet testObjet = objetList.get(objetList.size() - 1);
        assertThat(testObjet.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testObjet.getDateDepot()).isEqualTo(DEFAULT_DATE_DEPOT);
        assertThat(testObjet.getImage1()).isEqualTo(DEFAULT_IMAGE_1);
        assertThat(testObjet.getImage1ContentType()).isEqualTo(DEFAULT_IMAGE_1_CONTENT_TYPE);
        assertThat(testObjet.getImage2()).isEqualTo(DEFAULT_IMAGE_2);
        assertThat(testObjet.getImage2ContentType()).isEqualTo(DEFAULT_IMAGE_2_CONTENT_TYPE);
        assertThat(testObjet.getImage3()).isEqualTo(DEFAULT_IMAGE_3);
        assertThat(testObjet.getImage3ContentType()).isEqualTo(DEFAULT_IMAGE_3_CONTENT_TYPE);
        assertThat(testObjet.getImage4()).isEqualTo(DEFAULT_IMAGE_4);
        assertThat(testObjet.getImage4ContentType()).isEqualTo(DEFAULT_IMAGE_4_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createObjetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = objetRepository.findAll().size();

        // Create the Objet with an existing ID
        Objet existingObjet = new Objet();
        existingObjet.setId(1L);
        ObjetDTO existingObjetDTO = objetMapper.objetToObjetDTO(existingObjet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restObjetMockMvc.perform(post("/api/objets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingObjetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Objet> objetList = objetRepository.findAll();
        assertThat(objetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllObjets() throws Exception {
        // Initialize the database
        objetRepository.saveAndFlush(objet);

        // Get all the objetList
        restObjetMockMvc.perform(get("/api/objets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(objet.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].dateDepot").value(hasItem(sameInstant(DEFAULT_DATE_DEPOT))))
            .andExpect(jsonPath("$.[*].image1ContentType").value(hasItem(DEFAULT_IMAGE_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_1))))
            .andExpect(jsonPath("$.[*].image2ContentType").value(hasItem(DEFAULT_IMAGE_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_2))))
            .andExpect(jsonPath("$.[*].image3ContentType").value(hasItem(DEFAULT_IMAGE_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image3").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_3))))
            .andExpect(jsonPath("$.[*].image4ContentType").value(hasItem(DEFAULT_IMAGE_4_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image4").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_4))));
    }

    @Test
    @Transactional
    public void getObjet() throws Exception {
        // Initialize the database
       /** objetRepository.saveAndFlush(objet);

        // Get the objet
        restObjetMockMvc.perform(get("/api/objets/{id}", objet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(objet.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.dateDepot").value(sameInstant(DEFAULT_DATE_DEPOT)))
            .andExpect(jsonPath("$.image1ContentType").value(DEFAULT_IMAGE_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.image1").value(Base64Utils.encodeToString(DEFAULT_IMAGE_1)))
            .andExpect(jsonPath("$.image2ContentType").value(DEFAULT_IMAGE_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.image2").value(Base64Utils.encodeToString(DEFAULT_IMAGE_2)))
            .andExpect(jsonPath("$.image3ContentType").value(DEFAULT_IMAGE_3_CONTENT_TYPE))
            .andExpect(jsonPath("$.image3").value(Base64Utils.encodeToString(DEFAULT_IMAGE_3)))
            .andExpect(jsonPath("$.image4ContentType").value(DEFAULT_IMAGE_4_CONTENT_TYPE))
            .andExpect(jsonPath("$.image4").value(Base64Utils.encodeToString(DEFAULT_IMAGE_4))); **/
    }

    @Test
    @Transactional
    public void getNonExistingObjet() throws Exception {
        // Get the objet
       // restObjetMockMvc.perform(get("/api/objets/{id}", Long.MAX_VALUE))
       //     .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateObjet() throws Exception {
        // Initialize the database
        objetRepository.saveAndFlush(objet);
        int databaseSizeBeforeUpdate = objetRepository.findAll().size();

        // Update the objet
        Objet updatedObjet = objetRepository.findOne(objet.getId());
        updatedObjet
                .libelle(UPDATED_LIBELLE)
                .dateDepot(UPDATED_DATE_DEPOT)
                .image1(UPDATED_IMAGE_1)
                .image1ContentType(UPDATED_IMAGE_1_CONTENT_TYPE)
                .image2(UPDATED_IMAGE_2)
                .image2ContentType(UPDATED_IMAGE_2_CONTENT_TYPE)
                .image3(UPDATED_IMAGE_3)
                .image3ContentType(UPDATED_IMAGE_3_CONTENT_TYPE)
                .image4(UPDATED_IMAGE_4)
                .image4ContentType(UPDATED_IMAGE_4_CONTENT_TYPE);
        ObjetDTO objetDTO = objetMapper.objetToObjetDTO(updatedObjet);

        restObjetMockMvc.perform(put("/api/objets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(objetDTO)))
            .andExpect(status().isOk());

        // Validate the Objet in the database
        List<Objet> objetList = objetRepository.findAll();
        assertThat(objetList).hasSize(databaseSizeBeforeUpdate);
        Objet testObjet = objetList.get(objetList.size() - 1);
        assertThat(testObjet.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testObjet.getDateDepot()).isEqualTo(UPDATED_DATE_DEPOT);
        assertThat(testObjet.getImage1()).isEqualTo(UPDATED_IMAGE_1);
        assertThat(testObjet.getImage1ContentType()).isEqualTo(UPDATED_IMAGE_1_CONTENT_TYPE);
        assertThat(testObjet.getImage2()).isEqualTo(UPDATED_IMAGE_2);
        assertThat(testObjet.getImage2ContentType()).isEqualTo(UPDATED_IMAGE_2_CONTENT_TYPE);
        assertThat(testObjet.getImage3()).isEqualTo(UPDATED_IMAGE_3);
        assertThat(testObjet.getImage3ContentType()).isEqualTo(UPDATED_IMAGE_3_CONTENT_TYPE);
        assertThat(testObjet.getImage4()).isEqualTo(UPDATED_IMAGE_4);
        assertThat(testObjet.getImage4ContentType()).isEqualTo(UPDATED_IMAGE_4_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingObjet() throws Exception {
        int databaseSizeBeforeUpdate = objetRepository.findAll().size();

        // Create the Objet
        ObjetDTO objetDTO = objetMapper.objetToObjetDTO(objet);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restObjetMockMvc.perform(put("/api/objets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(objetDTO)))
            .andExpect(status().isCreated());

        // Validate the Objet in the database
        List<Objet> objetList = objetRepository.findAll();
        assertThat(objetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteObjet() throws Exception {
        // Initialize the database
        objetRepository.saveAndFlush(objet);
        int databaseSizeBeforeDelete = objetRepository.findAll().size();

        // Get the objet
        restObjetMockMvc.perform(delete("/api/objets/{id}", objet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Objet> objetList = objetRepository.findAll();
        assertThat(objetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Objet.class);
    }
}
