package club.profb.domain;

import club.profb.domain.enumeration.CupType;
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
 * A Cup.
 */
@Entity
@Table(name = "cup")
public class Cup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36)
    private UUID id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CupType type;

    @Column(name = "events")
    private String events;

    @Column(name = "finished")
    private Boolean finished;

    @NotNull
    @Column(name = "entrance", nullable = false)
    private Integer entrance;

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

    @OneToMany(mappedBy = "cup")
    @JsonIgnoreProperties(value = { "matches", "cup" }, allowSetters = true)
    private Set<CupBoard> cupBoards = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Cup id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CupType getType() {
        return this.type;
    }

    public Cup type(CupType type) {
        this.setType(type);
        return this;
    }

    public void setType(CupType type) {
        this.type = type;
    }

    public String getEvents() {
        return this.events;
    }

    public Cup events(String events) {
        this.setEvents(events);
        return this;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    public Boolean getFinished() {
        return this.finished;
    }

    public Cup finished(Boolean finished) {
        this.setFinished(finished);
        return this;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Integer getEntrance() {
        return this.entrance;
    }

    public Cup entrance(Integer entrance) {
        this.setEntrance(entrance);
        return this;
    }

    public void setEntrance(Integer entrance) {
        this.entrance = entrance;
    }

    public Instant getStart() {
        return this.start;
    }

    public Cup start(Instant start) {
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

    public Cup goalScorer(Player player) {
        this.setGoalScorer(player);
        return this;
    }

    public NewsPaper getNewsPaper() {
        return this.newsPaper;
    }

    public void setNewsPaper(NewsPaper newsPaper) {
        this.newsPaper = newsPaper;
    }

    public Cup newsPaper(NewsPaper newsPaper) {
        this.setNewsPaper(newsPaper);
        return this;
    }

    public Set<CupBoard> getCupBoards() {
        return this.cupBoards;
    }

    public void setCupBoards(Set<CupBoard> cupBoards) {
        if (this.cupBoards != null) {
            this.cupBoards.forEach(i -> i.setCup(null));
        }
        if (cupBoards != null) {
            cupBoards.forEach(i -> i.setCup(this));
        }
        this.cupBoards = cupBoards;
    }

    public Cup cupBoards(Set<CupBoard> cupBoards) {
        this.setCupBoards(cupBoards);
        return this;
    }

    public Cup addCupBoard(CupBoard cupBoard) {
        this.cupBoards.add(cupBoard);
        cupBoard.setCup(this);
        return this;
    }

    public Cup removeCupBoard(CupBoard cupBoard) {
        this.cupBoards.remove(cupBoard);
        cupBoard.setCup(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cup)) {
            return false;
        }
        return id != null && id.equals(((Cup) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cup{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", events='" + getEvents() + "'" +
            ", finished='" + getFinished() + "'" +
            ", entrance=" + getEntrance() +
            ", start='" + getStart() + "'" +
            "}";
    }
}
