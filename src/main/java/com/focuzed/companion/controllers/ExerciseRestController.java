package com.focuzed.companion.controllers;

import com.focuzed.companion.dto.ExerciseDto;
import com.focuzed.companion.services.ExerciseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/exercise")
@RequiredArgsConstructor
@Slf4j
public class ExerciseRestController implements GenericController {

    private final ExerciseService service;

    @PostMapping
    public ResponseEntity<Void> saveExercise(@RequestBody @Valid ExerciseDto exerciseDto) {
        var exerciseId = service.save(exerciseDto);
        URI location = generateHeaderLocation(exerciseId);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ExerciseDto> getById(@PathVariable("id") String id) {
        var exerciseDto = service.getExerciseById(id);
        return ResponseEntity.ok(exerciseDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<ExerciseDto> update(@PathVariable String id, @RequestBody @Valid ExerciseDto dto) {
        var updatedExercise = service.update(id, dto);
        return ResponseEntity.ok(updatedExercise);
    }

}
