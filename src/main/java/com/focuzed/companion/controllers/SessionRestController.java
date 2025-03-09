package com.focuzed.companion.controllers;

import com.focuzed.companion.dto.SessionDto;
import com.focuzed.companion.services.SessionService;
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
public class SessionRestController implements GenericController {

    private final SessionService service;

    @PostMapping
    public ResponseEntity<Void> createNewSession(@RequestParam(value = "trainingPlanId") String trainingPlanId, @RequestBody @Valid SessionDto dto) {
        var sessionId = service.createNewSession(trainingPlanId, dto);
        URI location = generateHeaderLocation(sessionId);
        return ResponseEntity.created(location).build();
    }

}
