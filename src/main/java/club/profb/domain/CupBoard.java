package club.profb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Type;

/**
 * A CupBoard.
 */
@Entity
@Table(name = "cup_board")
public class CupBoard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36)
    private UUID id;

    @NotNull
    @Min(value = 1)
    @Max(value = 12)
    @Column(name = "level", nullable = false)
    private Integer level;

    @OneToMany(mappedBy = "cupBoard")
    @JsonIgnoreProperties(
        value = { "host", "guest", "hostComposition", "guestComposition", "bestPlayer", "league", "cupBoard" },
        allowSetters = true
    )
    private Set<Match> matches = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "goalScorer", "newsPaper", "cupBoards" }, allowSetters = true)
    private Cup cup;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public CupBoard id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getLevel() {
        return this.level;
    }

    public CupBoard level(Integer level) {
        this.setLevel(level);
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Set<Match> getMatches() {
        return this.matches;
    }

    public void setMatches(Set<Match> matches) {
        if (this.matches != null) {
            this.matches.forEach(i -> i.setCupBoard(null));
        }
        if (matches != null) {
            matches.forEach(i -> i.setCupBoard(this));
        }
        this.matches = matches;
    }

    public CupBoard matches(Set<Match> matches) {
        this.setMatches(matches);
        return this;
    }

    public CupBoard addMatch(Match match) {
        this.matches.add(match);
        match.setCupBoard(this);
        return this;
    }

    public CupBoard removeMatch(Match match) {
        this.matches.remove(match);
        match.setCupBoard(null);
        return this;
    }

    public Cup getCup() {
        return this.cup;
    }

    public void setCup(Cup cup) {
        this.cup = cup;
    }

    public CupBoard cup(Cup cup) {
        this.setCup(cup);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CupBoard)) {
            return false;
        }
        return id != null && id.equals(((CupBoard) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CupBoard{" +
            "id=" + getId() +
            ", level=" + getLevel() +
            "}";
    }
}
