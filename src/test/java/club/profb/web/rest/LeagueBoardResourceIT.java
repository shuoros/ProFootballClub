package club.profb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import club.profb.IntegrationTest;
import club.profb.domain.LeagueBoard;
import club.profb.repository.LeagueBoardRepository;
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
 * Integration tests for the {@link LeagueBoardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeagueBoardResourceIT {

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;

    private static final Integer DEFAULT_WIN = 1;
    private static final Integer UPDATED_WIN = 2;

    private static final Integer DEFAULT_LOSE = 1;
    private static final Integer UPDATED_LOSE = 2;

    private static final Integer DEFAULT_DRAW = 1;
    private static final Integer UPDATED_DRAW = 2;

    private static final Integer DEFAULT_GOAL_DIFFERENCE = 1;
    private static final Integer UPDATED_GOAL_DIFFERENCE = 2;

    private static final Integer DEFAULT_POINTS = 1;
    private static final Integer UPDATED_POINTS = 2;

    private static final String ENTITY_API_URL = "/api/league-boards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private LeagueBoardRepository leagueBoardRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeagueBoardMockMvc;

    private LeagueBoard leagueBoard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeagueBoard createEntity(EntityManager em) {
        LeagueBoard leagueBoard = new LeagueBoard()
            .position(DEFAULT_POSITION)
            .win(DEFAULT_WIN)
            .lose(DEFAULT_LOSE)
            .draw(DEFAULT_DRAW)
            .goalDifference(DEFAULT_GOAL_DIFFERENCE)
            .points(DEFAULT_POINTS);
        return leagueBoard;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeagueBoard createUpdatedEntity(EntityManager em) {
        LeagueBoard leagueBoard = new LeagueBoard()
            .position(UPDATED_POSITION)
            .win(UPDATED_WIN)
            .lose(UPDATED_LOSE)
            .draw(UPDATED_DRAW)
            .goalDifference(UPDATED_GOAL_DIFFERENCE)
            .points(UPDATED_POINTS);
        return leagueBoard;
    }

    @BeforeEach
    public void initTest() {
        leagueBoard = createEntity(em);
    }

    @Test
    @Transactional
    void createLeagueBoard() throws Exception {
        int databaseSizeBeforeCreate = leagueBoardRepository.findAll().size();
        // Create the LeagueBoard
        restLeagueBoardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leagueBoard)))
            .andExpect(status().isCreated());

        // Validate the LeagueBoard in the database
        List<LeagueBoard> leagueBoardList = leagueBoardRepository.findAll();
        assertThat(leagueBoardList).hasSize(databaseSizeBeforeCreate + 1);
        LeagueBoard testLeagueBoard = leagueBoardList.get(leagueBoardList.size() - 1);
        assertThat(testLeagueBoard.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testLeagueBoard.getWin()).isEqualTo(DEFAULT_WIN);
        assertThat(testLeagueBoard.getLose()).isEqualTo(DEFAULT_LOSE);
        assertThat(testLeagueBoard.getDraw()).isEqualTo(DEFAULT_DRAW);
        assertThat(testLeagueBoard.getGoalDifference()).isEqualTo(DEFAULT_GOAL_DIFFERENCE);
        assertThat(testLeagueBoard.getPoints()).isEqualTo(DEFAULT_POINTS);
    }

    @Test
    @Transactional
    void createLeagueBoardWithExistingId() throws Exception {
        // Create the LeagueBoard with an existing ID
        leagueBoardRepository.saveAndFlush(leagueBoard);

        int databaseSizeBeforeCreate = leagueBoardRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeagueBoardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leagueBoard)))
            .andExpect(status().isBadRequest());

        // Validate the LeagueBoard in the database
        List<LeagueBoard> leagueBoardList = leagueBoardRepository.findAll();
        assertThat(leagueBoardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = leagueBoardRepository.findAll().size();
        // set the field null
        leagueBoard.setPosition(null);

        // Create the LeagueBoard, which fails.

        restLeagueBoardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leagueBoard)))
            .andExpect(status().isBadRequest());

        List<LeagueBoard> leagueBoardList = leagueBoardRepository.findAll();
        assertThat(leagueBoardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeagueBoards() throws Exception {
        // Initialize the database
        leagueBoardRepository.saveAndFlush(leagueBoard);

        // Get all the leagueBoardList
        restLeagueBoardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leagueBoard.getId().toString())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].win").value(hasItem(DEFAULT_WIN)))
            .andExpect(jsonPath("$.[*].lose").value(hasItem(DEFAULT_LOSE)))
            .andExpect(jsonPath("$.[*].draw").value(hasItem(DEFAULT_DRAW)))
            .andExpect(jsonPath("$.[*].goalDifference").value(hasItem(DEFAULT_GOAL_DIFFERENCE)))
            .andExpect(jsonPath("$.[*].points").value(hasItem(DEFAULT_POINTS)));
    }

    @Test
    @Transactional
    void getLeagueBoard() throws Exception {
        // Initialize the database
        leagueBoardRepository.saveAndFlush(leagueBoard);

        // Get the leagueBoard
        restLeagueBoardMockMvc
            .perform(get(ENTITY_API_URL_ID, leagueBoard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leagueBoard.getId().toString()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.win").value(DEFAULT_WIN))
            .andExpect(jsonPath("$.lose").value(DEFAULT_LOSE))
            .andExpect(jsonPath("$.draw").value(DEFAULT_DRAW))
            .andExpect(jsonPath("$.goalDifference").value(DEFAULT_GOAL_DIFFERENCE))
            .andExpect(jsonPath("$.points").value(DEFAULT_POINTS));
    }

    @Test
    @Transactional
    void getNonExistingLeagueBoard() throws Exception {
        // Get the leagueBoard
        restLeagueBoardMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLeagueBoard() throws Exception {
        // Initialize the database
        leagueBoardRepository.saveAndFlush(leagueBoard);

        int databaseSizeBeforeUpdate = leagueBoardRepository.findAll().size();

        // Update the leagueBoard
        LeagueBoard updatedLeagueBoard = leagueBoardRepository.findById(leagueBoard.getId()).get();
        // Disconnect from session so that the updates on updatedLeagueBoard are not directly saved in db
        em.detach(updatedLeagueBoard);
        updatedLeagueBoard
            .position(UPDATED_POSITION)
            .win(UPDATED_WIN)
            .lose(UPDATED_LOSE)
            .draw(UPDATED_DRAW)
            .goalDifference(UPDATED_GOAL_DIFFERENCE)
            .points(UPDATED_POINTS);

        restLeagueBoardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeagueBoard.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLeagueBoard))
            )
            .andExpect(status().isOk());

        // Validate the LeagueBoard in the database
        List<LeagueBoard> leagueBoardList = leagueBoardRepository.findAll();
        assertThat(leagueBoardList).hasSize(databaseSizeBeforeUpdate);
        LeagueBoard testLeagueBoard = leagueBoardList.get(leagueBoardList.size() - 1);
        assertThat(testLeagueBoard.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testLeagueBoard.getWin()).isEqualTo(UPDATED_WIN);
        assertThat(testLeagueBoard.getLose()).isEqualTo(UPDATED_LOSE);
        assertThat(testLeagueBoard.getDraw()).isEqualTo(UPDATED_DRAW);
        assertThat(testLeagueBoard.getGoalDifference()).isEqualTo(UPDATED_GOAL_DIFFERENCE);
        assertThat(testLeagueBoard.getPoints()).isEqualTo(UPDATED_POINTS);
    }

    @Test
    @Transactional
    void putNonExistingLeagueBoard() throws Exception {
        int databaseSizeBeforeUpdate = leagueBoardRepository.findAll().size();
        leagueBoard.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeagueBoardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leagueBoard.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leagueBoard))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeagueBoard in the database
        List<LeagueBoard> leagueBoardList = leagueBoardRepository.findAll();
        assertThat(leagueBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeagueBoard() throws Exception {
        int databaseSizeBeforeUpdate = leagueBoardRepository.findAll().size();
        leagueBoard.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeagueBoardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leagueBoard))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeagueBoard in the database
        List<LeagueBoard> leagueBoardList = leagueBoardRepository.findAll();
        assertThat(leagueBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeagueBoard() throws Exception {
        int databaseSizeBeforeUpdate = leagueBoardRepository.findAll().size();
        leagueBoard.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeagueBoardMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leagueBoard)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeagueBoard in the database
        List<LeagueBoard> leagueBoardList = leagueBoardRepository.findAll();
        assertThat(leagueBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeagueBoardWithPatch() throws Exception {
        // Initialize the database
        leagueBoardRepository.saveAndFlush(leagueBoard);

        int databaseSizeBeforeUpdate = leagueBoardRepository.findAll().size();

        // Update the leagueBoard using partial update
        LeagueBoard partialUpdatedLeagueBoard = new LeagueBoard();
        partialUpdatedLeagueBoard.setId(leagueBoard.getId());

        partialUpdatedLeagueBoard
            .position(UPDATED_POSITION)
            .win(UPDATED_WIN)
            .lose(UPDATED_LOSE)
            .draw(UPDATED_DRAW)
            .goalDifference(UPDATED_GOAL_DIFFERENCE)
            .points(UPDATED_POINTS);

        restLeagueBoardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeagueBoard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeagueBoard))
            )
            .andExpect(status().isOk());

        // Validate the LeagueBoard in the database
        List<LeagueBoard> leagueBoardList = leagueBoardRepository.findAll();
        assertThat(leagueBoardList).hasSize(databaseSizeBeforeUpdate);
        LeagueBoard testLeagueBoard = leagueBoardList.get(leagueBoardList.size() - 1);
        assertThat(testLeagueBoard.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testLeagueBoard.getWin()).isEqualTo(UPDATED_WIN);
        assertThat(testLeagueBoard.getLose()).isEqualTo(UPDATED_LOSE);
        assertThat(testLeagueBoard.getDraw()).isEqualTo(UPDATED_DRAW);
        assertThat(testLeagueBoard.getGoalDifference()).isEqualTo(UPDATED_GOAL_DIFFERENCE);
        assertThat(testLeagueBoard.getPoints()).isEqualTo(UPDATED_POINTS);
    }

    @Test
    @Transactional
    void fullUpdateLeagueBoardWithPatch() throws Exception {
        // Initialize the database
        leagueBoardRepository.saveAndFlush(leagueBoard);

        int databaseSizeBeforeUpdate = leagueBoardRepository.findAll().size();

        // Update the leagueBoard using partial update
        LeagueBoard partialUpdatedLeagueBoard = new LeagueBoard();
        partialUpdatedLeagueBoard.setId(leagueBoard.getId());

        partialUpdatedLeagueBoard
            .position(UPDATED_POSITION)
            .win(UPDATED_WIN)
            .lose(UPDATED_LOSE)
            .draw(UPDATED_DRAW)
            .goalDifference(UPDATED_GOAL_DIFFERENCE)
            .points(UPDATED_POINTS);

        restLeagueBoardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeagueBoard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeagueBoard))
            )
            .andExpect(status().isOk());

        // Validate the LeagueBoard in the database
        List<LeagueBoard> leagueBoardList = leagueBoardRepository.findAll();
        assertThat(leagueBoardList).hasSize(databaseSizeBeforeUpdate);
        LeagueBoard testLeagueBoard = leagueBoardList.get(leagueBoardList.size() - 1);
        assertThat(testLeagueBoard.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testLeagueBoard.getWin()).isEqualTo(UPDATED_WIN);
        assertThat(testLeagueBoard.getLose()).isEqualTo(UPDATED_LOSE);
        assertThat(testLeagueBoard.getDraw()).isEqualTo(UPDATED_DRAW);
        assertThat(testLeagueBoard.getGoalDifference()).isEqualTo(UPDATED_GOAL_DIFFERENCE);
        assertThat(testLeagueBoard.getPoints()).isEqualTo(UPDATED_POINTS);
    }

    @Test
    @Transactional
    void patchNonExistingLeagueBoard() throws Exception {
        int databaseSizeBeforeUpdate = leagueBoardRepository.findAll().size();
        leagueBoard.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeagueBoardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leagueBoard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leagueBoard))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeagueBoard in the database
        List<LeagueBoard> leagueBoardList = leagueBoardRepository.findAll();
        assertThat(leagueBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeagueBoard() throws Exception {
        int databaseSizeBeforeUpdate = leagueBoardRepository.findAll().size();
        leagueBoard.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeagueBoardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leagueBoard))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeagueBoard in the database
        List<LeagueBoard> leagueBoardList = leagueBoardRepository.findAll();
        assertThat(leagueBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeagueBoard() throws Exception {
        int databaseSizeBeforeUpdate = leagueBoardRepository.findAll().size();
        leagueBoard.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeagueBoardMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(leagueBoard))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeagueBoard in the database
        List<LeagueBoard> leagueBoardList = leagueBoardRepository.findAll();
        assertThat(leagueBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeagueBoard() throws Exception {
        // Initialize the database
        leagueBoardRepository.saveAndFlush(leagueBoard);

        int databaseSizeBeforeDelete = leagueBoardRepository.findAll().size();

        // Delete the leagueBoard
        restLeagueBoardMockMvc
            .perform(delete(ENTITY_API_URL_ID, leagueBoard.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeagueBoard> leagueBoardList = leagueBoardRepository.findAll();
        assertThat(leagueBoardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
