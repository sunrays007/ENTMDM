package org.kiran.entmdm.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.kiran.entmdm.domain.Enterprisecustomer;

import org.kiran.entmdm.repository.EnterprisecustomerRepository;
import org.kiran.entmdm.repository.search.EnterprisecustomerSearchRepository;
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
 * REST controller for managing Enterprisecustomer.
 */
@RestController
@RequestMapping("/api")
public class EnterprisecustomerResource {

    private final Logger log = LoggerFactory.getLogger(EnterprisecustomerResource.class);

    private static final String ENTITY_NAME = "enterprisecustomer";
        
    private final EnterprisecustomerRepository enterprisecustomerRepository;

    private final EnterprisecustomerSearchRepository enterprisecustomerSearchRepository;

    public EnterprisecustomerResource(EnterprisecustomerRepository enterprisecustomerRepository, EnterprisecustomerSearchRepository enterprisecustomerSearchRepository) {
        this.enterprisecustomerRepository = enterprisecustomerRepository;
        this.enterprisecustomerSearchRepository = enterprisecustomerSearchRepository;
    }

    /**
     * POST  /enterprisecustomers : Create a new enterprisecustomer.
     *
     * @param enterprisecustomer the enterprisecustomer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new enterprisecustomer, or with status 400 (Bad Request) if the enterprisecustomer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/enterprisecustomers")
    @Timed
    public ResponseEntity<Enterprisecustomer> createEnterprisecustomer(@Valid @RequestBody Enterprisecustomer enterprisecustomer) throws URISyntaxException {
        log.debug("REST request to save Enterprisecustomer : {}", enterprisecustomer);
        if (enterprisecustomer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new enterprisecustomer cannot already have an ID")).body(null);
        }
        Enterprisecustomer result = enterprisecustomerRepository.save(enterprisecustomer);
        enterprisecustomerSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/enterprisecustomers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /enterprisecustomers : Updates an existing enterprisecustomer.
     *
     * @param enterprisecustomer the enterprisecustomer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated enterprisecustomer,
     * or with status 400 (Bad Request) if the enterprisecustomer is not valid,
     * or with status 500 (Internal Server Error) if the enterprisecustomer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/enterprisecustomers")
    @Timed
    public ResponseEntity<Enterprisecustomer> updateEnterprisecustomer(@Valid @RequestBody Enterprisecustomer enterprisecustomer) throws URISyntaxException {
        log.debug("REST request to update Enterprisecustomer : {}", enterprisecustomer);
        if (enterprisecustomer.getId() == null) {
            return createEnterprisecustomer(enterprisecustomer);
        }
        Enterprisecustomer result = enterprisecustomerRepository.save(enterprisecustomer);
        enterprisecustomerSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, enterprisecustomer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /enterprisecustomers : get all the enterprisecustomers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of enterprisecustomers in body
     */
    @GetMapping("/enterprisecustomers")
    @Timed
    public ResponseEntity<List<Enterprisecustomer>> getAllEnterprisecustomers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Enterprisecustomers");
        Page<Enterprisecustomer> page = enterprisecustomerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/enterprisecustomers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /enterprisecustomers/:id : get the "id" enterprisecustomer.
     *
     * @param id the id of the enterprisecustomer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the enterprisecustomer, or with status 404 (Not Found)
     */
    @GetMapping("/enterprisecustomers/{id}")
    @Timed
    public ResponseEntity<Enterprisecustomer> getEnterprisecustomer(@PathVariable Long id) {
        log.debug("REST request to get Enterprisecustomer : {}", id);
        Enterprisecustomer enterprisecustomer = enterprisecustomerRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(enterprisecustomer));
    }

    /**
     * DELETE  /enterprisecustomers/:id : delete the "id" enterprisecustomer.
     *
     * @param id the id of the enterprisecustomer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/enterprisecustomers/{id}")
    @Timed
    public ResponseEntity<Void> deleteEnterprisecustomer(@PathVariable Long id) {
        log.debug("REST request to delete Enterprisecustomer : {}", id);
        enterprisecustomerRepository.delete(id);
        enterprisecustomerSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/enterprisecustomers?query=:query : search for the enterprisecustomer corresponding
     * to the query.
     *
     * @param query the query of the enterprisecustomer search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/enterprisecustomers")
    @Timed
    public ResponseEntity<List<Enterprisecustomer>> searchEnterprisecustomers(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Enterprisecustomers for query {}", query);
        Page<Enterprisecustomer> page = enterprisecustomerSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/enterprisecustomers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
