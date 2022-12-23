package club.profb.domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Type;

/**
 * A Variables.
 */
@Entity
@Table(name = "variables")
public class Variables implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36)
    private UUID id;

    @Column(name = "league_first_place_prize")
    private Integer leagueFirstPlacePrize;

    @Column(name = "league_second_place_prize")
    private Integer leagueSecondPlacePrize;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Variables id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getLeagueFirstPlacePrize() {
        return this.leagueFirstPlacePrize;
    }

    public Variables leagueFirstPlacePrize(Integer leagueFirstPlacePrize) {
        this.setLeagueFirstPlacePrize(leagueFirstPlacePrize);
        return this;
    }

    public void setLeagueFirstPlacePrize(Integer leagueFirstPlacePrize) {
        this.leagueFirstPlacePrize = leagueFirstPlacePrize;
    }

    public Integer getLeagueSecondPlacePrize() {
        return this.leagueSecondPlacePrize;
    }

    public Variables leagueSecondPlacePrize(Integer leagueSecondPlacePrize) {
        this.setLeagueSecondPlacePrize(leagueSecondPlacePrize);
        return this;
    }

    public void setLeagueSecondPlacePrize(Integer leagueSecondPlacePrize) {
        this.leagueSecondPlacePrize = leagueSecondPlacePrize;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Variables)) {
            return false;
        }
        return id != null && id.equals(((Variables) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Variables{" +
            "id=" + getId() +
            ", leagueFirstPlacePrize=" + getLeagueFirstPlacePrize() +
            ", leagueSecondPlacePrize=" + getLeagueSecondPlacePrize() +
            "}";
    }
}
