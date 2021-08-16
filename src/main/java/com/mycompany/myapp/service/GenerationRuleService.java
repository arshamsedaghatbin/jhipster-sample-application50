package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.GenerationRuleDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.GenerationRule}.
 */
public interface GenerationRuleService {
    /**
     * Save a generationRule.
     *
     * @param generationRuleDTO the entity to save.
     * @return the persisted entity.
     */
    GenerationRuleDTO save(GenerationRuleDTO generationRuleDTO);

    /**
     * Partially updates a generationRule.
     *
     * @param generationRuleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GenerationRuleDTO> partialUpdate(GenerationRuleDTO generationRuleDTO);

    /**
     * Get all the generationRules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GenerationRuleDTO> findAll(Pageable pageable);

    /**
     * Get the "id" generationRule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GenerationRuleDTO> findOne(Long id);

    /**
     * Delete the "id" generationRule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
