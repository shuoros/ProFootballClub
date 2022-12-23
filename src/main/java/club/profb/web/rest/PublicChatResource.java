package club.profb.web.rest;

import club.profb.domain.PublicChat;
import club.profb.repository.PublicChatRepository;
import club.profb.service.PublicChatService;
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
 * REST controller for managing {@link club.profb.domain.PublicChat}.
 */
@RestController
@RequestMapping("/api")
public class PublicChatResource {

    private final Logger log = LoggerFactory.getLogger(PublicChatResource.class);

    private static final String ENTITY_NAME = "publicChat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PublicChatService publicChatService;

    private final PublicChatRepository publicChatRepository;

    public PublicChatResource(PublicChatService publicChatService, PublicChatRepository publicChatRepository) {
        this.publicChatService = publicChatService;
        this.publicChatRepository = publicChatRepository;
    }

    /**
     * {@code POST  /public-chats} : Create a new publicChat.
     *
     * @param publicChat the publicChat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new publicChat, or with status {@code 400 (Bad Request)} if the publicChat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/public-chats")
    public ResponseEntity<PublicChat> createPublicChat(@RequestBody PublicChat publicChat) throws URISyntaxException {
        log.debug("REST request to save PublicChat : {}", publicChat);
        if (publicChat.getId() != null) {
            throw new BadRequestAlertException("A new publicChat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PublicChat result = publicChatService.save(publicChat);
        return ResponseEntity
            .created(new URI("/api/public-chats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /public-chats/:id} : Updates an existing publicChat.
     *
     * @param id the id of the publicChat to save.
     * @param publicChat the publicChat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicChat,
     * or with status {@code 400 (Bad Request)} if the publicChat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the publicChat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/public-chats/{id}")
    public ResponseEntity<PublicChat> updatePublicChat(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody PublicChat publicChat
    ) throws URISyntaxException {
        log.debug("REST request to update PublicChat : {}, {}", id, publicChat);
        if (publicChat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publicChat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicChatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PublicChat result = publicChatService.update(publicChat);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicChat.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /public-chats/:id} : Partial updates given fields of an existing publicChat, field will ignore if it is null
     *
     * @param id the id of the publicChat to save.
     * @param publicChat the publicChat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicChat,
     * or with status {@code 400 (Bad Request)} if the publicChat is not valid,
     * or with status {@code 404 (Not Found)} if the publicChat is not found,
     * or with status {@code 500 (Internal Server Error)} if the publicChat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/public-chats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PublicChat> partialUpdatePublicChat(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody PublicChat publicChat
    ) throws URISyntaxException {
        log.debug("REST request to partial update PublicChat partially : {}, {}", id, publicChat);
        if (publicChat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publicChat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicChatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PublicChat> result = publicChatService.partialUpdate(publicChat);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicChat.getId().toString())
        );
    }

    /**
     * {@code GET  /public-chats} : get all the publicChats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publicChats in body.
     */
    @GetMapping("/public-chats")
    public List<PublicChat> getAllPublicChats() {
        log.debug("REST request to get all PublicChats");
        return publicChatService.findAll();
    }

    /**
     * {@code GET  /public-chats/:id} : get the "id" publicChat.
     *
     * @param id the id of the publicChat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publicChat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/public-chats/{id}")
    public ResponseEntity<PublicChat> getPublicChat(@PathVariable UUID id) {
        log.debug("REST request to get PublicChat : {}", id);
        Optional<PublicChat> publicChat = publicChatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(publicChat);
    }

    /**
     * {@code DELETE  /public-chats/:id} : delete the "id" publicChat.
     *
     * @param id the id of the publicChat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/public-chats/{id}")
    public ResponseEntity<Void> deletePublicChat(@PathVariable UUID id) {
        log.debug("REST request to delete PublicChat : {}", id);
        publicChatService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
