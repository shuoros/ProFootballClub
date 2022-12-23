package club.profb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Type;

/**
 * A Message.
 */
@Entity
@Table(name = "message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36)
    private UUID id;

    @NotNull
    @Column(name = "message", nullable = false)
    private String message;

    @JsonIgnoreProperties(value = { "stadium", "players", "compositions", "friends", "leagueBoard", "coach", "team" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Team coach;

    @ManyToOne
    @JsonIgnoreProperties(value = { "coach", "messages" }, allowSetters = true)
    private Ticket ticket;

    @ManyToOne
    @JsonIgnoreProperties(value = { "league", "cup", "messages" }, allowSetters = true)
    private PrivateChat privateChat;

    @ManyToOne
    @JsonIgnoreProperties(value = { "from", "tu", "messages" }, allowSetters = true)
    private PublicChat publicChat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Message id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }

    public Message message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Team getCoach() {
        return this.coach;
    }

    public void setCoach(Team team) {
        this.coach = team;
    }

    public Message coach(Team team) {
        this.setCoach(team);
        return this;
    }

    public Ticket getTicket() {
        return this.ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Message ticket(Ticket ticket) {
        this.setTicket(ticket);
        return this;
    }

    public PrivateChat getPrivateChat() {
        return this.privateChat;
    }

    public void setPrivateChat(PrivateChat privateChat) {
        this.privateChat = privateChat;
    }

    public Message privateChat(PrivateChat privateChat) {
        this.setPrivateChat(privateChat);
        return this;
    }

    public PublicChat getPublicChat() {
        return this.publicChat;
    }

    public void setPublicChat(PublicChat publicChat) {
        this.publicChat = publicChat;
    }

    public Message publicChat(PublicChat publicChat) {
        this.setPublicChat(publicChat);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        return id != null && id.equals(((Message) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
