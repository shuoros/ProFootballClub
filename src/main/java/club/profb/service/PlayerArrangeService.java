package club.profb.service;

import club.profb.domain.PlayerArrange;
import club.profb.repository.PlayerArrangeRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PlayerArrange}.
 */
@Service
@Transactional
public class PlayerArrangeService {

    private final Logger log = LoggerFactory.getLogger(PlayerArrangeService.class);

    private final PlayerArrangeRepository playerArrangeRepository;

    public PlayerArrangeService(PlayerArrangeRepository playerArrangeRepository) {
        this.playerArrangeRepository = playerArrangeRepository;
    }

    /**
     * Save a playerArrange.
     *
     * @param playerArrange the entity to save.
     * @return the persisted entity.
     */
    public PlayerArrange save(PlayerArrange playerArrange) {
        log.debug("Request to save PlayerArrange : {}", playerArrange);
        return playerArrangeRepository.save(playerArrange);
    }

    /**
     * Update a playerArrange.
     *
     * @param playerArrange the entity to save.
     * @return the persisted entity.
     */
    public PlayerArrange update(PlayerArrange playerArrange) {
        log.debug("Request to save PlayerArrange : {}", playerArrange);
        return playerArrangeRepository.save(playerArrange);
    }

    /**
     * Partially update a playerArrange.
     *
     * @param playerArrange the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PlayerArrange> partialUpdate(PlayerArrange playerArrange) {
        log.debug("Request to partially update PlayerArrange : {}", playerArrange);

        return playerArrangeRepository
            .findById(playerArrange.getId())
            .map(existingPlayerArrange -> {
                if (playerArrange.getPost() != null) {
                    existingPlayerArrange.setPost(playerArrange.getPost());
                }

                return existingPlayerArrange;
            })
            .map(playerArrangeRepository::save);
    }

    /**
     * Get all the playerArranges.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PlayerArrange> findAll() {
        log.debug("Request to get all PlayerArranges");
        return playerArrangeRepository.findAll();
    }

    /**
     * Get one playerArrange by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PlayerArrange> findOne(UUID id) {
        log.debug("Request to get PlayerArrange : {}", id);
        return playerArrangeRepository.findById(id);
    }

    /**
     * Delete the playerArrange by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete PlayerArrange : {}", id);
        playerArrangeRepository.deleteById(id);
    }
}
