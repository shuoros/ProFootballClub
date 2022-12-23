package club.profb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import club.profb.IntegrationTest;
import club.profb.domain.Team;
import club.profb.domain.enumeration.Country;
import club.profb.domain.enumeration.Gender;
import club.profb.repository.TeamRepository;
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
 * Integration tests for the {@link TeamResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TeamResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Country DEFAULT_HOMELAND = Country.MULTY_CULTI;
    private static final Country UPDATED_HOMELAND = Country.UK;

    private static final Gender DEFAULT_GENDER = Gender.DIVERSE;
    private static final Gender UPDATED_GENDER = Gender.MALE;

    private static final Long DEFAULT_MONEY = 1L;
    private static final Long UPDATED_MONEY = 2L;

    private static final Long DEFAULT_POINTS = 1L;
    private static final Long UPDATED_POINTS = 2L;

    private static final Long DEFAULT_MATCHES = 1L;
    private static final Long UPDATED_MATCHES = 2L;

    private static final Long DEFAULT_TROPHIES = 1L;
    private static final Long UPDATED_TROPHIES = 2L;

    private static final Integer DEFAULT_READINESS = 0;
    private static final Integer UPDATED_READINESS = 1;

    private static final Integer DEFAULT_SPIRIT = 0;
    private static final Integer UPDATED_SPIRIT = 1;

    private static final Integer DEFAULT_FANS = 1;
    private static final Integer UPDATED_FANS = 2;

    private static final Integer DEFAULT_FANS_SATISFACTION = 0;
    private static final Integer UPDATED_FANS_SATISFACTION = 1;

    private static final String ENTITY_API_URL = "/api/teams";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTeamMockMvc;

    private Team team;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Team createEntity(EntityManager em) {
        Team team = new Team()
            .name(DEFAULT_NAME)
            .homeland(DEFAULT_HOMELAND)
            .gender(DEFAULT_GENDER)
            .money(DEFAULT_MONEY)
            .points(DEFAULT_POINTS)
            .matches(DEFAULT_MATCHES)
            .trophies(DEFAULT_TROPHIES)
            .readiness(DEFAULT_READINESS)
            .spirit(DEFAULT_SPIRIT)
            .fans(DEFAULT_FANS)
            .fansSatisfaction(DEFAULT_FANS_SATISFACTION);
        return team;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Team createUpdatedEntity(EntityManager em) {
        Team team = new Team()
            .name(UPDATED_NAME)
            .homeland(UPDATED_HOMELAND)
            .gender(UPDATED_GENDER)
            .money(UPDATED_MONEY)
            .points(UPDATED_POINTS)
            .matches(UPDATED_MATCHES)
            .trophies(UPDATED_TROPHIES)
            .readiness(UPDATED_READINESS)
            .spirit(UPDATED_SPIRIT)
            .fans(UPDATED_FANS)
            .fansSatisfaction(UPDATED_FANS_SATISFACTION);
        return team;
    }

    @BeforeEach
    public void initTest() {
        team = createEntity(em);
    }

    @Test
    @Transactional
    void createTeam() throws Exception {
        int databaseSizeBeforeCreate = teamRepository.findAll().size();
        // Create the Team
        restTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isCreated());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeCreate + 1);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTeam.getHomeland()).isEqualTo(DEFAULT_HOMELAND);
        assertThat(testTeam.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testTeam.getMoney()).isEqualTo(DEFAULT_MONEY);
        assertThat(testTeam.getPoints()).isEqualTo(DEFAULT_POINTS);
        assertThat(testTeam.getMatches()).isEqualTo(DEFAULT_MATCHES);
        assertThat(testTeam.getTrophies()).isEqualTo(DEFAULT_TROPHIES);
        assertThat(testTeam.getReadiness()).isEqualTo(DEFAULT_READINESS);
        assertThat(testTeam.getSpirit()).isEqualTo(DEFAULT_SPIRIT);
        assertThat(testTeam.getFans()).isEqualTo(DEFAULT_FANS);
        assertThat(testTeam.getFansSatisfaction()).isEqualTo(DEFAULT_FANS_SATISFACTION);
    }

    @Test
    @Transactional
    void createTeamWithExistingId() throws Exception {
        // Create the Team with an existing ID
        teamRepository.saveAndFlush(team);

        int databaseSizeBeforeCreate = teamRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isBadRequest());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamRepository.findAll().size();
        // set the field null
        team.setName(null);

        // Create the Team, which fails.

        restTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isBadRequest());

        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHomelandIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamRepository.findAll().size();
        // set the field null
        team.setHomeland(null);

        // Create the Team, which fails.

        restTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isBadRequest());

        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamRepository.findAll().size();
        // set the field null
        team.setGender(null);

        // Create the Team, which fails.

        restTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isBadRequest());

        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMoneyIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamRepository.findAll().size();
        // set the field null
        team.setMoney(null);

        // Create the Team, which fails.

        restTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isBadRequest());

        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPointsIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamRepository.findAll().size();
        // set the field null
        team.setPoints(null);

        // Create the Team, which fails.

        restTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isBadRequest());

        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMatchesIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamRepository.findAll().size();
        // set the field null
        team.setMatches(null);

        // Create the Team, which fails.

        restTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isBadRequest());

        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTrophiesIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamRepository.findAll().size();
        // set the field null
        team.setTrophies(null);

        // Create the Team, which fails.

        restTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isBadRequest());

        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkReadinessIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamRepository.findAll().size();
        // set the field null
        team.setReadiness(null);

        // Create the Team, which fails.

        restTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isBadRequest());

        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSpiritIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamRepository.findAll().size();
        // set the field null
        team.setSpirit(null);

        // Create the Team, which fails.

        restTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isBadRequest());

        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFansIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamRepository.findAll().size();
        // set the field null
        team.setFans(null);

        // Create the Team, which fails.

        restTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isBadRequest());

        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFansSatisfactionIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamRepository.findAll().size();
        // set the field null
        team.setFansSatisfaction(null);

        // Create the Team, which fails.

        restTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isBadRequest());

        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTeams() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList
        restTeamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(team.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].homeland").value(hasItem(DEFAULT_HOMELAND.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].money").value(hasItem(DEFAULT_MONEY.intValue())))
            .andExpect(jsonPath("$.[*].points").value(hasItem(DEFAULT_POINTS.intValue())))
            .andExpect(jsonPath("$.[*].matches").value(hasItem(DEFAULT_MATCHES.intValue())))
            .andExpect(jsonPath("$.[*].trophies").value(hasItem(DEFAULT_TROPHIES.intValue())))
            .andExpect(jsonPath("$.[*].readiness").value(hasItem(DEFAULT_READINESS)))
            .andExpect(jsonPath("$.[*].spirit").value(hasItem(DEFAULT_SPIRIT)))
            .andExpect(jsonPath("$.[*].fans").value(hasItem(DEFAULT_FANS)))
            .andExpect(jsonPath("$.[*].fansSatisfaction").value(hasItem(DEFAULT_FANS_SATISFACTION)));
    }

    @Test
    @Transactional
    void getTeam() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get the team
        restTeamMockMvc
            .perform(get(ENTITY_API_URL_ID, team.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(team.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.homeland").value(DEFAULT_HOMELAND.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.money").value(DEFAULT_MONEY.intValue()))
            .andExpect(jsonPath("$.points").value(DEFAULT_POINTS.intValue()))
            .andExpect(jsonPath("$.matches").value(DEFAULT_MATCHES.intValue()))
            .andExpect(jsonPath("$.trophies").value(DEFAULT_TROPHIES.intValue()))
            .andExpect(jsonPath("$.readiness").value(DEFAULT_READINESS))
            .andExpect(jsonPath("$.spirit").value(DEFAULT_SPIRIT))
            .andExpect(jsonPath("$.fans").value(DEFAULT_FANS))
            .andExpect(jsonPath("$.fansSatisfaction").value(DEFAULT_FANS_SATISFACTION));
    }

    @Test
    @Transactional
    void getNonExistingTeam() throws Exception {
        // Get the team
        restTeamMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTeam() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        int databaseSizeBeforeUpdate = teamRepository.findAll().size();

        // Update the team
        Team updatedTeam = teamRepository.findById(team.getId()).get();
        // Disconnect from session so that the updates on updatedTeam are not directly saved in db
        em.detach(updatedTeam);
        updatedTeam
            .name(UPDATED_NAME)
            .homeland(UPDATED_HOMELAND)
            .gender(UPDATED_GENDER)
            .money(UPDATED_MONEY)
            .points(UPDATED_POINTS)
            .matches(UPDATED_MATCHES)
            .trophies(UPDATED_TROPHIES)
            .readiness(UPDATED_READINESS)
            .spirit(UPDATED_SPIRIT)
            .fans(UPDATED_FANS)
            .fansSatisfaction(UPDATED_FANS_SATISFACTION);

        restTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTeam.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTeam))
            )
            .andExpect(status().isOk());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTeam.getHomeland()).isEqualTo(UPDATED_HOMELAND);
        assertThat(testTeam.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testTeam.getMoney()).isEqualTo(UPDATED_MONEY);
        assertThat(testTeam.getPoints()).isEqualTo(UPDATED_POINTS);
        assertThat(testTeam.getMatches()).isEqualTo(UPDATED_MATCHES);
        assertThat(testTeam.getTrophies()).isEqualTo(UPDATED_TROPHIES);
        assertThat(testTeam.getReadiness()).isEqualTo(UPDATED_READINESS);
        assertThat(testTeam.getSpirit()).isEqualTo(UPDATED_SPIRIT);
        assertThat(testTeam.getFans()).isEqualTo(UPDATED_FANS);
        assertThat(testTeam.getFansSatisfaction()).isEqualTo(UPDATED_FANS_SATISFACTION);
    }

    @Test
    @Transactional
    void putNonExistingTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();
        team.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, team.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(team))
            )
            .andExpect(status().isBadRequest());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();
        team.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(team))
            )
            .andExpect(status().isBadRequest());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();
        team.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTeamWithPatch() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        int databaseSizeBeforeUpdate = teamRepository.findAll().size();

        // Update the team using partial update
        Team partialUpdatedTeam = new Team();
        partialUpdatedTeam.setId(team.getId());

        partialUpdatedTeam
            .gender(UPDATED_GENDER)
            .money(UPDATED_MONEY)
            .points(UPDATED_POINTS)
            .readiness(UPDATED_READINESS)
            .spirit(UPDATED_SPIRIT)
            .fans(UPDATED_FANS);

        restTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeam))
            )
            .andExpect(status().isOk());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTeam.getHomeland()).isEqualTo(DEFAULT_HOMELAND);
        assertThat(testTeam.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testTeam.getMoney()).isEqualTo(UPDATED_MONEY);
        assertThat(testTeam.getPoints()).isEqualTo(UPDATED_POINTS);
        assertThat(testTeam.getMatches()).isEqualTo(DEFAULT_MATCHES);
        assertThat(testTeam.getTrophies()).isEqualTo(DEFAULT_TROPHIES);
        assertThat(testTeam.getReadiness()).isEqualTo(UPDATED_READINESS);
        assertThat(testTeam.getSpirit()).isEqualTo(UPDATED_SPIRIT);
        assertThat(testTeam.getFans()).isEqualTo(UPDATED_FANS);
        assertThat(testTeam.getFansSatisfaction()).isEqualTo(DEFAULT_FANS_SATISFACTION);
    }

    @Test
    @Transactional
    void fullUpdateTeamWithPatch() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        int databaseSizeBeforeUpdate = teamRepository.findAll().size();

        // Update the team using partial update
        Team partialUpdatedTeam = new Team();
        partialUpdatedTeam.setId(team.getId());

        partialUpdatedTeam
            .name(UPDATED_NAME)
            .homeland(UPDATED_HOMELAND)
            .gender(UPDATED_GENDER)
            .money(UPDATED_MONEY)
            .points(UPDATED_POINTS)
            .matches(UPDATED_MATCHES)
            .trophies(UPDATED_TROPHIES)
            .readiness(UPDATED_READINESS)
            .spirit(UPDATED_SPIRIT)
            .fans(UPDATED_FANS)
            .fansSatisfaction(UPDATED_FANS_SATISFACTION);

        restTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeam))
            )
            .andExpect(status().isOk());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTeam.getHomeland()).isEqualTo(UPDATED_HOMELAND);
        assertThat(testTeam.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testTeam.getMoney()).isEqualTo(UPDATED_MONEY);
        assertThat(testTeam.getPoints()).isEqualTo(UPDATED_POINTS);
        assertThat(testTeam.getMatches()).isEqualTo(UPDATED_MATCHES);
        assertThat(testTeam.getTrophies()).isEqualTo(UPDATED_TROPHIES);
        assertThat(testTeam.getReadiness()).isEqualTo(UPDATED_READINESS);
        assertThat(testTeam.getSpirit()).isEqualTo(UPDATED_SPIRIT);
        assertThat(testTeam.getFans()).isEqualTo(UPDATED_FANS);
        assertThat(testTeam.getFansSatisfaction()).isEqualTo(UPDATED_FANS_SATISFACTION);
    }

    @Test
    @Transactional
    void patchNonExistingTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();
        team.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, team.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(team))
            )
            .andExpect(status().isBadRequest());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();
        team.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(team))
            )
            .andExpect(status().isBadRequest());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();
        team.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTeam() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        int databaseSizeBeforeDelete = teamRepository.findAll().size();

        // Delete the team
        restTeamMockMvc
            .perform(delete(ENTITY_API_URL_ID, team.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
