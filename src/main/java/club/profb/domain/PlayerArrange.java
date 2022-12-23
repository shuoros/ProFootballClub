package club.profb.domain;

import club.profb.domain.enumeration.Post;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Type;

/**
 * A PlayerArrange.
 */
@Entity
@Table(name = "player_arrange")
public class PlayerArrange implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "post")
    private Post post;

    @JsonIgnoreProperties(value = { "team" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Player player;

    @ManyToOne
    @JsonIgnoreProperties(value = { "capitan", "playerArranges", "team" }, allowSetters = true)
    private Composition composition;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public PlayerArrange id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Post getPost() {
        return this.post;
    }

    public PlayerArrange post(Post post) {
        this.setPost(post);
        return this;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public PlayerArrange player(Player player) {
        this.setPlayer(player);
        return this;
    }

    public Composition getComposition() {
        return this.composition;
    }

    public void setComposition(Composition composition) {
        this.composition = composition;
    }

    public PlayerArrange composition(Composition composition) {
        this.setComposition(composition);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlayerArrange)) {
            return false;
        }
        return id != null && id.equals(((PlayerArrange) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlayerArrange{" +
            "id=" + getId() +
            ", post='" + getPost() + "'" +
            "}";
    }
}
