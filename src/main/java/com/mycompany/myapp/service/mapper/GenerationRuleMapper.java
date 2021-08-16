package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.GenerationRuleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GenerationRule} and its DTO {@link GenerationRuleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GenerationRuleMapper extends EntityMapper<GenerationRuleDTO, GenerationRule> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GenerationRuleDTO toDtoId(GenerationRule generationRule);
}
