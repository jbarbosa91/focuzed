package com.focuzed.companion.services;

import com.focuzed.companion.dto.*;
import com.focuzed.companion.entities.*;
import com.focuzed.companion.repositories.ExerciseRepository;
import com.focuzed.companion.repositories.TrainingPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainingPlanService {

    private final UserService userService;
    private final ExerciseRepository exerciseRepository;
    private final TrainingPlanRepository trainingPlanRepository;

    @Transactional
    public UUID createTrainingPlan(String userIdString, TrainingPlanDto trainingPlanDto) {
        log.info("Saving training plan: {}", trainingPlanDto);

        UserEntity userEntity = userService.getUserById(userIdString);

        if (userEntity == null) {
            return null;
        }

        TrainingPlanEntity trainingPlan = convertTrainingPlanDtoToEntity(trainingPlanDto);

        trainingPlanRepository.save(trainingPlan);

        // TODO: Add new training plan entity to user

        userService.save(userEntity);
        return trainingPlan.getId();
    }

    // TODO : Finish Mapping
    private TrainingPlanEntity convertTrainingPlanDtoToEntity(TrainingPlanDto trainingPlanDto) {
        var trainingPlan = new TrainingPlanEntity();
        for (PlanDayTemplateDto planDayTemplateDto : trainingPlanDto.planDayTemplateDtos()) {
            for (TemplateExerciseDto templateExerciseDto : planDayTemplateDto.templateExerciseDtos()) {
                var exercise = exerciseRepository.findById(templateExerciseDto.exerciseId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found"));

            }
        }

        return trainingPlan;
    }

}
