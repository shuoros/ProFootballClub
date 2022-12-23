package club.profb.service;

import club.profb.domain.Player;
import club.profb.repository.PlayerRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Player}.
 */
@Service
@Transactional
public class PlayerService {

    private final Logger log = LoggerFactory.getLogger(PlayerService.class);

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    /**
     * Save a player.
     *
     * @param player the entity to save.
     * @return the persisted entity.
     */
    public Player save(Player player) {
        log.debug("Request to save Player : {}", player);
        return playerRepository.save(player);
    }

    /**
     * Update a player.
     *
     * @param player the entity to save.
     * @return the persisted entity.
     */
    public Player update(Player player) {
        log.debug("Request to save Player : {}", player);
        return playerRepository.save(player);
    }

    /**
     * Partially update a player.
     *
     * @param player the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Player> partialUpdate(Player player) {
        log.debug("Request to partially update Player : {}", player);

        return playerRepository
            .findById(player.getId())
            .map(existingPlayer -> {
                if (player.getFirstName() != null) {
                    existingPlayer.setFirstName(player.getFirstName());
                }
                if (player.getLastName() != null) {
                    existingPlayer.setLastName(player.getLastName());
                }
                if (player.getGender() != null) {
                    existingPlayer.setGender(player.getGender());
                }
                if (player.getCountry() != null) {
                    existingPlayer.setCountry(player.getCountry());
                }
                if (player.getAge() != null) {
                    existingPlayer.setAge(player.getAge());
                }
                if (player.getStatus() != null) {
                    existingPlayer.setStatus(player.getStatus());
                }
                if (player.getPost() != null) {
                    existingPlayer.setPost(player.getPost());
                }
                if (player.getTotalPower() != null) {
                    existingPlayer.setTotalPower(player.getTotalPower());
                }
                if (player.getGoalkeeping() != null) {
                    existingPlayer.setGoalkeeping(player.getGoalkeeping());
                }
                if (player.getDefence() != null) {
                    existingPlayer.setDefence(player.getDefence());
                }
                if (player.getTackling() != null) {
                    existingPlayer.setTackling(player.getTackling());
                }
                if (player.getPassing() != null) {
                    existingPlayer.setPassing(player.getPassing());
                }
                if (player.getTeamSkill() != null) {
                    existingPlayer.setTeamSkill(player.getTeamSkill());
                }
                if (player.getBallSkill() != null) {
                    existingPlayer.setBallSkill(player.getBallSkill());
                }
                if (player.getShooting() != null) {
                    existingPlayer.setShooting(player.getShooting());
                }
                if (player.getLongShooting() != null) {
                    existingPlayer.setLongShooting(player.getLongShooting());
                }
                if (player.getDribbling() != null) {
                    existingPlayer.setDribbling(player.getDribbling());
                }
                if (player.getTechnique() != null) {
                    existingPlayer.setTechnique(player.getTechnique());
                }
                if (player.getConfidence() != null) {
                    existingPlayer.setConfidence(player.getConfidence());
                }

                return existingPlayer;
            })
            .map(playerRepository::save);
    }

    /**
     * Get all the players.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Player> findAll() {
        log.debug("Request to get all Players");
        return playerRepository.findAll();
    }

    /**
     * Get one player by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Player> findOne(UUID id) {
        log.debug("Request to get Player : {}", id);
        return playerRepository.findById(id);
    }

    /**
     * Delete the player by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Player : {}", id);
        playerRepository.deleteById(id);
    }
}
