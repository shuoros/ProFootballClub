package club.profb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import club.profb.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class FriendRequestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FriendRequest.class);
        FriendRequest friendRequest1 = new FriendRequest();
        friendRequest1.setId(UUID.randomUUID());
        FriendRequest friendRequest2 = new FriendRequest();
        friendRequest2.setId(friendRequest1.getId());
        assertThat(friendRequest1).isEqualTo(friendRequest2);
        friendRequest2.setId(UUID.randomUUID());
        assertThat(friendRequest1).isNotEqualTo(friendRequest2);
        friendRequest1.setId(null);
        assertThat(friendRequest1).isNotEqualTo(friendRequest2);
    }
}
