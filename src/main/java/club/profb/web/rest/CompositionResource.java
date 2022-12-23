package club.profb.web.rest;

import club.profb.domain.Composition;
import club.profb.repository.CompositionRepository;
import club.profb.service.CompositionService;
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
 * REST controller for managing {@link club.profb.domain.Composition}.
 */
@RestController
@RequestMapping("/api")
public class CompositionResource {

    private final Logger log = LoggerFactory.getLogger(CompositionResource.class);

    private static final String ENTITY_NAME = "composition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompositionService compositionService;

    private final CompositionRepository compositionRepository;

    public CompositionResource(CompositionService compositionService, CompositionRepository compositionRepository) {
        this.compositionService = compositionService;
        this.compositionRepository = compositionRepository;
    }

    /**
     * {@code POST  /compositions} : Create a new composition.
     *
     * @param composition the composition to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new composition, or with status {@code 400 (Bad Request)} if the composition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/compositions")
    public ResponseEntity<Composition> createComposition(@Valid @RequestBody Composition composition) throws URISyntaxException {
        log.debug("REST request to save Composition : {}", composition);
        if (composition.getId() != null) {
            throw new BadRequestAlertException("A new composition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Composition result = compositionService.save(composition);
        return ResponseEntity
            .created(new URI("/api/compositions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /compositions/:id} : Updates an existing composition.
     *
     * @param id the id of the composition to save.
     * @param composition the composition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated composition,
     * or with status {@code 400 (Bad Request)} if the composition is not valid,
     * or with status {@code 500 (Internal Server Error)} if the composition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/compositions/{id}")
    public ResponseEntity<Composition> updateComposition(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody Composition composition
    ) throws URISyntaxException {
        log.debug("REST request to update Composition : {}, {}", id, composition);
        if (composition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, composition.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!compositionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Composition result = compositionService.update(composition);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, composition.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /compositions/:id} : Partial updates given fields of an existing composition, field will ignore if it is null
     *
     * @param id the id of the composition to save.
     * @param composition the composition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated composition,
     * or with status {@code 400 (Bad Request)} if the composition is not valid,
     * or with status {@code 404 (Not Found)} if the composition is not found,
     * or with status {@code 500 (Internal Server Error)} if the composition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/compositions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Composition> partialUpdateComposition(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody Composition composition
    ) throws URISyntaxException {
        log.debug("REST request to partial update Composition partially : {}, {}", id, composition);
        if (composition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, composition.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!compositionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Composition> result = compositionService.partialUpdate(composition);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, composition.getId().toString())
        );
    }

    /**
     * {@code GET  /compositions} : get all the compositions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of compositions in body.
     */
    @GetMapping("/compositions")
    public List<Composition> getAllCompositions() {
        log.debug("REST request to get all Compositions");
        return compositionService.findAll();
    }

    /**
     * {@code GET  /compositions/:id} : get the "id" composition.
     *
     * @param id the id of the composition to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the composition, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compositions/{id}")
    public ResponseEntity<Composition> getComposition(@PathVariable UUID id) {
        log.debug("REST request to get Composition : {}", id);
        Optional<Composition> composition = compositionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(composition);
    }

    /**
     * {@code DELETE  /compositions/:id} : delete the "id" composition.
     *
     * @param id the id of the composition to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/compositions/{id}")
    public ResponseEntity<Void> deleteComposition(@PathVariable UUID id) {
        log.debug("REST request to delete Composition : {}", id);
        compositionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
