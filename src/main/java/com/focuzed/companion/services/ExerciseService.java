package com.focuzed.companion.services;

import com.focuzed.companion.dto.ExerciseDto;
import com.focuzed.companion.entities.ExerciseEntity;
import com.focuzed.companion.mappers.ExerciseMapper;
import com.focuzed.companion.repositories.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExerciseService {

    private final ExerciseRepository repository;
    private final ExerciseMapper exerciseMapper;

    public UUID save(ExerciseDto exerciseDto) {
        log.info("Saving exercise: {}", exerciseDto);

        ExerciseEntity exerciseEntity = exerciseMapper.toEntity(exerciseDto);

        repository.save(exerciseEntity);
        return exerciseEntity.getId();
    }

    public ExerciseDto getExerciseById(String id) {
        var exerciseId = UUID.fromString(id);

        var exercise = repository.findById(exerciseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found"));

        return exerciseMapper.toDto(exercise);
    }

    public void delete(String id) {
        log.info("Deleting exercise with ID: {} ", id);
        var exerciseId = UUID.fromString(id);

        var exercise = repository.findById(exerciseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found"));

        repository.delete(exercise);
    }

    public ExerciseDto update(String id, ExerciseDto dto) {
        var exerciseId = UUID.fromString(id);

        var exercise = repository.findById(exerciseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found"));

        exercise.setTitle(dto.title());
        exercise.setDescription(dto.description());
        exercise.setUrl(dto.url());

        repository.save(exercise);

        return exerciseMapper.toDto(exercise);
    }

}
