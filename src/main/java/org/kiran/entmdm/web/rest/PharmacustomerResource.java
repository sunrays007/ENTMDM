package org.kiran.entmdm.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.kiran.entmdm.domain.Pharmacustomer;

import org.kiran.entmdm.repository.PharmacustomerRepository;
import org.kiran.entmdm.repository.search.PharmacustomerSearchRepository;
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
 * REST controller for managing Pharmacustomer.
 */
@RestController
@RequestMapping("/api")
public class PharmacustomerResource {

    private final Logger log = LoggerFactory.getLogger(PharmacustomerResource.class);

    private static final String ENTITY_NAME = "pharmacustomer";
        
    private final PharmacustomerRepository pharmacustomerRepository;

    private final PharmacustomerSearchRepository pharmacustomerSearchRepository;

    public PharmacustomerResource(PharmacustomerRepository pharmacustomerRepository, PharmacustomerSearchRepository pharmacustomerSearchRepository) {
        this.pharmacustomerRepository = pharmacustomerRepository;
        this.pharmacustomerSearchRepository = pharmacustomerSearchRepository;
    }

    /**
     * POST  /pharmacustomers : Create a new pharmacustomer.
     *
     * @param pharmacustomer the pharmacustomer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pharmacustomer, or with status 400 (Bad Request) if the pharmacustomer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pharmacustomers")
    @Timed
    public ResponseEntity<Pharmacustomer> createPharmacustomer(@Valid @RequestBody Pharmacustomer pharmacustomer) throws URISyntaxException {
        log.debug("REST request to save Pharmacustomer : {}", pharmacustomer);
        if (pharmacustomer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pharmacustomer cannot already have an ID")).body(null);
        }
        Pharmacustomer result = pharmacustomerRepository.save(pharmacustomer);
        pharmacustomerSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pharmacustomers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pharmacustomers : Updates an existing pharmacustomer.
     *
     * @param pharmacustomer the pharmacustomer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pharmacustomer,
     * or with status 400 (Bad Request) if the pharmacustomer is not valid,
     * or with status 500 (Internal Server Error) if the pharmacustomer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pharmacustomers")
    @Timed
    public ResponseEntity<Pharmacustomer> updatePharmacustomer(@Valid @RequestBody Pharmacustomer pharmacustomer) throws URISyntaxException {
        log.debug("REST request to update Pharmacustomer : {}", pharmacustomer);
        if (pharmacustomer.getId() == null) {
            return createPharmacustomer(pharmacustomer);
        }
        Pharmacustomer result = pharmacustomerRepository.save(pharmacustomer);
        pharmacustomerSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pharmacustomer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pharmacustomers : get all the pharmacustomers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pharmacustomers in body
     */
    @GetMapping("/pharmacustomers")
    @Timed
    public ResponseEntity<List<Pharmacustomer>> getAllPharmacustomers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Pharmacustomers");
        Page<Pharmacustomer> page = pharmacustomerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pharmacustomers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pharmacustomers/:id : get the "id" pharmacustomer.
     *
     * @param id the id of the pharmacustomer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pharmacustomer, or with status 404 (Not Found)
     */
    @GetMapping("/pharmacustomers/{id}")
    @Timed
    public ResponseEntity<Pharmacustomer> getPharmacustomer(@PathVariable Long id) {
        log.debug("REST request to get Pharmacustomer : {}", id);
        Pharmacustomer pharmacustomer = pharmacustomerRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pharmacustomer));
    }

    /**
     * DELETE  /pharmacustomers/:id : delete the "id" pharmacustomer.
     *
     * @param id the id of the pharmacustomer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pharmacustomers/{id}")
    @Timed
    public ResponseEntity<Void> deletePharmacustomer(@PathVariable Long id) {
        log.debug("REST request to delete Pharmacustomer : {}", id);
        pharmacustomerRepository.delete(id);
        pharmacustomerSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pharmacustomers?query=:query : search for the pharmacustomer corresponding
     * to the query.
     *
     * @param query the query of the pharmacustomer search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/pharmacustomers")
    @Timed
    public ResponseEntity<List<Pharmacustomer>> searchPharmacustomers(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Pharmacustomers for query {}", query);
        Page<Pharmacustomer> page = pharmacustomerSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/pharmacustomers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
