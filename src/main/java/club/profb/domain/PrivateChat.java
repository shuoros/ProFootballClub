package club.profb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Type;

/**
 * A PrivateChat.
 */
@Entity
@Table(name = "private_chat")
public class PrivateChat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36)
    private UUID id;

    @JsonIgnoreProperties(value = { "goalScorer", "newsPaper", "matches", "leagueBoards" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private League league;

    @JsonIgnoreProperties(value = { "goalScorer", "newsPaper", "cupBoards" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Cup cup;

    @OneToMany(mappedBy = "privateChat")
    @JsonIgnoreProperties(value = { "coach", "ticket", "privateChat", "publicChat" }, allowSetters = true)
    private Set<Message> messages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public PrivateChat id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public League getLeague() {
        return this.league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public PrivateChat league(League league) {
        this.setLeague(league);
        return this;
    }

    public Cup getCup() {
        return this.cup;
    }

    public void setCup(Cup cup) {
        this.cup = cup;
    }

    public PrivateChat cup(Cup cup) {
        this.setCup(cup);
        return this;
    }

    public Set<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(Set<Message> messages) {
        if (this.messages != null) {
            this.messages.forEach(i -> i.setPrivateChat(null));
        }
        if (messages != null) {
            messages.forEach(i -> i.setPrivateChat(this));
        }
        this.messages = messages;
    }

    public PrivateChat messages(Set<Message> messages) {
        this.setMessages(messages);
        return this;
    }

    public PrivateChat addMessage(Message message) {
        this.messages.add(message);
        message.setPrivateChat(this);
        return this;
    }

    public PrivateChat removeMessage(Message message) {
        this.messages.remove(message);
        message.setPrivateChat(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrivateChat)) {
            return false;
        }
        return id != null && id.equals(((PrivateChat) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrivateChat{" +
            "id=" + getId() +
            "}";
    }
}
