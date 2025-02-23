package com.focuzed.companion.mappers;

import com.focuzed.companion.dto.PlanDayTemplateDto;
import com.focuzed.companion.entities.PlanDayTemplateEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PlanDayTemplateMapper {

    PlanDayTemplateDto toDto(PlanDayTemplateEntity planDayTemplateEntity);

    PlanDayTemplateEntity toEntity(PlanDayTemplateDto planDayTemplateDto);
}