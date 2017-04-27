package org.kiran.entmdm.web.rest;

import org.kiran.entmdm.EntmdmApp;

import org.kiran.entmdm.domain.Pharmacustomer;
import org.kiran.entmdm.repository.PharmacustomerRepository;
import org.kiran.entmdm.repository.search.PharmacustomerSearchRepository;
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
 * Test class for the PharmacustomerResource REST controller.
 *
 * @see PharmacustomerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EntmdmApp.class)
public class PharmacustomerResourceIntTest {

    private static final String DEFAULT_PHARMACUSTOMERNR = "AAAAAAAAAA";
    private static final String UPDATED_PHARMACUSTOMERNR = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ZIPCODE = 5;
    private static final Integer UPDATED_ZIPCODE = 4;

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final Integer DEFAULT_PHARMASHIPTO = 1;
    private static final Integer UPDATED_PHARMASHIPTO = 2;

    private static final Integer DEFAULT_PHARMABILLTO = 1;
    private static final Integer UPDATED_PHARMABILLTO = 2;

    private static final String DEFAULT_DEALICENSENR = "AAAAAAAAAA";
    private static final String UPDATED_DEALICENSENR = "BBBBBBBBBB";

    @Autowired
    private PharmacustomerRepository pharmacustomerRepository;

    @Autowired
    private PharmacustomerSearchRepository pharmacustomerSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPharmacustomerMockMvc;

    private Pharmacustomer pharmacustomer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PharmacustomerResource pharmacustomerResource = new PharmacustomerResource(pharmacustomerRepository, pharmacustomerSearchRepository);
        this.restPharmacustomerMockMvc = MockMvcBuilders.standaloneSetup(pharmacustomerResource)
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
    public static Pharmacustomer createEntity(EntityManager em) {
        Pharmacustomer pharmacustomer = new Pharmacustomer()
            .pharmacustomernr(DEFAULT_PHARMACUSTOMERNR)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .zipcode(DEFAULT_ZIPCODE)
            .country(DEFAULT_COUNTRY)
            .pharmashipto(DEFAULT_PHARMASHIPTO)
            .pharmabillto(DEFAULT_PHARMABILLTO)
            .dealicensenr(DEFAULT_DEALICENSENR);
        return pharmacustomer;
    }

    @Before
    public void initTest() {
        pharmacustomerSearchRepository.deleteAll();
        pharmacustomer = createEntity(em);
    }

