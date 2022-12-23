package club.profb.service;

import club.profb.domain.League;
import club.profb.repository.LeagueRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link League}.
 */
@Service
@Transactional
public class LeagueService {

    private final Logger log = LoggerFactory.getLogger(LeagueService.class);

    private final LeagueRepository leagueRepository;

    public LeagueService(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    /**
     * Save a league.
     *
     * @param league the entity to save.
     * @return the persisted entity.
     */
    public League save(League league) {
        log.debug("Request to save League : {}", league);
        return leagueRepository.save(league);
    }

    /**
     * Update a league.
     *
     * @param league the entity to save.
     * @return the persisted entity.
     */
    public League update(League league) {
        log.debug("Request to save League : {}", league);
        return leagueRepository.save(league);
    }

    /**
     * Partially update a league.
     *
     * @param league the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<League> partialUpdate(League league) {
        log.debug("Request to partially update League : {}", league);

        return leagueRepository
            .findById(league.getId())
            .map(existingLeague -> {
                if (league.getClazz() != null) {
                    existingLeague.setClazz(league.getClazz());
                }
                if (league.getEvents() != null) {
                    existingLeague.setEvents(league.getEvents());
                }
                if (league.getFinished() != null) {
                    existingLeague.setFinished(league.getFinished());
                }
                if (league.getStart() != null) {
                    existingLeague.setStart(league.getStart());
                }

                return existingLeague;
            })
            .map(leagueRepository::save);
    }

    /**
     * Get all the leagues.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<League> findAll() {
        log.debug("Request to get all Leagues");
        return leagueRepository.findAll();
    }

    /**
     * Get one league by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<League> findOne(UUID id) {
        log.debug("Request to get League : {}", id);
        return leagueRepository.findById(id);
    }

    /**
     * Delete the league by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete League : {}", id);
        leagueRepository.deleteById(id);
    }
}
