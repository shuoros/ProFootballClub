package club.profb.web.rest;

import club.profb.domain.Transfer;
import club.profb.repository.TransferRepository;
import club.profb.service.TransferService;
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
 * REST controller for managing {@link club.profb.domain.Transfer}.
 */
@RestController
@RequestMapping("/api")
public class TransferResource {

    private final Logger log = LoggerFactory.getLogger(TransferResource.class);

    private static final String ENTITY_NAME = "transfer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransferService transferService;

    private final TransferRepository transferRepository;

    public TransferResource(TransferService transferService, TransferRepository transferRepository) {
        this.transferService = transferService;
        this.transferRepository = transferRepository;
    }

    /**
     * {@code POST  /transfers} : Create a new transfer.
     *
     * @param transfer the transfer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transfer, or with status {@code 400 (Bad Request)} if the transfer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transfers")
    public ResponseEntity<Transfer> createTransfer(@Valid @RequestBody Transfer transfer) throws URISyntaxException {
        log.debug("REST request to save Transfer : {}", transfer);
        if (transfer.getId() != null) {
            throw new BadRequestAlertException("A new transfer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Transfer result = transferService.save(transfer);
        return ResponseEntity
            .created(new URI("/api/transfers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transfers/:id} : Updates an existing transfer.
     *
     * @param id the id of the transfer to save.
     * @param transfer the transfer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transfer,
     * or with status {@code 400 (Bad Request)} if the transfer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transfer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transfers/{id}")
    public ResponseEntity<Transfer> updateTransfer(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody Transfer transfer
    ) throws URISyntaxException {
        log.debug("REST request to update Transfer : {}, {}", id, transfer);
        if (transfer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transfer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transferRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Transfer result = transferService.update(transfer);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transfer.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /transfers/:id} : Partial updates given fields of an existing transfer, field will ignore if it is null
     *
     * @param id the id of the transfer to save.
     * @param transfer the transfer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transfer,
     * or with status {@code 400 (Bad Request)} if the transfer is not valid,
     * or with status {@code 404 (Not Found)} if the transfer is not found,
     * or with status {@code 500 (Internal Server Error)} if the transfer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/transfers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Transfer> partialUpdateTransfer(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody Transfer transfer
    ) throws URISyntaxException {
        log.debug("REST request to partial update Transfer partially : {}, {}", id, transfer);
        if (transfer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transfer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transferRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Transfer> result = transferService.partialUpdate(transfer);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transfer.getId().toString())
        );
    }

    /**
     * {@code GET  /transfers} : get all the transfers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transfers in body.
     */
    @GetMapping("/transfers")
    public List<Transfer> getAllTransfers() {
        log.debug("REST request to get all Transfers");
        return transferService.findAll();
    }

    /**
     * {@code GET  /transfers/:id} : get the "id" transfer.
     *
     * @param id the id of the transfer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transfer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transfers/{id}")
    public ResponseEntity<Transfer> getTransfer(@PathVariable UUID id) {
        log.debug("REST request to get Transfer : {}", id);
        Optional<Transfer> transfer = transferService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transfer);
    }

    /**
     * {@code DELETE  /transfers/:id} : delete the "id" transfer.
     *
     * @param id the id of the transfer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transfers/{id}")
    public ResponseEntity<Void> deleteTransfer(@PathVariable UUID id) {
        log.debug("REST request to delete Transfer : {}", id);
        transferService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
