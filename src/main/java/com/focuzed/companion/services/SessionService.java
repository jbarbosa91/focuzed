package com.focuzed.companion.services;

import com.focuzed.companion.dto.ExerciseSessionDto;
import com.focuzed.companion.dto.SessionDto;
import com.focuzed.companion.dto.SetDto;
import com.focuzed.companion.entities.ExerciseSessionEntity;
import com.focuzed.companion.entities.SessionEntity;
import com.focuzed.companion.entities.SetEntity;
import com.focuzed.companion.entities.UserEntity;
import com.focuzed.companion.repositories.ExerciseRepository;
import com.focuzed.companion.repositories.SessionRepository;
import com.focuzed.companion.repositories.UserRepository;
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
    private final UserService userService;
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;

    @Transactional
    public UUID createTrainingPlan(String userIdString, SessionDto sessionDto) {
        log.info("Saving training plan: {}", sessionDto);

        UserEntity userEntity = userService.getUserById(userIdString);

        if (userEntity == null) {
            return null;
        }

        SessionEntity sessionEntity = convertSessionDtoToEntity(sessionDto);

        sessionRepository.save(sessionEntity);

 // TODO: Add new training plan entity to user

        userRepository.save(userEntity);
        return sessionEntity.getId();
    }

    private SessionEntity convertSessionDtoToEntity(SessionDto sessionDto) {
        var sessionEntity = new SessionEntity();
        for (ExerciseSessionDto exerciseSessionDto : sessionDto.exercises()) {
            var exercise = exerciseRepository.findById(exerciseSessionDto.exerciseId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found"));

            var exerciseSession = new ExerciseSessionEntity();
            exerciseSession.setExerciseEntity(exercise);

            for (SetDto setDto : exerciseSessionDto.sets()) {
                var set = SetEntity.builder()
                        .setNumber(setDto.setNumber())
                        .repetitions(setDto.repetitions())
                        .build();

                exerciseSession.addSet(set);
            }

            sessionEntity.addExercise(exerciseSession);
        }

        return sessionEntity;
    }

}
