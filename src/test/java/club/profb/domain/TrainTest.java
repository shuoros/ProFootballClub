package club.profb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import club.profb.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class TrainTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Train.class);
        Train train1 = new Train();
        train1.setId(UUID.randomUUID());
        Train train2 = new Train();
        train2.setId(train1.getId());
        assertThat(train1).isEqualTo(train2);
        train2.setId(UUID.randomUUID());
        assertThat(train1).isNotEqualTo(train2);
        train1.setId(null);
        assertThat(train1).isNotEqualTo(train2);
    }
}
