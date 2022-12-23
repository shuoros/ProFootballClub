package club.profb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import club.profb.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PrivateChatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrivateChat.class);
        PrivateChat privateChat1 = new PrivateChat();
        privateChat1.setId(UUID.randomUUID());
        PrivateChat privateChat2 = new PrivateChat();
        privateChat2.setId(privateChat1.getId());
        assertThat(privateChat1).isEqualTo(privateChat2);
        privateChat2.setId(UUID.randomUUID());
        assertThat(privateChat1).isNotEqualTo(privateChat2);
        privateChat1.setId(null);
        assertThat(privateChat1).isNotEqualTo(privateChat2);
    }
}
