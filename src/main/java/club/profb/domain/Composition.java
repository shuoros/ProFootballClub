package club.profb.domain;

import club.profb.domain.enumeration.Arrange;
import club.profb.domain.enumeration.Strategy;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Type;

/**
 * A Composition.
 */
@Entity
@Table(name = "composition")
public class Composition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36)
    private UUID id;

    @NotNull
    @Column(name = "d_3_fault", nullable = false)
    private Boolean d3fault;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "arrange", nullable = false)
    private Arrange arrange;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "strategy", nullable = false)
    private Strategy strategy;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "defence", nullable = false)
    private Integer defence;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "short_pass", nullable = false)
    private Integer shortPass;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "violence", nullable = false)
    private Integer violence;

    @JsonIgnoreProperties(value = { "team" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Player capitan;

    @OneToMany(mappedBy = "composition")
    @JsonIgnoreProperties(value = { "player", "composition" }, allowSetters = true)
    private Set<PlayerArrange> playerArranges = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "stadium", "players", "compositions", "friends", "leagueBoard", "coach", "team" }, allowSetters = true)
    private Team team;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Composition id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Boolean getd3fault() {
        return this.d3fault;
    }

    public Composition d3fault(Boolean d3fault) {
        this.setd3fault(d3fault);
        return this;
    }

    public void setd3fault(Boolean d3fault) {
        this.d3fault = d3fault;
    }

    public Arrange getArrange() {
        return this.arrange;
    }

    public Composition arrange(Arrange arrange) {
        this.setArrange(arrange);
        return this;
    }

    public void setArrange(Arrange arrange) {
        this.arrange = arrange;
    }

    public Strategy getStrategy() {
        return this.strategy;
    }

    public Composition strategy(Strategy strategy) {
        this.setStrategy(strategy);
        return this;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Integer getDefence() {
        return this.defence;
    }

    public Composition defence(Integer defence) {
        this.setDefence(defence);
        return this;
    }

    public void setDefence(Integer defence) {
        this.defence = defence;
    }

    public Integer getShortPass() {
        return this.shortPass;
    }

    public Composition shortPass(Integer shortPass) {
        this.setShortPass(shortPass);
        return this;
    }

    public void setShortPass(Integer shortPass) {
        this.shortPass = shortPass;
    }

    public Integer getViolence() {
        return this.violence;
    }

    public Composition violence(Integer violence) {
        this.setViolence(violence);
        return this;
    }

    public void setViolence(Integer violence) {
        this.violence = violence;
    }

    public Player getCapitan() {
        return this.capitan;
    }

    public void setCapitan(Player player) {
        this.capitan = player;
    }

    public Composition capitan(Player player) {
        this.setCapitan(player);
        return this;
    }

    public Set<PlayerArrange> getPlayerArranges() {
        return this.playerArranges;
    }

    public void setPlayerArranges(Set<PlayerArrange> playerArranges) {
        if (this.playerArranges != null) {
            this.playerArranges.forEach(i -> i.setComposition(null));
        }
        if (playerArranges != null) {
            playerArranges.forEach(i -> i.setComposition(this));
        }
        this.playerArranges = playerArranges;
    }

    public Composition playerArranges(Set<PlayerArrange> playerArranges) {
        this.setPlayerArranges(playerArranges);
        return this;
    }

    public Composition addPlayerArranges(PlayerArrange playerArrange) {
        this.playerArranges.add(playerArrange);
        playerArrange.setComposition(this);
        return this;
    }

    public Composition removePlayerArranges(PlayerArrange playerArrange) {
        this.playerArranges.remove(playerArrange);
        playerArrange.setComposition(null);
        return this;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Composition team(Team team) {
        this.setTeam(team);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Composition)) {
            return false;
        }
        return id != null && id.equals(((Composition) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Composition{" +
            "id=" + getId() +
            ", d3fault='" + getd3fault() + "'" +
            ", arrange='" + getArrange() + "'" +
            ", strategy='" + getStrategy() + "'" +
            ", defence=" + getDefence() +
            ", shortPass=" + getShortPass() +
            ", violence=" + getViolence() +
            "}";
    }
}
