package club.profb.service;

import club.profb.domain.Composition;
import club.profb.repository.CompositionRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Composition}.
 */
@Service
@Transactional
public class CompositionService {

    private final Logger log = LoggerFactory.getLogger(CompositionService.class);

    private final CompositionRepository compositionRepository;

    public CompositionService(CompositionRepository compositionRepository) {
        this.compositionRepository = compositionRepository;
    }

    /**
     * Save a composition.
     *
     * @param composition the entity to save.
     * @return the persisted entity.
     */
    public Composition save(Composition composition) {
        log.debug("Request to save Composition : {}", composition);
        return compositionRepository.save(composition);
    }

    /**
     * Update a composition.
     *
     * @param composition the entity to save.
     * @return the persisted entity.
     */
    public Composition update(Composition composition) {
        log.debug("Request to save Composition : {}", composition);
        return compositionRepository.save(composition);
    }

    /**
     * Partially update a composition.
     *
     * @param composition the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Composition> partialUpdate(Composition composition) {
        log.debug("Request to partially update Composition : {}", composition);

        return compositionRepository
            .findById(composition.getId())
            .map(existingComposition -> {
                if (composition.getd3fault() != null) {
                    existingComposition.setd3fault(composition.getd3fault());
                }
                if (composition.getArrange() != null) {
                    existingComposition.setArrange(composition.getArrange());
                }
                if (composition.getStrategy() != null) {
                    existingComposition.setStrategy(composition.getStrategy());
                }
                if (composition.getDefence() != null) {
                    existingComposition.setDefence(composition.getDefence());
                }
                if (composition.getShortPass() != null) {
                    existingComposition.setShortPass(composition.getShortPass());
                }
                if (composition.getViolence() != null) {
                    existingComposition.setViolence(composition.getViolence());
                }

                return existingComposition;
            })
            .map(compositionRepository::save);
    }

    /**
     * Get all the compositions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Composition> findAll() {
        log.debug("Request to get all Compositions");
        return compositionRepository.findAll();
    }

    /**
     * Get one composition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Composition> findOne(UUID id) {
        log.debug("Request to get Composition : {}", id);
        return compositionRepository.findById(id);
    }

    /**
     * Delete the composition by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Composition : {}", id);
        compositionRepository.deleteById(id);
    }
}
