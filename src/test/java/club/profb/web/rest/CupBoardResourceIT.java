package club.profb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import club.profb.IntegrationTest;
import club.profb.domain.CupBoard;
import club.profb.repository.CupBoardRepository;
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
 * Integration tests for the {@link CupBoardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CupBoardResourceIT {

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    private static final String ENTITY_API_URL = "/api/cup-boards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CupBoardRepository cupBoardRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCupBoardMockMvc;

    private CupBoard cupBoard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CupBoard createEntity(EntityManager em) {
        CupBoard cupBoard = new CupBoard().level(DEFAULT_LEVEL);
        return cupBoard;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CupBoard createUpdatedEntity(EntityManager em) {
        CupBoard cupBoard = new CupBoard().level(UPDATED_LEVEL);
        return cupBoard;
    }

    @BeforeEach
    public void initTest() {
        cupBoard = createEntity(em);
    }

    @Test
    @Transactional
    void createCupBoard() throws Exception {
        int databaseSizeBeforeCreate = cupBoardRepository.findAll().size();
        // Create the CupBoard
        restCupBoardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cupBoard)))
            .andExpect(status().isCreated());

        // Validate the CupBoard in the database
        List<CupBoard> cupBoardList = cupBoardRepository.findAll();
        assertThat(cupBoardList).hasSize(databaseSizeBeforeCreate + 1);
        CupBoard testCupBoard = cupBoardList.get(cupBoardList.size() - 1);
        assertThat(testCupBoard.getLevel()).isEqualTo(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    void createCupBoardWithExistingId() throws Exception {
        // Create the CupBoard with an existing ID
        cupBoardRepository.saveAndFlush(cupBoard);

        int databaseSizeBeforeCreate = cupBoardRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCupBoardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cupBoard)))
            .andExpect(status().isBadRequest());

        // Validate the CupBoard in the database
        List<CupBoard> cupBoardList = cupBoardRepository.findAll();
        assertThat(cupBoardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = cupBoardRepository.findAll().size();
        // set the field null
        cupBoard.setLevel(null);

        // Create the CupBoard, which fails.

        restCupBoardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cupBoard)))
            .andExpect(status().isBadRequest());

        List<CupBoard> cupBoardList = cupBoardRepository.findAll();
        assertThat(cupBoardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCupBoards() throws Exception {
        // Initialize the database
        cupBoardRepository.saveAndFlush(cupBoard);

        // Get all the cupBoardList
        restCupBoardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cupBoard.getId().toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)));
    }

    @Test
    @Transactional
    void getCupBoard() throws Exception {
        // Initialize the database
        cupBoardRepository.saveAndFlush(cupBoard);

        // Get the cupBoard
        restCupBoardMockMvc
            .perform(get(ENTITY_API_URL_ID, cupBoard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cupBoard.getId().toString()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL));
    }

    @Test
    @Transactional
    void getNonExistingCupBoard() throws Exception {
        // Get the cupBoard
        restCupBoardMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCupBoard() throws Exception {
        // Initialize the database
        cupBoardRepository.saveAndFlush(cupBoard);

        int databaseSizeBeforeUpdate = cupBoardRepository.findAll().size();

        // Update the cupBoard
        CupBoard updatedCupBoard = cupBoardRepository.findById(cupBoard.getId()).get();
        // Disconnect from session so that the updates on updatedCupBoard are not directly saved in db
        em.detach(updatedCupBoard);
        updatedCupBoard.level(UPDATED_LEVEL);

        restCupBoardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCupBoard.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCupBoard))
            )
            .andExpect(status().isOk());

        // Validate the CupBoard in the database
        List<CupBoard> cupBoardList = cupBoardRepository.findAll();
        assertThat(cupBoardList).hasSize(databaseSizeBeforeUpdate);
        CupBoard testCupBoard = cupBoardList.get(cupBoardList.size() - 1);
        assertThat(testCupBoard.getLevel()).isEqualTo(UPDATED_LEVEL);
    }

    @Test
    @Transactional
    void putNonExistingCupBoard() throws Exception {
        int databaseSizeBeforeUpdate = cupBoardRepository.findAll().size();
        cupBoard.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCupBoardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cupBoard.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cupBoard))
            )
            .andExpect(status().isBadRequest());

        // Validate the CupBoard in the database
        List<CupBoard> cupBoardList = cupBoardRepository.findAll();
        assertThat(cupBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCupBoard() throws Exception {
        int databaseSizeBeforeUpdate = cupBoardRepository.findAll().size();
        cupBoard.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCupBoardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cupBoard))
            )
            .andExpect(status().isBadRequest());

        // Validate the CupBoard in the database
        List<CupBoard> cupBoardList = cupBoardRepository.findAll();
        assertThat(cupBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCupBoard() throws Exception {
        int databaseSizeBeforeUpdate = cupBoardRepository.findAll().size();
        cupBoard.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCupBoardMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cupBoard)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CupBoard in the database
        List<CupBoard> cupBoardList = cupBoardRepository.findAll();
        assertThat(cupBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCupBoardWithPatch() throws Exception {
        // Initialize the database
        cupBoardRepository.saveAndFlush(cupBoard);

        int databaseSizeBeforeUpdate = cupBoardRepository.findAll().size();

        // Update the cupBoard using partial update
        CupBoard partialUpdatedCupBoard = new CupBoard();
        partialUpdatedCupBoard.setId(cupBoard.getId());

        restCupBoardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCupBoard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCupBoard))
            )
            .andExpect(status().isOk());

        // Validate the CupBoard in the database
        List<CupBoard> cupBoardList = cupBoardRepository.findAll();
        assertThat(cupBoardList).hasSize(databaseSizeBeforeUpdate);
        CupBoard testCupBoard = cupBoardList.get(cupBoardList.size() - 1);
        assertThat(testCupBoard.getLevel()).isEqualTo(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    void fullUpdateCupBoardWithPatch() throws Exception {
        // Initialize the database
        cupBoardRepository.saveAndFlush(cupBoard);

        int databaseSizeBeforeUpdate = cupBoardRepository.findAll().size();

        // Update the cupBoard using partial update
        CupBoard partialUpdatedCupBoard = new CupBoard();
        partialUpdatedCupBoard.setId(cupBoard.getId());

        partialUpdatedCupBoard.level(UPDATED_LEVEL);

        restCupBoardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCupBoard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCupBoard))
            )
            .andExpect(status().isOk());

        // Validate the CupBoard in the database
        List<CupBoard> cupBoardList = cupBoardRepository.findAll();
        assertThat(cupBoardList).hasSize(databaseSizeBeforeUpdate);
        CupBoard testCupBoard = cupBoardList.get(cupBoardList.size() - 1);
        assertThat(testCupBoard.getLevel()).isEqualTo(UPDATED_LEVEL);
    }

    @Test
    @Transactional
    void patchNonExistingCupBoard() throws Exception {
        int databaseSizeBeforeUpdate = cupBoardRepository.findAll().size();
        cupBoard.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCupBoardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cupBoard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cupBoard))
            )
            .andExpect(status().isBadRequest());

        // Validate the CupBoard in the database
        List<CupBoard> cupBoardList = cupBoardRepository.findAll();
        assertThat(cupBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCupBoard() throws Exception {
        int databaseSizeBeforeUpdate = cupBoardRepository.findAll().size();
        cupBoard.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCupBoardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cupBoard))
            )
            .andExpect(status().isBadRequest());

        // Validate the CupBoard in the database
        List<CupBoard> cupBoardList = cupBoardRepository.findAll();
        assertThat(cupBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCupBoard() throws Exception {
        int databaseSizeBeforeUpdate = cupBoardRepository.findAll().size();
        cupBoard.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCupBoardMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cupBoard)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CupBoard in the database
        List<CupBoard> cupBoardList = cupBoardRepository.findAll();
        assertThat(cupBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCupBoard() throws Exception {
        // Initialize the database
        cupBoardRepository.saveAndFlush(cupBoard);

        int databaseSizeBeforeDelete = cupBoardRepository.findAll().size();

        // Delete the cupBoard
        restCupBoardMockMvc
            .perform(delete(ENTITY_API_URL_ID, cupBoard.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CupBoard> cupBoardList = cupBoardRepository.findAll();
        assertThat(cupBoardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
