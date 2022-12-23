package club.profb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import club.profb.IntegrationTest;
import club.profb.domain.Stadium;
import club.profb.domain.enumeration.Assistant;
import club.profb.domain.enumeration.BodyBuilding;
import club.profb.domain.enumeration.Chair;
import club.profb.domain.enumeration.Doctor;
import club.profb.domain.enumeration.Field;
import club.profb.domain.enumeration.Light;
import club.profb.domain.enumeration.Vehicle;
import club.profb.repository.StadiumRepository;
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
 * Integration tests for the {@link StadiumResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StadiumResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAPACITY = 1;
    private static final Integer UPDATED_CAPACITY = 2;

    private static final Integer DEFAULT_TICKET = 1;
    private static final Integer UPDATED_TICKET = 2;

    private static final Instant DEFAULT_LEADER = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LEADER = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Vehicle DEFAULT_VEHICLE = Vehicle.BUS;
    private static final Vehicle UPDATED_VEHICLE = Vehicle.TRAIN;

    private static final Field DEFAULT_FIELD = Field.CLAY;
    private static final Field UPDATED_FIELD = Field.GRASS;

    private static final Light DEFAULT_LIGHT = Light.NO_LIGHT;
    private static final Light UPDATED_LIGHT = Light.LAMP;

    private static final Chair DEFAULT_CHAIR = Chair.WOODEN;
    private static final Chair UPDATED_CHAIR = Chair.CONCRETE;

    private static final Assistant DEFAULT_ASSISTANT = Assistant.NO_ASSISTANT;
    private static final Assistant UPDATED_ASSISTANT = Assistant.TRAINEE;

    private static final BodyBuilding DEFAULT_BODY_BUILDING = BodyBuilding.NO_BODYBUILDING;
    private static final BodyBuilding UPDATED_BODY_BUILDING = BodyBuilding.PARK;

    private static final Doctor DEFAULT_DOCTOR = Doctor.NO_DOCTOR;
    private static final Doctor UPDATED_DOCTOR = Doctor.PUBLIC_CLINIC;

    private static final String ENTITY_API_URL = "/api/stadiums";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private StadiumRepository stadiumRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStadiumMockMvc;

    private Stadium stadium;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stadium createEntity(EntityManager em) {
        Stadium stadium = new Stadium()
            .name(DEFAULT_NAME)
            .capacity(DEFAULT_CAPACITY)
            .ticket(DEFAULT_TICKET)
            .leader(DEFAULT_LEADER)
            .vehicle(DEFAULT_VEHICLE)
            .field(DEFAULT_FIELD)
            .light(DEFAULT_LIGHT)
            .chair(DEFAULT_CHAIR)
            .assistant(DEFAULT_ASSISTANT)
            .bodyBuilding(DEFAULT_BODY_BUILDING)
            .doctor(DEFAULT_DOCTOR);
        return stadium;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stadium createUpdatedEntity(EntityManager em) {
        Stadium stadium = new Stadium()
            .name(UPDATED_NAME)
            .capacity(UPDATED_CAPACITY)
            .ticket(UPDATED_TICKET)
            .leader(UPDATED_LEADER)
            .vehicle(UPDATED_VEHICLE)
            .field(UPDATED_FIELD)
            .light(UPDATED_LIGHT)
            .chair(UPDATED_CHAIR)
            .assistant(UPDATED_ASSISTANT)
            .bodyBuilding(UPDATED_BODY_BUILDING)
            .doctor(UPDATED_DOCTOR);
        return stadium;
    }

    @BeforeEach
    public void initTest() {
        stadium = createEntity(em);
    }

    @Test
    @Transactional
    void createStadium() throws Exception {
        int databaseSizeBeforeCreate = stadiumRepository.findAll().size();
        // Create the Stadium
        restStadiumMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stadium)))
            .andExpect(status().isCreated());

        // Validate the Stadium in the database
        List<Stadium> stadiumList = stadiumRepository.findAll();
        assertThat(stadiumList).hasSize(databaseSizeBeforeCreate + 1);
        Stadium testStadium = stadiumList.get(stadiumList.size() - 1);
        assertThat(testStadium.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStadium.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
        assertThat(testStadium.getTicket()).isEqualTo(DEFAULT_TICKET);
        assertThat(testStadium.getLeader()).isEqualTo(DEFAULT_LEADER);
        assertThat(testStadium.getVehicle()).isEqualTo(DEFAULT_VEHICLE);
        assertThat(testStadium.getField()).isEqualTo(DEFAULT_FIELD);
        assertThat(testStadium.getLight()).isEqualTo(DEFAULT_LIGHT);
        assertThat(testStadium.getChair()).isEqualTo(DEFAULT_CHAIR);
        assertThat(testStadium.getAssistant()).isEqualTo(DEFAULT_ASSISTANT);
        assertThat(testStadium.getBodyBuilding()).isEqualTo(DEFAULT_BODY_BUILDING);
        assertThat(testStadium.getDoctor()).isEqualTo(DEFAULT_DOCTOR);
    }

    @Test
    @Transactional
    void createStadiumWithExistingId() throws Exception {
        // Create the Stadium with an existing ID
        stadiumRepository.saveAndFlush(stadium);

        int databaseSizeBeforeCreate = stadiumRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStadiumMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stadium)))
            .andExpect(status().isBadRequest());

        // Validate the Stadium in the database
        List<Stadium> stadiumList = stadiumRepository.findAll();
        assertThat(stadiumList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stadiumRepository.findAll().size();
        // set the field null
        stadium.setName(null);

        // Create the Stadium, which fails.

        restStadiumMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stadium)))
            .andExpect(status().isBadRequest());

        List<Stadium> stadiumList = stadiumRepository.findAll();
        assertThat(stadiumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCapacityIsRequired() throws Exception {
        int databaseSizeBeforeTest = stadiumRepository.findAll().size();
        // set the field null
        stadium.setCapacity(null);

        // Create the Stadium, which fails.

        restStadiumMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stadium)))
            .andExpect(status().isBadRequest());

        List<Stadium> stadiumList = stadiumRepository.findAll();
        assertThat(stadiumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTicketIsRequired() throws Exception {
        int databaseSizeBeforeTest = stadiumRepository.findAll().size();
        // set the field null
        stadium.setTicket(null);

        // Create the Stadium, which fails.

        restStadiumMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stadium)))
            .andExpect(status().isBadRequest());

        List<Stadium> stadiumList = stadiumRepository.findAll();
        assertThat(stadiumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStadiums() throws Exception {
        // Initialize the database
        stadiumRepository.saveAndFlush(stadium);

        // Get all the stadiumList
        restStadiumMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stadium.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY)))
            .andExpect(jsonPath("$.[*].ticket").value(hasItem(DEFAULT_TICKET)))
            .andExpect(jsonPath("$.[*].leader").value(hasItem(DEFAULT_LEADER.toString())))
            .andExpect(jsonPath("$.[*].vehicle").value(hasItem(DEFAULT_VEHICLE.toString())))
            .andExpect(jsonPath("$.[*].field").value(hasItem(DEFAULT_FIELD.toString())))
            .andExpect(jsonPath("$.[*].light").value(hasItem(DEFAULT_LIGHT.toString())))
            .andExpect(jsonPath("$.[*].chair").value(hasItem(DEFAULT_CHAIR.toString())))
            .andExpect(jsonPath("$.[*].assistant").value(hasItem(DEFAULT_ASSISTANT.toString())))
            .andExpect(jsonPath("$.[*].bodyBuilding").value(hasItem(DEFAULT_BODY_BUILDING.toString())))
            .andExpect(jsonPath("$.[*].doctor").value(hasItem(DEFAULT_DOCTOR.toString())));
    }

    @Test
    @Transactional
    void getStadium() throws Exception {
        // Initialize the database
        stadiumRepository.saveAndFlush(stadium);

        // Get the stadium
        restStadiumMockMvc
            .perform(get(ENTITY_API_URL_ID, stadium.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stadium.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY))
            .andExpect(jsonPath("$.ticket").value(DEFAULT_TICKET))
            .andExpect(jsonPath("$.leader").value(DEFAULT_LEADER.toString()))
            .andExpect(jsonPath("$.vehicle").value(DEFAULT_VEHICLE.toString()))
            .andExpect(jsonPath("$.field").value(DEFAULT_FIELD.toString()))
            .andExpect(jsonPath("$.light").value(DEFAULT_LIGHT.toString()))
            .andExpect(jsonPath("$.chair").value(DEFAULT_CHAIR.toString()))
            .andExpect(jsonPath("$.assistant").value(DEFAULT_ASSISTANT.toString()))
            .andExpect(jsonPath("$.bodyBuilding").value(DEFAULT_BODY_BUILDING.toString()))
            .andExpect(jsonPath("$.doctor").value(DEFAULT_DOCTOR.toString()));
    }

    @Test
    @Transactional
    void getNonExistingStadium() throws Exception {
        // Get the stadium
        restStadiumMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStadium() throws Exception {
        // Initialize the database
        stadiumRepository.saveAndFlush(stadium);

        int databaseSizeBeforeUpdate = stadiumRepository.findAll().size();

        // Update the stadium
        Stadium updatedStadium = stadiumRepository.findById(stadium.getId()).get();
        // Disconnect from session so that the updates on updatedStadium are not directly saved in db
        em.detach(updatedStadium);
        updatedStadium
            .name(UPDATED_NAME)
            .capacity(UPDATED_CAPACITY)
            .ticket(UPDATED_TICKET)
            .leader(UPDATED_LEADER)
            .vehicle(UPDATED_VEHICLE)
            .field(UPDATED_FIELD)
            .light(UPDATED_LIGHT)
            .chair(UPDATED_CHAIR)
            .assistant(UPDATED_ASSISTANT)
            .bodyBuilding(UPDATED_BODY_BUILDING)
            .doctor(UPDATED_DOCTOR);

        restStadiumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStadium.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStadium))
            )
            .andExpect(status().isOk());

        // Validate the Stadium in the database
        List<Stadium> stadiumList = stadiumRepository.findAll();
        assertThat(stadiumList).hasSize(databaseSizeBeforeUpdate);
        Stadium testStadium = stadiumList.get(stadiumList.size() - 1);
        assertThat(testStadium.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStadium.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testStadium.getTicket()).isEqualTo(UPDATED_TICKET);
        assertThat(testStadium.getLeader()).isEqualTo(UPDATED_LEADER);
        assertThat(testStadium.getVehicle()).isEqualTo(UPDATED_VEHICLE);
        assertThat(testStadium.getField()).isEqualTo(UPDATED_FIELD);
        assertThat(testStadium.getLight()).isEqualTo(UPDATED_LIGHT);
        assertThat(testStadium.getChair()).isEqualTo(UPDATED_CHAIR);
        assertThat(testStadium.getAssistant()).isEqualTo(UPDATED_ASSISTANT);
        assertThat(testStadium.getBodyBuilding()).isEqualTo(UPDATED_BODY_BUILDING);
        assertThat(testStadium.getDoctor()).isEqualTo(UPDATED_DOCTOR);
    }

    @Test
    @Transactional
    void putNonExistingStadium() throws Exception {
        int databaseSizeBeforeUpdate = stadiumRepository.findAll().size();
        stadium.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStadiumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stadium.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stadium))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stadium in the database
        List<Stadium> stadiumList = stadiumRepository.findAll();
        assertThat(stadiumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStadium() throws Exception {
        int databaseSizeBeforeUpdate = stadiumRepository.findAll().size();
        stadium.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStadiumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stadium))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stadium in the database
        List<Stadium> stadiumList = stadiumRepository.findAll();
        assertThat(stadiumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStadium() throws Exception {
        int databaseSizeBeforeUpdate = stadiumRepository.findAll().size();
        stadium.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStadiumMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stadium)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stadium in the database
        List<Stadium> stadiumList = stadiumRepository.findAll();
        assertThat(stadiumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStadiumWithPatch() throws Exception {
        // Initialize the database
        stadiumRepository.saveAndFlush(stadium);

        int databaseSizeBeforeUpdate = stadiumRepository.findAll().size();

        // Update the stadium using partial update
        Stadium partialUpdatedStadium = new Stadium();
        partialUpdatedStadium.setId(stadium.getId());

        partialUpdatedStadium
            .name(UPDATED_NAME)
            .ticket(UPDATED_TICKET)
            .leader(UPDATED_LEADER)
            .vehicle(UPDATED_VEHICLE)
            .light(UPDATED_LIGHT)
            .chair(UPDATED_CHAIR)
            .assistant(UPDATED_ASSISTANT)
            .doctor(UPDATED_DOCTOR);

        restStadiumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStadium.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStadium))
            )
            .andExpect(status().isOk());

        // Validate the Stadium in the database
        List<Stadium> stadiumList = stadiumRepository.findAll();
        assertThat(stadiumList).hasSize(databaseSizeBeforeUpdate);
        Stadium testStadium = stadiumList.get(stadiumList.size() - 1);
        assertThat(testStadium.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStadium.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
        assertThat(testStadium.getTicket()).isEqualTo(UPDATED_TICKET);
        assertThat(testStadium.getLeader()).isEqualTo(UPDATED_LEADER);
        assertThat(testStadium.getVehicle()).isEqualTo(UPDATED_VEHICLE);
        assertThat(testStadium.getField()).isEqualTo(DEFAULT_FIELD);
        assertThat(testStadium.getLight()).isEqualTo(UPDATED_LIGHT);
        assertThat(testStadium.getChair()).isEqualTo(UPDATED_CHAIR);
        assertThat(testStadium.getAssistant()).isEqualTo(UPDATED_ASSISTANT);
        assertThat(testStadium.getBodyBuilding()).isEqualTo(DEFAULT_BODY_BUILDING);
        assertThat(testStadium.getDoctor()).isEqualTo(UPDATED_DOCTOR);
    }

    @Test
    @Transactional
    void fullUpdateStadiumWithPatch() throws Exception {
        // Initialize the database
        stadiumRepository.saveAndFlush(stadium);

        int databaseSizeBeforeUpdate = stadiumRepository.findAll().size();

        // Update the stadium using partial update
        Stadium partialUpdatedStadium = new Stadium();
        partialUpdatedStadium.setId(stadium.getId());

        partialUpdatedStadium
            .name(UPDATED_NAME)
            .capacity(UPDATED_CAPACITY)
            .ticket(UPDATED_TICKET)
            .leader(UPDATED_LEADER)
            .vehicle(UPDATED_VEHICLE)
            .field(UPDATED_FIELD)
            .light(UPDATED_LIGHT)
            .chair(UPDATED_CHAIR)
            .assistant(UPDATED_ASSISTANT)
            .bodyBuilding(UPDATED_BODY_BUILDING)
            .doctor(UPDATED_DOCTOR);

        restStadiumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStadium.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStadium))
            )
            .andExpect(status().isOk());

        // Validate the Stadium in the database
        List<Stadium> stadiumList = stadiumRepository.findAll();
        assertThat(stadiumList).hasSize(databaseSizeBeforeUpdate);
        Stadium testStadium = stadiumList.get(stadiumList.size() - 1);
        assertThat(testStadium.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStadium.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testStadium.getTicket()).isEqualTo(UPDATED_TICKET);
        assertThat(testStadium.getLeader()).isEqualTo(UPDATED_LEADER);
        assertThat(testStadium.getVehicle()).isEqualTo(UPDATED_VEHICLE);
        assertThat(testStadium.getField()).isEqualTo(UPDATED_FIELD);
        assertThat(testStadium.getLight()).isEqualTo(UPDATED_LIGHT);
        assertThat(testStadium.getChair()).isEqualTo(UPDATED_CHAIR);
        assertThat(testStadium.getAssistant()).isEqualTo(UPDATED_ASSISTANT);
        assertThat(testStadium.getBodyBuilding()).isEqualTo(UPDATED_BODY_BUILDING);
        assertThat(testStadium.getDoctor()).isEqualTo(UPDATED_DOCTOR);
    }

    @Test
    @Transactional
    void patchNonExistingStadium() throws Exception {
        int databaseSizeBeforeUpdate = stadiumRepository.findAll().size();
        stadium.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStadiumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stadium.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stadium))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stadium in the database
        List<Stadium> stadiumList = stadiumRepository.findAll();
        assertThat(stadiumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStadium() throws Exception {
        int databaseSizeBeforeUpdate = stadiumRepository.findAll().size();
        stadium.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStadiumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stadium))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stadium in the database
        List<Stadium> stadiumList = stadiumRepository.findAll();
        assertThat(stadiumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStadium() throws Exception {
        int databaseSizeBeforeUpdate = stadiumRepository.findAll().size();
        stadium.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStadiumMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(stadium)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stadium in the database
        List<Stadium> stadiumList = stadiumRepository.findAll();
        assertThat(stadiumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStadium() throws Exception {
        // Initialize the database
        stadiumRepository.saveAndFlush(stadium);

        int databaseSizeBeforeDelete = stadiumRepository.findAll().size();

        // Delete the stadium
        restStadiumMockMvc
            .perform(delete(ENTITY_API_URL_ID, stadium.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Stadium> stadiumList = stadiumRepository.findAll();
        assertThat(stadiumList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
