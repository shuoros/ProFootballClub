package club.profb.domain;

import club.profb.domain.enumeration.Country;
import club.profb.domain.enumeration.Gender;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Type;

/**
 * A Team.
 */
@Entity
@Table(name = "team")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36)
    private UUID id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "homeland", nullable = false)
    private Country homeland;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @NotNull
    @Column(name = "money", nullable = false)
    private Long money;

    @NotNull
    @Column(name = "points", nullable = false)
    private Long points;

    @NotNull
    @Column(name = "matches", nullable = false)
    private Long matches;

    @NotNull
    @Column(name = "trophies", nullable = false)
    private Long trophies;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "readiness", nullable = false)
    private Integer readiness;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "spirit", nullable = false)
    private Integer spirit;

    @NotNull
    @Column(name = "fans", nullable = false)
    private Integer fans;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "fans_satisfaction", nullable = false)
    private Integer fansSatisfaction;

    @JsonIgnoreProperties(value = { "team" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Stadium stadium;

    @OneToMany(mappedBy = "team")
    @JsonIgnoreProperties(value = { "team" }, allowSetters = true)
    private Set<Player> players = new HashSet<>();

    @OneToMany(mappedBy = "team")
    @JsonIgnoreProperties(value = { "capitan", "playerArranges", "team" }, allowSetters = true)
    private Set<Composition> compositions = new HashSet<>();

    @OneToMany(mappedBy = "team")
    @JsonIgnoreProperties(value = { "stadium", "players", "compositions", "friends", "leagueBoard", "coach", "team" }, allowSetters = true)
    private Set<Team> friends = new HashSet<>();

    @JsonIgnoreProperties(value = { "team", "league" }, allowSetters = true)
    @OneToOne(mappedBy = "team")
    private LeagueBoard leagueBoard;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "teams" }, allowSetters = true)
    private Coach coach;

    @ManyToOne
    @JsonIgnoreProperties(value = { "stadium", "players", "compositions", "friends", "leagueBoard", "coach", "team" }, allowSetters = true)
    private Team team;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Team id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Team name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getHomeland() {
        return this.homeland;
    }

    public Team homeland(Country homeland) {
        this.setHomeland(homeland);
        return this;
    }

    public void setHomeland(Country homeland) {
        this.homeland = homeland;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Team gender(Gender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Long getMoney() {
        return this.money;
    }

    public Team money(Long money) {
        this.setMoney(money);
        return this;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Long getPoints() {
        return this.points;
    }

    public Team points(Long points) {
        this.setPoints(points);
        return this;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public Long getMatches() {
        return this.matches;
    }

    public Team matches(Long matches) {
        this.setMatches(matches);
        return this;
    }

    public void setMatches(Long matches) {
        this.matches = matches;
    }

    public Long getTrophies() {
        return this.trophies;
    }

    public Team trophies(Long trophies) {
        this.setTrophies(trophies);
        return this;
    }

    public void setTrophies(Long trophies) {
        this.trophies = trophies;
    }

    public Integer getReadiness() {
        return this.readiness;
    }

    public Team readiness(Integer readiness) {
        this.setReadiness(readiness);
        return this;
    }

    public void setReadiness(Integer readiness) {
        this.readiness = readiness;
    }

    public Integer getSpirit() {
        return this.spirit;
    }

    public Team spirit(Integer spirit) {
        this.setSpirit(spirit);
        return this;
    }

    public void setSpirit(Integer spirit) {
        this.spirit = spirit;
    }

    public Integer getFans() {
        return this.fans;
    }

    public Team fans(Integer fans) {
        this.setFans(fans);
        return this;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    public Integer getFansSatisfaction() {
        return this.fansSatisfaction;
    }

    public Team fansSatisfaction(Integer fansSatisfaction) {
        this.setFansSatisfaction(fansSatisfaction);
        return this;
    }

    public void setFansSatisfaction(Integer fansSatisfaction) {
        this.fansSatisfaction = fansSatisfaction;
    }

    public Stadium getStadium() {
        return this.stadium;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    public Team stadium(Stadium stadium) {
        this.setStadium(stadium);
        return this;
    }

    public Set<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(Set<Player> players) {
        if (this.players != null) {
            this.players.forEach(i -> i.setTeam(null));
        }
        if (players != null) {
            players.forEach(i -> i.setTeam(this));
        }
        this.players = players;
    }

    public Team players(Set<Player> players) {
        this.setPlayers(players);
        return this;
    }

    public Team addPlayer(Player player) {
        this.players.add(player);
        player.setTeam(this);
        return this;
    }

    public Team removePlayer(Player player) {
        this.players.remove(player);
        player.setTeam(null);
        return this;
    }

    public Set<Composition> getCompositions() {
        return this.compositions;
    }

    public void setCompositions(Set<Composition> compositions) {
        if (this.compositions != null) {
            this.compositions.forEach(i -> i.setTeam(null));
        }
        if (compositions != null) {
            compositions.forEach(i -> i.setTeam(this));
        }
        this.compositions = compositions;
    }

    public Team compositions(Set<Composition> compositions) {
        this.setCompositions(compositions);
        return this;
    }

    public Team addComposition(Composition composition) {
        this.compositions.add(composition);
        composition.setTeam(this);
        return this;
    }

    public Team removeComposition(Composition composition) {
        this.compositions.remove(composition);
        composition.setTeam(null);
        return this;
    }

    public Set<Team> getFriends() {
        return this.friends;
    }

    public void setFriends(Set<Team> teams) {
        if (this.friends != null) {
            this.friends.forEach(i -> i.setTeam(null));
        }
        if (teams != null) {
            teams.forEach(i -> i.setTeam(this));
        }
        this.friends = teams;
    }

    public Team friends(Set<Team> teams) {
        this.setFriends(teams);
        return this;
    }

    public Team addFriends(Team team) {
        this.friends.add(team);
        team.setTeam(this);
        return this;
    }

    public Team removeFriends(Team team) {
        this.friends.remove(team);
        team.setTeam(null);
        return this;
    }

    public LeagueBoard getLeagueBoard() {
        return this.leagueBoard;
    }

    public void setLeagueBoard(LeagueBoard leagueBoard) {
        if (this.leagueBoard != null) {
            this.leagueBoard.setTeam(null);
        }
        if (leagueBoard != null) {
            leagueBoard.setTeam(this);
        }
        this.leagueBoard = leagueBoard;
    }

    public Team leagueBoard(LeagueBoard leagueBoard) {
        this.setLeagueBoard(leagueBoard);
        return this;
    }

    public Coach getCoach() {
        return this.coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public Team coach(Coach coach) {
        this.setCoach(coach);
        return this;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team team(Team team) {
        this.setTeam(team);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Team)) {
            return false;
        }
        return id != null && id.equals(((Team) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Team{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", homeland='" + getHomeland() + "'" +
            ", gender='" + getGender() + "'" +
            ", money=" + getMoney() +
            ", points=" + getPoints() +
            ", matches=" + getMatches() +
            ", trophies=" + getTrophies() +
            ", readiness=" + getReadiness() +
            ", spirit=" + getSpirit() +
            ", fans=" + getFans() +
            ", fansSatisfaction=" + getFansSatisfaction() +
            "}";
    }
}
