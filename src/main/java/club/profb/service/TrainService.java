package club.profb.service;

import club.profb.domain.Train;
import club.profb.repository.TrainRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Train}.
 */
@Service
@Transactional
public class TrainService {

    private final Logger log = LoggerFactory.getLogger(TrainService.class);

    private final TrainRepository trainRepository;

    public TrainService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    /**
     * Save a train.
     *
     * @param train the entity to save.
     * @return the persisted entity.
     */
    public Train save(Train train) {
        log.debug("Request to save Train : {}", train);
        return trainRepository.save(train);
    }

    /**
     * Update a train.
     *
     * @param train the entity to save.
     * @return the persisted entity.
     */
    public Train update(Train train) {
        log.debug("Request to save Train : {}", train);
        return trainRepository.save(train);
    }

    /**
     * Partially update a train.
     *
     * @param train the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Train> partialUpdate(Train train) {
        log.debug("Request to partially update Train : {}", train);

        return trainRepository
            .findById(train.getId())
            .map(existingTrain -> {
                if (train.getTraining() != null) {
                    existingTrain.setTraining(train.getTraining());
                }
                if (train.getFinishes() != null) {
                    existingTrain.setFinishes(train.getFinishes());
                }

                return existingTrain;
            })
            .map(trainRepository::save);
    }

    /**
     * Get all the trains.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Train> findAll() {
        log.debug("Request to get all Trains");
        return trainRepository.findAll();
    }

    /**
     * Get one train by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Train> findOne(UUID id) {
        log.debug("Request to get Train : {}", id);
        return trainRepository.findById(id);
    }

    /**
     * Delete the train by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Train : {}", id);
        trainRepository.deleteById(id);
    }
}
