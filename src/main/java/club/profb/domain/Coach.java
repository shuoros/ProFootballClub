package club.profb.domain;

import club.profb.domain.enumeration.Plan;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Type;

/**
 * A Coach.
 */
@Entity
@Table(name = "coach")
public class Coach implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36)
    private UUID id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "banned", nullable = false)
    private Boolean banned;

    @NotNull
    @Column(name = "abandoned", nullable = false)
    private Boolean abandoned;

    @NotNull
    @Column(name = "subscribed", nullable = false)
    private Boolean subscribed;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "plan", nullable = false)
    private Plan plan;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "coach")
    @JsonIgnoreProperties(value = { "stadium", "players", "compositions", "friends", "leagueBoard", "coach", "team" }, allowSetters = true)
    private Set<Team> teams = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Coach id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Coach name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getBanned() {
        return this.banned;
    }

    public Coach banned(Boolean banned) {
        this.setBanned(banned);
        return this;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Boolean getAbandoned() {
        return this.abandoned;
    }

    public Coach abandoned(Boolean abandoned) {
        this.setAbandoned(abandoned);
        return this;
    }

    public void setAbandoned(Boolean abandoned) {
        this.abandoned = abandoned;
    }

    public Boolean getSubscribed() {
        return this.subscribed;
    }

    public Coach subscribed(Boolean subscribed) {
        this.setSubscribed(subscribed);
        return this;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }

    public Plan getPlan() {
        return this.plan;
    }

    public Coach plan(Plan plan) {
        this.setPlan(plan);
        return this;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Coach user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Team> getTeams() {
        return this.teams;
    }

    public void setTeams(Set<Team> teams) {
        if (this.teams != null) {
            this.teams.forEach(i -> i.setCoach(null));
        }
        if (teams != null) {
            teams.forEach(i -> i.setCoach(this));
        }
        this.teams = teams;
    }

    public Coach teams(Set<Team> teams) {
        this.setTeams(teams);
        return this;
    }

    public Coach addTeam(Team team) {
        this.teams.add(team);
        team.setCoach(this);
        return this;
    }

    public Coach removeTeam(Team team) {
        this.teams.remove(team);
        team.setCoach(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Coach)) {
            return false;
        }
        return id != null && id.equals(((Coach) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Coach{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", banned='" + getBanned() + "'" +
            ", abandoned='" + getAbandoned() + "'" +
            ", subscribed='" + getSubscribed() + "'" +
            ", plan='" + getPlan() + "'" +
            "}";
    }
}
