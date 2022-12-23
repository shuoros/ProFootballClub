package club.profb.service;

import club.profb.domain.Transfer;
import club.profb.repository.TransferRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Transfer}.
 */
@Service
@Transactional
public class TransferService {

    private final Logger log = LoggerFactory.getLogger(TransferService.class);

    private final TransferRepository transferRepository;

    public TransferService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    /**
     * Save a transfer.
     *
     * @param transfer the entity to save.
     * @return the persisted entity.
     */
    public Transfer save(Transfer transfer) {
        log.debug("Request to save Transfer : {}", transfer);
        return transferRepository.save(transfer);
    }

    /**
     * Update a transfer.
     *
     * @param transfer the entity to save.
     * @return the persisted entity.
     */
    public Transfer update(Transfer transfer) {
        log.debug("Request to save Transfer : {}", transfer);
        return transferRepository.save(transfer);
    }

    /**
     * Partially update a transfer.
     *
     * @param transfer the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Transfer> partialUpdate(Transfer transfer) {
        log.debug("Request to partially update Transfer : {}", transfer);

        return transferRepository
            .findById(transfer.getId())
            .map(existingTransfer -> {
                if (transfer.getPrice() != null) {
                    existingTransfer.setPrice(transfer.getPrice());
                }
                if (transfer.getPassword() != null) {
                    existingTransfer.setPassword(transfer.getPassword());
                }
                if (transfer.getBought() != null) {
                    existingTransfer.setBought(transfer.getBought());
                }

                return existingTransfer;
            })
            .map(transferRepository::save);
    }

    /**
     * Get all the transfers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Transfer> findAll() {
        log.debug("Request to get all Transfers");
        return transferRepository.findAll();
    }

    /**
     * Get one transfer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Transfer> findOne(UUID id) {
        log.debug("Request to get Transfer : {}", id);
        return transferRepository.findById(id);
    }

    /**
     * Delete the transfer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Transfer : {}", id);
        transferRepository.deleteById(id);
    }
}
