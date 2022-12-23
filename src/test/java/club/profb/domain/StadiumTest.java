package club.profb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import club.profb.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class StadiumTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stadium.class);
        Stadium stadium1 = new Stadium();
        stadium1.setId(UUID.randomUUID());
        Stadium stadium2 = new Stadium();
        stadium2.setId(stadium1.getId());
        assertThat(stadium1).isEqualTo(stadium2);
        stadium2.setId(UUID.randomUUID());
        assertThat(stadium1).isNotEqualTo(stadium2);
        stadium1.setId(null);
        assertThat(stadium1).isNotEqualTo(stadium2);
    }
}
