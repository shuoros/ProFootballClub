package club.profb.web.rest;

import club.profb.domain.NewsPaper;
import club.profb.repository.NewsPaperRepository;
import club.profb.service.NewsPaperService;
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
 * REST controller for managing {@link club.profb.domain.NewsPaper}.
 */
@RestController
@RequestMapping("/api")
public class NewsPaperResource {

    private final Logger log = LoggerFactory.getLogger(NewsPaperResource.class);

    private static final String ENTITY_NAME = "newsPaper";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NewsPaperService newsPaperService;

    private final NewsPaperRepository newsPaperRepository;

    public NewsPaperResource(NewsPaperService newsPaperService, NewsPaperRepository newsPaperRepository) {
        this.newsPaperService = newsPaperService;
        this.newsPaperRepository = newsPaperRepository;
    }

    /**
     * {@code POST  /news-papers} : Create a new newsPaper.
     *
     * @param newsPaper the newsPaper to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new newsPaper, or with status {@code 400 (Bad Request)} if the newsPaper has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/news-papers")
    public ResponseEntity<NewsPaper> createNewsPaper(@Valid @RequestBody NewsPaper newsPaper) throws URISyntaxException {
        log.debug("REST request to save NewsPaper : {}", newsPaper);
        if (newsPaper.getId() != null) {
            throw new BadRequestAlertException("A new newsPaper cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NewsPaper result = newsPaperService.save(newsPaper);
        return ResponseEntity
            .created(new URI("/api/news-papers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /news-papers/:id} : Updates an existing newsPaper.
     *
     * @param id the id of the newsPaper to save.
     * @param newsPaper the newsPaper to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated newsPaper,
     * or with status {@code 400 (Bad Request)} if the newsPaper is not valid,
     * or with status {@code 500 (Internal Server Error)} if the newsPaper couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/news-papers/{id}")
    public ResponseEntity<NewsPaper> updateNewsPaper(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody NewsPaper newsPaper
    ) throws URISyntaxException {
        log.debug("REST request to update NewsPaper : {}, {}", id, newsPaper);
        if (newsPaper.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, newsPaper.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!newsPaperRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NewsPaper result = newsPaperService.update(newsPaper);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, newsPaper.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /news-papers/:id} : Partial updates given fields of an existing newsPaper, field will ignore if it is null
     *
     * @param id the id of the newsPaper to save.
     * @param newsPaper the newsPaper to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated newsPaper,
     * or with status {@code 400 (Bad Request)} if the newsPaper is not valid,
     * or with status {@code 404 (Not Found)} if the newsPaper is not found,
     * or with status {@code 500 (Internal Server Error)} if the newsPaper couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/news-papers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NewsPaper> partialUpdateNewsPaper(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody NewsPaper newsPaper
    ) throws URISyntaxException {
        log.debug("REST request to partial update NewsPaper partially : {}, {}", id, newsPaper);
        if (newsPaper.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, newsPaper.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!newsPaperRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NewsPaper> result = newsPaperService.partialUpdate(newsPaper);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, newsPaper.getId().toString())
        );
    }

    /**
     * {@code GET  /news-papers} : get all the newsPapers.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of newsPapers in body.
     */
    @GetMapping("/news-papers")
    public List<NewsPaper> getAllNewsPapers(@RequestParam(required = false) String filter) {
        if ("league-is-null".equals(filter)) {
            log.debug("REST request to get all NewsPapers where league is null");
            return newsPaperService.findAllWhereLeagueIsNull();
        }

        if ("cup-is-null".equals(filter)) {
            log.debug("REST request to get all NewsPapers where cup is null");
            return newsPaperService.findAllWhereCupIsNull();
        }
        log.debug("REST request to get all NewsPapers");
        return newsPaperService.findAll();
    }

    /**
     * {@code GET  /news-papers/:id} : get the "id" newsPaper.
     *
     * @param id the id of the newsPaper to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the newsPaper, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/news-papers/{id}")
    public ResponseEntity<NewsPaper> getNewsPaper(@PathVariable UUID id) {
        log.debug("REST request to get NewsPaper : {}", id);
        Optional<NewsPaper> newsPaper = newsPaperService.findOne(id);
        return ResponseUtil.wrapOrNotFound(newsPaper);
    }

    /**
     * {@code DELETE  /news-papers/:id} : delete the "id" newsPaper.
     *
     * @param id the id of the newsPaper to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/news-papers/{id}")
    public ResponseEntity<Void> deleteNewsPaper(@PathVariable UUID id) {
        log.debug("REST request to delete NewsPaper : {}", id);
        newsPaperService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
