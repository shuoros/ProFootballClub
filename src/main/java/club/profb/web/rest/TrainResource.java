package club.profb.web.rest;

import club.profb.domain.Train;
import club.profb.repository.TrainRepository;
import club.profb.service.TrainService;
import club.profb.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link club.profb.domain.Train}.
 */
@RestController
@RequestMapping("/api")
public class TrainResource {

    private final Logger log = LoggerFactory.getLogger(TrainResource.class);

    private static final String ENTITY_NAME = "train";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrainService trainService;

    private final TrainRepository trainRepository;

    public TrainResource(TrainService trainService, TrainRepository trainRepository) {
        this.trainService = trainService;
        this.trainRepository = trainRepository;
    }

    /**
     * {@code POST  /trains} : Create a new train.
     *
     * @param train the train to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new train, or with status {@code 400 (Bad Request)} if the train has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trains")
    public ResponseEntity<Train> createTrain(@RequestBody Train train) throws URISyntaxException {
        log.debug("REST request to save Train : {}", train);
        if (train.getId() != null) {
            throw new BadRequestAlertException("A new train cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Train result = trainService.save(train);
        return ResponseEntity
            .created(new URI("/api/trains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trains/:id} : Updates an existing train.
     *
     * @param id the id of the train to save.
     * @param train the train to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated train,
     * or with status {@code 400 (Bad Request)} if the train is not valid,
     * or with status {@code 500 (Internal Server Error)} if the train couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trains/{id}")
    public ResponseEntity<Train> updateTrain(@PathVariable(value = "id", required = false) final UUID id, @RequestBody Train train)
        throws URISyntaxException {
        log.debug("REST request to update Train : {}, {}", id, train);
        if (train.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, train.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Train result = trainService.update(train);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, train.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /trains/:id} : Partial updates given fields of an existing train, field will ignore if it is null
     *
     * @param id the id of the train to save.
     * @param train the train to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated train,
     * or with status {@code 400 (Bad Request)} if the train is not valid,
     * or with status {@code 404 (Not Found)} if the train is not found,
     * or with status {@code 500 (Internal Server Error)} if the train couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/trains/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Train> partialUpdateTrain(@PathVariable(value = "id", required = false) final UUID id, @RequestBody Train train)
        throws URISyntaxException {
        log.debug("REST request to partial update Train partially : {}, {}", id, train);
        if (train.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, train.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Train> result = trainService.partialUpdate(train);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, train.getId().toString())
        );
    }

    /**
     * {@code GET  /trains} : get all the trains.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trains in body.
     */
    @GetMapping("/trains")
    public List<Train> getAllTrains() {
        log.debug("REST request to get all Trains");
        return trainService.findAll();
    }

    /**
     * {@code GET  /trains/:id} : get the "id" train.
     *
     * @param id the id of the train to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the train, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trains/{id}")
    public ResponseEntity<Train> getTrain(@PathVariable UUID id) {
        log.debug("REST request to get Train : {}", id);
        Optional<Train> train = trainService.findOne(id);
        return ResponseUtil.wrapOrNotFound(train);
    }

    /**
     * {@code DELETE  /trains/:id} : delete the "id" train.
     *
     * @param id the id of the train to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trains/{id}")
    public ResponseEntity<Void> deleteTrain(@PathVariable UUID id) {
        log.debug("REST request to delete Train : {}", id);
        trainService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
