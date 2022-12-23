package club.profb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import club.profb.IntegrationTest;
import club.profb.domain.Composition;
import club.profb.domain.enumeration.Arrange;
import club.profb.domain.enumeration.Strategy;
import club.profb.repository.CompositionRepository;
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
 * Integration tests for the {@link CompositionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompositionResourceIT {

    private static final Boolean DEFAULT_D_3_FAULT = false;
    private static final Boolean UPDATED_D_3_FAULT = true;

    private static final Arrange DEFAULT_ARRANGE = Arrange.A235;
    private static final Arrange UPDATED_ARRANGE = Arrange.A442;

    private static final Strategy DEFAULT_STRATEGY = Strategy.GET_SCORE;
    private static final Strategy UPDATED_STRATEGY = Strategy.NOT_GIVE_SCORE;

    private static final Integer DEFAULT_DEFENCE = 0;
    private static final Integer UPDATED_DEFENCE = 1;

    private static final Integer DEFAULT_SHORT_PASS = 0;
    private static final Integer UPDATED_SHORT_PASS = 1;

    private static final Integer DEFAULT_VIOLENCE = 0;
    private static final Integer UPDATED_VIOLENCE = 1;

    private static final String ENTITY_API_URL = "/api/compositions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CompositionRepository compositionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompositionMockMvc;

    private Composition composition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Composition createEntity(EntityManager em) {
        Composition composition = new Composition()
            .d3fault(DEFAULT_D_3_FAULT)
            .arrange(DEFAULT_ARRANGE)
            .strategy(DEFAULT_STRATEGY)
            .defence(DEFAULT_DEFENCE)
            .shortPass(DEFAULT_SHORT_PASS)
            .violence(DEFAULT_VIOLENCE);
        return composition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Composition createUpdatedEntity(EntityManager em) {
        Composition composition = new Composition()
            .d3fault(UPDATED_D_3_FAULT)
            .arrange(UPDATED_ARRANGE)
            .strategy(UPDATED_STRATEGY)
            .defence(UPDATED_DEFENCE)
            .shortPass(UPDATED_SHORT_PASS)
            .violence(UPDATED_VIOLENCE);
        return composition;
    }

    @BeforeEach
    public void initTest() {
        composition = createEntity(em);
    }

    @Test
    @Transactional
    void createComposition() throws Exception {
        int databaseSizeBeforeCreate = compositionRepository.findAll().size();
        // Create the Composition
        restCompositionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(composition)))
            .andExpect(status().isCreated());

        // Validate the Composition in the database
        List<Composition> compositionList = compositionRepository.findAll();
        assertThat(compositionList).hasSize(databaseSizeBeforeCreate + 1);
        Composition testComposition = compositionList.get(compositionList.size() - 1);
        assertThat(testComposition.getd3fault()).isEqualTo(DEFAULT_D_3_FAULT);
        assertThat(testComposition.getArrange()).isEqualTo(DEFAULT_ARRANGE);
        assertThat(testComposition.getStrategy()).isEqualTo(DEFAULT_STRATEGY);
        assertThat(testComposition.getDefence()).isEqualTo(DEFAULT_DEFENCE);
        assertThat(testComposition.getShortPass()).isEqualTo(DEFAULT_SHORT_PASS);
        assertThat(testComposition.getViolence()).isEqualTo(DEFAULT_VIOLENCE);
    }

    @Test
    @Transactional
    void createCompositionWithExistingId() throws Exception {
        // Create the Composition with an existing ID
        compositionRepository.saveAndFlush(composition);

        int databaseSizeBeforeCreate = compositionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompositionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(composition)))
            .andExpect(status().isBadRequest());

        // Validate the Composition in the database
        List<Composition> compositionList = compositionRepository.findAll();
        assertThat(compositionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkd3faultIsRequired() throws Exception {
        int databaseSizeBeforeTest = compositionRepository.findAll().size();
        // set the field null
        composition.setd3fault(null);

        // Create the Composition, which fails.

        restCompositionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(composition)))
            .andExpect(status().isBadRequest());

        List<Composition> compositionList = compositionRepository.findAll();
        assertThat(compositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkArrangeIsRequired() throws Exception {
        int databaseSizeBeforeTest = compositionRepository.findAll().size();
        // set the field null
        composition.setArrange(null);

        // Create the Composition, which fails.

        restCompositionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(composition)))
            .andExpect(status().isBadRequest());

        List<Composition> compositionList = compositionRepository.findAll();
        assertThat(compositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStrategyIsRequired() throws Exception {
        int databaseSizeBeforeTest = compositionRepository.findAll().size();
        // set the field null
        composition.setStrategy(null);

        // Create the Composition, which fails.

        restCompositionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(composition)))
            .andExpect(status().isBadRequest());

        List<Composition> compositionList = compositionRepository.findAll();
        assertThat(compositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDefenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = compositionRepository.findAll().size();
        // set the field null
        composition.setDefence(null);

        // Create the Composition, which fails.

        restCompositionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(composition)))
            .andExpect(status().isBadRequest());

        List<Composition> compositionList = compositionRepository.findAll();
        assertThat(compositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkShortPassIsRequired() throws Exception {
        int databaseSizeBeforeTest = compositionRepository.findAll().size();
        // set the field null
        composition.setShortPass(null);

        // Create the Composition, which fails.

        restCompositionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(composition)))
            .andExpect(status().isBadRequest());

        List<Composition> compositionList = compositionRepository.findAll();
        assertThat(compositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkViolenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = compositionRepository.findAll().size();
        // set the field null
        composition.setViolence(null);

        // Create the Composition, which fails.

        restCompositionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(composition)))
            .andExpect(status().isBadRequest());

        List<Composition> compositionList = compositionRepository.findAll();
        assertThat(compositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCompositions() throws Exception {
        // Initialize the database
        compositionRepository.saveAndFlush(composition);

        // Get all the compositionList
        restCompositionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(composition.getId().toString())))
            .andExpect(jsonPath("$.[*].d3fault").value(hasItem(DEFAULT_D_3_FAULT.booleanValue())))
            .andExpect(jsonPath("$.[*].arrange").value(hasItem(DEFAULT_ARRANGE.toString())))
            .andExpect(jsonPath("$.[*].strategy").value(hasItem(DEFAULT_STRATEGY.toString())))
            .andExpect(jsonPath("$.[*].defence").value(hasItem(DEFAULT_DEFENCE)))
            .andExpect(jsonPath("$.[*].shortPass").value(hasItem(DEFAULT_SHORT_PASS)))
            .andExpect(jsonPath("$.[*].violence").value(hasItem(DEFAULT_VIOLENCE)));
    }

    @Test
    @Transactional
    void getComposition() throws Exception {
        // Initialize the database
        compositionRepository.saveAndFlush(composition);

        // Get the composition
        restCompositionMockMvc
            .perform(get(ENTITY_API_URL_ID, composition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(composition.getId().toString()))
            .andExpect(jsonPath("$.d3fault").value(DEFAULT_D_3_FAULT.booleanValue()))
            .andExpect(jsonPath("$.arrange").value(DEFAULT_ARRANGE.toString()))
            .andExpect(jsonPath("$.strategy").value(DEFAULT_STRATEGY.toString()))
            .andExpect(jsonPath("$.defence").value(DEFAULT_DEFENCE))
            .andExpect(jsonPath("$.shortPass").value(DEFAULT_SHORT_PASS))
            .andExpect(jsonPath("$.violence").value(DEFAULT_VIOLENCE));
    }

    @Test
    @Transactional
    void getNonExistingComposition() throws Exception {
        // Get the composition
        restCompositionMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewComposition() throws Exception {
        // Initialize the database
        compositionRepository.saveAndFlush(composition);

        int databaseSizeBeforeUpdate = compositionRepository.findAll().size();

        // Update the composition
        Composition updatedComposition = compositionRepository.findById(composition.getId()).get();
        // Disconnect from session so that the updates on updatedComposition are not directly saved in db
        em.detach(updatedComposition);
        updatedComposition
            .d3fault(UPDATED_D_3_FAULT)
            .arrange(UPDATED_ARRANGE)
            .strategy(UPDATED_STRATEGY)
            .defence(UPDATED_DEFENCE)
            .shortPass(UPDATED_SHORT_PASS)
            .violence(UPDATED_VIOLENCE);

        restCompositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedComposition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedComposition))
            )
            .andExpect(status().isOk());

        // Validate the Composition in the database
        List<Composition> compositionList = compositionRepository.findAll();
        assertThat(compositionList).hasSize(databaseSizeBeforeUpdate);
        Composition testComposition = compositionList.get(compositionList.size() - 1);
        assertThat(testComposition.getd3fault()).isEqualTo(UPDATED_D_3_FAULT);
        assertThat(testComposition.getArrange()).isEqualTo(UPDATED_ARRANGE);
        assertThat(testComposition.getStrategy()).isEqualTo(UPDATED_STRATEGY);
        assertThat(testComposition.getDefence()).isEqualTo(UPDATED_DEFENCE);
        assertThat(testComposition.getShortPass()).isEqualTo(UPDATED_SHORT_PASS);
        assertThat(testComposition.getViolence()).isEqualTo(UPDATED_VIOLENCE);
    }

    @Test
    @Transactional
    void putNonExistingComposition() throws Exception {
        int databaseSizeBeforeUpdate = compositionRepository.findAll().size();
        composition.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, composition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(composition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Composition in the database
        List<Composition> compositionList = compositionRepository.findAll();
        assertThat(compositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchComposition() throws Exception {
        int databaseSizeBeforeUpdate = compositionRepository.findAll().size();
        composition.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(composition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Composition in the database
        List<Composition> compositionList = compositionRepository.findAll();
        assertThat(compositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamComposition() throws Exception {
        int databaseSizeBeforeUpdate = compositionRepository.findAll().size();
        composition.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompositionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(composition)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Composition in the database
        List<Composition> compositionList = compositionRepository.findAll();
        assertThat(compositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompositionWithPatch() throws Exception {
        // Initialize the database
        compositionRepository.saveAndFlush(composition);

        int databaseSizeBeforeUpdate = compositionRepository.findAll().size();

        // Update the composition using partial update
        Composition partialUpdatedComposition = new Composition();
        partialUpdatedComposition.setId(composition.getId());

        partialUpdatedComposition.d3fault(UPDATED_D_3_FAULT).arrange(UPDATED_ARRANGE);

        restCompositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComposition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComposition))
            )
            .andExpect(status().isOk());

        // Validate the Composition in the database
        List<Composition> compositionList = compositionRepository.findAll();
        assertThat(compositionList).hasSize(databaseSizeBeforeUpdate);
        Composition testComposition = compositionList.get(compositionList.size() - 1);
        assertThat(testComposition.getd3fault()).isEqualTo(UPDATED_D_3_FAULT);
        assertThat(testComposition.getArrange()).isEqualTo(UPDATED_ARRANGE);
        assertThat(testComposition.getStrategy()).isEqualTo(DEFAULT_STRATEGY);
        assertThat(testComposition.getDefence()).isEqualTo(DEFAULT_DEFENCE);
        assertThat(testComposition.getShortPass()).isEqualTo(DEFAULT_SHORT_PASS);
        assertThat(testComposition.getViolence()).isEqualTo(DEFAULT_VIOLENCE);
    }

    @Test
    @Transactional
    void fullUpdateCompositionWithPatch() throws Exception {
        // Initialize the database
        compositionRepository.saveAndFlush(composition);

        int databaseSizeBeforeUpdate = compositionRepository.findAll().size();

        // Update the composition using partial update
        Composition partialUpdatedComposition = new Composition();
        partialUpdatedComposition.setId(composition.getId());

        partialUpdatedComposition
            .d3fault(UPDATED_D_3_FAULT)
            .arrange(UPDATED_ARRANGE)
            .strategy(UPDATED_STRATEGY)
            .defence(UPDATED_DEFENCE)
            .shortPass(UPDATED_SHORT_PASS)
            .violence(UPDATED_VIOLENCE);

        restCompositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComposition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComposition))
            )
            .andExpect(status().isOk());

        // Validate the Composition in the database
        List<Composition> compositionList = compositionRepository.findAll();
        assertThat(compositionList).hasSize(databaseSizeBeforeUpdate);
        Composition testComposition = compositionList.get(compositionList.size() - 1);
        assertThat(testComposition.getd3fault()).isEqualTo(UPDATED_D_3_FAULT);
        assertThat(testComposition.getArrange()).isEqualTo(UPDATED_ARRANGE);
        assertThat(testComposition.getStrategy()).isEqualTo(UPDATED_STRATEGY);
        assertThat(testComposition.getDefence()).isEqualTo(UPDATED_DEFENCE);
        assertThat(testComposition.getShortPass()).isEqualTo(UPDATED_SHORT_PASS);
        assertThat(testComposition.getViolence()).isEqualTo(UPDATED_VIOLENCE);
    }

    @Test
    @Transactional
    void patchNonExistingComposition() throws Exception {
        int databaseSizeBeforeUpdate = compositionRepository.findAll().size();
        composition.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, composition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(composition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Composition in the database
        List<Composition> compositionList = compositionRepository.findAll();
        assertThat(compositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchComposition() throws Exception {
        int databaseSizeBeforeUpdate = compositionRepository.findAll().size();
        composition.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(composition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Composition in the database
        List<Composition> compositionList = compositionRepository.findAll();
        assertThat(compositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamComposition() throws Exception {
        int databaseSizeBeforeUpdate = compositionRepository.findAll().size();
        composition.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompositionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(composition))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Composition in the database
        List<Composition> compositionList = compositionRepository.findAll();
        assertThat(compositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteComposition() throws Exception {
        // Initialize the database
        compositionRepository.saveAndFlush(composition);

        int databaseSizeBeforeDelete = compositionRepository.findAll().size();

        // Delete the composition
        restCompositionMockMvc
            .perform(delete(ENTITY_API_URL_ID, composition.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Composition> compositionList = compositionRepository.findAll();
        assertThat(compositionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
