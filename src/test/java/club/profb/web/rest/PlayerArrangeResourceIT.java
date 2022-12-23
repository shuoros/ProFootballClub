package club.profb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import club.profb.IntegrationTest;
import club.profb.domain.PlayerArrange;
import club.profb.domain.enumeration.Post;
import club.profb.repository.PlayerArrangeRepository;
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
 * Integration tests for the {@link PlayerArrangeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlayerArrangeResourceIT {

    private static final Post DEFAULT_POST = Post.GK;
    private static final Post UPDATED_POST = Post.SW;

    private static final String ENTITY_API_URL = "/api/player-arranges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PlayerArrangeRepository playerArrangeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlayerArrangeMockMvc;

    private PlayerArrange playerArrange;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlayerArrange createEntity(EntityManager em) {
        PlayerArrange playerArrange = new PlayerArrange().post(DEFAULT_POST);
        return playerArrange;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlayerArrange createUpdatedEntity(EntityManager em) {
        PlayerArrange playerArrange = new PlayerArrange().post(UPDATED_POST);
        return playerArrange;
    }

    @BeforeEach
    public void initTest() {
        playerArrange = createEntity(em);
    }

    @Test
    @Transactional
    void createPlayerArrange() throws Exception {
        int databaseSizeBeforeCreate = playerArrangeRepository.findAll().size();
        // Create the PlayerArrange
        restPlayerArrangeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(playerArrange)))
            .andExpect(status().isCreated());

        // Validate the PlayerArrange in the database
        List<PlayerArrange> playerArrangeList = playerArrangeRepository.findAll();
        assertThat(playerArrangeList).hasSize(databaseSizeBeforeCreate + 1);
        PlayerArrange testPlayerArrange = playerArrangeList.get(playerArrangeList.size() - 1);
        assertThat(testPlayerArrange.getPost()).isEqualTo(DEFAULT_POST);
    }

    @Test
    @Transactional
    void createPlayerArrangeWithExistingId() throws Exception {
        // Create the PlayerArrange with an existing ID
        playerArrangeRepository.saveAndFlush(playerArrange);

        int databaseSizeBeforeCreate = playerArrangeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlayerArrangeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(playerArrange)))
            .andExpect(status().isBadRequest());

        // Validate the PlayerArrange in the database
        List<PlayerArrange> playerArrangeList = playerArrangeRepository.findAll();
        assertThat(playerArrangeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlayerArranges() throws Exception {
        // Initialize the database
        playerArrangeRepository.saveAndFlush(playerArrange);

        // Get all the playerArrangeList
        restPlayerArrangeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(playerArrange.getId().toString())))
            .andExpect(jsonPath("$.[*].post").value(hasItem(DEFAULT_POST.toString())));
    }

    @Test
    @Transactional
    void getPlayerArrange() throws Exception {
        // Initialize the database
        playerArrangeRepository.saveAndFlush(playerArrange);

        // Get the playerArrange
        restPlayerArrangeMockMvc
            .perform(get(ENTITY_API_URL_ID, playerArrange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(playerArrange.getId().toString()))
            .andExpect(jsonPath("$.post").value(DEFAULT_POST.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPlayerArrange() throws Exception {
        // Get the playerArrange
        restPlayerArrangeMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPlayerArrange() throws Exception {
        // Initialize the database
        playerArrangeRepository.saveAndFlush(playerArrange);

        int databaseSizeBeforeUpdate = playerArrangeRepository.findAll().size();

        // Update the playerArrange
        PlayerArrange updatedPlayerArrange = playerArrangeRepository.findById(playerArrange.getId()).get();
        // Disconnect from session so that the updates on updatedPlayerArrange are not directly saved in db
        em.detach(updatedPlayerArrange);
        updatedPlayerArrange.post(UPDATED_POST);

        restPlayerArrangeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlayerArrange.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlayerArrange))
            )
            .andExpect(status().isOk());

        // Validate the PlayerArrange in the database
        List<PlayerArrange> playerArrangeList = playerArrangeRepository.findAll();
        assertThat(playerArrangeList).hasSize(databaseSizeBeforeUpdate);
        PlayerArrange testPlayerArrange = playerArrangeList.get(playerArrangeList.size() - 1);
        assertThat(testPlayerArrange.getPost()).isEqualTo(UPDATED_POST);
    }

    @Test
    @Transactional
    void putNonExistingPlayerArrange() throws Exception {
        int databaseSizeBeforeUpdate = playerArrangeRepository.findAll().size();
        playerArrange.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayerArrangeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, playerArrange.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(playerArrange))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlayerArrange in the database
        List<PlayerArrange> playerArrangeList = playerArrangeRepository.findAll();
        assertThat(playerArrangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlayerArrange() throws Exception {
        int databaseSizeBeforeUpdate = playerArrangeRepository.findAll().size();
        playerArrange.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerArrangeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(playerArrange))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlayerArrange in the database
        List<PlayerArrange> playerArrangeList = playerArrangeRepository.findAll();
        assertThat(playerArrangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlayerArrange() throws Exception {
        int databaseSizeBeforeUpdate = playerArrangeRepository.findAll().size();
        playerArrange.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerArrangeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(playerArrange)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlayerArrange in the database
        List<PlayerArrange> playerArrangeList = playerArrangeRepository.findAll();
        assertThat(playerArrangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlayerArrangeWithPatch() throws Exception {
        // Initialize the database
        playerArrangeRepository.saveAndFlush(playerArrange);

        int databaseSizeBeforeUpdate = playerArrangeRepository.findAll().size();

        // Update the playerArrange using partial update
        PlayerArrange partialUpdatedPlayerArrange = new PlayerArrange();
        partialUpdatedPlayerArrange.setId(playerArrange.getId());

        partialUpdatedPlayerArrange.post(UPDATED_POST);

        restPlayerArrangeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlayerArrange.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlayerArrange))
            )
            .andExpect(status().isOk());

        // Validate the PlayerArrange in the database
        List<PlayerArrange> playerArrangeList = playerArrangeRepository.findAll();
        assertThat(playerArrangeList).hasSize(databaseSizeBeforeUpdate);
        PlayerArrange testPlayerArrange = playerArrangeList.get(playerArrangeList.size() - 1);
        assertThat(testPlayerArrange.getPost()).isEqualTo(UPDATED_POST);
    }

    @Test
    @Transactional
    void fullUpdatePlayerArrangeWithPatch() throws Exception {
        // Initialize the database
        playerArrangeRepository.saveAndFlush(playerArrange);

        int databaseSizeBeforeUpdate = playerArrangeRepository.findAll().size();

        // Update the playerArrange using partial update
        PlayerArrange partialUpdatedPlayerArrange = new PlayerArrange();
        partialUpdatedPlayerArrange.setId(playerArrange.getId());

        partialUpdatedPlayerArrange.post(UPDATED_POST);

        restPlayerArrangeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlayerArrange.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlayerArrange))
            )
            .andExpect(status().isOk());

        // Validate the PlayerArrange in the database
        List<PlayerArrange> playerArrangeList = playerArrangeRepository.findAll();
        assertThat(playerArrangeList).hasSize(databaseSizeBeforeUpdate);
        PlayerArrange testPlayerArrange = playerArrangeList.get(playerArrangeList.size() - 1);
        assertThat(testPlayerArrange.getPost()).isEqualTo(UPDATED_POST);
    }

    @Test
    @Transactional
    void patchNonExistingPlayerArrange() throws Exception {
        int databaseSizeBeforeUpdate = playerArrangeRepository.findAll().size();
        playerArrange.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayerArrangeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, playerArrange.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(playerArrange))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlayerArrange in the database
        List<PlayerArrange> playerArrangeList = playerArrangeRepository.findAll();
        assertThat(playerArrangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlayerArrange() throws Exception {
        int databaseSizeBeforeUpdate = playerArrangeRepository.findAll().size();
        playerArrange.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerArrangeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(playerArrange))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlayerArrange in the database
        List<PlayerArrange> playerArrangeList = playerArrangeRepository.findAll();
        assertThat(playerArrangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlayerArrange() throws Exception {
        int databaseSizeBeforeUpdate = playerArrangeRepository.findAll().size();
        playerArrange.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerArrangeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(playerArrange))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlayerArrange in the database
        List<PlayerArrange> playerArrangeList = playerArrangeRepository.findAll();
        assertThat(playerArrangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlayerArrange() throws Exception {
        // Initialize the database
        playerArrangeRepository.saveAndFlush(playerArrange);

        int databaseSizeBeforeDelete = playerArrangeRepository.findAll().size();

        // Delete the playerArrange
        restPlayerArrangeMockMvc
            .perform(delete(ENTITY_API_URL_ID, playerArrange.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlayerArrange> playerArrangeList = playerArrangeRepository.findAll();
        assertThat(playerArrangeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
