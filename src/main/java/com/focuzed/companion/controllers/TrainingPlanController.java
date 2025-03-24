package com.focuzed.companion.controllers;

import com.focuzed.companion.dto.TrainingPlanDto;
import com.focuzed.companion.services.TrainingPlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/training-plan")
@RequiredArgsConstructor
@Slf4j
public class TrainingPlanController implements GenericController {

    private final TrainingPlanService service;

    @PostMapping
    public ResponseEntity<Void> createTrainingPlan(@RequestParam(value = "userId") String userId, @RequestBody TrainingPlanDto dto) {
        var trainingPlanId = service.createTrainingPlan(userId, dto);
        URI location = generateHeaderLocation(trainingPlanId);
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<UUID> getTrainingPlanId(@RequestParam(value = "userId") String userId) {
        var trainingPlanId = service.getTrainingPlanIdByUserId(userId);

        if (trainingPlanId == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(trainingPlanId, HttpStatus.OK);
    }

}
