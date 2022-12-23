package club.profb.repository;

import club.profb.domain.NewsPaper;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NewsPaper entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NewsPaperRepository extends JpaRepository<NewsPaper, UUID> {}
