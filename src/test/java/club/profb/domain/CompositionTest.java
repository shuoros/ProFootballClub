package club.profb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import club.profb.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CompositionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Composition.class);
        Composition composition1 = new Composition();
        composition1.setId(UUID.randomUUID());
        Composition composition2 = new Composition();
        composition2.setId(composition1.getId());
        assertThat(composition1).isEqualTo(composition2);
        composition2.setId(UUID.randomUUID());
        assertThat(composition1).isNotEqualTo(composition2);
        composition1.setId(null);
        assertThat(composition1).isNotEqualTo(composition2);
    }
}
