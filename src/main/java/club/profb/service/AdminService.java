package club.profb.service;

import club.profb.domain.Admin;
import club.profb.repository.AdminRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Admin}.
 */
@Service
@Transactional
public class AdminService {

    private final Logger log = LoggerFactory.getLogger(AdminService.class);

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * Save a admin.
     *
     * @param admin the entity to save.
     * @return the persisted entity.
     */
    public Admin save(Admin admin) {
        log.debug("Request to save Admin : {}", admin);
        return adminRepository.save(admin);
    }

    /**
     * Update a admin.
     *
     * @param admin the entity to save.
     * @return the persisted entity.
     */
    public Admin update(Admin admin) {
        log.debug("Request to save Admin : {}", admin);
        return adminRepository.save(admin);
    }

    /**
     * Partially update a admin.
     *
     * @param admin the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Admin> partialUpdate(Admin admin) {
        log.debug("Request to partially update Admin : {}", admin);

        return adminRepository
            .findById(admin.getId())
            .map(existingAdmin -> {
                if (admin.getAdminId() != null) {
                    existingAdmin.setAdminId(admin.getAdminId());
                }

                return existingAdmin;
            })
            .map(adminRepository::save);
    }

    /**
     * Get all the admins.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Admin> findAll() {
        log.debug("Request to get all Admins");
        return adminRepository.findAll();
    }

    /**
     * Get one admin by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Admin> findOne(UUID id) {
        log.debug("Request to get Admin : {}", id);
        return adminRepository.findById(id);
    }

    /**
     * Delete the admin by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Admin : {}", id);
        adminRepository.deleteById(id);
    }
}
