package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.GenerationRule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GenerationRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GenerationRuleRepository extends JpaRepository<GenerationRule, Long> {}
