package club.profb.repository;

import club.profb.domain.PlayerArrange;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PlayerArrange entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayerArrangeRepository extends JpaRepository<PlayerArrange, UUID> {}
