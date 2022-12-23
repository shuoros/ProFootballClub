package club.profb.domain;

import club.profb.domain.enumeration.MatchType;
import club.profb.domain.enumeration.Weather;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Type;

/**
 * A Match.
 */
@Entity
@Table(name = "jhi_match")
public class Match implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36)
    private UUID id;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "weather", nullable = false)
    private Weather weather;

    @Column(name = "host_goals")
    private Integer hostGoals;

    @Column(name = "guest_goals")
    private Integer guestGoals;

    @Column(name = "events")
    private String events;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private MatchType type;

    @JsonIgnoreProperties(value = { "stadium", "players", "compositions", "friends", "leagueBoard", "coach", "team" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Team host;

    @JsonIgnoreProperties(value = { "stadium", "players", "compositions", "friends", "leagueBoard", "coach", "team" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Team guest;

    @JsonIgnoreProperties(value = { "capitan", "playerArranges", "team" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Composition hostComposition;

    @JsonIgnoreProperties(value = { "capitan", "playerArranges", "team" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Composition guestComposition;

    @JsonIgnoreProperties(value = { "team" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Player bestPlayer;

    @ManyToOne
    @JsonIgnoreProperties(value = { "goalScorer", "newsPaper", "matches", "leagueBoards" }, allowSetters = true)
    private League league;

    @ManyToOne
    @JsonIgnoreProperties(value = { "matches", "cup" }, allowSetters = true)
    private CupBoard cupBoard;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Match id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getDate() {
        return this.date;
    }

    public Match date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Weather getWeather() {
        return this.weather;
    }

    public Match weather(Weather weather) {
        this.setWeather(weather);
        return this;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Integer getHostGoals() {
        return this.hostGoals;
    }

    public Match hostGoals(Integer hostGoals) {
        this.setHostGoals(hostGoals);
        return this;
    }

    public void setHostGoals(Integer hostGoals) {
        this.hostGoals = hostGoals;
    }

    public Integer getGuestGoals() {
        return this.guestGoals;
    }

    public Match guestGoals(Integer guestGoals) {
        this.setGuestGoals(guestGoals);
        return this;
    }

    public void setGuestGoals(Integer guestGoals) {
        this.guestGoals = guestGoals;
    }

    public String getEvents() {
        return this.events;
    }

    public Match events(String events) {
        this.setEvents(events);
        return this;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    public MatchType getType() {
        return this.type;
    }

    public Match type(MatchType type) {
        this.setType(type);
        return this;
    }

    public void setType(MatchType type) {
        this.type = type;
    }

    public Team getHost() {
        return this.host;
    }

    public void setHost(Team team) {
        this.host = team;
    }

    public Match host(Team team) {
        this.setHost(team);
        return this;
    }

    public Team getGuest() {
        return this.guest;
    }

    public void setGuest(Team team) {
        this.guest = team;
    }

    public Match guest(Team team) {
        this.setGuest(team);
        return this;
    }

    public Composition getHostComposition() {
        return this.hostComposition;
    }

    public void setHostComposition(Composition composition) {
        this.hostComposition = composition;
    }

    public Match hostComposition(Composition composition) {
        this.setHostComposition(composition);
        return this;
    }

    public Composition getGuestComposition() {
        return this.guestComposition;
    }

    public void setGuestComposition(Composition composition) {
        this.guestComposition = composition;
    }

    public Match guestComposition(Composition composition) {
        this.setGuestComposition(composition);
        return this;
    }

    public Player getBestPlayer() {
        return this.bestPlayer;
    }

    public void setBestPlayer(Player player) {
        this.bestPlayer = player;
    }

    public Match bestPlayer(Player player) {
        this.setBestPlayer(player);
        return this;
    }

    public League getLeague() {
        return this.league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public Match league(League league) {
        this.setLeague(league);
        return this;
    }

    public CupBoard getCupBoard() {
        return this.cupBoard;
    }

    public void setCupBoard(CupBoard cupBoard) {
        this.cupBoard = cupBoard;
    }

    public Match cupBoard(CupBoard cupBoard) {
        this.setCupBoard(cupBoard);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Match)) {
            return false;
        }
        return id != null && id.equals(((Match) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Match{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", weather='" + getWeather() + "'" +
            ", hostGoals=" + getHostGoals() +
            ", guestGoals=" + getGuestGoals() +
            ", events='" + getEvents() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
