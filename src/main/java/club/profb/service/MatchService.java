package club.profb.service;

import club.profb.domain.Match;
import club.profb.repository.MatchRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Match}.
 */
@Service
@Transactional
public class MatchService {

    private final Logger log = LoggerFactory.getLogger(MatchService.class);

    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    /**
     * Save a match.
     *
     * @param match the entity to save.
     * @return the persisted entity.
     */
    public Match save(Match match) {
        log.debug("Request to save Match : {}", match);
        return matchRepository.save(match);
    }

    /**
     * Update a match.
     *
     * @param match the entity to save.
     * @return the persisted entity.
     */
    public Match update(Match match) {
        log.debug("Request to save Match : {}", match);
        return matchRepository.save(match);
    }

    /**
     * Partially update a match.
     *
     * @param match the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Match> partialUpdate(Match match) {
        log.debug("Request to partially update Match : {}", match);

        return matchRepository
            .findById(match.getId())
            .map(existingMatch -> {
                if (match.getDate() != null) {
                    existingMatch.setDate(match.getDate());
                }
                if (match.getWeather() != null) {
                    existingMatch.setWeather(match.getWeather());
                }
                if (match.getHostGoals() != null) {
                    existingMatch.setHostGoals(match.getHostGoals());
                }
                if (match.getGuestGoals() != null) {
                    existingMatch.setGuestGoals(match.getGuestGoals());
                }
                if (match.getEvents() != null) {
                    existingMatch.setEvents(match.getEvents());
                }
                if (match.getType() != null) {
                    existingMatch.setType(match.getType());
                }

                return existingMatch;
            })
            .map(matchRepository::save);
    }

    /**
     * Get all the matches.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Match> findAll() {
        log.debug("Request to get all Matches");
        return matchRepository.findAll();
    }

    /**
     * Get one match by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Match> findOne(UUID id) {
        log.debug("Request to get Match : {}", id);
        return matchRepository.findById(id);
    }

    /**
     * Delete the match by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Match : {}", id);
        matchRepository.deleteById(id);
    }
}
