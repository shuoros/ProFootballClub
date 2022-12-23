package club.profb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import club.profb.IntegrationTest;
import club.profb.domain.Cup;
import club.profb.domain.enumeration.CupType;
import club.profb.repository.CupRepository;
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
 * Integration tests for the {@link CupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CupResourceIT {

    private static final CupType DEFAULT_TYPE = CupType.T4;
    private static final CupType UPDATED_TYPE = CupType.T8;

    private static final String DEFAULT_EVENTS = "AAAAAAAAAA";
    private static final String UPDATED_EVENTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FINISHED = false;
    private static final Boolean UPDATED_FINISHED = true;

    private static final Integer DEFAULT_ENTRANCE = 1;
    private static final Integer UPDATED_ENTRANCE = 2;

    private static final Instant DEFAULT_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/cups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CupRepository cupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCupMockMvc;

    private Cup cup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cup createEntity(EntityManager em) {
        Cup cup = new Cup()
            .type(DEFAULT_TYPE)
            .events(DEFAULT_EVENTS)
            .finished(DEFAULT_FINISHED)
            .entrance(DEFAULT_ENTRANCE)
            .start(DEFAULT_START);
        return cup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cup createUpdatedEntity(EntityManager em) {
        Cup cup = new Cup()
            .type(UPDATED_TYPE)
            .events(UPDATED_EVENTS)
            .finished(UPDATED_FINISHED)
            .entrance(UPDATED_ENTRANCE)
            .start(UPDATED_START);
        return cup;
    }

    @BeforeEach
    public void initTest() {
        cup = createEntity(em);
    }

    @Test
    @Transactional
    void createCup() throws Exception {
        int databaseSizeBeforeCreate = cupRepository.findAll().size();
        // Create the Cup
        restCupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cup)))
            .andExpect(status().isCreated());

        // Validate the Cup in the database
        List<Cup> cupList = cupRepository.findAll();
        assertThat(cupList).hasSize(databaseSizeBeforeCreate + 1);
        Cup testCup = cupList.get(cupList.size() - 1);
        assertThat(testCup.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCup.getEvents()).isEqualTo(DEFAULT_EVENTS);
        assertThat(testCup.getFinished()).isEqualTo(DEFAULT_FINISHED);
        assertThat(testCup.getEntrance()).isEqualTo(DEFAULT_ENTRANCE);
        assertThat(testCup.getStart()).isEqualTo(DEFAULT_START);
    }

    @Test
    @Transactional
    void createCupWithExistingId() throws Exception {
        // Create the Cup with an existing ID
        cupRepository.saveAndFlush(cup);

        int databaseSizeBeforeCreate = cupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cup)))
            .andExpect(status().isBadRequest());

        // Validate the Cup in the database
        List<Cup> cupList = cupRepository.findAll();
        assertThat(cupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cupRepository.findAll().size();
        // set the field null
        cup.setType(null);

        // Create the Cup, which fails.

        restCupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cup)))
            .andExpect(status().isBadRequest());

        List<Cup> cupList = cupRepository.findAll();
        assertThat(cupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEntranceIsRequired() throws Exception {
        int databaseSizeBeforeTest = cupRepository.findAll().size();
        // set the field null
        cup.setEntrance(null);

        // Create the Cup, which fails.

        restCupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cup)))
            .andExpect(status().isBadRequest());

        List<Cup> cupList = cupRepository.findAll();
        assertThat(cupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCups() throws Exception {
        // Initialize the database
        cupRepository.saveAndFlush(cup);

        // Get all the cupList
        restCupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cup.getId().toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].events").value(hasItem(DEFAULT_EVENTS)))
            .andExpect(jsonPath("$.[*].finished").value(hasItem(DEFAULT_FINISHED.booleanValue())))
            .andExpect(jsonPath("$.[*].entrance").value(hasItem(DEFAULT_ENTRANCE)))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())));
    }

    @Test
    @Transactional
    void getCup() throws Exception {
        // Initialize the database
        cupRepository.saveAndFlush(cup);

        // Get the cup
        restCupMockMvc
            .perform(get(ENTITY_API_URL_ID, cup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cup.getId().toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.events").value(DEFAULT_EVENTS))
            .andExpect(jsonPath("$.finished").value(DEFAULT_FINISHED.booleanValue()))
            .andExpect(jsonPath("$.entrance").value(DEFAULT_ENTRANCE))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCup() throws Exception {
        // Get the cup
        restCupMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCup() throws Exception {
        // Initialize the database
        cupRepository.saveAndFlush(cup);

        int databaseSizeBeforeUpdate = cupRepository.findAll().size();

        // Update the cup
        Cup updatedCup = cupRepository.findById(cup.getId()).get();
        // Disconnect from session so that the updates on updatedCup are not directly saved in db
        em.detach(updatedCup);
        updatedCup.type(UPDATED_TYPE).events(UPDATED_EVENTS).finished(UPDATED_FINISHED).entrance(UPDATED_ENTRANCE).start(UPDATED_START);

        restCupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCup))
            )
            .andExpect(status().isOk());

        // Validate the Cup in the database
        List<Cup> cupList = cupRepository.findAll();
        assertThat(cupList).hasSize(databaseSizeBeforeUpdate);
        Cup testCup = cupList.get(cupList.size() - 1);
        assertThat(testCup.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCup.getEvents()).isEqualTo(UPDATED_EVENTS);
        assertThat(testCup.getFinished()).isEqualTo(UPDATED_FINISHED);
        assertThat(testCup.getEntrance()).isEqualTo(UPDATED_ENTRANCE);
        assertThat(testCup.getStart()).isEqualTo(UPDATED_START);
    }

    @Test
    @Transactional
    void putNonExistingCup() throws Exception {
        int databaseSizeBeforeUpdate = cupRepository.findAll().size();
        cup.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cup.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cup))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cup in the database
        List<Cup> cupList = cupRepository.findAll();
        assertThat(cupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCup() throws Exception {
        int databaseSizeBeforeUpdate = cupRepository.findAll().size();
        cup.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cup))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cup in the database
        List<Cup> cupList = cupRepository.findAll();
        assertThat(cupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCup() throws Exception {
        int databaseSizeBeforeUpdate = cupRepository.findAll().size();
        cup.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cup in the database
        List<Cup> cupList = cupRepository.findAll();
        assertThat(cupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCupWithPatch() throws Exception {
        // Initialize the database
        cupRepository.saveAndFlush(cup);

        int databaseSizeBeforeUpdate = cupRepository.findAll().size();

        // Update the cup using partial update
        Cup partialUpdatedCup = new Cup();
        partialUpdatedCup.setId(cup.getId());

        partialUpdatedCup.start(UPDATED_START);

        restCupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCup))
            )
            .andExpect(status().isOk());

        // Validate the Cup in the database
        List<Cup> cupList = cupRepository.findAll();
        assertThat(cupList).hasSize(databaseSizeBeforeUpdate);
        Cup testCup = cupList.get(cupList.size() - 1);
        assertThat(testCup.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCup.getEvents()).isEqualTo(DEFAULT_EVENTS);
        assertThat(testCup.getFinished()).isEqualTo(DEFAULT_FINISHED);
        assertThat(testCup.getEntrance()).isEqualTo(DEFAULT_ENTRANCE);
        assertThat(testCup.getStart()).isEqualTo(UPDATED_START);
    }

    @Test
    @Transactional
    void fullUpdateCupWithPatch() throws Exception {
        // Initialize the database
        cupRepository.saveAndFlush(cup);

        int databaseSizeBeforeUpdate = cupRepository.findAll().size();

        // Update the cup using partial update
        Cup partialUpdatedCup = new Cup();
        partialUpdatedCup.setId(cup.getId());

        partialUpdatedCup
            .type(UPDATED_TYPE)
            .events(UPDATED_EVENTS)
            .finished(UPDATED_FINISHED)
            .entrance(UPDATED_ENTRANCE)
            .start(UPDATED_START);

        restCupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCup))
            )
            .andExpect(status().isOk());

        // Validate the Cup in the database
        List<Cup> cupList = cupRepository.findAll();
        assertThat(cupList).hasSize(databaseSizeBeforeUpdate);
        Cup testCup = cupList.get(cupList.size() - 1);
        assertThat(testCup.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCup.getEvents()).isEqualTo(UPDATED_EVENTS);
        assertThat(testCup.getFinished()).isEqualTo(UPDATED_FINISHED);
        assertThat(testCup.getEntrance()).isEqualTo(UPDATED_ENTRANCE);
        assertThat(testCup.getStart()).isEqualTo(UPDATED_START);
    }

    @Test
    @Transactional
    void patchNonExistingCup() throws Exception {
        int databaseSizeBeforeUpdate = cupRepository.findAll().size();
        cup.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cup))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cup in the database
        List<Cup> cupList = cupRepository.findAll();
        assertThat(cupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCup() throws Exception {
        int databaseSizeBeforeUpdate = cupRepository.findAll().size();
        cup.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cup))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cup in the database
        List<Cup> cupList = cupRepository.findAll();
        assertThat(cupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCup() throws Exception {
        int databaseSizeBeforeUpdate = cupRepository.findAll().size();
        cup.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCupMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cup in the database
        List<Cup> cupList = cupRepository.findAll();
        assertThat(cupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCup() throws Exception {
        // Initialize the database
        cupRepository.saveAndFlush(cup);

        int databaseSizeBeforeDelete = cupRepository.findAll().size();

        // Delete the cup
        restCupMockMvc
            .perform(delete(ENTITY_API_URL_ID, cup.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cup> cupList = cupRepository.findAll();
        assertThat(cupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
