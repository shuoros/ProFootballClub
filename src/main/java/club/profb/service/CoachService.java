package club.profb.service;

import club.profb.domain.Coach;
import club.profb.repository.CoachRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Coach}.
 */
@Service
@Transactional
public class CoachService {

    private final Logger log = LoggerFactory.getLogger(CoachService.class);

    private final CoachRepository coachRepository;

    public CoachService(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    /**
     * Save a coach.
     *
     * @param coach the entity to save.
     * @return the persisted entity.
     */
    public Coach save(Coach coach) {
        log.debug("Request to save Coach : {}", coach);
        return coachRepository.save(coach);
    }

    /**
     * Update a coach.
     *
     * @param coach the entity to save.
     * @return the persisted entity.
     */
    public Coach update(Coach coach) {
        log.debug("Request to save Coach : {}", coach);
        return coachRepository.save(coach);
    }

    /**
     * Partially update a coach.
     *
     * @param coach the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Coach> partialUpdate(Coach coach) {
        log.debug("Request to partially update Coach : {}", coach);

        return coachRepository
            .findById(coach.getId())
            .map(existingCoach -> {
                if (coach.getName() != null) {
                    existingCoach.setName(coach.getName());
                }
                if (coach.getBanned() != null) {
                    existingCoach.setBanned(coach.getBanned());
                }
                if (coach.getAbandoned() != null) {
                    existingCoach.setAbandoned(coach.getAbandoned());
                }
                if (coach.getSubscribed() != null) {
                    existingCoach.setSubscribed(coach.getSubscribed());
                }
                if (coach.getPlan() != null) {
                    existingCoach.setPlan(coach.getPlan());
                }

                return existingCoach;
            })
            .map(coachRepository::save);
    }

    /**
     * Get all the coaches.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Coach> findAll() {
        log.debug("Request to get all Coaches");
        return coachRepository.findAll();
    }

    /**
     * Get one coach by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Coach> findOne(UUID id) {
        log.debug("Request to get Coach : {}", id);
        return coachRepository.findById(id);
    }

    /**
     * Delete the coach by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Coach : {}", id);
        coachRepository.deleteById(id);
    }
}
