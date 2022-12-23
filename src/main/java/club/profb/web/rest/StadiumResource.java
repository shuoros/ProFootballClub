package club.profb.web.rest;

import club.profb.domain.Stadium;
import club.profb.repository.StadiumRepository;
import club.profb.service.StadiumService;
import club.profb.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link club.profb.domain.Stadium}.
 */
@RestController
@RequestMapping("/api")
public class StadiumResource {

    private final Logger log = LoggerFactory.getLogger(StadiumResource.class);

    private static final String ENTITY_NAME = "stadium";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StadiumService stadiumService;

    private final StadiumRepository stadiumRepository;

    public StadiumResource(StadiumService stadiumService, StadiumRepository stadiumRepository) {
        this.stadiumService = stadiumService;
        this.stadiumRepository = stadiumRepository;
    }

    /**
     * {@code POST  /stadiums} : Create a new stadium.
     *
     * @param stadium the stadium to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stadium, or with status {@code 400 (Bad Request)} if the stadium has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stadiums")
    public ResponseEntity<Stadium> createStadium(@Valid @RequestBody Stadium stadium) throws URISyntaxException {
        log.debug("REST request to save Stadium : {}", stadium);
        if (stadium.getId() != null) {
            throw new BadRequestAlertException("A new stadium cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Stadium result = stadiumService.save(stadium);
        return ResponseEntity
            .created(new URI("/api/stadiums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stadiums/:id} : Updates an existing stadium.
     *
     * @param id the id of the stadium to save.
     * @param stadium the stadium to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stadium,
     * or with status {@code 400 (Bad Request)} if the stadium is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stadium couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stadiums/{id}")
    public ResponseEntity<Stadium> updateStadium(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody Stadium stadium
    ) throws URISyntaxException {
        log.debug("REST request to update Stadium : {}, {}", id, stadium);
        if (stadium.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stadium.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stadiumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Stadium result = stadiumService.update(stadium);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stadium.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /stadiums/:id} : Partial updates given fields of an existing stadium, field will ignore if it is null
     *
     * @param id the id of the stadium to save.
     * @param stadium the stadium to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stadium,
     * or with status {@code 400 (Bad Request)} if the stadium is not valid,
     * or with status {@code 404 (Not Found)} if the stadium is not found,
     * or with status {@code 500 (Internal Server Error)} if the stadium couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/stadiums/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Stadium> partialUpdateStadium(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody Stadium stadium
    ) throws URISyntaxException {
        log.debug("REST request to partial update Stadium partially : {}, {}", id, stadium);
        if (stadium.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stadium.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stadiumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Stadium> result = stadiumService.partialUpdate(stadium);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stadium.getId().toString())
        );
    }

    /**
     * {@code GET  /stadiums} : get all the stadiums.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stadiums in body.
     */
    @GetMapping("/stadiums")
    public List<Stadium> getAllStadiums(@RequestParam(required = false) String filter) {
        if ("team-is-null".equals(filter)) {
            log.debug("REST request to get all Stadiums where team is null");
            return stadiumService.findAllWhereTeamIsNull();
        }
        log.debug("REST request to get all Stadiums");
        return stadiumService.findAll();
    }

    /**
     * {@code GET  /stadiums/:id} : get the "id" stadium.
     *
     * @param id the id of the stadium to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stadium, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stadiums/{id}")
    public ResponseEntity<Stadium> getStadium(@PathVariable UUID id) {
        log.debug("REST request to get Stadium : {}", id);
        Optional<Stadium> stadium = stadiumService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stadium);
    }

    /**
     * {@code DELETE  /stadiums/:id} : delete the "id" stadium.
     *
     * @param id the id of the stadium to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stadiums/{id}")
    public ResponseEntity<Void> deleteStadium(@PathVariable UUID id) {
        log.debug("REST request to delete Stadium : {}", id);
        stadiumService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
