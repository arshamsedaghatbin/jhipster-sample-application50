package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.TimeSlotDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TimeSlot} and its DTO {@link TimeSlotDTO}.
 */
@Mapper(componentModel = "spring", uses = { GenerationRuleMapper.class })
public interface TimeSlotMapper extends EntityMapper<TimeSlotDTO, TimeSlot> {
    @Mapping(target = "generationRule", source = "generationRule", qualifiedByName = "id")
    TimeSlotDTO toDto(TimeSlot s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TimeSlotDTO toDtoId(TimeSlot timeSlot);
}
