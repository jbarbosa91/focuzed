package com.focuzed.companion.mappers;

import com.focuzed.companion.dto.ExerciseDto;
import com.focuzed.companion.entities.Exercise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {

    ExerciseDto toDto(Exercise exercise);

    Exercise toEntity(ExerciseDto exerciseDto);
}
