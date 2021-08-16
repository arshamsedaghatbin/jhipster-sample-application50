package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.TimeSlot;
import com.mycompany.myapp.domain.enumeration.AvailAbilityStatus;
import com.mycompany.myapp.repository.TimeSlotRepository;
import com.mycompany.myapp.service.dto.TimeSlotDTO;
import com.mycompany.myapp.service.mapper.TimeSlotMapper;
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
 * Integration tests for the {@link TimeSlotResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TimeSlotResourceIT {

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_CAPACITY = 1;
    private static final Integer UPDATED_CAPACITY = 2;

    private static final Integer DEFAULT_REMAINING = 1;
    private static final Integer UPDATED_REMAINING = 2;

    private static final AvailAbilityStatus DEFAULT_AVAILABILITY_STATUS = AvailAbilityStatus.AVAILABLE;
    private static final AvailAbilityStatus UPDATED_AVAILABILITY_STATUS = AvailAbilityStatus.AVAILABLE;

    private static final String DEFAULT_CENTER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CENTER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION_BY = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/time-slots";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private TimeSlotMapper timeSlotMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTimeSlotMockMvc;

    private TimeSlot timeSlot;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeSlot createEntity(EntityManager em) {
        TimeSlot timeSlot = new TimeSlot()
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .capacity(DEFAULT_CAPACITY)
            .remaining(DEFAULT_REMAINING)
            .availabilityStatus(DEFAULT_AVAILABILITY_STATUS)
            .centerName(DEFAULT_CENTER_NAME)
            .actionBy(DEFAULT_ACTION_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return timeSlot;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeSlot createUpdatedEntity(EntityManager em) {
        TimeSlot timeSlot = new TimeSlot()
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .capacity(UPDATED_CAPACITY)
            .remaining(UPDATED_REMAINING)
            .availabilityStatus(UPDATED_AVAILABILITY_STATUS)
            .centerName(UPDATED_CENTER_NAME)
            .actionBy(UPDATED_ACTION_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return timeSlot;
    }

    @BeforeEach
    public void initTest() {
        timeSlot = createEntity(em);
    }

    @Test
    @Transactional
    void createTimeSlot() throws Exception {
        int databaseSizeBeforeCreate = timeSlotRepository.findAll().size();
        // Create the TimeSlot
        TimeSlotDTO timeSlotDTO = timeSlotMapper.toDto(timeSlot);
        restTimeSlotMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeSlotDTO)))
            .andExpect(status().isCreated());

        // Validate the TimeSlot in the database
        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeCreate + 1);
        TimeSlot testTimeSlot = timeSlotList.get(timeSlotList.size() - 1);
        assertThat(testTimeSlot.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testTimeSlot.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testTimeSlot.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
        assertThat(testTimeSlot.getRemaining()).isEqualTo(DEFAULT_REMAINING);
        assertThat(testTimeSlot.getAvailabilityStatus()).isEqualTo(DEFAULT_AVAILABILITY_STATUS);
        assertThat(testTimeSlot.getCenterName()).isEqualTo(DEFAULT_CENTER_NAME);
        assertThat(testTimeSlot.getActionBy()).isEqualTo(DEFAULT_ACTION_BY);
        assertThat(testTimeSlot.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testTimeSlot.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createTimeSlotWithExistingId() throws Exception {
        // Create the TimeSlot with an existing ID
        timeSlot.setId(1L);
        TimeSlotDTO timeSlotDTO = timeSlotMapper.toDto(timeSlot);

        int databaseSizeBeforeCreate = timeSlotRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeSlotMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeSlotDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TimeSlot in the database
        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeSlotRepository.findAll().size();
        // set the field null
        timeSlot.setStartTime(null);

        // Create the TimeSlot, which fails.
        TimeSlotDTO timeSlotDTO = timeSlotMapper.toDto(timeSlot);

        restTimeSlotMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeSlotDTO)))
            .andExpect(status().isBadRequest());

        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeSlotRepository.findAll().size();
        // set the field null
        timeSlot.setEndTime(null);

        // Create the TimeSlot, which fails.
        TimeSlotDTO timeSlotDTO = timeSlotMapper.toDto(timeSlot);

        restTimeSlotMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeSlotDTO)))
            .andExpect(status().isBadRequest());

        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCapacityIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeSlotRepository.findAll().size();
        // set the field null
        timeSlot.setCapacity(null);

        // Create the TimeSlot, which fails.
        TimeSlotDTO timeSlotDTO = timeSlotMapper.toDto(timeSlot);

        restTimeSlotMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeSlotDTO)))
            .andExpect(status().isBadRequest());

        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRemainingIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeSlotRepository.findAll().size();
        // set the field null
        timeSlot.setRemaining(null);

        // Create the TimeSlot, which fails.
        TimeSlotDTO timeSlotDTO = timeSlotMapper.toDto(timeSlot);

        restTimeSlotMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeSlotDTO)))
            .andExpect(status().isBadRequest());

        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAvailabilityStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeSlotRepository.findAll().size();
        // set the field null
        timeSlot.setAvailabilityStatus(null);

        // Create the TimeSlot, which fails.
        TimeSlotDTO timeSlotDTO = timeSlotMapper.toDto(timeSlot);

        restTimeSlotMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeSlotDTO)))
            .andExpect(status().isBadRequest());

        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCenterNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeSlotRepository.findAll().size();
        // set the field null
        timeSlot.setCenterName(null);

        // Create the TimeSlot, which fails.
        TimeSlotDTO timeSlotDTO = timeSlotMapper.toDto(timeSlot);

        restTimeSlotMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeSlotDTO)))
            .andExpect(status().isBadRequest());

        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActionByIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeSlotRepository.findAll().size();
        // set the field null
        timeSlot.setActionBy(null);

        // Create the TimeSlot, which fails.
        TimeSlotDTO timeSlotDTO = timeSlotMapper.toDto(timeSlot);

        restTimeSlotMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeSlotDTO)))
            .andExpect(status().isBadRequest());

        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeSlotRepository.findAll().size();
        // set the field null
        timeSlot.setCreatedAt(null);

        // Create the TimeSlot, which fails.
        TimeSlotDTO timeSlotDTO = timeSlotMapper.toDto(timeSlot);

        restTimeSlotMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeSlotDTO)))
            .andExpect(status().isBadRequest());

        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeSlotRepository.findAll().size();
        // set the field null
        timeSlot.setUpdatedAt(null);

        // Create the TimeSlot, which fails.
        TimeSlotDTO timeSlotDTO = timeSlotMapper.toDto(timeSlot);

        restTimeSlotMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeSlotDTO)))
            .andExpect(status().isBadRequest());

        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTimeSlots() throws Exception {
        // Initialize the database
        timeSlotRepository.saveAndFlush(timeSlot);

        // Get all the timeSlotList
        restTimeSlotMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeSlot.getId().intValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY)))
            .andExpect(jsonPath("$.[*].remaining").value(hasItem(DEFAULT_REMAINING)))
            .andExpect(jsonPath("$.[*].availabilityStatus").value(hasItem(DEFAULT_AVAILABILITY_STATUS.toString())))
            .andExpect(jsonPath("$.[*].centerName").value(hasItem(DEFAULT_CENTER_NAME)))
            .andExpect(jsonPath("$.[*].actionBy").value(hasItem(DEFAULT_ACTION_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getTimeSlot() throws Exception {
        // Initialize the database
        timeSlotRepository.saveAndFlush(timeSlot);

        // Get the timeSlot
        restTimeSlotMockMvc
            .perform(get(ENTITY_API_URL_ID, timeSlot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(timeSlot.getId().intValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY))
            .andExpect(jsonPath("$.remaining").value(DEFAULT_REMAINING))
            .andExpect(jsonPath("$.availabilityStatus").value(DEFAULT_AVAILABILITY_STATUS.toString()))
            .andExpect(jsonPath("$.centerName").value(DEFAULT_CENTER_NAME))
            .andExpect(jsonPath("$.actionBy").value(DEFAULT_ACTION_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTimeSlot() throws Exception {
        // Get the timeSlot
        restTimeSlotMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTimeSlot() throws Exception {
        // Initialize the database
        timeSlotRepository.saveAndFlush(timeSlot);

        int databaseSizeBeforeUpdate = timeSlotRepository.findAll().size();

        // Update the timeSlot
        TimeSlot updatedTimeSlot = timeSlotRepository.findById(timeSlot.getId()).get();
        // Disconnect from session so that the updates on updatedTimeSlot are not directly saved in db
        em.detach(updatedTimeSlot);
        updatedTimeSlot
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .capacity(UPDATED_CAPACITY)
            .remaining(UPDATED_REMAINING)
            .availabilityStatus(UPDATED_AVAILABILITY_STATUS)
            .centerName(UPDATED_CENTER_NAME)
            .actionBy(UPDATED_ACTION_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        TimeSlotDTO timeSlotDTO = timeSlotMapper.toDto(updatedTimeSlot);

        restTimeSlotMockMvc
            .perform(
                put(ENTITY_API_URL_ID, timeSlotDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(timeSlotDTO))
            )
            .andExpect(status().isOk());

        // Validate the TimeSlot in the database
        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeUpdate);
        TimeSlot testTimeSlot = timeSlotList.get(timeSlotList.size() - 1);
        assertThat(testTimeSlot.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testTimeSlot.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testTimeSlot.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testTimeSlot.getRemaining()).isEqualTo(UPDATED_REMAINING);
        assertThat(testTimeSlot.getAvailabilityStatus()).isEqualTo(UPDATED_AVAILABILITY_STATUS);
        assertThat(testTimeSlot.getCenterName()).isEqualTo(UPDATED_CENTER_NAME);
        assertThat(testTimeSlot.getActionBy()).isEqualTo(UPDATED_ACTION_BY);
        assertThat(testTimeSlot.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTimeSlot.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingTimeSlot() throws Exception {
        int databaseSizeBeforeUpdate = timeSlotRepository.findAll().size();
        timeSlot.setId(count.incrementAndGet());

        // Create the TimeSlot
        TimeSlotDTO timeSlotDTO = timeSlotMapper.toDto(timeSlot);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeSlotMockMvc
            .perform(
                put(ENTITY_API_URL_ID, timeSlotDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(timeSlotDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeSlot in the database
        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTimeSlot() throws Exception {
        int databaseSizeBeforeUpdate = timeSlotRepository.findAll().size();
        timeSlot.setId(count.incrementAndGet());

        // Create the TimeSlot
        TimeSlotDTO timeSlotDTO = timeSlotMapper.toDto(timeSlot);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeSlotMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(timeSlotDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeSlot in the database
        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTimeSlot() throws Exception {
        int databaseSizeBeforeUpdate = timeSlotRepository.findAll().size();
        timeSlot.setId(count.incrementAndGet());

        // Create the TimeSlot
        TimeSlotDTO timeSlotDTO = timeSlotMapper.toDto(timeSlot);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeSlotMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeSlotDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TimeSlot in the database
        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTimeSlotWithPatch() throws Exception {
        // Initialize the database
        timeSlotRepository.saveAndFlush(timeSlot);

        int databaseSizeBeforeUpdate = timeSlotRepository.findAll().size();

        // Update the timeSlot using partial update
        TimeSlot partialUpdatedTimeSlot = new TimeSlot();
        partialUpdatedTimeSlot.setId(timeSlot.getId());

        partialUpdatedTimeSlot.startTime(UPDATED_START_TIME).capacity(UPDATED_CAPACITY).remaining(UPDATED_REMAINING);

        restTimeSlotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTimeSlot.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTimeSlot))
            )
            .andExpect(status().isOk());

        // Validate the TimeSlot in the database
        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeUpdate);
        TimeSlot testTimeSlot = timeSlotList.get(timeSlotList.size() - 1);
        assertThat(testTimeSlot.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testTimeSlot.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testTimeSlot.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testTimeSlot.getRemaining()).isEqualTo(UPDATED_REMAINING);
        assertThat(testTimeSlot.getAvailabilityStatus()).isEqualTo(DEFAULT_AVAILABILITY_STATUS);
        assertThat(testTimeSlot.getCenterName()).isEqualTo(DEFAULT_CENTER_NAME);
        assertThat(testTimeSlot.getActionBy()).isEqualTo(DEFAULT_ACTION_BY);
        assertThat(testTimeSlot.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testTimeSlot.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateTimeSlotWithPatch() throws Exception {
        // Initialize the database
        timeSlotRepository.saveAndFlush(timeSlot);

        int databaseSizeBeforeUpdate = timeSlotRepository.findAll().size();

        // Update the timeSlot using partial update
        TimeSlot partialUpdatedTimeSlot = new TimeSlot();
        partialUpdatedTimeSlot.setId(timeSlot.getId());

        partialUpdatedTimeSlot
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .capacity(UPDATED_CAPACITY)
            .remaining(UPDATED_REMAINING)
            .availabilityStatus(UPDATED_AVAILABILITY_STATUS)
            .centerName(UPDATED_CENTER_NAME)
            .actionBy(UPDATED_ACTION_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restTimeSlotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTimeSlot.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTimeSlot))
            )
            .andExpect(status().isOk());

        // Validate the TimeSlot in the database
        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeUpdate);
        TimeSlot testTimeSlot = timeSlotList.get(timeSlotList.size() - 1);
        assertThat(testTimeSlot.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testTimeSlot.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testTimeSlot.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testTimeSlot.getRemaining()).isEqualTo(UPDATED_REMAINING);
        assertThat(testTimeSlot.getAvailabilityStatus()).isEqualTo(UPDATED_AVAILABILITY_STATUS);
        assertThat(testTimeSlot.getCenterName()).isEqualTo(UPDATED_CENTER_NAME);
        assertThat(testTimeSlot.getActionBy()).isEqualTo(UPDATED_ACTION_BY);
        assertThat(testTimeSlot.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTimeSlot.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingTimeSlot() throws Exception {
        int databaseSizeBeforeUpdate = timeSlotRepository.findAll().size();
        timeSlot.setId(count.incrementAndGet());

        // Create the TimeSlot
        TimeSlotDTO timeSlotDTO = timeSlotMapper.toDto(timeSlot);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeSlotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, timeSlotDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(timeSlotDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeSlot in the database
        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTimeSlot() throws Exception {
        int databaseSizeBeforeUpdate = timeSlotRepository.findAll().size();
        timeSlot.setId(count.incrementAndGet());

        // Create the TimeSlot
        TimeSlotDTO timeSlotDTO = timeSlotMapper.toDto(timeSlot);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeSlotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(timeSlotDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeSlot in the database
        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTimeSlot() throws Exception {
        int databaseSizeBeforeUpdate = timeSlotRepository.findAll().size();
        timeSlot.setId(count.incrementAndGet());

        // Create the TimeSlot
        TimeSlotDTO timeSlotDTO = timeSlotMapper.toDto(timeSlot);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeSlotMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(timeSlotDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TimeSlot in the database
        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTimeSlot() throws Exception {
        // Initialize the database
        timeSlotRepository.saveAndFlush(timeSlot);

        int databaseSizeBeforeDelete = timeSlotRepository.findAll().size();

        // Delete the timeSlot
        restTimeSlotMockMvc
            .perform(delete(ENTITY_API_URL_ID, timeSlot.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
