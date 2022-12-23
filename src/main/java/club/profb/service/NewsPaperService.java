package club.profb.service;

import club.profb.domain.NewsPaper;
import club.profb.repository.NewsPaperRepository;
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
 * Service Implementation for managing {@link NewsPaper}.
 */
@Service
@Transactional
public class NewsPaperService {

    private final Logger log = LoggerFactory.getLogger(NewsPaperService.class);

    private final NewsPaperRepository newsPaperRepository;

    public NewsPaperService(NewsPaperRepository newsPaperRepository) {
        this.newsPaperRepository = newsPaperRepository;
    }

    /**
     * Save a newsPaper.
     *
     * @param newsPaper the entity to save.
     * @return the persisted entity.
     */
    public NewsPaper save(NewsPaper newsPaper) {
        log.debug("Request to save NewsPaper : {}", newsPaper);
        return newsPaperRepository.save(newsPaper);
    }

    /**
     * Update a newsPaper.
     *
     * @param newsPaper the entity to save.
     * @return the persisted entity.
     */
    public NewsPaper update(NewsPaper newsPaper) {
        log.debug("Request to save NewsPaper : {}", newsPaper);
        return newsPaperRepository.save(newsPaper);
    }

    /**
     * Partially update a newsPaper.
     *
     * @param newsPaper the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NewsPaper> partialUpdate(NewsPaper newsPaper) {
        log.debug("Request to partially update NewsPaper : {}", newsPaper);

        return newsPaperRepository
            .findById(newsPaper.getId())
            .map(existingNewsPaper -> {
                if (newsPaper.getNews() != null) {
                    existingNewsPaper.setNews(newsPaper.getNews());
                }

                return existingNewsPaper;
            })
            .map(newsPaperRepository::save);
    }

    /**
     * Get all the newsPapers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<NewsPaper> findAll() {
        log.debug("Request to get all NewsPapers");
        return newsPaperRepository.findAll();
    }

    /**
     *  Get all the newsPapers where League is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<NewsPaper> findAllWhereLeagueIsNull() {
        log.debug("Request to get all newsPapers where League is null");
        return StreamSupport
            .stream(newsPaperRepository.findAll().spliterator(), false)
            .filter(newsPaper -> newsPaper.getLeague() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get all the newsPapers where Cup is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<NewsPaper> findAllWhereCupIsNull() {
        log.debug("Request to get all newsPapers where Cup is null");
        return StreamSupport
            .stream(newsPaperRepository.findAll().spliterator(), false)
            .filter(newsPaper -> newsPaper.getCup() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one newsPaper by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NewsPaper> findOne(UUID id) {
        log.debug("Request to get NewsPaper : {}", id);
        return newsPaperRepository.findById(id);
    }

    /**
     * Delete the newsPaper by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete NewsPaper : {}", id);
        newsPaperRepository.deleteById(id);
    }
}
