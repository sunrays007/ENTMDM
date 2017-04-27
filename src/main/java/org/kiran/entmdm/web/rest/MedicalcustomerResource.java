package org.kiran.entmdm.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.kiran.entmdm.domain.Medicalcustomer;

import org.kiran.entmdm.repository.MedicalcustomerRepository;
import org.kiran.entmdm.repository.search.MedicalcustomerSearchRepository;
import org.kiran.entmdm.web.rest.util.HeaderUtil;
import org.kiran.entmdm.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Medicalcustomer.
 */
@RestController
@RequestMapping("/api")
public class MedicalcustomerResource {

    private final Logger log = LoggerFactory.getLogger(MedicalcustomerResource.class);

    private static final String ENTITY_NAME = "medicalcustomer";
        
    private final MedicalcustomerRepository medicalcustomerRepository;

    private final MedicalcustomerSearchRepository medicalcustomerSearchRepository;

    public MedicalcustomerResource(MedicalcustomerRepository medicalcustomerRepository, MedicalcustomerSearchRepository medicalcustomerSearchRepository) {
        this.medicalcustomerRepository = medicalcustomerRepository;
        this.medicalcustomerSearchRepository = medicalcustomerSearchRepository;
    }

    /**
     * POST  /medicalcustomers : Create a new medicalcustomer.
     *
     * @param medicalcustomer the medicalcustomer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medicalcustomer, or with status 400 (Bad Request) if the medicalcustomer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medicalcustomers")
    @Timed
    public ResponseEntity<Medicalcustomer> createMedicalcustomer(@Valid @RequestBody Medicalcustomer medicalcustomer) throws URISyntaxException {
        log.debug("REST request to save Medicalcustomer : {}", medicalcustomer);
        if (medicalcustomer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new medicalcustomer cannot already have an ID")).body(null);
        }
        Medicalcustomer result = medicalcustomerRepository.save(medicalcustomer);
        medicalcustomerSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/medicalcustomers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medicalcustomers : Updates an existing medicalcustomer.
     *
     * @param medicalcustomer the medicalcustomer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medicalcustomer,
     * or with status 400 (Bad Request) if the medicalcustomer is not valid,
     * or with status 500 (Internal Server Error) if the medicalcustomer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medicalcustomers")
    @Timed
    public ResponseEntity<Medicalcustomer> updateMedicalcustomer(@Valid @RequestBody Medicalcustomer medicalcustomer) throws URISyntaxException {
        log.debug("REST request to update Medicalcustomer : {}", medicalcustomer);
        if (medicalcustomer.getId() == null) {
            return createMedicalcustomer(medicalcustomer);
        }
        Medicalcustomer result = medicalcustomerRepository.save(medicalcustomer);
        medicalcustomerSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medicalcustomer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medicalcustomers : get all the medicalcustomers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of medicalcustomers in body
     */
    @GetMapping("/medicalcustomers")
    @Timed
    public ResponseEntity<List<Medicalcustomer>> getAllMedicalcustomers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Medicalcustomers");
        Page<Medicalcustomer> page = medicalcustomerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/medicalcustomers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /medicalcustomers/:id : get the "id" medicalcustomer.
     *
     * @param id the id of the medicalcustomer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medicalcustomer, or with status 404 (Not Found)
     */
    @GetMapping("/medicalcustomers/{id}")
    @Timed
    public ResponseEntity<Medicalcustomer> getMedicalcustomer(@PathVariable Long id) {
        log.debug("REST request to get Medicalcustomer : {}", id);
        Medicalcustomer medicalcustomer = medicalcustomerRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(medicalcustomer));
    }

    /**
     * DELETE  /medicalcustomers/:id : delete the "id" medicalcustomer.
     *
     * @param id the id of the medicalcustomer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medicalcustomers/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedicalcustomer(@PathVariable Long id) {
        log.debug("REST request to delete Medicalcustomer : {}", id);
        medicalcustomerRepository.delete(id);
        medicalcustomerSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/medicalcustomers?query=:query : search for the medicalcustomer corresponding
     * to the query.
     *
     * @param query the query of the medicalcustomer search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/medicalcustomers")
    @Timed
    public ResponseEntity<List<Medicalcustomer>> searchMedicalcustomers(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Medicalcustomers for query {}", query);
        Page<Medicalcustomer> page = medicalcustomerSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/medicalcustomers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
