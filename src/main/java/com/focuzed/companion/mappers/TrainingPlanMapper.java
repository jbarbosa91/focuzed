package com.focuzed.companion.mappers;

import com.focuzed.companion.dto.TrainingPlanDto;
import com.focuzed.companion.entities.TrainingPlanEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrainingPlanMapper {

    TrainingPlanDto toDto(TrainingPlanEntity trainingPlanEntity);

    TrainingPlanEntity toEntity(TrainingPlanDto trainingPlanDto);
}