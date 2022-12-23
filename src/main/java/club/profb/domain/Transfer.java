package club.profb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Type;

/**
 * A Transfer.
 */
@Entity
@Table(name = "transfer")
public class Transfer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36)
    private UUID id;

    @NotNull
    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "password")
    private String password;

    @Column(name = "bought")
    private Boolean bought;

    @JsonIgnoreProperties(value = { "team" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Player player;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Transfer id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getPrice() {
        return this.price;
    }

    public Transfer price(Long price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getPassword() {
        return this.password;
    }

    public Transfer password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getBought() {
        return this.bought;
    }

    public Transfer bought(Boolean bought) {
        this.setBought(bought);
        return this;
    }

    public void setBought(Boolean bought) {
        this.bought = bought;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Transfer player(Player player) {
        this.setPlayer(player);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transfer)) {
            return false;
        }
        return id != null && id.equals(((Transfer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transfer{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", password='" + getPassword() + "'" +
            ", bought='" + getBought() + "'" +
            "}";
    }
}
