package org.kiran.entmdm.web.rest;

import org.kiran.entmdm.EntmdmApp;

import org.kiran.entmdm.domain.Enterprisecustomer;
import org.kiran.entmdm.repository.EnterprisecustomerRepository;
import org.kiran.entmdm.repository.search.EnterprisecustomerSearchRepository;
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
 * Test class for the EnterprisecustomerResource REST controller.
 *
 * @see EnterprisecustomerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EntmdmApp.class)
public class EnterprisecustomerResourceIntTest {

    private static final String DEFAULT_ENTERPRISECUSTOMERNR = "AAAAAAAAAA";
    private static final String UPDATED_ENTERPRISECUSTOMERNR = "BBBBBBBBBB";

    private static final String DEFAULT_PHARMAACCTNR = "AAAAAAAAAA";
    private static final String UPDATED_PHARMAACCTNR = "BBBBBBBBBB";

    private static final String DEFAULT_MEDICALACCTNR = "AAAAAAAAAA";
    private static final String UPDATED_MEDICALACCTNR = "BBBBBBBBBB";

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

    private static final Integer DEFAULT_PHARMA_SHIP_TO = 0;
    private static final Integer UPDATED_PHARMA_SHIP_TO = 1;

    private static final Integer DEFAULT_PHARMABILLTO = 0;
    private static final Integer UPDATED_PHARMABILLTO = 1;

    private static final Integer DEFAULT_MEDICALSHIPTO = 0;
    private static final Integer UPDATED_MEDICALSHIPTO = 1;

    private static final String DEFAULT_DEALICENSENR = "AAAAAAAAAA";
    private static final String UPDATED_DEALICENSENR = "BBBBBBBBBB";

    private static final String DEFAULT_HEALTHFACILITYLICENSENR = "AAAAAAAAAA";
    private static final String UPDATED_HEALTHFACILITYLICENSENR = "BBBBBBBBBB";

    @Autowired
    private EnterprisecustomerRepository enterprisecustomerRepository;

    @Autowired
    private EnterprisecustomerSearchRepository enterprisecustomerSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEnterprisecustomerMockMvc;

    private Enterprisecustomer enterprisecustomer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EnterprisecustomerResource enterprisecustomerResource = new EnterprisecustomerResource(enterprisecustomerRepository, enterprisecustomerSearchRepository);
        this.restEnterprisecustomerMockMvc = MockMvcBuilders.standaloneSetup(enterprisecustomerResource)
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
    public static Enterprisecustomer createEntity(EntityManager em) {
        Enterprisecustomer enterprisecustomer = new Enterprisecustomer()
            .enterprisecustomernr(DEFAULT_ENTERPRISECUSTOMERNR)
            .pharmaacctnr(DEFAULT_PHARMAACCTNR)
            .medicalacctnr(DEFAULT_MEDICALACCTNR)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .zipcode(DEFAULT_ZIPCODE)
            .country(DEFAULT_COUNTRY)
            .pharmaShipTo(DEFAULT_PHARMA_SHIP_TO)
            .pharmabillto(DEFAULT_PHARMABILLTO)
            .medicalshipto(DEFAULT_MEDICALSHIPTO)
            .dealicensenr(DEFAULT_DEALICENSENR)
            .healthfacilitylicensenr(DEFAULT_HEALTHFACILITYLICENSENR);
        return enterprisecustomer;
    }

