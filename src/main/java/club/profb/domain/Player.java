package club.profb.domain;

import club.profb.domain.enumeration.Country;
import club.profb.domain.enumeration.Gender;
import club.profb.domain.enumeration.PlayerStatus;
import club.profb.domain.enumeration.Post;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Type;

/**
 * A Player.
 */
@Entity
@Table(name = "player")
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36)
    private UUID id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "country", nullable = false)
    private Country country;

    @NotNull
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PlayerStatus status;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "post", nullable = false)
    private Post post;

    @NotNull
    @Min(value = 0)
    @Max(value = 256)
    @Column(name = "total_power", nullable = false)
    private Integer totalPower;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "goalkeeping", nullable = false)
    private Integer goalkeeping;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "defence", nullable = false)
    private Integer defence;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "tackling", nullable = false)
    private Integer tackling;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "passing", nullable = false)
    private Integer passing;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "team_skill", nullable = false)
    private Integer teamSkill;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "ball_skill", nullable = false)
    private Integer ballSkill;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "shooting", nullable = false)
    private Integer shooting;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "long_shooting", nullable = false)
    private Integer longShooting;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "dribbling", nullable = false)
    private Integer dribbling;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "technique", nullable = false)
    private Integer technique;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "confidence", nullable = false)
    private Integer confidence;

    @ManyToOne
    @JsonIgnoreProperties(value = { "stadium", "players", "compositions", "friends", "leagueBoard", "coach", "team" }, allowSetters = true)
    private Team team;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Player id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Player firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Player lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Player gender(Gender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Country getCountry() {
        return this.country;
    }

    public Player country(Country country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Integer getAge() {
        return this.age;
    }

    public Player age(Integer age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public PlayerStatus getStatus() {
        return this.status;
    }

    public Player status(PlayerStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    public Post getPost() {
        return this.post;
    }

    public Player post(Post post) {
        this.setPost(post);
        return this;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Integer getTotalPower() {
        return this.totalPower;
    }

    public Player totalPower(Integer totalPower) {
        this.setTotalPower(totalPower);
        return this;
    }

    public void setTotalPower(Integer totalPower) {
        this.totalPower = totalPower;
    }

    public Integer getGoalkeeping() {
        return this.goalkeeping;
    }

    public Player goalkeeping(Integer goalkeeping) {
        this.setGoalkeeping(goalkeeping);
        return this;
    }

    public void setGoalkeeping(Integer goalkeeping) {
        this.goalkeeping = goalkeeping;
    }

    public Integer getDefence() {
        return this.defence;
    }

    public Player defence(Integer defence) {
        this.setDefence(defence);
        return this;
    }

    public void setDefence(Integer defence) {
        this.defence = defence;
    }

    public Integer getTackling() {
        return this.tackling;
    }

    public Player tackling(Integer tackling) {
        this.setTackling(tackling);
        return this;
    }

    public void setTackling(Integer tackling) {
        this.tackling = tackling;
    }

    public Integer getPassing() {
        return this.passing;
    }

    public Player passing(Integer passing) {
        this.setPassing(passing);
        return this;
    }

    public void setPassing(Integer passing) {
        this.passing = passing;
    }

    public Integer getTeamSkill() {
        return this.teamSkill;
    }

    public Player teamSkill(Integer teamSkill) {
        this.setTeamSkill(teamSkill);
        return this;
    }

    public void setTeamSkill(Integer teamSkill) {
        this.teamSkill = teamSkill;
    }

    public Integer getBallSkill() {
        return this.ballSkill;
    }

    public Player ballSkill(Integer ballSkill) {
        this.setBallSkill(ballSkill);
        return this;
    }

    public void setBallSkill(Integer ballSkill) {
        this.ballSkill = ballSkill;
    }

    public Integer getShooting() {
        return this.shooting;
    }

    public Player shooting(Integer shooting) {
        this.setShooting(shooting);
        return this;
    }

    public void setShooting(Integer shooting) {
        this.shooting = shooting;
    }

    public Integer getLongShooting() {
        return this.longShooting;
    }

    public Player longShooting(Integer longShooting) {
        this.setLongShooting(longShooting);
        return this;
    }

    public void setLongShooting(Integer longShooting) {
        this.longShooting = longShooting;
    }

    public Integer getDribbling() {
        return this.dribbling;
    }

    public Player dribbling(Integer dribbling) {
        this.setDribbling(dribbling);
        return this;
    }

    public void setDribbling(Integer dribbling) {
        this.dribbling = dribbling;
    }

    public Integer getTechnique() {
        return this.technique;
    }

    public Player technique(Integer technique) {
        this.setTechnique(technique);
        return this;
    }

    public void setTechnique(Integer technique) {
        this.technique = technique;
    }

    public Integer getConfidence() {
        return this.confidence;
    }

    public Player confidence(Integer confidence) {
        this.setConfidence(confidence);
        return this;
    }

    public void setConfidence(Integer confidence) {
        this.confidence = confidence;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Player team(Team team) {
        this.setTeam(team);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Player)) {
            return false;
        }
        return id != null && id.equals(((Player) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Player{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", gender='" + getGender() + "'" +
            ", country='" + getCountry() + "'" +
            ", age=" + getAge() +
            ", status='" + getStatus() + "'" +
            ", post='" + getPost() + "'" +
            ", totalPower=" + getTotalPower() +
            ", goalkeeping=" + getGoalkeeping() +
            ", defence=" + getDefence() +
            ", tackling=" + getTackling() +
            ", passing=" + getPassing() +
            ", teamSkill=" + getTeamSkill() +
            ", ballSkill=" + getBallSkill() +
            ", shooting=" + getShooting() +
            ", longShooting=" + getLongShooting() +
            ", dribbling=" + getDribbling() +
            ", technique=" + getTechnique() +
            ", confidence=" + getConfidence() +
            "}";
    }
}
