package club.profb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import club.profb.IntegrationTest;
import club.profb.domain.FriendRequest;
import club.profb.repository.FriendRequestRepository;
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
 * Integration tests for the {@link FriendRequestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FriendRequestResourceIT {

    private static final String ENTITY_API_URL = "/api/friend-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFriendRequestMockMvc;

    private FriendRequest friendRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FriendRequest createEntity(EntityManager em) {
        FriendRequest friendRequest = new FriendRequest();
        return friendRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FriendRequest createUpdatedEntity(EntityManager em) {
        FriendRequest friendRequest = new FriendRequest();
        return friendRequest;
    }

    @BeforeEach
    public void initTest() {
        friendRequest = createEntity(em);
    }

    @Test
    @Transactional
    void createFriendRequest() throws Exception {
        int databaseSizeBeforeCreate = friendRequestRepository.findAll().size();
        // Create the FriendRequest
        restFriendRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(friendRequest)))
            .andExpect(status().isCreated());

        // Validate the FriendRequest in the database
        List<FriendRequest> friendRequestList = friendRequestRepository.findAll();
        assertThat(friendRequestList).hasSize(databaseSizeBeforeCreate + 1);
        FriendRequest testFriendRequest = friendRequestList.get(friendRequestList.size() - 1);
    }

    @Test
    @Transactional
    void createFriendRequestWithExistingId() throws Exception {
        // Create the FriendRequest with an existing ID
        friendRequestRepository.saveAndFlush(friendRequest);

        int databaseSizeBeforeCreate = friendRequestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFriendRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(friendRequest)))
            .andExpect(status().isBadRequest());

        // Validate the FriendRequest in the database
        List<FriendRequest> friendRequestList = friendRequestRepository.findAll();
        assertThat(friendRequestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFriendRequests() throws Exception {
        // Initialize the database
        friendRequestRepository.saveAndFlush(friendRequest);

        // Get all the friendRequestList
        restFriendRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(friendRequest.getId().toString())));
    }

    @Test
    @Transactional
    void getFriendRequest() throws Exception {
        // Initialize the database
        friendRequestRepository.saveAndFlush(friendRequest);

        // Get the friendRequest
        restFriendRequestMockMvc
            .perform(get(ENTITY_API_URL_ID, friendRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(friendRequest.getId().toString()));
    }

    @Test
    @Transactional
    void getNonExistingFriendRequest() throws Exception {
        // Get the friendRequest
        restFriendRequestMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFriendRequest() throws Exception {
        // Initialize the database
        friendRequestRepository.saveAndFlush(friendRequest);

        int databaseSizeBeforeUpdate = friendRequestRepository.findAll().size();

        // Update the friendRequest
        FriendRequest updatedFriendRequest = friendRequestRepository.findById(friendRequest.getId()).get();
        // Disconnect from session so that the updates on updatedFriendRequest are not directly saved in db
        em.detach(updatedFriendRequest);

        restFriendRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFriendRequest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFriendRequest))
            )
            .andExpect(status().isOk());

        // Validate the FriendRequest in the database
        List<FriendRequest> friendRequestList = friendRequestRepository.findAll();
        assertThat(friendRequestList).hasSize(databaseSizeBeforeUpdate);
        FriendRequest testFriendRequest = friendRequestList.get(friendRequestList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingFriendRequest() throws Exception {
        int databaseSizeBeforeUpdate = friendRequestRepository.findAll().size();
        friendRequest.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFriendRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, friendRequest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(friendRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the FriendRequest in the database
        List<FriendRequest> friendRequestList = friendRequestRepository.findAll();
        assertThat(friendRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFriendRequest() throws Exception {
        int databaseSizeBeforeUpdate = friendRequestRepository.findAll().size();
        friendRequest.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFriendRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(friendRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the FriendRequest in the database
        List<FriendRequest> friendRequestList = friendRequestRepository.findAll();
        assertThat(friendRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFriendRequest() throws Exception {
        int databaseSizeBeforeUpdate = friendRequestRepository.findAll().size();
        friendRequest.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFriendRequestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(friendRequest)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FriendRequest in the database
        List<FriendRequest> friendRequestList = friendRequestRepository.findAll();
        assertThat(friendRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFriendRequestWithPatch() throws Exception {
        // Initialize the database
        friendRequestRepository.saveAndFlush(friendRequest);

        int databaseSizeBeforeUpdate = friendRequestRepository.findAll().size();

        // Update the friendRequest using partial update
        FriendRequest partialUpdatedFriendRequest = new FriendRequest();
        partialUpdatedFriendRequest.setId(friendRequest.getId());

        restFriendRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFriendRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFriendRequest))
            )
            .andExpect(status().isOk());

        // Validate the FriendRequest in the database
        List<FriendRequest> friendRequestList = friendRequestRepository.findAll();
        assertThat(friendRequestList).hasSize(databaseSizeBeforeUpdate);
        FriendRequest testFriendRequest = friendRequestList.get(friendRequestList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateFriendRequestWithPatch() throws Exception {
        // Initialize the database
        friendRequestRepository.saveAndFlush(friendRequest);

        int databaseSizeBeforeUpdate = friendRequestRepository.findAll().size();

        // Update the friendRequest using partial update
        FriendRequest partialUpdatedFriendRequest = new FriendRequest();
        partialUpdatedFriendRequest.setId(friendRequest.getId());

        restFriendRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFriendRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFriendRequest))
            )
            .andExpect(status().isOk());

        // Validate the FriendRequest in the database
        List<FriendRequest> friendRequestList = friendRequestRepository.findAll();
        assertThat(friendRequestList).hasSize(databaseSizeBeforeUpdate);
        FriendRequest testFriendRequest = friendRequestList.get(friendRequestList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingFriendRequest() throws Exception {
        int databaseSizeBeforeUpdate = friendRequestRepository.findAll().size();
        friendRequest.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFriendRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, friendRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(friendRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the FriendRequest in the database
        List<FriendRequest> friendRequestList = friendRequestRepository.findAll();
        assertThat(friendRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFriendRequest() throws Exception {
        int databaseSizeBeforeUpdate = friendRequestRepository.findAll().size();
        friendRequest.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFriendRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(friendRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the FriendRequest in the database
        List<FriendRequest> friendRequestList = friendRequestRepository.findAll();
        assertThat(friendRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFriendRequest() throws Exception {
        int databaseSizeBeforeUpdate = friendRequestRepository.findAll().size();
        friendRequest.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFriendRequestMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(friendRequest))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FriendRequest in the database
        List<FriendRequest> friendRequestList = friendRequestRepository.findAll();
        assertThat(friendRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFriendRequest() throws Exception {
        // Initialize the database
        friendRequestRepository.saveAndFlush(friendRequest);

        int databaseSizeBeforeDelete = friendRequestRepository.findAll().size();

        // Delete the friendRequest
        restFriendRequestMockMvc
            .perform(delete(ENTITY_API_URL_ID, friendRequest.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FriendRequest> friendRequestList = friendRequestRepository.findAll();
        assertThat(friendRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
