package club.profb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import club.profb.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CoachTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Coach.class);
        Coach coach1 = new Coach();
        coach1.setId(UUID.randomUUID());
        Coach coach2 = new Coach();
        coach2.setId(coach1.getId());
        assertThat(coach1).isEqualTo(coach2);
        coach2.setId(UUID.randomUUID());
        assertThat(coach1).isNotEqualTo(coach2);
        coach1.setId(null);
        assertThat(coach1).isNotEqualTo(coach2);
    }
}
