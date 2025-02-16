package com.focuzed.companion.mappers;

import com.focuzed.companion.dto.ExerciseSetDto;
import com.focuzed.companion.entities.SetEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExerciseSetMapper {
    ExerciseSetDto toDto(SetEntity setEntity);

    SetEntity toEntity(ExerciseSetDto exerciseSetDto);
}
