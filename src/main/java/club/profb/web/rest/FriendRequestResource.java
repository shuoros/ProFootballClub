package club.profb.web.rest;

import club.profb.domain.FriendRequest;
import club.profb.repository.FriendRequestRepository;
import club.profb.service.FriendRequestService;
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
 * REST controller for managing {@link club.profb.domain.FriendRequest}.
 */
@RestController
@RequestMapping("/api")
public class FriendRequestResource {

    private final Logger log = LoggerFactory.getLogger(FriendRequestResource.class);

    private static final String ENTITY_NAME = "friendRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FriendRequestService friendRequestService;

    private final FriendRequestRepository friendRequestRepository;

    public FriendRequestResource(FriendRequestService friendRequestService, FriendRequestRepository friendRequestRepository) {
        this.friendRequestService = friendRequestService;
        this.friendRequestRepository = friendRequestRepository;
    }

    /**
     * {@code POST  /friend-requests} : Create a new friendRequest.
     *
     * @param friendRequest the friendRequest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new friendRequest, or with status {@code 400 (Bad Request)} if the friendRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/friend-requests")
    public ResponseEntity<FriendRequest> createFriendRequest(@RequestBody FriendRequest friendRequest) throws URISyntaxException {
        log.debug("REST request to save FriendRequest : {}", friendRequest);
        if (friendRequest.getId() != null) {
            throw new BadRequestAlertException("A new friendRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FriendRequest result = friendRequestService.save(friendRequest);
        return ResponseEntity
            .created(new URI("/api/friend-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /friend-requests/:id} : Updates an existing friendRequest.
     *
     * @param id the id of the friendRequest to save.
     * @param friendRequest the friendRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated friendRequest,
     * or with status {@code 400 (Bad Request)} if the friendRequest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the friendRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/friend-requests/{id}")
    public ResponseEntity<FriendRequest> updateFriendRequest(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody FriendRequest friendRequest
    ) throws URISyntaxException {
        log.debug("REST request to update FriendRequest : {}, {}", id, friendRequest);
        if (friendRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, friendRequest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!friendRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FriendRequest result = friendRequestService.update(friendRequest);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, friendRequest.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /friend-requests/:id} : Partial updates given fields of an existing friendRequest, field will ignore if it is null
     *
     * @param id the id of the friendRequest to save.
     * @param friendRequest the friendRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated friendRequest,
     * or with status {@code 400 (Bad Request)} if the friendRequest is not valid,
     * or with status {@code 404 (Not Found)} if the friendRequest is not found,
     * or with status {@code 500 (Internal Server Error)} if the friendRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/friend-requests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FriendRequest> partialUpdateFriendRequest(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody FriendRequest friendRequest
    ) throws URISyntaxException {
        log.debug("REST request to partial update FriendRequest partially : {}, {}", id, friendRequest);
        if (friendRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, friendRequest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!friendRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FriendRequest> result = friendRequestService.partialUpdate(friendRequest);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, friendRequest.getId().toString())
        );
    }

    /**
     * {@code GET  /friend-requests} : get all the friendRequests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of friendRequests in body.
     */
    @GetMapping("/friend-requests")
    public List<FriendRequest> getAllFriendRequests() {
        log.debug("REST request to get all FriendRequests");
        return friendRequestService.findAll();
    }

    /**
     * {@code GET  /friend-requests/:id} : get the "id" friendRequest.
     *
     * @param id the id of the friendRequest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the friendRequest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/friend-requests/{id}")
    public ResponseEntity<FriendRequest> getFriendRequest(@PathVariable UUID id) {
        log.debug("REST request to get FriendRequest : {}", id);
        Optional<FriendRequest> friendRequest = friendRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(friendRequest);
    }

    /**
     * {@code DELETE  /friend-requests/:id} : delete the "id" friendRequest.
     *
     * @param id the id of the friendRequest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/friend-requests/{id}")
    public ResponseEntity<Void> deleteFriendRequest(@PathVariable UUID id) {
        log.debug("REST request to delete FriendRequest : {}", id);
        friendRequestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
