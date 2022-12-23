package club.profb.web.rest;

import club.profb.domain.PrivateChat;
import club.profb.repository.PrivateChatRepository;
import club.profb.service.PrivateChatService;
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
 * REST controller for managing {@link club.profb.domain.PrivateChat}.
 */
@RestController
@RequestMapping("/api")
public class PrivateChatResource {

    private final Logger log = LoggerFactory.getLogger(PrivateChatResource.class);

    private static final String ENTITY_NAME = "privateChat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrivateChatService privateChatService;

    private final PrivateChatRepository privateChatRepository;

    public PrivateChatResource(PrivateChatService privateChatService, PrivateChatRepository privateChatRepository) {
        this.privateChatService = privateChatService;
        this.privateChatRepository = privateChatRepository;
    }

    /**
     * {@code POST  /private-chats} : Create a new privateChat.
     *
     * @param privateChat the privateChat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new privateChat, or with status {@code 400 (Bad Request)} if the privateChat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/private-chats")
    public ResponseEntity<PrivateChat> createPrivateChat(@RequestBody PrivateChat privateChat) throws URISyntaxException {
        log.debug("REST request to save PrivateChat : {}", privateChat);
        if (privateChat.getId() != null) {
            throw new BadRequestAlertException("A new privateChat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrivateChat result = privateChatService.save(privateChat);
        return ResponseEntity
            .created(new URI("/api/private-chats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /private-chats/:id} : Updates an existing privateChat.
     *
     * @param id the id of the privateChat to save.
     * @param privateChat the privateChat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated privateChat,
     * or with status {@code 400 (Bad Request)} if the privateChat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the privateChat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/private-chats/{id}")
    public ResponseEntity<PrivateChat> updatePrivateChat(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody PrivateChat privateChat
    ) throws URISyntaxException {
        log.debug("REST request to update PrivateChat : {}, {}", id, privateChat);
        if (privateChat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, privateChat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!privateChatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PrivateChat result = privateChatService.update(privateChat);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, privateChat.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /private-chats/:id} : Partial updates given fields of an existing privateChat, field will ignore if it is null
     *
     * @param id the id of the privateChat to save.
     * @param privateChat the privateChat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated privateChat,
     * or with status {@code 400 (Bad Request)} if the privateChat is not valid,
     * or with status {@code 404 (Not Found)} if the privateChat is not found,
     * or with status {@code 500 (Internal Server Error)} if the privateChat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/private-chats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PrivateChat> partialUpdatePrivateChat(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody PrivateChat privateChat
    ) throws URISyntaxException {
        log.debug("REST request to partial update PrivateChat partially : {}, {}", id, privateChat);
        if (privateChat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, privateChat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!privateChatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PrivateChat> result = privateChatService.partialUpdate(privateChat);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, privateChat.getId().toString())
        );
    }

    /**
     * {@code GET  /private-chats} : get all the privateChats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of privateChats in body.
     */
    @GetMapping("/private-chats")
    public List<PrivateChat> getAllPrivateChats() {
        log.debug("REST request to get all PrivateChats");
        return privateChatService.findAll();
    }

    /**
     * {@code GET  /private-chats/:id} : get the "id" privateChat.
     *
     * @param id the id of the privateChat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the privateChat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/private-chats/{id}")
    public ResponseEntity<PrivateChat> getPrivateChat(@PathVariable UUID id) {
        log.debug("REST request to get PrivateChat : {}", id);
        Optional<PrivateChat> privateChat = privateChatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(privateChat);
    }

    /**
     * {@code DELETE  /private-chats/:id} : delete the "id" privateChat.
     *
     * @param id the id of the privateChat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/private-chats/{id}")
    public ResponseEntity<Void> deletePrivateChat(@PathVariable UUID id) {
        log.debug("REST request to delete PrivateChat : {}", id);
        privateChatService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
