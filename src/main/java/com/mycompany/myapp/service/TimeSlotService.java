package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.TimeSlotDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.TimeSlot}.
 */
public interface TimeSlotService {
    /**
     * Save a timeSlot.
     *
     * @param timeSlotDTO the entity to save.
     * @return the persisted entity.
     */
    TimeSlotDTO save(TimeSlotDTO timeSlotDTO);

    /**
     * Partially updates a timeSlot.
     *
     * @param timeSlotDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TimeSlotDTO> partialUpdate(TimeSlotDTO timeSlotDTO);

    /**
     * Get all the timeSlots.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TimeSlotDTO> findAll(Pageable pageable);

    /**
     * Get the "id" timeSlot.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TimeSlotDTO> findOne(Long id);

    /**
     * Delete the "id" timeSlot.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
