package club.profb.web.rest;

import club.profb.domain.Cup;
import club.profb.repository.CupRepository;
import club.profb.service.CupService;
import club.profb.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
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
 * REST controller for managing {@link club.profb.domain.Cup}.
 */
@RestController
@RequestMapping("/api")
public class CupResource {

    private final Logger log = LoggerFactory.getLogger(CupResource.class);

    private static final String ENTITY_NAME = "cup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CupService cupService;

    private final CupRepository cupRepository;

    public CupResource(CupService cupService, CupRepository cupRepository) {
        this.cupService = cupService;
        this.cupRepository = cupRepository;
    }

    /**
     * {@code POST  /cups} : Create a new cup.
     *
     * @param cup the cup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cup, or with status {@code 400 (Bad Request)} if the cup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cups")
    public ResponseEntity<Cup> createCup(@Valid @RequestBody Cup cup) throws URISyntaxException {
        log.debug("REST request to save Cup : {}", cup);
        if (cup.getId() != null) {
            throw new BadRequestAlertException("A new cup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cup result = cupService.save(cup);
        return ResponseEntity
            .created(new URI("/api/cups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cups/:id} : Updates an existing cup.
     *
     * @param id the id of the cup to save.
     * @param cup the cup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cup,
     * or with status {@code 400 (Bad Request)} if the cup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cups/{id}")
    public ResponseEntity<Cup> updateCup(@PathVariable(value = "id", required = false) final UUID id, @Valid @RequestBody Cup cup)
        throws URISyntaxException {
        log.debug("REST request to update Cup : {}, {}", id, cup);
        if (cup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Cup result = cupService.update(cup);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cup.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cups/:id} : Partial updates given fields of an existing cup, field will ignore if it is null
     *
     * @param id the id of the cup to save.
     * @param cup the cup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cup,
     * or with status {@code 400 (Bad Request)} if the cup is not valid,
     * or with status {@code 404 (Not Found)} if the cup is not found,
     * or with status {@code 500 (Internal Server Error)} if the cup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Cup> partialUpdateCup(@PathVariable(value = "id", required = false) final UUID id, @NotNull @RequestBody Cup cup)
        throws URISyntaxException {
        log.debug("REST request to partial update Cup partially : {}, {}", id, cup);
        if (cup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Cup> result = cupService.partialUpdate(cup);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cup.getId().toString())
        );
    }

    /**
     * {@code GET  /cups} : get all the cups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cups in body.
     */
    @GetMapping("/cups")
    public List<Cup> getAllCups() {
        log.debug("REST request to get all Cups");
        return cupService.findAll();
    }

    /**
     * {@code GET  /cups/:id} : get the "id" cup.
     *
     * @param id the id of the cup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cups/{id}")
    public ResponseEntity<Cup> getCup(@PathVariable UUID id) {
        log.debug("REST request to get Cup : {}", id);
        Optional<Cup> cup = cupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cup);
    }

    /**
     * {@code DELETE  /cups/:id} : delete the "id" cup.
     *
     * @param id the id of the cup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cups/{id}")
    public ResponseEntity<Void> deleteCup(@PathVariable UUID id) {
        log.debug("REST request to delete Cup : {}", id);
        cupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
