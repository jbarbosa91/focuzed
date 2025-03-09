package com.focuzed.companion.services;

import com.focuzed.companion.dto.ExerciseSessionDto;
import com.focuzed.companion.dto.SessionDto;
import com.focuzed.companion.dto.SetDto;
import com.focuzed.companion.entities.ExerciseSessionEntity;
import com.focuzed.companion.entities.SessionEntity;
import com.focuzed.companion.entities.SetEntity;
import com.focuzed.companion.repositories.ExerciseRepository;
import com.focuzed.companion.repositories.SessionRepository;
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
public class SessionService {

    private final SessionRepository sessionRepository;
    private final TrainingPlanService trainingPlanService;
    private final ExerciseRepository exerciseRepository;

    // TODO: adapt to add new session instead of adding training plan
    @Transactional
    public UUID createNewSession(String trainingPlanId, SessionDto sessionDto) {
        log.info("Creating new session: {}", sessionDto);

        var trainingPlan = trainingPlanService.findTrainingPlanById(UUID.fromString(trainingPlanId));

        if (trainingPlan == null || trainingPlan.getIsActive().equals(Boolean.FALSE)) {
            return null;
        }

        SessionEntity sessionEntity = convertSessionDtoToEntity(sessionDto);

        sessionEntity.setTrainingPlan(trainingPlan);

        sessionRepository.save(sessionEntity);

        return sessionEntity.getId();
    }

    private SessionEntity convertSessionDtoToEntity(SessionDto sessionDto) {
        var sessionEntity = new SessionEntity();
        for (ExerciseSessionDto exerciseSessionDto : sessionDto.exercises()) {
            var exercise = exerciseRepository.findById(exerciseSessionDto.exerciseId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found"));

            var exerciseSession = new ExerciseSessionEntity();
            exerciseSession.setExerciseEntity(exercise);
            exerciseSession.setExerciseOrder(exerciseSessionDto.exerciseOrder());

            for (SetDto setDto : exerciseSessionDto.sets()) {
                var set = SetEntity.builder()
                        .setNumber(setDto.setNumber())
                        .repetitions(setDto.repetitions())
                        .build();

                exerciseSession.addSet(set);
            }
            sessionEntity.setStatus(sessionDto.status());
            sessionEntity.addExercise(exerciseSession);
            sessionEntity.setDay(sessionDto.day());
        }

        return sessionEntity;
    }

}
