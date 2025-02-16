package com.focuzed.companion.mappers;

import com.focuzed.companion.dto.ExerciseDto;
import com.focuzed.companion.entities.ExerciseEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {

    ExerciseDto toDto(ExerciseEntity exerciseEntity);

    ExerciseEntity toEntity(ExerciseDto exerciseDto);
}
