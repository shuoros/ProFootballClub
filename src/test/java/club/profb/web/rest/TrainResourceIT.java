package club.profb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import club.profb.IntegrationTest;
import club.profb.domain.Train;
import club.profb.domain.enumeration.Training;
import club.profb.repository.TrainRepository;
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
 * Integration tests for the {@link TrainResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrainResourceIT {

    private static final Training DEFAULT_TRAINING = Training.GOALKEEPING;
    private static final Training UPDATED_TRAINING = Training.PENALTY_DEFENCE;

    private static final Instant DEFAULT_FINISHES = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FINISHES = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/trains";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrainMockMvc;

    private Train train;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Train createEntity(EntityManager em) {
        Train train = new Train().training(DEFAULT_TRAINING).finishes(DEFAULT_FINISHES);
        return train;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Train createUpdatedEntity(EntityManager em) {
        Train train = new Train().training(UPDATED_TRAINING).finishes(UPDATED_FINISHES);
        return train;
    }

    @BeforeEach
    public void initTest() {
        train = createEntity(em);
    }

    @Test
    @Transactional
    void createTrain() throws Exception {
        int databaseSizeBeforeCreate = trainRepository.findAll().size();
        // Create the Train
        restTrainMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(train)))
            .andExpect(status().isCreated());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeCreate + 1);
        Train testTrain = trainList.get(trainList.size() - 1);
        assertThat(testTrain.getTraining()).isEqualTo(DEFAULT_TRAINING);
        assertThat(testTrain.getFinishes()).isEqualTo(DEFAULT_FINISHES);
    }

    @Test
    @Transactional
    void createTrainWithExistingId() throws Exception {
        // Create the Train with an existing ID
        trainRepository.saveAndFlush(train);

        int databaseSizeBeforeCreate = trainRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(train)))
            .andExpect(status().isBadRequest());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTrains() throws Exception {
        // Initialize the database
        trainRepository.saveAndFlush(train);

        // Get all the trainList
        restTrainMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(train.getId().toString())))
            .andExpect(jsonPath("$.[*].training").value(hasItem(DEFAULT_TRAINING.toString())))
            .andExpect(jsonPath("$.[*].finishes").value(hasItem(DEFAULT_FINISHES.toString())));
    }

    @Test
    @Transactional
    void getTrain() throws Exception {
        // Initialize the database
        trainRepository.saveAndFlush(train);

        // Get the train
        restTrainMockMvc
            .perform(get(ENTITY_API_URL_ID, train.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(train.getId().toString()))
            .andExpect(jsonPath("$.training").value(DEFAULT_TRAINING.toString()))
            .andExpect(jsonPath("$.finishes").value(DEFAULT_FINISHES.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTrain() throws Exception {
        // Get the train
        restTrainMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTrain() throws Exception {
        // Initialize the database
        trainRepository.saveAndFlush(train);

        int databaseSizeBeforeUpdate = trainRepository.findAll().size();

        // Update the train
        Train updatedTrain = trainRepository.findById(train.getId()).get();
        // Disconnect from session so that the updates on updatedTrain are not directly saved in db
        em.detach(updatedTrain);
        updatedTrain.training(UPDATED_TRAINING).finishes(UPDATED_FINISHES);

        restTrainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTrain.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTrain))
            )
            .andExpect(status().isOk());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeUpdate);
        Train testTrain = trainList.get(trainList.size() - 1);
        assertThat(testTrain.getTraining()).isEqualTo(UPDATED_TRAINING);
        assertThat(testTrain.getFinishes()).isEqualTo(UPDATED_FINISHES);
    }

    @Test
    @Transactional
    void putNonExistingTrain() throws Exception {
        int databaseSizeBeforeUpdate = trainRepository.findAll().size();
        train.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, train.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(train))
            )
            .andExpect(status().isBadRequest());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrain() throws Exception {
        int databaseSizeBeforeUpdate = trainRepository.findAll().size();
        train.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(train))
            )
            .andExpect(status().isBadRequest());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrain() throws Exception {
        int databaseSizeBeforeUpdate = trainRepository.findAll().size();
        train.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(train)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrainWithPatch() throws Exception {
        // Initialize the database
        trainRepository.saveAndFlush(train);

        int databaseSizeBeforeUpdate = trainRepository.findAll().size();

        // Update the train using partial update
        Train partialUpdatedTrain = new Train();
        partialUpdatedTrain.setId(train.getId());

        partialUpdatedTrain.training(UPDATED_TRAINING).finishes(UPDATED_FINISHES);

        restTrainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrain))
            )
            .andExpect(status().isOk());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeUpdate);
        Train testTrain = trainList.get(trainList.size() - 1);
        assertThat(testTrain.getTraining()).isEqualTo(UPDATED_TRAINING);
        assertThat(testTrain.getFinishes()).isEqualTo(UPDATED_FINISHES);
    }

    @Test
    @Transactional
    void fullUpdateTrainWithPatch() throws Exception {
        // Initialize the database
        trainRepository.saveAndFlush(train);

        int databaseSizeBeforeUpdate = trainRepository.findAll().size();

        // Update the train using partial update
        Train partialUpdatedTrain = new Train();
        partialUpdatedTrain.setId(train.getId());

        partialUpdatedTrain.training(UPDATED_TRAINING).finishes(UPDATED_FINISHES);

        restTrainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrain))
            )
            .andExpect(status().isOk());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeUpdate);
        Train testTrain = trainList.get(trainList.size() - 1);
        assertThat(testTrain.getTraining()).isEqualTo(UPDATED_TRAINING);
        assertThat(testTrain.getFinishes()).isEqualTo(UPDATED_FINISHES);
    }

    @Test
    @Transactional
    void patchNonExistingTrain() throws Exception {
        int databaseSizeBeforeUpdate = trainRepository.findAll().size();
        train.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, train.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(train))
            )
            .andExpect(status().isBadRequest());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrain() throws Exception {
        int databaseSizeBeforeUpdate = trainRepository.findAll().size();
        train.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(train))
            )
            .andExpect(status().isBadRequest());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrain() throws Exception {
        int databaseSizeBeforeUpdate = trainRepository.findAll().size();
        train.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(train)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrain() throws Exception {
        // Initialize the database
        trainRepository.saveAndFlush(train);

        int databaseSizeBeforeDelete = trainRepository.findAll().size();

        // Delete the train
        restTrainMockMvc
            .perform(delete(ENTITY_API_URL_ID, train.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
