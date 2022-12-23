package club.profb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import club.profb.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CupBoardTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CupBoard.class);
        CupBoard cupBoard1 = new CupBoard();
        cupBoard1.setId(UUID.randomUUID());
        CupBoard cupBoard2 = new CupBoard();
        cupBoard2.setId(cupBoard1.getId());
        assertThat(cupBoard1).isEqualTo(cupBoard2);
        cupBoard2.setId(UUID.randomUUID());
        assertThat(cupBoard1).isNotEqualTo(cupBoard2);
        cupBoard1.setId(null);
        assertThat(cupBoard1).isNotEqualTo(cupBoard2);
    }
}
