package club.profb.service;

import club.profb.domain.Team;
import club.profb.repository.TeamRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Team}.
 */
@Service
@Transactional
public class TeamService {

    private final Logger log = LoggerFactory.getLogger(TeamService.class);

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    /**
     * Save a team.
     *
     * @param team the entity to save.
     * @return the persisted entity.
     */
    public Team save(Team team) {
        log.debug("Request to save Team : {}", team);
        return teamRepository.save(team);
    }

    /**
     * Update a team.
     *
     * @param team the entity to save.
     * @return the persisted entity.
     */
    public Team update(Team team) {
        log.debug("Request to save Team : {}", team);
        return teamRepository.save(team);
    }

    /**
     * Partially update a team.
     *
     * @param team the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Team> partialUpdate(Team team) {
        log.debug("Request to partially update Team : {}", team);

        return teamRepository
            .findById(team.getId())
            .map(existingTeam -> {
                if (team.getName() != null) {
                    existingTeam.setName(team.getName());
                }
                if (team.getHomeland() != null) {
                    existingTeam.setHomeland(team.getHomeland());
                }
                if (team.getGender() != null) {
                    existingTeam.setGender(team.getGender());
                }
                if (team.getMoney() != null) {
                    existingTeam.setMoney(team.getMoney());
                }
                if (team.getPoints() != null) {
                    existingTeam.setPoints(team.getPoints());
                }
                if (team.getMatches() != null) {
                    existingTeam.setMatches(team.getMatches());
                }
                if (team.getTrophies() != null) {
                    existingTeam.setTrophies(team.getTrophies());
                }
                if (team.getReadiness() != null) {
                    existingTeam.setReadiness(team.getReadiness());
                }
                if (team.getSpirit() != null) {
                    existingTeam.setSpirit(team.getSpirit());
                }
                if (team.getFans() != null) {
                    existingTeam.setFans(team.getFans());
                }
                if (team.getFansSatisfaction() != null) {
                    existingTeam.setFansSatisfaction(team.getFansSatisfaction());
                }

                return existingTeam;
            })
            .map(teamRepository::save);
    }

    /**
     * Get all the teams.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Team> findAll() {
        log.debug("Request to get all Teams");
        return teamRepository.findAll();
    }

    /**
     *  Get all the teams where LeagueBoard is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Team> findAllWhereLeagueBoardIsNull() {
        log.debug("Request to get all teams where LeagueBoard is null");
        return StreamSupport
            .stream(teamRepository.findAll().spliterator(), false)
            .filter(team -> team.getLeagueBoard() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one team by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Team> findOne(UUID id) {
        log.debug("Request to get Team : {}", id);
        return teamRepository.findById(id);
    }

    /**
     * Delete the team by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Team : {}", id);
        teamRepository.deleteById(id);
    }
}
