package club.profb.service;

import club.profb.domain.Message;
import club.profb.repository.MessageRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Message}.
 */
@Service
@Transactional
public class MessageService {

    private final Logger log = LoggerFactory.getLogger(MessageService.class);

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Save a message.
     *
     * @param message the entity to save.
     * @return the persisted entity.
     */
    public Message save(Message message) {
        log.debug("Request to save Message : {}", message);
        return messageRepository.save(message);
    }

    /**
     * Update a message.
     *
     * @param message the entity to save.
     * @return the persisted entity.
     */
    public Message update(Message message) {
        log.debug("Request to save Message : {}", message);
        return messageRepository.save(message);
    }

    /**
     * Partially update a message.
     *
     * @param message the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Message> partialUpdate(Message message) {
        log.debug("Request to partially update Message : {}", message);

        return messageRepository
            .findById(message.getId())
            .map(existingMessage -> {
                if (message.getMessage() != null) {
                    existingMessage.setMessage(message.getMessage());
                }

                return existingMessage;
            })
            .map(messageRepository::save);
    }

    /**
     * Get all the messages.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Message> findAll() {
        log.debug("Request to get all Messages");
        return messageRepository.findAll();
    }

    /**
     * Get one message by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Message> findOne(UUID id) {
        log.debug("Request to get Message : {}", id);
        return messageRepository.findById(id);
    }

    /**
     * Delete the message by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Message : {}", id);
        messageRepository.deleteById(id);
    }
}
