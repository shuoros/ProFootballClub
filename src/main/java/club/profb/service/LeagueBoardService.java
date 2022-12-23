package club.profb.service;

import club.profb.domain.LeagueBoard;
import club.profb.repository.LeagueBoardRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeagueBoard}.
 */
@Service
@Transactional
public class LeagueBoardService {

    private final Logger log = LoggerFactory.getLogger(LeagueBoardService.class);

    private final LeagueBoardRepository leagueBoardRepository;

    public LeagueBoardService(LeagueBoardRepository leagueBoardRepository) {
        this.leagueBoardRepository = leagueBoardRepository;
    }

    /**
     * Save a leagueBoard.
     *
     * @param leagueBoard the entity to save.
     * @return the persisted entity.
     */
    public LeagueBoard save(LeagueBoard leagueBoard) {
        log.debug("Request to save LeagueBoard : {}", leagueBoard);
        return leagueBoardRepository.save(leagueBoard);
    }

    /**
     * Update a leagueBoard.
     *
     * @param leagueBoard the entity to save.
     * @return the persisted entity.
     */
    public LeagueBoard update(LeagueBoard leagueBoard) {
        log.debug("Request to save LeagueBoard : {}", leagueBoard);
        return leagueBoardRepository.save(leagueBoard);
    }

    /**
     * Partially update a leagueBoard.
     *
     * @param leagueBoard the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LeagueBoard> partialUpdate(LeagueBoard leagueBoard) {
        log.debug("Request to partially update LeagueBoard : {}", leagueBoard);

        return leagueBoardRepository
            .findById(leagueBoard.getId())
            .map(existingLeagueBoard -> {
                if (leagueBoard.getPosition() != null) {
                    existingLeagueBoard.setPosition(leagueBoard.getPosition());
                }
                if (leagueBoard.getWin() != null) {
                    existingLeagueBoard.setWin(leagueBoard.getWin());
                }
                if (leagueBoard.getLose() != null) {
                    existingLeagueBoard.setLose(leagueBoard.getLose());
                }
                if (leagueBoard.getDraw() != null) {
                    existingLeagueBoard.setDraw(leagueBoard.getDraw());
                }
                if (leagueBoard.getGoalDifference() != null) {
                    existingLeagueBoard.setGoalDifference(leagueBoard.getGoalDifference());
                }
                if (leagueBoard.getPoints() != null) {
                    existingLeagueBoard.setPoints(leagueBoard.getPoints());
                }

                return existingLeagueBoard;
            })
            .map(leagueBoardRepository::save);
    }

    /**
     * Get all the leagueBoards.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LeagueBoard> findAll() {
        log.debug("Request to get all LeagueBoards");
        return leagueBoardRepository.findAll();
    }

    /**
     * Get one leagueBoard by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeagueBoard> findOne(UUID id) {
        log.debug("Request to get LeagueBoard : {}", id);
        return leagueBoardRepository.findById(id);
    }

    /**
     * Delete the leagueBoard by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete LeagueBoard : {}", id);
        leagueBoardRepository.deleteById(id);
    }
}
