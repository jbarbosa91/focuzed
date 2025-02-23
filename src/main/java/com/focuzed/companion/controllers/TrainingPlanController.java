package com.focuzed.companion.controllers;

import com.focuzed.companion.dto.TrainingPlanDto;
import com.focuzed.companion.services.TrainingPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
@Slf4j
public class TrainingPlanController implements GenericController {

    private final TrainingPlanService service;

    @PostMapping
    public ResponseEntity<Void> createTrainingPlan(@RequestParam(value = "userId") String userId, @RequestBody @Valid TrainingPlanDto dto) {
        var trainingPlanId = service.createTrainingPlan(userId, dto);
        URI location = generateHeaderLocation(trainingPlanId);
        return ResponseEntity.created(location).build();
    }
}
