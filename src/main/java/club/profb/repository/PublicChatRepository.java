package club.profb.repository;

import club.profb.domain.PublicChat;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PublicChat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicChatRepository extends JpaRepository<PublicChat, UUID> {}
