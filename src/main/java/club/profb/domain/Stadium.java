package club.profb.domain;

import club.profb.domain.enumeration.Assistant;
import club.profb.domain.enumeration.BodyBuilding;
import club.profb.domain.enumeration.Chair;
import club.profb.domain.enumeration.Doctor;
import club.profb.domain.enumeration.Field;
import club.profb.domain.enumeration.Light;
import club.profb.domain.enumeration.Vehicle;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Type;

/**
 * A Stadium.
 */
@Entity
@Table(name = "stadium")
public class Stadium implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36)
    private UUID id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @NotNull
    @Column(name = "ticket", nullable = false)
    private Integer ticket;

    @Column(name = "leader")
    private Instant leader;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle")
    private Vehicle vehicle;

    @Enumerated(EnumType.STRING)
    @Column(name = "field")
    private Field field;

    @Enumerated(EnumType.STRING)
    @Column(name = "light")
    private Light light;

    @Enumerated(EnumType.STRING)
    @Column(name = "chair")
    private Chair chair;

    @Enumerated(EnumType.STRING)
    @Column(name = "assistant")
    private Assistant assistant;

    @Enumerated(EnumType.STRING)
    @Column(name = "body_building")
    private BodyBuilding bodyBuilding;

    @Enumerated(EnumType.STRING)
    @Column(name = "doctor")
    private Doctor doctor;

    @JsonIgnoreProperties(value = { "stadium", "players", "compositions", "friends", "leagueBoard", "coach", "team" }, allowSetters = true)
    @OneToOne(mappedBy = "stadium")
    private Team team;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Stadium id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Stadium name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return this.capacity;
    }

    public Stadium capacity(Integer capacity) {
        this.setCapacity(capacity);
        return this;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getTicket() {
        return this.ticket;
    }

    public Stadium ticket(Integer ticket) {
        this.setTicket(ticket);
        return this;
    }

    public void setTicket(Integer ticket) {
        this.ticket = ticket;
    }

    public Instant getLeader() {
        return this.leader;
    }

    public Stadium leader(Instant leader) {
        this.setLeader(leader);
        return this;
    }

    public void setLeader(Instant leader) {
        this.leader = leader;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public Stadium vehicle(Vehicle vehicle) {
        this.setVehicle(vehicle);
        return this;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Field getField() {
        return this.field;
    }

    public Stadium field(Field field) {
        this.setField(field);
        return this;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Light getLight() {
        return this.light;
    }

    public Stadium light(Light light) {
        this.setLight(light);
        return this;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    public Chair getChair() {
        return this.chair;
    }

    public Stadium chair(Chair chair) {
        this.setChair(chair);
        return this;
    }

    public void setChair(Chair chair) {
        this.chair = chair;
    }

    public Assistant getAssistant() {
        return this.assistant;
    }

    public Stadium assistant(Assistant assistant) {
        this.setAssistant(assistant);
        return this;
    }

    public void setAssistant(Assistant assistant) {
        this.assistant = assistant;
    }

    public BodyBuilding getBodyBuilding() {
        return this.bodyBuilding;
    }

    public Stadium bodyBuilding(BodyBuilding bodyBuilding) {
        this.setBodyBuilding(bodyBuilding);
        return this;
    }

    public void setBodyBuilding(BodyBuilding bodyBuilding) {
        this.bodyBuilding = bodyBuilding;
    }

    public Doctor getDoctor() {
        return this.doctor;
    }

    public Stadium doctor(Doctor doctor) {
        this.setDoctor(doctor);
        return this;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        if (this.team != null) {
            this.team.setStadium(null);
        }
        if (team != null) {
            team.setStadium(this);
        }
        this.team = team;
    }

    public Stadium team(Team team) {
        this.setTeam(team);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stadium)) {
            return false;
        }
        return id != null && id.equals(((Stadium) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Stadium{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", capacity=" + getCapacity() +
            ", ticket=" + getTicket() +
            ", leader='" + getLeader() + "'" +
            ", vehicle='" + getVehicle() + "'" +
            ", field='" + getField() + "'" +
            ", light='" + getLight() + "'" +
            ", chair='" + getChair() + "'" +
            ", assistant='" + getAssistant() + "'" +
            ", bodyBuilding='" + getBodyBuilding() + "'" +
            ", doctor='" + getDoctor() + "'" +
            "}";
    }
}
