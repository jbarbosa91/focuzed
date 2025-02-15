package com.focuzed.companion.services;

import com.focuzed.companion.dto.ExerciseSetDto;
import com.focuzed.companion.entities.ExerciseSet;
import com.focuzed.companion.mappers.ExerciseSetMapper;
import com.focuzed.companion.repositories.ExerciseSetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SetService {
    private final ExerciseSetRepository repository;
    private final ExerciseSetMapper exerciseSetMapper;

    public UUID save(ExerciseSetDto exerciseSetDto) {
        log.info("Saving exercise set: {}", exerciseSetDto);

        ExerciseSet exerciseSet = exerciseSetMapper.toEntity(exerciseSetDto);

        repository.save(exerciseSet);
        return exerciseSet.getId();
    }

    public ExerciseSetDto getExerciseSetById(String id) {
        var exerciseSetId = UUID.fromString(id);

        var exerciseSet = repository.findById(exerciseSetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise set not found"));

        return exerciseSetMapper.toDto(exerciseSet);
    }

    public void delete(String id) {
        log.info("Deleting exercise set with ID: {} ", id);
        var exerciseSetId = UUID.fromString(id);

        var exerciseSet = repository.findById(exerciseSetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise set not found"));

        repository.delete(exerciseSet);
    }

    public ExerciseSetDto update(String id, ExerciseSetDto dto) {
        var exerciseSetId = UUID.fromString(id);

        var exerciseSet = repository.findById(exerciseSetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise set not found"));

        exerciseSet.setWeight(dto.weight());
        exerciseSet.setReps(dto.reps());
        exerciseSet.setDifficulty(dto.difficulty());

        repository.save(exerciseSet);

        return exerciseSetMapper.toDto(exerciseSet);
    }
}
