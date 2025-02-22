package com.focuzed.companion.services;

import com.focuzed.companion.dto.SetDto;
import com.focuzed.companion.entities.SetEntity;
import com.focuzed.companion.mappers.SetMapper;
import com.focuzed.companion.repositories.SetRepository;
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
    private final SetRepository repository;
    private final SetMapper setMapper;

    public UUID save(SetDto setDto) {
        log.info("Saving exercise set: {}", setDto);

        SetEntity setEntity = setMapper.toEntity(setDto);

        repository.save(setEntity);
        return setEntity.getId();
    }

    public SetDto getExerciseSetById(String id) {
        var exerciseSetId = UUID.fromString(id);

        var exerciseSet = repository.findById(exerciseSetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise set not found"));

        return setMapper.toDto(exerciseSet);
    }

    public void delete(String id) {
        log.info("Deleting exercise set with ID: {} ", id);
        var exerciseSetId = UUID.fromString(id);

        var exerciseSet = repository.findById(exerciseSetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise set not found"));

        repository.delete(exerciseSet);
    }

    public SetDto update(String id, SetDto dto) {
        var exerciseSetId = UUID.fromString(id);

        var exerciseSet = repository.findById(exerciseSetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise set not found"));

        exerciseSet.setWeight(dto.weight());
        exerciseSet.setRepetitions(dto.repetitions());

        repository.save(exerciseSet);

        return setMapper.toDto(exerciseSet);
    }
}
