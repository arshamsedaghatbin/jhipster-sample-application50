package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.GenerationRule;
import com.mycompany.myapp.domain.enumeration.GenerationRuleStatus;
import com.mycompany.myapp.repository.GenerationRuleRepository;
import com.mycompany.myapp.service.dto.GenerationRuleDTO;
import com.mycompany.myapp.service.mapper.GenerationRuleMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
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
 * Integration tests for the {@link GenerationRuleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GenerationRuleResourceIT {

    private static final String DEFAULT_CENTER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CENTER_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_VALID_TO = "AAAAAAAAAA";
    private static final String UPDATED_VALID_TO = "BBBBBBBBBB";

    private static final Integer DEFAULT_DAYS_OF_WEEK = 1;
    private static final Integer UPDATED_DAYS_OF_WEEK = 2;

    private static final Integer DEFAULT_DEFAULT_CAPACITY = 1;
    private static final Integer UPDATED_DEFAULT_CAPACITY = 2;

    private static final Instant DEFAULT_START_SLOTS_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_SLOTS_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_SLOTS_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_SLOTS_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_SLOT_DURATION = 1;
    private static final Integer UPDATED_SLOT_DURATION = 2;

    private static final Integer DEFAULT_MAX_AVAILABLE_DAYS = 1;
    private static final Integer UPDATED_MAX_AVAILABLE_DAYS = 2;

    private static final GenerationRuleStatus DEFAULT_STATUS = GenerationRuleStatus.ACTIVE;
    private static final GenerationRuleStatus UPDATED_STATUS = GenerationRuleStatus.ACTIVE;

    private static final String DEFAULT_ACTION_BY = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/generation-rules";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GenerationRuleRepository generationRuleRepository;

    @Autowired
    private GenerationRuleMapper generationRuleMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGenerationRuleMockMvc;

    private GenerationRule generationRule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GenerationRule createEntity(EntityManager em) {
        GenerationRule generationRule = new GenerationRule()
            .centerName(DEFAULT_CENTER_NAME)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO)
            .daysOfWeek(DEFAULT_DAYS_OF_WEEK)
            .defaultCapacity(DEFAULT_DEFAULT_CAPACITY)
            .startSlotsTime(DEFAULT_START_SLOTS_TIME)
            .endSlotsTime(DEFAULT_END_SLOTS_TIME)
            .slotDuration(DEFAULT_SLOT_DURATION)
            .maxAvailableDays(DEFAULT_MAX_AVAILABLE_DAYS)
            .status(DEFAULT_STATUS)
            .actionBy(DEFAULT_ACTION_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return generationRule;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GenerationRule createUpdatedEntity(EntityManager em) {
        GenerationRule generationRule = new GenerationRule()
            .centerName(UPDATED_CENTER_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .daysOfWeek(UPDATED_DAYS_OF_WEEK)
            .defaultCapacity(UPDATED_DEFAULT_CAPACITY)
            .startSlotsTime(UPDATED_START_SLOTS_TIME)
            .endSlotsTime(UPDATED_END_SLOTS_TIME)
            .slotDuration(UPDATED_SLOT_DURATION)
            .maxAvailableDays(UPDATED_MAX_AVAILABLE_DAYS)
            .status(UPDATED_STATUS)
            .actionBy(UPDATED_ACTION_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return generationRule;
    }

    @BeforeEach
    public void initTest() {
        generationRule = createEntity(em);
    }

    @Test
    @Transactional
    void createGenerationRule() throws Exception {
        int databaseSizeBeforeCreate = generationRuleRepository.findAll().size();
        // Create the GenerationRule
        GenerationRuleDTO generationRuleDTO = generationRuleMapper.toDto(generationRule);
        restGenerationRuleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generationRuleDTO))
            )
            .andExpect(status().isCreated());

        // Validate the GenerationRule in the database
        List<GenerationRule> generationRuleList = generationRuleRepository.findAll();
        assertThat(generationRuleList).hasSize(databaseSizeBeforeCreate + 1);
        GenerationRule testGenerationRule = generationRuleList.get(generationRuleList.size() - 1);
        assertThat(testGenerationRule.getCenterName()).isEqualTo(DEFAULT_CENTER_NAME);
        assertThat(testGenerationRule.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testGenerationRule.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
        assertThat(testGenerationRule.getDaysOfWeek()).isEqualTo(DEFAULT_DAYS_OF_WEEK);
        assertThat(testGenerationRule.getDefaultCapacity()).isEqualTo(DEFAULT_DEFAULT_CAPACITY);
        assertThat(testGenerationRule.getStartSlotsTime()).isEqualTo(DEFAULT_START_SLOTS_TIME);
        assertThat(testGenerationRule.getEndSlotsTime()).isEqualTo(DEFAULT_END_SLOTS_TIME);
        assertThat(testGenerationRule.getSlotDuration()).isEqualTo(DEFAULT_SLOT_DURATION);
        assertThat(testGenerationRule.getMaxAvailableDays()).isEqualTo(DEFAULT_MAX_AVAILABLE_DAYS);
        assertThat(testGenerationRule.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testGenerationRule.getActionBy()).isEqualTo(DEFAULT_ACTION_BY);
        assertThat(testGenerationRule.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testGenerationRule.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createGenerationRuleWithExistingId() throws Exception {
        // Create the GenerationRule with an existing ID
        generationRule.setId(1L);
        GenerationRuleDTO generationRuleDTO = generationRuleMapper.toDto(generationRule);

        int databaseSizeBeforeCreate = generationRuleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGenerationRuleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generationRuleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GenerationRule in the database
        List<GenerationRule> generationRuleList = generationRuleRepository.findAll();
        assertThat(generationRuleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCenterNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = generationRuleRepository.findAll().size();
        // set the field null
        generationRule.setCenterName(null);

        // Create the GenerationRule, which fails.
        GenerationRuleDTO generationRuleDTO = generationRuleMapper.toDto(generationRule);

        restGenerationRuleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generationRuleDTO))
            )
            .andExpect(status().isBadRequest());

        List<GenerationRule> generationRuleList = generationRuleRepository.findAll();
        assertThat(generationRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = generationRuleRepository.findAll().size();
        // set the field null
        generationRule.setValidFrom(null);

        // Create the GenerationRule, which fails.
        GenerationRuleDTO generationRuleDTO = generationRuleMapper.toDto(generationRule);

        restGenerationRuleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generationRuleDTO))
            )
            .andExpect(status().isBadRequest());

        List<GenerationRule> generationRuleList = generationRuleRepository.findAll();
        assertThat(generationRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = generationRuleRepository.findAll().size();
        // set the field null
        generationRule.setValidTo(null);

        // Create the GenerationRule, which fails.
        GenerationRuleDTO generationRuleDTO = generationRuleMapper.toDto(generationRule);

        restGenerationRuleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generationRuleDTO))
            )
            .andExpect(status().isBadRequest());

        List<GenerationRule> generationRuleList = generationRuleRepository.findAll();
        assertThat(generationRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDaysOfWeekIsRequired() throws Exception {
        int databaseSizeBeforeTest = generationRuleRepository.findAll().size();
        // set the field null
        generationRule.setDaysOfWeek(null);

        // Create the GenerationRule, which fails.
        GenerationRuleDTO generationRuleDTO = generationRuleMapper.toDto(generationRule);

        restGenerationRuleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generationRuleDTO))
            )
            .andExpect(status().isBadRequest());

        List<GenerationRule> generationRuleList = generationRuleRepository.findAll();
        assertThat(generationRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDefaultCapacityIsRequired() throws Exception {
        int databaseSizeBeforeTest = generationRuleRepository.findAll().size();
        // set the field null
        generationRule.setDefaultCapacity(null);

        // Create the GenerationRule, which fails.
        GenerationRuleDTO generationRuleDTO = generationRuleMapper.toDto(generationRule);

        restGenerationRuleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generationRuleDTO))
            )
            .andExpect(status().isBadRequest());

        List<GenerationRule> generationRuleList = generationRuleRepository.findAll();
        assertThat(generationRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStartSlotsTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = generationRuleRepository.findAll().size();
        // set the field null
        generationRule.setStartSlotsTime(null);

        // Create the GenerationRule, which fails.
        GenerationRuleDTO generationRuleDTO = generationRuleMapper.toDto(generationRule);

        restGenerationRuleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generationRuleDTO))
            )
            .andExpect(status().isBadRequest());

        List<GenerationRule> generationRuleList = generationRuleRepository.findAll();
        assertThat(generationRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndSlotsTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = generationRuleRepository.findAll().size();
        // set the field null
        generationRule.setEndSlotsTime(null);

        // Create the GenerationRule, which fails.
        GenerationRuleDTO generationRuleDTO = generationRuleMapper.toDto(generationRule);

        restGenerationRuleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generationRuleDTO))
            )
            .andExpect(status().isBadRequest());

        List<GenerationRule> generationRuleList = generationRuleRepository.findAll();
        assertThat(generationRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSlotDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = generationRuleRepository.findAll().size();
        // set the field null
        generationRule.setSlotDuration(null);

        // Create the GenerationRule, which fails.
        GenerationRuleDTO generationRuleDTO = generationRuleMapper.toDto(generationRule);

        restGenerationRuleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generationRuleDTO))
            )
            .andExpect(status().isBadRequest());

        List<GenerationRule> generationRuleList = generationRuleRepository.findAll();
        assertThat(generationRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMaxAvailableDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = generationRuleRepository.findAll().size();
        // set the field null
        generationRule.setMaxAvailableDays(null);

        // Create the GenerationRule, which fails.
        GenerationRuleDTO generationRuleDTO = generationRuleMapper.toDto(generationRule);

        restGenerationRuleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generationRuleDTO))
            )
            .andExpect(status().isBadRequest());

        List<GenerationRule> generationRuleList = generationRuleRepository.findAll();
        assertThat(generationRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = generationRuleRepository.findAll().size();
        // set the field null
        generationRule.setStatus(null);

        // Create the GenerationRule, which fails.
        GenerationRuleDTO generationRuleDTO = generationRuleMapper.toDto(generationRule);

        restGenerationRuleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generationRuleDTO))
            )
            .andExpect(status().isBadRequest());

        List<GenerationRule> generationRuleList = generationRuleRepository.findAll();
        assertThat(generationRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActionByIsRequired() throws Exception {
        int databaseSizeBeforeTest = generationRuleRepository.findAll().size();
        // set the field null
        generationRule.setActionBy(null);

        // Create the GenerationRule, which fails.
        GenerationRuleDTO generationRuleDTO = generationRuleMapper.toDto(generationRule);

        restGenerationRuleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generationRuleDTO))
            )
            .andExpect(status().isBadRequest());

        List<GenerationRule> generationRuleList = generationRuleRepository.findAll();
        assertThat(generationRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = generationRuleRepository.findAll().size();
        // set the field null
        generationRule.setCreatedAt(null);

        // Create the GenerationRule, which fails.
        GenerationRuleDTO generationRuleDTO = generationRuleMapper.toDto(generationRule);

        restGenerationRuleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generationRuleDTO))
            )
            .andExpect(status().isBadRequest());

        List<GenerationRule> generationRuleList = generationRuleRepository.findAll();
        assertThat(generationRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGenerationRules() throws Exception {
        // Initialize the database
        generationRuleRepository.saveAndFlush(generationRule);

        // Get all the generationRuleList
        restGenerationRuleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(generationRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].centerName").value(hasItem(DEFAULT_CENTER_NAME)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO)))
            .andExpect(jsonPath("$.[*].daysOfWeek").value(hasItem(DEFAULT_DAYS_OF_WEEK)))
            .andExpect(jsonPath("$.[*].defaultCapacity").value(hasItem(DEFAULT_DEFAULT_CAPACITY)))
            .andExpect(jsonPath("$.[*].startSlotsTime").value(hasItem(DEFAULT_START_SLOTS_TIME.toString())))
            .andExpect(jsonPath("$.[*].endSlotsTime").value(hasItem(DEFAULT_END_SLOTS_TIME.toString())))
            .andExpect(jsonPath("$.[*].slotDuration").value(hasItem(DEFAULT_SLOT_DURATION)))
            .andExpect(jsonPath("$.[*].maxAvailableDays").value(hasItem(DEFAULT_MAX_AVAILABLE_DAYS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].actionBy").value(hasItem(DEFAULT_ACTION_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getGenerationRule() throws Exception {
        // Initialize the database
        generationRuleRepository.saveAndFlush(generationRule);

        // Get the generationRule
        restGenerationRuleMockMvc
            .perform(get(ENTITY_API_URL_ID, generationRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(generationRule.getId().intValue()))
            .andExpect(jsonPath("$.centerName").value(DEFAULT_CENTER_NAME))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO))
            .andExpect(jsonPath("$.daysOfWeek").value(DEFAULT_DAYS_OF_WEEK))
            .andExpect(jsonPath("$.defaultCapacity").value(DEFAULT_DEFAULT_CAPACITY))
            .andExpect(jsonPath("$.startSlotsTime").value(DEFAULT_START_SLOTS_TIME.toString()))
            .andExpect(jsonPath("$.endSlotsTime").value(DEFAULT_END_SLOTS_TIME.toString()))
            .andExpect(jsonPath("$.slotDuration").value(DEFAULT_SLOT_DURATION))
            .andExpect(jsonPath("$.maxAvailableDays").value(DEFAULT_MAX_AVAILABLE_DAYS))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.actionBy").value(DEFAULT_ACTION_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingGenerationRule() throws Exception {
        // Get the generationRule
        restGenerationRuleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGenerationRule() throws Exception {
        // Initialize the database
        generationRuleRepository.saveAndFlush(generationRule);

        int databaseSizeBeforeUpdate = generationRuleRepository.findAll().size();

        // Update the generationRule
        GenerationRule updatedGenerationRule = generationRuleRepository.findById(generationRule.getId()).get();
        // Disconnect from session so that the updates on updatedGenerationRule are not directly saved in db
        em.detach(updatedGenerationRule);
        updatedGenerationRule
            .centerName(UPDATED_CENTER_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .daysOfWeek(UPDATED_DAYS_OF_WEEK)
            .defaultCapacity(UPDATED_DEFAULT_CAPACITY)
            .startSlotsTime(UPDATED_START_SLOTS_TIME)
            .endSlotsTime(UPDATED_END_SLOTS_TIME)
            .slotDuration(UPDATED_SLOT_DURATION)
            .maxAvailableDays(UPDATED_MAX_AVAILABLE_DAYS)
            .status(UPDATED_STATUS)
            .actionBy(UPDATED_ACTION_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        GenerationRuleDTO generationRuleDTO = generationRuleMapper.toDto(updatedGenerationRule);

        restGenerationRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, generationRuleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(generationRuleDTO))
            )
            .andExpect(status().isOk());

        // Validate the GenerationRule in the database
        List<GenerationRule> generationRuleList = generationRuleRepository.findAll();
        assertThat(generationRuleList).hasSize(databaseSizeBeforeUpdate);
        GenerationRule testGenerationRule = generationRuleList.get(generationRuleList.size() - 1);
        assertThat(testGenerationRule.getCenterName()).isEqualTo(UPDATED_CENTER_NAME);
        assertThat(testGenerationRule.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testGenerationRule.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testGenerationRule.getDaysOfWeek()).isEqualTo(UPDATED_DAYS_OF_WEEK);
        assertThat(testGenerationRule.getDefaultCapacity()).isEqualTo(UPDATED_DEFAULT_CAPACITY);
        assertThat(testGenerationRule.getStartSlotsTime()).isEqualTo(UPDATED_START_SLOTS_TIME);
        assertThat(testGenerationRule.getEndSlotsTime()).isEqualTo(UPDATED_END_SLOTS_TIME);
        assertThat(testGenerationRule.getSlotDuration()).isEqualTo(UPDATED_SLOT_DURATION);
        assertThat(testGenerationRule.getMaxAvailableDays()).isEqualTo(UPDATED_MAX_AVAILABLE_DAYS);
        assertThat(testGenerationRule.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testGenerationRule.getActionBy()).isEqualTo(UPDATED_ACTION_BY);
        assertThat(testGenerationRule.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testGenerationRule.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingGenerationRule() throws Exception {
        int databaseSizeBeforeUpdate = generationRuleRepository.findAll().size();
        generationRule.setId(count.incrementAndGet());

        // Create the GenerationRule
        GenerationRuleDTO generationRuleDTO = generationRuleMapper.toDto(generationRule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGenerationRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, generationRuleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(generationRuleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GenerationRule in the database
        List<GenerationRule> generationRuleList = generationRuleRepository.findAll();
        assertThat(generationRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGenerationRule() throws Exception {
        int databaseSizeBeforeUpdate = generationRuleRepository.findAll().size();
        generationRule.setId(count.incrementAndGet());

        // Create the GenerationRule
        GenerationRuleDTO generationRuleDTO = generationRuleMapper.toDto(generationRule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGenerationRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(generationRuleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GenerationRule in the database
        List<GenerationRule> generationRuleList = generationRuleRepository.findAll();
        assertThat(generationRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGenerationRule() throws Exception {
        int databaseSizeBeforeUpdate = generationRuleRepository.findAll().size();
        generationRule.setId(count.incrementAndGet());

        // Create the GenerationRule
        GenerationRuleDTO generationRuleDTO = generationRuleMapper.toDto(generationRule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGenerationRuleMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generationRuleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GenerationRule in the database
        List<GenerationRule> generationRuleList = generationRuleRepository.findAll();
        assertThat(generationRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGenerationRuleWithPatch() throws Exception {
        // Initialize the database
        generationRuleRepository.saveAndFlush(generationRule);

        int databaseSizeBeforeUpdate = generationRuleRepository.findAll().size();

        // Update the generationRule using partial update
        GenerationRule partialUpdatedGenerationRule = new GenerationRule();
        partialUpdatedGenerationRule.setId(generationRule.getId());

        partialUpdatedGenerationRule
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .defaultCapacity(UPDATED_DEFAULT_CAPACITY)
            .startSlotsTime(UPDATED_START_SLOTS_TIME)
            .slotDuration(UPDATED_SLOT_DURATION)
            .actionBy(UPDATED_ACTION_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restGenerationRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGenerationRule.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGenerationRule))
            )
            .andExpect(status().isOk());

        // Validate the GenerationRule in the database
        List<GenerationRule> generationRuleList = generationRuleRepository.findAll();
        assertThat(generationRuleList).hasSize(databaseSizeBeforeUpdate);
        GenerationRule testGenerationRule = generationRuleList.get(generationRuleList.size() - 1);
        assertThat(testGenerationRule.getCenterName()).isEqualTo(DEFAULT_CENTER_NAME);
        assertThat(testGenerationRule.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testGenerationRule.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testGenerationRule.getDaysOfWeek()).isEqualTo(DEFAULT_DAYS_OF_WEEK);
        assertThat(testGenerationRule.getDefaultCapacity()).isEqualTo(UPDATED_DEFAULT_CAPACITY);
        assertThat(testGenerationRule.getStartSlotsTime()).isEqualTo(UPDATED_START_SLOTS_TIME);
        assertThat(testGenerationRule.getEndSlotsTime()).isEqualTo(DEFAULT_END_SLOTS_TIME);
        assertThat(testGenerationRule.getSlotDuration()).isEqualTo(UPDATED_SLOT_DURATION);
        assertThat(testGenerationRule.getMaxAvailableDays()).isEqualTo(DEFAULT_MAX_AVAILABLE_DAYS);
        assertThat(testGenerationRule.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testGenerationRule.getActionBy()).isEqualTo(UPDATED_ACTION_BY);
        assertThat(testGenerationRule.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testGenerationRule.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateGenerationRuleWithPatch() throws Exception {
        // Initialize the database
        generationRuleRepository.saveAndFlush(generationRule);

        int databaseSizeBeforeUpdate = generationRuleRepository.findAll().size();

        // Update the generationRule using partial update
        GenerationRule partialUpdatedGenerationRule = new GenerationRule();
        partialUpdatedGenerationRule.setId(generationRule.getId());

        partialUpdatedGenerationRule
            .centerName(UPDATED_CENTER_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .daysOfWeek(UPDATED_DAYS_OF_WEEK)
            .defaultCapacity(UPDATED_DEFAULT_CAPACITY)
            .startSlotsTime(UPDATED_START_SLOTS_TIME)
            .endSlotsTime(UPDATED_END_SLOTS_TIME)
            .slotDuration(UPDATED_SLOT_DURATION)
            .maxAvailableDays(UPDATED_MAX_AVAILABLE_DAYS)
            .status(UPDATED_STATUS)
            .actionBy(UPDATED_ACTION_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restGenerationRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGenerationRule.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGenerationRule))
            )
            .andExpect(status().isOk());

        // Validate the GenerationRule in the database
        List<GenerationRule> generationRuleList = generationRuleRepository.findAll();
        assertThat(generationRuleList).hasSize(databaseSizeBeforeUpdate);
        GenerationRule testGenerationRule = generationRuleList.get(generationRuleList.size() - 1);
        assertThat(testGenerationRule.getCenterName()).isEqualTo(UPDATED_CENTER_NAME);
        assertThat(testGenerationRule.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testGenerationRule.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testGenerationRule.getDaysOfWeek()).isEqualTo(UPDATED_DAYS_OF_WEEK);
        assertThat(testGenerationRule.getDefaultCapacity()).isEqualTo(UPDATED_DEFAULT_CAPACITY);
        assertThat(testGenerationRule.getStartSlotsTime()).isEqualTo(UPDATED_START_SLOTS_TIME);
        assertThat(testGenerationRule.getEndSlotsTime()).isEqualTo(UPDATED_END_SLOTS_TIME);
        assertThat(testGenerationRule.getSlotDuration()).isEqualTo(UPDATED_SLOT_DURATION);
        assertThat(testGenerationRule.getMaxAvailableDays()).isEqualTo(UPDATED_MAX_AVAILABLE_DAYS);
        assertThat(testGenerationRule.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testGenerationRule.getActionBy()).isEqualTo(UPDATED_ACTION_BY);
        assertThat(testGenerationRule.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testGenerationRule.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingGenerationRule() throws Exception {
        int databaseSizeBeforeUpdate = generationRuleRepository.findAll().size();
        generationRule.setId(count.incrementAndGet());

        // Create the GenerationRule
        GenerationRuleDTO generationRuleDTO = generationRuleMapper.toDto(generationRule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGenerationRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, generationRuleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(generationRuleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GenerationRule in the database
        List<GenerationRule> generationRuleList = generationRuleRepository.findAll();
        assertThat(generationRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGenerationRule() throws Exception {
        int databaseSizeBeforeUpdate = generationRuleRepository.findAll().size();
        generationRule.setId(count.incrementAndGet());

        // Create the GenerationRule
        GenerationRuleDTO generationRuleDTO = generationRuleMapper.toDto(generationRule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGenerationRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(generationRuleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GenerationRule in the database
        List<GenerationRule> generationRuleList = generationRuleRepository.findAll();
        assertThat(generationRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGenerationRule() throws Exception {
        int databaseSizeBeforeUpdate = generationRuleRepository.findAll().size();
        generationRule.setId(count.incrementAndGet());

        // Create the GenerationRule
        GenerationRuleDTO generationRuleDTO = generationRuleMapper.toDto(generationRule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGenerationRuleMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(generationRuleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GenerationRule in the database
        List<GenerationRule> generationRuleList = generationRuleRepository.findAll();
        assertThat(generationRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGenerationRule() throws Exception {
        // Initialize the database
        generationRuleRepository.saveAndFlush(generationRule);

        int databaseSizeBeforeDelete = generationRuleRepository.findAll().size();

        // Delete the generationRule
        restGenerationRuleMockMvc
            .perform(delete(ENTITY_API_URL_ID, generationRule.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GenerationRule> generationRuleList = generationRuleRepository.findAll();
        assertThat(generationRuleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
