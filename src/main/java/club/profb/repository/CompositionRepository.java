package club.profb.repository;

import club.profb.domain.Composition;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Composition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompositionRepository extends JpaRepository<Composition, UUID> {}
