package club.profb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import club.profb.IntegrationTest;
import club.profb.domain.NewsPaper;
import club.profb.repository.NewsPaperRepository;
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
 * Integration tests for the {@link NewsPaperResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NewsPaperResourceIT {

    private static final String DEFAULT_NEWS = "AAAAAAAAAA";
    private static final String UPDATED_NEWS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/news-papers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private NewsPaperRepository newsPaperRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNewsPaperMockMvc;

    private NewsPaper newsPaper;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NewsPaper createEntity(EntityManager em) {
        NewsPaper newsPaper = new NewsPaper().news(DEFAULT_NEWS);
        return newsPaper;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NewsPaper createUpdatedEntity(EntityManager em) {
        NewsPaper newsPaper = new NewsPaper().news(UPDATED_NEWS);
        return newsPaper;
    }

    @BeforeEach
    public void initTest() {
        newsPaper = createEntity(em);
    }

    @Test
    @Transactional
    void createNewsPaper() throws Exception {
        int databaseSizeBeforeCreate = newsPaperRepository.findAll().size();
        // Create the NewsPaper
        restNewsPaperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsPaper)))
            .andExpect(status().isCreated());

        // Validate the NewsPaper in the database
        List<NewsPaper> newsPaperList = newsPaperRepository.findAll();
        assertThat(newsPaperList).hasSize(databaseSizeBeforeCreate + 1);
        NewsPaper testNewsPaper = newsPaperList.get(newsPaperList.size() - 1);
        assertThat(testNewsPaper.getNews()).isEqualTo(DEFAULT_NEWS);
    }

    @Test
    @Transactional
    void createNewsPaperWithExistingId() throws Exception {
        // Create the NewsPaper with an existing ID
        newsPaperRepository.saveAndFlush(newsPaper);

        int databaseSizeBeforeCreate = newsPaperRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNewsPaperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsPaper)))
            .andExpect(status().isBadRequest());

        // Validate the NewsPaper in the database
        List<NewsPaper> newsPaperList = newsPaperRepository.findAll();
        assertThat(newsPaperList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNewsIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsPaperRepository.findAll().size();
        // set the field null
        newsPaper.setNews(null);

        // Create the NewsPaper, which fails.

        restNewsPaperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsPaper)))
            .andExpect(status().isBadRequest());

        List<NewsPaper> newsPaperList = newsPaperRepository.findAll();
        assertThat(newsPaperList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNewsPapers() throws Exception {
        // Initialize the database
        newsPaperRepository.saveAndFlush(newsPaper);

        // Get all the newsPaperList
        restNewsPaperMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(newsPaper.getId().toString())))
            .andExpect(jsonPath("$.[*].news").value(hasItem(DEFAULT_NEWS)));
    }

    @Test
    @Transactional
    void getNewsPaper() throws Exception {
        // Initialize the database
        newsPaperRepository.saveAndFlush(newsPaper);

        // Get the newsPaper
        restNewsPaperMockMvc
            .perform(get(ENTITY_API_URL_ID, newsPaper.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(newsPaper.getId().toString()))
            .andExpect(jsonPath("$.news").value(DEFAULT_NEWS));
    }

    @Test
    @Transactional
    void getNonExistingNewsPaper() throws Exception {
        // Get the newsPaper
        restNewsPaperMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNewsPaper() throws Exception {
        // Initialize the database
        newsPaperRepository.saveAndFlush(newsPaper);

        int databaseSizeBeforeUpdate = newsPaperRepository.findAll().size();

        // Update the newsPaper
        NewsPaper updatedNewsPaper = newsPaperRepository.findById(newsPaper.getId()).get();
        // Disconnect from session so that the updates on updatedNewsPaper are not directly saved in db
        em.detach(updatedNewsPaper);
        updatedNewsPaper.news(UPDATED_NEWS);

        restNewsPaperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNewsPaper.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNewsPaper))
            )
            .andExpect(status().isOk());

        // Validate the NewsPaper in the database
        List<NewsPaper> newsPaperList = newsPaperRepository.findAll();
        assertThat(newsPaperList).hasSize(databaseSizeBeforeUpdate);
        NewsPaper testNewsPaper = newsPaperList.get(newsPaperList.size() - 1);
        assertThat(testNewsPaper.getNews()).isEqualTo(UPDATED_NEWS);
    }

    @Test
    @Transactional
    void putNonExistingNewsPaper() throws Exception {
        int databaseSizeBeforeUpdate = newsPaperRepository.findAll().size();
        newsPaper.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNewsPaperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, newsPaper.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(newsPaper))
            )
            .andExpect(status().isBadRequest());

        // Validate the NewsPaper in the database
        List<NewsPaper> newsPaperList = newsPaperRepository.findAll();
        assertThat(newsPaperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNewsPaper() throws Exception {
        int databaseSizeBeforeUpdate = newsPaperRepository.findAll().size();
        newsPaper.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsPaperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(newsPaper))
            )
            .andExpect(status().isBadRequest());

        // Validate the NewsPaper in the database
        List<NewsPaper> newsPaperList = newsPaperRepository.findAll();
        assertThat(newsPaperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNewsPaper() throws Exception {
        int databaseSizeBeforeUpdate = newsPaperRepository.findAll().size();
        newsPaper.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsPaperMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsPaper)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NewsPaper in the database
        List<NewsPaper> newsPaperList = newsPaperRepository.findAll();
        assertThat(newsPaperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNewsPaperWithPatch() throws Exception {
        // Initialize the database
        newsPaperRepository.saveAndFlush(newsPaper);

        int databaseSizeBeforeUpdate = newsPaperRepository.findAll().size();

        // Update the newsPaper using partial update
        NewsPaper partialUpdatedNewsPaper = new NewsPaper();
        partialUpdatedNewsPaper.setId(newsPaper.getId());

        restNewsPaperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNewsPaper.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNewsPaper))
            )
            .andExpect(status().isOk());

        // Validate the NewsPaper in the database
        List<NewsPaper> newsPaperList = newsPaperRepository.findAll();
        assertThat(newsPaperList).hasSize(databaseSizeBeforeUpdate);
        NewsPaper testNewsPaper = newsPaperList.get(newsPaperList.size() - 1);
        assertThat(testNewsPaper.getNews()).isEqualTo(DEFAULT_NEWS);
    }

    @Test
    @Transactional
    void fullUpdateNewsPaperWithPatch() throws Exception {
        // Initialize the database
        newsPaperRepository.saveAndFlush(newsPaper);

        int databaseSizeBeforeUpdate = newsPaperRepository.findAll().size();

        // Update the newsPaper using partial update
        NewsPaper partialUpdatedNewsPaper = new NewsPaper();
        partialUpdatedNewsPaper.setId(newsPaper.getId());

        partialUpdatedNewsPaper.news(UPDATED_NEWS);

        restNewsPaperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNewsPaper.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNewsPaper))
            )
            .andExpect(status().isOk());

        // Validate the NewsPaper in the database
        List<NewsPaper> newsPaperList = newsPaperRepository.findAll();
        assertThat(newsPaperList).hasSize(databaseSizeBeforeUpdate);
        NewsPaper testNewsPaper = newsPaperList.get(newsPaperList.size() - 1);
        assertThat(testNewsPaper.getNews()).isEqualTo(UPDATED_NEWS);
    }

    @Test
    @Transactional
    void patchNonExistingNewsPaper() throws Exception {
        int databaseSizeBeforeUpdate = newsPaperRepository.findAll().size();
        newsPaper.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNewsPaperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, newsPaper.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(newsPaper))
            )
            .andExpect(status().isBadRequest());

        // Validate the NewsPaper in the database
        List<NewsPaper> newsPaperList = newsPaperRepository.findAll();
        assertThat(newsPaperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNewsPaper() throws Exception {
        int databaseSizeBeforeUpdate = newsPaperRepository.findAll().size();
        newsPaper.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsPaperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(newsPaper))
            )
            .andExpect(status().isBadRequest());

        // Validate the NewsPaper in the database
        List<NewsPaper> newsPaperList = newsPaperRepository.findAll();
        assertThat(newsPaperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNewsPaper() throws Exception {
        int databaseSizeBeforeUpdate = newsPaperRepository.findAll().size();
        newsPaper.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsPaperMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(newsPaper))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NewsPaper in the database
        List<NewsPaper> newsPaperList = newsPaperRepository.findAll();
        assertThat(newsPaperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNewsPaper() throws Exception {
        // Initialize the database
        newsPaperRepository.saveAndFlush(newsPaper);

        int databaseSizeBeforeDelete = newsPaperRepository.findAll().size();

        // Delete the newsPaper
        restNewsPaperMockMvc
            .perform(delete(ENTITY_API_URL_ID, newsPaper.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NewsPaper> newsPaperList = newsPaperRepository.findAll();
        assertThat(newsPaperList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
