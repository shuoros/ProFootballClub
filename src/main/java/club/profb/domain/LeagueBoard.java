package club.profb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Type;

/**
 * A LeagueBoard.
 */
@Entity
@Table(name = "league_board")
public class LeagueBoard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36)
    private UUID id;

    @NotNull
    @Min(value = 1)
    @Max(value = 8)
    @Column(name = "position", nullable = false)
    private Integer position;

    @Column(name = "win")
    private Integer win;

    @Column(name = "lose")
    private Integer lose;

    @Column(name = "draw")
    private Integer draw;

    @Column(name = "goal_difference")
    private Integer goalDifference;

    @Column(name = "points")
    private Integer points;

    @JsonIgnoreProperties(value = { "stadium", "players", "compositions", "friends", "leagueBoard", "coach", "team" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Team team;

    @ManyToOne
    @JsonIgnoreProperties(value = { "goalScorer", "newsPaper", "matches", "leagueBoards" }, allowSetters = true)
    private League league;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public LeagueBoard id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getPosition() {
        return this.position;
    }

    public LeagueBoard position(Integer position) {
        this.setPosition(position);
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getWin() {
        return this.win;
    }

    public LeagueBoard win(Integer win) {
        this.setWin(win);
        return this;
    }

    public void setWin(Integer win) {
        this.win = win;
    }

    public Integer getLose() {
        return this.lose;
    }

    public LeagueBoard lose(Integer lose) {
        this.setLose(lose);
        return this;
    }

    public void setLose(Integer lose) {
        this.lose = lose;
    }

    public Integer getDraw() {
        return this.draw;
    }

    public LeagueBoard draw(Integer draw) {
        this.setDraw(draw);
        return this;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getGoalDifference() {
        return this.goalDifference;
    }

    public LeagueBoard goalDifference(Integer goalDifference) {
        this.setGoalDifference(goalDifference);
        return this;
    }

    public void setGoalDifference(Integer goalDifference) {
        this.goalDifference = goalDifference;
    }

    public Integer getPoints() {
        return this.points;
    }

    public LeagueBoard points(Integer points) {
        this.setPoints(points);
        return this;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public LeagueBoard team(Team team) {
        this.setTeam(team);
        return this;
    }

    public League getLeague() {
        return this.league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public LeagueBoard league(League league) {
        this.setLeague(league);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeagueBoard)) {
            return false;
        }
        return id != null && id.equals(((LeagueBoard) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeagueBoard{" +
            "id=" + getId() +
            ", position=" + getPosition() +
            ", win=" + getWin() +
            ", lose=" + getLose() +
            ", draw=" + getDraw() +
            ", goalDifference=" + getGoalDifference() +
            ", points=" + getPoints() +
            "}";
    }
}
