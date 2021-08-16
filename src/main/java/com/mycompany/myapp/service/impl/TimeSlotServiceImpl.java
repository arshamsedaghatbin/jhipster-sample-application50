package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.TimeSlot;
import com.mycompany.myapp.repository.TimeSlotRepository;
import com.mycompany.myapp.service.TimeSlotService;
import com.mycompany.myapp.service.dto.TimeSlotDTO;
import com.mycompany.myapp.service.mapper.TimeSlotMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TimeSlot}.
 */
@Service
@Transactional
public class TimeSlotServiceImpl implements TimeSlotService {

    private final Logger log = LoggerFactory.getLogger(TimeSlotServiceImpl.class);

    private final TimeSlotRepository timeSlotRepository;

    private final TimeSlotMapper timeSlotMapper;

    public TimeSlotServiceImpl(TimeSlotRepository timeSlotRepository, TimeSlotMapper timeSlotMapper) {
        this.timeSlotRepository = timeSlotRepository;
        this.timeSlotMapper = timeSlotMapper;
    }

    @Override
    public TimeSlotDTO save(TimeSlotDTO timeSlotDTO) {
        log.debug("Request to save TimeSlot : {}", timeSlotDTO);
        TimeSlot timeSlot = timeSlotMapper.toEntity(timeSlotDTO);
        timeSlot = timeSlotRepository.save(timeSlot);
        return timeSlotMapper.toDto(timeSlot);
    }

    @Override
    public Optional<TimeSlotDTO> partialUpdate(TimeSlotDTO timeSlotDTO) {
        log.debug("Request to partially update TimeSlot : {}", timeSlotDTO);

        return timeSlotRepository
            .findById(timeSlotDTO.getId())
            .map(
                existingTimeSlot -> {
                    timeSlotMapper.partialUpdate(existingTimeSlot, timeSlotDTO);

                    return existingTimeSlot;
                }
            )
            .map(timeSlotRepository::save)
            .map(timeSlotMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TimeSlotDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TimeSlots");
        return timeSlotRepository.findAll(pageable).map(timeSlotMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TimeSlotDTO> findOne(Long id) {
        log.debug("Request to get TimeSlot : {}", id);
        return timeSlotRepository.findById(id).map(timeSlotMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TimeSlot : {}", id);
        timeSlotRepository.deleteById(id);
    }
}
