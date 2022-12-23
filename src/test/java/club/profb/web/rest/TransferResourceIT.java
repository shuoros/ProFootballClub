package club.profb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import club.profb.IntegrationTest;
import club.profb.domain.Transfer;
import club.profb.repository.TransferRepository;
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
 * Integration tests for the {@link TransferResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TransferResourceIT {

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BOUGHT = false;
    private static final Boolean UPDATED_BOUGHT = true;

    private static final String ENTITY_API_URL = "/api/transfers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransferMockMvc;

    private Transfer transfer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transfer createEntity(EntityManager em) {
        Transfer transfer = new Transfer().price(DEFAULT_PRICE).password(DEFAULT_PASSWORD).bought(DEFAULT_BOUGHT);
        return transfer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transfer createUpdatedEntity(EntityManager em) {
        Transfer transfer = new Transfer().price(UPDATED_PRICE).password(UPDATED_PASSWORD).bought(UPDATED_BOUGHT);
        return transfer;
    }

    @BeforeEach
    public void initTest() {
        transfer = createEntity(em);
    }

    @Test
    @Transactional
    void createTransfer() throws Exception {
        int databaseSizeBeforeCreate = transferRepository.findAll().size();
        // Create the Transfer
        restTransferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transfer)))
            .andExpect(status().isCreated());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeCreate + 1);
        Transfer testTransfer = transferList.get(transferList.size() - 1);
        assertThat(testTransfer.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTransfer.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testTransfer.getBought()).isEqualTo(DEFAULT_BOUGHT);
    }

    @Test
    @Transactional
    void createTransferWithExistingId() throws Exception {
        // Create the Transfer with an existing ID
        transferRepository.saveAndFlush(transfer);

        int databaseSizeBeforeCreate = transferRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transfer)))
            .andExpect(status().isBadRequest());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = transferRepository.findAll().size();
        // set the field null
        transfer.setPrice(null);

        // Create the Transfer, which fails.

        restTransferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transfer)))
            .andExpect(status().isBadRequest());

        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTransfers() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList
        restTransferMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transfer.getId().toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].bought").value(hasItem(DEFAULT_BOUGHT.booleanValue())));
    }

    @Test
    @Transactional
    void getTransfer() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get the transfer
        restTransferMockMvc
            .perform(get(ENTITY_API_URL_ID, transfer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transfer.getId().toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.bought").value(DEFAULT_BOUGHT.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingTransfer() throws Exception {
        // Get the transfer
        restTransferMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTransfer() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        int databaseSizeBeforeUpdate = transferRepository.findAll().size();

        // Update the transfer
        Transfer updatedTransfer = transferRepository.findById(transfer.getId()).get();
        // Disconnect from session so that the updates on updatedTransfer are not directly saved in db
        em.detach(updatedTransfer);
        updatedTransfer.price(UPDATED_PRICE).password(UPDATED_PASSWORD).bought(UPDATED_BOUGHT);

        restTransferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTransfer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTransfer))
            )
            .andExpect(status().isOk());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
        Transfer testTransfer = transferList.get(transferList.size() - 1);
        assertThat(testTransfer.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTransfer.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testTransfer.getBought()).isEqualTo(UPDATED_BOUGHT);
    }

    @Test
    @Transactional
    void putNonExistingTransfer() throws Exception {
        int databaseSizeBeforeUpdate = transferRepository.findAll().size();
        transfer.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transfer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transfer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransfer() throws Exception {
        int databaseSizeBeforeUpdate = transferRepository.findAll().size();
        transfer.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transfer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransfer() throws Exception {
        int databaseSizeBeforeUpdate = transferRepository.findAll().size();
        transfer.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transfer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransferWithPatch() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        int databaseSizeBeforeUpdate = transferRepository.findAll().size();

        // Update the transfer using partial update
        Transfer partialUpdatedTransfer = new Transfer();
        partialUpdatedTransfer.setId(transfer.getId());

        partialUpdatedTransfer.price(UPDATED_PRICE).bought(UPDATED_BOUGHT);

        restTransferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransfer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransfer))
            )
            .andExpect(status().isOk());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
        Transfer testTransfer = transferList.get(transferList.size() - 1);
        assertThat(testTransfer.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTransfer.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testTransfer.getBought()).isEqualTo(UPDATED_BOUGHT);
    }

    @Test
    @Transactional
    void fullUpdateTransferWithPatch() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        int databaseSizeBeforeUpdate = transferRepository.findAll().size();

        // Update the transfer using partial update
        Transfer partialUpdatedTransfer = new Transfer();
        partialUpdatedTransfer.setId(transfer.getId());

        partialUpdatedTransfer.price(UPDATED_PRICE).password(UPDATED_PASSWORD).bought(UPDATED_BOUGHT);

        restTransferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransfer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransfer))
            )
            .andExpect(status().isOk());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
        Transfer testTransfer = transferList.get(transferList.size() - 1);
        assertThat(testTransfer.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTransfer.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testTransfer.getBought()).isEqualTo(UPDATED_BOUGHT);
    }

    @Test
    @Transactional
    void patchNonExistingTransfer() throws Exception {
        int databaseSizeBeforeUpdate = transferRepository.findAll().size();
        transfer.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transfer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transfer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransfer() throws Exception {
        int databaseSizeBeforeUpdate = transferRepository.findAll().size();
        transfer.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transfer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransfer() throws Exception {
        int databaseSizeBeforeUpdate = transferRepository.findAll().size();
        transfer.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(transfer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransfer() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        int databaseSizeBeforeDelete = transferRepository.findAll().size();

        // Delete the transfer
        restTransferMockMvc
            .perform(delete(ENTITY_API_URL_ID, transfer.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
