package club.profb.repository;

import club.profb.domain.Cup;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Cup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CupRepository extends JpaRepository<Cup, UUID> {}
