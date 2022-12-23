package club.profb.repository;

import club.profb.domain.CupBoard;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CupBoard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CupBoardRepository extends JpaRepository<CupBoard, UUID> {}