    @Test
    @Transactional
    public void createPharmacustomer() throws Exception {
        int databaseSizeBeforeCreate = pharmacustomerRepository.findAll().size();

        // Create the Pharmacustomer
        restPharmacustomerMockMvc.perform(post("/api/pharmacustomers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmacustomer)))
            .andExpect(status().isCreated());

        // Validate the Pharmacustomer in the database
        List<Pharmacustomer> pharmacustomerList = pharmacustomerRepository.findAll();
        assertThat(pharmacustomerList).hasSize(databaseSizeBeforeCreate + 1);
        Pharmacustomer testPharmacustomer = pharmacustomerList.get(pharmacustomerList.size() - 1);
        assertThat(testPharmacustomer.getPharmacustomernr()).isEqualTo(DEFAULT_PHARMACUSTOMERNR);
        assertThat(testPharmacustomer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPharmacustomer.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testPharmacustomer.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testPharmacustomer.getZipcode()).isEqualTo(DEFAULT_ZIPCODE);
        assertThat(testPharmacustomer.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testPharmacustomer.getPharmashipto()).isEqualTo(DEFAULT_PHARMASHIPTO);
        assertThat(testPharmacustomer.getPharmabillto()).isEqualTo(DEFAULT_PHARMABILLTO);
        assertThat(testPharmacustomer.getDealicensenr()).isEqualTo(DEFAULT_DEALICENSENR);

        // Validate the Pharmacustomer in Elasticsearch
        Pharmacustomer pharmacustomerEs = pharmacustomerSearchRepository.findOne(testPharmacustomer.getId());
        assertThat(pharmacustomerEs).isEqualToComparingFieldByField(testPharmacustomer);
    }

    @Test
    @Transactional
    public void createPharmacustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pharmacustomerRepository.findAll().size();

        // Create the Pharmacustomer with an existing ID
        pharmacustomer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPharmacustomerMockMvc.perform(post("/api/pharmacustomers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmacustomer)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Pharmacustomer> pharmacustomerList = pharmacustomerRepository.findAll();
        assertThat(pharmacustomerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPharmacustomernrIsRequired() throws Exception {
        int databaseSizeBeforeTest = pharmacustomerRepository.findAll().size();
        // set the field null
        pharmacustomer.setPharmacustomernr(null);

        // Create the Pharmacustomer, which fails.

        restPharmacustomerMockMvc.perform(post("/api/pharmacustomers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmacustomer)))
            .andExpect(status().isBadRequest());

        List<Pharmacustomer> pharmacustomerList = pharmacustomerRepository.findAll();
        assertThat(pharmacustomerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = pharmacustomerRepository.findAll().size();
        // set the field null
        pharmacustomer.setAddress(null);

        // Create the Pharmacustomer, which fails.

        restPharmacustomerMockMvc.perform(post("/api/pharmacustomers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmacustomer)))
            .andExpect(status().isBadRequest());

        List<Pharmacustomer> pharmacustomerList = pharmacustomerRepository.findAll();
        assertThat(pharmacustomerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = pharmacustomerRepository.findAll().size();
        // set the field null
        pharmacustomer.setCity(null);

        // Create the Pharmacustomer, which fails.

        restPharmacustomerMockMvc.perform(post("/api/pharmacustomers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmacustomer)))
            .andExpect(status().isBadRequest());

        List<Pharmacustomer> pharmacustomerList = pharmacustomerRepository.findAll();
        assertThat(pharmacustomerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pharmacustomerRepository.findAll().size();
        // set the field null
        pharmacustomer.setState(null);

        // Create the Pharmacustomer, which fails.

        restPharmacustomerMockMvc.perform(post("/api/pharmacustomers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmacustomer)))
            .andExpect(status().isBadRequest());

        List<Pharmacustomer> pharmacustomerList = pharmacustomerRepository.findAll();
        assertThat(pharmacustomerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkZipcodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pharmacustomerRepository.findAll().size();
        // set the field null
        pharmacustomer.setZipcode(null);

        // Create the Pharmacustomer, which fails.

        restPharmacustomerMockMvc.perform(post("/api/pharmacustomers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmacustomer)))
            .andExpect(status().isBadRequest());

        List<Pharmacustomer> pharmacustomerList = pharmacustomerRepository.findAll();
        assertThat(pharmacustomerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = pharmacustomerRepository.findAll().size();
        // set the field null
        pharmacustomer.setCountry(null);

        // Create the Pharmacustomer, which fails.

        restPharmacustomerMockMvc.perform(post("/api/pharmacustomers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmacustomer)))
            .andExpect(status().isBadRequest());

        List<Pharmacustomer> pharmacustomerList = pharmacustomerRepository.findAll();
        assertThat(pharmacustomerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPharmacustomers() throws Exception {
        // Initialize the database
        pharmacustomerRepository.saveAndFlush(pharmacustomer);

        // Get all the pharmacustomerList
        restPharmacustomerMockMvc.perform(get("/api/pharmacustomers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pharmacustomer.getId().intValue())))
            .andExpect(jsonPath("$.[*].pharmacustomernr").value(hasItem(DEFAULT_PHARMACUSTOMERNR.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].zipcode").value(hasItem(DEFAULT_ZIPCODE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].pharmashipto").value(hasItem(DEFAULT_PHARMASHIPTO)))
            .andExpect(jsonPath("$.[*].pharmabillto").value(hasItem(DEFAULT_PHARMABILLTO)))
            .andExpect(jsonPath("$.[*].dealicensenr").value(hasItem(DEFAULT_DEALICENSENR.toString())));
    }

    @Test
    @Transactional
    public void getPharmacustomer() throws Exception {
        // Initialize the database
        pharmacustomerRepository.saveAndFlush(pharmacustomer);

        // Get the pharmacustomer
        restPharmacustomerMockMvc.perform(get("/api/pharmacustomers/{id}", pharmacustomer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pharmacustomer.getId().intValue()))
            .andExpect(jsonPath("$.pharmacustomernr").value(DEFAULT_PHARMACUSTOMERNR.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.zipcode").value(DEFAULT_ZIPCODE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.pharmashipto").value(DEFAULT_PHARMASHIPTO))
            .andExpect(jsonPath("$.pharmabillto").value(DEFAULT_PHARMABILLTO))
            .andExpect(jsonPath("$.dealicensenr").value(DEFAULT_DEALICENSENR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPharmacustomer() throws Exception {
        // Get the pharmacustomer
        restPharmacustomerMockMvc.perform(get("/api/pharmacustomers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePharmacustomer() throws Exception {
        // Initialize the database
        pharmacustomerRepository.saveAndFlush(pharmacustomer);
        pharmacustomerSearchRepository.save(pharmacustomer);
        int databaseSizeBeforeUpdate = pharmacustomerRepository.findAll().size();

        // Update the pharmacustomer
        Pharmacustomer updatedPharmacustomer = pharmacustomerRepository.findOne(pharmacustomer.getId());
        updatedPharmacustomer
            .pharmacustomernr(UPDATED_PHARMACUSTOMERNR)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .zipcode(UPDATED_ZIPCODE)
            .country(UPDATED_COUNTRY)
            .pharmashipto(UPDATED_PHARMASHIPTO)
            .pharmabillto(UPDATED_PHARMABILLTO)
            .dealicensenr(UPDATED_DEALICENSENR);

        restPharmacustomerMockMvc.perform(put("/api/pharmacustomers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPharmacustomer)))
            .andExpect(status().isOk());

        // Validate the Pharmacustomer in the database
        List<Pharmacustomer> pharmacustomerList = pharmacustomerRepository.findAll();
        assertThat(pharmacustomerList).hasSize(databaseSizeBeforeUpdate);
        Pharmacustomer testPharmacustomer = pharmacustomerList.get(pharmacustomerList.size() - 1);
        assertThat(testPharmacustomer.getPharmacustomernr()).isEqualTo(UPDATED_PHARMACUSTOMERNR);
        assertThat(testPharmacustomer.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPharmacustomer.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testPharmacustomer.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testPharmacustomer.getZipcode()).isEqualTo(UPDATED_ZIPCODE);
        assertThat(testPharmacustomer.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testPharmacustomer.getPharmashipto()).isEqualTo(UPDATED_PHARMASHIPTO);
        assertThat(testPharmacustomer.getPharmabillto()).isEqualTo(UPDATED_PHARMABILLTO);
        assertThat(testPharmacustomer.getDealicensenr()).isEqualTo(UPDATED_DEALICENSENR);

        // Validate the Pharmacustomer in Elasticsearch
        Pharmacustomer pharmacustomerEs = pharmacustomerSearchRepository.findOne(testPharmacustomer.getId());
        assertThat(pharmacustomerEs).isEqualToComparingFieldByField(testPharmacustomer);
    }

    @Test
    @Transactional
    public void updateNonExistingPharmacustomer() throws Exception {
        int databaseSizeBeforeUpdate = pharmacustomerRepository.findAll().size();

        // Create the Pharmacustomer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPharmacustomerMockMvc.perform(put("/api/pharmacustomers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmacustomer)))
            .andExpect(status().isCreated());

        // Validate the Pharmacustomer in the database
        List<Pharmacustomer> pharmacustomerList = pharmacustomerRepository.findAll();
        assertThat(pharmacustomerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePharmacustomer() throws Exception {
        // Initialize the database
        pharmacustomerRepository.saveAndFlush(pharmacustomer);
        pharmacustomerSearchRepository.save(pharmacustomer);
        int databaseSizeBeforeDelete = pharmacustomerRepository.findAll().size();

        // Get the pharmacustomer
        restPharmacustomerMockMvc.perform(delete("/api/pharmacustomers/{id}", pharmacustomer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean pharmacustomerExistsInEs = pharmacustomerSearchRepository.exists(pharmacustomer.getId());
        assertThat(pharmacustomerExistsInEs).isFalse();

        // Validate the database is empty
        List<Pharmacustomer> pharmacustomerList = pharmacustomerRepository.findAll();
        assertThat(pharmacustomerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPharmacustomer() throws Exception {
        // Initialize the database
        pharmacustomerRepository.saveAndFlush(pharmacustomer);
        pharmacustomerSearchRepository.save(pharmacustomer);

        // Search the pharmacustomer
        restPharmacustomerMockMvc.perform(get("/api/_search/pharmacustomers?query=id:" + pharmacustomer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pharmacustomer.getId().intValue())))
            .andExpect(jsonPath("$.[*].pharmacustomernr").value(hasItem(DEFAULT_PHARMACUSTOMERNR.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].zipcode").value(hasItem(DEFAULT_ZIPCODE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].pharmashipto").value(hasItem(DEFAULT_PHARMASHIPTO)))
            .andExpect(jsonPath("$.[*].pharmabillto").value(hasItem(DEFAULT_PHARMABILLTO)))
            .andExpect(jsonPath("$.[*].dealicensenr").value(hasItem(DEFAULT_DEALICENSENR.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pharmacustomer.class);
    }
}
