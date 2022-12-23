package club.profb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Type;

/**
 * A PublicChat.
 */
@Entity
@Table(name = "public_chat")
public class PublicChat implements Serializable {

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

    @OneToMany(mappedBy = "publicChat")
    @JsonIgnoreProperties(value = { "coach", "ticket", "privateChat", "publicChat" }, allowSetters = true)
    private Set<Message> messages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public PublicChat id(UUID id) {
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

    public PublicChat from(Team team) {
        this.setFrom(team);
        return this;
    }

    public Team getTu() {
        return this.tu;
    }

    public void setTu(Team team) {
        this.tu = team;
    }

    public PublicChat tu(Team team) {
        this.setTu(team);
        return this;
    }

    public Set<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(Set<Message> messages) {
        if (this.messages != null) {
            this.messages.forEach(i -> i.setPublicChat(null));
        }
        if (messages != null) {
            messages.forEach(i -> i.setPublicChat(this));
        }
        this.messages = messages;
    }

    public PublicChat messages(Set<Message> messages) {
        this.setMessages(messages);
        return this;
    }

    public PublicChat addMessage(Message message) {
        this.messages.add(message);
        message.setPublicChat(this);
        return this;
    }

    public PublicChat removeMessage(Message message) {
        this.messages.remove(message);
        message.setPublicChat(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PublicChat)) {
            return false;
        }
        return id != null && id.equals(((PublicChat) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PublicChat{" +
            "id=" + getId() +
            "}";
    }
}
