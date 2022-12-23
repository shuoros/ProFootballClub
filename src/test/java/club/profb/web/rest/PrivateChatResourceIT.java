package club.profb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import club.profb.IntegrationTest;
import club.profb.domain.PrivateChat;
import club.profb.repository.PrivateChatRepository;
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
 * Integration tests for the {@link PrivateChatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PrivateChatResourceIT {

    private static final String ENTITY_API_URL = "/api/private-chats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PrivateChatRepository privateChatRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrivateChatMockMvc;

    private PrivateChat privateChat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrivateChat createEntity(EntityManager em) {
        PrivateChat privateChat = new PrivateChat();
        return privateChat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrivateChat createUpdatedEntity(EntityManager em) {
        PrivateChat privateChat = new PrivateChat();
        return privateChat;
    }

    @BeforeEach
    public void initTest() {
        privateChat = createEntity(em);
    }

    @Test
    @Transactional
    void createPrivateChat() throws Exception {
        int databaseSizeBeforeCreate = privateChatRepository.findAll().size();
        // Create the PrivateChat
        restPrivateChatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(privateChat)))
            .andExpect(status().isCreated());

        // Validate the PrivateChat in the database
        List<PrivateChat> privateChatList = privateChatRepository.findAll();
        assertThat(privateChatList).hasSize(databaseSizeBeforeCreate + 1);
        PrivateChat testPrivateChat = privateChatList.get(privateChatList.size() - 1);
    }

    @Test
    @Transactional
    void createPrivateChatWithExistingId() throws Exception {
        // Create the PrivateChat with an existing ID
        privateChatRepository.saveAndFlush(privateChat);

        int databaseSizeBeforeCreate = privateChatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrivateChatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(privateChat)))
            .andExpect(status().isBadRequest());

        // Validate the PrivateChat in the database
        List<PrivateChat> privateChatList = privateChatRepository.findAll();
        assertThat(privateChatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPrivateChats() throws Exception {
        // Initialize the database
        privateChatRepository.saveAndFlush(privateChat);

        // Get all the privateChatList
        restPrivateChatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(privateChat.getId().toString())));
    }

    @Test
    @Transactional
    void getPrivateChat() throws Exception {
        // Initialize the database
        privateChatRepository.saveAndFlush(privateChat);

        // Get the privateChat
        restPrivateChatMockMvc
            .perform(get(ENTITY_API_URL_ID, privateChat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(privateChat.getId().toString()));
    }

    @Test
    @Transactional
    void getNonExistingPrivateChat() throws Exception {
        // Get the privateChat
        restPrivateChatMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPrivateChat() throws Exception {
        // Initialize the database
        privateChatRepository.saveAndFlush(privateChat);

        int databaseSizeBeforeUpdate = privateChatRepository.findAll().size();

        // Update the privateChat
        PrivateChat updatedPrivateChat = privateChatRepository.findById(privateChat.getId()).get();
        // Disconnect from session so that the updates on updatedPrivateChat are not directly saved in db
        em.detach(updatedPrivateChat);

        restPrivateChatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPrivateChat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPrivateChat))
            )
            .andExpect(status().isOk());

        // Validate the PrivateChat in the database
        List<PrivateChat> privateChatList = privateChatRepository.findAll();
        assertThat(privateChatList).hasSize(databaseSizeBeforeUpdate);
        PrivateChat testPrivateChat = privateChatList.get(privateChatList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingPrivateChat() throws Exception {
        int databaseSizeBeforeUpdate = privateChatRepository.findAll().size();
        privateChat.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrivateChatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, privateChat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(privateChat))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrivateChat in the database
        List<PrivateChat> privateChatList = privateChatRepository.findAll();
        assertThat(privateChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrivateChat() throws Exception {
        int databaseSizeBeforeUpdate = privateChatRepository.findAll().size();
        privateChat.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrivateChatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(privateChat))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrivateChat in the database
        List<PrivateChat> privateChatList = privateChatRepository.findAll();
        assertThat(privateChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrivateChat() throws Exception {
        int databaseSizeBeforeUpdate = privateChatRepository.findAll().size();
        privateChat.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrivateChatMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(privateChat)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrivateChat in the database
        List<PrivateChat> privateChatList = privateChatRepository.findAll();
        assertThat(privateChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrivateChatWithPatch() throws Exception {
        // Initialize the database
        privateChatRepository.saveAndFlush(privateChat);

        int databaseSizeBeforeUpdate = privateChatRepository.findAll().size();

        // Update the privateChat using partial update
        PrivateChat partialUpdatedPrivateChat = new PrivateChat();
        partialUpdatedPrivateChat.setId(privateChat.getId());

        restPrivateChatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrivateChat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrivateChat))
            )
            .andExpect(status().isOk());

        // Validate the PrivateChat in the database
        List<PrivateChat> privateChatList = privateChatRepository.findAll();
        assertThat(privateChatList).hasSize(databaseSizeBeforeUpdate);
        PrivateChat testPrivateChat = privateChatList.get(privateChatList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdatePrivateChatWithPatch() throws Exception {
        // Initialize the database
        privateChatRepository.saveAndFlush(privateChat);

        int databaseSizeBeforeUpdate = privateChatRepository.findAll().size();

        // Update the privateChat using partial update
        PrivateChat partialUpdatedPrivateChat = new PrivateChat();
        partialUpdatedPrivateChat.setId(privateChat.getId());

        restPrivateChatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrivateChat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrivateChat))
            )
            .andExpect(status().isOk());

        // Validate the PrivateChat in the database
        List<PrivateChat> privateChatList = privateChatRepository.findAll();
        assertThat(privateChatList).hasSize(databaseSizeBeforeUpdate);
        PrivateChat testPrivateChat = privateChatList.get(privateChatList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingPrivateChat() throws Exception {
        int databaseSizeBeforeUpdate = privateChatRepository.findAll().size();
        privateChat.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrivateChatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, privateChat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(privateChat))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrivateChat in the database
        List<PrivateChat> privateChatList = privateChatRepository.findAll();
        assertThat(privateChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrivateChat() throws Exception {
        int databaseSizeBeforeUpdate = privateChatRepository.findAll().size();
        privateChat.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrivateChatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(privateChat))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrivateChat in the database
        List<PrivateChat> privateChatList = privateChatRepository.findAll();
        assertThat(privateChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrivateChat() throws Exception {
        int databaseSizeBeforeUpdate = privateChatRepository.findAll().size();
        privateChat.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrivateChatMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(privateChat))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrivateChat in the database
        List<PrivateChat> privateChatList = privateChatRepository.findAll();
        assertThat(privateChatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrivateChat() throws Exception {
        // Initialize the database
        privateChatRepository.saveAndFlush(privateChat);

        int databaseSizeBeforeDelete = privateChatRepository.findAll().size();

        // Delete the privateChat
        restPrivateChatMockMvc
            .perform(delete(ENTITY_API_URL_ID, privateChat.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrivateChat> privateChatList = privateChatRepository.findAll();
        assertThat(privateChatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
