package com.focuzed.companion.mappers;

import com.focuzed.companion.dto.TemplateExerciseDto;
import com.focuzed.companion.entities.TemplateExerciseEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TemplateExerciseMapper {

    TemplateExerciseDto toDto(TemplateExerciseEntity templateExerciseEntity);

    TemplateExerciseEntity toEntity(TemplateExerciseDto templateExerciseDto);
}