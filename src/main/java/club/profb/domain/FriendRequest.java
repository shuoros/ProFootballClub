package club.profb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Type;

/**
 * A FriendRequest.
 */
@Entity
@Table(name = "friend_request")
public class FriendRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36)
    private UUID id;

    @JsonIgnoreProperties(value = { "stadium", "players", "compositions", "friends", "leagueBoard", "coach", "team" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Team from;

    @JsonIgnoreProperties(value = { "stadium", "players", "compositions", "friends", "leagueBoard", "coach", "team" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Team tu;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public FriendRequest id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Team getFrom() {
        return this.from;
    }

    public void setFrom(Team team) {
        this.from = team;
    }

    public FriendRequest from(Team team) {
        this.setFrom(team);
        return this;
    }

    public Team getTu() {
        return this.tu;
    }

    public void setTu(Team team) {
        this.tu = team;
    }

    public FriendRequest tu(Team team) {
        this.setTu(team);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FriendRequest)) {
            return false;
        }
        return id != null && id.equals(((FriendRequest) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FriendRequest{" +
            "id=" + getId() +
            "}";
    }
}
