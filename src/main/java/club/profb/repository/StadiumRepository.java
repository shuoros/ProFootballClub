package club.profb.repository;

import club.profb.domain.Stadium;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Stadium entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StadiumRepository extends JpaRepository<Stadium, UUID> {}
