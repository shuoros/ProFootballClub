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
 * A Ticket.
 */
@Entity
@Table(name = "ticket")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36)
    private UUID id;

    @NotNull
    @Column(name = "subject", nullable = false)
    private String subject;

    @JsonIgnoreProperties(value = { "stadium", "players", "compositions", "friends", "leagueBoard", "coach", "team" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Team coach;

    @OneToMany(mappedBy = "ticket")
    @JsonIgnoreProperties(value = { "coach", "ticket", "privateChat", "publicChat" }, allowSetters = true)
    private Set<Message> messages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Ticket id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSubject() {
        return this.subject;
    }

    public Ticket subject(String subject) {
        this.setSubject(subject);
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Team getCoach() {
        return this.coach;
    }

    public void setCoach(Team team) {
        this.coach = team;
    }

    public Ticket coach(Team team) {
        this.setCoach(team);
        return this;
    }

    public Set<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(Set<Message> messages) {
        if (this.messages != null) {
            this.messages.forEach(i -> i.setTicket(null));
        }
        if (messages != null) {
            messages.forEach(i -> i.setTicket(this));
        }
        this.messages = messages;
    }

    public Ticket messages(Set<Message> messages) {
        this.setMessages(messages);
        return this;
    }

    public Ticket addMessages(Message message) {
        this.messages.add(message);
        message.setTicket(this);
        return this;
    }

    public Ticket removeMessages(Message message) {
        this.messages.remove(message);
        message.setTicket(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ticket)) {
            return false;
        }
        return id != null && id.equals(((Ticket) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ticket{" +
            "id=" + getId() +
            ", subject='" + getSubject() + "'" +
            "}";
    }
}
