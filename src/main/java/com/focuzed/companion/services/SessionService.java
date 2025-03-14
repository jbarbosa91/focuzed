package com.focuzed.companion.services;

import com.focuzed.companion.dto.SessionDto;
import com.focuzed.companion.entities.*;
import com.focuzed.companion.exceptions.DuplicatedEntryException;
import com.focuzed.companion.exceptions.OperationNotAllowedException;
import com.focuzed.companion.repositories.ExerciseRepository;
import com.focuzed.companion.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final TrainingPlanService trainingPlanService;
    private final ExerciseRepository exerciseRepository;

    // TODO: adapt to add new session instead of adding training plan
    @Transactional
    public UUID createNewSession(String trainingPlanId, SessionDto sessionDto) {
        log.info("Creating new session: {}", sessionDto);

        Optional<SessionEntity> sessionOptional = sessionRepository.findByCreatedAtCurrentDay(LocalDate.now());

        if (sessionOptional.isPresent()) {
            throw new DuplicatedEntryException("Session already created today");
        }

        var trainingPlan = trainingPlanService.findTrainingPlanById(UUID.fromString(trainingPlanId));

        if (trainingPlan == null || trainingPlan.getIsActive().equals(Boolean.FALSE)) {
            throw new OperationNotAllowedException("Training plan is not active");
        }

        Optional<PlanDayTemplateEntity> planDayTemplate = trainingPlan.getPlanDayTemplateEntities().stream()
                .filter(planDay -> planDay.getDay().equals(sessionDto.day())).findFirst();

        if (planDayTemplate.isEmpty()) {
            // TODO: Create dedicated exception and add that case to Global Exception Handler
            return null;
        }

        var planDayTemplateEntity = planDayTemplate.get();

        List<TemplateExerciseEntity> templateExercises =
                planDayTemplateEntity.getTemplateExerciseEntities();

        SessionEntity sessionEntity = convertSessionDtoToEntity(sessionDto, templateExercises);

        sessionEntity.setPlanDayTemplate(planDayTemplateEntity);

        sessionEntity.setTrainingPlan(trainingPlan);

        sessionRepository.save(sessionEntity);

        return sessionEntity.getId();
    }

    private SessionEntity convertSessionDtoToEntity(SessionDto sessionDto,
                                                    List<TemplateExerciseEntity> templateExercises) {
        var sessionEntity = new SessionEntity();
        for (TemplateExerciseEntity templateExerciseEntity : templateExercises) {
            var exercise = exerciseRepository.findById(templateExerciseEntity.getExerciseEntity().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found"));

            var exerciseSession = new ExerciseSessionEntity();
            exerciseSession.setExerciseEntity(exercise);
            exerciseSession.setExerciseOrder(templateExerciseEntity.getExerciseOrder());
            exerciseSession.setNotes(templateExerciseEntity.getNotes());
            exerciseSession.setIsCompleted(false);

            /* for (SetDto setDto : templateExerciseEntity.getPlannedSets()) {
                var set = SetEntity.builder()
                        .setNumber(setDto.setNumber())
                        .repetitions(setDto.repetitions())
                        .build();

                exerciseSession.addSet(set);
            } */
            sessionEntity.addExercise(exerciseSession);
        }
        sessionEntity.setStatus(Status.PENDING);
        sessionEntity.setDay(sessionDto.day());
        sessionEntity.setIsCompleted(false);

        return sessionEntity;
    }

    private SessionDto convertSessionEntityToDto(SessionEntity sessionEntity) {
        return null;
    }

}
