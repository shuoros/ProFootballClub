package club.profb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import club.profb.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PlayerArrangeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlayerArrange.class);
        PlayerArrange playerArrange1 = new PlayerArrange();
        playerArrange1.setId(UUID.randomUUID());
        PlayerArrange playerArrange2 = new PlayerArrange();
        playerArrange2.setId(playerArrange1.getId());
        assertThat(playerArrange1).isEqualTo(playerArrange2);
        playerArrange2.setId(UUID.randomUUID());
        assertThat(playerArrange1).isNotEqualTo(playerArrange2);
        playerArrange1.setId(null);
        assertThat(playerArrange1).isNotEqualTo(playerArrange2);
    }
}
