package org.kiran.entmdm.web.rest;

import org.kiran.entmdm.EntmdmApp;

import org.kiran.entmdm.domain.Medicalcustomer;
import org.kiran.entmdm.repository.MedicalcustomerRepository;
import org.kiran.entmdm.repository.search.MedicalcustomerSearchRepository;
import org.kiran.entmdm.web.rest.errors.ExceptionTranslator;

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
 * Test class for the MedicalcustomerResource REST controller.
 *
 * @see MedicalcustomerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EntmdmApp.class)
public class MedicalcustomerResourceIntTest {

    private static final String DEFAULT_MEDCUSTOMERNR = "AAAAAAAAAA";
    private static final String UPDATED_MEDCUSTOMERNR = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ZIPCODE = 99999;
    private static final Integer UPDATED_ZIPCODE = 99998;

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    @Autowired
    private MedicalcustomerRepository medicalcustomerRepository;

    @Autowired
    private MedicalcustomerSearchRepository medicalcustomerSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedicalcustomerMockMvc;

    private Medicalcustomer medicalcustomer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MedicalcustomerResource medicalcustomerResource = new MedicalcustomerResource(medicalcustomerRepository, medicalcustomerSearchRepository);
        this.restMedicalcustomerMockMvc = MockMvcBuilders.standaloneSetup(medicalcustomerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medicalcustomer createEntity(EntityManager em) {
        Medicalcustomer medicalcustomer = new Medicalcustomer()
            .medcustomernr(DEFAULT_MEDCUSTOMERNR)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .zipcode(DEFAULT_ZIPCODE)
            .country(DEFAULT_COUNTRY);
        return medicalcustomer;
    }

    @Before
    public void initTest() {
        medicalcustomerSearchRepository.deleteAll();
        medicalcustomer = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicalcustomer() throws Exception {
        int databaseSizeBeforeCreate = medicalcustomerRepository.findAll().size();

        // Create the Medicalcustomer
        restMedicalcustomerMockMvc.perform(post("/api/medicalcustomers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalcustomer)))
            .andExpect(status().isCreated());

        // Validate the Medicalcustomer in the database
        List<Medicalcustomer> medicalcustomerList = medicalcustomerRepository.findAll();
        assertThat(medicalcustomerList).hasSize(databaseSizeBeforeCreate + 1);
        Medicalcustomer testMedicalcustomer = medicalcustomerList.get(medicalcustomerList.size() - 1);
        assertThat(testMedicalcustomer.getMedcustomernr()).isEqualTo(DEFAULT_MEDCUSTOMERNR);
        assertThat(testMedicalcustomer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testMedicalcustomer.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testMedicalcustomer.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testMedicalcustomer.getZipcode()).isEqualTo(DEFAULT_ZIPCODE);
        assertThat(testMedicalcustomer.getCountry()).isEqualTo(DEFAULT_COUNTRY);

        // Validate the Medicalcustomer in Elasticsearch
        Medicalcustomer medicalcustomerEs = medicalcustomerSearchRepository.findOne(testMedicalcustomer.getId());
        assertThat(medicalcustomerEs).isEqualToComparingFieldByField(testMedicalcustomer);
    }

    @Test
    @Transactional
    public void createMedicalcustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicalcustomerRepository.findAll().size();

        // Create the Medicalcustomer with an existing ID
        medicalcustomer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicalcustomerMockMvc.perform(post("/api/medicalcustomers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalcustomer)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Medicalcustomer> medicalcustomerList = medicalcustomerRepository.findAll();
        assertThat(medicalcustomerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMedcustomernrIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicalcustomerRepository.findAll().size();
        // set the field null
        medicalcustomer.setMedcustomernr(null);

        // Create the Medicalcustomer, which fails.

        restMedicalcustomerMockMvc.perform(post("/api/medicalcustomers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalcustomer)))
            .andExpect(status().isBadRequest());

        List<Medicalcustomer> medicalcustomerList = medicalcustomerRepository.findAll();
        assertThat(medicalcustomerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkZipcodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicalcustomerRepository.findAll().size();
        // set the field null
        medicalcustomer.setZipcode(null);

        // Create the Medicalcustomer, which fails.

        restMedicalcustomerMockMvc.perform(post("/api/medicalcustomers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalcustomer)))
            .andExpect(status().isBadRequest());

        List<Medicalcustomer> medicalcustomerList = medicalcustomerRepository.findAll();
        assertThat(medicalcustomerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMedicalcustomers() throws Exception {
        // Initialize the database
        medicalcustomerRepository.saveAndFlush(medicalcustomer);

        // Get all the medicalcustomerList
        restMedicalcustomerMockMvc.perform(get("/api/medicalcustomers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalcustomer.getId().intValue())))
            .andExpect(jsonPath("$.[*].medcustomernr").value(hasItem(DEFAULT_MEDCUSTOMERNR.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].zipcode").value(hasItem(DEFAULT_ZIPCODE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())));
    }

    @Test
    @Transactional
    public void getMedicalcustomer() throws Exception {
        // Initialize the database
        medicalcustomerRepository.saveAndFlush(medicalcustomer);

        // Get the medicalcustomer
        restMedicalcustomerMockMvc.perform(get("/api/medicalcustomers/{id}", medicalcustomer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicalcustomer.getId().intValue()))
            .andExpect(jsonPath("$.medcustomernr").value(DEFAULT_MEDCUSTOMERNR.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.zipcode").value(DEFAULT_ZIPCODE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMedicalcustomer() throws Exception {
        // Get the medicalcustomer
        restMedicalcustomerMockMvc.perform(get("/api/medicalcustomers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicalcustomer() throws Exception {
        // Initialize the database
        medicalcustomerRepository.saveAndFlush(medicalcustomer);
        medicalcustomerSearchRepository.save(medicalcustomer);
        int databaseSizeBeforeUpdate = medicalcustomerRepository.findAll().size();

        // Update the medicalcustomer
        Medicalcustomer updatedMedicalcustomer = medicalcustomerRepository.findOne(medicalcustomer.getId());
        updatedMedicalcustomer
            .medcustomernr(UPDATED_MEDCUSTOMERNR)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .zipcode(UPDATED_ZIPCODE)
            .country(UPDATED_COUNTRY);

        restMedicalcustomerMockMvc.perform(put("/api/medicalcustomers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicalcustomer)))
            .andExpect(status().isOk());

        // Validate the Medicalcustomer in the database
        List<Medicalcustomer> medicalcustomerList = medicalcustomerRepository.findAll();
        assertThat(medicalcustomerList).hasSize(databaseSizeBeforeUpdate);
        Medicalcustomer testMedicalcustomer = medicalcustomerList.get(medicalcustomerList.size() - 1);
        assertThat(testMedicalcustomer.getMedcustomernr()).isEqualTo(UPDATED_MEDCUSTOMERNR);
        assertThat(testMedicalcustomer.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testMedicalcustomer.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testMedicalcustomer.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testMedicalcustomer.getZipcode()).isEqualTo(UPDATED_ZIPCODE);
        assertThat(testMedicalcustomer.getCountry()).isEqualTo(UPDATED_COUNTRY);

        // Validate the Medicalcustomer in Elasticsearch
        Medicalcustomer medicalcustomerEs = medicalcustomerSearchRepository.findOne(testMedicalcustomer.getId());
        assertThat(medicalcustomerEs).isEqualToComparingFieldByField(testMedicalcustomer);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicalcustomer() throws Exception {
        int databaseSizeBeforeUpdate = medicalcustomerRepository.findAll().size();

        // Create the Medicalcustomer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMedicalcustomerMockMvc.perform(put("/api/medicalcustomers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalcustomer)))
            .andExpect(status().isCreated());

        // Validate the Medicalcustomer in the database
        List<Medicalcustomer> medicalcustomerList = medicalcustomerRepository.findAll();
        assertThat(medicalcustomerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMedicalcustomer() throws Exception {
        // Initialize the database
        medicalcustomerRepository.saveAndFlush(medicalcustomer);
        medicalcustomerSearchRepository.save(medicalcustomer);
        int databaseSizeBeforeDelete = medicalcustomerRepository.findAll().size();

        // Get the medicalcustomer
        restMedicalcustomerMockMvc.perform(delete("/api/medicalcustomers/{id}", medicalcustomer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean medicalcustomerExistsInEs = medicalcustomerSearchRepository.exists(medicalcustomer.getId());
        assertThat(medicalcustomerExistsInEs).isFalse();

        // Validate the database is empty
        List<Medicalcustomer> medicalcustomerList = medicalcustomerRepository.findAll();
        assertThat(medicalcustomerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMedicalcustomer() throws Exception {
        // Initialize the database
        medicalcustomerRepository.saveAndFlush(medicalcustomer);
        medicalcustomerSearchRepository.save(medicalcustomer);

        // Search the medicalcustomer
        restMedicalcustomerMockMvc.perform(get("/api/_search/medicalcustomers?query=id:" + medicalcustomer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalcustomer.getId().intValue())))
            .andExpect(jsonPath("$.[*].medcustomernr").value(hasItem(DEFAULT_MEDCUSTOMERNR.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].zipcode").value(hasItem(DEFAULT_ZIPCODE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medicalcustomer.class);
    }
}
