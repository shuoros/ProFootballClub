package club.profb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Type;

/**
 * A NewsPaper.
 */
@Entity
@Table(name = "news_paper")
public class NewsPaper implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36)
    private UUID id;

    @NotNull
    @Column(name = "news", nullable = false)
    private String news;

    @JsonIgnoreProperties(value = { "goalScorer", "newsPaper", "matches", "leagueBoards" }, allowSetters = true)
    @OneToOne(mappedBy = "newsPaper")
    private League league;

    @JsonIgnoreProperties(value = { "goalScorer", "newsPaper", "cupBoards" }, allowSetters = true)
    @OneToOne(mappedBy = "newsPaper")
    private Cup cup;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public NewsPaper id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNews() {
        return this.news;
    }

    public NewsPaper news(String news) {
        this.setNews(news);
        return this;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public League getLeague() {
        return this.league;
    }

    public void setLeague(League league) {
        if (this.league != null) {
            this.league.setNewsPaper(null);
        }
        if (league != null) {
            league.setNewsPaper(this);
        }
        this.league = league;
    }

    public NewsPaper league(League league) {
        this.setLeague(league);
        return this;
    }

    public Cup getCup() {
        return this.cup;
    }

    public void setCup(Cup cup) {
        if (this.cup != null) {
            this.cup.setNewsPaper(null);
        }
        if (cup != null) {
            cup.setNewsPaper(this);
        }
        this.cup = cup;
    }

    public NewsPaper cup(Cup cup) {
        this.setCup(cup);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NewsPaper)) {
            return false;
        }
        return id != null && id.equals(((NewsPaper) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NewsPaper{" +
            "id=" + getId() +
            ", news='" + getNews() + "'" +
            "}";
    }
}
