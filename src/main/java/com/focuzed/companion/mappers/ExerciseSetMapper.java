package com.focuzed.companion.mappers;

import com.focuzed.companion.dto.ExerciseSetDto;
import com.focuzed.companion.entities.ExerciseSet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExerciseSetMapper {
    ExerciseSetDto toDto(ExerciseSet exerciseSet);

    ExerciseSet toEntity(ExerciseSetDto exerciseSetDto);
}
