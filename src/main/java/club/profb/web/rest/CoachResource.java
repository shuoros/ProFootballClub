package club.profb.web.rest;

import club.profb.domain.Coach;
import club.profb.repository.CoachRepository;
import club.profb.service.CoachService;
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
 * REST controller for managing {@link club.profb.domain.Coach}.
 */
@RestController
@RequestMapping("/api")
public class CoachResource {

    private final Logger log = LoggerFactory.getLogger(CoachResource.class);

    private static final String ENTITY_NAME = "coach";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CoachService coachService;

    private final CoachRepository coachRepository;

    public CoachResource(CoachService coachService, CoachRepository coachRepository) {
        this.coachService = coachService;
        this.coachRepository = coachRepository;
    }

    /**
     * {@code POST  /coaches} : Create a new coach.
     *
     * @param coach the coach to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new coach, or with status {@code 400 (Bad Request)} if the coach has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/coaches")
    public ResponseEntity<Coach> createCoach(@Valid @RequestBody Coach coach) throws URISyntaxException {
        log.debug("REST request to save Coach : {}", coach);
        if (coach.getId() != null) {
            throw new BadRequestAlertException("A new coach cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Coach result = coachService.save(coach);
        return ResponseEntity
            .created(new URI("/api/coaches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /coaches/:id} : Updates an existing coach.
     *
     * @param id the id of the coach to save.
     * @param coach the coach to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coach,
     * or with status {@code 400 (Bad Request)} if the coach is not valid,
     * or with status {@code 500 (Internal Server Error)} if the coach couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/coaches/{id}")
    public ResponseEntity<Coach> updateCoach(@PathVariable(value = "id", required = false) final UUID id, @Valid @RequestBody Coach coach)
        throws URISyntaxException {
        log.debug("REST request to update Coach : {}, {}", id, coach);
        if (coach.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, coach.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!coachRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Coach result = coachService.update(coach);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coach.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /coaches/:id} : Partial updates given fields of an existing coach, field will ignore if it is null
     *
     * @param id the id of the coach to save.
     * @param coach the coach to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coach,
     * or with status {@code 400 (Bad Request)} if the coach is not valid,
     * or with status {@code 404 (Not Found)} if the coach is not found,
     * or with status {@code 500 (Internal Server Error)} if the coach couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/coaches/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Coach> partialUpdateCoach(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody Coach coach
    ) throws URISyntaxException {
        log.debug("REST request to partial update Coach partially : {}, {}", id, coach);
        if (coach.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, coach.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!coachRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Coach> result = coachService.partialUpdate(coach);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coach.getId().toString())
        );
    }

    /**
     * {@code GET  /coaches} : get all the coaches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of coaches in body.
     */
    @GetMapping("/coaches")
    public List<Coach> getAllCoaches() {
        log.debug("REST request to get all Coaches");
        return coachService.findAll();
    }

    /**
     * {@code GET  /coaches/:id} : get the "id" coach.
     *
     * @param id the id of the coach to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the coach, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/coaches/{id}")
    public ResponseEntity<Coach> getCoach(@PathVariable UUID id) {
        log.debug("REST request to get Coach : {}", id);
        Optional<Coach> coach = coachService.findOne(id);
        return ResponseUtil.wrapOrNotFound(coach);
    }

    /**
     * {@code DELETE  /coaches/:id} : delete the "id" coach.
     *
     * @param id the id of the coach to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/coaches/{id}")
    public ResponseEntity<Void> deleteCoach(@PathVariable UUID id) {
        log.debug("REST request to delete Coach : {}", id);
        coachService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
