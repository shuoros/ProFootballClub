package club.profb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import club.profb.IntegrationTest;
import club.profb.domain.League;
import club.profb.repository.LeagueRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LeagueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeagueResourceIT {

    private static final Integer DEFAULT_CLAZZ = 1;
    private static final Integer UPDATED_CLAZZ = 2;

    private static final String DEFAULT_EVENTS = "AAAAAAAAAA";
    private static final String UPDATED_EVENTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FINISHED = false;
    private static final Boolean UPDATED_FINISHED = true;

    private static final Instant DEFAULT_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/leagues";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeagueMockMvc;

    private League league;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static League createEntity(EntityManager em) {
        League league = new League().clazz(DEFAULT_CLAZZ).events(DEFAULT_EVENTS).finished(DEFAULT_FINISHED).start(DEFAULT_START);
        return league;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static League createUpdatedEntity(EntityManager em) {
        League league = new League().clazz(UPDATED_CLAZZ).events(UPDATED_EVENTS).finished(UPDATED_FINISHED).start(UPDATED_START);
        return league;
    }

    @BeforeEach
    public void initTest() {
        league = createEntity(em);
    }

    @Test
    @Transactional
    void createLeague() throws Exception {
        int databaseSizeBeforeCreate = leagueRepository.findAll().size();
        // Create the League
        restLeagueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(league)))
            .andExpect(status().isCreated());

        // Validate the League in the database
        List<League> leagueList = leagueRepository.findAll();
        assertThat(leagueList).hasSize(databaseSizeBeforeCreate + 1);
        League testLeague = leagueList.get(leagueList.size() - 1);
        assertThat(testLeague.getClazz()).isEqualTo(DEFAULT_CLAZZ);
        assertThat(testLeague.getEvents()).isEqualTo(DEFAULT_EVENTS);
        assertThat(testLeague.getFinished()).isEqualTo(DEFAULT_FINISHED);
        assertThat(testLeague.getStart()).isEqualTo(DEFAULT_START);
    }

    @Test
    @Transactional
    void createLeagueWithExistingId() throws Exception {
        // Create the League with an existing ID
        leagueRepository.saveAndFlush(league);

        int databaseSizeBeforeCreate = leagueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeagueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(league)))
            .andExpect(status().isBadRequest());

        // Validate the League in the database
        List<League> leagueList = leagueRepository.findAll();
        assertThat(leagueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkClazzIsRequired() throws Exception {
        int databaseSizeBeforeTest = leagueRepository.findAll().size();
        // set the field null
        league.setClazz(null);

        // Create the League, which fails.

        restLeagueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(league)))
            .andExpect(status().isBadRequest());

        List<League> leagueList = leagueRepository.findAll();
        assertThat(leagueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeagues() throws Exception {
        // Initialize the database
        leagueRepository.saveAndFlush(league);

        // Get all the leagueList
        restLeagueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(league.getId().toString())))
            .andExpect(jsonPath("$.[*].clazz").value(hasItem(DEFAULT_CLAZZ)))
            .andExpect(jsonPath("$.[*].events").value(hasItem(DEFAULT_EVENTS)))
            .andExpect(jsonPath("$.[*].finished").value(hasItem(DEFAULT_FINISHED.booleanValue())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())));
    }

    @Test
    @Transactional
    void getLeague() throws Exception {
        // Initialize the database
        leagueRepository.saveAndFlush(league);

        // Get the league
        restLeagueMockMvc
            .perform(get(ENTITY_API_URL_ID, league.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(league.getId().toString()))
            .andExpect(jsonPath("$.clazz").value(DEFAULT_CLAZZ))
            .andExpect(jsonPath("$.events").value(DEFAULT_EVENTS))
            .andExpect(jsonPath("$.finished").value(DEFAULT_FINISHED.booleanValue()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()));
    }

    @Test
    @Transactional
    void getNonExistingLeague() throws Exception {
        // Get the league
        restLeagueMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLeague() throws Exception {
        // Initialize the database
        leagueRepository.saveAndFlush(league);

        int databaseSizeBeforeUpdate = leagueRepository.findAll().size();

        // Update the league
        League updatedLeague = leagueRepository.findById(league.getId()).get();
        // Disconnect from session so that the updates on updatedLeague are not directly saved in db
        em.detach(updatedLeague);
        updatedLeague.clazz(UPDATED_CLAZZ).events(UPDATED_EVENTS).finished(UPDATED_FINISHED).start(UPDATED_START);

        restLeagueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeague.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLeague))
            )
            .andExpect(status().isOk());

        // Validate the League in the database
        List<League> leagueList = leagueRepository.findAll();
        assertThat(leagueList).hasSize(databaseSizeBeforeUpdate);
        League testLeague = leagueList.get(leagueList.size() - 1);
        assertThat(testLeague.getClazz()).isEqualTo(UPDATED_CLAZZ);
        assertThat(testLeague.getEvents()).isEqualTo(UPDATED_EVENTS);
        assertThat(testLeague.getFinished()).isEqualTo(UPDATED_FINISHED);
        assertThat(testLeague.getStart()).isEqualTo(UPDATED_START);
    }

    @Test
    @Transactional
    void putNonExistingLeague() throws Exception {
        int databaseSizeBeforeUpdate = leagueRepository.findAll().size();
        league.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeagueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, league.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(league))
            )
            .andExpect(status().isBadRequest());

        // Validate the League in the database
        List<League> leagueList = leagueRepository.findAll();
        assertThat(leagueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeague() throws Exception {
        int databaseSizeBeforeUpdate = leagueRepository.findAll().size();
        league.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeagueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(league))
            )
            .andExpect(status().isBadRequest());

        // Validate the League in the database
        List<League> leagueList = leagueRepository.findAll();
        assertThat(leagueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeague() throws Exception {
        int databaseSizeBeforeUpdate = leagueRepository.findAll().size();
        league.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeagueMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(league)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the League in the database
        List<League> leagueList = leagueRepository.findAll();
        assertThat(leagueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeagueWithPatch() throws Exception {
        // Initialize the database
        leagueRepository.saveAndFlush(league);

        int databaseSizeBeforeUpdate = leagueRepository.findAll().size();

        // Update the league using partial update
        League partialUpdatedLeague = new League();
        partialUpdatedLeague.setId(league.getId());

        partialUpdatedLeague.finished(UPDATED_FINISHED).start(UPDATED_START);

        restLeagueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeague.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeague))
            )
            .andExpect(status().isOk());

        // Validate the League in the database
        List<League> leagueList = leagueRepository.findAll();
        assertThat(leagueList).hasSize(databaseSizeBeforeUpdate);
        League testLeague = leagueList.get(leagueList.size() - 1);
        assertThat(testLeague.getClazz()).isEqualTo(DEFAULT_CLAZZ);
        assertThat(testLeague.getEvents()).isEqualTo(DEFAULT_EVENTS);
        assertThat(testLeague.getFinished()).isEqualTo(UPDATED_FINISHED);
        assertThat(testLeague.getStart()).isEqualTo(UPDATED_START);
    }

    @Test
    @Transactional
    void fullUpdateLeagueWithPatch() throws Exception {
        // Initialize the database
        leagueRepository.saveAndFlush(league);

        int databaseSizeBeforeUpdate = leagueRepository.findAll().size();

        // Update the league using partial update
        League partialUpdatedLeague = new League();
        partialUpdatedLeague.setId(league.getId());

        partialUpdatedLeague.clazz(UPDATED_CLAZZ).events(UPDATED_EVENTS).finished(UPDATED_FINISHED).start(UPDATED_START);

        restLeagueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeague.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeague))
            )
            .andExpect(status().isOk());

        // Validate the League in the database
        List<League> leagueList = leagueRepository.findAll();
        assertThat(leagueList).hasSize(databaseSizeBeforeUpdate);
        League testLeague = leagueList.get(leagueList.size() - 1);
        assertThat(testLeague.getClazz()).isEqualTo(UPDATED_CLAZZ);
        assertThat(testLeague.getEvents()).isEqualTo(UPDATED_EVENTS);
        assertThat(testLeague.getFinished()).isEqualTo(UPDATED_FINISHED);
        assertThat(testLeague.getStart()).isEqualTo(UPDATED_START);
    }

    @Test
    @Transactional
    void patchNonExistingLeague() throws Exception {
        int databaseSizeBeforeUpdate = leagueRepository.findAll().size();
        league.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeagueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, league.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(league))
            )
            .andExpect(status().isBadRequest());

        // Validate the League in the database
        List<League> leagueList = leagueRepository.findAll();
        assertThat(leagueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeague() throws Exception {
        int databaseSizeBeforeUpdate = leagueRepository.findAll().size();
        league.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeagueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(league))
            )
            .andExpect(status().isBadRequest());

        // Validate the League in the database
        List<League> leagueList = leagueRepository.findAll();
        assertThat(leagueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeague() throws Exception {
        int databaseSizeBeforeUpdate = leagueRepository.findAll().size();
        league.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeagueMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(league)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the League in the database
        List<League> leagueList = leagueRepository.findAll();
        assertThat(leagueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeague() throws Exception {
        // Initialize the database
        leagueRepository.saveAndFlush(league);

        int databaseSizeBeforeDelete = leagueRepository.findAll().size();

        // Delete the league
        restLeagueMockMvc
            .perform(delete(ENTITY_API_URL_ID, league.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<League> leagueList = leagueRepository.findAll();
        assertThat(leagueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
