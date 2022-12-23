package club.profb.repository;

import club.profb.domain.LeagueBoard;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeagueBoard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeagueBoardRepository extends JpaRepository<LeagueBoard, UUID> {}