    @Before
    public void initTest() {
        enterprisecustomerSearchRepository.deleteAll();
        enterprisecustomer = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnterprisecustomer() throws Exception {
        int databaseSizeBeforeCreate = enterprisecustomerRepository.findAll().size();

        // Create the Enterprisecustomer
        restEnterprisecustomerMockMvc.perform(post("/api/enterprisecustomers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enterprisecustomer)))
            .andExpect(status().isCreated());

        // Validate the Enterprisecustomer in the database
        List<Enterprisecustomer> enterprisecustomerList = enterprisecustomerRepository.findAll();
        assertThat(enterprisecustomerList).hasSize(databaseSizeBeforeCreate + 1);
        Enterprisecustomer testEnterprisecustomer = enterprisecustomerList.get(enterprisecustomerList.size() - 1);
        assertThat(testEnterprisecustomer.getEnterprisecustomernr()).isEqualTo(DEFAULT_ENTERPRISECUSTOMERNR);
        assertThat(testEnterprisecustomer.getPharmaacctnr()).isEqualTo(DEFAULT_PHARMAACCTNR);
        assertThat(testEnterprisecustomer.getMedicalacctnr()).isEqualTo(DEFAULT_MEDICALACCTNR);
        assertThat(testEnterprisecustomer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testEnterprisecustomer.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testEnterprisecustomer.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testEnterprisecustomer.getZipcode()).isEqualTo(DEFAULT_ZIPCODE);
        assertThat(testEnterprisecustomer.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testEnterprisecustomer.getPharmaShipTo()).isEqualTo(DEFAULT_PHARMA_SHIP_TO);
        assertThat(testEnterprisecustomer.getPharmabillto()).isEqualTo(DEFAULT_PHARMABILLTO);
        assertThat(testEnterprisecustomer.getMedicalshipto()).isEqualTo(DEFAULT_MEDICALSHIPTO);
        assertThat(testEnterprisecustomer.getDealicensenr()).isEqualTo(DEFAULT_DEALICENSENR);
        assertThat(testEnterprisecustomer.getHealthfacilitylicensenr()).isEqualTo(DEFAULT_HEALTHFACILITYLICENSENR);

        // Validate the Enterprisecustomer in Elasticsearch
        Enterprisecustomer enterprisecustomerEs = enterprisecustomerSearchRepository.findOne(testEnterprisecustomer.getId());
        assertThat(enterprisecustomerEs).isEqualToComparingFieldByField(testEnterprisecustomer);
    }

    @Test
    @Transactional
    public void createEnterprisecustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enterprisecustomerRepository.findAll().size();

        // Create the Enterprisecustomer with an existing ID
        enterprisecustomer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnterprisecustomerMockMvc.perform(post("/api/enterprisecustomers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enterprisecustomer)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Enterprisecustomer> enterprisecustomerList = enterprisecustomerRepository.findAll();
        assertThat(enterprisecustomerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEnterprisecustomernrIsRequired() throws Exception {
        int databaseSizeBeforeTest = enterprisecustomerRepository.findAll().size();
        // set the field null
        enterprisecustomer.setEnterprisecustomernr(null);

        // Create the Enterprisecustomer, which fails.

        restEnterprisecustomerMockMvc.perform(post("/api/enterprisecustomers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enterprisecustomer)))
            .andExpect(status().isBadRequest());

        List<Enterprisecustomer> enterprisecustomerList = enterprisecustomerRepository.findAll();
        assertThat(enterprisecustomerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = enterprisecustomerRepository.findAll().size();
        // set the field null
        enterprisecustomer.setAddress(null);

        // Create the Enterprisecustomer, which fails.

        restEnterprisecustomerMockMvc.perform(post("/api/enterprisecustomers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enterprisecustomer)))
            .andExpect(status().isBadRequest());

        List<Enterprisecustomer> enterprisecustomerList = enterprisecustomerRepository.findAll();
        assertThat(enterprisecustomerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPharmaShipToIsRequired() throws Exception {
        int databaseSizeBeforeTest = enterprisecustomerRepository.findAll().size();
        // set the field null
        enterprisecustomer.setPharmaShipTo(null);

        // Create the Enterprisecustomer, which fails.

        restEnterprisecustomerMockMvc.perform(post("/api/enterprisecustomers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enterprisecustomer)))
            .andExpect(status().isBadRequest());

        List<Enterprisecustomer> enterprisecustomerList = enterprisecustomerRepository.findAll();
        assertThat(enterprisecustomerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPharmabilltoIsRequired() throws Exception {
        int databaseSizeBeforeTest = enterprisecustomerRepository.findAll().size();
        // set the field null
        enterprisecustomer.setPharmabillto(null);

        // Create the Enterprisecustomer, which fails.

        restEnterprisecustomerMockMvc.perform(post("/api/enterprisecustomers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enterprisecustomer)))
            .andExpect(status().isBadRequest());

        List<Enterprisecustomer> enterprisecustomerList = enterprisecustomerRepository.findAll();
        assertThat(enterprisecustomerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMedicalshiptoIsRequired() throws Exception {
        int databaseSizeBeforeTest = enterprisecustomerRepository.findAll().size();
        // set the field null
        enterprisecustomer.setMedicalshipto(null);

        // Create the Enterprisecustomer, which fails.

        restEnterprisecustomerMockMvc.perform(post("/api/enterprisecustomers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enterprisecustomer)))
            .andExpect(status().isBadRequest());

        List<Enterprisecustomer> enterprisecustomerList = enterprisecustomerRepository.findAll();
        assertThat(enterprisecustomerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnterprisecustomers() throws Exception {
        // Initialize the database
        enterprisecustomerRepository.saveAndFlush(enterprisecustomer);

        // Get all the enterprisecustomerList
        restEnterprisecustomerMockMvc.perform(get("/api/enterprisecustomers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enterprisecustomer.getId().intValue())))
            .andExpect(jsonPath("$.[*].enterprisecustomernr").value(hasItem(DEFAULT_ENTERPRISECUSTOMERNR.toString())))
            .andExpect(jsonPath("$.[*].pharmaacctnr").value(hasItem(DEFAULT_PHARMAACCTNR.toString())))
            .andExpect(jsonPath("$.[*].medicalacctnr").value(hasItem(DEFAULT_MEDICALACCTNR.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].zipcode").value(hasItem(DEFAULT_ZIPCODE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].pharmaShipTo").value(hasItem(DEFAULT_PHARMA_SHIP_TO)))
            .andExpect(jsonPath("$.[*].pharmabillto").value(hasItem(DEFAULT_PHARMABILLTO)))
            .andExpect(jsonPath("$.[*].medicalshipto").value(hasItem(DEFAULT_MEDICALSHIPTO)))
            .andExpect(jsonPath("$.[*].dealicensenr").value(hasItem(DEFAULT_DEALICENSENR.toString())))
            .andExpect(jsonPath("$.[*].healthfacilitylicensenr").value(hasItem(DEFAULT_HEALTHFACILITYLICENSENR.toString())));
    }

    @Test
    @Transactional
    public void getEnterprisecustomer() throws Exception {
        // Initialize the database
        enterprisecustomerRepository.saveAndFlush(enterprisecustomer);

        // Get the enterprisecustomer
        restEnterprisecustomerMockMvc.perform(get("/api/enterprisecustomers/{id}", enterprisecustomer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(enterprisecustomer.getId().intValue()))
            .andExpect(jsonPath("$.enterprisecustomernr").value(DEFAULT_ENTERPRISECUSTOMERNR.toString()))
            .andExpect(jsonPath("$.pharmaacctnr").value(DEFAULT_PHARMAACCTNR.toString()))
            .andExpect(jsonPath("$.medicalacctnr").value(DEFAULT_MEDICALACCTNR.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.zipcode").value(DEFAULT_ZIPCODE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.pharmaShipTo").value(DEFAULT_PHARMA_SHIP_TO))
            .andExpect(jsonPath("$.pharmabillto").value(DEFAULT_PHARMABILLTO))
            .andExpect(jsonPath("$.medicalshipto").value(DEFAULT_MEDICALSHIPTO))
            .andExpect(jsonPath("$.dealicensenr").value(DEFAULT_DEALICENSENR.toString()))
            .andExpect(jsonPath("$.healthfacilitylicensenr").value(DEFAULT_HEALTHFACILITYLICENSENR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEnterprisecustomer() throws Exception {
        // Get the enterprisecustomer
        restEnterprisecustomerMockMvc.perform(get("/api/enterprisecustomers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnterprisecustomer() throws Exception {
        // Initialize the database
        enterprisecustomerRepository.saveAndFlush(enterprisecustomer);
        enterprisecustomerSearchRepository.save(enterprisecustomer);
        int databaseSizeBeforeUpdate = enterprisecustomerRepository.findAll().size();

        // Update the enterprisecustomer
        Enterprisecustomer updatedEnterprisecustomer = enterprisecustomerRepository.findOne(enterprisecustomer.getId());
        updatedEnterprisecustomer
            .enterprisecustomernr(UPDATED_ENTERPRISECUSTOMERNR)
            .pharmaacctnr(UPDATED_PHARMAACCTNR)
            .medicalacctnr(UPDATED_MEDICALACCTNR)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .zipcode(UPDATED_ZIPCODE)
            .country(UPDATED_COUNTRY)
            .pharmaShipTo(UPDATED_PHARMA_SHIP_TO)
            .pharmabillto(UPDATED_PHARMABILLTO)
            .medicalshipto(UPDATED_MEDICALSHIPTO)
            .dealicensenr(UPDATED_DEALICENSENR)
            .healthfacilitylicensenr(UPDATED_HEALTHFACILITYLICENSENR);

        restEnterprisecustomerMockMvc.perform(put("/api/enterprisecustomers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEnterprisecustomer)))
            .andExpect(status().isOk());

        // Validate the Enterprisecustomer in the database
        List<Enterprisecustomer> enterprisecustomerList = enterprisecustomerRepository.findAll();
        assertThat(enterprisecustomerList).hasSize(databaseSizeBeforeUpdate);
        Enterprisecustomer testEnterprisecustomer = enterprisecustomerList.get(enterprisecustomerList.size() - 1);
        assertThat(testEnterprisecustomer.getEnterprisecustomernr()).isEqualTo(UPDATED_ENTERPRISECUSTOMERNR);
        assertThat(testEnterprisecustomer.getPharmaacctnr()).isEqualTo(UPDATED_PHARMAACCTNR);
        assertThat(testEnterprisecustomer.getMedicalacctnr()).isEqualTo(UPDATED_MEDICALACCTNR);
        assertThat(testEnterprisecustomer.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testEnterprisecustomer.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testEnterprisecustomer.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testEnterprisecustomer.getZipcode()).isEqualTo(UPDATED_ZIPCODE);
        assertThat(testEnterprisecustomer.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testEnterprisecustomer.getPharmaShipTo()).isEqualTo(UPDATED_PHARMA_SHIP_TO);
        assertThat(testEnterprisecustomer.getPharmabillto()).isEqualTo(UPDATED_PHARMABILLTO);
        assertThat(testEnterprisecustomer.getMedicalshipto()).isEqualTo(UPDATED_MEDICALSHIPTO);
        assertThat(testEnterprisecustomer.getDealicensenr()).isEqualTo(UPDATED_DEALICENSENR);
        assertThat(testEnterprisecustomer.getHealthfacilitylicensenr()).isEqualTo(UPDATED_HEALTHFACILITYLICENSENR);

        // Validate the Enterprisecustomer in Elasticsearch
        Enterprisecustomer enterprisecustomerEs = enterprisecustomerSearchRepository.findOne(testEnterprisecustomer.getId());
        assertThat(enterprisecustomerEs).isEqualToComparingFieldByField(testEnterprisecustomer);
    }

    @Test
    @Transactional
    public void updateNonExistingEnterprisecustomer() throws Exception {
        int databaseSizeBeforeUpdate = enterprisecustomerRepository.findAll().size();

        // Create the Enterprisecustomer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEnterprisecustomerMockMvc.perform(put("/api/enterprisecustomers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enterprisecustomer)))
            .andExpect(status().isCreated());

        // Validate the Enterprisecustomer in the database
        List<Enterprisecustomer> enterprisecustomerList = enterprisecustomerRepository.findAll();
        assertThat(enterprisecustomerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEnterprisecustomer() throws Exception {
        // Initialize the database
        enterprisecustomerRepository.saveAndFlush(enterprisecustomer);
        enterprisecustomerSearchRepository.save(enterprisecustomer);
        int databaseSizeBeforeDelete = enterprisecustomerRepository.findAll().size();

        // Get the enterprisecustomer
        restEnterprisecustomerMockMvc.perform(delete("/api/enterprisecustomers/{id}", enterprisecustomer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean enterprisecustomerExistsInEs = enterprisecustomerSearchRepository.exists(enterprisecustomer.getId());
        assertThat(enterprisecustomerExistsInEs).isFalse();

        // Validate the database is empty
        List<Enterprisecustomer> enterprisecustomerList = enterprisecustomerRepository.findAll();
        assertThat(enterprisecustomerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEnterprisecustomer() throws Exception {
        // Initialize the database
        enterprisecustomerRepository.saveAndFlush(enterprisecustomer);
        enterprisecustomerSearchRepository.save(enterprisecustomer);

        // Search the enterprisecustomer
        restEnterprisecustomerMockMvc.perform(get("/api/_search/enterprisecustomers?query=id:" + enterprisecustomer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enterprisecustomer.getId().intValue())))
            .andExpect(jsonPath("$.[*].enterprisecustomernr").value(hasItem(DEFAULT_ENTERPRISECUSTOMERNR.toString())))
            .andExpect(jsonPath("$.[*].pharmaacctnr").value(hasItem(DEFAULT_PHARMAACCTNR.toString())))
            .andExpect(jsonPath("$.[*].medicalacctnr").value(hasItem(DEFAULT_MEDICALACCTNR.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].zipcode").value(hasItem(DEFAULT_ZIPCODE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].pharmaShipTo").value(hasItem(DEFAULT_PHARMA_SHIP_TO)))
            .andExpect(jsonPath("$.[*].pharmabillto").value(hasItem(DEFAULT_PHARMABILLTO)))
            .andExpect(jsonPath("$.[*].medicalshipto").value(hasItem(DEFAULT_MEDICALSHIPTO)))
            .andExpect(jsonPath("$.[*].dealicensenr").value(hasItem(DEFAULT_DEALICENSENR.toString())))
            .andExpect(jsonPath("$.[*].healthfacilitylicensenr").value(hasItem(DEFAULT_HEALTHFACILITYLICENSENR.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Enterprisecustomer.class);
    }
}
