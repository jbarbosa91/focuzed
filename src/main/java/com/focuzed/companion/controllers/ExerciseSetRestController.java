package com.focuzed.companion.controllers;

import com.focuzed.companion.dto.ExerciseSetDto;
import com.focuzed.companion.services.SetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/exercise-set")
@RequiredArgsConstructor
@Slf4j
public class ExerciseSetRestController implements GenericController {


    private final SetService service;

    @PostMapping
    public ResponseEntity<Void> saveSet(@RequestBody @Valid ExerciseSetDto exerciseSetDto) {
        log.info("Saving exercise set: {}", exerciseSetDto);
        var exerciseId = service.save(exerciseSetDto);
        URI location = generateHeaderLocation(exerciseId);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ExerciseSetDto> getById(@PathVariable("id") String id) {
        var exerciseSetDto = service.getExerciseSetById(id);
        return ResponseEntity.ok(exerciseSetDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<ExerciseSetDto> update(@PathVariable String id, @RequestBody @Valid ExerciseSetDto dto) {
        var updatedExercise = service.update(id, dto);
        return ResponseEntity.ok(updatedExercise);
    }

}
