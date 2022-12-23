package club.profb.web.rest;

import club.profb.domain.CupBoard;
import club.profb.repository.CupBoardRepository;
import club.profb.service.CupBoardService;
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
 * REST controller for managing {@link club.profb.domain.CupBoard}.
 */
@RestController
@RequestMapping("/api")
public class CupBoardResource {

    private final Logger log = LoggerFactory.getLogger(CupBoardResource.class);

    private static final String ENTITY_NAME = "cupBoard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CupBoardService cupBoardService;

    private final CupBoardRepository cupBoardRepository;

    public CupBoardResource(CupBoardService cupBoardService, CupBoardRepository cupBoardRepository) {
        this.cupBoardService = cupBoardService;
        this.cupBoardRepository = cupBoardRepository;
    }

    /**
     * {@code POST  /cup-boards} : Create a new cupBoard.
     *
     * @param cupBoard the cupBoard to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cupBoard, or with status {@code 400 (Bad Request)} if the cupBoard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cup-boards")
    public ResponseEntity<CupBoard> createCupBoard(@Valid @RequestBody CupBoard cupBoard) throws URISyntaxException {
        log.debug("REST request to save CupBoard : {}", cupBoard);
        if (cupBoard.getId() != null) {
            throw new BadRequestAlertException("A new cupBoard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CupBoard result = cupBoardService.save(cupBoard);
        return ResponseEntity
            .created(new URI("/api/cup-boards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cup-boards/:id} : Updates an existing cupBoard.
     *
     * @param id the id of the cupBoard to save.
     * @param cupBoard the cupBoard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cupBoard,
     * or with status {@code 400 (Bad Request)} if the cupBoard is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cupBoard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cup-boards/{id}")
    public ResponseEntity<CupBoard> updateCupBoard(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody CupBoard cupBoard
    ) throws URISyntaxException {
        log.debug("REST request to update CupBoard : {}, {}", id, cupBoard);
        if (cupBoard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cupBoard.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cupBoardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CupBoard result = cupBoardService.update(cupBoard);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cupBoard.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cup-boards/:id} : Partial updates given fields of an existing cupBoard, field will ignore if it is null
     *
     * @param id the id of the cupBoard to save.
     * @param cupBoard the cupBoard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cupBoard,
     * or with status {@code 400 (Bad Request)} if the cupBoard is not valid,
     * or with status {@code 404 (Not Found)} if the cupBoard is not found,
     * or with status {@code 500 (Internal Server Error)} if the cupBoard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cup-boards/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CupBoard> partialUpdateCupBoard(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody CupBoard cupBoard
    ) throws URISyntaxException {
        log.debug("REST request to partial update CupBoard partially : {}, {}", id, cupBoard);
        if (cupBoard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cupBoard.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cupBoardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CupBoard> result = cupBoardService.partialUpdate(cupBoard);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cupBoard.getId().toString())
        );
    }

    /**
     * {@code GET  /cup-boards} : get all the cupBoards.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cupBoards in body.
     */
    @GetMapping("/cup-boards")
    public List<CupBoard> getAllCupBoards() {
        log.debug("REST request to get all CupBoards");
        return cupBoardService.findAll();
    }

    /**
     * {@code GET  /cup-boards/:id} : get the "id" cupBoard.
     *
     * @param id the id of the cupBoard to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cupBoard, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cup-boards/{id}")
    public ResponseEntity<CupBoard> getCupBoard(@PathVariable UUID id) {
        log.debug("REST request to get CupBoard : {}", id);
        Optional<CupBoard> cupBoard = cupBoardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cupBoard);
    }

    /**
     * {@code DELETE  /cup-boards/:id} : delete the "id" cupBoard.
     *
     * @param id the id of the cupBoard to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cup-boards/{id}")
    public ResponseEntity<Void> deleteCupBoard(@PathVariable UUID id) {
        log.debug("REST request to delete CupBoard : {}", id);
        cupBoardService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
