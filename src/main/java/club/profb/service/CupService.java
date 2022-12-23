package club.profb.service;

import club.profb.domain.Cup;
import club.profb.repository.CupRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Cup}.
 */
@Service
@Transactional
public class CupService {

    private final Logger log = LoggerFactory.getLogger(CupService.class);

    private final CupRepository cupRepository;

    public CupService(CupRepository cupRepository) {
        this.cupRepository = cupRepository;
    }

    /**
     * Save a cup.
     *
     * @param cup the entity to save.
     * @return the persisted entity.
     */
    public Cup save(Cup cup) {
        log.debug("Request to save Cup : {}", cup);
        return cupRepository.save(cup);
    }

    /**
     * Update a cup.
     *
     * @param cup the entity to save.
     * @return the persisted entity.
     */
    public Cup update(Cup cup) {
        log.debug("Request to save Cup : {}", cup);
        return cupRepository.save(cup);
    }

    /**
     * Partially update a cup.
     *
     * @param cup the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Cup> partialUpdate(Cup cup) {
        log.debug("Request to partially update Cup : {}", cup);

        return cupRepository
            .findById(cup.getId())
            .map(existingCup -> {
                if (cup.getType() != null) {
                    existingCup.setType(cup.getType());
                }
                if (cup.getEvents() != null) {
                    existingCup.setEvents(cup.getEvents());
                }
                if (cup.getFinished() != null) {
                    existingCup.setFinished(cup.getFinished());
                }
                if (cup.getEntrance() != null) {
                    existingCup.setEntrance(cup.getEntrance());
                }
                if (cup.getStart() != null) {
                    existingCup.setStart(cup.getStart());
                }

                return existingCup;
            })
            .map(cupRepository::save);
    }

    /**
     * Get all the cups.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Cup> findAll() {
        log.debug("Request to get all Cups");
        return cupRepository.findAll();
    }

    /**
     * Get one cup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Cup> findOne(UUID id) {
        log.debug("Request to get Cup : {}", id);
        return cupRepository.findById(id);
    }

    /**
     * Delete the cup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Cup : {}", id);
        cupRepository.deleteById(id);
    }
}
