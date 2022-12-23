package club.profb.service;

import club.profb.domain.Stadium;
import club.profb.repository.StadiumRepository;
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
 * Service Implementation for managing {@link Stadium}.
 */
@Service
@Transactional
public class StadiumService {

    private final Logger log = LoggerFactory.getLogger(StadiumService.class);

    private final StadiumRepository stadiumRepository;

    public StadiumService(StadiumRepository stadiumRepository) {
        this.stadiumRepository = stadiumRepository;
    }

    /**
     * Save a stadium.
     *
     * @param stadium the entity to save.
     * @return the persisted entity.
     */
    public Stadium save(Stadium stadium) {
        log.debug("Request to save Stadium : {}", stadium);
        return stadiumRepository.save(stadium);
    }

    /**
     * Update a stadium.
     *
     * @param stadium the entity to save.
     * @return the persisted entity.
     */
    public Stadium update(Stadium stadium) {
        log.debug("Request to save Stadium : {}", stadium);
        return stadiumRepository.save(stadium);
    }

    /**
     * Partially update a stadium.
     *
     * @param stadium the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Stadium> partialUpdate(Stadium stadium) {
        log.debug("Request to partially update Stadium : {}", stadium);

        return stadiumRepository
            .findById(stadium.getId())
            .map(existingStadium -> {
                if (stadium.getName() != null) {
                    existingStadium.setName(stadium.getName());
                }
                if (stadium.getCapacity() != null) {
                    existingStadium.setCapacity(stadium.getCapacity());
                }
                if (stadium.getTicket() != null) {
                    existingStadium.setTicket(stadium.getTicket());
                }
                if (stadium.getLeader() != null) {
                    existingStadium.setLeader(stadium.getLeader());
                }
                if (stadium.getVehicle() != null) {
                    existingStadium.setVehicle(stadium.getVehicle());
                }
                if (stadium.getField() != null) {
                    existingStadium.setField(stadium.getField());
                }
                if (stadium.getLight() != null) {
                    existingStadium.setLight(stadium.getLight());
                }
                if (stadium.getChair() != null) {
                    existingStadium.setChair(stadium.getChair());
                }
                if (stadium.getAssistant() != null) {
                    existingStadium.setAssistant(stadium.getAssistant());
                }
                if (stadium.getBodyBuilding() != null) {
                    existingStadium.setBodyBuilding(stadium.getBodyBuilding());
                }
                if (stadium.getDoctor() != null) {
                    existingStadium.setDoctor(stadium.getDoctor());
                }

                return existingStadium;
            })
            .map(stadiumRepository::save);
    }

    /**
     * Get all the stadiums.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Stadium> findAll() {
        log.debug("Request to get all Stadiums");
        return stadiumRepository.findAll();
    }

    /**
     *  Get all the stadiums where Team is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Stadium> findAllWhereTeamIsNull() {
        log.debug("Request to get all stadiums where Team is null");
        return StreamSupport
            .stream(stadiumRepository.findAll().spliterator(), false)
            .filter(stadium -> stadium.getTeam() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one stadium by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Stadium> findOne(UUID id) {
        log.debug("Request to get Stadium : {}", id);
        return stadiumRepository.findById(id);
    }

    /**
     * Delete the stadium by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Stadium : {}", id);
        stadiumRepository.deleteById(id);
    }
}
