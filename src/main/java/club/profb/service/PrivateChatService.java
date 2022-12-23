package club.profb.service;

import club.profb.domain.PrivateChat;
import club.profb.repository.PrivateChatRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PrivateChat}.
 */
@Service
@Transactional
public class PrivateChatService {

    private final Logger log = LoggerFactory.getLogger(PrivateChatService.class);

    private final PrivateChatRepository privateChatRepository;

    public PrivateChatService(PrivateChatRepository privateChatRepository) {
        this.privateChatRepository = privateChatRepository;
    }

    /**
     * Save a privateChat.
     *
     * @param privateChat the entity to save.
     * @return the persisted entity.
     */
    public PrivateChat save(PrivateChat privateChat) {
        log.debug("Request to save PrivateChat : {}", privateChat);
        return privateChatRepository.save(privateChat);
    }

    /**
     * Update a privateChat.
     *
     * @param privateChat the entity to save.
     * @return the persisted entity.
     */
    public PrivateChat update(PrivateChat privateChat) {
        log.debug("Request to save PrivateChat : {}", privateChat);
        return privateChatRepository.save(privateChat);
    }

    /**
     * Partially update a privateChat.
     *
     * @param privateChat the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PrivateChat> partialUpdate(PrivateChat privateChat) {
        log.debug("Request to partially update PrivateChat : {}", privateChat);

        return privateChatRepository
            .findById(privateChat.getId())
            .map(existingPrivateChat -> {
                return existingPrivateChat;
            })
            .map(privateChatRepository::save);
    }

    /**
     * Get all the privateChats.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PrivateChat> findAll() {
        log.debug("Request to get all PrivateChats");
        return privateChatRepository.findAll();
    }

    /**
     * Get one privateChat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PrivateChat> findOne(UUID id) {
        log.debug("Request to get PrivateChat : {}", id);
        return privateChatRepository.findById(id);
    }

    /**
     * Delete the privateChat by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete PrivateChat : {}", id);
        privateChatRepository.deleteById(id);
    }
}
