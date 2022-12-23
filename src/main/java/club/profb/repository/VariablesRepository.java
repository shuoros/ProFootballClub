package club.profb.repository;

import club.profb.domain.Variables;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Variables entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VariablesRepository extends JpaRepository<Variables, UUID> {}
