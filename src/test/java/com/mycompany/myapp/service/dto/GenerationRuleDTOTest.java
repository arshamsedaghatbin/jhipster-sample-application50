package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GenerationRuleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GenerationRuleDTO.class);
        GenerationRuleDTO generationRuleDTO1 = new GenerationRuleDTO();
        generationRuleDTO1.setId(1L);
        GenerationRuleDTO generationRuleDTO2 = new GenerationRuleDTO();
        assertThat(generationRuleDTO1).isNotEqualTo(generationRuleDTO2);
        generationRuleDTO2.setId(generationRuleDTO1.getId());
        assertThat(generationRuleDTO1).isEqualTo(generationRuleDTO2);
        generationRuleDTO2.setId(2L);
        assertThat(generationRuleDTO1).isNotEqualTo(generationRuleDTO2);
        generationRuleDTO1.setId(null);
        assertThat(generationRuleDTO1).isNotEqualTo(generationRuleDTO2);
    }
}
