package club.profb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import club.profb.IntegrationTest;
import club.profb.domain.Coach;
import club.profb.domain.enumeration.Plan;
import club.profb.repository.CoachRepository;
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
 * Integration tests for the {@link CoachResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CoachResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BANNED = false;
    private static final Boolean UPDATED_BANNED = true;

    private static final Boolean DEFAULT_ABANDONED = false;
    private static final Boolean UPDATED_ABANDONED = true;

    private static final Boolean DEFAULT_SUBSCRIBED = false;
    private static final Boolean UPDATED_SUBSCRIBED = true;

    private static final Plan DEFAULT_PLAN = Plan.SIMPLE;
    private static final Plan UPDATED_PLAN = Plan.PREMIUM;

    private static final String ENTITY_API_URL = "/api/coaches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCoachMockMvc;

    private Coach coach;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Coach createEntity(EntityManager em) {
        Coach coach = new Coach()
            .name(DEFAULT_NAME)
            .banned(DEFAULT_BANNED)
            .abandoned(DEFAULT_ABANDONED)
            .subscribed(DEFAULT_SUBSCRIBED)
            .plan(DEFAULT_PLAN);
        return coach;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Coach createUpdatedEntity(EntityManager em) {
        Coach coach = new Coach()
            .name(UPDATED_NAME)
            .banned(UPDATED_BANNED)
            .abandoned(UPDATED_ABANDONED)
            .subscribed(UPDATED_SUBSCRIBED)
            .plan(UPDATED_PLAN);
        return coach;
    }

    @BeforeEach
    public void initTest() {
        coach = createEntity(em);
    }

    @Test
    @Transactional
    void createCoach() throws Exception {
        int databaseSizeBeforeCreate = coachRepository.findAll().size();
        // Create the Coach
        restCoachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(coach)))
            .andExpect(status().isCreated());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeCreate + 1);
        Coach testCoach = coachList.get(coachList.size() - 1);
        assertThat(testCoach.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCoach.getBanned()).isEqualTo(DEFAULT_BANNED);
        assertThat(testCoach.getAbandoned()).isEqualTo(DEFAULT_ABANDONED);
        assertThat(testCoach.getSubscribed()).isEqualTo(DEFAULT_SUBSCRIBED);
        assertThat(testCoach.getPlan()).isEqualTo(DEFAULT_PLAN);
    }

    @Test
    @Transactional
    void createCoachWithExistingId() throws Exception {
        // Create the Coach with an existing ID
        coachRepository.saveAndFlush(coach);

        int databaseSizeBeforeCreate = coachRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(coach)))
            .andExpect(status().isBadRequest());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = coachRepository.findAll().size();
        // set the field null
        coach.setName(null);

        // Create the Coach, which fails.

        restCoachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(coach)))
            .andExpect(status().isBadRequest());

        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBannedIsRequired() throws Exception {
        int databaseSizeBeforeTest = coachRepository.findAll().size();
        // set the field null
        coach.setBanned(null);

        // Create the Coach, which fails.

        restCoachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(coach)))
            .andExpect(status().isBadRequest());

        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAbandonedIsRequired() throws Exception {
        int databaseSizeBeforeTest = coachRepository.findAll().size();
        // set the field null
        coach.setAbandoned(null);

        // Create the Coach, which fails.

        restCoachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(coach)))
            .andExpect(status().isBadRequest());

        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSubscribedIsRequired() throws Exception {
        int databaseSizeBeforeTest = coachRepository.findAll().size();
        // set the field null
        coach.setSubscribed(null);

        // Create the Coach, which fails.

        restCoachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(coach)))
            .andExpect(status().isBadRequest());

        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPlanIsRequired() throws Exception {
        int databaseSizeBeforeTest = coachRepository.findAll().size();
        // set the field null
        coach.setPlan(null);

        // Create the Coach, which fails.

        restCoachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(coach)))
            .andExpect(status().isBadRequest());

        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCoaches() throws Exception {
        // Initialize the database
        coachRepository.saveAndFlush(coach);

        // Get all the coachList
        restCoachMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coach.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].banned").value(hasItem(DEFAULT_BANNED.booleanValue())))
            .andExpect(jsonPath("$.[*].abandoned").value(hasItem(DEFAULT_ABANDONED.booleanValue())))
            .andExpect(jsonPath("$.[*].subscribed").value(hasItem(DEFAULT_SUBSCRIBED.booleanValue())))
            .andExpect(jsonPath("$.[*].plan").value(hasItem(DEFAULT_PLAN.toString())));
    }

    @Test
    @Transactional
    void getCoach() throws Exception {
        // Initialize the database
        coachRepository.saveAndFlush(coach);

        // Get the coach
        restCoachMockMvc
            .perform(get(ENTITY_API_URL_ID, coach.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(coach.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.banned").value(DEFAULT_BANNED.booleanValue()))
            .andExpect(jsonPath("$.abandoned").value(DEFAULT_ABANDONED.booleanValue()))
            .andExpect(jsonPath("$.subscribed").value(DEFAULT_SUBSCRIBED.booleanValue()))
            .andExpect(jsonPath("$.plan").value(DEFAULT_PLAN.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCoach() throws Exception {
        // Get the coach
        restCoachMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCoach() throws Exception {
        // Initialize the database
        coachRepository.saveAndFlush(coach);

        int databaseSizeBeforeUpdate = coachRepository.findAll().size();

        // Update the coach
        Coach updatedCoach = coachRepository.findById(coach.getId()).get();
        // Disconnect from session so that the updates on updatedCoach are not directly saved in db
        em.detach(updatedCoach);
        updatedCoach
            .name(UPDATED_NAME)
            .banned(UPDATED_BANNED)
            .abandoned(UPDATED_ABANDONED)
            .subscribed(UPDATED_SUBSCRIBED)
            .plan(UPDATED_PLAN);

        restCoachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCoach.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCoach))
            )
            .andExpect(status().isOk());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeUpdate);
        Coach testCoach = coachList.get(coachList.size() - 1);
        assertThat(testCoach.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCoach.getBanned()).isEqualTo(UPDATED_BANNED);
        assertThat(testCoach.getAbandoned()).isEqualTo(UPDATED_ABANDONED);
        assertThat(testCoach.getSubscribed()).isEqualTo(UPDATED_SUBSCRIBED);
        assertThat(testCoach.getPlan()).isEqualTo(UPDATED_PLAN);
    }

    @Test
    @Transactional
    void putNonExistingCoach() throws Exception {
        int databaseSizeBeforeUpdate = coachRepository.findAll().size();
        coach.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, coach.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCoach() throws Exception {
        int databaseSizeBeforeUpdate = coachRepository.findAll().size();
        coach.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCoach() throws Exception {
        int databaseSizeBeforeUpdate = coachRepository.findAll().size();
        coach.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoachMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(coach)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCoachWithPatch() throws Exception {
        // Initialize the database
        coachRepository.saveAndFlush(coach);

        int databaseSizeBeforeUpdate = coachRepository.findAll().size();

        // Update the coach using partial update
        Coach partialUpdatedCoach = new Coach();
        partialUpdatedCoach.setId(coach.getId());

        partialUpdatedCoach.abandoned(UPDATED_ABANDONED);

        restCoachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCoach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCoach))
            )
            .andExpect(status().isOk());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeUpdate);
        Coach testCoach = coachList.get(coachList.size() - 1);
        assertThat(testCoach.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCoach.getBanned()).isEqualTo(DEFAULT_BANNED);
        assertThat(testCoach.getAbandoned()).isEqualTo(UPDATED_ABANDONED);
        assertThat(testCoach.getSubscribed()).isEqualTo(DEFAULT_SUBSCRIBED);
        assertThat(testCoach.getPlan()).isEqualTo(DEFAULT_PLAN);
    }

    @Test
    @Transactional
    void fullUpdateCoachWithPatch() throws Exception {
        // Initialize the database
        coachRepository.saveAndFlush(coach);

        int databaseSizeBeforeUpdate = coachRepository.findAll().size();

        // Update the coach using partial update
        Coach partialUpdatedCoach = new Coach();
        partialUpdatedCoach.setId(coach.getId());

        partialUpdatedCoach
            .name(UPDATED_NAME)
            .banned(UPDATED_BANNED)
            .abandoned(UPDATED_ABANDONED)
            .subscribed(UPDATED_SUBSCRIBED)
            .plan(UPDATED_PLAN);

        restCoachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCoach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCoach))
            )
            .andExpect(status().isOk());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeUpdate);
        Coach testCoach = coachList.get(coachList.size() - 1);
        assertThat(testCoach.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCoach.getBanned()).isEqualTo(UPDATED_BANNED);
        assertThat(testCoach.getAbandoned()).isEqualTo(UPDATED_ABANDONED);
        assertThat(testCoach.getSubscribed()).isEqualTo(UPDATED_SUBSCRIBED);
        assertThat(testCoach.getPlan()).isEqualTo(UPDATED_PLAN);
    }

    @Test
    @Transactional
    void patchNonExistingCoach() throws Exception {
        int databaseSizeBeforeUpdate = coachRepository.findAll().size();
        coach.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, coach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(coach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCoach() throws Exception {
        int databaseSizeBeforeUpdate = coachRepository.findAll().size();
        coach.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(coach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCoach() throws Exception {
        int databaseSizeBeforeUpdate = coachRepository.findAll().size();
        coach.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoachMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(coach)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCoach() throws Exception {
        // Initialize the database
        coachRepository.saveAndFlush(coach);

        int databaseSizeBeforeDelete = coachRepository.findAll().size();

        // Delete the coach
        restCoachMockMvc
            .perform(delete(ENTITY_API_URL_ID, coach.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
