package club.profb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import club.profb.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class LeagueBoardTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeagueBoard.class);
        LeagueBoard leagueBoard1 = new LeagueBoard();
        leagueBoard1.setId(UUID.randomUUID());
        LeagueBoard leagueBoard2 = new LeagueBoard();
        leagueBoard2.setId(leagueBoard1.getId());
        assertThat(leagueBoard1).isEqualTo(leagueBoard2);
        leagueBoard2.setId(UUID.randomUUID());
        assertThat(leagueBoard1).isNotEqualTo(leagueBoard2);
        leagueBoard1.setId(null);
        assertThat(leagueBoard1).isNotEqualTo(leagueBoard2);
    }
}
