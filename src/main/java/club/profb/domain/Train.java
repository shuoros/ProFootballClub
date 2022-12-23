package club.profb.domain;

import club.profb.domain.enumeration.Training;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Type;

/**
 * A Train.
 */
@Entity
@Table(name = "train")
public class Train implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "training")
    private Training training;

    @Column(name = "finishes")
    private Instant finishes;

    @JsonIgnoreProperties(value = { "team" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Player player;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Train id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Training getTraining() {
        return this.training;
    }

    public Train training(Training training) {
        this.setTraining(training);
        return this;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public Instant getFinishes() {
        return this.finishes;
    }

    public Train finishes(Instant finishes) {
        this.setFinishes(finishes);
        return this;
    }

    public void setFinishes(Instant finishes) {
        this.finishes = finishes;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Train player(Player player) {
        this.setPlayer(player);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Train)) {
            return false;
        }
        return id != null && id.equals(((Train) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Train{" +
            "id=" + getId() +
            ", training='" + getTraining() + "'" +
            ", finishes='" + getFinishes() + "'" +
            "}";
    }
}
