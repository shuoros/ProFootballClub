package club.profb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import club.profb.IntegrationTest;
import club.profb.domain.PublicChat;
import club.profb.repository.PublicChatRepository;
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
 * Integration tests for the {@link PublicChatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PublicChatResourceIT {

    private static final String ENTITY_API_URL = "/api/public-chats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PublicChatRepository publicChatRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPublicChatMockMvc;

    private PublicChat publicChat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublicChat createEntity(EntityManager em) {
        PublicChat publicChat = new PublicChat();
        return publicChat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublicChat createUpdatedEntity(EntityManager em) {
        PublicChat publicChat = new PublicChat();
        return publicChat;
    }

    @BeforeEach
    public void initTest() {
        publicChat = createEntity(em);
    }

    @Test
    @Transactional
    void createPublicChat() throws Exception {
        int databaseSizeBeforeCreate = publicChatRepository.findAll().size();
        // Create the PublicChat
        restPublicChatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publicChat)))
            .andExpect(status().isCreated());

        // Validate the PublicChat in the database
        List<PublicChat> publicChatList = publicChatRepository.findAll();
        assertThat(publicChatList).hasSize(databaseSizeBeforeCreate + 1);
        PublicChat testPublicChat = publicChatList.get(publicChatList.size() - 1);
    }

    @Test
    @Transactional
    void createPublicChatWithExistingId() throws Exception {
        // Create the PublicChat with an existing ID
        publicChatRepository.saveAndFlush(publicChat);

        int databaseSizeBeforeCreate = publicChatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicChatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publicChat)))
            .andExpect(status().isBadRequest());

        // Validate the PublicChat in the database
        List<PublicChat> publicChatList = publicChatRepository.findAll();
        assertThat(publicChatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPublicChats() throws Exception {
        // Initialize the database
        publicChatRepository.saveAndFlush(publicChat);

        // Get all the publicChatList
        restPublicChatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicChat.getId().toString())));
    }

    @Test
    @Transactional
    void getPublicChat() throws Exception {
        // Initialize the database
        publicChatRepository.saveAndFlush(publicChat);

        // Get the publicChat
        restPublicChatMockMvc
            .perform(get(ENTITY_API_URL_ID, publicChat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(publicChat.getId().toString()));
    }

    @Test
    @Transactional
    void getNonExistingPublicChat() throws Exception {
        // Get the publicChat
        restPublicChatMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPublicChat() throws Exception {
        // Initialize the database
        publicChatRepository.saveAndFlush(publicChat);

        int databaseSizeBeforeUpdate = publicChatRepository.findAll().size();

        // Update the publicChat
        PublicChat updatedPublicChat = publicChatRepository.findById(publicChat.getId()).get();
        // Disconnect from session so that the updates on updatedPublicChat are not directly saved in db
        em.detach(updatedPublicChat);

        restPublicChatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPublicChat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPublicChat))
            )
            .andExpect(status().isOk());

        // Validate the PublicChat in the database
        List<PublicChat> publicChatList = publicChatRepository.findAll();
        assertThat(publicChatList).hasSize(databaseSizeBeforeUpdate);
        PublicChat testPublicChat = publicChatList.get(publicChatList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingPublicChat() throws Exception {
        int databaseSizeBeforeUpdate = publicChatRepository.findAll().size();
        publicChat.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicChatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publicChat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publicChat))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicChat in the database
        List<PublicChat> publicChatList = publicChatRepository.findAll();
        assertThat(publicChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPublicChat() throws Exception {
        int databaseSizeBeforeUpdate = publicChatRepository.findAll().size();
        publicChat.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicChatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publicChat))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicChat in the database
        List<PublicChat> publicChatList = publicChatRepository.findAll();
        assertThat(publicChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPublicChat() throws Exception {
        int databaseSizeBeforeUpdate = publicChatRepository.findAll().size();
        publicChat.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicChatMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publicChat)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PublicChat in the database
        List<PublicChat> publicChatList = publicChatRepository.findAll();
        assertThat(publicChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePublicChatWithPatch() throws Exception {
        // Initialize the database
        publicChatRepository.saveAndFlush(publicChat);

        int databaseSizeBeforeUpdate = publicChatRepository.findAll().size();

        // Update the publicChat using partial update
        PublicChat partialUpdatedPublicChat = new PublicChat();
        partialUpdatedPublicChat.setId(publicChat.getId());

        restPublicChatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublicChat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPublicChat))
            )
            .andExpect(status().isOk());

        // Validate the PublicChat in the database
        List<PublicChat> publicChatList = publicChatRepository.findAll();
        assertThat(publicChatList).hasSize(databaseSizeBeforeUpdate);
        PublicChat testPublicChat = publicChatList.get(publicChatList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdatePublicChatWithPatch() throws Exception {
        // Initialize the database
        publicChatRepository.saveAndFlush(publicChat);

        int databaseSizeBeforeUpdate = publicChatRepository.findAll().size();

        // Update the publicChat using partial update
        PublicChat partialUpdatedPublicChat = new PublicChat();
        partialUpdatedPublicChat.setId(publicChat.getId());

        restPublicChatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublicChat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPublicChat))
            )
            .andExpect(status().isOk());

        // Validate the PublicChat in the database
        List<PublicChat> publicChatList = publicChatRepository.findAll();
        assertThat(publicChatList).hasSize(databaseSizeBeforeUpdate);
        PublicChat testPublicChat = publicChatList.get(publicChatList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingPublicChat() throws Exception {
        int databaseSizeBeforeUpdate = publicChatRepository.findAll().size();
        publicChat.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicChatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, publicChat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(publicChat))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicChat in the database
        List<PublicChat> publicChatList = publicChatRepository.findAll();
        assertThat(publicChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPublicChat() throws Exception {
        int databaseSizeBeforeUpdate = publicChatRepository.findAll().size();
        publicChat.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicChatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(publicChat))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicChat in the database
        List<PublicChat> publicChatList = publicChatRepository.findAll();
        assertThat(publicChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPublicChat() throws Exception {
        int databaseSizeBeforeUpdate = publicChatRepository.findAll().size();
        publicChat.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicChatMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(publicChat))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PublicChat in the database
        List<PublicChat> publicChatList = publicChatRepository.findAll();
        assertThat(publicChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePublicChat() throws Exception {
        // Initialize the database
        publicChatRepository.saveAndFlush(publicChat);

        int databaseSizeBeforeDelete = publicChatRepository.findAll().size();

        // Delete the publicChat
        restPublicChatMockMvc
            .perform(delete(ENTITY_API_URL_ID, publicChat.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PublicChat> publicChatList = publicChatRepository.findAll();
        assertThat(publicChatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
