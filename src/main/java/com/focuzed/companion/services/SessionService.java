package com.focuzed.companion.services;

import com.focuzed.companion.dto.ExerciseSessionDto;
import com.focuzed.companion.dto.SessionDto;
import com.focuzed.companion.dto.SetDto;
import com.focuzed.companion.entities.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final TrainingPlanService trainingPlanService;
    private final ExerciseRepository exerciseRepository;

    public SessionDto findSessionByCurrentDate() {
        Optional<SessionEntity> sessionOptional = sessionRepository.findByCreatedAtCurrentDay(LocalDate.now());

        if (sessionOptional.isPresent()) {
            return convertSessionEntityToDto(sessionOptional.get());
        }

        return null;
    }

    // TODO: adapt to add new session instead of adding training plan
    @Transactional
    public SessionDto createNewSession(SessionDto sessionDto) {
        log.info("Creating new session: {}", sessionDto);

        var trainingPlan = trainingPlanService.findTrainingPlanById(sessionDto.trainingPlanId());

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

        return convertSessionEntityToDto(sessionEntity);
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
        var exercises = new ArrayList<ExerciseSessionDto>();

        for (ExerciseSessionEntity exerciseSessionEntity : sessionEntity.getExercises()) {
            exercises.add(convertExerciseSessionEntityToDto(exerciseSessionEntity));
        }

        return new SessionDto(sessionEntity.getId(),
                exercises,
                sessionEntity.getDay(),
                sessionEntity.getStatus(),
                sessionEntity.getTrainingPlan().getId());
    }

    private ExerciseSessionDto convertExerciseSessionEntityToDto(ExerciseSessionEntity exerciseSessionEntity) {
        var sets = new ArrayList<SetDto>();
        for (SetEntity setEntity : exerciseSessionEntity.getSets()) {
            sets.add(convertSetEntityToDto(setEntity));
        }

        return new ExerciseSessionDto(exerciseSessionEntity.getExerciseEntity().getId(),
                sets,
                exerciseSessionEntity.getExerciseOrder());
    }

    private SetDto convertSetEntityToDto(SetEntity setEntity) {
        return new SetDto(setEntity.getSetNumber(), setEntity.getWeight(), setEntity.getRepetitions());
    }

}
