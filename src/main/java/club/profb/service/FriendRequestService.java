package club.profb.service;

import club.profb.domain.FriendRequest;
import club.profb.repository.FriendRequestRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FriendRequest}.
 */
@Service
@Transactional
public class FriendRequestService {

    private final Logger log = LoggerFactory.getLogger(FriendRequestService.class);

    private final FriendRequestRepository friendRequestRepository;

    public FriendRequestService(FriendRequestRepository friendRequestRepository) {
        this.friendRequestRepository = friendRequestRepository;
    }

    /**
     * Save a friendRequest.
     *
     * @param friendRequest the entity to save.
     * @return the persisted entity.
     */
    public FriendRequest save(FriendRequest friendRequest) {
        log.debug("Request to save FriendRequest : {}", friendRequest);
        return friendRequestRepository.save(friendRequest);
    }

    /**
     * Update a friendRequest.
     *
     * @param friendRequest the entity to save.
     * @return the persisted entity.
     */
    public FriendRequest update(FriendRequest friendRequest) {
        log.debug("Request to save FriendRequest : {}", friendRequest);
        return friendRequestRepository.save(friendRequest);
    }

    /**
     * Partially update a friendRequest.
     *
     * @param friendRequest the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FriendRequest> partialUpdate(FriendRequest friendRequest) {
        log.debug("Request to partially update FriendRequest : {}", friendRequest);

        return friendRequestRepository
            .findById(friendRequest.getId())
            .map(existingFriendRequest -> {
                return existingFriendRequest;
            })
            .map(friendRequestRepository::save);
    }

    /**
     * Get all the friendRequests.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FriendRequest> findAll() {
        log.debug("Request to get all FriendRequests");
        return friendRequestRepository.findAll();
    }

    /**
     * Get one friendRequest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FriendRequest> findOne(UUID id) {
        log.debug("Request to get FriendRequest : {}", id);
        return friendRequestRepository.findById(id);
    }

    /**
     * Delete the friendRequest by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete FriendRequest : {}", id);
        friendRequestRepository.deleteById(id);
    }
}
