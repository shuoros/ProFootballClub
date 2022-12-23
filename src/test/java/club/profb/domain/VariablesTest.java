package club.profb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import club.profb.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class VariablesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Variables.class);
        Variables variables1 = new Variables();
        variables1.setId(UUID.randomUUID());
        Variables variables2 = new Variables();
        variables2.setId(variables1.getId());
        assertThat(variables1).isEqualTo(variables2);
        variables2.setId(UUID.randomUUID());
        assertThat(variables1).isNotEqualTo(variables2);
        variables1.setId(null);
        assertThat(variables1).isNotEqualTo(variables2);
    }
}
