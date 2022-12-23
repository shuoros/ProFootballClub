package club.profb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import club.profb.IntegrationTest;
import club.profb.domain.Player;
import club.profb.domain.enumeration.Country;
import club.profb.domain.enumeration.Gender;
import club.profb.domain.enumeration.PlayerStatus;
import club.profb.domain.enumeration.Post;
import club.profb.repository.PlayerRepository;
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
 * Integration tests for the {@link PlayerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlayerResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.DIVERSE;
    private static final Gender UPDATED_GENDER = Gender.MALE;

    private static final Country DEFAULT_COUNTRY = Country.MULTY_CULTI;
    private static final Country UPDATED_COUNTRY = Country.UK;

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final PlayerStatus DEFAULT_STATUS = PlayerStatus.NOT_READY;
    private static final PlayerStatus UPDATED_STATUS = PlayerStatus.READY;

    private static final Post DEFAULT_POST = Post.GK;
    private static final Post UPDATED_POST = Post.SW;

    private static final Integer DEFAULT_TOTAL_POWER = 0;
    private static final Integer UPDATED_TOTAL_POWER = 1;

    private static final Integer DEFAULT_GOALKEEPING = 0;
    private static final Integer UPDATED_GOALKEEPING = 1;

    private static final Integer DEFAULT_DEFENCE = 0;
    private static final Integer UPDATED_DEFENCE = 1;

    private static final Integer DEFAULT_TACKLING = 0;
    private static final Integer UPDATED_TACKLING = 1;

    private static final Integer DEFAULT_PASSING = 0;
    private static final Integer UPDATED_PASSING = 1;

    private static final Integer DEFAULT_TEAM_SKILL = 0;
    private static final Integer UPDATED_TEAM_SKILL = 1;

    private static final Integer DEFAULT_BALL_SKILL = 0;
    private static final Integer UPDATED_BALL_SKILL = 1;

    private static final Integer DEFAULT_SHOOTING = 0;
    private static final Integer UPDATED_SHOOTING = 1;

    private static final Integer DEFAULT_LONG_SHOOTING = 0;
    private static final Integer UPDATED_LONG_SHOOTING = 1;

    private static final Integer DEFAULT_DRIBBLING = 0;
    private static final Integer UPDATED_DRIBBLING = 1;

    private static final Integer DEFAULT_TECHNIQUE = 0;
    private static final Integer UPDATED_TECHNIQUE = 1;

    private static final Integer DEFAULT_CONFIDENCE = 0;
    private static final Integer UPDATED_CONFIDENCE = 1;

    private static final String ENTITY_API_URL = "/api/players";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlayerMockMvc;

    private Player player;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Player createEntity(EntityManager em) {
        Player player = new Player()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .gender(DEFAULT_GENDER)
            .country(DEFAULT_COUNTRY)
            .age(DEFAULT_AGE)
            .status(DEFAULT_STATUS)
            .post(DEFAULT_POST)
            .totalPower(DEFAULT_TOTAL_POWER)
            .goalkeeping(DEFAULT_GOALKEEPING)
            .defence(DEFAULT_DEFENCE)
            .tackling(DEFAULT_TACKLING)
            .passing(DEFAULT_PASSING)
            .teamSkill(DEFAULT_TEAM_SKILL)
            .ballSkill(DEFAULT_BALL_SKILL)
            .shooting(DEFAULT_SHOOTING)
            .longShooting(DEFAULT_LONG_SHOOTING)
            .dribbling(DEFAULT_DRIBBLING)
            .technique(DEFAULT_TECHNIQUE)
            .confidence(DEFAULT_CONFIDENCE);
        return player;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Player createUpdatedEntity(EntityManager em) {
        Player player = new Player()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .gender(UPDATED_GENDER)
            .country(UPDATED_COUNTRY)
            .age(UPDATED_AGE)
            .status(UPDATED_STATUS)
            .post(UPDATED_POST)
            .totalPower(UPDATED_TOTAL_POWER)
            .goalkeeping(UPDATED_GOALKEEPING)
            .defence(UPDATED_DEFENCE)
            .tackling(UPDATED_TACKLING)
            .passing(UPDATED_PASSING)
            .teamSkill(UPDATED_TEAM_SKILL)
            .ballSkill(UPDATED_BALL_SKILL)
            .shooting(UPDATED_SHOOTING)
            .longShooting(UPDATED_LONG_SHOOTING)
            .dribbling(UPDATED_DRIBBLING)
            .technique(UPDATED_TECHNIQUE)
            .confidence(UPDATED_CONFIDENCE);
        return player;
    }

    @BeforeEach
    public void initTest() {
        player = createEntity(em);
    }

    @Test
    @Transactional
    void createPlayer() throws Exception {
        int databaseSizeBeforeCreate = playerRepository.findAll().size();
        // Create the Player
        restPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isCreated());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeCreate + 1);
        Player testPlayer = playerList.get(playerList.size() - 1);
        assertThat(testPlayer.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPlayer.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPlayer.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPlayer.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testPlayer.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testPlayer.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPlayer.getPost()).isEqualTo(DEFAULT_POST);
        assertThat(testPlayer.getTotalPower()).isEqualTo(DEFAULT_TOTAL_POWER);
        assertThat(testPlayer.getGoalkeeping()).isEqualTo(DEFAULT_GOALKEEPING);
        assertThat(testPlayer.getDefence()).isEqualTo(DEFAULT_DEFENCE);
        assertThat(testPlayer.getTackling()).isEqualTo(DEFAULT_TACKLING);
        assertThat(testPlayer.getPassing()).isEqualTo(DEFAULT_PASSING);
        assertThat(testPlayer.getTeamSkill()).isEqualTo(DEFAULT_TEAM_SKILL);
        assertThat(testPlayer.getBallSkill()).isEqualTo(DEFAULT_BALL_SKILL);
        assertThat(testPlayer.getShooting()).isEqualTo(DEFAULT_SHOOTING);
        assertThat(testPlayer.getLongShooting()).isEqualTo(DEFAULT_LONG_SHOOTING);
        assertThat(testPlayer.getDribbling()).isEqualTo(DEFAULT_DRIBBLING);
        assertThat(testPlayer.getTechnique()).isEqualTo(DEFAULT_TECHNIQUE);
        assertThat(testPlayer.getConfidence()).isEqualTo(DEFAULT_CONFIDENCE);
    }

    @Test
    @Transactional
    void createPlayerWithExistingId() throws Exception {
        // Create the Player with an existing ID
        playerRepository.saveAndFlush(player);

        int databaseSizeBeforeCreate = playerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isBadRequest());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerRepository.findAll().size();
        // set the field null
        player.setFirstName(null);

        // Create the Player, which fails.

        restPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isBadRequest());

        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerRepository.findAll().size();
        // set the field null
        player.setLastName(null);

        // Create the Player, which fails.

        restPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isBadRequest());

        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerRepository.findAll().size();
        // set the field null
        player.setGender(null);

        // Create the Player, which fails.

        restPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isBadRequest());

        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerRepository.findAll().size();
        // set the field null
        player.setCountry(null);

        // Create the Player, which fails.

        restPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isBadRequest());

        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerRepository.findAll().size();
        // set the field null
        player.setAge(null);

        // Create the Player, which fails.

        restPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isBadRequest());

        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerRepository.findAll().size();
        // set the field null
        player.setStatus(null);

        // Create the Player, which fails.

        restPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isBadRequest());

        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPostIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerRepository.findAll().size();
        // set the field null
        player.setPost(null);

        // Create the Player, which fails.

        restPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isBadRequest());

        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalPowerIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerRepository.findAll().size();
        // set the field null
        player.setTotalPower(null);

        // Create the Player, which fails.

        restPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isBadRequest());

        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGoalkeepingIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerRepository.findAll().size();
        // set the field null
        player.setGoalkeeping(null);

        // Create the Player, which fails.

        restPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isBadRequest());

        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDefenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerRepository.findAll().size();
        // set the field null
        player.setDefence(null);

        // Create the Player, which fails.

        restPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isBadRequest());

        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTacklingIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerRepository.findAll().size();
        // set the field null
        player.setTackling(null);

        // Create the Player, which fails.

        restPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isBadRequest());

        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPassingIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerRepository.findAll().size();
        // set the field null
        player.setPassing(null);

        // Create the Player, which fails.

        restPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isBadRequest());

        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTeamSkillIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerRepository.findAll().size();
        // set the field null
        player.setTeamSkill(null);

        // Create the Player, which fails.

        restPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isBadRequest());

        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBallSkillIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerRepository.findAll().size();
        // set the field null
        player.setBallSkill(null);

        // Create the Player, which fails.

        restPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isBadRequest());

        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkShootingIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerRepository.findAll().size();
        // set the field null
        player.setShooting(null);

        // Create the Player, which fails.

        restPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isBadRequest());

        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLongShootingIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerRepository.findAll().size();
        // set the field null
        player.setLongShooting(null);

        // Create the Player, which fails.

        restPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isBadRequest());

        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDribblingIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerRepository.findAll().size();
        // set the field null
        player.setDribbling(null);

        // Create the Player, which fails.

        restPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isBadRequest());

        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTechniqueIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerRepository.findAll().size();
        // set the field null
        player.setTechnique(null);

        // Create the Player, which fails.

        restPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isBadRequest());

        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConfidenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerRepository.findAll().size();
        // set the field null
        player.setConfidence(null);

        // Create the Player, which fails.

        restPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isBadRequest());

        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlayers() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList
        restPlayerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(player.getId().toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].post").value(hasItem(DEFAULT_POST.toString())))
            .andExpect(jsonPath("$.[*].totalPower").value(hasItem(DEFAULT_TOTAL_POWER)))
            .andExpect(jsonPath("$.[*].goalkeeping").value(hasItem(DEFAULT_GOALKEEPING)))
            .andExpect(jsonPath("$.[*].defence").value(hasItem(DEFAULT_DEFENCE)))
            .andExpect(jsonPath("$.[*].tackling").value(hasItem(DEFAULT_TACKLING)))
            .andExpect(jsonPath("$.[*].passing").value(hasItem(DEFAULT_PASSING)))
            .andExpect(jsonPath("$.[*].teamSkill").value(hasItem(DEFAULT_TEAM_SKILL)))
            .andExpect(jsonPath("$.[*].ballSkill").value(hasItem(DEFAULT_BALL_SKILL)))
            .andExpect(jsonPath("$.[*].shooting").value(hasItem(DEFAULT_SHOOTING)))
            .andExpect(jsonPath("$.[*].longShooting").value(hasItem(DEFAULT_LONG_SHOOTING)))
            .andExpect(jsonPath("$.[*].dribbling").value(hasItem(DEFAULT_DRIBBLING)))
            .andExpect(jsonPath("$.[*].technique").value(hasItem(DEFAULT_TECHNIQUE)))
            .andExpect(jsonPath("$.[*].confidence").value(hasItem(DEFAULT_CONFIDENCE)));
    }

    @Test
    @Transactional
    void getPlayer() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get the player
        restPlayerMockMvc
            .perform(get(ENTITY_API_URL_ID, player.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(player.getId().toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.post").value(DEFAULT_POST.toString()))
            .andExpect(jsonPath("$.totalPower").value(DEFAULT_TOTAL_POWER))
            .andExpect(jsonPath("$.goalkeeping").value(DEFAULT_GOALKEEPING))
            .andExpect(jsonPath("$.defence").value(DEFAULT_DEFENCE))
            .andExpect(jsonPath("$.tackling").value(DEFAULT_TACKLING))
            .andExpect(jsonPath("$.passing").value(DEFAULT_PASSING))
            .andExpect(jsonPath("$.teamSkill").value(DEFAULT_TEAM_SKILL))
            .andExpect(jsonPath("$.ballSkill").value(DEFAULT_BALL_SKILL))
            .andExpect(jsonPath("$.shooting").value(DEFAULT_SHOOTING))
            .andExpect(jsonPath("$.longShooting").value(DEFAULT_LONG_SHOOTING))
            .andExpect(jsonPath("$.dribbling").value(DEFAULT_DRIBBLING))
            .andExpect(jsonPath("$.technique").value(DEFAULT_TECHNIQUE))
            .andExpect(jsonPath("$.confidence").value(DEFAULT_CONFIDENCE));
    }

    @Test
    @Transactional
    void getNonExistingPlayer() throws Exception {
        // Get the player
        restPlayerMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPlayer() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        int databaseSizeBeforeUpdate = playerRepository.findAll().size();

        // Update the player
        Player updatedPlayer = playerRepository.findById(player.getId()).get();
        // Disconnect from session so that the updates on updatedPlayer are not directly saved in db
        em.detach(updatedPlayer);
        updatedPlayer
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .gender(UPDATED_GENDER)
            .country(UPDATED_COUNTRY)
            .age(UPDATED_AGE)
            .status(UPDATED_STATUS)
            .post(UPDATED_POST)
            .totalPower(UPDATED_TOTAL_POWER)
            .goalkeeping(UPDATED_GOALKEEPING)
            .defence(UPDATED_DEFENCE)
            .tackling(UPDATED_TACKLING)
            .passing(UPDATED_PASSING)
            .teamSkill(UPDATED_TEAM_SKILL)
            .ballSkill(UPDATED_BALL_SKILL)
            .shooting(UPDATED_SHOOTING)
            .longShooting(UPDATED_LONG_SHOOTING)
            .dribbling(UPDATED_DRIBBLING)
            .technique(UPDATED_TECHNIQUE)
            .confidence(UPDATED_CONFIDENCE);

        restPlayerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlayer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlayer))
            )
            .andExpect(status().isOk());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
        Player testPlayer = playerList.get(playerList.size() - 1);
        assertThat(testPlayer.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPlayer.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPlayer.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPlayer.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testPlayer.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testPlayer.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPlayer.getPost()).isEqualTo(UPDATED_POST);
        assertThat(testPlayer.getTotalPower()).isEqualTo(UPDATED_TOTAL_POWER);
        assertThat(testPlayer.getGoalkeeping()).isEqualTo(UPDATED_GOALKEEPING);
        assertThat(testPlayer.getDefence()).isEqualTo(UPDATED_DEFENCE);
        assertThat(testPlayer.getTackling()).isEqualTo(UPDATED_TACKLING);
        assertThat(testPlayer.getPassing()).isEqualTo(UPDATED_PASSING);
        assertThat(testPlayer.getTeamSkill()).isEqualTo(UPDATED_TEAM_SKILL);
        assertThat(testPlayer.getBallSkill()).isEqualTo(UPDATED_BALL_SKILL);
        assertThat(testPlayer.getShooting()).isEqualTo(UPDATED_SHOOTING);
        assertThat(testPlayer.getLongShooting()).isEqualTo(UPDATED_LONG_SHOOTING);
        assertThat(testPlayer.getDribbling()).isEqualTo(UPDATED_DRIBBLING);
        assertThat(testPlayer.getTechnique()).isEqualTo(UPDATED_TECHNIQUE);
        assertThat(testPlayer.getConfidence()).isEqualTo(UPDATED_CONFIDENCE);
    }

    @Test
    @Transactional
    void putNonExistingPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();
        player.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, player.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(player))
            )
            .andExpect(status().isBadRequest());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();
        player.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(player))
            )
            .andExpect(status().isBadRequest());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();
        player.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlayerWithPatch() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        int databaseSizeBeforeUpdate = playerRepository.findAll().size();

        // Update the player using partial update
        Player partialUpdatedPlayer = new Player();
        partialUpdatedPlayer.setId(player.getId());

        partialUpdatedPlayer
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .gender(UPDATED_GENDER)
            .age(UPDATED_AGE)
            .status(UPDATED_STATUS)
            .post(UPDATED_POST)
            .totalPower(UPDATED_TOTAL_POWER)
            .goalkeeping(UPDATED_GOALKEEPING)
            .defence(UPDATED_DEFENCE)
            .tackling(UPDATED_TACKLING)
            .passing(UPDATED_PASSING)
            .teamSkill(UPDATED_TEAM_SKILL)
            .shooting(UPDATED_SHOOTING)
            .longShooting(UPDATED_LONG_SHOOTING)
            .technique(UPDATED_TECHNIQUE)
            .confidence(UPDATED_CONFIDENCE);

        restPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlayer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlayer))
            )
            .andExpect(status().isOk());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
        Player testPlayer = playerList.get(playerList.size() - 1);
        assertThat(testPlayer.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPlayer.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPlayer.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPlayer.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testPlayer.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testPlayer.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPlayer.getPost()).isEqualTo(UPDATED_POST);
        assertThat(testPlayer.getTotalPower()).isEqualTo(UPDATED_TOTAL_POWER);
        assertThat(testPlayer.getGoalkeeping()).isEqualTo(UPDATED_GOALKEEPING);
        assertThat(testPlayer.getDefence()).isEqualTo(UPDATED_DEFENCE);
        assertThat(testPlayer.getTackling()).isEqualTo(UPDATED_TACKLING);
        assertThat(testPlayer.getPassing()).isEqualTo(UPDATED_PASSING);
        assertThat(testPlayer.getTeamSkill()).isEqualTo(UPDATED_TEAM_SKILL);
        assertThat(testPlayer.getBallSkill()).isEqualTo(DEFAULT_BALL_SKILL);
        assertThat(testPlayer.getShooting()).isEqualTo(UPDATED_SHOOTING);
        assertThat(testPlayer.getLongShooting()).isEqualTo(UPDATED_LONG_SHOOTING);
        assertThat(testPlayer.getDribbling()).isEqualTo(DEFAULT_DRIBBLING);
        assertThat(testPlayer.getTechnique()).isEqualTo(UPDATED_TECHNIQUE);
        assertThat(testPlayer.getConfidence()).isEqualTo(UPDATED_CONFIDENCE);
    }

    @Test
    @Transactional
    void fullUpdatePlayerWithPatch() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        int databaseSizeBeforeUpdate = playerRepository.findAll().size();

        // Update the player using partial update
        Player partialUpdatedPlayer = new Player();
        partialUpdatedPlayer.setId(player.getId());

        partialUpdatedPlayer
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .gender(UPDATED_GENDER)
            .country(UPDATED_COUNTRY)
            .age(UPDATED_AGE)
            .status(UPDATED_STATUS)
            .post(UPDATED_POST)
            .totalPower(UPDATED_TOTAL_POWER)
            .goalkeeping(UPDATED_GOALKEEPING)
            .defence(UPDATED_DEFENCE)
            .tackling(UPDATED_TACKLING)
            .passing(UPDATED_PASSING)
            .teamSkill(UPDATED_TEAM_SKILL)
            .ballSkill(UPDATED_BALL_SKILL)
            .shooting(UPDATED_SHOOTING)
            .longShooting(UPDATED_LONG_SHOOTING)
            .dribbling(UPDATED_DRIBBLING)
            .technique(UPDATED_TECHNIQUE)
            .confidence(UPDATED_CONFIDENCE);

        restPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlayer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlayer))
            )
            .andExpect(status().isOk());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
        Player testPlayer = playerList.get(playerList.size() - 1);
        assertThat(testPlayer.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPlayer.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPlayer.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPlayer.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testPlayer.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testPlayer.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPlayer.getPost()).isEqualTo(UPDATED_POST);
        assertThat(testPlayer.getTotalPower()).isEqualTo(UPDATED_TOTAL_POWER);
        assertThat(testPlayer.getGoalkeeping()).isEqualTo(UPDATED_GOALKEEPING);
        assertThat(testPlayer.getDefence()).isEqualTo(UPDATED_DEFENCE);
        assertThat(testPlayer.getTackling()).isEqualTo(UPDATED_TACKLING);
        assertThat(testPlayer.getPassing()).isEqualTo(UPDATED_PASSING);
        assertThat(testPlayer.getTeamSkill()).isEqualTo(UPDATED_TEAM_SKILL);
        assertThat(testPlayer.getBallSkill()).isEqualTo(UPDATED_BALL_SKILL);
        assertThat(testPlayer.getShooting()).isEqualTo(UPDATED_SHOOTING);
        assertThat(testPlayer.getLongShooting()).isEqualTo(UPDATED_LONG_SHOOTING);
        assertThat(testPlayer.getDribbling()).isEqualTo(UPDATED_DRIBBLING);
        assertThat(testPlayer.getTechnique()).isEqualTo(UPDATED_TECHNIQUE);
        assertThat(testPlayer.getConfidence()).isEqualTo(UPDATED_CONFIDENCE);
    }

    @Test
    @Transactional
    void patchNonExistingPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();
        player.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, player.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(player))
            )
            .andExpect(status().isBadRequest());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();
        player.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(player))
            )
            .andExpect(status().isBadRequest());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();
        player.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlayer() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        int databaseSizeBeforeDelete = playerRepository.findAll().size();

        // Delete the player
        restPlayerMockMvc
            .perform(delete(ENTITY_API_URL_ID, player.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
