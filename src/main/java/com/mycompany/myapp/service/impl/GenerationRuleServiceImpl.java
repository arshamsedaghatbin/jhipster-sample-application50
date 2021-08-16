package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.GenerationRule;
import com.mycompany.myapp.repository.GenerationRuleRepository;
import com.mycompany.myapp.service.GenerationRuleService;
import com.mycompany.myapp.service.dto.GenerationRuleDTO;
import com.mycompany.myapp.service.mapper.GenerationRuleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GenerationRule}.
 */
@Service
@Transactional
public class GenerationRuleServiceImpl implements GenerationRuleService {

    private final Logger log = LoggerFactory.getLogger(GenerationRuleServiceImpl.class);

    private final GenerationRuleRepository generationRuleRepository;

    private final GenerationRuleMapper generationRuleMapper;

    public GenerationRuleServiceImpl(GenerationRuleRepository generationRuleRepository, GenerationRuleMapper generationRuleMapper) {
        this.generationRuleRepository = generationRuleRepository;
        this.generationRuleMapper = generationRuleMapper;
    }

    @Override
    public GenerationRuleDTO save(GenerationRuleDTO generationRuleDTO) {
        log.debug("Request to save GenerationRule : {}", generationRuleDTO);
        GenerationRule generationRule = generationRuleMapper.toEntity(generationRuleDTO);
        generationRule = generationRuleRepository.save(generationRule);
        return generationRuleMapper.toDto(generationRule);
    }

    @Override
    public Optional<GenerationRuleDTO> partialUpdate(GenerationRuleDTO generationRuleDTO) {
        log.debug("Request to partially update GenerationRule : {}", generationRuleDTO);

        return generationRuleRepository
            .findById(generationRuleDTO.getId())
            .map(
                existingGenerationRule -> {
                    generationRuleMapper.partialUpdate(existingGenerationRule, generationRuleDTO);

                    return existingGenerationRule;
                }
            )
            .map(generationRuleRepository::save)
            .map(generationRuleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GenerationRuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GenerationRules");
        return generationRuleRepository.findAll(pageable).map(generationRuleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GenerationRuleDTO> findOne(Long id) {
        log.debug("Request to get GenerationRule : {}", id);
        return generationRuleRepository.findById(id).map(generationRuleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GenerationRule : {}", id);
        generationRuleRepository.deleteById(id);
    }
}
