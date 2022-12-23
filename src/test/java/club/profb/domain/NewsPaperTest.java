package club.profb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import club.profb.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class NewsPaperTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NewsPaper.class);
        NewsPaper newsPaper1 = new NewsPaper();
        newsPaper1.setId(UUID.randomUUID());
        NewsPaper newsPaper2 = new NewsPaper();
        newsPaper2.setId(newsPaper1.getId());
        assertThat(newsPaper1).isEqualTo(newsPaper2);
        newsPaper2.setId(UUID.randomUUID());
        assertThat(newsPaper1).isNotEqualTo(newsPaper2);
        newsPaper1.setId(null);
        assertThat(newsPaper1).isNotEqualTo(newsPaper2);
    }
}
