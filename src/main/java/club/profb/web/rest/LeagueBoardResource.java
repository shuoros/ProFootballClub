package club.profb.web.rest;

import club.profb.domain.LeagueBoard;
import club.profb.repository.LeagueBoardRepository;
import club.profb.service.LeagueBoardService;
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
 * REST controller for managing {@link club.profb.domain.LeagueBoard}.
 */
@RestController
@RequestMapping("/api")
public class LeagueBoardResource {

    private final Logger log = LoggerFactory.getLogger(LeagueBoardResource.class);

    private static final String ENTITY_NAME = "leagueBoard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeagueBoardService leagueBoardService;

    private final LeagueBoardRepository leagueBoardRepository;

    public LeagueBoardResource(LeagueBoardService leagueBoardService, LeagueBoardRepository leagueBoardRepository) {
        this.leagueBoardService = leagueBoardService;
        this.leagueBoardRepository = leagueBoardRepository;
    }

    /**
     * {@code POST  /league-boards} : Create a new leagueBoard.
     *
     * @param leagueBoard the leagueBoard to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leagueBoard, or with status {@code 400 (Bad Request)} if the leagueBoard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/league-boards")
    public ResponseEntity<LeagueBoard> createLeagueBoard(@Valid @RequestBody LeagueBoard leagueBoard) throws URISyntaxException {
        log.debug("REST request to save LeagueBoard : {}", leagueBoard);
        if (leagueBoard.getId() != null) {
            throw new BadRequestAlertException("A new leagueBoard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeagueBoard result = leagueBoardService.save(leagueBoard);
        return ResponseEntity
            .created(new URI("/api/league-boards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /league-boards/:id} : Updates an existing leagueBoard.
     *
     * @param id the id of the leagueBoard to save.
     * @param leagueBoard the leagueBoard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leagueBoard,
     * or with status {@code 400 (Bad Request)} if the leagueBoard is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leagueBoard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/league-boards/{id}")
    public ResponseEntity<LeagueBoard> updateLeagueBoard(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody LeagueBoard leagueBoard
    ) throws URISyntaxException {
        log.debug("REST request to update LeagueBoard : {}, {}", id, leagueBoard);
        if (leagueBoard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leagueBoard.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leagueBoardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeagueBoard result = leagueBoardService.update(leagueBoard);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, leagueBoard.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /league-boards/:id} : Partial updates given fields of an existing leagueBoard, field will ignore if it is null
     *
     * @param id the id of the leagueBoard to save.
     * @param leagueBoard the leagueBoard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leagueBoard,
     * or with status {@code 400 (Bad Request)} if the leagueBoard is not valid,
     * or with status {@code 404 (Not Found)} if the leagueBoard is not found,
     * or with status {@code 500 (Internal Server Error)} if the leagueBoard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/league-boards/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LeagueBoard> partialUpdateLeagueBoard(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody LeagueBoard leagueBoard
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeagueBoard partially : {}, {}", id, leagueBoard);
        if (leagueBoard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leagueBoard.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leagueBoardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeagueBoard> result = leagueBoardService.partialUpdate(leagueBoard);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, leagueBoard.getId().toString())
        );
    }

    /**
     * {@code GET  /league-boards} : get all the leagueBoards.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leagueBoards in body.
     */
    @GetMapping("/league-boards")
    public List<LeagueBoard> getAllLeagueBoards() {
        log.debug("REST request to get all LeagueBoards");
        return leagueBoardService.findAll();
    }

    /**
     * {@code GET  /league-boards/:id} : get the "id" leagueBoard.
     *
     * @param id the id of the leagueBoard to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leagueBoard, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/league-boards/{id}")
    public ResponseEntity<LeagueBoard> getLeagueBoard(@PathVariable UUID id) {
        log.debug("REST request to get LeagueBoard : {}", id);
        Optional<LeagueBoard> leagueBoard = leagueBoardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leagueBoard);
    }

    /**
     * {@code DELETE  /league-boards/:id} : delete the "id" leagueBoard.
     *
     * @param id the id of the leagueBoard to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/league-boards/{id}")
    public ResponseEntity<Void> deleteLeagueBoard(@PathVariable UUID id) {
        log.debug("REST request to delete LeagueBoard : {}", id);
        leagueBoardService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
