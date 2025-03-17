package com.focuzed.companion.controllers;

import com.focuzed.companion.dto.SessionDto;
import com.focuzed.companion.services.SessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
@Slf4j
public class SessionRestController implements GenericController {

    private final SessionService service;

    @PostMapping
    public ResponseEntity<SessionDto> createNewSession(@RequestBody @Valid SessionDto dto) {
        SessionDto existingSession = service.findSessionByCurrentDate();

        if (existingSession != null) {
            return new ResponseEntity<>(existingSession, HttpStatus.OK);
        }

        var newSession = service.createNewSession(dto);
        return new ResponseEntity<>(newSession, HttpStatus.CREATED);
    }

}
