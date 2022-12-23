package club.profb.service;

import club.profb.domain.PublicChat;
import club.profb.repository.PublicChatRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PublicChat}.
 */
@Service
@Transactional
public class PublicChatService {

    private final Logger log = LoggerFactory.getLogger(PublicChatService.class);

    private final PublicChatRepository publicChatRepository;

    public PublicChatService(PublicChatRepository publicChatRepository) {
        this.publicChatRepository = publicChatRepository;
    }

    /**
     * Save a publicChat.
     *
     * @param publicChat the entity to save.
     * @return the persisted entity.
     */
    public PublicChat save(PublicChat publicChat) {
        log.debug("Request to save PublicChat : {}", publicChat);
        return publicChatRepository.save(publicChat);
    }

    /**
     * Update a publicChat.
     *
     * @param publicChat the entity to save.
     * @return the persisted entity.
     */
    public PublicChat update(PublicChat publicChat) {
        log.debug("Request to save PublicChat : {}", publicChat);
        return publicChatRepository.save(publicChat);
    }

    /**
     * Partially update a publicChat.
     *
     * @param publicChat the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PublicChat> partialUpdate(PublicChat publicChat) {
        log.debug("Request to partially update PublicChat : {}", publicChat);

        return publicChatRepository
            .findById(publicChat.getId())
            .map(existingPublicChat -> {
                return existingPublicChat;
            })
            .map(publicChatRepository::save);
    }

    /**
     * Get all the publicChats.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PublicChat> findAll() {
        log.debug("Request to get all PublicChats");
        return publicChatRepository.findAll();
    }

    /**
     * Get one publicChat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PublicChat> findOne(UUID id) {
        log.debug("Request to get PublicChat : {}", id);
        return publicChatRepository.findById(id);
    }

    /**
     * Delete the publicChat by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete PublicChat : {}", id);
        publicChatRepository.deleteById(id);
    }
}
