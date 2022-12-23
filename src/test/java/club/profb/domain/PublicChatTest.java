package club.profb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import club.profb.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PublicChatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicChat.class);
        PublicChat publicChat1 = new PublicChat();
        publicChat1.setId(UUID.randomUUID());
        PublicChat publicChat2 = new PublicChat();
        publicChat2.setId(publicChat1.getId());
        assertThat(publicChat1).isEqualTo(publicChat2);
        publicChat2.setId(UUID.randomUUID());
        assertThat(publicChat1).isNotEqualTo(publicChat2);
        publicChat1.setId(null);
        assertThat(publicChat1).isNotEqualTo(publicChat2);
    }
}
