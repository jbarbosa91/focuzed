package com.focuzed.companion.controllers;

import com.focuzed.companion.dto.SetDto;
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
public class SetRestController implements GenericController {


    private final SetService service;

    @PostMapping
    public ResponseEntity<Void> saveSet(@RequestBody @Valid SetDto setDto) {
        log.info("Saving exercise set: {}", setDto);
        var exerciseId = service.save(setDto);
        URI location = generateHeaderLocation(exerciseId);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<SetDto> getById(@PathVariable("id") String id) {
        var exerciseSetDto = service.getExerciseSetById(id);
        return ResponseEntity.ok(exerciseSetDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<SetDto> update(@PathVariable String id, @RequestBody @Valid SetDto dto) {
        var updatedExercise = service.update(id, dto);
        return ResponseEntity.ok(updatedExercise);
    }

}
