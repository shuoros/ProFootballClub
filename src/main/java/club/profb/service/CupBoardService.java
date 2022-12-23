package club.profb.service;

import club.profb.domain.CupBoard;
import club.profb.repository.CupBoardRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CupBoard}.
 */
@Service
@Transactional
public class CupBoardService {

    private final Logger log = LoggerFactory.getLogger(CupBoardService.class);

    private final CupBoardRepository cupBoardRepository;

    public CupBoardService(CupBoardRepository cupBoardRepository) {
        this.cupBoardRepository = cupBoardRepository;
    }

    /**
     * Save a cupBoard.
     *
     * @param cupBoard the entity to save.
     * @return the persisted entity.
     */
    public CupBoard save(CupBoard cupBoard) {
        log.debug("Request to save CupBoard : {}", cupBoard);
        return cupBoardRepository.save(cupBoard);
    }

    /**
     * Update a cupBoard.
     *
     * @param cupBoard the entity to save.
     * @return the persisted entity.
     */
    public CupBoard update(CupBoard cupBoard) {
        log.debug("Request to save CupBoard : {}", cupBoard);
        return cupBoardRepository.save(cupBoard);
    }

    /**
     * Partially update a cupBoard.
     *
     * @param cupBoard the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CupBoard> partialUpdate(CupBoard cupBoard) {
        log.debug("Request to partially update CupBoard : {}", cupBoard);

        return cupBoardRepository
            .findById(cupBoard.getId())
            .map(existingCupBoard -> {
                if (cupBoard.getLevel() != null) {
                    existingCupBoard.setLevel(cupBoard.getLevel());
                }

                return existingCupBoard;
            })
            .map(cupBoardRepository::save);
    }

    /**
     * Get all the cupBoards.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CupBoard> findAll() {
        log.debug("Request to get all CupBoards");
        return cupBoardRepository.findAll();
    }

    /**
     * Get one cupBoard by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CupBoard> findOne(UUID id) {
        log.debug("Request to get CupBoard : {}", id);
        return cupBoardRepository.findById(id);
    }

    /**
     * Delete the cupBoard by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete CupBoard : {}", id);
        cupBoardRepository.deleteById(id);
    }
}
