package club.profb.repository;

import club.profb.domain.Transfer;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Transfer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransferRepository extends JpaRepository<Transfer, UUID> {}
