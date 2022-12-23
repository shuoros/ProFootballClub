package club.profb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Type;

/**
 * A League.
 */
@Entity
@Table(name = "league")
public class League implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36)
    private UUID id;

    @NotNull
    @Min(value = 1)
    @Max(value = 14)
    @Column(name = "clazz", nullable = false)
    private Integer clazz;

    @Column(name = "events")
    private String events;

    @Column(name = "finished")
    private Boolean finished;

    @Column(name = "start")
    private Instant start;

    @JsonIgnoreProperties(value = { "team" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Player goalScorer;

    @JsonIgnoreProperties(value = { "league", "cup" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private NewsPaper newsPaper;

    @OneToMany(mappedBy = "league")
    @JsonIgnoreProperties(
        value = { "host", "guest", "hostComposition", "guestComposition", "bestPlayer", "league", "cupBoard" },
        allowSetters = true
    )
    private Set<Match> matches = new HashSet<>();

    @OneToMany(mappedBy = "league")
    @JsonIgnoreProperties(value = { "team", "league" }, allowSetters = true)
    private Set<LeagueBoard> leagueBoards = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public League id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getClazz() {
        return this.clazz;
    }

    public League clazz(Integer clazz) {
        this.setClazz(clazz);
        return this;
    }

    public void setClazz(Integer clazz) {
        this.clazz = clazz;
    }

    public String getEvents() {
        return this.events;
    }

    public League events(String events) {
        this.setEvents(events);
        return this;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    public Boolean getFinished() {
        return this.finished;
    }

    public League finished(Boolean finished) {
        this.setFinished(finished);
        return this;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Instant getStart() {
        return this.start;
    }

    public League start(Instant start) {
        this.setStart(start);
        return this;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Player getGoalScorer() {
        return this.goalScorer;
    }

    public void setGoalScorer(Player player) {
        this.goalScorer = player;
    }

    public League goalScorer(Player player) {
        this.setGoalScorer(player);
        return this;
    }

    public NewsPaper getNewsPaper() {
        return this.newsPaper;
    }

    public void setNewsPaper(NewsPaper newsPaper) {
        this.newsPaper = newsPaper;
    }

    public League newsPaper(NewsPaper newsPaper) {
        this.setNewsPaper(newsPaper);
        return this;
    }

    public Set<Match> getMatches() {
        return this.matches;
    }

    public void setMatches(Set<Match> matches) {
        if (this.matches != null) {
            this.matches.forEach(i -> i.setLeague(null));
        }
        if (matches != null) {
            matches.forEach(i -> i.setLeague(this));
        }
        this.matches = matches;
    }

    public League matches(Set<Match> matches) {
        this.setMatches(matches);
        return this;
    }

    public League addMatch(Match match) {
        this.matches.add(match);
        match.setLeague(this);
        return this;
    }

    public League removeMatch(Match match) {
        this.matches.remove(match);
        match.setLeague(null);
        return this;
    }

    public Set<LeagueBoard> getLeagueBoards() {
        return this.leagueBoards;
    }

    public void setLeagueBoards(Set<LeagueBoard> leagueBoards) {
        if (this.leagueBoards != null) {
            this.leagueBoards.forEach(i -> i.setLeague(null));
        }
        if (leagueBoards != null) {
            leagueBoards.forEach(i -> i.setLeague(this));
        }
        this.leagueBoards = leagueBoards;
    }

    public League leagueBoards(Set<LeagueBoard> leagueBoards) {
        this.setLeagueBoards(leagueBoards);
        return this;
    }

    public League addLeagueBoard(LeagueBoard leagueBoard) {
        this.leagueBoards.add(leagueBoard);
        leagueBoard.setLeague(this);
        return this;
    }

    public League removeLeagueBoard(LeagueBoard leagueBoard) {
        this.leagueBoards.remove(leagueBoard);
        leagueBoard.setLeague(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof League)) {
            return false;
        }
        return id != null && id.equals(((League) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "League{" +
            "id=" + getId() +
            ", clazz=" + getClazz() +
            ", events='" + getEvents() + "'" +
            ", finished='" + getFinished() + "'" +
            ", start='" + getStart() + "'" +
            "}";
    }
}
