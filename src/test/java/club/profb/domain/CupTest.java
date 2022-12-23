package club.profb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import club.profb.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cup.class);
        Cup cup1 = new Cup();
        cup1.setId(UUID.randomUUID());
        Cup cup2 = new Cup();
        cup2.setId(cup1.getId());
        assertThat(cup1).isEqualTo(cup2);
        cup2.setId(UUID.randomUUID());
        assertThat(cup1).isNotEqualTo(cup2);
        cup1.setId(null);
        assertThat(cup1).isNotEqualTo(cup2);
    }
}
