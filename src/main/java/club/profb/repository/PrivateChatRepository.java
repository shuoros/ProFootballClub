package club.profb.repository;

import club.profb.domain.PrivateChat;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PrivateChat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrivateChatRepository extends JpaRepository<PrivateChat, UUID> {}
