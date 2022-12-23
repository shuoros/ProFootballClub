package club.profb.web.rest;

import club.profb.domain.PlayerArrange;
import club.profb.repository.PlayerArrangeRepository;
import club.profb.service.PlayerArrangeService;
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
 * REST controller for managing {@link club.profb.domain.PlayerArrange}.
 */
@RestController
@RequestMapping("/api")
public class PlayerArrangeResource {

    private final Logger log = LoggerFactory.getLogger(PlayerArrangeResource.class);

    private static final String ENTITY_NAME = "playerArrange";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlayerArrangeService playerArrangeService;

    private final PlayerArrangeRepository playerArrangeRepository;

    public PlayerArrangeResource(PlayerArrangeService playerArrangeService, PlayerArrangeRepository playerArrangeRepository) {
        this.playerArrangeService = playerArrangeService;
        this.playerArrangeRepository = playerArrangeRepository;
    }

    /**
     * {@code POST  /player-arranges} : Create a new playerArrange.
     *
     * @param playerArrange the playerArrange to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new playerArrange, or with status {@code 400 (Bad Request)} if the playerArrange has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/player-arranges")
    public ResponseEntity<PlayerArrange> createPlayerArrange(@RequestBody PlayerArrange playerArrange) throws URISyntaxException {
        log.debug("REST request to save PlayerArrange : {}", playerArrange);
        if (playerArrange.getId() != null) {
            throw new BadRequestAlertException("A new playerArrange cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlayerArrange result = playerArrangeService.save(playerArrange);
        return ResponseEntity
            .created(new URI("/api/player-arranges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /player-arranges/:id} : Updates an existing playerArrange.
     *
     * @param id the id of the playerArrange to save.
     * @param playerArrange the playerArrange to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated playerArrange,
     * or with status {@code 400 (Bad Request)} if the playerArrange is not valid,
     * or with status {@code 500 (Internal Server Error)} if the playerArrange couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/player-arranges/{id}")
    public ResponseEntity<PlayerArrange> updatePlayerArrange(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody PlayerArrange playerArrange
    ) throws URISyntaxException {
        log.debug("REST request to update PlayerArrange : {}, {}", id, playerArrange);
        if (playerArrange.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, playerArrange.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!playerArrangeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlayerArrange result = playerArrangeService.update(playerArrange);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, playerArrange.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /player-arranges/:id} : Partial updates given fields of an existing playerArrange, field will ignore if it is null
     *
     * @param id the id of the playerArrange to save.
     * @param playerArrange the playerArrange to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated playerArrange,
     * or with status {@code 400 (Bad Request)} if the playerArrange is not valid,
     * or with status {@code 404 (Not Found)} if the playerArrange is not found,
     * or with status {@code 500 (Internal Server Error)} if the playerArrange couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/player-arranges/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlayerArrange> partialUpdatePlayerArrange(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody PlayerArrange playerArrange
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlayerArrange partially : {}, {}", id, playerArrange);
        if (playerArrange.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, playerArrange.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!playerArrangeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlayerArrange> result = playerArrangeService.partialUpdate(playerArrange);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, playerArrange.getId().toString())
        );
    }

    /**
     * {@code GET  /player-arranges} : get all the playerArranges.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of playerArranges in body.
     */
    @GetMapping("/player-arranges")
    public List<PlayerArrange> getAllPlayerArranges() {
        log.debug("REST request to get all PlayerArranges");
        return playerArrangeService.findAll();
    }

    /**
     * {@code GET  /player-arranges/:id} : get the "id" playerArrange.
     *
     * @param id the id of the playerArrange to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the playerArrange, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/player-arranges/{id}")
    public ResponseEntity<PlayerArrange> getPlayerArrange(@PathVariable UUID id) {
        log.debug("REST request to get PlayerArrange : {}", id);
        Optional<PlayerArrange> playerArrange = playerArrangeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(playerArrange);
    }

    /**
     * {@code DELETE  /player-arranges/:id} : delete the "id" playerArrange.
     *
     * @param id the id of the playerArrange to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/player-arranges/{id}")
    public ResponseEntity<Void> deletePlayerArrange(@PathVariable UUID id) {
        log.debug("REST request to delete PlayerArrange : {}", id);
        playerArrangeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
