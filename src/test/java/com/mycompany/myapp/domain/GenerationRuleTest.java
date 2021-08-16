package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GenerationRuleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GenerationRule.class);
        GenerationRule generationRule1 = new GenerationRule();
        generationRule1.setId(1L);
        GenerationRule generationRule2 = new GenerationRule();
        generationRule2.setId(generationRule1.getId());
        assertThat(generationRule1).isEqualTo(generationRule2);
        generationRule2.setId(2L);
        assertThat(generationRule1).isNotEqualTo(generationRule2);
        generationRule1.setId(null);
        assertThat(generationRule1).isNotEqualTo(generationRule2);
    }
}
