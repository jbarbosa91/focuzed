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

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainingPlanService {

    private final UserService userService;
    private final ExerciseRepository exerciseRepository;
    private final TrainingPlanRepository trainingPlanRepository;

    public UUID getTrainingPlanIdByUserId(String userId) {
        var trainingPlans = trainingPlanRepository.findTrainingPlanEntitiesByUserEntityId(UUID.fromString(userId));

        Optional<TrainingPlanEntity> activeTrainingPlan = trainingPlans.stream().filter(t -> t.getIsActive().equals(Boolean.TRUE)).findFirst();

        if (activeTrainingPlan.isPresent()) {
            return activeTrainingPlan.get().getId();
        }
        return null;
    }

    @Transactional
    public UUID createTrainingPlan(String userIdString, TrainingPlanDto trainingPlanDto) {
        log.info("Saving training plan: {}", trainingPlanDto);

        UserEntity userEntity = userService.getUserById(userIdString);

        if (userEntity == null) {
            return null;
        }

        if (trainingPlanDto.isActive()) {
            var oldTrainingPlanList = trainingPlanRepository.findTrainingPlanEntitiesByUserEntityId(UUID.fromString(userIdString));

            oldTrainingPlanList.forEach(trainingPlan ->
                trainingPlan.setIsActive(Boolean.FALSE));
        }

        TrainingPlanEntity trainingPlan = convertTrainingPlanDtoToEntity(trainingPlanDto);
        trainingPlan.setUserEntity(userEntity);

        return trainingPlanRepository.save(trainingPlan).getId();
    }

    public TrainingPlanEntity findTrainingPlanById(UUID id) {
        return trainingPlanRepository.findById(id).orElse(null);
    }

    // TODO : Test Mapping
    private TrainingPlanEntity convertTrainingPlanDtoToEntity(TrainingPlanDto trainingPlanDto) {
        var trainingPlan = new TrainingPlanEntity();
        List<PlanDayTemplateEntity> planDayTemplateEntities = new ArrayList<>();
        for (PlanDayTemplateDto planDayTemplateDto : trainingPlanDto.planDayTemplateDtos()) {
            List<TemplateExerciseEntity> templateExerciseEntities = new ArrayList<>();

            var planDayTemplateEntity = new PlanDayTemplateEntity();
            planDayTemplateEntity.setTrainingPlan(trainingPlan);

            for (TemplateExerciseDto templateExerciseDto : planDayTemplateDto.templateExerciseDtos()) {
                var exercise = exerciseRepository.findById(templateExerciseDto.exerciseId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found"));

                TemplateExerciseEntity templateExerciseEntity = new TemplateExerciseEntity();

                templateExerciseEntity.setExerciseEntity(exercise);
                templateExerciseEntity.setExerciseOrder(templateExerciseDto.exerciseOrder());
                templateExerciseEntity.setPlannedSets(templateExerciseDto.plannedSets());
                templateExerciseEntity.setNotes(templateExerciseDto.notes());
                templateExerciseEntity.setPlanDayTemplate(planDayTemplateEntity);

                templateExerciseEntities.add(templateExerciseEntity);
            }
            planDayTemplateEntity.setTemplateExerciseEntities(templateExerciseEntities);
            planDayTemplateEntity.setDay(planDayTemplateDto.day());
            planDayTemplateEntity.setDescription(planDayTemplateDto.description());
            planDayTemplateEntity.setTrainingPlan(trainingPlan);

            planDayTemplateEntities.add(planDayTemplateEntity);
        }
        trainingPlan.setPlanDayTemplateEntities(planDayTemplateEntities);
        trainingPlan.setEndDate(trainingPlanDto.endDate());
        trainingPlan.setDaysPerWeek(planDayTemplateEntities.size());
        trainingPlan.setIsActive(trainingPlanDto.isActive());

        return trainingPlan;
    }

//    private TrainingPlanDto convertTrainingPlanEntityToDto(TrainingPlanEntity trainingPlanEntity) {
//
//        var planDayTemplateDtos = new LinkedList<PlanDayTemplateDto>();
//        for (PlanDayTemplateEntity planDayTemplateEntity : trainingPlanEntity.getPlanDayTemplateEntities()) {
//            planDayTemplateDtos.add(convertPlanDayTemplateEntityToDto(planDayTemplateEntity));
//        }
//
//        return new TrainingPlanDto(
//                planDayTemplateDtos,
//                trainingPlanEntity.getEndDate(),
//                trainingPlanEntity.getDaysPerWeek(),
//                trainingPlanEntity.getIsActive());
//    };
//
//    private PlanDayTemplateDto convertPlanDayTemplateEntityToDto(PlanDayTemplateEntity planDayTemplateEntity) {
//        return new PlanDayTemplateDto()
//    }

}
