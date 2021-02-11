package com.dihouse.app.web.rest;

import com.dihouse.app.domain.Appuser;
import com.dihouse.app.repository.AppuserRepository;
import com.dihouse.app.repository.search.AppuserSearchRepository;
import com.dihouse.app.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
 * REST controller for managing {@link com.dihouse.app.domain.Appuser}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AppuserResource {

    private final Logger log = LoggerFactory.getLogger(AppuserResource.class);

    private static final String ENTITY_NAME = "appuser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppuserRepository appuserRepository;

    private final AppuserSearchRepository appuserSearchRepository;

    public AppuserResource(AppuserRepository appuserRepository, AppuserSearchRepository appuserSearchRepository) {
        this.appuserRepository = appuserRepository;
        this.appuserSearchRepository = appuserSearchRepository;
    }

    /**
     * {@code POST  /appusers} : Create a new appuser.
     *
     * @param appuser the appuser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appuser, or with status {@code 400 (Bad Request)} if the appuser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/appusers")
    public ResponseEntity<Appuser> createAppuser(@Valid @RequestBody Appuser appuser) throws URISyntaxException {
        log.debug("REST request to save Appuser : {}", appuser);
        if (appuser.getId() != null) {
            throw new BadRequestAlertException("A new appuser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Appuser result = appuserRepository.save(appuser);
        appuserSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/appusers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /appusers} : Updates an existing appuser.
     *
     * @param appuser the appuser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appuser,
     * or with status {@code 400 (Bad Request)} if the appuser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appuser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/appusers")
    public ResponseEntity<Appuser> updateAppuser(@Valid @RequestBody Appuser appuser) throws URISyntaxException {
        log.debug("REST request to update Appuser : {}", appuser);
        if (appuser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Appuser result = appuserRepository.save(appuser);
        appuserSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appuser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /appusers} : get all the appusers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appusers in body.
     */
    @GetMapping("/appusers")
    public ResponseEntity<List<Appuser>> getAllAppusers(Pageable pageable) {
        log.debug("REST request to get a page of Appusers");
        Page<Appuser> page = appuserRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /appusers/:id} : get the "id" appuser.
     *
     * @param id the id of the appuser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appuser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/appusers/{id}")
    public ResponseEntity<Appuser> getAppuser(@PathVariable Long id) {
        log.debug("REST request to get Appuser : {}", id);
        Optional<Appuser> appuser = appuserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(appuser);
    }

    /**
     * {@code DELETE  /appusers/:id} : delete the "id" appuser.
     *
     * @param id the id of the appuser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/appusers/{id}")
    public ResponseEntity<Void> deleteAppuser(@PathVariable Long id) {
        log.debug("REST request to delete Appuser : {}", id);
        appuserRepository.deleteById(id);
        appuserSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/appusers?query=:query} : search for the appuser corresponding
     * to the query.
     *
     * @param query the query of the appuser search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/appusers")
    public ResponseEntity<List<Appuser>> searchAppusers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Appusers for query {}", query);
        Page<Appuser> page = appuserSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
